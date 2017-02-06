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
public class BigTextCommand extends Command
{

    public BigTextCommand()
    {
        super("bigtext");
    }

    @Override
    public void handle(CMessage message, String[] args) throws RateLimitedException
    {
        TextChannel channel = message.getChannel();
        if (args.length == 1)
        {
            channel.sendMessage("**Correct usage**: !bigtext <text>").queue();
            return;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 1; i < args.length; i++)
        {
            for (char c : args[i].toCharArray())
            {
                result.append(toRegionalIndicator(c)).append(" ");
            }
            result.append("   ");
        }
        channel.sendMessage(result.toString()).queue();
    }

    private String toRegionalIndicator(char c) {
        if (c >= CharCodes.SMALL_A && c <= CharCodes.SMALL_Z)
        {
            c -= CharCodes.SMALL_A;
            return String.valueOf(Character.toChars(CharCodes.REGIONAL_INDICATOR_A + c));
        }
        else
        {
            return "";
        }
    }

}
