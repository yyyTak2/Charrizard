package com.programmingwizzard.charrizard.bot;

import com.google.common.eventbus.AsyncEventBus;
import com.programmingwizzard.charrizard.bot.commands.AuthorCommand;
import com.programmingwizzard.charrizard.bot.commands.HelpCommand;
import com.programmingwizzard.charrizard.bot.commands.basic.CommandCaller;
import com.programmingwizzard.charrizard.bot.commands.InviteCommand;
import com.programmingwizzard.charrizard.bot.events.EventCaller;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.impl.GameImpl;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class Charrizard
{
    private final AsyncEventBus eventBus;
    private final Settings settings;
    private final CommandCaller commandCaller;
    private JDA discordAPI;

    public Charrizard(Settings settings)
    {
        this.settings = settings;
        this.eventBus = new AsyncEventBus("Charrizard | EventBUS", Executors.newCachedThreadPool());
        this.commandCaller = new CommandCaller(this);
    }

    public void start() throws RateLimitedException, InterruptedException, LoginException
    {
        this.discordAPI = new JDABuilder(AccountType.BOT)
                                  .setToken(settings.getToken())
                                  .setGame(new GameImpl(settings.getGame(), settings.getGameUrl(), Game.GameType.DEFAULT))
                                  .addListener(new EventCaller(this))
                                  .setAutoReconnect(true)
                                  .setAudioEnabled(false)
                                  .setBulkDeleteSplittingEnabled(false)
                                  .buildBlocking();
        initCommands();
    }

    private void initCommands()
    {
        commandCaller.getCommands().add(new AuthorCommand());
        commandCaller.getCommands().add(new InviteCommand());
        commandCaller.getCommands().add(new HelpCommand(this));
        this.eventBus.register(commandCaller);
    }

    public AsyncEventBus getEventBus()
    {
        return eventBus;
    }

    public CommandCaller getCommandCaller()
    {
        return commandCaller;
    }
}
