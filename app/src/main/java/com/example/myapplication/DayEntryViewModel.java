package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DayEntryViewModel extends AndroidViewModel {

    private DayEntryRepository mRepository;
    private LiveData<List<DayEntry>> mAllDayEntries;
//    private List<DayEntry> mAllDayEntries;

    public DayEntryViewModel(Application application) {
        super(application);
        mRepository = new DayEntryRepository(application);
        mAllDayEntries = mRepository.getAllDayEntries();
    }

    LiveData<List<DayEntry>> getAllDayEntries() { return mAllDayEntries; }
//    List<DayEntry> getAllDayEntries() { return mAllDayEntries; }

    public void insert(DayEntry dayEntry) { mRepository.insert(dayEntry); }

    public DayEntry getMatchedDayEntryByDate(String date) throws ExecutionException, InterruptedException { return mRepository.getMatchedDayEntryByDate(date); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteDayEntry(DayEntry dayEntry) {mRepository.deleteDayEntry(dayEntry);}

    public void updateJournalEntry(String date, String journalEntry) {mRepository.updateJournalEntry(date, journalEntry);}

    public void updateNotes(String date, String dayEntryNotes) {mRepository.updateNotes(date, dayEntryNotes);}


}
