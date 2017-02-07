package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
import java.text.NumberFormat;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class StatisticsCommand extends Command
{
    private final Charrizard charrizard;
    private final Runtime runtime;
    private final NumberFormat numberFormat;

    public StatisticsCommand(Charrizard charrizard)
    {
        super("statistics");
        this.charrizard = charrizard;
        this.runtime = Runtime.getRuntime();
        this.numberFormat = NumberFormat.getInstance();
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField("Statistics", "Servers: " + charrizard.getDiscordAPI().getGuilds().size() +
                                                                                            "\nClients: " + charrizard.getDiscordAPI().getUsers().size() +
                                                                                            "\nMemory:" +
                                                                                            "\n  Free: " + numberFormat.format(runtime.freeMemory() / 1024) + " KB" +
                                                                                            "\n  Allocated: " + numberFormat.format(runtime.totalMemory() / 1024) + " KB" +
                                                                                            "\n  Max: " + numberFormat.format(runtime.maxMemory() / 1024) + " KB", true);
        sendEmbedMessage(message, builder);
    }
}
