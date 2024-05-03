package com.ddr.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FlightsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FlightsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is flights fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}