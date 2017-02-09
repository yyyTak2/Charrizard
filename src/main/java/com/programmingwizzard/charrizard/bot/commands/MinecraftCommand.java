package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.mojang.MojangStatusResponses;
import com.programmingwizzard.charrizard.bot.response.skript.SkriptServerResponses;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
import java.util.Map;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class MinecraftCommand extends Command {
    private final MojangStatusResponses statusResponses = new MojangStatusResponses();
    private final SkriptServerResponses serverResponses = new SkriptServerResponses();

    public MinecraftCommand() {
        super("minecraft");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        if (args.length < 2) {
            sendUsage(message, "!minecraft <status|server>");
            return;
        }
        switch (args[1]) {
            case "server":
                checkServer(message, args);
                break;
            case "status":
                checkStatus(message);
                break;
            default:
                sendUsage(message, "!minecraft <status|server>");
                break;
        }
    }

    private void checkStatus(CMessage message) {
        statusResponses.call(response -> {
            StringBuilder info = new StringBuilder();
            for (Map.Entry<String, String> entry : response.getResults().entrySet()) {
                info.append("**").append(entry.getKey()).append("**: ").append(entry.getValue()).append("\n");
            }
            EmbedBuilder builder = getEmbedBuilder()
                .setTitle("Charrizard")
                .setFooter("© 2017 Charrizard contributors", null)
                .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                .setColor(new Color(0, 250, 0))
                .setImage("https://mojang.com/assets/icons/apple-touch-icon-60x60-ee8952236b57d5ac9c40f3f0b32ca417.png")
                .addField("Mojang Status", info.toString(), true);
            sendEmbedMessage(message, builder);
        });
    }

    private void checkServer(CMessage message, String[] args) {
        if (args.length != 3 || args[2].isEmpty()) {
            sendUsage(message, "!minecraft server <ip>");
            return;
        }

        String server = args[2];
        serverResponses.call(server, response -> {
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
                .addField("Minecraft Server: " + server, info, true);
            sendEmbedMessage(message, builder);
        });
    }
}
