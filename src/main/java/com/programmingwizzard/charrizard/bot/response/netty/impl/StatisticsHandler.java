package com.programmingwizzard.charrizard.bot.response.netty.impl;

import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CTextChannel;
import com.programmingwizzard.charrizard.bot.basic.CVoiceChannel;
import com.programmingwizzard.charrizard.bot.response.netty.basic.AbstractHandler;
import com.programmingwizzard.charrizard.utils.GsonUtils;
import net.dv8tion.jda.core.entities.Guild;

import java.text.NumberFormat;

/*
 * @author ProgrammingWizzard
 * @date 10.02.2017
 */
public class StatisticsHandler extends AbstractHandler {

    private final Charrizard charrizard = Charrizard.getInstance();
    private final Runtime runtime = Runtime.getRuntime();
    private final NumberFormat numberFormat = NumberFormat.getInstance();

    public StatisticsHandler() {
        super(3);
    }

    @Override
    public void handleMessage(String[] args, StringBuilder out) {
        String argument = args[2];
        JsonObject object = new JsonObject();
        if (argument.equals("bot")) {
            object.addProperty("servers", charrizard.getDiscordAPI().getGuilds().size());
            object.addProperty("clients", charrizard.getDiscordAPI().getUsers().size());
            JsonObject memory = new JsonObject();
            memory.addProperty("free", runtime.freeMemory() / 1024);
            memory.addProperty("allocated", runtime.totalMemory() / 1024);
            memory.addProperty("max", runtime.maxMemory() / 1024);
            object.add("memory", memory);
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
        } else if (argument.equals("guild")) {
            if (args.length == 3) {
                object.addProperty("error", "Guild does not exists!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
                return;
            }
            String guild = args[3];
            if (guild == null || guild.isEmpty()) {
                object.addProperty("error", "Guild does not exists!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
                return;
            }
            Guild g = charrizard.getDiscordAPI().getGuildById(guild);
            if (g == null) {
                object.addProperty("error", "Guild does not exists!");
                String json = GsonUtils.fromJsonElementToString(object);
                out.append(json);
                return;
            }
            CGuild cGuild = charrizard.getCGuildManager().getGuild(g);
            if (cGuild == null) {
                charrizard.getCGuildManager().createGuild(g);
                cGuild = charrizard.getCGuildManager().getGuild(g);
            }
            JsonObject text = new JsonObject();
            JsonObject voice = new JsonObject();
            for (CTextChannel textChannel : cGuild.getTextChannels()) {
                text.addProperty(textChannel.getName(), textChannel.getMessages());
            }
            for (CVoiceChannel voiceChannel : cGuild.getVoiceChannels()) {
                voice.addProperty(voiceChannel.getName(), voiceChannel.getConnections());
            }
            JsonObject basic = new JsonObject();
            basic.add("text", text);
            basic.add("voice", voice);
            object.add("channels", basic);
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
        } else {
            object.addProperty("error", "Wrong argument!");
            String json = GsonUtils.fromJsonElementToString(object);
            out.append(json);
        }
    }
}
