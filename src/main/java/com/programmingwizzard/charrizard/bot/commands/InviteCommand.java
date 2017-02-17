package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class InviteCommand extends Command {
    private final Charrizard charrizard;

    public InviteCommand(Charrizard charrizard)
    {
        super("invite", "Invite " + charrizard.getDiscordAPI().getSelfUser().getAsMention() + " to your server!");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        //TODO Untested!!!
        String clientId = charrizard.getDiscordAPI().getSelfUser().getId();
        EmbedBuilder builder = getEmbedBuilder()
           .addField("Invite URL", new StringBuilder().append("https://discordapp.com/oauth2/authorize?&client_id=").append(clientId).append("&scope=bot&permissions=8").toString(), true);
        sendEmbedMessage(message, builder);
    }
}
