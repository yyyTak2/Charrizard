package com.programmingwizzard.charrizard.bot.database.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.entities.Guild;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class StatisticsGuildManager {

    private final RedisConnection redisConnection;
    private final Cache<String, StatisticGuild> statisticGuildCache;

    public StatisticsGuildManager(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
        this.statisticGuildCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    }

    public void load(Guild guild) {

    }

    public StatisticGuild getStatistics(Guild guild) {
        return statisticGuildCache.getIfPresent(guild);
    }

    public Collection<StatisticGuild> getStatisticGuilds() {
        return statisticGuildCache.asMap().values();
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }
}
