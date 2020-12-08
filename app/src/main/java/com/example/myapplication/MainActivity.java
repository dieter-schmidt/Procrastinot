package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import android.media.MediaPlayer;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

//    private TaskViewModel mTaskViewModel;
//    private RecyclerView recyclerView;
//
//    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
//    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;
//
//    public static final String EXTRA_NAME = "com.example.android.twoactivities.extra.NAME";
//    public static final String EXTRA_TYPE = "com.example.android.twoactivities.extra.TYPE";
//    public static final String EXTRA_WEIGHT = "com.example.android.twoactivities.extra.WEIGHT";
//    public static final String EXTRA_ID = "com.example.android.twoactivities.extra.ID";
//    public static final String EXTRA_NOTES = "com.example.android.twoactivities.extra.NOTES";
//
//    private Task editedTask;

    ViewPager viewPager;
    PagerAdapter pAdapter;

    //public Toolbar toolbar;
    public MenuItem currentDateItem;

    private DayEntryViewModel mDayEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabstest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mDayEntryViewModel = ViewModelProviders.of(this).get(DayEntryViewModel.class);
//        List<DayEntry> dayEntries = mDayEntryViewModel.getAllDayEntries();
//        if (dayEntries != null) {
//            for (DayEntry day: dayEntries) {
//                Log.e("DAY", day.getDate());
//            }
//        }



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
//                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
//            }
//        });

        //NEW - toolbar
//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        recyclerView = findViewById(R.id.recyclerview);
//        final TaskListAdapter adapter = new TaskListAdapter(this, new OnItemClickListener() {
//            @Override
//            public void onItemClick(Task task) {
//                Toast.makeText(getApplicationContext(), "Item Clicked", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
//                intent.putExtra(EXTRA_NAME, task.getTask());
//                intent.putExtra(EXTRA_TYPE, task.getType());
//                intent.putExtra(EXTRA_WEIGHT, task.getWeight());
//                intent.putExtra(EXTRA_NOTES, task.getNotes());
//                intent.putExtra(EXTRA_ID, task.getID());
//                startActivityForResult(intent, EDIT_TASK_ACTIVITY_REQUEST_CODE);
//            }
//            public void onNotesClick(Task task) {
//                Toast.makeText(getApplicationContext(), task.getNotes(), Toast.LENGTH_LONG).show();
//                PopupWindow popup = new PopupWindow(50, 50);
//            }
//
//            @Override
//            public void onCompleteClick(Task task, View v) {
//                CheckBox cb = (CheckBox) v;
//                if (cb.isChecked()) {
//                    Toast.makeText(getApplicationContext(), "Task Complete!!!", Toast.LENGTH_SHORT).show();
//                    mTaskViewModel.updateCompletionStatus(task.getID(), true);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Task Incomplete.", Toast.LENGTH_SHORT).show();
//                    mTaskViewModel.updateCompletionStatus(task.getID(), false);
//                }
//            }
//
//        });
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
//
//        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
//            @Override
//            public void onChanged(@Nullable final List<Task> tasks) {
//                // Update the cached copy of the tasks in the adapter.
//                adapter.setTasks(tasks);
//            }
//        });
//
//        // Add the functionality to swipe items in the
//        // recycler view to delete that item
//        ItemTouchHelper helper = new ItemTouchHelper(
//                new ItemTouchHelper.SimpleCallback(0,
//                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                    @Override
//                    public boolean onMove(RecyclerView recyclerView,
//                                          RecyclerView.ViewHolder viewHolder,
//                                          RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
//                                         int direction) {
//                        int position = viewHolder.getAdapterPosition();
//                        Task myTask = adapter.getTaskAtPosition(position);
//                        Toast.makeText(MainActivity.this, "Deleting " +
//                                myTask.getTask(), Toast.LENGTH_LONG).show();
//
//                        // Delete the task
//                        mTaskViewModel.deleteTask(myTask);
//                    }
//
//                });
//
//
//        helper.attachToRecyclerView(recyclerView);

//        // NEW - Create an instance of the tab layout from the view.
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        // Set the text for each tab.
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.task_tab_label));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.notes_tab_label));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.journal_tab_label));
//        // Set the tabs to fill the entire layout.
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        // Use PagerAdapter to manage page views in fragments.
//        // Each page is represented by its own fragment.
//        final ViewPager viewPager = findViewById(R.id.pager);
//        final PagerAdapter pAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pAdapter);
//        // Setting a listener for clicks.
//        viewPager.addOnPageChangeListener(new
//                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//           @Override
//           public void onTabSelected(TabLayout.Tab tab) {
//               viewPager.setCurrentItem(tab.getPosition());
//           }
//
//           @Override
//           public void onTabUnselected(TabLayout.Tab tab) {
//           }
//
//           @Override
//           public void onTabReselected(TabLayout.Tab tab) {
//           }
//       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        currentDateItem = menu.findItem(R.id.current_date).setTitle(CalendarConverter.fromCalendar(Calendar.getInstance()));
        createTabLayout();
        return true;
    }

    public void createTabLayout() {
        // NEW - Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.task_tab_label));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.notes_tab_label));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.journal_tab_label));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        //final ViewPager viewPager = findViewById(R.id.pager);
        viewPager = findViewById(R.id.pager);
//        final PagerAdapter pAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pAdapter);
        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAB", Integer.toString(tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0) {
//                    viewPager.getAdapter()
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("TAB", "Unselected tab: "+tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //TODO - set initial tab, so tasks can be refreshed when the task tab is selected
        //viewPager.setCurrentItem(0);
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
            //mTaskViewModel.deleteAll();
            return true;
        }
        else if (id == R.id.change_date) {
            showDatePicker(item.getActionView());
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
//        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Task task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_NAME), data.getStringExtra(NewTaskActivity.EXTRA_WEIGHT),
//                                data.getStringExtra(NewTaskActivity.EXTRA_TYPE), data.getStringExtra(NewTaskActivity.EXTRA_NOTES));
//            //update task background color depending on weight
////            if (task.getWeight() == "Hard") {
////
////            }
//            mTaskViewModel.insert(task);
//        }
//        else if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            //If task name is the same, update task, otherwise create new
////            Task[] matches = mTaskViewModel.getMatchedTasksByName(data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME));
////            if (matches != null)
////            {
////                if (matches.length == 0)
////                {
////                    Task task = new Task(data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME), data.getStringExtra(EditTaskActivity.EXTRA_NEW_WEIGHT), data.getStringExtra(EditTaskActivity.EXTRA_NEW_TYPE));
////                    mTaskViewModel.insert(task);
////                }
////                else if (matches.length == 1)
////                {
////                    //update existing task with matched name (or changed name)
////
////                }
////                //ALTERNATIVE - UPDATE EVERYTHING
////            }
//            String name = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME);
//            String weight = data.getStringExtra(EditTaskActivity.EXTRA_NEW_WEIGHT);
//            String type = data.getStringExtra(EditTaskActivity.EXTRA_NEW_TYPE);
//            String notes = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NOTES);
//            int id = data.getIntExtra(EditTaskActivity.EXTRA_NEW_ID, -1);
//            mTaskViewModel.updateName(id, name);
//            mTaskViewModel.updateWeight(id, weight);
//            mTaskViewModel.updateType(id, type);
//            mTaskViewModel.updateNotes(id, notes);
//        }
//        else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
    }

    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showDatePicker(View view) {
        //update notes and/or journal when datepicker is selected (not handled by focus change listener)
        if (pAdapter.nFrag.isNotesChanged()) {
            pAdapter.nFrag.updateNotes();
        }
        if (pAdapter.jFrag.isJournalChanged()) {
            pAdapter.jFrag.updateJournal();
        }

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    /**
     * Process the date picker result into strings that can be displayed in
     * a Toast.
     *
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processDatePickerResult(int year, int month, int day) throws ExecutionException, InterruptedException {
        String month_string = Integer.toString(month + 1);
        if (Integer.parseInt(month_string) < 10) {
            month_string = "0" + month_string;
        }
        String day_string = Integer.toString(day);
        if (Integer.parseInt(day_string) < 10) {
            day_string = "0" + day_string;
        }

        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "-" + day_string +
                "-" + year_string);

        Toast.makeText(this, getString(R.string.date) + dateMessage,
                Toast.LENGTH_SHORT).show();

        //update toolbar date
        currentDateItem.setTitle(dateMessage);
        //TaskFragment tFrag = viewPager.getAdapter().
//        viewPager.getAdapter().

        //update tasks
        Log.e("FRAGMENT", "MainActivity: " + pAdapter.tFrag.toString());
        pAdapter.tFrag.refreshTasks();
        pAdapter.nFrag.refreshNotes();
        pAdapter.jFrag.refreshJournal();
        //pAdapter.notifyDataSetChanged();
    }

}