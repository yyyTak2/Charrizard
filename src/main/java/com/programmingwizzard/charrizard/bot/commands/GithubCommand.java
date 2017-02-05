package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.response.github.GithubResponses;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
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

    }
}
