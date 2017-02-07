package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;

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
           .setTitle("Charrizard")
           .setFooter("© 2017 Charrizard contributors", null)
           .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
           .setColor(new Color(0, 250, 0))
           .addField(
               "Authors & Informations", "Charrizard version: 1.5" +
               "\nAuthors: https://github.com/ProgrammingWizzard/Charrizard/contributors/" +
               "\nOfficial Discord server: https://discord.gg/jBCzCx8", true
           );
        sendEmbedMessage(message, builder);
    }
}
