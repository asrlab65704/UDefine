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
    /*Note*/
    @Insert
    void insertNotelist(NoteList noteList);

    @Insert
    void insertNote(Notes notes);

    /*Layout*/
    @Insert
    void insertLayoutlist(LayoutList layoutList);

    @Insert
    void insertLayouts(Layouts layouts);

    //----------搜尋---------//
    /*Note*/
    //@Query("select * from NoteList") //被livedata取代
    //NoteList[] getNoteList();

    //@Query("select * from Notes") //被livedata取代
    //Notes[] getNotes();

    @Query("SELECT * from NoteList ORDER BY NoteID ASC")
    LiveData<List<NoteList>> getAllNoteList();

    @Query("SELECT * from Notes ORDER BY `Index` ASC")
    LiveData<List<Notes>> getAllNotes();

    @Query("select * from Notes where NoteID = :noteID")
    Notes[] getNotesFromID(int noteID);

    /* 用來新增一筆Notes用的*/
    @Query("select MAX(NoteID) from NoteList")
    int getLastNoteID();

    /*用來設定layout 範例*/
    @Query("select MAX(LayoutID) from LayoutList")
    int getLastLayoutListID();

    /*用來設定Note 範例*/
    @Query("select MAX(LayoutID) from NoteList")
    int getLastNoteListID();

    @Query("select LayoutID from NoteList where NoteID = :noteID")
    int getLayoutIDFromNoteID(int noteID);

    @Query("select count(1) from NoteList")
    int getNumberOfNotes();

    /*Layout*/
    @Query("SELECT * from LayoutList ORDER BY LayoutID ASC")
    LiveData<List<LayoutList>> getAllLayoutList();

    @Query("SELECT * from Layouts ORDER BY `Index` ASC")
    LiveData<List<Layouts>> getAllLayouts();

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
    /*Note*/
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

    /*Layout*/
    @Delete
    void deleteLayout(Layouts layouts);

    @Query("delete from Layouts where LayoutID = :layoutID")
    void deleteLayout(int layoutID);

    @Delete
    void deleteLayoutList(LayoutList layoutList);

    @Query("delete from LayoutList where LayoutID = :layoutID")
    void deleteLayoutList(int layoutID);

    @Query("delete from LayoutList")
    void deleteAllLayoutList();

    @Query("delete from Layouts")
    void deleteAllLayouts();

    /**用來判斷Database是否是初始狀態**/
    @Query("SELECT * from LayoutList LIMIT 1")
    LayoutList[] getAnyLayoutList();

    @Query("SELECT * from NoteList LIMIT 1")
    NoteList[] getAnyNoteList();

}