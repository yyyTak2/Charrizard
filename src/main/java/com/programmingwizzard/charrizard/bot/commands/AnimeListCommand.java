package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author gabixdev
 * @date 17.02.2017
 */
public class AnimeListCommand extends Command {

    private final Charrizard charrizard;

    public AnimeListCommand(Charrizard charrizard) {
        super("animelist");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        if (args.length < 2) {
            sendUsage(message, charrizard.getSettings().getPrefix() + "animelist <search|info>");
            return;
        }
        switch (args[1]) {
            case "search":
                break;
            case "info":
                break;
            default:
                sendUsage(message, charrizard.getSettings().getPrefix() + "animelist <search|info>");
                break;
        }
    }
}
