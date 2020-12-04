package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private final LayoutInflater mInflater;
    private List<Task> mTasks; // Cached copy of words
    private final OnItemClickListener listener;

    TaskListAdapter(Context context, OnItemClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            holder.taskItemView.setText(current.getTask());
            holder.typeItemView.setText(current.getType());
            //holder.notesItemView.setText(current.getNotes());
            bind(current, holder, listener);
            //change background color depending on weight
            Log.e("TEST", current.getWeight());
            Log.e("TEST", current.getDate());
            if (current.getWeight().equals("Hard")) {
                holder.taskItemView.setBackgroundColor(mInflater.getContext().getResources().getColor(R.color.colorTaskHard));
            }
            else if (current.getWeight().equals("Medium")){
                holder.taskItemView.setBackgroundColor(mInflater.getContext().getResources().getColor(R.color.colorTaskMedium));
            }
            else if (current.getWeight().equals("Easy")) {
                holder.taskItemView.setBackgroundColor(mInflater.getContext().getResources().getColor(R.color.colorTaskEasy));
                holder.taskItemView.setTextColor(Color.BLACK);
            }

            if (current.getCompletionStatus() == true){
                holder.checkBoxView.setChecked(true);
                holder.taskItemView.setAlpha(0.2f);
                holder.notesItemView.setAlpha(0.2f);
                holder.typeItemView.setAlpha(0.2f);
            }
            else {
                holder.checkBoxView.setChecked(false);
                holder.taskItemView.setAlpha(1f);
                holder.notesItemView.setAlpha(1f);
                holder.typeItemView.setAlpha(1f);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.taskItemView.setText("No Task");
        }
    }

    public void bind(final Task task, TaskViewHolder holder, final OnItemClickListener listener) {
        holder.taskItemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(task);
            }
        });
        holder.notesItemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onNotesClick(task);
            }
        });
        holder.checkBoxView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onCompleteClick(task, v);
            }
        });
    }

    public Task getTaskAtPosition (int position) {
        return mTasks.get(position);
    }

    void setTasks(List<Task> tasks){
        //Log.e("ADAPTER", mTasks.get(0).getDate());
        mTasks = tasks;
        notifyDataSetChanged();
    }



    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskItemView;
        private final TextView typeItemView;
        private final TextView notesItemView;
        private final CheckBox checkBoxView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            typeItemView = itemView.findViewById(R.id.type_view);
            notesItemView = itemView.findViewById(R.id.notes_view);
            checkBoxView = itemView.findViewById(R.id.complete_checkbox);
        }
    }
}
