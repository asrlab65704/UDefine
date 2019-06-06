package com.example.udefine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class NoteListAdapter extends
        RecyclerView.Adapter<NoteListAdapter.NoteListHolder> {

    // TODO: This is testing data, should be replace later.
    private final LinkedList<String> mNoteTitleList;
    private final LinkedList<String> mNoteTimeList;
    private final LinkedList<String> mNoteTagList;
    private LayoutInflater mInflater;

    public NoteListAdapter(Context context,
                           LinkedList<String> titleList,
                           LinkedList<String> timeList,
                           LinkedList<String> tagList) {
        mInflater = LayoutInflater.from(context);

        // TODO: This is testing data, should be replace later.
        this.mNoteTitleList = titleList;
        this.mNoteTimeList = timeList;
        this.mNoteTagList = tagList;
    }

    @Override
    public NoteListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.note_list_item, parent, false);
        return new NoteListHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteListHolder noteListHolder, int position) {

        // TODO: This is testing data, should be replace later.
        String mTitleCurrent = mNoteTitleList.get(position);
        String mTimeCurrent = mNoteTimeList.get(position);
        String mTagCurrent = mNoteTagList.get(position);

        noteListHolder.NoteTitleView.setText(mTitleCurrent);
        noteListHolder.NoteTimeView.setText(mTimeCurrent);
        noteListHolder.NoteTagView.setText(mTagCurrent);
    }

    @Override
    public int getItemCount() {
        return mNoteTitleList.size();
    }

    class NoteListHolder extends RecyclerView.ViewHolder {
        public final TextView NoteTitleView;
        public final TextView NoteTimeView;
        public final TextView NoteTagView;
        final NoteListAdapter mAdapter;

        public NoteListHolder(View itemView, NoteListAdapter adapter) {
            super(itemView);
            NoteTitleView = itemView.findViewById(R.id.note_title);
            NoteTimeView = itemView.findViewById(R.id.note_time);
            NoteTagView = itemView.findViewById(R.id.note_tag);
            this.mAdapter = adapter;
        }
    }

}


