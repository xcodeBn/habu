package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoveCartDialogue extends Dialog implements View.OnClickListener {


    private static final String TAG = "RemoveCartDialogue";

   private Activity a;
   private Product product;
   private TextView yes;
   private TextView no;
   private RequestQueue queue;
   private Customer customer;
   private SharedInformation sharedInformation;

    public RemoveCartDialogue(@NonNull Activity a,Product product) {
        super(a);
        this.a=a;
        this.product=product;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_remove_cart);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        yes = findViewById(R.id.logoutyes_tv);
        no = findViewById(R.id.logoutnos_tv);
        sharedInformation = new SharedInformation(a.getApplicationContext());
        customer=sharedInformation.getUserCustomer();

        queue = Volley.newRequestQueue(a.getApplicationContext());


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeFromCart();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void removeFromCart() {

        String url = a.getResources().getString(R.string.dblink) + a.getResources().getString(R.string.removefromcart);

        //vf_cart.setDisplayedChild(1);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   vf_cart.setDisplayedChild(0);

                try {

                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){
                        Log.d(TAG, "onResponse: " + error_message);
                        dismiss();
                    }
                    else {
                        dismiss();
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dismiss();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params= new HashMap<>();
                params.put("pid",product.getId().toString());
                params.put("email",customer.getEmail());
                return params;
            }
        };
        queue.add(request);
    }
    @Override
    public void onClick(View v) {

    }
}
