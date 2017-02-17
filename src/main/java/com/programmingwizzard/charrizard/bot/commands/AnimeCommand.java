package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.myanimelist.MyAnimeListQuery;
import com.programmingwizzard.charrizard.bot.response.myanimelist.MyAnimeListStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author gabixdev
 * @date 17.02.2017
 */
public class AnimeCommand extends Command {

    private final Charrizard charrizard;

    public AnimeCommand(Charrizard charrizard) {
        super("anime", "Search for anime on MyAnimeList.");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        MyAnimeListQuery query = new MyAnimeListQuery(charrizard);
        if (query.getStatus() != MyAnimeListStatus.SUCCESS) {
            sendError(message, query.getErrorDescription());
            return;
        }

        if (args.length < 2) {
            sendUsage(message, charrizard.getSettings().getPrefix() + "anime <search|info>");
            return;
        }

        switch (args[1]) {
            case "search":
                if (args.length < 2) {
                    sendUsage(message, charrizard.getSettings().getPrefix() + "anime search <query>");
                }
                break;
            case "info":
                if (args.length < 2) {
                    sendUsage(message, charrizard.getSettings().getPrefix() + "anime info <id>");
                }
                break;
            default:
                sendUsage(message, charrizard.getSettings().getPrefix() + "anime <search|info>");
                break;
        }
    }
}
