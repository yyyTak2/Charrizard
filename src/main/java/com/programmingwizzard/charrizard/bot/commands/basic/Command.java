package com.programmingwizzard.charrizard.bot.commands.basic;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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

    public abstract void handle(User client, Channel channel, ChannelType type, String[] args) throws RateLimitedException;

    public String getPrefix()
    {
        return prefix;
    }
}
