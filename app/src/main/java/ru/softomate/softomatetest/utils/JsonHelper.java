package ru.softomate.softomatetest.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Вараздат on 27.11.2017.
 */

public class JsonHelper {

    public static final String STATUS_FIELD = "status";
    public static final String LANGUAGE_FIELD = "language";
    public static final String STATUS_ERROR = "ERROR";

    public static String getJsonField(String json, String field){
        String fieldValue = null;
        try {
            fieldValue = new JSONObject(json).getString(field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  fieldValue;
    }
}
