package com.programmingwizzard.charrizard.bot.response.skript;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.response.ResponsesGroup;
import com.programmingwizzard.charrizard.bot.response.SingleResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class SkriptResponses extends ResponsesGroup
{
    public static String API = "https://api.skript.pl/";

    private final Executor executor;
    private final Cache<String, JsonObject> jsonObjectCache;

    public SkriptResponses()
    {
        this.executor = Executors.newCachedThreadPool();
        this.jsonObjectCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    }

    /*
     * https://wiki.skript.pl/API:Serwer
     */

    public JsonObject getMinecraftServerStatus(String ip)
    {
        if (ip == null || ip.isEmpty())
        {
            return null;
        }
        JsonObject object = jsonObjectCache.getIfPresent(ip);
        if (object == null)
        {
            SingleResponse response = new SingleResponse(this, API + "server/" + ip + "/");
            response.call(callback -> jsonObjectCache.put(ip, callback));
            object = jsonObjectCache.getIfPresent(ip);
            if (object == null)
            {
                return null;
            }
        }
        return object;
    }

    @Override
    public Executor getExecutor()
    {
        synchronized (executor)
        {
            return executor;
        }
    }
}
