package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Database(entities = {Task.class, DayEntry.class}, version = 9, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract DayEntryDao dayEntryDao();
    private static TaskRoomDatabase INSTANCE;

    public static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "task_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                    //TODO - refresh tasks
                    //Broadcast open db to signal task refresh
//                    IntentFilter filter = new IntentFilter();
//                    filter.addAction(Intent.ACTION_DB_OPEN);
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao mTaskDao;
        private final DayEntryDao mDayEntryDao;
        String[] tasks = {"Laundry", "Work Out", "Take out the Trash", "Bibbery", "Bobbery", "Do Drugs", "Create World Peace", "Check your Privilege"};

        PopulateDbAsync(TaskRoomDatabase db) {
            mTaskDao = db.taskDao();
            mDayEntryDao = db.dayEntryDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mTaskDao.deleteAll();
            mDayEntryDao.deleteAll();
            // If we have no tasks, then create the initial list of tasks
            if (mTaskDao.getAnyTask().length < 1) {
                for (int i = 0; i <= tasks.length - 1; i++) {
                    Random rand = new Random();
                    int randomInt = rand.nextInt(3);
                    String weight;
                    Log.e("RANDOM TEST", String.valueOf(randomInt));
                    switch(randomInt) {
                        case(0):
                            weight = "Easy";
                            break;
                        case (1):
                            weight = "Medium";
                            break;
                        default:
                            weight = "Hard";
                    }
                    Task task = new Task(tasks[i], weight, "Fitness", "Basic notes text.", CalendarConverter.fromCalendar(Calendar.getInstance()));
                    mTaskDao.insert(task);
                    Log.e("TEST", task.getDate());
                }
            }
            if (mDayEntryDao.getAnyDayEntry().length < 1) {
                DayEntry day = new DayEntry(CalendarConverter.fromCalendar(Calendar.getInstance()),null,null);
                mDayEntryDao.insert(day);
            }
            DayEntry[] entries = mDayEntryDao.getAnyDayEntry();
//            List<DayEntry> entries = mDayEntryDao.getAllDayEntries().getValue();
            for (DayEntry day: entries) {
                Log.e("DAY", day.getDate());
            }
            return null;
        }
    }

}
