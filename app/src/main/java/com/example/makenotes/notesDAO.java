package com.example.makenotes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao

public interface notesDAO {
    @Query("select * from notes")
    List<noteTable> dispValues();

    @Insert
    void addVals(noteTable nTable);

    @Query("delete from notes where id=:id")
    void delVal(int id);

    @Query("Update notes set title=:title, content=:content where id=:id")
    void updateNote(int id, String title, String content);
}
