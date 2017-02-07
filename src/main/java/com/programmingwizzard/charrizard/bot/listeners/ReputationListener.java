package com.programmingwizzard.charrizard.bot.listeners;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class ReputationListener {

    private final Charrizard charrizard;

    public ReputationListener(Charrizard charrizard) {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(MessageReceivedEvent event) {
        if (!event.getMessage().getContent().startsWith("+")) {
            return;
        }
        TextChannel channel = event.getTextChannel();
        StatisticGuild statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(event.getGuild());
        if (statisticGuild == null) {
            charrizard.getStatisticsGuildManager().load(event.getGuild());
            statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(event.getGuild());
        }
        List<User> mentionedUsers = event.getMessage().getMentionedUsers();
        if (mentionedUsers.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (User mentionedUser : mentionedUsers) {
            int likes;
            if (statisticGuild.getUserMap().get(mentionedUser.getId()) == null) {
                likes = 0;
            } else {
                likes = statisticGuild.getUserMap().get(mentionedUser.getId());
            }
            statisticGuild.getUserMap().put(mentionedUser.getId(), likes + 1);
            sb.append(mentionedUser.getName()).append(", ");
        }
        String users = sb.toString();
        channel.sendMessage(event.getAuthor().getAsMention() + "- You mentioned the: " + users.substring(0, sb.length() - 2)).queue();
    }

}
