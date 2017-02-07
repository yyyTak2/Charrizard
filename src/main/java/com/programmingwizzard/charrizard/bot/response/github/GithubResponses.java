package com.programmingwizzard.charrizard.bot.response.github;

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
 * @date 05.02.2017
 */
public class GithubResponses extends ResponsesGroup {
    public static String API = "https://api.github.com";

    private final Executor executor;
    private final Cache<String, JsonObject> jsonObjectCache;

    public GithubResponses()
    {
        this.executor = Executors.newCachedThreadPool();
        this.jsonObjectCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();
        this.getAPI();
    }

    public JsonObject getUser(String nickname)
    {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }
        JsonObject object = jsonObjectCache.getIfPresent("user_" + nickname);
        if (object == null) {
            JsonObject api = this.getAPI();
            if (api == null) {
                return null;
            }
            String userUrl = api.getAsJsonPrimitive("user_url").getAsString();
            if (userUrl == null) {
                return null;
            }
            userUrl = userUrl.replaceAll("\\{user\\}", nickname);
            SingleResponse response = new SingleResponse(this, userUrl);
            response.call(callback -> jsonObjectCache.put("user_" + nickname, callback));
            object = jsonObjectCache.getIfPresent("user_" + nickname);
            if (object == null) {
                return null;
            }
        }
        return object;
    }

    public JsonObject getAPI()
    {
        JsonObject object = jsonObjectCache.getIfPresent("api");
        if (object == null) {
            SingleResponse response = new SingleResponse(this, API);
            response.call(callback -> jsonObjectCache.put("api", callback));
            object = jsonObjectCache.getIfPresent("api");
            if (object == null) {
                return null;
            }
        }
        return object;
    }

    @Override
    public Executor getExecutor()
    {
        synchronized (executor) {
            return executor;
        }
    }
}
