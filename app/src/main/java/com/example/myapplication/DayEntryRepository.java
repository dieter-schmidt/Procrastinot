package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DayEntryRepository {

    private DayEntryDao mDayEntryDao;
    private LiveData<List<DayEntry>> mAllDayEntries;
//    private List<DayEntry> mAllDayEntries;

    DayEntryRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mDayEntryDao = db.dayEntryDao();
        mAllDayEntries = mDayEntryDao.getAllDayEntries();
    }

    LiveData<List<DayEntry>> getAllDayEntries() { return mAllDayEntries; }
//    List<DayEntry> getAllDayEntries() { return mAllDayEntries; }

    public void updateJournalEntry(String dateString, String updateField)
    {
        UpdateParams params = new UpdateParams(dateString, updateField);
        new updateJournalEntryAsyncTask(mDayEntryDao).execute(params);
        //mTaskDao.updateType(taskID, taskType);
    }

    public void updateNotes(String dateString, String updateField)
    {
        UpdateParams params = new UpdateParams(dateString, updateField);
        new updateNotesAsyncTask(mDayEntryDao).execute(params);
        //mTaskDao.updateType(taskID, taskType);
    }

    public void insert (DayEntry dayEntry) {
        new insertAsyncTask(mDayEntryDao).execute(dayEntry);
    }
    public void deleteAll()  {
        new deleteAllDayEntriesAsyncTask(mDayEntryDao).execute();
    }
    public void deleteDayEntry(DayEntry dayEntry)  { new deleteDayEntryAsyncTask(mDayEntryDao).execute(dayEntry); }

    DayEntry getMatchedDayEntryByDate(String date) throws ExecutionException, InterruptedException {
        DayEntryRepository.getMatchedDayEntryByDateAsyncTask asyncTask = new DayEntryRepository.getMatchedDayEntryByDateAsyncTask(mDayEntryDao);
        asyncTask.execute(date).get();
        return asyncTask.match;
    }

    private static class getMatchedDayEntryByDateAsyncTask extends AsyncTask<String, Void, Void> {
        private DayEntryDao mAsyncDayEntryDao;
        DayEntry match;

        getMatchedDayEntryByDateAsyncTask(DayEntryDao dao) {
            mAsyncDayEntryDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            match = mAsyncDayEntryDao.getMatchedDayEntryByDate(params[0]);
            return null;
        }

    }

    private static class insertAsyncTask extends AsyncTask<DayEntry, Void, Void> {

        private DayEntryDao mAsyncTaskDao;

        insertAsyncTask(DayEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DayEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateParams {
        String dateString;
        String updateField;

        UpdateParams(String date, String field) {
            this.dateString = date;
            this.updateField = field;
        }
    }

    private static class updateNotesAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private DayEntryDao mAsyncTaskDao;

        updateNotesAsyncTask(DayEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateNotes(params[0].dateString, params[0].updateField);
            return null;
        }
    }

    private static class updateJournalEntryAsyncTask extends AsyncTask<UpdateParams, Void, Void> {
        private DayEntryDao mAsyncTaskDao;

        updateJournalEntryAsyncTask(DayEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateParams... params) {
            mAsyncTaskDao.updateJournalEntry(params[0].dateString, params[0].updateField);
            return null;
        }
    }

    private static class deleteAllDayEntriesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DayEntryDao mAsyncTaskDao;

        deleteAllDayEntriesAsyncTask(DayEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteDayEntryAsyncTask extends AsyncTask<DayEntry, Void, Void> {
        private DayEntryDao mAsyncTaskDao;

        deleteDayEntryAsyncTask(DayEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DayEntry... params) {
            mAsyncTaskDao.deleteDayEntry(params[0]);
            return null;
        }
    }

}
