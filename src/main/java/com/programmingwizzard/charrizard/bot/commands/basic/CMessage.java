package com.programmingwizzard.charrizard.bot.commands.basic;

import net.dv8tion.jda.core.entities.*;

import java.util.List;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class CMessage
{
    private final Message message;

    public CMessage(Message message)
    {
        this.message = message;
    }

    public Message getOrigin()
    {
        return message;
    }

    public User getAuthor()
    {
        return message.getAuthor();
    }

    public Guild getGuild()
    {
        return message.getGuild();
    }

    public String getContent()
    {
        return message.getContent();
    }

    public ChannelType getChannelType()
    {
        return message.getChannelType();
    }

    public TextChannel getChannel()
    {
        return message.getTextChannel();
    }

    public List<User> getMentionedUsers()
    {
        return message.getMentionedUsers();
    }

    public List<Role> getMentionedRoles()
    {
        return message.getMentionedRoles();
    }

    public List<TextChannel> getMentionedChannels()
    {
        return message.getMentionedChannels();
    }
}
