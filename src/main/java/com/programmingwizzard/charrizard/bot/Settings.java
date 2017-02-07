package com.programmingwizzard.charrizard.bot;

import com.google.gson.annotations.SerializedName;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
@SuppressWarnings("FieldCanBeLocal")
public class Settings {
    @SerializedName("token") private String token = "token";

    @SerializedName("game") private String game = "game";

    @SerializedName("game-url") private String gameUrl = "http://programmingwizzard.com";

    @SerializedName("redis") private Redis redis = new Redis();

    public String getToken()
    {
        return token;
    }

    public String getGame()
    {
        return game;
    }

    public String getGameUrl()
    {
        return gameUrl;
    }

    public Redis getRedis()
    {
        return redis;
    }

    public class Redis {
        @SerializedName("ip") private String ip;

        @SerializedName("port") private int port = 1234;

        public String getIp()
        {
            return ip;
        }

        public int getPort()
        {
            return port;
        }
    }
}
