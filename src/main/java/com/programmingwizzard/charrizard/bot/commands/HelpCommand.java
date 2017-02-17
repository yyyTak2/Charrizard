package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.Set;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class HelpCommand extends Command {
    private final Charrizard charrizard;

    public HelpCommand(Charrizard charrizard)
    {
        super("help");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        Set<Command> commands = charrizard.getCommandCaller().getCommands();
        String prefix = charrizard.getSettings().getPrefix();
        StringBuilder list = new StringBuilder();
        for (Command command : commands) {
            list.append(prefix).append(command.getPrefix()).append(", ");
        }
        String s = list.toString();
        EmbedBuilder builder = getEmbedBuilder()
           .addField("Commands", s.substring(0, s.length() - 2), true);
        sendEmbedMessage(message, builder);
    }
}
