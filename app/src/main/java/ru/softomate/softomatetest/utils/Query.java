package ru.softomate.softomatetest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Вараздат on 27.11.2017.
 */

public class Query {

    public static URL getUrl(String text){
        UrlBuilder urlBuilder = new UrlBuilder();
        URL url = urlBuilder.api(UrlBuilder.API_KEY)
                .outputMode(UrlBuilder.JSON_MODE)
                .text(text)
                .build();
        return url;
    }


    public static String query(URL url){
        InputStream inputStream = getInputStream(url);
        String json = convertStreamToString(inputStream);
        return json;
    }

    public static InputStream getInputStream(URL url){
        InputStream inputStream = null;
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            inputStream = httpsURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    public static String convertStreamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        try {
            while( (currentLine = bufferedReader.readLine()) != null ){
                stringBuilder.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
