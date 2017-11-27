package ru.softomate.softomatetest.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Вараздат on 25.11.2017.
 */


@Dao
public interface TextDAO {

    @Query("SELECT * FROM " + TextsDataBase.TEXT_TABLE)
    List<Text> getTexts();

    @Insert
    void insertText(Text text);

    @Insert
    void insertTexts(List<Text> texts);

    @Query("DELETE  FROM " + TextsDataBase.TEXT_TABLE)
    void deleteTexts();


}

