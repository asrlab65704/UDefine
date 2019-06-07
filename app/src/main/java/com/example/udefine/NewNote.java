package com.example.udefine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NewNote extends AppCompatActivity {

    private Spinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // tag spinner settings
        final String[] tag_colors = {
                "Select Tag", "#00ff00", "#ffff00", "#ff751a", "#ff0000",
                "#993399", "#6666ff"};
        mSpinner = findViewById(R.id.tag_spinner);

        ArrayList<tagItemStateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < tag_colors.length; i++) {
            tagItemStateVO stateVO = new tagItemStateVO();
            stateVO.setTitle(tag_colors[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        tagListAdapter myAdapter = new tagListAdapter(NewNote.this, 0,
                listVOs);
        mSpinner.setAdapter(myAdapter);

        /*
         *  1. load selected layout
         *     - load default layout
         *     - TextView - title
         *     - Widget
         *  2. buttons
         *     - save
         *     - cancel
         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(NewNote.this, LayoutSelection.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void datePicker(View view) {
    }

    public void timePicker(View view) {
    }
}
