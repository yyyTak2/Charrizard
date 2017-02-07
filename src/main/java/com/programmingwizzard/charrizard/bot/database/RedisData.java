package com.programmingwizzard.charrizard.bot.database;

import redis.clients.jedis.Jedis;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public interface RedisData {
    void save(Jedis jedis);
}
