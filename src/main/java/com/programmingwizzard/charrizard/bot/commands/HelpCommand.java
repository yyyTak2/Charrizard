package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class HelpCommand extends Command
{
    private final Charrizard charrizard;

    public HelpCommand(Charrizard charrizard)
    {
        super("help");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(User client, Channel channel, ChannelType type, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        textChannel.sendMessage(client.getAsMention() + " - look at PM!").queue();

        PrivateChannel privateChannel = client.openPrivateChannel().block();
        privateChannel.sendMessage("**Charrizard commands** (amount: 3): !help, !author, !invite").queue();
    }
}
