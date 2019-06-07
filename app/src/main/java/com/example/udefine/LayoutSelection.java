package com.example.udefine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.LinkedList;

public class LayoutSelection extends AppCompatActivity {

    // TODO:testing data, should be remove later.
    private final LinkedList<String> mLayoutList = new LinkedList<>();

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
//                String message = mMessageEditText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        // TODO: Testing data. Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mLayoutList.addLast("Layout: " + i);
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.layout_selection_recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new LayoutSelectionAdapter(this, mLayoutList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void edit_layout(View view) {
        Intent intent = new Intent(LayoutSelection.this, EditLayout.class);
//                String message = mMessageEditText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
