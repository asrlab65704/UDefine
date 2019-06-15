package com.example.udefine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.udefine.Database.LayoutList;
import com.example.udefine.Database.Layouts;
import com.example.udefine.Database.ViewModel;

import java.util.ArrayList;

public class NewLayout extends AppCompatActivity {
    private widgetManager widgetsManager;
    private LinearLayout parentLinear;
    private EditText mNewLayoutName;
    private ArrayList<Integer> component_list = new ArrayList<Integer>();
    private ArrayList<String> component_title = new ArrayList<String>();
    public static final String component_list_passing_key = "COMPONENT_LIST_KEY";
    public static final String component_title_passing_key = "COMPONENT_TITLE_KEY";
    public static final int TEXT_REQUEST = 1;

    private ViewModel mViewModel;

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

                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(component_list_passing_key, component_list);
                bundle.putStringArrayList(component_title_passing_key, component_title);

                Intent intent = new Intent(NewLayout.this, LayoutElement.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        mNewLayoutName = findViewById(R.id.new_layout_name);

        // Add default title
        component_list.add(1);
        component_title.add("Title");

        parentLinear = findViewById(R.id.newLayoutLinear);
        widgetsManager = new widgetManager(this, parentLinear,
                getSupportFragmentManager());
        widgetsManager.generate(component_list, component_title);

        // DB data initial
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                widgetsManager.removeAllHashmap();
                parentLinear.removeAllViews();
                component_list = intent.getIntegerArrayListExtra(component_list_passing_key);
                component_title = intent.getStringArrayListExtra(component_title_passing_key);
                widgetsManager.generate(component_list, component_title);
            }
        }
    }

    public void saveLayout(View view) {
        // save LayoutList into DB
        LayoutList new_layoutlist = new LayoutList(mNewLayoutName.getText().toString());
        new_layoutlist.setLayoutID(mViewModel.getLastLayoutID() + 1);
        mViewModel.inserLayoutList(new_layoutlist);

        Toast.makeText(getApplicationContext(), Integer.toString(new_layoutlist.getLayoutID()), Toast.LENGTH_LONG).show();
        // save Layouts into DB
        ArrayList<Layouts> new_layouts = widgetsManager.getLayoutContent(new_layoutlist.getLayoutID());
        mViewModel.insertLayouts(new_layouts);
        finish();
    }

    public void deleteLayoutElement(View view) {
        // delete the last layout
        if (component_list.size() > 1) {
            // Remove deleted widget
            component_list.remove(component_list.size() - 1);
            component_title.remove(component_title.size() - 1);
            widgetsManager.removeAllHashmap();
            parentLinear.removeAllViews();
            widgetsManager.generate(component_list, component_title);
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
