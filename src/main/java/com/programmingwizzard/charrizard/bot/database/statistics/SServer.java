package com.programmingwizzard.charrizard.bot.database.statistics;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
@Entity(value = "Servers")
public class SServer
{
    @Id
    private int id;

    @Indexed(options = @IndexOptions(unique = false))
    private String guildId;

    public String getGuildId()
    {
        return guildId;
    }
}
