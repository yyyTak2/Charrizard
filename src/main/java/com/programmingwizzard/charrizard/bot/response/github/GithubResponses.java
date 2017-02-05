package com.programmingwizzard.charrizard.bot.response.github;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.response.ResponsesGroup;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class GithubResponses extends ResponsesGroup
{
    private final Executor executor;
    private final Cache<String, JsonObject> jsonObjectCache;

    public GithubResponses()
    {
        this.executor = Executors.newCachedThreadPool();
        this.jsonObjectCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();
    }

    public JsonObject getUser(String nickname)
    {
        // TODO
        return null;
    }

    @Override
    public Executor getExecutor()
    {
        return executor;
    }

    @Override
    public Cache<String, JsonObject> getJsonObjectCache()
    {
        return jsonObjectCache;
    }
}
