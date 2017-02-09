package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class AuthorCommand extends Command {
    public AuthorCommand()
    {
        super("author");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        EmbedBuilder builder = getEmbedBuilder()
           .addField(
               "Authors & Informations", "Charrizard version: 1.6.2" +
                                                 "\nAuthors: https://github.com/CharrizardBot/Charrizard/contributors/" +
               "\nOfficial Discord server: https://discord.gg/jBCzCx8", true
           );
        sendEmbedMessage(message, builder);
    }
}
