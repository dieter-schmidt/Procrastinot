package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private LiveData<List<Task>> mAllTasks;

    public TaskViewModel(Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void insert(Task task) { mRepository.insert(task); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteTask(Task task) {mRepository.deleteTask(task);}

    Task[] getMatchedTasksByName(String taskName) {return mRepository.getMatchedTasksByName(taskName);}

    public void updateName(int taskID, String taskName) {mRepository.updateName(taskID, taskName);}

    public void updateWeight(int taskID, String taskWeight) {mRepository.updateWeight(taskID, taskWeight);}

    public void updateType(int taskID, String taskType) {mRepository.updateType(taskID, taskType);}

    public void updateNotes(int taskID, String taskNotes) {mRepository.updateType(taskID, taskNotes);}


}
