package ru.softomate.softomatetest.utils;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Вараздат on 25.11.2017.
 */

public class UrlBuilder {

    public static final String BASE_URL = "https://gateway-a.watsonplatform.net/calls/text/TextGetLanguage";
    public static final String JSON_MODE = "json";
    public static final String XML_MODE = "xml";
    public static final String API_KEY = "4978e60252ae102dfe1341146bb8cc3ec4bbbd78";
    public static final String REQUEST_SPACE = "%20";



    private String mOutputMode;
    private String mAPI;
    private String mText;


    public UrlBuilder outputMode(String outputMode){
        mOutputMode = outputMode;
        return this;
    }


    public UrlBuilder api(String api){
        mAPI = api;
        return this;
    }

    public UrlBuilder text(String text){
        mText = text.replaceAll(" ", REQUEST_SPACE);
        try {
            URLEncoder.encode(mText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("MyTag", "text request = " + mText);
        return this;
    }

    public URL build(){
        String urlString;
        URL url = null;
        urlString = BASE_URL + "?" + "apikey=" + mAPI + "&outputMode=" + mOutputMode
                + "&text=" + mText;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


}




