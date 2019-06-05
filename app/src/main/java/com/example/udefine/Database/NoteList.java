package com.example.udefine.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "NoteList")
public class NoteList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "NoteID")
    private int noteID=1;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Time")
    @Nullable
    private String time;

    @ColumnInfo(name = "Tag")
    @Nullable
    private String tag;

    @ColumnInfo(name = "LayoutID")
    private int layoutID;

    public NoteList(String title,String time,String tag, int layout) {
        this.title = title;
        this.time = time;
        this.tag = tag;
        this.layoutID = layout;
    }

    public void setNoteID(int i){
        this.noteID=i;
    }

    public int getNoteID(){return this.noteID;}
    public String getTitle(){return this.title;}
    public String getTime(){return this.time;}
    public String getTag(){ return this.tag;}
    public String [] getTagSplit(){
        return this.tag.split(",");
    }
    public int getLayoutID(){return this.layoutID;}

}
