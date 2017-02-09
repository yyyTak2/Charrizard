package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.kiciusie.KiciusieResponses;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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
        kiciusieResponses.getRandomPhoto(response -> {
            if (response == null) {
                sendError(message, "An error occurred while connecting with api.kiciusie.pl");
                return;
            }
            EmbedBuilder builder = getEmbedBuilder()
               .addField("Random cat", "powered by kiciusie.pl", true)
               .setImage(response.getImageUrl());
            sendEmbedMessage(message, builder);
        });
    }
}
