package com.programmingwizzard.charrizard.bot.events;

import com.programmingwizzard.charrizard.bot.Charrizard;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class EventCaller extends ListenerAdapter {
    private final Charrizard charrizard;

    public EventCaller(Charrizard charrizard)
    {
        this.charrizard = charrizard;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        charrizard.getEventBus().post(event);
    }
}
