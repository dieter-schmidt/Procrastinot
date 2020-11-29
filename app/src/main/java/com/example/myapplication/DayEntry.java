package com.example.myapplication;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.Calendar;

@Entity(tableName = "day_entry_table")
@TypeConverters(CalendarConverter.class)
public class DayEntry {

//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    @ColumnInfo(name = "ID")
//    private int mID;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "journal_entry")
    private String mJournalEntry;

    public DayEntry(String date, String notes, String journalEntry) {
        this.mNotes = notes;
        this.mJournalEntry = journalEntry;
        this.mDate = date;
    }

    public String getDate() {
        return this.mDate;
    }
    public String getNotes(){
        return this.mNotes;
    }
    public String getJournalEntry(){
        return this.mJournalEntry;
    }

}
