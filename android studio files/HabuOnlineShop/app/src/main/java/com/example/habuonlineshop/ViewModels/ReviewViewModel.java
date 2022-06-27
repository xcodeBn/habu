package com.example.habuonlineshop.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Review;
import com.example.habuonlineshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewViewModel extends ViewModel {


    //Just a test not used in the project for the moment ;
    private MutableLiveData<ArrayList<Review>> reviewMutablelive;
    private ArrayList<Review> reviewsAL;
    private Context context;
    private Application application;
    private String pid;

    public ReviewViewModel(@NonNull Application a,Context context,String pid) {

        this.application=a;

        this.context=context;
        this.pid = pid;
    }


    private void intialize(){
        getReviews();
        reviewMutablelive.setValue(reviewsAL);

    }

    private void getReviews() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url=context.getString(R.string.dblink) + context.getString(R.string.reviews);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",pid);

                return params;
            }
        };
    }
}
