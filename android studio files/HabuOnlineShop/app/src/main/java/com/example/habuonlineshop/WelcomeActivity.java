package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.SharedInformation;

public class WelcomeActivity extends AppCompatActivity {

    Button SignUpbtn;
    Button loginbtn;
    SharedInformation sharedInformation;
    private static final String TAG = "WelcomeActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        sharedInformation=new SharedInformation(getApplicationContext());
        String gettype = sharedInformation.getusertype();
       if(gettype.equals("customer")){

           Customer customer = sharedInformation.getUserCustomer();

           if(customer.getIsverified()){
               Intent gotocustomerActivity = new Intent(WelcomeActivity.this,CustomerMainActivity.class);
               startActivity(gotocustomerActivity);
           }

           else
           {
               Intent gotocustomerActivity = new Intent(WelcomeActivity.this,VerifiyAccount.class);
               startActivity(gotocustomerActivity);
           }

        }


       if(gettype.equals("admin")){



           Intent gotoadmin = new Intent(WelcomeActivity.this,AdminMainActivity.class);

           startActivity(gotoadmin);
       }
       //check if a user is logged in


        Log.d(TAG, "onCreate: Activity Started ");
        RelativeLayout relativeLayout = findViewById(R.id.welcomeactivitylayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);

        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();


        

        SignUpbtn = (Button) findViewById(R.id.signup_btn);
        loginbtn = (Button) findViewById(R.id.signin_btn);
        sharedInformation = new SharedInformation(this);
        Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);




        Intent j = new Intent(WelcomeActivity.this,SignUp.class);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //sharedInformation.settest("??????wowfinallyfhemet999999999");

       // System.out.println(sharedInformation.gettest()+"1");






        SignUpbtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Button Pressed
                    SignUpbtn.setBackground(getDrawable(R.drawable.welcomeactivity_botton2));
                }
                if(event.getAction() == MotionEvent.ACTION_UP){

                    SignUpbtn.setBackground(getDrawable(R.drawable.welcomeactivity_button1));
                    //finger was lifted
                }
                return false;
            }
        });


        loginbtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Button Pressed
                    loginbtn.setBackground(getDrawable(R.drawable.welcomeactivity_button1));

                }
                if(event.getAction() == MotionEvent.ACTION_UP){



                    loginbtn.setBackground(getDrawable(R.drawable.welcomeactivity_botton2));
                    //finger was lifted
                }
                return false;
            }
        });

        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(j);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

    }

    @Override
    public void onBackPressed() {
       moveTaskToBack(true);


    }
}