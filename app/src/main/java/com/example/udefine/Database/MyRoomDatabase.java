package com.example.udefine.Database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {NoteList.class, LayoutList.class,Notes.class,Layouts.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract MyDao myDao();
    private static MyRoomDatabase INSTANCE;

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){


                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MyDao mDao;

        // Initial data set
        private static String [] words = {"Question 1", "Question 2"};

        PopulateDbAsync(MyRoomDatabase db) {
            mDao = db.myDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.getAnyLayoutList().length < 1) {
                Log.d("TestMsg","Initiallize default data...");

                /**創建預設LayoutList**/
                LayoutList DefaultLayoutList = new LayoutList("Quick Note");
                mDao.insertLayoutlist(DefaultLayoutList);

                /**創建預設Layout**/
                int DefaultLayoutID = mDao.getLastLayoutListID();
                String[] DefaultLayoutName={"Title"};
                int[] format = {1};
                for(int i=0; i< DefaultLayoutName.length; i++)
                {
                    Layouts DefaultLayouts = new Layouts(DefaultLayoutID,DefaultLayoutName[i],format[i]);
                    mDao.insertLayouts(DefaultLayouts);
                }

                /**創建預設LayoutList**/
                LayoutList DefaultLayoutList1 = new LayoutList("Notification");
                mDao.insertLayoutlist(DefaultLayoutList1);

                /**創建預設Layout**/
                int DefaultLayoutID1 = mDao.getLastLayoutListID();
                String[] DefaultLayoutName1={"Title", "Time"};
                int[] format1 = {1, 2};
                for(int i=0; i< DefaultLayoutName1.length; i++)
                {
                    Layouts DefaultLayouts = new Layouts(DefaultLayoutID1,DefaultLayoutName1[i],format1[i]);
                    mDao.insertLayouts(DefaultLayouts);
                }

                /**創建預設LayoutList**/
                LayoutList DefaultLayoutList2 = new LayoutList("Note");
                mDao.insertLayoutlist(DefaultLayoutList2);

                /**創建預設Layout**/
                int DefaultLayoutID2 = mDao.getLastLayoutListID();
                String[] DefaultLayoutName2={"Title", "Time", "Tag"};
                int[] format2 = {1, 2, 3};
                for(int i=0; i< DefaultLayoutName2.length; i++)
                {
                    Layouts DefaultLayouts = new Layouts(DefaultLayoutID2,DefaultLayoutName2[i],format2[i]);
                    mDao.insertLayouts(DefaultLayouts);
                }

                /**創建預設NoteList**/
                NoteList DefaultNoteList = new NoteList("Demo", "2019/6/18,13:00", "#00ff00,#ffff00,#ff751a,#ff0000,#993399,#6666ff", DefaultLayoutID2);
                mDao.insertNotelist(DefaultNoteList);

                /**創建預設Notes**/
                int DefaultNoteID = mDao.getLastNoteListID();

                String[] DefaultTitle={"Title", "Time", "Tag"};
                String[] DefaultContent={"Demo", "2019/6/18,13:00", "#00ff00,#ffff00,#ff751a,#ff0000,#993399,#6666ff"};

                for(int i=0; i< DefaultTitle.length; i++)
                {
                    Notes DefaultNotes = new Notes(DefaultNoteID, DefaultTitle[i], DefaultContent[i]);
                    mDao.insertNote(DefaultNotes);
                }

                /**創建預設NoteList**/
                NoteList DefaultNoteList1 = new NoteList("幾爸昏", "13:00", "#00ff00,#ff751a,#993399", DefaultLayoutID2);
                mDao.insertNotelist(DefaultNoteList1);

                /**創建預設Notes**/
                int DefaultNoteID1 = mDao.getLastNoteListID();

                String[] DefaultTitle1={"Title", "Time", "Tag"};
                String[] DefaultContent1={"幾爸昏", "13:00", "#00ff00,#ff751a,#993399"};

                for(int i=0; i< DefaultTitle1.length; i++)
                {
                    Notes DefaultNotes = new Notes(DefaultNoteID1, DefaultTitle1[i], DefaultContent1[i]);
                    mDao.insertNote(DefaultNotes);
                }
            }
            return null;
        }
    }
    public static void deleteInstance(){
        INSTANCE=null;
    }

    static MyRoomDatabase getDatabase(final Context context) {
        //TODO migration的問題
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "my_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}