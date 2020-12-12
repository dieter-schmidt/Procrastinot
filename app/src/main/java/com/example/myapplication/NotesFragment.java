package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private DayEntryViewModel mDayEntryModel;
    private EditText editNotesField;
    private boolean notesChanged = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

//    public void setFont() {
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
//                "");
//        this.editNotesField.setTypeface(tf);
//    }

    public boolean isNotesChanged() {
        return notesChanged;
    }

    public void setNotesChanged(boolean notesChanged) {
        this.notesChanged = notesChanged;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
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
        View v = inflater.inflate(R.layout.fragment_tab_notes, container, false);

        editNotesField = v.findViewById(R.id.edit_tab_notes);
        editNotesField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("NOTES", "Notes Focus Changed, "+hasFocus);
                if (hasFocus) {
                    notesChanged = false;
                }
                else {
                    if (notesChanged) {
                        //update notes
                        updateNotes();
                    }
                }
            }
        });
        editNotesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("NOTES", "Notes Changed");
                notesChanged = true;
            }
        });
        mDayEntryModel = ViewModelProviders.of(this).get(DayEntryViewModel.class);
        refreshNotes();
//        setFont();
        return v;
    }

    public void updateNotes() {
        MainActivity mActivity = (MainActivity) getActivity();
        MenuItem currentDateView = (MenuItem) mActivity.currentDateItem;
        String title = currentDateView.getTitle().toString();
        mDayEntryModel.updateNotes(title, editNotesField.getText().toString());
    }

    public void refreshNotes() {
        String dateTitle;
        if (getActivity() != null) {
            MainActivity mActivity = (MainActivity) getActivity();
            MenuItem currentDateView = (MenuItem) mActivity.currentDateItem;
            dateTitle = currentDateView.getTitle().toString();
            DayEntry dayEntry = null;
            try {
                dayEntry = mDayEntryModel.getMatchedDayEntryByDate(dateTitle);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (dayEntry != null) {
                editNotesField.setText(dayEntry.getNotes());
            }
            else {
                editNotesField.setText(null);
            }
        }
    }
}