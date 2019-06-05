package com.example.udefine.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface MyDao {
    //----------新增---------//
    @Insert
    void insertNotelist(NoteList noteList);

    @Insert
    void insertNote(Notes notes);

    //----------搜尋---------//
    @Query("select * from NoteList")
    NoteList[] getNoteList();

    @Query("select * from Notes")
    Notes[] getNotes();

    @Query("SELECT * from NoteList ORDER BY NoteID ASC")
    LiveData<List<NoteList>> getAllNoteList();

    @Query("SELECT * from Notes ORDER BY `Index` ASC")
    LiveData<List<NoteList>> getAllNotes();

    @Query("select * from Notes where NoteID = :noteID")
    Notes[] getNotesFromID(int noteID);

    /* 用來新增一筆Notes用的*/
    @Query("select MAX(NoteID) from NoteList")
    int getLastNoteID();

    @Query("select LayoutID from NoteList where NoteID = :noteID")
    int getLayoutIDFromNoteID(int noteID);

    @Query("select count(1) from NoteList")
    int getNumberOfNotes();

    //----------更新---------//
    @Update
    void updateNoteList(NoteList noteList);

    @Update
    void updateNotes(Notes notes);

    @Query("update NoteList set LayoutID = :layoutID where NoteID = :noteID")
    void updateNoteListLayout(int layoutID,int noteID);

    @Query("update NoteList set Title = :title where NoteID = :noteID; ")
    void updateNoteListTitle(int noteID,String title);


    //----------刪除---------//
    @Delete
    void deleteNote(Notes notes);

    @Query("delete from Notes where NoteID = :noteID")
    void deleteNote(int noteID);

    @Delete
    void deleteNoteList(NoteList noteList);

    @Query("delete from NoteList where NoteID = :noteID")
    void deleteNoteList(int noteID);

    @Query("delete from NoteList")
    void deleteAllNoteList();

    @Query("delete from Notes")
    void deleteAllNotes();


}
