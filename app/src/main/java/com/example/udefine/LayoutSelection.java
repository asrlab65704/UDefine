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
import android.widget.Toast;

import com.example.udefine.Database.LayoutList;
import com.example.udefine.Database.NoteList;
import com.example.udefine.Database.ViewModel;

import java.util.LinkedList;
import java.util.List;

public class LayoutSelection extends AppCompatActivity {

    public static final String layout_id_key = "LAYOUT_ID_KEY";

    // shared preference
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "Udefine.sharedPrefs";
    private final String LAYOUT_ID = "Layout_ID";
    private int layout_id;

    // TODO:DB data
    private ViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private LayoutSelectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_selection);

        // Default toolbar setting
        Toolbar toolbar = findViewById(R.id.layout_selection_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LayoutSelection.this, NewLayout.class);
                startActivity(intent);
            }
        });

        // DB parameter initialize
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mViewModel.getAllLayoutList().observe(this, new Observer<List<LayoutList>>() {
            @Override
            public void onChanged(@Nullable final List<LayoutList> layoutLists) {
                mAdapter.setLayoutList(layoutLists);

                // set default layout
                mAdapter.setDefaultSelection(layout_id);
            }
        });

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.layout_selection_recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new LayoutSelectionAdapter(this);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // shared preference
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        layout_id = mPreferences.getInt(LAYOUT_ID, 0);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mAdapter.notifyDataSetChanged();
    }

    public void edit_layout(View view)
    {
        Intent intent = new Intent(LayoutSelection.this, EditLayout.class);
        intent.putExtra(layout_id_key, mAdapter.getSelectedLayoutID());
        //Toast.makeText(getApplicationContext(), Integer.toString(mAdapter.getSelectedLayoutID()), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void del_layout(View view)
    {
        int del_layout_id = mAdapter.getSelectedLayoutID();
        if (del_layout_id != -1) {
            mViewModel.deleteLayout(del_layout_id);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No layout can be deleted.", Toast.LENGTH_LONG).show();
        }
    }

    public void default_layout(View view)
    {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(LAYOUT_ID, mAdapter.getSelectedLayoutID());
        preferencesEditor.apply();
        Toast.makeText(getApplicationContext(), "Set " + mAdapter.setDefaultHint()
                + " as default layout.", Toast.LENGTH_LONG).show();
    }
}
