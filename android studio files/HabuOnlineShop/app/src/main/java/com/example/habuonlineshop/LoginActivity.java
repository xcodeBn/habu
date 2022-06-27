package com.example.habuonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Admin;
import com.example.habuonlineshop.Classes.Appfunctions;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Signupsuccefulldialogue;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {



    private DatabaseReference testdbreference;
    private static final String TAG = "LoginActivity";

    SharedInformation sharedInformation;
Button testdialogue;
Signupsuccefulldialogue su;
RelativeLayout loginrl;
TextInputEditText emailed;
TextInputEditText passworded;
String securitykey;
ViewFlipper vf;
TextView login;
RequestQueue queue ;
    Appfunctions appfunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);



sharedInformation = new SharedInformation(this);




 appfunctions = new Appfunctions(LoginActivity.this,this);
loginrl=(RelativeLayout) findViewById(R.id.loginactivity_relativelayout);
  emailed = (TextInputEditText) findViewById(R.id.email_EditText_LOGINPAGE);
  passworded = (TextInputEditText) findViewById(R.id.password_EditText_LOGINPAGE);
queue = Volley.newRequestQueue(this);
vf= (ViewFlipper) findViewById(R.id.vf_loginactivity);



login =(TextView) findViewById(R.id.loginbtn_tv);


        AnimationDrawable animationDrawable = (AnimationDrawable) loginrl.getBackground();
        animationDrawable.setEnterFadeDuration(1500);

        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
        //Animation

        securitykey=getResources().getString(R.string.securitykey);
        String logninurl = getResources().getString(R.string.dblink)+getResources().getString(R.string.loginphp);

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                //login.setTextSize(30);

                return false;
            }
        });


login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        appfunctions.hideKeyboard(LoginActivity.this);



        String emailg = emailed.getText().toString();
        String passwordg = passworded.getText().toString();

        if(emailg.isEmpty() || passwordg.isEmpty()){



        }

        else {


            vf.setDisplayedChild(0);
            login.setText("");
            vf.setVisibility(View.VISIBLE);
try{

    login(logninurl,emailg,passwordg);


    }

catch (Exception e){

    Log.d(TAG, "onClick: EXCEPTION" + e.getMessage());
}



        }



    }
});



    }

    public void login(String url,String email,String password){



        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d(TAG, "onResponse: String response: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("error");
                    String type = jsonObject.getString("type");
                    String error_message = jsonObject.getString("error_message");



                    Log.d(TAG, "onResponse: TYPE" +type);
                    //TYPEcustomer
                    if(error=="false"){

                        if(type.equals("customer")){


                            String name = jsonObject.getString("name");
                            String creationDate=jsonObject.getString("CreationDate");
                            String location = jsonObject.getString("location");
                            int isverfied=jsonObject.getInt("isverified");

                            Boolean verified = appfunctions.inttoBoolean(isverfied);
                            sharedInformation.setUserCustomer(name,email,password,location,creationDate,verified);
                            sharedInformation.setusertype("customer");


                            if(verified) {
                                //Customer currentcustomer = new Customer();


                                Intent i = new Intent(LoginActivity.this, CustomerMainActivity.class);
                                startActivity(i);
                            }
                            else {

                                Intent goverify = new Intent(LoginActivity.this,VerifiyAccount.class);

                                startActivity(goverify);
                            }


                        }

                       else if(type.equals("admin")){

                           String email = jsonObject.getString("email");
                            String name = jsonObject.getString("name");
                            String password = jsonObject.getString("password");

                            Admin admin = new Admin(email,name,password);
                            sharedInformation.setusertype("admin");
                            sharedInformation.setAdmin(admin);
                            Intent j = new Intent(LoginActivity.this,AdminMainActivity.class);
                            startActivity(j);


                        }

                       else {

                            Log.d(TAG, "onResponse: ERROR #009LOGINERROR NO TYPE FOR THE ACCOUNT" + type );
                        }

                    }

                    else {


                        vf.setVisibility(View.GONE);
                        login.setText("Login");
                        Snackbar certerror = Snackbar.make(loginrl,error_message,Snackbar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                emailed.setText("");
                                passworded.setText("");
                            }
                        }).setActionTextColor(getResources().getColor(R.color.white));


                        certerror.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                        certerror.show();
                        Log.d(TAG, "onResponse: Error message" + error_message);


                    }

                }
                catch (JSONException e){

                    Log.d(TAG, "onResponse: JSON" + e.getMessage());

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.d(TAG, "onErrorResponse: " + error);
                vf.setVisibility(View.GONE);
                login.setText("Login");

            }
        }){ @Override
        protected Map<String,String> getParams() throws AuthFailureError {


            Map<String,String> params= new HashMap<>();
            params.put("email",email);

            params.put("password",password);
            params.put("key",securitykey);




            return params;

        }


        };

        queue.add(request.setRetryPolicy(new DefaultRetryPolicy(10000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));

    }


    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}