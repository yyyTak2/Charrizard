package com.programmingwizzard.charrizard.bot.response.skript;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.response.Callback;
import com.programmingwizzard.charrizard.bot.response.ResponsesGroup;
import com.programmingwizzard.charrizard.bot.response.SingleResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @author Libter
 * @date 07.02.2017
 */
public class SkriptServerResponses extends ResponsesGroup {
    private static String URL = "https://api.skript.pl/server/%s/";

    private final Executor executor;
    private final Cache<String, SkriptServerResponse> cache;

    public SkriptServerResponses() {
        this.executor = Executors.newCachedThreadPool();
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    }

    public void call(String rawIp, Callback<SkriptServerResponse> callback) {
        String ip = rawIp.toLowerCase();
        SkriptServerResponse result = cache.getIfPresent(ip);
        if (result == null) {
            SingleResponse response = new SingleResponse(this, String.format(URL, ip));
            response.call(json -> {
                SkriptServerResponse newResult = new SkriptServerResponse(json);
                callback.call(newResult);
                cache.put(rawIp, newResult);
            });
        } else {
            callback.call(result);
        }
    }

    @Override
    public Executor getExecutor() {
        synchronized (executor) {
            return executor;
        }
    }
}
