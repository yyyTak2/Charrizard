package com.programmingwizzard.charrizard;

import com.programmingwizzard.charrizard.bot.Charrizard;
import com.programmingwizzard.charrizard.bot.Settings;
import com.programmingwizzard.charrizard.utils.GsonUtils;

import java.io.File;

/*
 * @author ProgrammingWizzard
 * @date 04.02.2017
 */
public class Main {
    public static void main(String[] args) throws Exception
    {
        Settings settings = GsonUtils.readConfiguration(Settings.class, new File("config.json"));
        Charrizard charrizard = new Charrizard(settings);
        charrizard.start();
    }
}
