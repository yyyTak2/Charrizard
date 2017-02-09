package com.programmingwizzard.charrizard.bot.basic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.database.RedisData;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import redis.clients.jedis.Jedis;

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
    private final Executor executor;
    private final Cache<String, CUser> userCache;
    private final Cache<String, CTextChannel> textChannelCache;
    private final Cache<String, CVoiceChannel> voiceChannelCache;

    public CGuild(Guild guild) {
        this.guild = guild;
        this.executor = new ThreadPoolExecutor(2, 16, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.userCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.textChannelCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
        this.voiceChannelCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
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
        // TODO: download reputation
        CUser cUser = new CUser(user, 0);
        userCache.put(user.getId(), cUser);
    }

    public void createTextChannel(TextChannel channel) {
        if (channel == null) {
            return;
        }
        if (getTextChannel(channel) != null) {
            return;
        }
        CTextChannel cTextChannel = new CTextChannel(channel, 0);
        textChannelCache.put(channel.getId(), cTextChannel);
    }

    public void createVoiceChannel(VoiceChannel channel) {
        if (channel == null) {
            return;
        }
        if (getVoiceChannel(channel) != null) {
            return;
        }
        CVoiceChannel cVoiceChannel = new CVoiceChannel(channel, 0);
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

}
