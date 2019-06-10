package com.example.udefine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class NewLayout extends AppCompatActivity {
    private widgetManager widgetsManager;
    private LinearLayout parentLinear;
    private ArrayList<Integer> component_list = new ArrayList<Integer>();
    private ArrayList<String> component_title = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_layout);
        Toolbar toolbar = findViewById(R.id.new_layout_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewLayout.this, LayoutElement.class);
//                String message = mMessageEditText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });


        // Add default title
        component_list.add(1);
        component_title.add("Title");

        // TODO: Remove testing widget
        component_list.add(2);
        component_title.add("Time");

        parentLinear = findViewById(R.id.newLayoutLayout);
        widgetsManager = new widgetManager(this, parentLinear,
                getSupportFragmentManager());
        widgetsManager.generate(component_list, component_title);

    }

    public void saveLayout(View view) {
        // TODO: save layout to db
        finish();
    }

    public void deleteLayoutElement(View view) {
        // delete the last layout
        if (component_list.size() > 1) {
            parentLinear.removeViewAt(component_list.size());
            // Remove deleted widget
            component_list.remove(component_list.size() - 1);
            component_title.remove(component_title.size() - 1);
        } else {
            String warning_msg = "You can not remove Title";
            Toast warning = Toast.makeText(NewLayout.this,
                    warning_msg, Toast.LENGTH_SHORT);
            warning.show();
        }
    }

    public void cancelLayout(View view) {
        finish();
    }
}
