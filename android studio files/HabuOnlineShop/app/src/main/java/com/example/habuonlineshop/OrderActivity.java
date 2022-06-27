package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Logout_Dialogue;
import com.example.habuonlineshop.Dialogues.OrderSuccess_dialogue;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {


    private static final String TAG = "OrderActivity";
    private ImageView logoutbtn;
    private ImageView gobackbtn;
    private Product product;
    private Activity activity;
    private TextInputEditText province;
    private TextInputEditText city;
    private TextInputEditText street;
    private TextView price;
    private Button order_btn;
    private Customer customer;
    private SharedInformation sharedInformation;
    private RequestQueue queue;
    private ViewFlipper vf;
    private LinearLayout parent;
    private OrderSuccess_dialogue success_dialogue;
    private Window success_window;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        logoutbtn = findViewById(R.id.logoutBtn);
        gobackbtn = findViewById(R.id.gobackiv);
        province = findViewById(R.id.province);
        city = findViewById(R.id.city);
        parent = findViewById(R.id.parent);
        street = findViewById(R.id.street);
        price = findViewById(R.id.price);
        sharedInformation = new SharedInformation(this);
        queue = Volley.newRequestQueue(this);
        activity = this;
        vf = findViewById(R.id.vf);

        success_dialogue = new OrderSuccess_dialogue(this);
        customer=sharedInformation.getUserCustomer();
        province = findViewById(R.id.province);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);

        success_window = success_dialogue.getWindow();
        success_window.setBackgroundDrawableResource(R.color.transperent);
        order_btn=findViewById(R.id.order);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOut(activity);

            }
        });

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        
        product = getProduct();

        String priced = product.getPrice().toString()+"$";

        price.setText(priced);

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
            }
        });


        
        

    }

    private void makeOrder() {

        vf.setDisplayedChild(1);

        String o_street = street.getText().toString();
        String o_city = city.getText().toString();
        String o_province = province.getText().toString();
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.makeorder);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String date = simpleDateFormat.format(new Date());

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){



                        Log.d(TAG, "onResponse: "  + error_message );
                        Snackbar snackbar = Snackbar.make(parent,"Error, please try again later",Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                        snackbar.show();
                    }
                    else {


                        Log.d(TAG, "onResponse: " + response);
                        success_dialogue.show();
                    }
                }

                catch (JSONException e){
                    e.printStackTrace();


                }
                vf.setDisplayedChild(0);

                Log.d(TAG, "onResponse: " + response);
                success_dialogue.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                vf.setDisplayedChild(0);
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

                Snackbar snackbar = Snackbar.make(parent,error.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                snackbar.show();
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("productid",product.getId().toString());
                params.put("pid",product.getName());
                params.put("email",customer.getEmail());
                params.put("province",o_province);
                params.put("city",o_city);
                params.put("street",o_street);
                params.put("progress","not delivered");
                params.put("price",product.getPrice().toString());
                params.put("date",date);

                return params;
            }
        };
        queue.add(request);


    }


    private Product getProduct(){

        Gson gson = new Gson();
        Product ka;


        String getjson = getIntent().getStringExtra("product");

        ka = gson.fromJson(getjson,Product.class);

        Log.d(TAG, "getProduct: " + ka.toString());
        return ka;
    }
    private void logOut(Activity a){


        Logout_Dialogue logout_dialogue = new Logout_Dialogue(a);

        Window logoutwindow = logout_dialogue.getWindow();

        logoutwindow.setBackgroundDrawableResource(R.color.transperent);
        logout_dialogue.show();
    }
    
    private void goBack(){

        finish();
    }
    @Override
    public void finish() {

        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}