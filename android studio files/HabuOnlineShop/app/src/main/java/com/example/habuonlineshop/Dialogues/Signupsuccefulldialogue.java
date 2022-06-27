package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.LoginActivity;
import com.example.habuonlineshop.R;

import java.util.List;

public class Signupsuccefulldialogue extends Dialog implements View.OnClickListener {


   private Activity activity;
   private Dialog dialog;
   SharedInformation sharedInformation;
   TextView login_act;

   public Signupsuccefulldialogue(Activity a){

       super(a);
       this.activity=a;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signupcompletelayout_dialogue);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        login_act = (TextView) findViewById(R.id.Login_signupdialogue);

        login_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity.getApplicationContext(), LoginActivity.class);
                activity.startActivity(i);
            }
        });



    }

    @Override
    public void onClick(View view) {

    }
}
