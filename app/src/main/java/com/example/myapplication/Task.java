package com.example.myapplication;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private int mID;

//    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    @ColumnInfo(name = "weight")
    private String mWeight;

    @ColumnInfo(name = "type")
    private String mType;

    public Task(@NonNull String task, String weight, String type) {
        this.mID = 0;
        this.mTask = task;
        this.mWeight = weight;
        this.mType = type;
    }

    public void setID(int id){mID = id;}

    public String getTask(){return this.mTask;}

    public String getWeight(){return this.mWeight;}

    public String getType(){return this.mType;}

    public int getID(){return this.mID;}
}
