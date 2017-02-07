package com.programmingwizzard.charrizard.bot.database.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

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

    public void loadGuild(Guild guild) {
        if (getStatistics(guild) != null) {
            return;
        }
        String id = guild.getId();
        StatisticGuild statisticGuild = new StatisticGuild(id, redisConnection);
        for (TextChannel channel : guild.getTextChannels()) {
            String number = redisConnection.getJedis().get("channel_" + id + "_" + channel.getId());
            if (number == null) {
                continue;
            }
            int messages = Integer.parseInt(number);
            statisticGuild.putChannel(channel, messages);
        }
        this.statisticGuildCache.put(id, statisticGuild);
    }

    public StatisticGuild getStatistics(Guild guild) {
        return statisticGuildCache.getIfPresent(guild.getId());
    }

    public Collection<StatisticGuild> getStatisticGuilds() {
        return statisticGuildCache.asMap().values();
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }
}
