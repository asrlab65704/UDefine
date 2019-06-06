package com.example.udefine;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class NoteListAdapter extends
        RecyclerView.Adapter<NoteListAdapter.NoteListHolder> {

    // TODO: This is testing data, should be replace later.
    private final LinkedList<String> mNoteTitleList;
    private final LinkedList<String> mNoteTimeList;
    private final LinkedList<String> mNoteTagList;
    private LayoutInflater mInflater;

    // for adapter context
    private Context context;

    // flag for delete mode, false for disable, true for enable
    private boolean del_flag = false;
    private boolean delete_note[] = new boolean[200];

    public NoteListAdapter(Context context,
                           LinkedList<String> titleList,
                           LinkedList<String> timeList,
                           LinkedList<String> tagList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);

        // TODO: This is testing data, should be replace later.
        this.mNoteTitleList = titleList;
        this.mNoteTimeList = timeList;
        this.mNoteTagList = tagList;

        // fill delete_note with false
        Arrays.fill(delete_note, Boolean.FALSE);
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

        if (delete_note[position]) {
            noteListHolder.NoteItemView.setBackground(
                    context.getResources().getDrawable(
                            R.drawable.note_list_delete));
        } else {
            noteListHolder.NoteItemView.setBackground(
                    context.getResources().getDrawable(
                            R.drawable.note_list_layout));
        }
    }

    @Override
    public int getItemCount() {
        return mNoteTitleList.size();
    }

    class NoteListHolder extends RecyclerView.ViewHolder {
        public final TextView NoteTitleView;
        public final TextView NoteTimeView;
        public final TextView NoteTagView;
        public final LinearLayout NoteItemView;
        final NoteListAdapter mAdapter;

        public NoteListHolder(View itemView, NoteListAdapter adapter) {
            super(itemView);
            NoteTitleView = itemView.findViewById(R.id.note_title);
            NoteTimeView = itemView.findViewById(R.id.note_time);
            NoteTagView = itemView.findViewById(R.id.note_tag);
            NoteItemView = itemView.findViewById(R.id.note_item);
            this.mAdapter = adapter;

            NoteItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (del_flag) {
                        delete_note[getAdapterPosition()] = !delete_note[getAdapterPosition()];
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    // function for delete mode
    public boolean get_del_mode() { return del_flag; }
    public void enable_del_mode() { del_flag = true; }
    public void disable_del_mode() { del_flag = false; }

    public void reset_note_list()
    {
        Arrays.fill(delete_note, Boolean.FALSE);
        notifyDataSetChanged();
    }

    public void del_note()
    {
        int note_num = getItemCount();
        int del_num = 0;
        for (int i = 0; i < note_num; i++) {
            if (delete_note[i]) {
                mNoteTitleList.remove(i - del_num);
                mNoteTimeList.remove(i - del_num);
                mNoteTagList.remove(i - del_num);
                del_num++;
            }
        }
        notifyDataSetChanged();
    }
}
