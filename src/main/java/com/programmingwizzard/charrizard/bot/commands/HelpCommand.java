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

    public HelpCommand(Charrizard charrizard) {
        super("help", "Prints all bot commands.");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        Set<Command> commands = charrizard.getCommandCaller().getCommands();
        String prefix = charrizard.getSettings().getPrefix();
        StringBuilder labels = new StringBuilder();
        StringBuilder descs = new StringBuilder();
        for (Command command : commands) {
            labels.append(prefix).append(command.getLabel()).append("\n");
            descs.append(command.getDescription()).append("\n");
        }
        String ls = labels.toString();
        String ds = descs.toString();
        EmbedBuilder builder = getEmbedBuilder()
                .addField("Command", ls.substring(0, ls.length() - 2), true)
                .addField("Description", ds.substring(0, ds.length() - 2), true);
        sendEmbedMessage(message, builder);
    }
}
