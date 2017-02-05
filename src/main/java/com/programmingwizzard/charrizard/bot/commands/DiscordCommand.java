package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.time.OffsetDateTime;

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
            textChannel.sendMessage("**Correct usage**: !discord <user>").queue();
            return;
        }
        switch (args[1])
        {
            case "user":
                this.checkUser(client, textChannel, args);
                break;
            default:
                textChannel.sendMessage("**Correct usage**: !discord <user>").queue();
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
}
