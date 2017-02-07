package com.programmingwizzard.charrizard.bot.response.skript;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Libter
 * @date 07.02.2017
 */
public class SkriptServerResponse {

    private final String address;
    private final Boolean online;
    private final Float latency;
    private final Integer onlinePlayers;
    private final Integer maxPlayers;
    private final List<String> playersList;
    private final String version;
    private final Integer protocol;
    private final String description;
    private final String favicon;

    public SkriptServerResponse(JsonObject json)
    {
        address = json.get("address").getAsString();
        online = json.get("online").getAsBoolean();
        if (online) {
            latency = json.get("latency").getAsFloat();

            JsonObject playersJson = json.getAsJsonObject("players");
            onlinePlayers = playersJson.get("online").getAsInt();
            maxPlayers = playersJson.get("max").getAsInt();

            playersList = new ArrayList<>();
            JsonArray playersListJson = playersJson.getAsJsonArray("list");
            for (JsonElement player : playersListJson)
            {
                playersList.add(player.getAsString());
            }

            JsonObject versionJson = json.getAsJsonObject("version");
            version = versionJson.get("name").getAsString();
            protocol = versionJson.get("protocol").getAsInt();

            description = json.get("description").getAsString();
            favicon = json.get("favicon").getAsString();
        } else {
            latency = null;
            onlinePlayers = null;
            maxPlayers = null;
            playersList = null;
            version = null;
            protocol = null;
            description = null;
            favicon = null;
        }
    }

    public String getAddress()
    {
        return address;
    }

    public boolean isOnline()
    {
        return online;
    }

    public float getLatency()
    {
        return latency;
    }

    public int getOnlinePlayers()
    {
        return onlinePlayers;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public List<String> getPlayersList()
    {
        return playersList;
    }

    public String getVersion()
    {
        return version;
    }

    public int getProtocol()
    {
        return protocol;
    }

    public String getDescription()
    {
        return description;
    }

    public String getFavicon()
    {
        return favicon;
    }
}
