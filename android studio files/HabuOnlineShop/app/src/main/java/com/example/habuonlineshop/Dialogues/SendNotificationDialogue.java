package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.WelcomeActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class SendNotificationDialogue extends Dialog implements View.OnClickListener {


    private Activity activity;
    private Dialog dialog;
    SharedInformation sharedInformation;
    TextView yes;
    TextView no;
    private static final String TAG = "SendNotificationDialogu";
    private TextInputEditText title;
    private TextInputEditText body;
    private Button send;

    public SendNotificationDialogue(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_send_notification);


        sharedInformation = new SharedInformation(activity.getApplicationContext());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);

        send = findViewById(R.id.add);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().isEmpty() || body.getText().toString().isEmpty()) {


                } else {

                    addnotification();
                }
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

    private void addnotification() {

        String url = activity.getResources().getString(R.string.dblink) + activity.getResources().getString(R.string.addnotification);
        ProgressDialog dialog = ProgressDialog.show(getContext(),"sending","Please wait",false,false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                dismiss();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
                dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                String tita = title.getText().toString();
                String bod = body.getText().toString();

                params.put("title", tita);
                params.put("body", bod);


                return params;
            }




        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
