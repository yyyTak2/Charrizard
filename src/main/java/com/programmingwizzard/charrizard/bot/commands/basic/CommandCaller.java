package com.programmingwizzard.charrizard.bot.commands.basic;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.HashSet;
import java.util.Set;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class CommandCaller
{
    private final Charrizard charrizard;
    private final Set<Command> commands = new HashSet<>();

    public CommandCaller(Charrizard charrizard)
    {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(MessageReceivedEvent event)
    {
        if (!event.getMessage().getContent().startsWith("!"))
        {
            return;
        }
        String argument = event.getMessage().getContent().substring(1);
        String[] args = argument.split(" ");
        Command command = commands.stream().filter(c -> c.getPrefix().equals(argument)).findFirst().orElse(null);
        if (command == null)
        {
            return;
        }
        try
        {
            command.handle(event.getAuthor(), event.getTextChannel(), event.getChannelType(), args);
        } catch (RateLimitedException ex)
        {
            ex.printStackTrace();
            event.getTextChannel().sendMessage("Response error! Please, look at the console!").queue();
        }
    }

    public Set<Command> getCommands()
    {
        return commands;
    }
}
