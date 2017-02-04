package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class InviteCommand extends Command
{
    public InviteCommand()
    {
        super("invite");
    }

    @Override
    public void handle(User client, Channel channel, ChannelType type, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        textChannel.sendMessage("Invite URL: https://discordapp.com/oauth2/authorize?&client_id=277567355360509962&scope=bot&permissions=268561430").queue();
    }
}
