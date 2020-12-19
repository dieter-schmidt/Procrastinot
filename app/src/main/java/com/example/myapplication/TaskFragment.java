package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;

import android.media.MediaPlayer;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private TaskViewModel mTaskViewModel;
    private RecyclerView recyclerView;

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_TASK_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_NAME = "com.example.android.twoactivities.extra.NAME";
    public static final String EXTRA_TYPE = "com.example.android.twoactivities.extra.TYPE";
    public static final String EXTRA_WEIGHT = "com.example.android.twoactivities.extra.WEIGHT";
    public static final String EXTRA_ID = "com.example.android.twoactivities.extra.ID";
    public static final String EXTRA_NOTES = "com.example.android.twoactivities.extra.NOTES";
    public static final String EXTRA_COLOR = "com.example.android.twoactivities.extra.COLOR";

    private Task editedTask;
    TaskListAdapter adapter;

    public Task[] selectedDateTasks = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_task, container, false);
        //setContentView(R.layout.activity_main);
       // Toolbar toolbar = v.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTaskActivity.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        //NEW - toolbar
//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView = v.findViewById(R.id.recyclerview);
        //final TaskListAdapter adapter = new TaskListAdapter(getContext(), new OnItemClickListener() {
        adapter = new TaskListAdapter(getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Toast.makeText(getActivity().getApplicationContext(), "Item Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                intent.putExtra(EXTRA_NAME, task.getTask());
                intent.putExtra(EXTRA_TYPE, task.getType());
                intent.putExtra(EXTRA_WEIGHT, task.getWeight());
                intent.putExtra(EXTRA_NOTES, task.getNotes());
                intent.putExtra(EXTRA_ID, task.getID());
                intent.putExtra(EXTRA_COLOR, task.getColor());
                startActivityForResult(intent, EDIT_TASK_ACTIVITY_REQUEST_CODE);
            }
            public void onNotesClick(Task task) {
                Toast.makeText(getActivity().getApplicationContext(), task.getNotes(), Toast.LENGTH_LONG).show();
                PopupWindow popup = new PopupWindow(50, 50);
            }

            @Override
            public void onCompleteClick(Task task, View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Task Complete!!!", Toast.LENGTH_SHORT).show();
                    mTaskViewModel.updateCompletionStatus(task.getID(), true);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Task Incomplete.", Toast.LENGTH_SHORT).show();
                    mTaskViewModel.updateCompletionStatus(task.getID(), false);
                }
            }

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        mTaskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the adapter.
                adapter.setTasks(tasks);
            }
        });
//        //TODO - updated adapter to show tasks for current date
//        MainActivity mActivity = (MainActivity) getActivity();
//        MenuItem currentDateView = (MenuItem) mActivity.currentDateItem;
//        String title = currentDateView.getTitle().toString();
//        Log.e("TEST", "Menu Title: "+title);
//        boolean dbOpen = TaskRoomDatabase.getDatabase(getActivity().getApplicationContext()).isOpen();
//        List<Task> tasks = null;
//        if (dbOpen) {
//            Task[] selectedDateTasks = mTaskViewModel.getMatchedTasksByDate(currentDateView.getTitle().toString());
//            if (selectedDateTasks != null) {
//                tasks = Arrays.asList(selectedDateTasks);
//            }
//        }
        //adapter.setTasks(tasks);




        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT) {
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
                        Toast.makeText(getActivity().getApplicationContext(), "Deleting " +
                                myTask.getTask(), Toast.LENGTH_LONG).show();

                        // Delete the task
                        mTaskViewModel.deleteTask(myTask);
                    }

                });


        helper.attachToRecyclerView(recyclerView);

        // NEW - Create an instance of the tab layout from the view.
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
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });


        return v;
    }

    public void refreshTasks() throws ExecutionException, InterruptedException {
        if (getActivity() != null) {
            MainActivity mActivity = (MainActivity) getActivity();
            MenuItem currentDateView = (MenuItem) mActivity.currentDateItem;
            String title = currentDateView.getTitle().toString();
            Log.e("TEST", "Menu Title: "+title);
            boolean dbOpen = TaskRoomDatabase.getDatabase(getActivity().getApplicationContext()).isOpen();
            LiveData<List<Task>> allTasks = mTaskViewModel.getAllTasks();
            String d = allTasks.getValue().get(0).getDate();
            //LiveData<List<Task>> selectedDateTasks = mTaskViewModel.getMatchedTasksByDate(currentDateView.getTitle().toString());
            Task[] selectedDateTasks = mTaskViewModel.getMatchedTasksByDate(title);
            //LiveData<List<Task>> selectedDateTasks = mTaskViewModel.getMatchedTasksByDate(newDate);
            if (selectedDateTasks != null && selectedDateTasks.length > 0) {
//                selectedDateTasks.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
//                //mTaskViewModel.getMatchedTasksByDate(currentDateView.getTitle().toString()).observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
//                    @Override
//                    public void onChanged(@Nullable final List<Task> tasks) {
////                         Update the cached copy of the tasks in the adapter.
//                        adapter.setTasks(tasks);
//                    }
//                });
                adapter.setTasks(Arrays.asList(selectedDateTasks));
            }
            else {
                adapter.setTasks(null);
            }
        }
    }

//    @Override
//    public void processFinish(Task[] output) {
//        if (selectedDateTasks != null && selectedDateTasks.length > 0) {
////                selectedDateTasks.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
////                //mTaskViewModel.getMatchedTasksByDate(currentDateView.getTitle().toString()).observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
////                    @Override
////                    public void onChanged(@Nullable final List<Task> tasks) {
//////                         Update the cached copy of the tasks in the adapter.
////                        adapter.setTasks(tasks);
////                    }
////                });
//            adapter.setTasks(Arrays.asList(selectedDateTasks));
//        }
//        else {
//            adapter.setTasks(null);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Task task = new Task(data.getStringExtra(NewTaskActivity.EXTRA_NAME), data.getStringExtra(NewTaskActivity.EXTRA_WEIGHT),
                    data.getStringExtra(NewTaskActivity.EXTRA_TYPE), data.getStringExtra(NewTaskActivity.EXTRA_NOTES), CalendarConverter.fromCalendar(Calendar.getInstance()), "#F44336");
            //TODO - fix logic for adding date to new task
            //update task background color depending on weight
//            if (task.getWeight() == "Hard") {
//
//            }
            mTaskViewModel.insert(task);
        }
        else if (requestCode == EDIT_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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
            //TODO - add logic for changing date of task
            String name = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NAME);
            String weight = data.getStringExtra(EditTaskActivity.EXTRA_NEW_WEIGHT);
            String type = data.getStringExtra(EditTaskActivity.EXTRA_NEW_TYPE);
            String notes = data.getStringExtra(EditTaskActivity.EXTRA_NEW_NOTES);
            int id = data.getIntExtra(EditTaskActivity.EXTRA_NEW_ID, -1);
            String color = data.getStringExtra(EditTaskActivity.EXTRA_NEW_COLOR);
            mTaskViewModel.updateName(id, name);
            mTaskViewModel.updateWeight(id, weight);
            mTaskViewModel.updateType(id, type);
            mTaskViewModel.updateNotes(id, notes);
            mTaskViewModel.updateColor(id, color);
        }
        else {
//            Toast.makeText(
//                    getActivity().getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
        }
    }


}