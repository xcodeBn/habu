package com.example.habuonlineshop.Functions;

import android.content.Context;
import android.util.AttributeSet;

import androidx.lifecycle.MutableLiveData;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

public class Chart  {

    private String title;
    private Float rating;


    MutableLiveData<Chart> chartMutableLiveData;


    public Chart(String title, Float rating) {
        this.title = title;
        this.rating = rating;

        chartMutableLiveData = new MutableLiveData<>();


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
