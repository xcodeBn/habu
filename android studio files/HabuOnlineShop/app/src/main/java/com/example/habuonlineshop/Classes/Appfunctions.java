package com.example.habuonlineshop.Classes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

public class Appfunctions {


    Activity activity;
    Context c;

    public Appfunctions(Activity activity, Context context) {
        this.activity = activity;
        this.c=context;
    }


    public static void hideKeyboard(@NonNull Activity activity){

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view=new View(activity);
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);


    }

    public Boolean inttoBoolean(int x){

        if(x==0)
            return false;
        else
            return true;

    }




}
