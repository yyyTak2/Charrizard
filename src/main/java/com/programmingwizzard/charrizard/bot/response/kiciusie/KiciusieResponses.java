package com.programmingwizzard.charrizard.bot.response.kiciusie;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

    public static String API = "https://api.kiciusie.pl/";
    private final Executor executor;

    public KiciusieResponses() {
        this.executor = Executors.newCachedThreadPool();
    }

    /*
     * https://api.kiciusie.pl/index.php?type=get&mode=random
     */

    public void getRandomPhoto(Callback<JsonElement> callback) {
        SingleResponse response = new SingleResponse(this, API + "index.php?type=get&mode=random");
        response.call(json -> {
            if (json == null) {
                callback.call(null);
            }
            callback.call(json);
        });
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }
}
