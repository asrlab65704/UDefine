package com.example.udefine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.udefine.Database.NoteList;
import com.example.udefine.Database.Notes;
import com.example.udefine.Database.ViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // DB data
    private ViewModel mViewModel;

    // shared preference
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "Udefine.sharedPrefs";
    private final String LAYOUT_ID = "Layout_ID";

    private RecyclerView mRecyclerView;
    private NoteListAdapter mAdapter;
    private Button mCancelBtn;
    private Button mDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // shared preference
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Default toolbar setting
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Default FAB setting
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNote.class);
                startActivity(intent);
            }
        });

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.note_list_recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new NoteListAdapter(this);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // DB parameter initialize
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.getAllNoteList().observe(this, new Observer<List<NoteList>>() {
            @Override
            public void onChanged(@Nullable final List<NoteList> noteLists) {
                mAdapter.setNoteList(noteLists);
            }
        });

        // Get buttons used in delete mode
        mCancelBtn = findViewById(R.id.del_note_cancel_btn);
        mDeleteBtn = findViewById(R.id.del_note_del_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Go to LayoutSelectionActivity
        if (id == R.id.action_layout_selection) {
            Intent intent = new Intent(MainActivity.this, LayoutSelection.class);
            startActivity(intent);
        } else if (id == R.id.action_delete_note) {
            mAdapter.enable_del_mode();

            // show up the button
            mDeleteBtn.setVisibility(View.VISIBLE);
            mCancelBtn.setVisibility(View.VISIBLE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void del_note_cancel(View view)
    {
        // hidden btn
        mCancelBtn.setVisibility(View.INVISIBLE);
        mDeleteBtn.setVisibility(View.INVISIBLE);

        // disable delete mode
        mAdapter.reset_note_list();
        mAdapter.disable_del_mode();
    }

    public void del_note_del(View view)
    {
        ArrayList<Integer> note_id_del_list = mAdapter.get_del_note_id();

        // hidden btn
        mCancelBtn.setVisibility(View.INVISIBLE);
        mDeleteBtn.setVisibility(View.INVISIBLE);

        // delete note
        if (note_id_del_list != null)
            for (int i = 0; i < note_id_del_list.size(); i++) {
                mViewModel.deleteNote(note_id_del_list.get(i));
            }
        mAdapter.disable_del_mode();
        mAdapter.reset_note_list();
    }
}