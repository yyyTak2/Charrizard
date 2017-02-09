package com.programmingwizzard.charrizard.bot.database;

import com.programmingwizzard.charrizard.bot.Settings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class RedisConnection {

    private final JedisPool jedisPool;
    private final JedisPoolConfig config;

    public RedisConnection(Settings settings) {
        this.config = new JedisPoolConfig();
        config.setMaxTotal(4);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, settings.getRedis().getIp(), settings.getRedis().getPort());
    }

    public String get(String key) {
        try (Jedis j = jedisPool.getResource()) {
            return j.get(key);
        }
    }

    public String set(String key, String value) {
        try (Jedis j = jedisPool.getResource()) {
            return j.set(key, value);
        }
    }

    public void saveData(RedisData redisData) {
        try (Jedis j = jedisPool.getResource()) {
            redisData.save(j);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
