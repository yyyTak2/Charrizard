package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
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
        {
            list.append("!").append(command.getPrefix()).append(", \n");
        }
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField(":information_source: Commands", list.toString(), true);
        sendEmbedMessage(message, builder);
    }
}
