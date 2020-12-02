package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DayEntryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DayEntry dayEntry);

    @Query("DELETE FROM day_entry_table")
    void deleteAll();

    @Delete
    void deleteDayEntry(DayEntry dayEntry);

    @Query("SELECT * from day_entry_table ORDER BY date ASC")
    LiveData<List<DayEntry>> getAllDayEntries();
    //List<DayEntry> getAllDayEntries();

    @Query("SELECT * from day_entry_table LIMIT 1")
    DayEntry[] getAnyDayEntry();

//    @Query("UPDATE task_table SET task = :taskName WHERE ID = :taskID")
//    void updateName(int taskID, String taskName);

    @Query("UPDATE day_entry_table SET journal_entry = :journalEntry WHERE date = :date")
    void updateJournalEntry(String date, String journalEntry);

    @Query("UPDATE day_entry_table SET notes = :dayEntryNotes WHERE date = :date")
    void updateNotes(String date, String dayEntryNotes);

//    @Query("UPDATE task_table SET completion_status = :taskStatus where ID = :taskID")
//    void updateCompletionStatus(int taskID, boolean taskStatus);

}