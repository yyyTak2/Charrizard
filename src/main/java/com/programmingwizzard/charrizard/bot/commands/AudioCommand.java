package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CGuild;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.basic.audio.CAudio;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author ProgrammingWizzard
 * @date 17.02.2017
 */
public class AudioCommand extends Command {

    private final Charrizard charrizard;

    public AudioCommand(Charrizard charrizard) {
        super("audio");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        if (args.length < 3) {
            sendUsage(message, "!audio <open/close/queue>");
            return;
        }
        switch (args[1]) {
            case "open":
                open(message, args);
                break;
            case "close":
                close(message, args);
                break;
            case "queue":
                queue(message, args);
                break;
            default:
                sendUsage(message, "!audio <open/close/queue>");
                break;
        }
    }

    private void open(CMessage message, String[] args) {
        CGuild guild = charrizard.getCGuildManager().getGuild(message.getGuild());
        if (guild == null) {
            charrizard.getCGuildManager().createGuild(message.getGuild());
            guild = charrizard.getCGuildManager().getGuild(message.getGuild());
        }
        CAudio audio = guild.getAudio();
        if (audio.isConnected()) {
            return;
        }
        VoiceChannel channel = message.getGuild().getVoiceChannels().stream().filter(voice -> voice.getMembers().stream().filter(member -> member.getUser().getId().equals(message.getAuthor().getId())).findFirst().orElse(null) != null).findFirst().orElse(null);
        if (channel == null) {
            return;
        }
        audio.open(channel);
    }

    private void close(CMessage message, String[] args) {
        CGuild guild = charrizard.getCGuildManager().getGuild(message.getGuild());
        if (guild == null) {
            charrizard.getCGuildManager().createGuild(message.getGuild());
            guild = charrizard.getCGuildManager().getGuild(message.getGuild());
        }
        CAudio audio = guild.getAudio();
        if (!audio.isConnected()) {
            return;
        }
        audio.close();
    }

    private void queue(CMessage message, String[] args) {
        sendError(message, "Not implementable yet!");
    }
}
