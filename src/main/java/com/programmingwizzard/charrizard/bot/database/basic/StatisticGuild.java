package com.programmingwizzard.charrizard.bot.database.basic;

import com.programmingwizzard.charrizard.bot.database.RedisData;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class StatisticGuild implements RedisData {
    private final String guildId;
    private final Map<String, Integer> channelMap = new HashMap<>();
    private final Map<String, Integer> userMap = new HashMap<>();

    public StatisticGuild(String guildId) {
        this.guildId = guildId;
    }

    @Override
    public void save(Jedis jedis) {
        for (Map.Entry<String, Integer> channelEntry : channelMap.entrySet()) {
            jedis.set("channel_" + guildId + "_" + channelEntry.getKey(), String.valueOf(channelEntry.getValue()));
        }
        for (Map.Entry<String, Integer> userEntry : userMap.entrySet()) {
            jedis.set("like_" + guildId + "_" + userEntry.getKey(), String.valueOf(userEntry.getValue()));
        }
    }

    public String getGuildId() {
        return guildId;
    }

    public Map<String, Integer> getChannelMap() {
        return channelMap;
    }

    public Map<String, Integer> getUserMap() {
        return userMap;
    }
}
