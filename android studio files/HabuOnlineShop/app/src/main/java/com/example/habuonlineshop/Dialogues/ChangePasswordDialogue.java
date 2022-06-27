package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordDialogue extends Dialog implements View.OnClickListener {

    Activity a;
    private ViewFlipper vf;
    private TextInputEditText currentpass;
    private TextInputEditText newpass;
    private TextInputEditText confpass;
    private TextInputLayout currentpasslay;
    private TextInputLayout newpasslay;
    private TextInputLayout confpasslay;
    private RequestQueue queue;
    private ProgressBar progressBar;
    private Button submit;
    private CardView card;
    private View view;
    public ChangePasswordDialogue(Activity a){
        super(a);
        this.a=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_changepassword);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        queue = Volley.newRequestQueue(getContext());
        currentpass = findViewById(R.id.currentpassword);
        progressBar = findViewById(R.id.loading);
        submit = findViewById(R.id.submitbtn);
        card = findViewById(R.id.card);
        view = findViewById(R.id.view);


        TextView goback  = findViewById(R.id.gobackbtn);
         vf = findViewById(R.id.vfchangepass);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.setDisplayedChild(1);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        vf.setDisplayedChild(0);

                    }
                },2000);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
