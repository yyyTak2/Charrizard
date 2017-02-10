package com.programmingwizzard.charrizard.bot.listeners;

import com.google.common.eventbus.Subscribe;
import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CUser;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class ReputationListener {

    private final Charrizard charrizard;

    public ReputationListener(Charrizard charrizard) {
        this.charrizard = charrizard;
    }

    @Subscribe
    public void onTextMessage(MessageReceivedEvent event) {
        String content = event.getMessage().getContent();
        StringBuilder sb = new StringBuilder();
        if (content.startsWith("+") || content.endsWith("+")) {
            CGuild cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
            if (cGuild == null) {
                charrizard.getCGuildManager().createGuild(event.getGuild());
                cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
            }
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            if (mentionedUsers.size() == 0) {
                return;
            }
            CUser authorCUser = cGuild.getUser(event.getAuthor());
            if (authorCUser != null) {
                cGuild.createUser(event.getAuthor());
                authorCUser = cGuild.getUser(event.getAuthor());
            }
            if (authorCUser.getReputation() < 0) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " - your points can not be negative!").queue();
                return;
            }
            for (User mentionedUser : mentionedUsers) {
                if (mentionedUser.getId().equals(event.getAuthor().getId())) {
                    continue;
                }
                CUser cUser = cGuild.getUser(mentionedUser);
                if (cUser == null) {
                    cGuild.createUser(mentionedUser);
                    cUser = cGuild.getUser(mentionedUser);
                }
                cUser.addReputation();
                sb.append(mentionedUser.getName()).append(", ");
            }
            String users = sb.toString();
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " - You mentioned the: " + users.substring(0, sb.length() - 2)).queue();
        } else if (content.startsWith("-") || content.endsWith("-")) {
            CGuild cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
            if (cGuild == null) {
                charrizard.getCGuildManager().createGuild(event.getGuild());
                cGuild = charrizard.getCGuildManager().getGuild(event.getGuild());
            }
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            if (mentionedUsers.size() == 0) {
                return;
            }
            CUser authorCUser = cGuild.getUser(event.getAuthor());
            if (authorCUser != null) {
                cGuild.createUser(event.getAuthor());
                authorCUser = cGuild.getUser(event.getAuthor());
            }
            if (authorCUser.getReputation() < 0) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " - your points can not be negative!").queue();
                return;
            }
            for (User mentionedUser : mentionedUsers) {
                if (mentionedUser.getId().equals(event.getAuthor().getId())) {
                    continue;
                }
                CUser cUser = cGuild.getUser(mentionedUser);
                if (cUser == null) {
                    cGuild.createUser(mentionedUser);
                    cUser = cGuild.getUser(mentionedUser);
                }
                cUser.removeReputation();
                sb.append(mentionedUser.getName()).append(", ");
            }
            String users = sb.toString();
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " - You mentioned the: " + users.substring(0, sb.length() - 2)).queue();
        }
    }

}
