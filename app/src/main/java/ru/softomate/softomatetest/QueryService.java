package ru.softomate.softomatetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.data.TextsDataBase;


/**
 * Created by Вараздат on 25.11.2017.
 */

public class QueryService extends Service {

    private Text mText;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mText = (Text) intent.getSerializableExtra(Text.TEXT);
        new Thread(runnable).start();
        return START_REDELIVER_INTENT;
    }

    //runnable for inserting new Text instance in DB
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            TextsDataBase db  = TextsDataBase.getTextsDatabase(getApplicationContext());
            db.userDao().insertText(mText);
        }
    };

}
