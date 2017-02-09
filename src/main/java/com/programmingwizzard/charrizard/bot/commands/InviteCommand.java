package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class InviteCommand extends Command {
    public InviteCommand()
    {
        super("invite");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        EmbedBuilder builder = getEmbedBuilder()
           .addField("Invite URL", "https://discordapp.com/oauth2/authorize?&client_id=277567355360509962&scope=bot&permissions=8", true);
        sendEmbedMessage(message, builder);
    }
}
