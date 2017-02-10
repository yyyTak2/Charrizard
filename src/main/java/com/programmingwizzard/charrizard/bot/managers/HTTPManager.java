package com.programmingwizzard.charrizard.bot.managers;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.response.netty.basic.AbstractHandler;
import com.programmingwizzard.charrizard.utils.GsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class HTTPManager {

    private final static Map<String, AbstractHandler> handlerMap = new ConcurrentHashMap<>();

    public static byte[] handleMessage(String[] args) {
        StringBuilder response = new StringBuilder();
        AbstractHandler handler = handlerMap.get(args[0].toLowerCase());
        if (handler == null) {
            JsonObject object = new JsonObject();
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            response.append(json);
        } else {
            if (handler.neededArgs() == 0) {
                handler.handleMessage(args, response);
            } else {
                if (args.length - 1 >= handler.neededArgs()) {
                    args = Arrays.copyOfRange(args, 1, args.length);
                    handler.handleMessage(args, response);
                } else {
                    JsonObject object = new JsonObject();
                    object.addProperty("error", "Not enought args!");
                    String json = GsonUtils.fromJsonElementToString(object);
                    response.append(json);
                }
            }
        }
        return response.toString().getBytes(StandardCharsets.UTF_8);
    }

}
