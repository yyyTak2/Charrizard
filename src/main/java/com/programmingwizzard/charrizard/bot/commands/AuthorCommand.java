package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class AuthorCommand extends Command
{
    public AuthorCommand()
    {
        super("author");
    }

    @Override
    public void handle(User client, Channel channel, ChannelType type, List<User> mentionedUsers, List<Role> mentionedRoles, List<TextChannel> mentionedChannels, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        textChannel.sendMessage("**Charrizard** created by ProgrammingWizzard (http://programmingwizzard.com)").queue();
        textChannel.sendMessage("**GitHub** project: https://github.com/ProgrammingWizzard/Charrizard/").queue();
    }
}
