package com.programmingwizzard.charrizard.bot.response.netty.impl;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CUser;
import com.programmingwizzard.charrizard.bot.response.netty.basic.AbstractHandler;
import com.programmingwizzard.charrizard.utils.GsonUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class ReputationHandler extends AbstractHandler {

    private final Charrizard charrizard = Charrizard.getInstance();

    public ReputationHandler() {
        super(4);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        String guildID = args[2];
        String userID = args[3];
        JsonObject object = new JsonObject();
        if (guildID == null || userID == null || guildID.isEmpty() || userID.isEmpty()) {
            object.addProperty("error", "guildID and userID can not be null!");
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
            return;
        }
        Guild guild = charrizard.getDiscordAPI().getGuildById(guildID);
        if (guild == null) {
            object.addProperty("error", "Guild does not exists!");
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
        CGuild cGuild = charrizard.getCGuildManager().getGuild(guild);
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(guild);
            cGuild = charrizard.getCGuildManager().getGuild(guild);
        }
        CUser cUser = cGuild.getUser(user);
        if (cUser == null) {
            cGuild.createUser(user);
            cUser = cGuild.getUser(user);
        }
        object.addProperty("reputation", cUser.getReputation());
        String json = GsonUtils.fromJsonElementToString(object);
        out.append(json);
    }
}
