package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.customer.OrderAdapter;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.Order;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Logout_Dialogue;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOrders extends AppCompatActivity {

    private ViewFlipper vf;
    private RecyclerView rv;
    private TextView tryagain;
    private RequestQueue queue;
    private SharedInformation sharedInformation ;
    private Customer customer;
    private static final String TAG = "ViewOrders";
    private ArrayList<Order> orders_al;
    private ArrayList<Product> products_al;
    private OrderAdapter orderAdapter;
    private ImageView goback;
    private ImageView logout;

    private Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        vf = findViewById(R.id.vf);
        rv = findViewById(R.id.rv);
        queue = Volley.newRequestQueue(this);

        a=this;

        goback= findViewById(R.id.gobackiv);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        products_al = new ArrayList<>();
        orders_al = new ArrayList<>();

        logout = findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_Dialogue logout_dialogue = new Logout_Dialogue(a);

                Window window = logout_dialogue.getWindow();
                window.setBackgroundDrawableResource(R.color.transperent);
                logout_dialogue.show();
            }
        });
        orderAdapter = new OrderAdapter(this,orders_al);

        rv.setAdapter(orderAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));



        sharedInformation = new SharedInformation(this);
        customer = sharedInformation.getUserCustomer();


        getOrders();

        tryagain = findViewById(R.id.tryagain_btn_error);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrders();
            }
        });


    }


    private void getOrders(){

        loading();
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getorders);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0;i<jsonArray.length();i++){





                        JSONObject row  = jsonArray.getJSONObject(i);



                        Integer id = row.getInt("id");
                        String pid = row.getString("pname");
                        String customer_emaill = row.getString("customer_email");
                        String province = row.getString("province");
                        String city = row.getString("city");
                        String street = row.getString("street");
                        String progress = row.getString("progress");
                        Integer price = row.getInt("price");
                        String date= row.getString("date");
                        String recipient = row.getString("recipient");

                        String k = "";
                        date.replace("/","");

                        Log.d(TAG, "onResponse:ORDERS " + date);

                        orders_al.add(new Order(id,pid,customer_emaill,province,city,street,progress,price,date,recipient));


                        orderAdapter.notifyDataSetChanged();


                        Log.d(TAG, "onResponse:ORDERS " + row.toString());
                    }

                    disapper();

                    if(orders_al.isEmpty()){

                        noOrders();
                    }
                }

                catch (JSONException e){
                    e.printStackTrace();
                    error();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                error();
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
    private void error(){

        vf.setVisibility(View.VISIBLE);
        vf.setDisplayedChild(1);
    }
    private void loading(){

        rv.setVisibility(View.INVISIBLE);
        vf.setVisibility(View.VISIBLE);
        vf.setDisplayedChild(0);
    }

    private void disapper(){
        vf.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }
    private void noOrders(){

        vf.setVisibility(View.VISIBLE);
        vf.setDisplayedChild(2);
        rv.setVisibility(View.INVISIBLE);
    }
    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}