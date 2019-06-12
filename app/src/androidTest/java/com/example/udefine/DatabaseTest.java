package com.example.udefine;


import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.udefine.Database.LayoutList;
import com.example.udefine.Database.Layouts;
import com.example.udefine.Database.MyRoomDatabase;
import com.example.udefine.Database.NoteList;
import com.example.udefine.Database.Notes;
import com.example.udefine.Database.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    ViewModel viewModel;
    MyRoomDatabase roomDatabase;

    @Before
    public void setup(){
//        viewModel = ViewModelProviders.of(new MainActivity()).get(ViewModel.class);
        roomDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                MyRoomDatabase.class)
                .build();


    }

    @After
    public void close(){
        roomDatabase.close();
    }

    @Test
    public void testInsert(){
        insertLayout();
        insertNote();

        int[] allNoteID = roomDatabase.myDao().getAllNoteID();
        int lastNoteID = roomDatabase.myDao().getLastNoteID();

        assertEquals(allNoteID[allNoteID.length-1],lastNoteID);
        assertEquals("青江菜",roomDatabase.myDao().getNotesFromID(allNoteID[0])[2].getContent());
        assertEquals(1,roomDatabase.myDao().getLayoutIDFromNoteID(lastNoteID));
        assertEquals("搶票",roomDatabase.myDao().getNotesFromID(lastNoteID)[0].getContent());
    }

    @Test
    public void testUpdate(){

    }



    public void insertNote(){
        roomDatabase.myDao().insertNotelist(new NoteList("買菜",null,"#12345",2));
        int lastNoteID = roomDatabase.myDao().getLastNoteID();
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"標題","買菜"));
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"TODO","空心菜"));
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"TODO","青江菜"));
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"TODO","蘿蔔"));
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"Tag","#12345"));

        roomDatabase.myDao().insertNotelist(new NoteList("搶票","2019/5/26 9:30",null,1));
        lastNoteID = roomDatabase.myDao().getLastNoteID();
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"標題","搶票"));
        roomDatabase.myDao().insertNote(new Notes(lastNoteID,"時間","2019/5/26 9:30"));

    }

    public void insertLayout(){
        roomDatabase.myDao().insertLayoutlist(new LayoutList("default"));
        int lastLayoutID = roomDatabase.myDao().getLastLayoutListID();
        roomDatabase.myDao().insertLayouts(new Layouts(lastLayoutID,"標題",1));
        roomDatabase.myDao().insertLayouts(new Layouts(lastLayoutID,"時間",2));

        roomDatabase.myDao().insertLayoutlist(new LayoutList("購物清單"));
        lastLayoutID = roomDatabase.myDao().getLastLayoutListID();
        roomDatabase.myDao().insertLayouts(new Layouts(lastLayoutID,"標題",1));
        roomDatabase.myDao().insertLayouts(new Layouts(lastLayoutID,"TODO",3));
        roomDatabase.myDao().insertLayouts(new Layouts(lastLayoutID,"Tag",4));
    }

}
