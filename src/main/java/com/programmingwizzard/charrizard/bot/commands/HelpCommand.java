package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.Set;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class HelpCommand extends Command
{
    private final Charrizard charrizard;

    public HelpCommand(Charrizard charrizard)
    {
        super("help");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel channel = message.getChannel();
        Set<Command> commands = charrizard.getCommandCaller().getCommands();

        StringBuilder list = new StringBuilder();
        for (Command command : commands)
            list.append("!").append(command.getPrefix()).append(", ");

        String result = String.format(" - **Charrizard commands** (amount: %d): %s", commands.size(), list.substring(0, list.length() - 2));
        channel.sendMessage(message.getAuthor().getAsMention() + result).queue();
    }
}
