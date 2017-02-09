package com.programmingwizzard.charrizard.bot.response.mojang;

import com.programmingwizzard.charrizard.bot.response.Callback;
import com.programmingwizzard.charrizard.bot.response.ResponsesGroup;
import com.programmingwizzard.charrizard.bot.response.SingleResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * @author Libter
 * @date 09.02.2017
 */
public class MojangStatusResponses extends ResponsesGroup {

    private static String URL = "https://status.mojang.com/check";

    private final Executor executor;

    public MojangStatusResponses() {
        this.executor = Executors.newCachedThreadPool();
    }

    public void call(Callback<MojangStatusResponse> callback) {
        SingleResponse response = new SingleResponse(this, URL);
        response.call(json -> {
            MojangStatusResponse result = new MojangStatusResponse(json);
            callback.call(result);
        });
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

}
