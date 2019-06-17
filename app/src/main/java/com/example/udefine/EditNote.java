package com.example.udefine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.udefine.Database.Layouts;
import com.example.udefine.Database.NoteList;
import com.example.udefine.Database.Notes;
import com.example.udefine.Database.ViewModel;

import java.util.ArrayList;

public class EditNote extends AppCompatActivity {
    // from EditNote intent
    public static final String EDIT_NOTE_ID =
            "com.example.android.udefine.extra.EDITNOTEID";
    private widgetManager widgetsManager;
    private ViewModel mViewModel;
    private int noteId, layoutId;
    private Layouts layout_list[];
    private ArrayList<Integer> component_type = new ArrayList<Integer>();
    private ArrayList<String> component_title = new ArrayList<String>();

    // parameter for initial
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "Udefine.sharedPrefs";
    private final String LAYOUT_ID = "Layout_ID";
    private int defaultLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        LinearLayout parentLinear = findViewById(R.id.editNoteLayout);
        widgetsManager = new widgetManager(this, parentLinear,
                getSupportFragmentManager());

        // get set layout id
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        defaultLayoutId = mPreferences.getInt(LAYOUT_ID, 0);

        // get noteID from main activity
        Intent intent = getIntent();
        noteId = intent.getIntExtra(EDIT_NOTE_ID, 0);
        Log.d("editnote", Integer.toString(noteId));

        // get layout ID from note ID

        layoutId = mViewModel.getLayoutIDFromNoteID(noteId);
        Log.d("editnote", Integer.toString(layoutId));

        // get layout list
        layout_list = mViewModel.getLayoutsFromLayoutID(layoutId);
        // iterate layout_list to get widget type and title
        for(int i = 0; i < layout_list.length; ++i) {
            component_type.add(layout_list[i].getFormat());
            component_title.add(layout_list[i].getLayoutName());
        }

        // generate layout
        widgetsManager.generate(component_type, component_title);

        //fill in content
        Notes[] notes = mViewModel.getNotesFromNoteID(noteId);
        widgetsManager.fillContentToWidget(notes);
    }

    public void saveNote(View view) {
        ArrayList<Notes> content = widgetsManager.getNoteContent(noteId);
        // TODO: update to DB
        //取得更新後的NoteList
        ArrayList<String> updatedNoteListContent = widgetsManager.getNoteTitleTimeTag();
        String noteTitle = updatedNoteListContent.get(0);
        String time = updatedNoteListContent.get(1) == null ? null:
                updatedNoteListContent.get(1);
        String tag = updatedNoteListContent.get(2) == null ? null:
                updatedNoteListContent.get(2);
        NoteList updatedNoteList = new NoteList(noteTitle, time, tag, layoutId);
        updatedNoteList.setNoteID(noteId);
        Log.d("db", tag);
        mViewModel.updateNoteList(updatedNoteList);

        //取得更新後的ArrayList<Notes>
        mViewModel.editNote(noteId,content);
        finish();
    }

    public void cancelNote(View view) {
        finish();
    }

    public void deleteNote(View view) {
        mViewModel.deleteNote(noteId);
        finish();
    }
}
