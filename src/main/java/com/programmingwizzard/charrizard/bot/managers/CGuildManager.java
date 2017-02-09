package com.programmingwizzard.charrizard.bot.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CGuildManager {

    private final Cache<String, CGuild> guildCache;
    private final RedisConnection redisConnection;

    public CGuildManager(RedisConnection redisConnection) {
        this.guildCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.redisConnection = redisConnection;
    }

    public void createGuild(Guild guild) {
        if (guild == null) {
            return;
        }
        if (getGuild(guild) != null) {
            return;
        }
        CGuild cGuild = new CGuild(guild, redisConnection);
        for (TextChannel channel : guild.getTextChannels()) {
            cGuild.createTextChannel(channel);
        }
        for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
            cGuild.createVoiceChannel(voiceChannel);
        }
        guildCache.put(guild.getId(), cGuild);
    }

    public CGuild getGuild(Guild guild) {
        if (guild == null) {
            return null;
        }
        return guildCache.getIfPresent(guild.getId());
    }

}
