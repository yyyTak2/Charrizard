package com.programmingwizzard.charrizard.bot.basic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.Settings;
import com.programmingwizzard.charrizard.bot.basic.audio.CAudio;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.database.RedisData;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.AudioManager;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CGuild implements RedisData {

    private final Guild guild;
    private final Charrizard charrizard;
    private final Settings settings;
    private final RedisConnection redisConnection;
    private final Executor executor;
    private final Cache<String, CUser> userCache;
    private final Cache<String, CTextChannel> textChannelCache;
    private final Cache<String, CVoiceChannel> voiceChannelCache;
    private final CAudio audio;

    public CGuild(Guild guild, Charrizard charrizard) {
        this.guild = guild;
        this.charrizard = charrizard;
        this.settings = charrizard.getSettings();
        this.redisConnection = charrizard.getRedisConnection();
        this.executor = new ThreadPoolExecutor(2, 16, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.userCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.textChannelCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.voiceChannelCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.audio = new CAudio(this);
    }

    @Override
    public void save(Jedis jedis) {
        for (Map.Entry<String, CUser> userEntry : userCache.asMap().entrySet()) {
            jedis.set("like_" + getGuildId() + "_" + userEntry.getKey(), String.valueOf(userEntry.getValue().getReputation()));
        }
        for (Map.Entry<String, CTextChannel> textChannelEntry : textChannelCache.asMap().entrySet()) {
            jedis.set("channel_" + getGuildId() + "_" + textChannelEntry.getKey(), String.valueOf(textChannelEntry.getValue().getMessages()));
        }
        for (Map.Entry<String, CVoiceChannel> voiceChannelEntry : voiceChannelCache.asMap().entrySet()) {
            jedis.set("voice_" + getGuildId() + "_" + voiceChannelEntry.getKey(), String.valueOf(voiceChannelEntry.getValue().getConnections()));
        }
    }

    public void runCommand(Command command, CMessage message, String[] args) {
        if (command == null) {
            return;
        }
        runAsync(() -> {
            try {
                command.handle(message, args);
            } catch (RateLimitedException ex) {
                ex.printStackTrace();
                message.getOrigin().getTextChannel().sendMessage("Response error! Please, look at the console!").queue();
            }
        });
    }

    public void runAsync(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.execute(runnable);
    }

    public void createUser(User user) {
        if (user == null) {
            return;
        }
        if (getUser(user) != null) {
            return;
        }
        String string = null;
        if (settings.getRedis().isEnabled()) {
            string = redisConnection.get("like_" + getGuildId() + "_" + user.getId());
        }
        int reputation;
        if (string == null) {
            reputation = 0;
        } else {
            reputation = Integer.parseInt(string);
        }
        CUser cUser = new CUser(user, reputation);
        userCache.put(user.getId(), cUser);
    }

    public void createTextChannel(TextChannel channel) {
        if (channel == null) {
            return;
        }
        if (getTextChannel(channel) != null) {
            return;
        }
        String string = null;
        if (settings.getRedis().isEnabled()) {
            string = redisConnection.get("channel_" + getGuildId() + "_" + channel.getId());
        }
        int messages;
        if (string == null) {
            messages = 0;
        } else {
            messages = Integer.parseInt(string);
        }
        CTextChannel cTextChannel = new CTextChannel(channel, messages);
        textChannelCache.put(channel.getId(), cTextChannel);
    }

    public void createVoiceChannel(VoiceChannel channel) {
        if (channel == null) {
            return;
        }
        if (getVoiceChannel(channel) != null) {
            return;
        }
        String string = null;
        if (settings.getRedis().isEnabled()) {
            string = redisConnection.get("voice_" + getGuildId() + "_" + channel.getId());
        }
        int connections;
        if (string == null) {
            connections = 0;
        } else {
            connections = Integer.parseInt(string);
        }
        CVoiceChannel cVoiceChannel = new CVoiceChannel(channel, connections);
        voiceChannelCache.put(channel.getId(), cVoiceChannel);
    }

    public Guild getOrigin() {
        return guild;
    }

    public String getGuildId() {
        return getOrigin().getId();
    }

    public CUser getUser(User user) {
        if (user == null) {
            return null;
        }
        return userCache.getIfPresent(user.getId());
    }

    public CTextChannel getTextChannel(TextChannel channel) {
        if (channel == null) {
            return null;
        }
        return textChannelCache.getIfPresent(channel.getId());
    }

    public CVoiceChannel getVoiceChannel(VoiceChannel channel) {
        if (channel == null) {
            return null;
        }
        return voiceChannelCache.getIfPresent(channel.getId());
    }

    public Collection<CTextChannel> getTextChannels() {
        return textChannelCache.asMap().values();
    }

    public Collection<CVoiceChannel> getVoiceChannels() {
        return voiceChannelCache.asMap().values();
    }

    public AudioManager getAudioManager() {
        return guild.getAudioManager();
    }

    public CAudio getAudio() {
        return audio;
    }
}
