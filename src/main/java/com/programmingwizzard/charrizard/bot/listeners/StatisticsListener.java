package com.programmingwizzard.charrizard.bot.listeners;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class StatisticsListener {

    private final Charrizard charrizard;

    public StatisticsListener(Charrizard charrizard) {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("!")) {
            return;
        }
        TextChannel channel = event.getTextChannel();
        StatisticGuild statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(event.getGuild());
        if (statisticGuild == null) {
            charrizard.getStatisticsGuildManager().load(event.getGuild());
            statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(event.getGuild());
        }
        int messages;
        if (statisticGuild.getChannelMap().get(channel.getId()) == null) {
            messages = 0;
        } else {
            messages = statisticGuild.getChannelMap().get(channel.getId());
        }
        statisticGuild.getChannelMap().put(channel.getId(), messages + 1);
    }

}
