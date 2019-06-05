package com.example.udefine.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Index")
    private int index=1;

    @ColumnInfo(name = "NoteID")
    private int noteID;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Content")
    private String content;

    public Notes(int noteID,String title,String content){
        this.noteID = noteID;
        this.content = content;
        this.title = title;
    }

    public void setIndex(int idx){this.index=idx;}

    public int getIndex(){return this.index;}
    public String getTitle(){return this.title;}
    public String getContent(){return this.content;}

}
