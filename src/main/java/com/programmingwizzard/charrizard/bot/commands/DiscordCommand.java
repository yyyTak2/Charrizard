package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class DiscordCommand extends Command {
    private final Charrizard charrizard;

    public DiscordCommand(Charrizard charrizard)
    {
        super("discord");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = message.getChannel();
        if (args.length == 0 || args.length == 1) {
            sendUsage(message, "!discord <user|guild|icons>");
            return;
        }
        switch (args[1]) {
            case "user":
                this.checkUser(message, args);
                break;
            case "guild":
                this.checkGuild(message, args);
                break;
            case "icons":
                this.checkIcons(message, args);
                break;
            default:
                sendUsage(message, "!discord <user|guild|icons>");
                break;
        }
    }

    private void checkUser(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3) {
            sendUsage(message, "!discord <user> <id|mention>");
            return;
        }
        List<User> mentionedUsers = message.getMentionedUsers();
        User targetUser = null;
        if (mentionedUsers.size() == 0) {
            targetUser = this.charrizard.getDiscordAPI().getUserById(args[2]);
        } else {
            targetUser = mentionedUsers.get(0);
        }
        if (targetUser == null) {
            sendError(message, "This user does not exists!");
            return;
        }
        OffsetDateTime creationTime = targetUser.getCreationTime();
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField("Discord User: " + targetUser.getName(),
                                               "Bot account: " + BooleanUtils.parseBoolean(targetUser.isBot()) +
                                                       "\nMention tag: " + targetUser.getAsMention() +
                                                       "\nAvatar: " + targetUser.getAvatarUrl() +
                                                       "\nRegister date: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute(), true);

        sendEmbedMessage(message, builder);
    }

    private void checkGuild(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3) {
            sendUsage(message, "!discord guild <guild id|this>");
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
        OffsetDateTime creationTime = targetGuild.getCreationTime();
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField("Discord Guild: " + targetGuild.getName(), "Icon: " + targetGuild.getIconUrl() +
                                                                                                    "\nOwner:" +
                                                                                                    "\n  Name: " + targetGuild.getOwner().getUser().getName() +
                                                                                                    "\n  Mention tag: " + targetGuild.getOwner().getAsMention() +
                                                                                                    "\n  Status: " + targetGuild.getOwner().getOnlineStatus() +
                                                                                                    "\nStatistics:" +
                                                                                                    "\n  Users: " + targetGuild.getMembers().size() +
                                                                                                    "\n  Channels: " + targetGuild.getTextChannels().size() + targetGuild.getVoiceChannels().size() +
                                                                                                    "\nRegister date: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute(), true);
        sendEmbedMessage(message, builder);
    }

    private void checkIcons(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3) {
            sendUsage(message, "!discord icons <guild id|this>");
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
        if (targetGuild.getEmotes().size() == 0) {
            sendError(message, "This server does not have any own icons!");
            return;
        }
        channel.sendMessage("**Icons**:").queue();
        targetGuild.getEmotes().forEach(icon -> channel.sendMessage(icon.getAsMention() + " - " + icon.getImageUrl()).queue());
    }
}
