package com.example.udefine.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "LayoutList")
public class LayoutList {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "LayoutID")
    private int layoutID=1;

    @ColumnInfo(name = "LayoutName")
    private String layoutName;

    public LayoutList(String layoutName) {
        this.layoutName = layoutName;
    }


    public String getLayoutName(){
        return this.layoutName;
    }

    public int getLayoutID(){return this.layoutID;}

    public void setLayoutID (int i){
        this.layoutID = i;
    }
}
