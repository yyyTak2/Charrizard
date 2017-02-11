package com.programmingwizzard.charrizard.bot.response.mojang;

import com.programmingwizzard.charrizard.bot.response.Response;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * @author Libter
 * @date 09.02.2017
 */
public class MojangStatusResponses {

    private static String URL = "https://status.mojang.com/check";

    private final Executor executor;

    public MojangStatusResponses() {
        this.executor = Executors.newCachedThreadPool();
    }

    public MojangStatusResponse call() {
        return new MojangStatusResponse(Response.getJson(URL));
    }

}
