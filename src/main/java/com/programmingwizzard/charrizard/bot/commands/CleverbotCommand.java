package com.programmingwizzard.charrizard.bot.commands;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class CleverbotCommand extends Command {

    private final ChatterBotFactory factory;
    private final Cache<String, ChatterBotSession> chatterBotSessionCache;
    private ChatterBot bot;
    private final Charrizard charrizard;

    public CleverbotCommand(Charrizard charrizard) {
        super("clever", "Talk with CleverBot");
        this.charrizard = charrizard;
        this.factory = new ChatterBotFactory();
        this.chatterBotSessionCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        TextChannel channel = message.getChannel();
        if (args.length == 0 || args.length == 1) {
            sendUsage(message, charrizard.getSettings().getPrefix() + "clever <text>");
            return;
        }
        if (args[1] == null || args[1].isEmpty()) {
            sendUsage(message, charrizard.getSettings().getPrefix() + "clever <text>");
            return;
        }
        if (bot == null) {
            try {
                bot = factory.create(ChatterBotType.CLEVERBOT);
            } catch (Exception ex) {
                sendError(message, "The problem during query execution! See the console!");
                ex.printStackTrace();
                return;
            }
        }
        ChatterBotSession session = chatterBotSessionCache.getIfPresent(message.getAuthor().getId());
        if (session == null) {
            session = bot.createSession(Locale.ENGLISH);
            chatterBotSessionCache.put(message.getAuthor().getId(), session);
        }
        try {
            String s = session.think(args[1]);
            if (s == null) {
                sendError(message, "The problem during query execution!");
                return;
            }
            channel.sendMessage(message.getAuthor().getAsMention() + " - " + s).queue();
        } catch (Exception ex) {
            sendError(message, "The problem during query execution! See the console!");
            ex.printStackTrace();
        }
    }
}
