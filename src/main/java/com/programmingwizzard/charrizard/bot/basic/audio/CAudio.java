package com.programmingwizzard.charrizard.bot.basic.audio;

import com.programmingwizzard.charrizard.bot.basic.CGuild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.PriorityQueue;
import java.util.Queue;

/*
 * @author ProgrammingWizzard
 * @date 17.02.2017
 */
public class CAudio {

    private final CGuild guild;
    private final Queue<CAudioQueueElement> elements = new PriorityQueue<>();

    public CAudio(CGuild guild) {
        this.guild = guild;
    }

    public void addElement(CAudioQueueElement element) {
        elements.add(element);
    }

    public void open(VoiceChannel channel) {
        if (channel == null) {
            return;
        }
        AudioManager manager = guild.getAudioManager();
        if (manager.isConnected()) {
            return;
        }
        manager.openAudioConnection(channel);
        manager.setAutoReconnect(true);
        manager.setSelfMuted(true);
    }

    public void close() {
        AudioManager manager = guild.getAudioManager();
        if (!manager.isConnected()) {
            return;
        }
        manager.closeAudioConnection();
    }

    public boolean isConnected() {
        AudioManager manager = guild.getAudioManager();
        return manager.isConnected();
    }

}
