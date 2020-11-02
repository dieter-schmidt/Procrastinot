package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import android.media.MediaPlayer;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskViewModel mTaskViewModel;
    private RecyclerView recyclerView;

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_NAME = "com.example.android.twoactivities.extra.NAME";
    public static final String EXTRA_TYPE = "com.example.android.twoactivities.extra.TYPE";
    public static final String EXTRA_WEIGHT = "com.example.android.twoactivities.extra.WEIGHT";
    public static final String EXTRA_ID = "com.example.android.twoactivities.extra.ID";
    public static final String EXTRA_NOTES = "com.example.android.twoactivities.extra.NOTES";

    private Task editedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView = findViewById(R.id.recyclerview);
        final TaskListAdapter adapter = new TaskListAdapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Toast.makeText(getApplicationContext(), "Item Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra(EXTRA_NAME, task.getTask());
                intent.putExtra(EXTRA_TYPE, task.getType());
                intent.putExtra(EXTRA_WEIGHT, task.getWeight());
                intent.putExtra(EXTRA_NOTES, task.getNotes());
                intent.putExtra(EXTRA_ID, task.getID());
                startActivityForResult(intent, EDIT_TASK_ACTIVITY_REQUEST_CODE);
            }
            public void onNotesClick(Task task) {
                Toast.makeText(getApplicationContext(), task.getNotes(), Toast.LENGTH_LONG).show();
                PopupWindow popup = new PopupWindow(50, 50);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the adapter.
                adapter.setTasks(tasks);
            }
        });

        // Add the functionality to swipe items in the 
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Task myTask = adapter.getTaskAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myTask.getTask(), Toast.LENGTH_LONG).show();

                        // Delete the task
                        mTaskViewModel.deleteTask(myTask);
                    }

                });


        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mTaskViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_NAME), data.getStringExtra(NewTaskActivity.EXTRA_WEIGHT),
                                data.getStringExtra(NewTaskActivity.EXTRA_TYPE), data.getStringExtra(NewTaskActivity.EXTRA_NOTES));
            //update task background color depending on weight
//            if (task.getWeight() == "Hard") {
//
//            }
            mTaskViewModel.insert(task);
        }
        else if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //If task name is the same, update task, otherwise create new
//            Task[] matches = mTaskViewModel.getMatchedTasksByName(data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME));
//            if (matches != null)
//            {
//                if (matches.length == 0)
//                {
//                    Task task = new Task(data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME), data.getStringExtra(EditTaskActivity.EXTRA_NEW_WEIGHT), data.getStringExtra(EditTaskActivity.EXTRA_NEW_TYPE));
//                    mTaskViewModel.insert(task);
//                }
//                else if (matches.length == 1)
//                {
//                    //update existing task with matched name (or changed name)
//
//                }
//                //ALTERNATIVE - UPDATE EVERYTHING
//            }
            String name = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME);
            String weight = data.getStringExtra(EditTaskActivity.EXTRA_NEW_WEIGHT);
            String type = data.getStringExtra(EditTaskActivity.EXTRA_NEW_TYPE);
            String notes = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NOTES);
            int id = data.getIntExtra(EditTaskActivity.EXTRA_NEW_ID, -1);
            mTaskViewModel.updateName(id, name);
            mTaskViewModel.updateWeight(id, weight);
            mTaskViewModel.updateType(id, type);
            mTaskViewModel.updateNotes(id, notes);
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}