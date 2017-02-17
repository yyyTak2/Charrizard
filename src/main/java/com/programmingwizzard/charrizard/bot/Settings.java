package com.programmingwizzard.charrizard.bot;

import com.google.gson.annotations.SerializedName;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
@SuppressWarnings("FieldCanBeLocal")
public class Settings {

    @SerializedName("token")
    private String token = "token";

    @SerializedName("prefix")
    private String prefix = "!";

    @SerializedName("game")
    private String game = "game";

    @SerializedName("twitch")
    private boolean twitch = false;

    @SerializedName("game-url")
    private String gameUrl = "https://twitch.tv/";

    @SerializedName("redis")
    private Redis redis = new Redis();

    @SerializedName("myanimelist")
    private MyAnimeList myAnimeList = new MyAnimeList();

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getGame() {
        return game;
    }

    public boolean isTwitch() {
        return twitch;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public Redis getRedis() {
        return redis;
    }

    public MyAnimeList getMyAnimeList() {
        return myAnimeList;
    }

    public class Redis {
        @SerializedName("enabled")
        private boolean enabled = false;

        @SerializedName("ip")
        private String ip = "localhost";

        @SerializedName("port")
        private int port = 6379;

        public boolean isEnabled() {
            return enabled;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }
    }

    public class MyAnimeList {
        @SerializedName("enabled")
        private boolean enabled = false;

        @SerializedName("username")
        private String username = "jestemangozjebe";

        @SerializedName("password")
        private String password = "nekosiek123";

        public boolean isEnabled() {
            return enabled;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
