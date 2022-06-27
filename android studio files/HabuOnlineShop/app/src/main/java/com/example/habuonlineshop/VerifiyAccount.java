package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.renderscript.Type;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Appfunctions;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Logout_Dialogue;
import com.google.android.material.snackbar.Snackbar;
import com.jkb.vcedittext.VerificationCodeEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifiyAccount extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView logoutbtn;
    private Logout_Dialogue logout_dialogue;
    private PinEntryEditText editText;
    private static final String TAG = "VerifiyAccount";
    private Button btn;
    private ViewFlipper vf;
    private RequestQueue queue;
    private Customer customer;
    private SharedInformation sharedInformation;
    private RelativeLayout parent;
    private Appfunctions appfunctions;
    private Activity activity;

    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiy_account);

        appfunctions = new Appfunctions(this,this);
        Log.d(TAG, "onCreate: Created");
        editText = findViewById(R.id.verify);


        activity = this;
        parent = findViewById(R.id.parent);

        sharedInformation = new SharedInformation(this);
        customer=sharedInformation.getUserCustomer();

        Log.d(TAG, "onCreate: CUSTOMER" + customer.getEmail());
        vf = findViewById(R.id.vf);
        queue = Volley.newRequestQueue(this);


        btn = findViewById(R.id.btn);

        
try {


        toolbar = (Toolbar) findViewById(R.id.verifymain_toolbar);

        logoutbtn = (ImageView) findViewById(R.id.logoutBtn);
        logout_dialogue = new Logout_Dialogue(VerifiyAccount.this);
}

catch (Exception e){

    Log.d(TAG, "onCreate: ExceptionErrorElementAssignment: "+ e.getMessage() );
}


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window logoutwindow = logout_dialogue.getWindow();
                logoutwindow.setBackgroundDrawableResource(android.R.color.transparent);
                logout_dialogue.show();

            }
        });

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        verifyEmail();
    }
});

editText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
    @Override
    public void onPinEntered(CharSequence str) {
        if(str.toString().equals(token)){

            hasVerified();

        }
        else {
            appfunctions.hideKeyboard(activity);
            editText.setText(null);
            Snackbar snackbar = Snackbar.make(parent,"Token is wrong,please try again",Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(getResources().getColor(R.color.red));
            snackbar.show();
        }
    }
});

    }


    private void hasVerified(){

        loading();
        appfunctions.hideKeyboard(activity);

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.emailverified);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                 JSONObject row = new JSONObject(response);
                 Boolean error = row.getBoolean("error");
                 String error_message = row.getString("error_message");


                 if(error){
                 error();
                     Log.d(TAG, "onResponse: "   + error_message);
                 }
                 else {
                     Customer c = customer;
                     c.setIsverified(true);
                     sharedInformation.setCustomer(c);

                     Intent i = new Intent(VerifiyAccount.this,CustomerMainActivity.class);
                     startActivity(i);
                 }

                }
                catch (JSONException e ){
                    e.printStackTrace();
                    error();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error();
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",customer.getEmail());


                return params;
            }
        };
        queue.add(request);
    }
    private void verifyEmail(){

        loading();
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.verifyemail)+"?mail="+customer.getEmail();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject row = new JSONObject(response);
                    token=row.getString("token");
                    enterPin();

                }
                catch (JSONException e){
                    e.printStackTrace();

                    Log.d(TAG, "onResponse: " + e.getMessage());
                    Log.d(TAG, "onResponse: ERROR");
                    error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                error();

                Log.d(TAG, "onErrorResponse: " +  error.getMessage());
            }
        });
        queue.add(request);



    }

    private void loading(){

        vf.setDisplayedChild(1);
    }

    private void enterPin(){

        vf.setDisplayedChild(2);
    }
    private void error(){

        vf.setDisplayedChild(0);

        Snackbar snackbar = Snackbar.make(parent,"Error try again later",Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(getResources().getColor(R.color.red));
        snackbar.show();

        appfunctions.hideKeyboard(activity);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}