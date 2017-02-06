package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class DiscordCommand extends Command
{
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
        if (args.length == 0 || args.length == 1)
        {
            sendUsage(message, "!discord <user|guild|icons>");
            return;
        }
        switch (args[1])
        {
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
        if (args.length != 3)
        {

            return;
        }
        List<User> mentionedUsers = message.getMentionedUsers();
        User targetUser = null;
        if (mentionedUsers.size() == 0)
        {
            targetUser = this.charrizard.getDiscordAPI().getUserById(args[2]);
        }
        else
        {
            targetUser = mentionedUsers.get(0);
        }
        if (targetUser == null)
        {
            channel.sendMessage("This user does not exists!").queue();
            return;
        }
        OffsetDateTime creationTime = targetUser.getCreationTime();
        channel.sendMessage("**Name**: " + targetUser.getName()).queue();
        channel.sendMessage("**Bot account**: " + BooleanUtils.parseBoolean(targetUser.isBot())).queue();
        channel.sendMessage("**Mention tag**: " + targetUser.getAsMention()).queue();
        channel.sendMessage("**Avatar**: " + targetUser.getAvatarUrl()).queue();
        channel.sendMessage("**Register date**: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute()).queue();
    }

    private void checkGuild(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3)
        {
            sendUsage(message, "!discord guild <guild id|this>");
            return;
        }
        String guild = args[2];
        Guild targetGuild = null;
        if (guild.equals("this"))
        {
            targetGuild = message.getGuild();
        }
        else
        {
            targetGuild = this.charrizard.getDiscordAPI().getGuildById(args[2]);
        }
        if (targetGuild == null)
        {
            channel.sendMessage("This guild does not exists!").queue();
            return;
        }
        OffsetDateTime creationTime = targetGuild.getCreationTime();
        channel.sendMessage("**Name**: " + targetGuild.getName()).queue();
        channel.sendMessage("**Icon**: " + targetGuild.getIconUrl()).queue();
        channel.sendMessage("\n**Owner**:" +
                                    "\n  **Name**: " + targetGuild.getOwner().getUser().getName() +
                                    "\n  **Mention Tag**: " + targetGuild.getOwner().getAsMention() +
                                    "\n  **Status**: " + targetGuild.getOwner().getOnlineStatus()).queue();
        channel.sendMessage("\n**Statistics**:" +
                                    "\n  **Users**: " + targetGuild.getMembers().size() +
                                    "\n    **Online**: " + targetGuild.getMembers().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.ONLINE).filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n    **Bots**: " + targetGuild.getMembers().stream().filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n      **Online**: " + targetGuild.getMembers().stream().filter(m -> m.getUser().isBot()).filter(m -> m.getOnlineStatus() == OnlineStatus.ONLINE).collect(Collectors.toList()).size() +
                                    "\n      **Offline**: " + targetGuild.getMembers().stream().filter(m -> m.getUser().isBot()).filter(m -> m.getOnlineStatus() != OnlineStatus.ONLINE).collect(Collectors.toList()).size() +
                                    "\n    **Other users**: " + targetGuild.getMembers().stream().filter(m -> m.getOnlineStatus() != OnlineStatus.ONLINE).filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n  **Channels**" +
                                    "\n    **Text channels**: " + targetGuild.getTextChannels().size() +
                                    "\n    **Voice channels**: " + targetGuild.getVoiceChannels().size()).queue();
        channel.sendMessage("**Register date**: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute()).queue();
    }

    private void checkIcons(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3)
        {
            sendUsage(message, "!discord icons <guild id|this>");
            return;
        }
        String guild = args[2];
        Guild targetGuild = null;
        if (guild.equals("this"))
        {
            targetGuild = message.getGuild();
        }
        else
        {
            targetGuild = this.charrizard.getDiscordAPI().getGuildById(args[2]);
        }
        if (targetGuild == null)
        {
            channel.sendMessage("This guild does not exists!").queue();
            return;
        }
        if (targetGuild.getEmotes().size() == 0)
        {
            channel.sendMessage("This server does not have any own icons!").queue();
            return;
        }
        channel.sendMessage("**Icons**:").queue();
        targetGuild.getEmotes().forEach(icon -> channel.sendMessage(icon.getAsMention() + " - " + icon.getImageUrl()).queue());
    }
}
