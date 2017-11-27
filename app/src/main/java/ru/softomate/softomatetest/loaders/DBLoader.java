package ru.softomate.softomatetest.loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.data.TextsDataBase;

/**
 * Created by Вараздат on 25.11.2017.
 */

public class DBLoader extends AsyncTaskLoader<List<Text>> {

    public static final int DB_LOADER_ID = 222222;

    private Context mContext;

    public DBLoader(Context context, Bundle bundle){
        super(context);
        mContext = context;
    }


    @Override
    public List<Text> loadInBackground() {
        //load history from DB
        TextsDataBase db = TextsDataBase.getTextsDatabase(mContext);
        List<Text> texts = db.userDao().getTexts();
        return texts;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
