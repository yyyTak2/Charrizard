package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.basic.CTextChannel;
import com.programmingwizzard.charrizard.bot.basic.CVoiceChannel;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.text.NumberFormat;

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
            sendUsage(message, "!statistics <bot|guild>");
            return;
        }
        switch (args[1]) {
            case "bot":
                EmbedBuilder builder = getEmbedBuilder()
                                               .addField("Servers", String.valueOf(charrizard.getDiscordAPI().getGuilds().size()), true)
                                               .addField("Clients", String.valueOf(charrizard.getDiscordAPI().getUsers().size()), true)
                                               .addField("Memory",
                                                       "Free: " + numberFormat.format(runtime.freeMemory() / 1024) + " KB" +
                                                               "\nAllocated: " + numberFormat.format(runtime.totalMemory() / 1024) + " KB" +
                                                               "\nMax: " + numberFormat.format(runtime.maxMemory() / 1024) + " KB", true);
                sendEmbedMessage(message, builder);
                break;
            case "guild":
                handleGuild(message, args);
                break;
            default:
                sendUsage(message, "!statistics <bot|guild>");
                break;
        }
    }

    private void handleGuild(CMessage message, String[] args) {
        if (args.length == 2) {
            sendUsage(message, "!statistics guild <id|this>");
            return;
        }
        String guild = args[2];
        Guild targetGuild = null;
        if (guild.equals("this")) {
            targetGuild = message.getGuild();
        } else {
            targetGuild = this.charrizard.getDiscordAPI().getGuildById(args[2]);
        }
        if (targetGuild == null) {
            sendError(message, "This guild does not exists!");
            return;
        }
        CGuild cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(message.getGuild());
            cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        }
        StringBuilder messages = new StringBuilder();
        for (CTextChannel textChannel : cGuild.getTextChannels()) {
            messages.append(textChannel.getName()).append(": ").append(textChannel.getMessages()).append("\n");
        }
        StringBuilder connections = new StringBuilder();
        for (CVoiceChannel voiceChannel : cGuild.getVoiceChannels()) {
            connections.append(voiceChannel.getName()).append(": ").append(voiceChannel.getConnections()).append("\n");
        }
        EmbedBuilder builder = getEmbedBuilder()
                                       .addField("Messages on channels", messages.toString(), true)
                                       .addField("Connections to channels", connections.toString(), true);
        sendEmbedMessage(message, builder);
    }
}
