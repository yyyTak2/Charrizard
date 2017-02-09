package com.programmingwizzard.charrizard.bot.commands.basic;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashSet;
import java.util.Set;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class CommandCaller {
    private final Charrizard charrizard;
    private final Set<Command> commands = new HashSet<>();

    public CommandCaller(Charrizard charrizard)
    {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(MessageReceivedEvent event)
    {
        if (!event.getMessage().getContent().startsWith("!")) {
            return;
        }
        String[] args = event.getMessage().getContent().split(" ");
        args[0] = args[0].substring(1);
        Command command = commands.stream().filter(c -> c.getPrefix().equals(args[0])).findFirst().orElse(null);
        if (command == null) {
            return;
        }
        CGuild cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(event.getGuild());
            cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
        }
        cGuild.runCommand(command, new CMessage(event.getMessage()), args);
    }

    public Set<Command> getCommands()
    {
        return commands;
    }
}
