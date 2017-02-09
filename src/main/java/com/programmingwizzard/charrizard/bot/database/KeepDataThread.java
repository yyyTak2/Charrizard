package com.programmingwizzard.charrizard.bot.database;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class KeepDataThread extends Thread {

    private final Charrizard charrizard;

    public KeepDataThread(Charrizard charrizard) {
        this.charrizard = charrizard;
    }

    @Override
    public void run() {
        while (true) {
            for (CGuild guild : charrizard.getCGuildManager().getGuilds()) {
                charrizard.getRedisConnection().saveData(guild);
            }
            try {
                wait(1000 * 60);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
