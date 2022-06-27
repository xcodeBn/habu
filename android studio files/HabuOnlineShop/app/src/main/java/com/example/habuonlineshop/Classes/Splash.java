package com.example.habuonlineshop.Classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.habuonlineshop.CustomerMainActivity;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.SignUp;
import com.example.habuonlineshop.WelcomeActivity;

import ch.halcyon.squareprogressbar.SquareProgressBar;

public class Splash extends Activity {

    private final int splash_length = 1000;
//    private SquareProgressBar squareProgressBar;

    private ImageView img;
    private ImageView slide;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


       // img = findViewById(R.id.img);
        //postponeEnterTransition();
        slide = findViewById(R.id.bck);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //animateBell();
                animateSlide();
            }
        },100);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                Intent mainintent = new Intent(Splash.this, WelcomeActivity.class);
                Splash.this.startActivity(mainintent);
                Splash.this.finish();
            }
        },splash_length);
    }



    public void animateSlide(){

        Animation animation = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.wooooo);
        animation.setDuration(1000);
        slide.startAnimation(animation);
    }
}
