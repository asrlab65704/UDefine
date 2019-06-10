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
    private static RoomDatabase.Callback roomDatabaseCallbacl =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();

                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MyDao mDao;
        String [] title = {"買菜","搶養唱會門票","作業Demo"};

        PopulateDbAsync(MyRoomDatabase db) {
            mDao = db.myDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAllNotes();
            mDao.deleteAllNoteList();

            for (int i = 0; i <= title.length - 1; i++) {
                NoteList noteList = new NoteList(title[i],"5/29","0",1);
                mDao.insertNotelist(noteList);
                Notes notes = new Notes(mDao.getLastNoteID(),"標題",title[i]);
                mDao.insertNote(notes);
            }
            return null;
        }
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
                            .addCallback(roomDatabaseCallbacl)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
