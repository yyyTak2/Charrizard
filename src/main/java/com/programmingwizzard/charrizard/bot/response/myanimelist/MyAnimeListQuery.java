package com.programmingwizzard.charrizard.bot.response.myanimelist;

import com.programmingwizzard.charrizard.bot.Charrizard;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by gabixdev on 17.02.2017.
 */
public class MyAnimeListQuery {
    private MyAnimeListStatus status;
    private String errorDescription = "";

    public MyAnimeListQuery(Charrizard charrizard) {
        status = MyAnimeListStatus.UNKNOWN_ERROR;
        errorDescription = "Unknown error.";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("https://myanimelist.net/api/account/verify_credentials.xml");
            httpGet.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials(charrizard.getSettings().getMyAnimeList().getUsername(), charrizard.getSettings().getMyAnimeList().getUsername()),
                    "UTF-8", false));

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() >= 400) {
                status = MyAnimeListStatus.AUTH_ERROR;
                errorDescription = "Authorization Error: " + httpResponse.getStatusLine().getReasonPhrase();
                return;
            }
        } catch (IOException e) {
            status = MyAnimeListStatus.REQUEST_ERROR;
            errorDescription = "Can't connect to MyAnimeList: " + e.getMessage();
            e.printStackTrace();
        }
    }

    public MyAnimeListStatus getStatus() {
        return status;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
