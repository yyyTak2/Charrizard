package com.programmingwizzard.charrizard.bot.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.skript.SkriptResponses;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class MinecraftCommand extends Command
{
    private final SkriptResponses skriptResponses;

    public MinecraftCommand()
    {
        super("minecraft");
        this.skriptResponses = new SkriptResponses();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel channel = message.getChannel();
        if (args.length == 0 || args.length == 1)
        {
            sendUsage(message, "!minecraft <status>");
            return;
        }
        switch (args[1])
        {
            case "status":
                this.checkStatus(message, args);
                break;
            default:
                sendUsage(message, "!minecraft <status>");
                break;
        }
    }

    private void checkStatus(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3)
        {
            sendUsage(message, "!minecraft status <ip>");
            return;
        }
        String server = args[2];
        if (server == null || server.isEmpty())
        {
            sendUsage(message, "!minecraft status <ip>");
            return;
        }
        JsonObject object = skriptResponses.getMinecraftServerStatus(server);
        if (object == null)
        {
            sendError(message, "An error occurred in conjunction with https://api.skript.pl");
            return;
        }
        JsonObject players = object.getAsJsonObject("players");
        if (players == null)
        {
            sendError(message, "This server is turned off!");
            return;
        }
        String list = "";
        for (JsonElement element : players.getAsJsonArray("list"))
        {
            list = list + ", " + element.getAsString() + "";
        }
        list = list.substring(1);
        list = list.substring(1);
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField(":information_source: " + server,
                                                        "Online: " + BooleanUtils.parseBoolean(object.getAsJsonPrimitive("online").getAsBoolean()) +
                                                       "\nPlayers (" + players.getAsJsonPrimitive("online").getAsInt() + "/" + players.getAsJsonPrimitive("max").getAsInt() + "): " + list, true);
        sendEmbedMessage(message, builder);
    }
}
