package ru.softomate.softomatetest.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.net.URL;

import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.utils.JsonHelper;
import ru.softomate.softomatetest.utils.Query;

/**
 * Created by Вараздат on 25.11.2017.
 */

public class JsonLoader extends AsyncTaskLoader<Text> {


    public static final int JSON_LOADER_ID = 111111;
    public static final String TEXT = "Text";

    private String mSubmittedText;

    public JsonLoader(Context context, Bundle bundle){
        super(context);
        mSubmittedText = bundle.getString(TEXT);
    }

    /**
     *It loads and parses json
     */
    @Override
    public Text loadInBackground() {
        URL url = Query.getUrl(mSubmittedText);
        String json = Query.query(url);
        String status = JsonHelper.getJsonField(json, JsonHelper.STATUS_FIELD);
        if(status.equals(JsonHelper.STATUS_ERROR)){
            return null;
        }
        String language = JsonHelper.getJsonField(json, JsonHelper.LANGUAGE_FIELD);
        Text text = new Text(mSubmittedText, language);
        return  text;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
