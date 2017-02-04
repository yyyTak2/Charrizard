package com.programmingwizzard.charrizard.bot.commands.basic;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class CommandCaller
{
    private final Charrizard charrizard;

    public CommandCaller(Charrizard charrizard)
    {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(Event event)
    {
        if (!(event instanceof MessageReceivedEvent))
        {
            return;
        }
        MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
        if (!messageEvent.getMessage().getContent().startsWith("!"))
        {
            return;
        }
        String[] args = messageEvent.getMessage().getContent().substring(1).split(" ");
        if (args.length == 1)
        {
            // TODO: syntax help
        }
        // TODO: command
    }
}
