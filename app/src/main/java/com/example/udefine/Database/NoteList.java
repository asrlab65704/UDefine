package com.example.udefine.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "NoteList")
public class NoteList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "NoteID")
    private int noteID=1;
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "LayoutID")
    private int layoutID;

    public NoteList(String title, int layout) {
        this.title = title;
        this.layoutID = layout;
    }

    public void setNoteID(int i){
        this.noteID=i;
    }

    public int getNoteID(){return this.noteID;}
    public String getTitle(){return this.title;}
    public int getLayoutID(){return this.layoutID;}

}
