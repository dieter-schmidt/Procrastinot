package com.example.myapplication;

import android.view.View;

public interface OnItemClickListener {

    void onItemClick(Task task);
    void onNotesClick(Task task);
    void onCompleteClick(Task task, View v);
}
