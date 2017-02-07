package com.programmingwizzard.charrizard.bot.database.basic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.database.RedisData;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.User;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class StatisticGuild implements RedisData {
    private final String guildId;
    private final RedisConnection redisConnection;
    private final Map<String, Integer> channelMap = new HashMap<>();
    private final Cache<String, Integer> userCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public StatisticGuild(String guildId, RedisConnection redisConnection) {
        this.guildId = guildId;
        this.redisConnection = redisConnection;
    }

    @Override
    public void save(Jedis jedis) {
        for (Map.Entry<String, Integer> channelEntry : channelMap.entrySet()) {
            jedis.set("channel_" + guildId + "_" + channelEntry.getKey(), String.valueOf(channelEntry.getValue()));
        }
        for (Map.Entry<String, Integer> userEntry : userCache.asMap().entrySet()) {
            jedis.set("like_" + guildId + "_" + userEntry.getKey(), String.valueOf(userEntry.getValue()));
        }
    }

    public void loadUser(User user) {
        if (user == null) {
            return;
        }
        String string = redisConnection.getJedis().get("like_" + guildId + "_" + user.getId());
        int likes;
        if (string == null) {
            likes = 0;
        } else {
            likes = Integer.parseInt(string);
        }
        userCache.put(user.getId(), likes);
    }

    public void putChannel(Channel channel, int messages) {
        channelMap.put(channel.getId(), messages);
    }

    public void putUser(User user, int likes) {
        userCache.put(user.getId(), likes);
    }

    public String getGuildId() {
        return guildId;
    }

    public int getChannel(Channel channel) {
        if (channelMap.get(channel.getId()) == null) {
            return 0;
        }
        return channelMap.get(channel.getId());
    }

    public int getUser(User user) {
        if (userCache.getIfPresent(user.getId()) == null) {
            return 0;
        }
        return userCache.getIfPresent(user.getId());
    }

    public Set<Map.Entry<String, Integer>> getChannelEntrySet() {
        return channelMap.entrySet();
    }
}
