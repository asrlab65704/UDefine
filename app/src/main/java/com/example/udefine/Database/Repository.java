package com.example.udefine.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    private MyDao mMyDao;
    private LiveData<List<NoteList>> mNoteList;
    private LiveData<List<LayoutList>> mLayoutList;
    private LiveData<List<Notes>> mNotes;
    private LiveData<List<Layouts>> mLayouts;

    Repository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mMyDao = db.myDao();

        /*Get live data*/
        mNoteList = mMyDao.getAllNoteList();
        mLayoutList = mMyDao.getAllLayoutList();
        mNotes = mMyDao.getAllNotes();
        mLayouts = mMyDao.getAllLayouts();
    }

    public int getLayoutIDFromNoteID(int noteID){
        return mMyDao.getLayoutIDFromNoteID(noteID);
    }

    LiveData<List<NoteList>> getAllNoteList() { return mNoteList; }

    LiveData<List<LayoutList>> getAllLayoutList() { return mLayoutList; }

    LiveData<List<Notes>> getAllNotes() { return mNotes; }

    LiveData<List<Layouts>> getAllLayouts() { return mLayouts; }

    //----------Get-----------//
    public int getLastNoteID(){
        int mLastNoteID = 0;
        try {
            String mLastNoteID_str = new getLastNoteIDAsyncTask(mMyDao).execute().get();
            mLastNoteID = Integer.parseInt(mLastNoteID_str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mLastNoteID;
    }
    public int getLastLayoutID()
    {
        int mLastLayoutID = 0;
        try {
            String mLastLayoutID_str = new getLastLayoutIDAsyncTask(mMyDao).execute().get();
            mLastLayoutID = Integer.parseInt(mLastLayoutID_str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mLastLayoutID;
    }
    public int getNumberOfNotes()
    {
        int mNumberOfNotes = 0;
        try {
            String mNumberOfNotes_str = new getNumberOfNotesAsyncTask(mMyDao).execute().get();
            mNumberOfNotes = Integer.parseInt(mNumberOfNotes_str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mNumberOfNotes;
    }

    public Notes [] getNotesFromNoteID(int id){
        Notes [] notes = null;
        try {
            notes = new getNotesFromIDAsyncTask(mMyDao,id).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return notes;
    }



    //----------新增---------//
    /*Note*/
    public void insertNotelist(NoteList noteList) {
        new insertNotelistAsyncTask(mMyDao).execute(noteList);
    }

    public void insertNote(Notes notes) {
        new insertNoteAsyncTask(mMyDao).execute(notes);
    }

    /*Layout*/
    public void insertLayoutlist(LayoutList layoutList) {
        new insertLayoutlistAsyncTask(mMyDao).execute(layoutList);
    }

    public void insertLayouts(Layouts layouts) {
        new insertLayoutsAsyncTask(mMyDao).execute(layouts);
    }

    //----------更新---------//
    public void updateNoteList(NoteList noteList) {
        new updateNoteListAsyncTask(mMyDao).execute(noteList);
    }

    public void updateNote(Notes notes) {
        new updateNoteAsyncTask(mMyDao).execute(notes);
    }

    public void updateLayouts(Layouts layouts){
        new updateLayoutsAsyncTask(mMyDao).execute(layouts);
    }

    public void updateNoteListLayout(int layoutID, int noteID) {
        int[] IDs = new int[2];
        IDs[0] = layoutID;
        IDs[1] = noteID;
        new updateNoteListLayoutAsyncTask(mMyDao).execute(IDs);
    }

    public void updateNoteListTitle(int noteID, String title) {
        Object[] obj = new Object[2];
        obj[0] = noteID;
        obj[1] = title;
        new updateNoteListTitleAsyncTask(mMyDao).execute(obj);
    }

    public void updateLayoutList(LayoutList layoutList){
        new updateLayoutListAsyncTask(mMyDao).execute(layoutList);
    }

    //----------刪除---------//
    /*Note*/
    public void deleteNote(Notes notes) {
        new deleteNoteAsyncTask(mMyDao).execute(notes);
    }

    public void deleteNote(int noteID) {
        int[] arr = new int[1];
        arr[0] = noteID;
        new deleteNoteByNoteIDAsyncTask(mMyDao).execute(arr);
    }

    public void deleteNoteList(NoteList noteList) {
        new deleteNoteListAsyncTask(mMyDao).execute(noteList);
    }

    public void deleteNoteList(int noteID) {
        int[] arr = new int[1];
        arr[0] = noteID;
        new deleteNoteListByNoteIDAsyncTask(mMyDao).execute(arr);
    }

    public void deleteAllNoteList(){
        new deleteAllNoteListAsyncTask(mMyDao).execute();
    }

    public void deleteAllNotes(){
        new deleteAllNotesAsyncTask(mMyDao).execute();
    }

    /*Layout*/
    public void deleteLayout(int layoutID){
        int[] arr = new int[1];
        arr[0] = layoutID;
        new deleteLayoutByLayoutIDAsyncTask(mMyDao).execute(arr);
    }


    public void deleteLayoutList(LayoutList layoutList) {
        new deleteLayoutListAsyncTask(mMyDao).execute(layoutList);
    }

    public void deleteLayoutList(int layoutID) {
        int[] arr = new int[1];
        arr[0] = layoutID;
        new deleteLayoutListBylayoutIDAsyncTask(mMyDao).execute(arr);
    }

    public void deleteAllLayoutList(){
        new deleteAllLayoutListAsyncTask(mMyDao).execute();
    }

    public void deleteAllLayouts(){
        new deleteAllLayoutsAsyncTask(mMyDao).execute();
    }

    /**
     * Insert a NoteList into the database.
     */
    private static class insertNotelistAsyncTask extends AsyncTask<NoteList, Void, Void> {

        private MyDao mAsyncTaskDao;

        insertNotelistAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteList... params) {
            mAsyncTaskDao.insertNotelist(params[0]);
            return null;
        }
    }

    /**
     * Insert a Note into the database.
     */
    private static class insertNoteAsyncTask extends AsyncTask<Notes, Void, Void> {

        private MyDao mAsyncTaskDao;

        insertNoteAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Notes... params) {
            mAsyncTaskDao.insertNote(params[0]);
            return null;
        }
    }

    /**
     * Insert a LayoutList into the database.
     */
    private static class insertLayoutlistAsyncTask extends AsyncTask<LayoutList, Void, Void> {

        private MyDao mAsyncTaskDao;

        insertLayoutlistAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LayoutList... params) {
            mAsyncTaskDao.insertLayoutlist(params[0]);
            return null;
        }
    }

    /**
     * Insert a LayoutList into the database.
     */
    private static class insertLayoutsAsyncTask extends AsyncTask<Layouts, Void, Void> {

        private MyDao mAsyncTaskDao;

        insertLayoutsAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Layouts... params) {
            mAsyncTaskDao.insertLayouts(params[0]);
            return null;
        }
    }

    /*Update a Notelist*/
    private static class updateNoteListAsyncTask extends AsyncTask<NoteList, Void, Void> {
        private MyDao mAsyncTaskDao;

        updateNoteListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteList... params) {
            mAsyncTaskDao.updateNoteList(params[0]);
            return null;
        }
    }

    /*Update a Note*/
    private static class updateNoteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private MyDao mAsyncTaskDao;

        updateNoteAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Notes... params) {
            mAsyncTaskDao.updateNotes(params[0]);
            return null;
        }
    }

    /*Update the Layout in a NoteList*/
    private static class updateNoteListLayoutAsyncTask extends AsyncTask<int[], Void, Void> {
        private MyDao mAsyncTaskDao;

        updateNoteListLayoutAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final int[]... params) {
            mAsyncTaskDao.updateNoteListLayout(params[0][0], params[0][1]);
            return null;
        }
    }

    /*Update the Layout in a NoteList*/
    private static class updateNoteListTitleAsyncTask extends AsyncTask<Object[], Void, Void> {
        private MyDao mAsyncTaskDao;

        updateNoteListTitleAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Object[]... params) {
            int NoteID = Integer.parseInt(params[0][0].toString());
            String Title = params[0][1].toString();
            mAsyncTaskDao.updateNoteListTitle(NoteID, Title);
            return null;
        }
    }


    private static class updateLayoutListAsyncTask extends AsyncTask<LayoutList, Void, Void> {
        private MyDao mAsyncTaskDao;

        updateLayoutListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LayoutList... params) {
            mAsyncTaskDao.updateLayoutList(params[0]);
            return null;
        }
    }

    private static class updateLayoutsAsyncTask extends AsyncTask<Layouts, Void, Void> {
        private MyDao mAsyncTaskDao;

        updateLayoutsAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Layouts... params) {
            mAsyncTaskDao.updateLayouts(params[0]);
            return null;
        }
    }


    private static class deleteNoteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteNoteAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Notes... params) {
            mAsyncTaskDao.deleteNote(params[0]);
            return null;
        }
    }

    private static class deleteNoteByNoteIDAsyncTask extends AsyncTask<int[], Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteNoteByNoteIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final int[]... params) {
            mAsyncTaskDao.deleteNote(params[0][0]);
            return null;
        }
    }

    private static class deleteNoteListAsyncTask extends AsyncTask<NoteList, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteNoteListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteList... params) {
            mAsyncTaskDao.deleteNoteList(params[0]);
            return null;
        }
    }

    private static class deleteNoteListByNoteIDAsyncTask extends AsyncTask<int[], Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteNoteListByNoteIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final int[]... params) {
            mAsyncTaskDao.deleteNoteList(params[0][0]);
            return null;
        }
    }

    private static class deleteAllNoteListAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteAllNoteListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllNoteList();
            return null;
        }
    }

    private static class deleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteAllNotesAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllNotes();
            return null;
        }
    }

    private static class deleteLayoutListAsyncTask extends AsyncTask<LayoutList, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteLayoutListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LayoutList... params) {
            mAsyncTaskDao.deleteLayoutList(params[0]);
            return null;
        }
    }

    private static class deleteLayoutByLayoutIDAsyncTask extends AsyncTask<int[], Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteLayoutByLayoutIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final int[]... params) {
            mAsyncTaskDao.deleteLayout(params[0][0]);
            return null;
        }
    }

    private static class deleteLayoutListBylayoutIDAsyncTask extends AsyncTask<int[], Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteLayoutListBylayoutIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final int[]... params) {
            mAsyncTaskDao.deleteLayoutList(params[0][0]);
            return null;
        }
    }

    private static class deleteAllLayoutListAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteAllLayoutListAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllLayoutList();
            return null;
        }
    }

    private static class deleteAllLayoutsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteAllLayoutsAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllLayouts();
            return null;
        }
    }

    /** get **/
    private static class getLastNoteIDAsyncTask extends AsyncTask<Void, Void, String> {
        private MyDao mAsyncTaskDao;

        getLastNoteIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String LastNoteID = Integer.toString(mAsyncTaskDao.getLastNoteID());
            return LastNoteID;
        }

    }

    private static class getLastLayoutIDAsyncTask extends AsyncTask<Void, Void, String> {
        private MyDao mAsyncTaskDao;

        getLastLayoutIDAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String LastLayoutID = Integer.toString(mAsyncTaskDao.getLastLayoutListID());
            return LastLayoutID;
        }

    }

    private static class getNumberOfNotesAsyncTask extends AsyncTask<Void, Void, String> {
        private MyDao mAsyncTaskDao;

        getNumberOfNotesAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String NumberOfNotes = Integer.toString(mAsyncTaskDao.getNumberOfNotes());
            return NumberOfNotes;
        }

    }

    private static class getNotesFromIDAsyncTask extends AsyncTask<Void, Void, Notes []> {
        private MyDao mAsyncTaskDao;
        private int noteID;

        getNotesFromIDAsyncTask(MyDao dao,int noteID) {
            mAsyncTaskDao = dao;
            this.noteID = noteID;
        }

        @Override
        protected Notes [] doInBackground(Void... voids) {
            Notes [] NumberOfNotes = mAsyncTaskDao.getNotesFromID(noteID);
            return NumberOfNotes;
        }

    }


}
