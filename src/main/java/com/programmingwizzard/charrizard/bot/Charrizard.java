package com.programmingwizzard.charrizard.bot;

import com.google.common.eventbus.EventBus;
import com.programmingwizzard.charrizard.bot.commands.*;
import com.programmingwizzard.charrizard.bot.commands.basic.CommandCaller;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.database.managers.StatisticsGuildManager;
import com.programmingwizzard.charrizard.bot.database.threads.StatisticsSaveThread;
import com.programmingwizzard.charrizard.bot.events.EventCaller;
import com.programmingwizzard.charrizard.bot.listeners.LikeListener;
import com.programmingwizzard.charrizard.bot.listeners.StatisticsListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.impl.GameImpl;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class Charrizard {
    private final EventBus eventBus;
    private final Settings settings;
    private final CommandCaller commandCaller;
    private final RedisConnection redisConnection;
    private final StatisticsGuildManager statisticsGuildManager;
    private final StatisticsSaveThread statisticsSaveThread;
    private final StatisticsListener statisticsListener;
    private final LikeListener likeListener;
    private JDA discordAPI;

    public Charrizard(Settings settings) {
        this.settings = settings;
        this.eventBus = new EventBus();
        this.commandCaller = new CommandCaller(this);
        this.redisConnection = new RedisConnection(settings);
        this.statisticsGuildManager = new StatisticsGuildManager(redisConnection);
        this.statisticsSaveThread = new StatisticsSaveThread(statisticsGuildManager);
        this.statisticsListener = new StatisticsListener(this);
        this.likeListener = new LikeListener(this);
    }

    public void start() throws RateLimitedException, InterruptedException, LoginException {
        this.discordAPI = new JDABuilder(AccountType.BOT)
                                  .setToken(settings.getToken())
                                  .setGame(new GameImpl(settings.getGame(), settings.getGameUrl(), Game.GameType.DEFAULT))
                                  .addListener(new EventCaller(this))
                                  .setAutoReconnect(true)
                                  .setAudioEnabled(false)
                                  .setBulkDeleteSplittingEnabled(false)
                                  .buildBlocking();
        initListeners();
        initCommands();

        redisConnection.start();
        statisticsSaveThread.start();
    }

    private void initCommands() {
        commandCaller.getCommands().add(new AuthorCommand());
        commandCaller.getCommands().add(new BigTextCommand());
        commandCaller.getCommands().add(new InviteCommand());
        commandCaller.getCommands().add(new MinecraftCommand());
        commandCaller.getCommands().add(new CleverbotCommand());
        commandCaller.getCommands().add(new PingCommand());
        commandCaller.getCommands().add(new HelpCommand(this));
        commandCaller.getCommands().add(new DiscordCommand(this));
        commandCaller.getCommands().add(new StatisticsCommand(this));
        this.eventBus.register(commandCaller);
    }

    private void initListeners() {
        this.eventBus.register(statisticsListener);
        this.eventBus.register(likeListener);
    }

    public JDA getDiscordAPI() {
        return discordAPI;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public CommandCaller getCommandCaller() {
        return commandCaller;
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }

    public StatisticsGuildManager getStatisticsGuildManager() {
        return statisticsGuildManager;
    }
}
