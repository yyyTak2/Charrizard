package com.programmingwizzard.charrizard.bot.basic;

import net.dv8tion.jda.core.entities.VoiceChannel;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CVoiceChannel {

    private final VoiceChannel channel;
    private int connections;

    public CVoiceChannel(VoiceChannel channel, int connections) {
        this.channel = channel;
        this.connections = connections;
    }

    public void addConnection() {
        connections++;
    }

    public int getConnections() {
        return connections;
    }

    public VoiceChannel getOrigin() {
        return channel;
    }

    public String getName() {
        return getOrigin().getName();
    }

}
