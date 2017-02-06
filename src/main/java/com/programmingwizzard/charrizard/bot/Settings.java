package com.programmingwizzard.charrizard.bot;

import com.google.gson.annotations.SerializedName;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
@SuppressWarnings("FieldCanBeLocal")
public class Settings
{
    @SerializedName("token")
    private String token = "token";

    @SerializedName("game")
    private String game = "game";

    @SerializedName("game-url")
    private String gameUrl = "http://programmingwizzard.com";

    @SerializedName("mongo")
    private MongoSettings mongoSettings = new MongoSettings();

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

    public MongoSettings getMongoSettings()
    {
        return mongoSettings;
    }

    public class MongoSettings
    {
        @SerializedName("ip")
        private String ip = "localhost";

        @SerializedName("port")
        private int port = 27017;

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
