package com.programmingwizzard.charrizard.bot.basic;

import net.dv8tion.jda.core.entities.User;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CUser {

    private final User user;
    private int reputation;

    public CUser(User user, int reputation) {
        this.user = user;
        this.reputation = reputation;
    }

    public void reset() {
        reputation = 0;
    }

    public void addReputation() {
        reputation++;
    }

    public void removeReputation() {
        reputation--;
    }

    public int getReputation() {
        return reputation;
    }
}
