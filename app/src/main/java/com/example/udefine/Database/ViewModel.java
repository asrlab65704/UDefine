package com.example.udefine.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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
    LiveData<List<NoteList>> getAllNoteList() { return mAllNoteList; }
    LiveData<List<LayoutList>> getAllLayoutList() { return mAllLayoutList; }
    LiveData<List<Notes>> getAllNotes() { return mAllNotes; }
    LiveData<List<Layouts>> getAllLayouts() { return mAllLayouts; }


    /*Wrapper*/
    public void insertNote(NoteList noteList,Notes notes){
        mRepository.insertNotelist(noteList);
        mRepository.insertNote(notes);
    }

    public void insertLayout(LayoutList layoutList,Layouts layouts){
        mRepository.insertLayoutlist(layoutList);
        mRepository.insertLayouts(layouts);
    }

    public int getLastNoteID(){
        return mRepository.getLastNoteID();
    }

    public int getNumberofNote(){
        return mRepository.getNumberOfNotes();
    }

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