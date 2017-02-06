package com.programmingwizzard.charrizard.bot.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.skript.SkriptResponses;
import com.programmingwizzard.charrizard.utils.BooleanUtils;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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
            channel.sendMessage("**Correct usage**: !minecraft <status>").queue();
            return;
        }
        switch (args[1])
        {
            case "status":
                this.checkStatus(message, args);
                break;
            default:
                channel.sendMessage("**Correct usage**: !minecraft <status>").queue();
                break;
        }
    }

    private void checkStatus(CMessage message, String[] args)
    {
        TextChannel channel = message.getChannel();
        if (args.length != 3)
        {
            channel.sendMessage("**Correct usage**: !minecraft status <ip>").queue();
            return;
        }
        String server = args[2];
        if (server == null || server.isEmpty())
        {
            channel.sendMessage("**Correct usage**: !minecraft status <ip>").queue();
            return;
        }
        JsonObject object = skriptResponses.getMinecraftServerStatus(server);
        if (object == null)
        {
            channel.sendMessage("An error occurred in conjunction with https://api.skript.pl").queue();
            return;
        }
        channel.sendMessage("**IP**: " + server).queue();
        channel.sendMessage("**Online**: " + BooleanUtils.parseBoolean(object.getAsJsonPrimitive("online").getAsBoolean())).queue();
        if (!object.getAsJsonPrimitive("online").getAsBoolean())
        {
            return;
        }
        JsonObject players = object.getAsJsonObject("players");
        String list = "";
        for (JsonElement element : players.getAsJsonArray("list"))
        {
            list = list + ", " + element.getAsString() + "";
        }
        list = list.substring(1);
        list = list.substring(1);
        channel.sendMessage("**Players** (" + players.getAsJsonPrimitive("online").getAsInt() + "/" + players.getAsJsonPrimitive("max").getAsInt() + "): " + list).queue();
    }
}
