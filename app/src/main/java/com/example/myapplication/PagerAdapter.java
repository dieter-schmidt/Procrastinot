package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String date;

    public TaskFragment tFrag;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                TaskFragment tFrag = new TaskFragment();
//                tFrag.refreshTasks();
//                return tFrag;
                tFrag = new TaskFragment();
                return tFrag;
            case 1: return new NotesFragment();
            case 2: return new JournalFragment();
            default: return null;
        }
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
