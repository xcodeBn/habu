package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.WelcomeActivity;

public class Logout_Dialogue extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog;
    SharedInformation sharedInformation;
    TextView yes;
    TextView no;

    public Logout_Dialogue(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_logout);


        sharedInformation = new SharedInformation(activity.getApplicationContext());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        yes = (TextView) findViewById(R.id.logoutyes_tv);
        no = (TextView) findViewById(R.id.logoutnos_tv);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedInformation.clearinfo();
                Intent intent = new Intent(activity.getApplicationContext(), WelcomeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                //activity.finishAffinity();



            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();


            }
        });


    }


//    @Override
//    public void onBackPressed() {
//       activity.finish();
//    }

    @Override
    public void onClick(View view) {

    }
}
