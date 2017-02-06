package com.programmingwizzard.charrizard.bot.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.programmingwizzard.charrizard.bot.Settings;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class MongoConnection
{
    private final MongoClient mongoClient;
    private MongoDatabase charrizardDatabase;

    public MongoConnection(Settings settings)
    {
        this.mongoClient = new MongoClient(settings.getMongoSettings().getIp(), settings.getMongoSettings().getPort());
    }

    public void start()
    {
        if (mongoClient.isLocked())
        {
            System.out.println("[MONGO] Client is locked!");
            return;
        }
        charrizardDatabase = mongoClient.getDatabase("charrizard");
        if (charrizardDatabase == null)
        {
            mongoClient.close();
            System.out.println("[MONGO] Database with name `charrizard` does not exists!");
            return;
        }
        // TODO: morphia
    }

    public MongoClient getMongoClient()
    {
        return mongoClient;
    }

    public MongoDatabase getCharrizardDatabase()
    {
        return charrizardDatabase;
    }
}
