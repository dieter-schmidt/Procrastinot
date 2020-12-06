package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String date;

    public TaskFragment tFrag;
    public NotesFragment nFrag;
    public JournalFragment jFrag;

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
                Log.e("FRAGMENT", "getItem: " + tFrag.toString());
                return tFrag;
            case 1:
                nFrag = new NotesFragment();
                Log.e("FRAGMENT", "getItem: " + nFrag.toString());
                return nFrag;
            case 2:
                jFrag = new JournalFragment();
                Log.e("FRAGMENT", "getItem: " + jFrag.toString());
                return jFrag;
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
