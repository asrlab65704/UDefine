package com.example.udefine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.udefine.Database.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class widgetManager {
    private LinearLayout parentLinearLayout;
    private Context mainContext;
    private FragmentManager fragmentManager;
    private int margin;

    /* A hash map record View id and title */
    private HashMap<Integer, String> title_id_list;

    public widgetManager(Context context, LinearLayout parent,
                           FragmentManager fragment) {
        mainContext = context;
        parentLinearLayout = parent;
        fragmentManager = fragment;
        title_id_list = new HashMap<Integer, String>();
        margin = Math.round(16 * mainContext.getResources()
                .getDisplayMetrics().density);
    }

    // widget.generate for one element
    public void generate(int widget_type, String title) {
        switch (widget_type) {
            case 1:
                /* Add editText */
                this.addEditText(title);
                break;
            case 2:
                /* Add DateTime */
                this.addDateTime(title);
                break;
            case 3:
                /* Add Tag */
                this.addTag(title);
                break;
            case 4:
                /* Add PlainText */
                this.addPlainText(title);
                break;
        }
    }

    // widget.generate for multiple element
    public void generate(int componentList[], String[] title) {

        // make sure one element with one title name
        if (componentList.length != title.length)
            return;

        for (int i = 0; i < componentList.length; ++i) {
            switch (componentList[i]) {
                case 1:
                    /* Add editText */
                    this.addEditText(title[i]);
                    break;
                case 2:
                    /* Add DateTime */
                    this.addDateTime(title[i]);
                    break;
                case 3:
                    /* Add Tag */
                    this.addTag(title[i]);
                    break;
                case 4:
                    /* Add PlainText */
                    this.addPlainText(title[i]);
                    break;
            }
        }
    }

    // widget.generate for multiple element with Arraylist<String> type
    public void generate(ArrayList<Integer> componentList, ArrayList<String> title) {

        // make sure one element with one title name
        if (componentList.size() != title.size())
            return;

        for (int i = 0; i < componentList.size(); ++i) {
            switch (componentList.get(i)) {
                case 1:
                    /* Add editText */
                    this.addEditText(title.get(i));
                    break;
                case 2:
                    /* Add DateTime */
                    this.addDateTime(title.get(i));
                    break;
                case 3:
                    /* Add Tag */
                    this.addTag(title.get(i));
                    break;
                case 4:
                    /* Add PlainText */
                    this.addPlainText(title.get(i));
                    break;
            }
        }
    }

    public ArrayList<Notes> getLayoutValue(int noteID) {
        int id;
        String title, content = "";
        ArrayList<Notes> notes = new ArrayList<Notes>();
        Iterator<HashMap.Entry<Integer, String>> iterator =
                title_id_list.entrySet().iterator();

        while (iterator.hasNext()) {
            // get Title and View
            HashMap.Entry<Integer, String> entry = iterator.next();
            id = entry.getKey();
            title = entry.getValue();
            View v = parentLinearLayout.findViewById(id);

            if (v instanceof EditText) {
                EditText e = (EditText)v;
                content = e.getText().toString();
                //Log.d("widget", title + ":" + e.getText().toString());
            } else if (v instanceof Button) {
                Button b = (Button)v;
                String tmp = b.getText().toString();

                // if Date and Time is not set, content remain ""
                if (tmp.equals("Date")) {
                    continue;
                } else if (!tmp.equals("Time")) {
                    content = content + tmp;
                    // date string, continue to get time
                    if (tmp.contains("/")) {
                        content = content + ",";
                        continue;
                    }
                }
                //Log.d("widget", title + ":" + b.getText().toString());
            } else if (v instanceof Spinner) {
                Spinner s = (Spinner)v;
                tagListAdapter adapter = (tagListAdapter)s.getAdapter();
                ArrayList<tagItemStateVO> listState = adapter.getSelectedItems();
                boolean first = false;
                for(int i = 0; i < listState.size(); ++i) {
                    tagItemStateVO tmp = listState.get(i);
                    if (tmp.isSelected()) {
                        if (first) {
                            content = content + ',';
                        } else {
                            first = true;
                        }
                        content = content + tmp.getTitle();
                    }
                }
            }

            // Add Notes object to list
            Log.d("widget", title + " " + content);
            Notes n = new Notes(noteID, title, content);
            notes.add(n);
            content = "";
        }
        return notes;
    }

    public void addEditText(String title) {
        LinearLayout childLayout = new LinearLayout(mainContext);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, margin, 0, margin);

        /* element layout setting */
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /* Title TextView */
        TextView titleTextView = new TextView(mainContext);
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);

        /* EditText */
        EditText editText = new EditText(mainContext);
        editText.setTextSize(18);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setId(View.generateViewId());

        /* Add element to child layout */
        childLayout.addView(titleTextView, titleLayoutParams);
        childLayout.addView(editText, titleLayoutParams);

        /* Add child layout to parent layout */
        parentLinearLayout.addView(childLayout, layoutParams);

        /* Update hashmap list */
        title_id_list.put(editText.getId(), title);
    }

    public void addDateTime(String title) {
        LinearLayout childLayout = new LinearLayout(mainContext);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, margin, 0, margin);
        childLayout.setLayoutParams(layoutParams);

        /* Time Date button layout */
        LinearLayout grandChildLayout = new LinearLayout(mainContext);
        grandChildLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams grandLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        grandChildLayout.setLayoutParams(grandLayoutParams);

        /* Title Layout setting */
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /* Title TextView */
        TextView titleTextView = new TextView(mainContext);
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);

        /* buttons layout setting */
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.weight = 1;

        /* Date button */
        Button dateButton = new Button(mainContext);
        dateButton.setText(R.string.date_button);
        dateButton.setId(View.generateViewId());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment(view);
                newFragment.show(fragmentManager,"datePicker");
            }
        });

        /* Time button */
        Button timeButton = new Button(mainContext);
        timeButton.setText(R.string.time_button);
        timeButton.setId(View.generateViewId());
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment(view);
                newFragment.show(fragmentManager,"timePicker");
            }
        });

        /* Add element to child layout */
        childLayout.addView(titleTextView, titleLayoutParams);
        grandChildLayout.addView(dateButton, buttonLayoutParams);
        grandChildLayout.addView(timeButton, buttonLayoutParams);
        childLayout.addView(grandChildLayout);

        /* Add child layout to parent layout */
        parentLinearLayout.addView(childLayout);

        /* Update hashmap list */
        title_id_list.put(dateButton.getId(), title);
        title_id_list.put(timeButton.getId(), title);
    }

    public void addTag(String title) {
        LinearLayout childLayout = new LinearLayout(mainContext);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, margin, 0, margin);
        childLayout.setLayoutParams(layoutParams);

        /* Title Layout setting */
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /* Title TextView */
        TextView titleTextView = new TextView(mainContext);
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);

        /* spinner layout setting */
        LinearLayout.LayoutParams spinnerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /* spinner */
        Spinner tagSpinner = new Spinner(mainContext, Spinner.MODE_DROPDOWN);
        tagSpinner.setId(View.generateViewId());
        final String[] tag_colors = mainContext.getResources()
                .getStringArray(R.array.tag_color_spinner);

        ArrayList<tagItemStateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < tag_colors.length; i++) {
            tagItemStateVO stateVO = new tagItemStateVO();
            stateVO.setTitle(tag_colors[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        tagListAdapter myAdapter = new tagListAdapter(mainContext, 0,
                listVOs);
        tagSpinner.setAdapter(myAdapter);

        /* Add element to child layout */
        childLayout.addView(titleTextView, titleLayoutParams);
        childLayout.addView(tagSpinner, spinnerLayoutParams);

        /* Add child layout to parent layout */
        parentLinearLayout.addView(childLayout);

        /* Update hashmap list */
        title_id_list.put(tagSpinner.getId(), title);
    }

    public void addPlainText(String title) {

        LinearLayout childLayout = new LinearLayout(mainContext);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, margin, 0, margin);

        /* element layout setting */
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /* Title TextView */
        TextView titleTextView = new TextView(mainContext);
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);

        /* EditText */
        EditText editText = new EditText(mainContext);
        editText.setTextSize(18);
        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                              InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setMinLines(3);
        editText.setId(View.generateViewId());

        /* Add element to child layout */
        childLayout.addView(titleTextView, titleLayoutParams);
        childLayout.addView(editText, titleLayoutParams);

        /* Add child layout to parent layout */
        parentLinearLayout.addView(childLayout, layoutParams);

        /* Update hashmap list */
        title_id_list.put(editText.getId(), title);
    }
}
