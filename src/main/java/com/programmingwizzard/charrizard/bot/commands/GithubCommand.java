package com.programmingwizzard.charrizard.bot.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.github.GithubResponses;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 05.02.2017
 */
public class GithubCommand extends Command
{
    private final GithubResponses githubResponses;

    public GithubCommand()
    {
        super("github");
        this.githubResponses = new GithubResponses();
    }

    @Override
    public void handle(User client, Channel channel, ChannelType type, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        if (args.length == 0 || args.length == 1)
        {
            textChannel.sendMessage("**Correct usage**: !github <user>").queue();
            return;
        }
        switch (args[1])
        {
            case "user":
                this.checkUser(client, textChannel, args);
                break;
            default:
                textChannel.sendMessage("**Correct usage**: !github <user>").queue();
                break;
        }
    }

    private void checkUser(User client, TextChannel channel, String[] args)
    {
        if (args.length != 3)
        {
            channel.sendMessage("**Correct usage**: !github user <nickname>").queue();
            return;
        }
        String nickname = args[2];
        if (nickname == null || nickname.isEmpty())
        {
            channel.sendMessage("**Correct usage**: !github user <nickname>").queue();
            return;
        }
        JsonObject object = githubResponses.getUser(nickname);
        if (object == null)
        {
            channel.sendMessage("This user does not exist!").queue();
            return;
        }
        System.out.println(new Gson().toJson(object));
    }
}
