package com.programmingwizzard.charrizard.bot.response;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.utils.GsonUtils;
import com.programmingwizzard.charrizard.utils.URLUtils;

import java.io.IOException;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class SingleResponse {
    private final ResponsesGroup responsesGroup;
    private final String url;

    public SingleResponse(ResponsesGroup responsesGroup, String url) {
        this.responsesGroup = responsesGroup;
        this.url = url;
    }

    public void call(Callback<JsonObject> callback) {
        if (callback == null) {
            return;
        }
        responsesGroup.getExecutor().execute(() -> {
            try {
                JsonObject object = GsonUtils.fromStringToJsonObject(URLUtils.readUrl(url));
                callback.call(object);
            } catch (IOException ex) {
                callback.call(null);
                ex.printStackTrace();
            }
        });
    }
}
