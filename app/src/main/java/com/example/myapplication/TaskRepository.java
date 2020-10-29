package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert (Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }
    public void deleteAll()  {
        new deleteAlltasksAsyncTask(mTaskDao).execute();
    }
    public void deleteTask(Task task)  {
        new deletetaskAsyncTask(mTaskDao).execute(task);
    }
    Task[] getMatchedTasksByName(String taskName) {
        getMatchedTasksByNameAsyncTask asyncTask = new getMatchedTasksByNameAsyncTask(mTaskDao);
        asyncTask.execute(taskName);
        return asyncTask.matches;
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getMatchedTasksByNameAsyncTask extends AsyncTask<String, Void, Void> {
        private TaskDao mAsyncTaskDao;
        Task[] matches;

        getMatchedTasksByNameAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            matches = mAsyncTaskDao.getMatchedTasksByName(params[0]);
            return null;
        }
    }

    private static class deleteAlltasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteAlltasksAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deletetaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deletetaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }
}
