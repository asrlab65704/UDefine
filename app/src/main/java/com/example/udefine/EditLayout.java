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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.udefine.Database.LayoutList;
import com.example.udefine.Database.Layouts;
import com.example.udefine.Database.ViewModel;

import java.util.ArrayList;

public class EditLayout extends AppCompatActivity {
    private widgetManager widgetsManager;
    private LinearLayout parentLinear;
    private ArrayList<Integer> component_list = new ArrayList<Integer>();
    private ArrayList<String> component_title = new ArrayList<String>();
    public static final String component_list_passing_key = "COMPONENT_LIST_KEY";
    public static final String component_title_passing_key = "COMPONENT_TITLE_KEY";
    public static final int TEXT_REQUEST = 1;

    // parameter for initial
    private int layout_id;
    private Layouts[] init_layout;

    // parameter for save layout
    private ViewModel mViewModel;
    public static final String layout_id_key = "LAYOUT_ID_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_layout);
        Toolbar toolbar = findViewById(R.id.edit_layout_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(component_list_passing_key, component_list);
                bundle.putStringArrayList(component_title_passing_key, component_title);

                Intent intent = new Intent(EditLayout.this, LayoutElement.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        // Intent setting
        Intent intent = getIntent();
        layout_id = intent.getIntExtra(layout_id_key, -1);

        // DB data initial
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        init_layout = mViewModel.getLayoutsFromLayoutID(layout_id);

        // extract data from init_layout and insert into component_list
        for (int i = 0; i < init_layout.length; i++) {
            component_title.add(init_layout[i].getLayoutName());
            component_list.add(init_layout[i].getFormat());
        }

        parentLinear = findViewById(R.id.editLayoutLinear);
        widgetsManager = new widgetManager(this, parentLinear,
                getSupportFragmentManager());
        widgetsManager.generate(component_list, component_title);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                parentLinear.removeAllViews();
                widgetsManager.removeAllHashmap();
                component_list = intent.getIntegerArrayListExtra(component_list_passing_key);
                component_title = intent.getStringArrayListExtra(component_title_passing_key);
                widgetsManager.generate(component_list, component_title);
            }
        }
    }

    public void saveLayout(View view) {
        // save Layouts into DB
        if (layout_id != -1) {
            ArrayList<Layouts> new_layouts = widgetsManager.getLayoutContent(layout_id);
            mViewModel.updateLayouts(new_layouts);
        } else {
            Log.w("EditLayout", "something wrong in save Layout.", null);
        }
        finish();
    }

    public void deleteLayoutElement(View view) {
        // delete the last layout
        if (component_list.size() > 1) {
            widgetsManager.removeLastHashmap();
            parentLinear.removeViewAt(component_list.size() - 1);
            // Remove deleted widget
            component_list.remove(component_list.size() - 1);
            component_title.remove(component_title.size() - 1);
        } else {
            String warning_msg = "You can not remove Title";
            Toast warning = Toast.makeText(EditLayout.this,
                    warning_msg, Toast.LENGTH_SHORT);
            warning.show();
        }
    }

    public void cancelLayout(View view) {
        finish();
    }
}
