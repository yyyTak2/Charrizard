package com.programmingwizzard.charrizard.bot.basic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.bot.database.RedisData;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author ProgrammingWizzard
 * @date 09.02.2017
 */
public class CGuild implements RedisData {

    private final Guild guild;
    private final Executor executor;
    private final Cache<String, CUser> userCache;

    public CGuild(Guild guild) {
        this.guild = guild;
        this.executor = new ThreadPoolExecutor(2, 16, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.userCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    }

    @Override
    public void save(Jedis jedis) {
        // TODO
    }

    public void runCommand(Command command, CMessage message, String[] args) {
        if (command == null) {
            return;
        }
        runAsync(() -> {
            try {
                command.handle(message, args);
            } catch (RateLimitedException ex) {
                ex.printStackTrace();
                message.getOrigin().getTextChannel().sendMessage("Response error! Please, look at the console!").queue();
            }
        });
    }

    public void runAsync(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.execute(runnable);
    }

    public void createUser(User user) {
        if (user == null) {
            return;
        }
        // TODO: download reputation
        CUser cUser = new CUser(user, 0);
        userCache.put(user.getId(), cUser);
    }

    public Guild getOrigin() {
        return guild;
    }

    public String getGuildId() {
        return getOrigin().getId();
    }

    public CUser getUser(User user) {
        if (user == null) {
            return null;
        }
        return userCache.getIfPresent(user.getId());
    }

}
