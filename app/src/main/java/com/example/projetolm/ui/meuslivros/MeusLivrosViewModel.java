package com.example.projetolm.ui.meuslivros;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeusLivrosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MeusLivrosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}