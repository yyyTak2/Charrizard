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

    public StatisticGuild(String guildId)
    {
        this.guildId = guildId;
    }

    @Override
    public void save(Jedis jedis)
    {
    }

    public String getGuildId()
    {
        return guildId;
    }

    public Map<String, Integer> getChannelMap()
    {
        return channelMap;
    }
}
