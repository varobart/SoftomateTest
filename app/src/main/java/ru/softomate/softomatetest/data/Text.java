package ru.softomate.softomatetest.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Вараздат on 25.11.2017.
 */

@Entity(tableName = TextsDataBase.TEXT_TABLE)
public class Text implements Serializable{

    public static String TEXT = "text";


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "text")
    private String mText;
    @ColumnInfo(name = "language")
    private String mLanguage;

    public Text(String text, String language){
        mText = text;
        mLanguage = language;
    }

    public String getText() {
        return mText;
    }

    public String getLanguage() {
        return mLanguage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
