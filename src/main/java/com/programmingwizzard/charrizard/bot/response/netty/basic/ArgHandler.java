package com.programmingwizzard.charrizard.bot.response.netty.basic;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public interface ArgHandler {

    int neededArgs();

    void handleMessage(String[] args, StringBuilder out);

}
