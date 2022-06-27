package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminChangePassword extends AppCompatActivity {
    private static final String TAG = "AdminChangePassword";
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
    private RelativeLayout parent;
    private ImageView goback;
    private String changepassword;
    private String dblink;
    private String url;
    private String securitykey;
    private String newpassword;
    private String useremail;
    private SharedInformation sharedInformation;
    private String getcurrentpass;
//    private Customer customer;
    private Appfunctions appfunctions;
    private Admin admin;
    private Activity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);

        TextView t = findViewById(R.id.username_toolbar);

        t.setText("Change Password");

        appfunctions = new Appfunctions(this,getApplicationContext());
        a= this;
        queue = Volley.newRequestQueue(getApplicationContext());
        currentpass = findViewById(R.id.currentpassword);
        progressBar = findViewById(R.id.loading);
        submit = findViewById(R.id.submitbtn);
        card = findViewById(R.id.card);
        view = findViewById(R.id.view);
        goback= findViewById(R.id.gobackiv);
        sharedInformation = new SharedInformation(this);

        admin = sharedInformation.getAdmin();
        currentpasslay = findViewById(R.id.oldpasslayout);
        newpasslay = findViewById(R.id.newpasslayout);
        confpasslay = findViewById(R.id.confirmpasslayout);


        newpass = findViewById(R.id.newpassword);
        confpass = findViewById(R.id.confirmnewpassword);
        newpassword = newpass.getText().toString();
        parent = findViewById(R.id.parent);

        //customer = sharedInformation.getUserCustomer();




        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vf = findViewById(R.id.vfchangepass);

        changepassword = getResources().getString(R.string.adminchangepass);
        dblink = getResources().getString(R.string.dblink);
        url = dblink + changepassword;

        securitykey = getResources().getString(R.string.securitykey);

        parent = findViewById(R.id.parent);








        newpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(newpass.getText().toString().isEmpty() ){


                    newpasslay.setError("");

                }


                if(isValidPassword() && !newpass.getText().toString().isEmpty()){
                    newpasslay.setError("");
                }
                if(!isValidPassword() && !newpass.getText().toString().isEmpty()){
                    newpasslay.setError("The password must be at least 8 characters long and include a number, lowercase letter and an uppercase letter ");
                }





            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                confpasslay.setError("");

                if(confpass.getText().toString().isEmpty()  ){
                    confpasslay.setError("");
                }

                else if(match() && !confpass.getText().toString().isEmpty()){
                    confpasslay.setError("");
                    Log.d(TAG, "onTextChanged: Match:" +match().toString() );
                }

                if(!match() && !confpass.getText().toString().isEmpty()){
                    confpasslay.setError("Passwords must match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        String get = admin.getPassword();
        String get2 = sharedInformation.getAdmin().getPassword();

        Log.d(TAG, "onCreate: Get1:" + get + "get2:" + get2);






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newpassword = newpass.getText().toString();
                useremail =  sharedInformation.getAdmin().getEmail();




                if(newpassword.isEmpty()
                        ||useremail.isEmpty()
                        || confpass.getText().toString().isEmpty()
                        || currentpass.getText().toString().isEmpty()
                        || !match()
                        || !isValidPassword()
                        || !iscurrentpass()){

                    Log.d(TAG, "onClick: PLease Enter Correct Values");
                    try {

                        appfunctions.hideKeyboard(a);

                        Snackbar snackbar = Snackbar.make(parent,"Please enter correct values",Snackbar.LENGTH_SHORT).setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        }).setActionTextColor(getColor(R.color.white));

                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                        snackbar.show();
                        Toast t =new Toast(a);
                        t.makeText(a.getApplicationContext(),"Please enter validvalues",Toast.LENGTH_LONG).show();

                    }
                    catch (Exception e){

                        Log.d(TAG, "onClick: ToastException" + e.getMessage());
                        e.printStackTrace();
                    }





                }
                else {
                    appfunctions.hideKeyboard(a);
                    changepassword();
                }

            }
        });
    }

    public void changepassword(){


        vf.setDisplayedChild(1);

        Log.d(TAG, "changepassword: Started" );

        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                vf.setDisplayedChild(0);

                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){
                        Log.d(TAG, "onResponse: " + error_message);

                        Snackbar snackbar = Snackbar.make(parent,error_message,Snackbar.LENGTH_INDEFINITE).setAction("try again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                changepassword();
                            }
                        }).setActionTextColor(getColor(R.color.white));

                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                        snackbar.show();


                    }

                    else {

                        newpass.setText("");
                        confpass.setText("");
                        currentpass.setText("");

                        admin.setPassword(newpassword);

                        sharedInformation.setAdmin(admin);

                        Snackbar snackbar = Snackbar.make(parent,"Success",Snackbar.LENGTH_INDEFINITE).setAction("go back", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                finish();

                            }
                        }).setActionTextColor(getResources().getColor(R.color.white));
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        snackbar.show();
                    }

                }
                catch (JSONException e){
                    vf.setDisplayedChild(0);
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: Error");
                error.printStackTrace();

            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params= new HashMap<>();
                params.put("key",securitykey);
                params.put("email",useremail);
                params.put("password",newpassword);

                return params;
            }
        };
        queue.add(request.setRetryPolicy(new DefaultRetryPolicy(5000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));





    }

    public Boolean match(){

        String password = newpass.getText().toString();


        if(password.equals(confpass.getText().toString()))
            return true;

        else return false;

    }

    public boolean isValidPassword() {

        String password = newpass.getText().toString();
        if (password.isEmpty()){
            return false;
        }


        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public Boolean iscurrentpass(){
       // customer = sharedInformation.getUserCustomer();
admin = sharedInformation.getAdmin();

        String oldpass = admin.getPassword();
        String enteredpass = currentpass.getText().toString();

        if(oldpass.equals(enteredpass))
            return true;
        else return false;



    }





    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}