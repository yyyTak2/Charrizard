package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class HelpCommand extends Command
{
    public HelpCommand()
    {
        super("help");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = message.getChannel();
        textChannel.sendMessage(message.getAuthor().getAsMention() + " - **Charrizard commands** (amount: 6): !help, !author, !invite, !discord, !statistics, !minecraft").queue();
    }
}
