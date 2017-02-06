package com.programmingwizzard.charrizard.bot.database;

import com.programmingwizzard.charrizard.bot.Settings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class RedisConnection
{
    private final JedisPool jedisPool;

    public RedisConnection(Settings settings)
    {
        jedisPool = new JedisPool(settings.getRedis().getIp(), settings.getRedis().getPort());
    }

    public void start()
    {
        if (!getJedis().isConnected())
        {
            getJedis().connect();
        }
        getJedis().ping();
    }

    public Jedis getJedis()
    {
        return jedisPool.getResource();
    }
}
