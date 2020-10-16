package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    @ColumnInfo(name = "weight")
    private String mWeight;

    public Task(@NonNull String task, String weight) {
        this.mTask = task;
        this.mWeight = weight;
    }

    public String getTask(){return this.mTask;}

    public String getWeight(){return this.mWeight;}
}
