package com.example.habuonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class SignUp extends AppCompatActivity {

    TextInputEditText name;
    TextInputEditText password;
    TextInputEditText confpassword;
    TextInputEditText location;
    TextInputEditText email;
    TextInputLayout required1;
    TextInputLayout required2;
    TextInputLayout required3;
    TextInputLayout required4;
    TextInputLayout required5;
    TextView signupbtn;
    Boolean passwordmatch;
    Boolean emailvalid;
    ViewFlipper vf;
    Handler handler;
    RelativeLayout suRelativelayout;
    private static final String TAG = "SignUp";
    Customer customer;

    String key;
    String dblink;
    FirebaseDatabase fb;

    String phpsignup;
    private FirebaseAuth firebaseAuth;
    RequestQueue queue ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        RelativeLayout relativeLayout = findViewById(R.id.signuprelativelayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);

        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
        passwordmatch=false;
        emailvalid=false;
        //dblink= getResources().getString(R.string.dblink);
        dblink= getResources().getString(R.string.localhost);
        //infinity didnt work



        queue= Volley.newRequestQueue(this);
        key = getResources().getString(R.string.securitykey);



        phpsignup = getResources().getString(R.string.signupphp);







        try {
            
        
        vf=(ViewFlipper) findViewById(R.id.vf_signup) ;

        name = (TextInputEditText) findViewById(R.id.name_edittext);
        password = (TextInputEditText) findViewById(R.id.password_EditText);
        confpassword = (TextInputEditText) findViewById(R.id.passwordverification_EditText);
        email=(TextInputEditText) findViewById(R.id.email_EditText);
        location = (TextInputEditText) findViewById(R.id.location_EditText);

        required1=(TextInputLayout) findViewById(R.id.text1);
        required2=(TextInputLayout) findViewById(R.id.text2);
        required3=(TextInputLayout) findViewById(R.id.text3);
        required4=(TextInputLayout) findViewById(R.id.text4);
        required5=(TextInputLayout) findViewById(R.id.text5);
        signupbtn=(TextView) findViewById(R.id.signup_btn);
        suRelativelayout=(RelativeLayout) findViewById(R.id.signuprelativelayout);

        }
        
        catch (Exception e){

            Log.d(TAG, "onCreate: Item binding errors in signup main activity");
        }








        enablebutton();



        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                enablebutton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                enablebutton();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //email verification
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(email.getText().toString().isEmpty())
                    required2.setError(null);
                else {

                    if(isValidEmail(email.getText().toString()))
                        required2.setError(null);
                    else required2.setError("Please enter a valid email address");
                }

                enablebutton();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //password verification

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                required3.setError("");

                if(!password.getText().toString().isEmpty()){
                    if(isValidPassword(password.getText().toString()))
                    {


                        required3.setError(null);
                    }
                    else {
                        required3.setError("The password must be at least 8 characters long and include a number, lowercase letter and an uppercase letter");
                    }

                }



                if(password.getText().toString().equals(confpassword.getText().toString() ) || confpassword.getText().toString().isEmpty())
                {

                    required4.setError(null);
                    if(password.getText().toString().equals(confpassword.getText().toString() ) && !confpassword.getText().toString().isEmpty())
                    {
                        passwordmatch = true;

                    }

                    else{}

                }

                else {


                    required4.setError("Passwords Must Match!");
                    passwordmatch=false;

                }

                if(confpassword.getText().toString().isEmpty())
                    required4.setError(null);

                enablebutton();





            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // password match verification
        confpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(password.getText().toString().equals(confpassword.getText().toString()) && !confpassword.getText().toString().isEmpty())
                {required4.setError(null);
                passwordmatch=true;}


                else {

                 required4.setError("Passwords Must Match!");
                 passwordmatch = false;
                    Log.d(TAG, "onTextChanged: PASSWORDCONF : PASSWORDS DONT MATCH");

                }

                if(confpassword.toString().isEmpty())
                    required4.setError(null);


                enablebutton();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //log
        System.out.println(passwordmatch+"kkkkkkkkkkk");


      //  signupbtn.setEnabled(true);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if(enablebutton()){
                    CreateAccount(dblink,phpsignup,key);
                    Log.d(TAG, "onClick: SIGNUP");
                }
                else {
                    Log.d(TAG, "onClick: Enter All Values");
                }

                    

                






            }
        });





    }

    public void CreateAccount(String link,String signupphp,String securitykey)  {

        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        String userlocation = location.getText().toString();

        //button.setEnabled(false);
        loadingbar_on();

        Log.d(TAG, "CreateAccount: creating the account ");

        String todaysdate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        todaysdate = simpleDateFormat.format(new Date());
        final ProgressDialog loading = ProgressDialog.show(this,"Signing Up...","Please wait...",false,false);
        Customer newcustomer = new Customer(username,useremail,userpassword,userlocation,todaysdate,false);
        Log.d(TAG, "New Customer INfo: " + newcustomer.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link+signupphp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                String error;
                String errormessage;
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d(TAG, "onResponse: JSON OBJECT SUCCESS");
                     error = res.getString("error");
                     errormessage = res.getString("error_message");
                    Log.d(TAG, "onResponse: Signuperror::" + error);
                    Log.d(TAG, "onResponse: Signuperrormessage::" + errormessage);

                    if(error=="false"){
                        Log.d(TAG, "onResponse: ERROR IS FALSE SIGNUP SUCCEFUL");

                        name.setText(null);
                        email.setText(null);
                        password.setText(null);
                        location.setText(null);
                        confpassword.setText(null);
                        // TODO: 6/3/2021 Intent

                        Intent intent = new Intent(SignUp.this,LoginActivity.class);
                        startActivity(intent);
                        finish();



                    }
                    else {

                        Log.d(TAG, "onResponse:ERROR IS TRUE " + errormessage);
                    }

                }
                catch (JSONException e){
                    Log.d(TAG, "onResponse: JSON OBJECT FAILURE" +e.getMessage());
                }


                loadingbar_off();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: " + error);
                loadingbar_off();
            }
        } )
        { @Override
        protected Map<String,String> getParams() throws AuthFailureError {


            Map<String,String> params= new HashMap<>();
            params.put("email",useremail);
            params.put("name",username);
            params.put("password",userpassword);
            params.put("location",userlocation);
            params.put("date",todaysdate);
            params.put("key",securitykey);





            return params;

        }


        };

        queue.add(stringRequest);
    }




    public Boolean enablebutton(){

        if(!(password.getText().toString().isEmpty()
                && confpassword.getText().toString().isEmpty()
                && name.getText().toString().isEmpty()
                && location.getText().toString().isEmpty()
                && email.getText().toString().isEmpty() )




                && passwordmatch
                && isValidPassword(password.getText().toString())
                && isValidEmail(email.getText().toString())  ){


            Log.d(TAG, "enablebutton: enable");
        return true;
        }


        else{

            Log.d(TAG, "enablebutton: disabled");

            return false;

        }

    }





    public void loadingbar_on(){

        signupbtn.setText("");

        vf.setVisibility(View.VISIBLE);

    }

    public void loadingbar_off(){

        signupbtn.setText("Sign Up");
        vf.setVisibility(View.INVISIBLE);
    }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean isValidEmail(final String email){

        Pattern pattern;
        Matcher matcher;

        final String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(email);


        return matcher.matches();

    }

    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}