package com.programmingwizzard.charrizard.bot.commands.basic;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public abstract class Command
{
    private final String prefix;

    public Command(String prefix)
    {
        this.prefix = prefix;
    }

    public abstract void handle(User client, Channel channel, ChannelType type, List<User> mentionedUsers, List<Role> mentionedRoles, List<TextChannel> mentionedChannels, String[] args) throws RateLimitedException;

    public final String getPrefix()
    {
        return prefix;
    }
}
