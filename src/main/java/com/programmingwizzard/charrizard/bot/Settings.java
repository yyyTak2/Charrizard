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
}
