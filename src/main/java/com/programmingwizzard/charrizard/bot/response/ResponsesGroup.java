package com.programmingwizzard.charrizard.bot.response;

import com.google.common.cache.Cache;
import com.google.gson.JsonObject;

import java.util.concurrent.Executor;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public abstract class ResponsesGroup
{
    public abstract Executor getExecutor();

    public abstract Cache<String, JsonObject> getJsonObjectCache();
}
