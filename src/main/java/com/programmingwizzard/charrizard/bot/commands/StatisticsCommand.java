package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class StatisticsCommand extends Command {
    private final Charrizard charrizard;
    private final Runtime runtime;
    private final NumberFormat numberFormat;

    public StatisticsCommand(Charrizard charrizard) {
        super("statistics");
        this.charrizard = charrizard;
        this.runtime = Runtime.getRuntime();
        this.numberFormat = NumberFormat.getInstance();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        if (args.length == 0 || args.length == 1) {
            sendUsage(message, "!statistics <bot|guild|delete>");
            return;
        }
        switch (args[1]) {
            case "bot":
                EmbedBuilder builder = getEmbedBuilder()
                                               .setTitle("Charrizard")
                                               .setFooter("© 2017 Charrizard contributors", null)
                                               .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                               .setColor(new Color(0, 250, 0))
                                               .addField("Statistics", "Servers: " + charrizard.getDiscordAPI().getGuilds().size() +
                                                                               "\nClients: " + charrizard.getDiscordAPI().getUsers().size() +
                                                                               "\nMemory:" +
                                                                               "\n  Free: " + numberFormat.format(runtime.freeMemory() / 1024) + " KB" +
                                                                               "\n  Allocated: " + numberFormat.format(runtime.totalMemory() / 1024) + " KB" +
                                                                               "\n  Max: " + numberFormat.format(runtime.maxMemory() / 1024) + " KB", true);
                sendEmbedMessage(message, builder);
                break;
            case "guild":
                checkGuild(message, args);
                break;
            case "delete":
                deleteStatistics(message, args);
                break;
            default:
                sendUsage(message, "!statistics <bot|guild|delete>");
                break;
        }
    }

    private void checkGuild(CMessage message, String[] args) {
        if (args.length == 2) {
            sendUsage(message, "!statistics guild <id|this>");
            return;
        }
        String guildId = args[2];
        Guild targetGuild = null;
        if (guildId.equals("this")) {
            targetGuild = message.getGuild();
        } else {
            targetGuild = charrizard.getDiscordAPI().getGuildById(guildId);
        }
        if (targetGuild == null) {
            sendError(message, "This guild does not exists!");
            return;
        }
        StatisticGuild statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(targetGuild);
        if (statisticGuild == null) {
            charrizard.getStatisticsGuildManager().load(targetGuild);
        }
        statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(targetGuild);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> channelEntry : statisticGuild.getChannelMap().entrySet()) {
            Channel channel = targetGuild.getTextChannelById(channelEntry.getKey());
            if (channel == null) {
                continue;
            }
            sb.append("\n  ").append(channel.getName()).append(": ").append(channelEntry.getValue());
        }
        String channels = sb.toString();
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField(targetGuild.getName(), "Messages on channels: " + channels, true);
        sendEmbedMessage(message, builder);
    }

    private void deleteStatistics(CMessage message, String[] args) {
        if (args.length == 2) {
            sendUsage(message, "!statistics delete <guild id|this>");
            return;
        }
        String guildId = args[2];
        Guild targetGuild = null;
        if (guildId.equals("this")) {
            targetGuild = message.getGuild();
        } else {
            targetGuild = charrizard.getDiscordAPI().getGuildById(guildId);
        }
        if (targetGuild == null) {
            sendError(message, "This guild does not exists!");
            return;
        }
        StatisticGuild statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(targetGuild);
        if (statisticGuild == null) {
            sendError(message, "This guild does not exists!");
            return;
        }
        User owner = targetGuild.getOwner().getUser();
        if (!owner.getId().equals(message.getAuthor().getId())) {
            sendError(message, "You are not owner of server!");
            return;
        }
        for (Map.Entry<String, Integer> channelEntry : statisticGuild.getChannelMap().entrySet()) {
            statisticGuild.getChannelMap().put(channelEntry.getKey(), 0);
        }
        statisticGuild.save(charrizard.getRedisConnection().getJedis());
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField(targetGuild.getName(), "Statistics are correctly restarted!", true);
        sendEmbedMessage(message, builder);
    }
}
