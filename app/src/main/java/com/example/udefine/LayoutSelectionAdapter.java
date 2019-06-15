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

import com.example.udefine.Database.LayoutList;
import com.example.udefine.Database.NoteList;

import java.util.LinkedList;
import java.util.List;

public class LayoutSelectionAdapter extends
        RecyclerView.Adapter<LayoutSelectionAdapter.LayoutSelectionHolder> {

    // TODO: This is testing data, should be replace later.
    private List<LayoutList> mLayoutList;

    private LayoutInflater mInflater;
    private Context context;

    // Use for single selection checked
    private int lastSelectedPosition = -1;

    public LayoutSelectionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public LayoutSelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_selection_item, parent, false);
        return new LayoutSelectionHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutSelectionHolder noteListHolder, int position) {

        String mTitleCurrent = mLayoutList.get(position).getLayoutName();
        noteListHolder.LayoutView.setText(mTitleCurrent);

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        noteListHolder.LayoutView.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        if (mLayoutList != null)
            return mLayoutList.size();
        else
            return 0;
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

    // function for DB control
    public void setLayoutList(List<LayoutList> layoutLists)
    {
        this.mLayoutList = layoutLists;
        notifyDataSetChanged();
    }

    // return Selected LayoutID
    public int getSelectedLayoutID()
    {
        if (getItemCount() != 0) {
            return mLayoutList.get(lastSelectedPosition).getLayoutID();
        } else {
            return -1;
        }
    }

    // return lastSelectedPosition
    public int getSelectedPosition() { return lastSelectedPosition; }
}


