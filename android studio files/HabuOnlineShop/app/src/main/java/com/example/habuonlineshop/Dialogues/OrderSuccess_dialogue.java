package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.habuonlineshop.CustomerMainActivity;
import com.example.habuonlineshop.R;

public class OrderSuccess_dialogue extends Dialog implements View.OnClickListener {


    private Activity a;

    public OrderSuccess_dialogue(@NonNull  Activity a) {
        super(a);
        this.a = a;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_order_sucess);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView parent = findViewById(R.id.parent);
        Intent intent = new Intent(a, CustomerMainActivity.class);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(a, CustomerMainActivity.class);
                a.startActivity(intent);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a.startActivity(intent);

            }
        },500);

    }



    @Override
    public void onClick(View v) {

        Intent intent = new Intent(a, CustomerMainActivity.class);
        a.startActivity(intent);

    }
}
