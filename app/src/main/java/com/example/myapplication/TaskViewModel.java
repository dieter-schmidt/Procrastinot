package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private LiveData<List<Task>> mAllTasks;
    //private LiveData<List<Task>> mMatchedTasks;

    public TaskViewModel(Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
        //mMatchedTasks = mRepository.getMatchedTasksByDate(CalendarConverter.fromCalendar(Calendar.getInstance()));
    }

    LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void insert(Task task) { mRepository.insert(task); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteTask(Task task) {mRepository.deleteTask(task);}

    Task[] getMatchedTasksByName(String taskName) {return mRepository.getMatchedTasksByName(taskName);}

    Task[] getMatchedTasksByDate(String date) {return mRepository.getMatchedTasksByDate(date);}

    public void updateName(int taskID, String taskName) {mRepository.updateName(taskID, taskName);}

    public void updateWeight(int taskID, String taskWeight) {mRepository.updateWeight(taskID, taskWeight);}

    public void updateType(int taskID, String taskType) {mRepository.updateType(taskID, taskType);}

    public void updateNotes(int taskID, String taskNotes) {mRepository.updateNotes(taskID, taskNotes);}

    public void updateCompletionStatus(int taskID, boolean taskStatus) {mRepository.updateCompletionStatus(taskID, taskStatus);}

}
