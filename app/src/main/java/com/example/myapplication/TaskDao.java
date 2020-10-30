package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * from task_table ORDER BY task ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * from task_table LIMIT 1")
    Task[] getAnyTask();

    @Query("SELECT * from task_table WHERE task IN(:taskName)")
    Task[] getMatchedTasksByName(String taskName);

    @Query("UPDATE task_table SET task = :taskName WHERE ID = :taskID")
    void updateName(int taskID, String taskName);

    @Query("UPDATE task_table SET weight = :taskWeight WHERE ID = :taskID")
    void updateWeight(int taskID, String taskWeight);

    @Query("UPDATE task_table SET type = :taskType WHERE ID = :taskID")
    void updateType(int taskID, String taskType);
}
