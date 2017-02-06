package com.programmingwizzard.charrizard.bot.commands;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.Locale;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class CleverbotCommand extends Command
{
    private final ChatterBotFactory factory;
    private ChatterBot bot;
    private ChatterBotSession session;

    public CleverbotCommand()
    {
        super("clever");
        this.factory = new ChatterBotFactory();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel channel = message.getChannel();
        if (args.length == 0 || args.length == 1)
        {
            sendUsage(message, "!clever <text>");
            return;
        }
        if (args[1] == null || args[1].isEmpty())
        {
            sendUsage(message, "!clever <text>");
            return;
        }
        if (bot == null)
        {
            try
            {
                bot = factory.create(ChatterBotType.CLEVERBOT);
            } catch (Exception ex)
            {
                channel.sendMessage("The problem during query execution! See the console!").queue();
                ex.printStackTrace();
                return;
            }
        }
        if (session == null)
        {
            session = bot.createSession(Locale.ENGLISH);
        }
        try
        {
            String s = session.think(args[1]);
            channel.sendMessage(message.getAuthor().getAsMention() + " - " + s).queue();
        } catch (Exception ex)
        {
            channel.sendMessage("The problem during query execution! See the console!").queue();
            ex.printStackTrace();
        }
    }
}
