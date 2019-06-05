package com.example.udefine.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Layouts")
public class Layouts {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Index")
    private int index = 1;

    @ColumnInfo(name = "LayoutID")
    private int mLayoutID;

    @ColumnInfo(name = "LayoutName")
    private String mLayoutName;

    @ColumnInfo(name = "Format")
    private int mFormat;

    public Layouts(int mLayoutID, String mLayoutName, int mFormat) {
        this.mLayoutID = mLayoutID;
        this.mLayoutName = mLayoutName;
        this.mFormat = mFormat;
    }


//    public void setmIndex(int idx){this.mIndex=idx;}
    public void setIndex(int idx){this.index=idx;}
    public int getIndex(){return this.index;}
    public int getLayoutID(){return this.mLayoutID;}
    public String getLayoutName(){return this.mLayoutName;}
    public int getFormat(){return this.mFormat;}

}
