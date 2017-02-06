package com.programmingwizzard.charrizard.bot.commands.basic;

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

    public abstract void handle(CMessage message, String[] args) throws RateLimitedException;

    public final String getPrefix()
    {
        return prefix;
    }
}
