package com.programmingwizzard.charrizard.bot.listeners;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CVoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class VoiceListener {

    private final Charrizard charrizard;

    public VoiceListener(Charrizard charrizard) {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onVoiceJoin(GuildVoiceJoinEvent event) {
        CGuild cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(event.getGuild());
            cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
        }
        CVoiceChannel cVoiceChannel = cGuild.getVoiceChannel(event.getChannelJoined());
        if (cVoiceChannel == null) {
            cGuild.createVoiceChannel(event.getChannelJoined());
            cVoiceChannel = cGuild.getVoiceChannel(event.getChannelJoined());
        }
        cVoiceChannel.addConnection();
    }

}
