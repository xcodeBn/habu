package com.example.habuonlineshop.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habuonlineshop.Classes.Cart;

import java.util.ArrayList;

public class CartViewModel extends AndroidViewModel {


    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    //private Application application;
   // private ArrayList<Cart> cartArrayList;
    private MutableLiveData<ArrayList<Cart>> cartlivedata;




    public void init(){

    }

    public LiveData<ArrayList<Cart>> getCartArrayList(){


        return cartlivedata;
    }

}
