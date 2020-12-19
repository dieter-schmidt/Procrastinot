package com.example.myapplication;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")//,
//        foreignKeys = @ForeignKey(entity = DayEntry.class,
//        parentColumns = "date",
//        childColumns = "date"))//,onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private int mID;

    @ColumnInfo(name = "date")
    private String mDate;

//    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    @ColumnInfo(name = "weight")
    private String mWeight;

    @ColumnInfo(name = "type")
    private String mType;

    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "color")
    @NonNull
    private String mColor;

    @ColumnInfo(name = "completion_status")
    private boolean mCompletionStatus;

    public Task(@NonNull String task, String weight, String type, String notes, String date, String color) {
        this.mID = 0;
        this.mTask = task;
        this.mWeight = weight;
        this.mType = type;
        this.mNotes = notes;
        this.mCompletionStatus = false;
        this.mDate = date;
        this.mColor = color;
    }

    public void setCompletionStatus(boolean status){mCompletionStatus = status;}

    public void setID(int id){mID = id;}

    public void setDate(String date){mDate = date;}

    public void setColor(String color){mColor = color;}

    public String getTask(){return this.mTask;}

    public String getWeight(){return this.mWeight;}

    public String getType(){return this.mType;}

    public String getNotes(){return this.mNotes;}

    public String getColor() {return this.mColor;}

    public String getDate(){return this.mDate;}

    public boolean getCompletionStatus(){return this.mCompletionStatus;}

    public int getID(){return this.mID;}
}
