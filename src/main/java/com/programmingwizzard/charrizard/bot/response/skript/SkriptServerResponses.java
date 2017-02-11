package com.programmingwizzard.charrizard.bot.response.skript;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.response.Response;

import java.util.concurrent.TimeUnit;

/*
 * @author Libter
 * @date 07.02.2017
 */
public class SkriptServerResponses {

    private static String URL = "https://api.skript.pl/server/%s/";

    private final Cache<String, SkriptServerResponse> cache;

    public SkriptServerResponses() {
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    }

    public SkriptServerResponse call(String rawIp) {
        String ip = rawIp.toLowerCase();
        SkriptServerResponse result = cache.getIfPresent(ip);
        if (result == null) {
            SkriptServerResponse newResult = new SkriptServerResponse(Response.getJson(String.format(URL, ip)));
            cache.put(ip, newResult);
            return newResult;
        } else {
            return result;
        }
    }

}
