package com.programmingwizzard.charrizard.bot.database.threads;

import com.programmingwizzard.charrizard.bot.database.managers.StatisticsGuildManager;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class StatisticsSaveThread extends Thread {

    private final StatisticsGuildManager statisticsGuildManager;

    public StatisticsSaveThread(StatisticsGuildManager statisticsGuildManager) {
        this.statisticsGuildManager = statisticsGuildManager;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            statisticsGuildManager.getStatisticGuilds().forEach(g -> g.save(statisticsGuildManager.getRedisConnection().getJedis()));
            try {
                sleep(1000 * 60);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
