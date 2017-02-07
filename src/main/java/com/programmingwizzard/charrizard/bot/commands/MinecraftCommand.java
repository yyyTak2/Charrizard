package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.skript.SkriptServerResponses;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class MinecraftCommand extends Command {
    private final SkriptServerResponses responses;

    public MinecraftCommand()
    {
        super("minecraft");
        this.responses = new SkriptServerResponses();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        if (args.length < 2) {
            sendUsage(message, "!minecraft <status>");
            return;
        }
        switch (args[1]) {
            case "status":
                checkStatus(message, args);
                break;
            default:
                sendUsage(message, "!minecraft <status>");
                break;
        }
    }

    private void checkStatus(CMessage message, String[] args)
    {
        if (args.length != 3 || args[2].isEmpty()) {
            sendUsage(message, "!minecraft status <ip>");
            return;
        }

        String server = args[2];
        responses.call(server, response -> {
            if (response == null) {
                sendError(message, "An error occurred while connecting with api.skript.pl");
                return;
            }

            String info;
            if (response.isOnline()) {
                String list = "";
                if (!response.getPlayersList().isEmpty()) {
                    StringBuilder listBuilder = new StringBuilder();
                    for (String player : response.getPlayersList()) {
                        listBuilder.append(", ").append(player);
                    }
                    if (listBuilder.length() > 512) {
                        list = " (`" + listBuilder.substring(2, 512) + "...`)";
                    } else {
                        list = " (`" + listBuilder.substring(2) + "`)";
                    }
                }
                info =
                    "**Online:** YES\n" +
                    String.format("**Latency:** %d ms\n", (int) response.getLatency()) +
                    String.format("**Version:** %s (Protocol #%d)\n", response.getVersion(), response.getProtocol()) +
                    String.format("**Players:** %d/%d%s\n", response.getOnlinePlayers(), response.getMaxPlayers(), list) +
                    String.format("**Description:**\n %s\n", response.getDescription()) +
                    "**Favicon:**";
            } else {
                info = "**Online:** NO\n**Favicon:**";
            }

            EmbedBuilder builder = getEmbedBuilder()
               .setTitle("Charrizard")
               .setFooter("© 2017 Charrizard contributors", null)
               .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
               .setColor(new Color(0, 250, 0))
               .setImage("https://api.skript.pl/server/" + response.getAddress() + "/icon.png")
               .addField("Minecraft Status: " + server, info, true);
            sendEmbedMessage(message, builder);
        });
    }
}
