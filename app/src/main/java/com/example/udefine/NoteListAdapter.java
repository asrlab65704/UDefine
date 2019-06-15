package com.example.udefine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.udefine.Database.NoteList;
import com.example.udefine.Database.Notes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NoteListAdapter extends
        RecyclerView.Adapter<NoteListAdapter.NoteListHolder> {

    // DB Data
    private List<NoteList> mNoteList;

    private LayoutInflater mInflater;

    // for adapter context
    private Context context;

    // to EditNote intent
    public static final String EDIT_NOTE_ID =
            "com.example.android.udefine.extra.EDITNOTEID";

    // flag for delete mode, false for disable, true for enable
    private boolean del_flag = false;
    private boolean delete_note[] = new boolean[200];

    public NoteListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);

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
        String mTitleCurrent = mNoteList.get(position).getTitle();
        String mTimeCurrent = mNoteList.get(position).getTime();
        String mTagCurrent = mNoteList.get(position).getTag();

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
        if (mNoteList != null)
            return mNoteList.size();
        else
            return 0;
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
                    } else {
                        Intent intent = new Intent(context, EditNote.class);
                        // send note ID to EditNote Activity
                        intent.putExtra(EDIT_NOTE_ID, 1);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    // function for DB control
    public void setNoteList(List<NoteList> noteLists) {
        this.mNoteList = noteLists;
        notifyDataSetChanged();
    }

    // function for delete mode
    public boolean get_del_mode() { return del_flag; }
    public void enable_del_mode() { del_flag = true; }
    public void disable_del_mode() { del_flag = false; }

    public ArrayList<Integer> get_del_note_id()
    {
        ArrayList<Integer> note_id_del_list = new ArrayList<>();

        for (int i = 0; i < mNoteList.size(); i++) {
            if (delete_note[i])
                note_id_del_list.add(mNoteList.get(i).getNoteID());
        }
        return note_id_del_list;
    }

    public void reset_note_list()
    {
        Arrays.fill(delete_note, Boolean.FALSE);
        notifyDataSetChanged();
    }
}