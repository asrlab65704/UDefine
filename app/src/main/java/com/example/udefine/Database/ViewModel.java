package com.example.udefine.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<Notes>> mAllNotes;
    private LiveData<List<NoteList>> mAllNoteList;
    private LiveData<List<Layouts>> mAllLayouts;
    private LiveData<List<LayoutList>> mAllLayoutList;

    /*Constructor*/
    public ViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllNoteList = mRepository.getAllNoteList();
        mAllNotes = mRepository.getAllNotes();
        mAllLayoutList = mRepository.getAllLayoutList();
        mAllLayouts = mRepository.getAllLayouts();
    }

    /*Getter*/
    public  LiveData<List<NoteList>> getAllNoteList() { return mAllNoteList; }
    public LiveData<List<LayoutList>> getAllLayoutList() { return mAllLayoutList; }
    public LiveData<List<Notes>> getAllNotes() { return mAllNotes; }
    public LiveData<List<Layouts>> getAllLayouts() { return mAllLayouts; }

    /*Wrapper*/
    public void insertNote(NoteList noteList,Notes notes){
        mRepository.insertNotelist(noteList);
        mRepository.insertNote(notes);
    }

    public void insertNoteList(NoteList noteList){
        mRepository.insertNotelist(noteList);
    }

    /***
     * 用來輸入一筆note中的所有欄位
     * 每個欄位即是一個Notes
     */
    public void insertNotes(ArrayList<Notes> notes){
        for(Notes note:notes){
            mRepository.insertNote(note);
        }
    }

    public void insertLayout(LayoutList layoutList,Layouts layouts){
        mRepository.insertLayoutlist(layoutList);
        mRepository.insertLayouts(layouts);
    }

    public void inserLayoutList(LayoutList layoutList){
        mRepository.insertLayoutlist(layoutList);
    }

    public void insertLayouts(ArrayList<Layouts> layouts){
        for(Layouts layout:layouts){
            mRepository.insertLayouts(layout);
        }
    }

    public int getLastNoteID(){
        return mRepository.getLastNoteID();
    }

    public int getLastLayoutID(){
        return mRepository.getLastLayoutID();
    }

    public int getNumberofNote(){
        return mRepository.getNumberOfNotes();
    }

    public Notes[] getNotesFromNoteID(int id){return mRepository.getNotesFromNoteID(id);}

    public void deleteNote(int noteID){
        mRepository.deleteNote(noteID);
        mRepository.deleteNoteList(noteID);
    }

    public void deleteLayout(int layoutID){
        mRepository.deleteLayoutList(layoutID);
        mRepository.deleteLayout(layoutID);
    }

    public void updateNote(Notes notes)
    {
        mRepository.updateNote(notes);
    }

    public void updateNoteList(NoteList noteList)
    {
        mRepository.updateNoteList(noteList);
    }

}