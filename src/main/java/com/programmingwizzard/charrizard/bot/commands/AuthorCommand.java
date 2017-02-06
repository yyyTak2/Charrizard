package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = message.getChannel();
        textChannel.sendMessage("**Charrizard** created by ProgrammingWizzard (http://programmingwizzard.com)").queue();
        textChannel.sendMessage("**GitHub** project: https://github.com/ProgrammingWizzard/Charrizard/").queue();
    }
}
