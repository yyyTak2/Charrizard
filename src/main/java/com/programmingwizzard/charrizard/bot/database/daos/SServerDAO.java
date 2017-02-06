package com.programmingwizzard.charrizard.bot.database.daos;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/*
 * @author ProgrammingWizzard
 * @date 06.02.2017
 */
public class SServerDAO extends BasicDAO<SServerDAO, String>
{
    public SServerDAO(Class<SServerDAO> entityClass, Datastore ds)
    {
        super(entityClass, ds);
    }
}
