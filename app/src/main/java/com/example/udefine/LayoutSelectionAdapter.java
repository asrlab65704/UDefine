package com.example.udefine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class LayoutSelectionAdapter extends
        RecyclerView.Adapter<LayoutSelectionAdapter.LayoutSelectionHolder> {

    // TODO: This is testing data, should be replace later.
    private final LinkedList<String> mLayoutList;

    private LayoutInflater mInflater;

    // Use for single selection checked
    private int lastSelectedPosition = -1;

    public LayoutSelectionAdapter(Context context,
                           LinkedList<String> LayoutList) {
        mInflater = LayoutInflater.from(context);

        // TODO: This is testing data, should be replace later.
        this.mLayoutList = LayoutList;
    }

    @Override
    public LayoutSelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_selection_item, parent, false);
        return new LayoutSelectionHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(@NonNull LayoutSelectionHolder noteListHolder, int position) {

        // TODO: This is testing data, should be replace later.
        String mTitleCurrent = mLayoutList.get(position);
        noteListHolder.LayoutView.setText(mTitleCurrent);

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        noteListHolder.LayoutView.setChecked(lastSelectedPosition == position);
    }

    // TODO: This is testing data, should be replace later.
    @Override
    public int getItemCount() {
        return mLayoutList.size();
    }

    class LayoutSelectionHolder extends RecyclerView.ViewHolder {
        public final RadioButton LayoutView;
        final LayoutSelectionAdapter mAdapter;

        public LayoutSelectionHolder(View itemView, LayoutSelectionAdapter adapter) {
            super(itemView);
            LayoutView = itemView.findViewById(R.id.layout_select);

            this.mAdapter = adapter;

            LayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

}


