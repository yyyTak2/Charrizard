package com.programmingwizzard.charrizard.bot.database;

import com.programmingwizzard.charrizard.bot.Settings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.atomic.AtomicReference;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class RedisConnection {
    private final JedisPool jedisPool;
    private final AtomicReference<Jedis> jedisReference = new AtomicReference<>();

    public RedisConnection(Settings settings) {
        jedisPool = new JedisPool(settings.getRedis().getIp(), settings.getRedis().getPort());
    }

    public void start() {
        getJedis().ping();
    }

    public Jedis getJedis() {
        Jedis jedis = jedisReference.get();
        if (jedis == null || !jedis.isConnected()) {
            jedis = jedisPool.getResource();
            jedisReference.set(jedis);
        }
        return jedis;
    }
}
