package com.example.habuonlineshop.Functions;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {

    private CountDownTimer test;
//just a text , nothing for project
    private MutableLiveData<Integer> sec;

    void startTest(){
        test  = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };
    }
}
