package com.programmingwizzard.charrizard.bot.response.netty.basic;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public abstract class AbstractHandler implements ArgHandler {

    private final int args;

    public AbstractHandler(int args) {
        this.args = args;
    }

    @Override
    public int neededArgs() {
        return args;
    }
}
