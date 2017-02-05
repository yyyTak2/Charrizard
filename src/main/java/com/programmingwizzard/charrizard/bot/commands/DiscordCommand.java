package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.time.OffsetDateTime;
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
    public void handle(User client, Channel channel, ChannelType type, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        if (args.length == 0 || args.length == 1)
        {
            textChannel.sendMessage("**Correct usage**: !discord <user/guild/icons>").queue();
            return;
        }
        switch (args[1])
        {
            case "user":
                this.checkUser(client, textChannel, args);
                break;
            case "guild":
                this.checkGuild(client, textChannel, args);
                break;
            case "icons":
                this.checkIcons(client, textChannel, args);
                break;
            default:
                textChannel.sendMessage("**Correct usage**: !discord <user/guild/icons>").queue();
                break;
        }
    }

    private void checkUser(User client, TextChannel channel, String[] args)
    {
        if (args.length != 3)
        {
            channel.sendMessage("**Correct usage**: !discord user <user id>").queue();
            return;
        }
        User user = this.charrizard.getDiscordAPI().getUserById(args[2]);
        if (user == null)
        {
            channel.sendMessage("This user does not exists!").queue();
            return;
        }
        OffsetDateTime creationTime = user.getCreationTime();
        channel.sendMessage("**Name**: " + user.getName()).queue();
        channel.sendMessage("**Bot account**: " + BooleanUtils.parseBoolean(user.isBot())).queue();
        channel.sendMessage("**Mention tag**: " + user.getAsMention()).queue();
        channel.sendMessage("**Avatar**: " + user.getAvatarUrl()).queue();
        channel.sendMessage("**Register date**: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute()).queue();
    }

    private void checkGuild(User client, TextChannel channel, String[] args)
    {
        if (args.length != 3)
        {
            channel.sendMessage("**Correct usage**: !discord guild <guild id>").queue();
            return;
        }
        Guild guild = this.charrizard.getDiscordAPI().getGuildById(args[2]);
        if (guild == null)
        {
            channel.sendMessage("This guild does not exists!").queue();
            return;
        }
        OffsetDateTime creationTime = guild.getCreationTime();
        channel.sendMessage("**Name**: " + guild.getName()).queue();
        channel.sendMessage("**Icon**: " + guild.getIconUrl()).queue();
        channel.sendMessage("\n**Owner**:" +
                                    "\n  **Name**: " + guild.getOwner().getUser().getName() +
                                    "\n  **Mention Tag**: " + guild.getOwner().getAsMention() +
                                    "\n  **Status**: " + guild.getOwner().getOnlineStatus()).queue();
        channel.sendMessage("\n**Statistics**:" +
                                    "\n  **Users**: " + guild.getMembers().size() +
                                    "\n    **Online**: " + guild.getMembers().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.ONLINE).filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n    **Bots**: " + guild.getMembers().stream().filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n      **Online**: " + guild.getMembers().stream().filter(m -> m.getUser().isBot()).filter(m -> m.getOnlineStatus() == OnlineStatus.ONLINE).collect(Collectors.toList()).size() +
                                    "\n      **Offline**: " + guild.getMembers().stream().filter(m -> m.getUser().isBot()).filter(m -> m.getOnlineStatus() != OnlineStatus.ONLINE).collect(Collectors.toList()).size() +
                                    "\n    **Other users**: " + guild.getMembers().stream().filter(m -> m.getOnlineStatus() != OnlineStatus.ONLINE).filter(m -> !m.getUser().isBot()).collect(Collectors.toList()).size() +
                                    "\n  **Channels**" +
                                    "\n    **Text channels**: " + guild.getTextChannels().size() +
                                    "\n    **Voice channels**: " + guild.getVoiceChannels().size()).queue();
        channel.sendMessage("**Register date**: " + creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute()).queue();
    }

    private void checkIcons(User client, TextChannel channel, String[] args)
    {
        if (args.length != 3)
        {
            channel.sendMessage("**Correct usage**: !discord icons <guild id>").queue();
            return;
        }
        Guild guild = this.charrizard.getDiscordAPI().getGuildById(args[2]);
        if (guild == null)
        {
            channel.sendMessage("This guild does not exists!").queue();
            return;
        }
        channel.sendMessage("**Icons**:").queue();
        guild.getEmotes().forEach(icon -> channel.sendMessage(icon.getAsMention() + "- " + icon.getImageUrl()).queue());
    }
}
