package com.programmingwizzard.charrizard.bot.response.netty.impl;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.response.netty.basic.AbstractHandler;
import com.programmingwizzard.charrizard.utils.GsonUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.time.OffsetDateTime;

/*
 * @author ProgrammingWizzard
 * @date 11.02.2017
 */
public class DiscordHandler extends AbstractHandler {

    private final Charrizard charrizard = Charrizard.getInstance();

    public DiscordHandler() {
        super(3);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        String argument = args[2];
        JsonObject object = new JsonObject();
        if (argument.equals("user")) {
            if (args.length == 4) {
                String userID = args[3];
                if (userID == null || userID.isEmpty()) {
                    object.addProperty("error", "userID can not be null!");
                    String json = GsonUtils.fromJsonElementToString(object);
                    out.append(json);
                    return;
                }
                User user = charrizard.getDiscordAPI().getUserById(userID);
                if (user == null) {
                    object.addProperty("error", "User does not exists!");
                    String json = GsonUtils.fromJsonElementToString(object);
                    out.append(json);
                    return;
                }
                OffsetDateTime creationTime = user.getCreationTime();
                object.addProperty("nickname", user.getName());
                object.addProperty("mention", user.getAsMention());
                object.addProperty("register-date", creationTime.getDayOfMonth() + "/" + creationTime.getMonthValue() + "/" + creationTime.getYear() + " " + creationTime.getHour() + ":" + creationTime.getMinute());
                String avatar = user.getAvatarUrl();
                if (avatar != null) {
                    object.addProperty("avatar-url", avatar);
                }
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
            } else {
                object.addProperty("error", "Wrong argument!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
            }
        } else if (argument.equals("guild")) {
            String guildID = args[3];
            if (guildID == null || guildID.isEmpty()) {
                object.addProperty("error", "guildID can not be null!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
                return;
            }
            Guild guild = this.charrizard.getDiscordAPI().getGuildById(guildID);
            if (guild == null) {
                object.addProperty("error", "Guild does not exists!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
                return;
            }
            object.addProperty("name", guild.getName());
            JsonObject owner = new JsonObject();
            owner.addProperty("name", guild.getOwner().getUser().getName());
            owner.addProperty("mention", guild.getOwner().getUser().getAsMention());
            owner.addProperty("status", String.valueOf(guild.getOwner().getOnlineStatus()));
            JsonObject statistics = new JsonObject();
            statistics.addProperty("users", guild.getMembers().size());
            JsonObject channels = new JsonObject();
            channels.addProperty("text", guild.getTextChannels().size());
            channels.addProperty("voice", guild.getVoiceChannels().size());
            statistics.add("channels", channels);
            object.add("owner", owner);
            object.add("statistics", statistics);
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
        } else {
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
        }
    }
}
