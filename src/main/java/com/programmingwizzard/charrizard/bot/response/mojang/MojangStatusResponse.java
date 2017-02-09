package com.programmingwizzard.charrizard.bot.response.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * @author Libter
 * @date 09.02.2017
 */
public class MojangStatusResponse {

    private Map<String, String> results = new LinkedHashMap<>();

    public MojangStatusResponse(JsonObject json) {
        JsonArray array = json.getAsJsonArray();
        for (JsonElement element : array) {
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                results.put(entry.getKey(), entry.getValue().getAsString());
            }
        }
    }

    public Map<String, String> getResults() {
        return results;
    }

}
