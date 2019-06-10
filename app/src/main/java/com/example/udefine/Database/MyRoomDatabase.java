package com.example.udefine.Database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
            if (mDao.getAnyLayoutList().length < 1 & mDao.getAnyNoteList().length < 1) {
                /**創建預設LayoutList**/
                LayoutList DefaultLayoutList = new LayoutList("Default");
                mDao.insertLayoutlist(DefaultLayoutList);

                /**創建預設Layout**/
                int DefaultLayoutID = mDao.getLastLayoutListID();
                String[] DefaultLayoutName={"標題", "時間", "Tag", "備註"};
                int[] format = {0, 1, 2, 3};
                for(int i=0; i< DefaultLayoutName.length; i++)
                {
                    Layouts DefaultLayouts = new Layouts(DefaultLayoutID,DefaultLayoutName[i],format[i]);
                    mDao.insertLayouts(DefaultLayouts);
                }

                /**創建預設NoteList**/
                NoteList DefaultNoteList = new NoteList("幫App評分", "2019/5/26,09:30", "#123456,#123749", DefaultLayoutID);
                mDao.insertNotelist(DefaultNoteList);

                /**創建預設Notes**/
                int DefaultNoteID = mDao.getLastNoteListID();
                String[] DefaultTitle={"標題", "時間", "Tag", "備註"};
                String[] DefaultContent={"幫App評分", "2019/5/26,09:30", "#123456,#123749", "喜歡的話去Store按個5星QQ"};
                for(int i=0; i< DefaultTitle.length; i++)
                {
                    Notes DefaultNotes = new Notes(DefaultNoteID, DefaultTitle[i], DefaultContent[i]);
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