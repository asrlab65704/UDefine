package com.example.udefine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LayoutElement extends AppCompatActivity {
    private widgetManager widgetsManager;
    private LinearLayout mElementPreview;
    private Spinner mElementSpinner;
    private TextView mWidgetTitle;
    private ArrayList<Integer> component_list = new ArrayList<Integer>();
    private ArrayList<String> component_title = new ArrayList<String>();
    public static final String component_list_passing_key = "COMPONENT_LIST_KEY";
    public static final String component_title_passing_key = "COMPONENT_TITLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_element);

        // Intent setting
        Intent intent = getIntent();
        component_list = intent.getIntegerArrayListExtra(component_list_passing_key);
        component_title = intent.getStringArrayListExtra(component_title_passing_key);

        // toolbar setting
        Toolbar toolbar = findViewById(R.id.layout_element_toolbar);
        setSupportActionBar(toolbar);

        mElementPreview = findViewById(R.id.element_preview);
        mWidgetTitle = findViewById(R.id.element_title);

        // attach adapter to widget type spinner
        mElementSpinner = findViewById(R.id.widget_type_spinner);
        ArrayAdapter<CharSequence> widget_type_list = ArrayAdapter.createFromResource(
                LayoutElement.this,
                R.array.widget_type_spinner,
                android.R.layout.simple_spinner_dropdown_item);
        mElementSpinner.setAdapter(widget_type_list);

        mElementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mElementPreview.removeAllViews();

                mElementPreview.setBackground(getResources().getDrawable(R.drawable.note_list_layout));
                widgetsManager = new widgetManager(LayoutElement.this, mElementPreview,
                        getSupportFragmentManager());

                widgetsManager.generate(position + 1, mWidgetTitle.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void save_element(View view) {

        component_list.add(mElementSpinner.getSelectedItemPosition() + 1);
        if (mWidgetTitle.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please insert title", Toast.LENGTH_LONG).show();
            return;
        }
        component_title.add(mWidgetTitle.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(component_list_passing_key, component_list);
        bundle.putStringArrayList(component_title_passing_key, component_title);

        Intent intent = new Intent(LayoutElement.this, NewLayout.class);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel_element(View view) {
        finish();
    }
}
