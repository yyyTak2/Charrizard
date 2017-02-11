package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.kiciusie.KiciusieMode;
import com.programmingwizzard.charrizard.bot.response.kiciusie.KiciusieResponse;
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
        if (args.length != 2) {
            sendUsage(message, "!cat <random|image|gif>");
            return;
        }
        KiciusieMode mode;
        try {
            mode = KiciusieMode.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException iae) {
            sendUsage(message, "!cat <random|image|gif>");
            return;
        }

        KiciusieResponse response = kiciusieResponses.call(mode);
        EmbedBuilder builder = getEmbedBuilder()
                .addField("Random cat", "powered by kiciusie.pl", true)
                .setImage(response.getImageUrl());
        sendEmbedMessage(message, builder);
    }
}
