package com.example.projetolm.ui.assinatura;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssinaturaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AssinaturaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}