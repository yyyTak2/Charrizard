package com.programmingwizzard.charrizard.bot.response.kiciusie;

import com.programmingwizzard.charrizard.bot.response.Callback;
import com.programmingwizzard.charrizard.bot.response.ResponsesGroup;
import com.programmingwizzard.charrizard.bot.response.SingleResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class KiciusieResponses extends ResponsesGroup {

    public static String API = "https://api.kiciusie.pl/?type=get&mode=%s";
    private final Executor executor;

    public KiciusieResponses() {
        this.executor = Executors.newCachedThreadPool();
    }

    public void call(KiciusieMode mode, Callback<KiciusieResponse> callback) {
        SingleResponse response = new SingleResponse(this, String.format(API, mode.name().toLowerCase()));
        response.call(json -> {
            if (json == null) {
                callback.call(null);
            }
            callback.call(new KiciusieResponse(json));
        });
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

}
