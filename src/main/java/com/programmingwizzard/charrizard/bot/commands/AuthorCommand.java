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
        textChannel.sendMessage("**Charrizard** is creating by all those involved in the repository bot - https://github.com/ProgrammingWizzard/Charrizard/").queue();
        textChannel.sendMessage("Official **Discord server** - https://discord.gg/jBCzCx8").queue();
    }
}
