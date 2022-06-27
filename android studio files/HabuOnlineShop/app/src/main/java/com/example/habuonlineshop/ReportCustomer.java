package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Appfunctions;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportCustomer extends AppCompatActivity {

    private ImageView goback;
    private TextView activityname;
    private ViewFlipper vf;
    private Button submit;
    private TextInputEditText subject;
    private TextInputEditText body;
    private Date date;
    private String todaysdate;

    private SharedInformation sharedInformation;
    private Customer customer;
    private String email;
//    private String securitykey;
    private String dblink;
    private String reports;
    private String url;
    private RequestQueue queue;
    private LinearLayout reportlay;
    private Activity a;
    private Appfunctions appfunctions;
    private static final String TAG = "ReportCustomer";









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer);

        activityname = findViewById(R.id.username_toolbar);


        a=this;

        queue = Volley.newRequestQueue(getApplicationContext());
        reportlay = findViewById(R.id.reportlay);
        vf = findViewById(R.id.vfreport);
        submit = findViewById(R.id.submitbtn);
        subject = findViewById(R.id.subject);
        body = findViewById(R.id.body);
        sharedInformation = new SharedInformation(this);

        dblink = getResources().getString(R.string.dblink);
        reports = getResources().getString(R.string.report);


        url = dblink+reports;

        date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        todaysdate = simpleDateFormat.format(new Date());

        customer = sharedInformation.getUserCustomer();

        email =customer.getEmail();

                appfunctions = new Appfunctions(a,getApplicationContext());

        activityname.setText("Report a problem");
        goback= findViewById(R.id.gobackiv);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appfunctions.hideKeyboard(a);

                if(subject.getText().toString().isEmpty()
                ||body.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(reportlay,"Please enter all required values",Snackbar.LENGTH_SHORT);

                    snackbar.setBackgroundTint(getColor(R.color.red));
                    snackbar.show();
                }
                else {

                    newreport();

                }
            }
        });
    }


    public void newreport(){

        vf.setDisplayedChild(1);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){

                        Log.d(TAG, "onResponse: " + error_message);
                    }

                    else {

                        Snackbar snackbar = Snackbar.make(reportlay,"Report sent succefully!",Snackbar.LENGTH_INDEFINITE).setAction("go back", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                finish();

                            }
                        }).setActionTextColor(getResources().getColor(R.color.white));
                        snackbar.setBackgroundTint(getColor(R.color.green));
                        body.setText("");
                        subject.setText("");
                        Log.d(TAG, "onResponse: Success");
                    }


                }
                catch (JSONException e){

                    e.printStackTrace();
                    Log.d(TAG, "onResponse:Error=true -"  +e.getMessage());
                    vf.setDisplayedChild(0);

                }

                vf.setDisplayedChild(0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Snackbar snackbar = Snackbar.make(reportlay,"Error",Snackbar.LENGTH_SHORT);

                snackbar.setBackgroundTint(getColor(R.color.red));

                snackbar.show();
                vf.setDisplayedChild(0);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("email",email);
                params.put("title",subject.getText().toString());
                params.put("text",body.getText().toString());
                params.put("date",todaysdate);
                return params;
            }
        };

        queue.add(request);


    }


    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}