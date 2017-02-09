package com.programmingwizzard.charrizard.bot.basic;

import net.dv8tion.jda.core.entities.TextChannel;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CTextChannel {

    private final TextChannel channel;
    private int messages;

    public CTextChannel(TextChannel channel, int messages) {
        this.channel = channel;
        this.messages = messages;
    }

    public void addMessage() {
        messages++;
    }

    public int getMessages() {
        return messages;
    }

    public TextChannel getOrigin() {
        return channel;
    }

    public String getName() {
        return getOrigin().getName();
    }
}
