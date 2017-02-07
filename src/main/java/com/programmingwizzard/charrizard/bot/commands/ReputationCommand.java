package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.database.basic.StatisticGuild;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.awt.*;
import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class ReputationCommand extends Command {

    private final Charrizard charrizard;

    public ReputationCommand(Charrizard charrizard) {
        super("reputation");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        if (args.length < 2) {
            sendUsage(message, "!reputation <info|reset>");
            return;
        }
        switch (args[1]) {
            case "info":
                infoArgument(message, args);
                break;
            case "reset":
                resetArgument(message, args);
                break;
            default:
                sendUsage(message, "!reputation <info|reset>");
                break;
        }
    }

    private void infoArgument(CMessage message, String[] args) {
        if (args.length == 2) {
            sendUsage(message, "!reputation info <mention|id>");
            return;
        }
        List<User> mentionedUsers = message.getMentionedUsers();
        User targetUser;
        if (mentionedUsers.size() == 0) {
            targetUser = this.charrizard.getDiscordAPI().getUserById(args[2]);
        } else {
            targetUser = mentionedUsers.get(0);
        }
        if (targetUser == null) {
            sendError(message, "This user does not exists!");
            return;
        }
        StatisticGuild statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(message.getGuild());
        if (statisticGuild == null) {
            charrizard.getStatisticsGuildManager().loadGuild(message.getGuild());
            statisticGuild = charrizard.getStatisticsGuildManager().getStatistics(message.getGuild());
        }
        int likes = statisticGuild.getUser(targetUser);
        if (likes == 0) {
            statisticGuild.loadUser(targetUser);
            likes = statisticGuild.getUser(targetUser);
        }
        EmbedBuilder builder = getEmbedBuilder()
                                       .setTitle("Charrizard")
                                       .setFooter("© 2017 Charrizard contributors", null)
                                       .setUrl("https://github.com/ProgrammingWizzard/Charrizard/")
                                       .setColor(new Color(0, 250, 0))
                                       .addField(targetUser.getName(), "Likes: " + likes, true);
        sendEmbedMessage(message, builder);
    }

    private void resetArgument(CMessage message, String[] args) {
        sendError(message, "Not implementable yet!");
    }
}
