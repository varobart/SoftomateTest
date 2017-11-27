package ru.softomate.softomatetest.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Вараздат on 25.11.2017.
 */



@Database(entities = {Text.class}, version = 1)
public abstract class TextsDataBase extends RoomDatabase {

    public static final String TEXT_DATABADE = "text_database";
    public static final String TEXT_TABLE = "text_table";


    private static TextsDataBase INSTANCE;
    public abstract TextDAO userDao();

    public static TextsDataBase getTextsDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), TextsDataBase.class, TEXT_DATABADE)
                            //.allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }
}


