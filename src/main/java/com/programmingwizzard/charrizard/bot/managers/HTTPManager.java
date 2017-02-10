package com.programmingwizzard.charrizard.bot.managers;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.response.netty.basic.AbstractHandler;
import com.programmingwizzard.charrizard.bot.response.netty.impl.ReputationHandler;
import com.programmingwizzard.charrizard.bot.response.netty.impl.StatisticsHandler;
import com.programmingwizzard.charrizard.utils.GsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class HTTPManager {

    private final static Map<String, AbstractHandler> handlerMap = new ConcurrentHashMap<>();

    static {
        handlerMap.put("reputation", new ReputationHandler());
        handlerMap.put("statistics", new StatisticsHandler());
    }

    public static byte[] handleMessage(String[] args) {
        StringBuilder response = new StringBuilder();
        if (args.length == 1) {
            JsonObject object = new JsonObject();
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            response.append(json);
            return response.toString().getBytes(StandardCharsets.UTF_8);
        }
        String argument = args[1];
        if (argument == null) {
            JsonObject object = new JsonObject();
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            response.append(json);
        }
        AbstractHandler handler = handlerMap.get(argument);
        if (handler == null) {
            JsonObject object = new JsonObject();
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            response.append(json);
        } else {
            if (handler.neededArgs() == 2) {
                handler.handleMessage(args, response);
            } else {
                if (args.length >= handler.neededArgs()) {
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
