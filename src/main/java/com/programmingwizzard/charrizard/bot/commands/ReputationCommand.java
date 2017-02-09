package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.basic.CUser;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
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
        CGuild cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(message.getGuild());
            cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        }
        CUser cUser = cGuild.getUser(targetUser);
        if (cUser == null) {
            cGuild.createUser(targetUser);
            cUser = cGuild.getUser(targetUser);
        }
        EmbedBuilder builder = getEmbedBuilder()
                                       .addField("Reputation", String.valueOf(cUser.getReputation()), true);
        sendEmbedMessage(message, builder);
    }

    private void resetArgument(CMessage message, String[] args) {
        CGuild cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        if (cGuild == null) {
            charrizard.getCGuildManager().createGuild(message.getGuild());
            cGuild = charrizard.getCGuildManager().getGuild(message.getGuild());
        }
        CUser cUser = cGuild.getUser(message.getAuthor());
        if (cUser == null) {
            cGuild.createUser(message.getAuthor());
            cUser = cGuild.getUser(message.getAuthor());
        }
        if (cUser.getReputation() > 0) {
            cUser.reset();
            message.getChannel().sendMessage(message.getAuthor().getAsMention() + " - done!").queue();
        } else {
            sendError(message, "Points can not be negative!");
        }
    }
}
