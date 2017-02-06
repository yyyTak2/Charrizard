package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.text.NumberFormat;
import java.util.List;

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
    public void handle(User client, Channel channel, ChannelType type, List<User> mentionedUsers, List<Role> mentionedRoles, List<TextChannel> mentionedChannels, String[] args) throws RateLimitedException
    {
        TextChannel textChannel = (TextChannel) channel;
        textChannel.sendMessage("**Statistics**:").queue();
        textChannel.sendMessage("**Servers**: " + charrizard.getDiscordAPI().getGuilds().size()).queue();
        textChannel.sendMessage("**Clients**: " + charrizard.getDiscordAPI().getUsers().size()).queue();
        textChannel.sendMessage("**Memory**:" +
                                        "\n  **Free**: " + numberFormat.format(runtime.freeMemory() / 1024) + " MB" +
                                        "\n  **Allocated**: " + numberFormat.format(runtime.totalMemory() / 1024) + " MB" +
                                        "\n  **Max**: " + numberFormat.format(runtime.maxMemory() / 1024) + " MB").queue();
    }
}
