package com.programmingwizzard.charrizard.bot.response.kiciusie;

import com.programmingwizzard.charrizard.bot.response.Response;

/*
 * @author ProgrammingWizzard
 * @date 07.02.2017
 */
public class KiciusieResponses {

    public static String API = "https://api.kiciusie.pl/?type=get&mode=%s";

    public KiciusieResponse call(KiciusieMode mode) {
        return new KiciusieResponse(Response.getJson(
            String.format(API, mode.name().toLowerCase())));
    }

}
