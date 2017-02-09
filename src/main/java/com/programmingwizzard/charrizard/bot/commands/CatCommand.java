package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.kiciusie.KiciusieResponses;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class CatCommand extends Command {

    private final KiciusieResponses kiciusieResponses;

    public CatCommand() {
        super("cat");
        this.kiciusieResponses = new KiciusieResponses();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        kiciusieResponses.getRandomPhoto(callback -> {
            if (callback == null) {
                sendError(message, "An error occurred while connecting with api.kiciusie.pl");
                return;
            }
            String url = callback.getAsJsonObject().get("url").getAsString();
            if (url == null) {
                sendError(message, "An error occurred while connecting with api.kiciusie.pl");
                return;
            }
            url = url.replace('\'', 'i');
            EmbedBuilder builder = getEmbedBuilder()
                                           .setTitle("Charrizard")
                                           .setFooter("© 2017 Charrizard contributors", null)
                                           .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                           .setColor(new Color(0, 250, 0))
                                           .addField("Random cat", "powered by kiciusie.pl", true)
                                           .setImage(url);
            sendEmbedMessage(message, builder);
        });
    }
}
