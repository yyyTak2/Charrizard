package com.programmingwizzard.charrizard.bot.response;

import com.google.gson.JsonElement;
import com.programmingwizzard.charrizard.utils.GsonUtils;
import com.programmingwizzard.charrizard.utils.URLUtils;

import java.io.IOException;

/*
 * @author Libter
 * @date 11.02.2017
 */
public class Response {

    private Response() { }

    public static JsonElement getJson(String url) {
        try {
            return GsonUtils.fromStringToJsonElement(URLUtils.readUrl(url));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
