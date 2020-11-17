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

    public void updateName(int taskID, String taskName)
    {
        UpdateParams params = new UpdateParams(taskID, taskName);
        new updateNameAsyncTask(mTaskDao).execute(params);
        //mTaskDao.updateName(taskID, taskName);
    }
    public void updateWeight(int taskID, String taskWeight)
    {
        UpdateParams params = new UpdateParams(taskID, taskWeight);
        new updateWeightAsyncTask(mTaskDao).execute(params);
        //mTaskDao.updateWeight(taskID, taskWeight);
    }
    public void updateType(int taskID, String taskType)
    {
        UpdateParams params = new UpdateParams(taskID, taskType);
        new updateTypeAsyncTask(mTaskDao).execute(params);
        //mTaskDao.updateType(taskID, taskType);
    }

    public void updateNotes(int taskID, String taskNotes)
    {
        UpdateParams params = new UpdateParams(taskID, taskNotes);
        new updateNotesAsyncTask(mTaskDao).execute(params);
        //mTaskDao.updateType(taskID, taskType);
    }

    public void updateCompletionStatus(int taskID, boolean taskStatus)
    {
        UpdateParams params = new UpdateParams(taskID, taskStatus);
        new updateStatusAsyncTask(mTaskDao).execute(params);
        //mTaskDao.updateType(taskID, taskType);
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

    private static class UpdateParams {
        int id;
        String sField;
        boolean bField;

        UpdateParams(int id, String field) {
            this.id = id;
            this.sField = field;
        }
        UpdateParams(int id, boolean field) {
            this.id = id;
            this.bField = field;
        }
    }

    private static class updateWeightAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateWeightAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateWeight(params[0].id, params[0].sField);
            return null;
        }
    }

    private static class updateNotesAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateNotesAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateNotes(params[0].id, params[0].sField);
            return null;
        }
    }

    private static class updateNameAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateNameAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateName(params[0].id, params[0].sField);
            return null;
        }
    }

    private static class updateStatusAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateStatusAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateCompletionStatus(params[0].id, params[0].bField);
            return null;
        }
    }

    private static class updateTypeAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateTypeAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateType(params[0].id, params[0].sField);
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
