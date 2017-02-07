package com.programmingwizzard.charrizard.bot.commands;

import com.programmingwizzard.charrizard.bot.commands.basic.CMessage;
import com.programmingwizzard.charrizard.bot.commands.basic.Command;
import com.programmingwizzard.charrizard.utils.CharCodes;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/*
 * @author Libter
 * @date 06.02.2017
 */
public class BigTextCommand extends Command {
    public BigTextCommand()
    {
        super("bigtext");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel channel = message.getChannel();
        if (args.length < 3) {
            sendUsage(message, "!bigtext <print|raw|react> <text>");
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
                for (int i = 2; i < args.length; i++) {
                    for (char c : args[i].toLowerCase().toCharArray()) {
                        String reaction = toRegionalIndicator(c);
                        if (!reaction.isEmpty()) {
                            message.getOrigin().addReaction(reaction).queue();
                        }
                    }
                }
                break;
            default:
                sendUsage(message, "!bigtext <print|raw|react> <text>");
        }
    }

    private String toRegionalIndicator(char c)
    {
        if (c >= CharCodes.SMALL_A && c <= CharCodes.SMALL_Z) {
            c -= CharCodes.SMALL_A;
            return String.valueOf(Character.toChars(CharCodes.REGIONAL_INDICATOR_A + c));
        } else {
            return "";
        }
    }

}
