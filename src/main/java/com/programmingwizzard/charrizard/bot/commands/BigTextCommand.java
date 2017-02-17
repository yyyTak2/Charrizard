package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.CharCodes;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.util.LinkedHashSet;
import java.util.Set;

/*
 * @author Libter
 * @date 06.02.2017
 */
public class BigTextCommand extends Command {
    private final Charrizard charrizard;

    public BigTextCommand(Charrizard charrizard) {
        super("bigtext", "Sends message from regional indicator characters.");
        this.charrizard = charrizard;
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException {
        TextChannel channel = message.getChannel();
        if (args.length < 3) {
            sendUsage(message, charrizard.getSettings().getPrefix() + "bigtext <print|raw|react> <text>");
            return;
        }

        StringBuilder result = new StringBuilder();
        String action = args[1];
        switch (action.toLowerCase()) {
            case "print":
                for (int i = 2; i < args.length; i++) {
                    for (char c : args[i].toLowerCase().toCharArray()) {
                        result.append(toRegionalIndicator(c)).append(" ");
                    }
                    result.append("   ");
                }
                channel.sendMessage(result.toString()).queue();
                break;
            case "raw":
                result.append("```");
                for (int i = 2; i < args.length; i++) {
                    for (char c : args[i].toLowerCase().toCharArray()) {
                        if (!toRegionalIndicator(c).isEmpty()) {
                            result.append(":regional_indicator_").append(c).append(": ");
                        }
                    }
                    result.append("   ");
                }
                result.append("```");
                channel.sendMessage(result.toString()).queue();
                break;
            case "react":
                Set<String> reactions = new LinkedHashSet<>();
                for (int i = 2; i < args.length; i++) {
                    for (char c : args[i].toLowerCase().toCharArray()) {
                        String reaction = toRegionalIndicator(c);
                        if (!reaction.isEmpty()) {
                            reactions.add(reaction);
                        }
                    }
                }
                for (String reaction : reactions) {
                    message.getOrigin().addReaction(reaction).queue();
                }
                break;
            default:
                sendUsage(message, charrizard.getSettings().getPrefix() + "bigtext <print|raw|react> <text>");
        }
    }

    private String toRegionalIndicator(char c) {
        if (c >= CharCodes.SMALL_A && c <= CharCodes.SMALL_Z) {
            c -= CharCodes.SMALL_A;
            return String.valueOf(Character.toChars(CharCodes.REGIONAL_INDICATOR_A + c));
        } else {
            return "";
        }
    }

}
