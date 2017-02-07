package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.time.OffsetDateTime;

/*
 * @author Libter
 * @date 07.02.2017
 */
public class PingCommand extends Command {

    public PingCommand()
    {
        super("ping");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        OffsetDateTime time = message.getOrigin().getCreationTime();
        long start = time.toEpochSecond() * 1000 + time.getNano() / 1000000;
        long end = System.currentTimeMillis();
        long ping = end - start;

        message.getChannel().sendMessage(String.format("%s, pong! (%d ms)", message.getAuthor().getAsMention(), ping)).queue();
    }

}
