package com.programmingwizzard.charrizard.bot;

import com.google.common.eventbus.EventBus;
import com.programmingwizzard.charrizard.bot.commands.*;
import com.programmingwizzard.charrizard.bot.commands.basic.CommandCaller;
import com.programmingwizzard.charrizard.bot.database.KeepDataThread;
import com.programmingwizzard.charrizard.bot.database.RedisConnection;
import com.programmingwizzard.charrizard.bot.events.EventCaller;
import com.programmingwizzard.charrizard.bot.listeners.ReputationListener;
import com.programmingwizzard.charrizard.bot.listeners.VoiceListener;
import com.programmingwizzard.charrizard.bot.managers.CGuildManager;
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
    private final CGuildManager cGuildManager;
    private final KeepDataThread keepDataThread;
    private JDA discordAPI;

    public Charrizard(Settings settings) {
        this.settings = settings;
        this.eventBus = new EventBus();
        this.commandCaller = new CommandCaller(this);
        this.redisConnection = new RedisConnection(settings);
        this.cGuildManager = new CGuildManager(this);
        this.keepDataThread = new KeepDataThread(this);
    }

    public void start() throws RateLimitedException, InterruptedException, LoginException {
        this.discordAPI = new JDABuilder(AccountType.BOT)
                                  .setToken(settings.getToken())
                                  .setGame(new GameImpl(settings.getGame(), settings.getGameUrl(), settings.isTwitch() ? Game.GameType.TWITCH : Game.GameType.DEFAULT))
                                  .addListener(new EventCaller(this))
                                  .setAutoReconnect(true)
                                  .setAudioEnabled(true)
                                  .setBulkDeleteSplittingEnabled(false)
                                  .buildBlocking();
        if (settings.getRedis().isEnabled()) {
            redisConnection.start();
            keepDataThread.start();
        }
        initCommands();
        initListeners();
    }

    private void initCommands() {
        commandCaller.getCommands().add(new AuthorCommand());
        commandCaller.getCommands().add(new BigTextCommand(this));
        commandCaller.getCommands().add(new InviteCommand(this));
        commandCaller.getCommands().add(new MinecraftCommand(this));
        commandCaller.getCommands().add(new CleverbotCommand(this));
        commandCaller.getCommands().add(new PingCommand());
        commandCaller.getCommands().add(new CatCommand(this));
        commandCaller.getCommands().add(new AudioCommand(this));
        commandCaller.getCommands().add(new HelpCommand(this));
        commandCaller.getCommands().add(new DiscordCommand(this));
        commandCaller.getCommands().add(new StatisticsCommand(this));
        commandCaller.getCommands().add(new ReputationCommand(this));
        if (settings.getMyAnimeList().isEnabled()) {
            commandCaller.getCommands().add(new AnimeCommand(this));
        }
        this.eventBus.register(commandCaller);
    }

    private void initListeners() {
        this.eventBus.register(new VoiceListener(this));
        this.eventBus.register(new ReputationListener(this));
    }

    public Settings getSettings() {
        return settings;
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

    public CGuildManager getCGuildManager() {
        return cGuildManager;
    }
}
