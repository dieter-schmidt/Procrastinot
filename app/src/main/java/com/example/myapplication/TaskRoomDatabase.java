package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Random;

@Database(entities = {Task.class}, version = 6, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
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
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao mDao;
        String[] tasks = {"Laundry", "Work Out", "Take out the Trash", "Bibbery", "Bobbery", "Do Drugs", "Create World Peace", "Check your Privilege"};

        PopulateDbAsync(TaskRoomDatabase db) {
            mDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //mDao.deleteAll();
            // If we have no tasks, then create the initial list of tasks
            if (mDao.getAnyTask().length < 1) {
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
                    Task task = new Task(tasks[i], weight, "Fitness", "Basic notes text.");
                    mDao.insert(task);
                }
            }
            return null;
        }
    }

}
