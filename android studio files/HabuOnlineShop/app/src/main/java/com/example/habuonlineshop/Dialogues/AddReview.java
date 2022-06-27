package com.example.habuonlineshop.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class AddReview extends Dialog implements View.OnClickListener {

    private Context c;
    private Product product;
    private Customer customer;
    private SharedInformation sharedInformation;
    private RequestQueue queue;
    private RatingBar ratingBar;
    private static final String TAG = "AddReview";
    private TextView title;
    private Button add;
    private TextInputEditText body;
    private ViewFlipper vf;

    public AddReview(@NonNull Context context, Product product) {
        super(context);
        this.c = context;
        this.product=product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_review);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        queue = Volley.newRequestQueue(c);
        sharedInformation = new SharedInformation(c);
        customer = sharedInformation.getUserCustomer();

        ratingBar = findViewById(R.id.rate);

        title = findViewById(R.id.title);
        add = findViewById(R.id.add);

body = findViewById(R.id.body);
vf=findViewById(R.id.vf);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


                if(rating<1f){
                    ratingBar.setRating(1f);
                }

                settitle(ratingBar.getRating(),title);

            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addReview();

            }
        });




    }
    private void addReview(){


        String titleP = title.getText().toString();
        String email = customer.getEmail();
        String pid = product.getId().toString();
        String text = body.getText().toString();
        String rate = String.valueOf(ratingBar.getRating());
        vf.setDisplayedChild(1);


        String url = c.getResources().getString(R.string.dblink)  + c.getResources().getString(R.string.addreview);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);


                vf.setDisplayedChild(0);
                Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },1000);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                vf.setDisplayedChild(0);



            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("email",email);
                params.put("pid",pid);
                params.put("title",titleP);
                params.put("text",text);
                params.put("rate",rate);

                return params;
            }


        };
        queue.add(request);

    }


    private String gettitle(){
        String title="";


        return title;
    }

    public void settitle(Float rate, TextView title){

        //Log.d(TAG, "settitle: Ratingrate:" + rate );
        if(rate==0f){

            title.setText("Very Bad");
        }
        else if(rate.floatValue()==1f){
            title.setText("Very Bad");
        }
        else if(rate==2f){
            title.setText("Bad");
        }
        else if(rate.floatValue()==3f){
            title.setText("Average");
        }
        else if(rate==4f){
            title.setText("Good");
        }
        else if(rate==5f){
            title.setText("Very Good");
        }

    }
    @Override
    public void onClick(View v) {

    }
}
