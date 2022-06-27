package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.Design.Category_AdapterHolder;
import com.example.habuonlineshop.Adapters.Design.Product_AdapterHolder;
import com.example.habuonlineshop.Adapters.customer.GetProductAdapterCustomer;
import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.Classes.Product;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer_ViewProductActivity extends AppCompatActivity {

    TextView categorynametv;
    private static final String TAG = "Customer_ViewProductAct";
    //ImageView test;
    private ImageView goback;

    private RecyclerView holderrv;
    private RecyclerView productrv;
    private TextView errorbtn;
    private String productimgurl;
    private ViewFlipper flipper;
    ArrayList<Product> productArrayList;

    private String categoryname;
    private Integer categoryid;
    RequestQueue queue;
    SwipeRefreshLayout swipeRefreshLayout;
    String link;
    String productlink;
    String url;
    private RelativeLayout rl;

    Product_AdapterHolder holderadapter;
    GetProductAdapterCustomer getproductadapter;
    ArrayList<Holder> holderArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_top);


        link=getResources().getString(R.string.dblink);
        productlink = getResources().getString(R.string.getproducts);


        rl=findViewById(R.id.rl);
        setContentView(R.layout.activity_customer__view_product);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_viewproduct_customer);
        holderrv  = (RecyclerView) findViewById(R.id.rvholder_viewproducts_customer);
        productrv = (RecyclerView) findViewById(R.id.rv_viewproducts_customer);
        queue = Volley.newRequestQueue(this);
        categorynametv = (TextView) findViewById(R.id.username_toolbar);

        flipper = (ViewFlipper) findViewById(R.id.viewflipper_products);
        goback = (ImageView) findViewById(R.id.gobackiv);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        Intent y = getIntent();


        categoryname= y.getStringExtra("categoryname");
        categoryid = y.getIntExtra("categoryid",0);
        categorynametv.setText(categoryname);

        url = link + productlink +"?id=" + categoryid;

        Log.d(TAG, "onCreate: " + url);

        holderArrayList = new ArrayList<>();

        for(int i=0;i<9;i++){
            holderArrayList.add(new Holder(1,"1"));
        }
        holderadapter = new Product_AdapterHolder(getApplicationContext(),holderArrayList);
        holderrv.setAdapter(holderadapter);

        holderrv.setLayoutManager(new LinearLayoutManager(this));


        productArrayList = new ArrayList<>();
        getproductadapter = new GetProductAdapterCustomer(this,productArrayList,this);

        productrv.setAdapter(getproductadapter);
        LinearLayoutManager l = new LinearLayoutManager(this);
        productrv.setLayoutManager(l);







        getproducts();
        onSwipeRefresh();










    }

    public void getproducts(){


        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                flipper.setVisibility(View.INVISIBLE);
                if(response.length()==0){

                    flipper.setVisibility(View.VISIBLE);
                    flipper.setDisplayedChild(1);

                }
                swipeRefreshLayout.setRefreshing(false);
                productArrayList.clear();

                Log.d(TAG, "onResponse: " + response.toString());
                try {
                    JSONObject row ;

                    for (int i=0;i<response.length();i++){

                        row = response.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String weight = row.getString("weight");
                        String size = row.getString("size");
                        Integer category = row.getInt("category");
                        String image = row.getString("image");
                        Integer price = row.getInt("price");
                        Integer stock = row.getInt("stock");
                        Log.d(TAG, "onResponse: row " + i + " = " + row.toString()  );

                        productArrayList.add(new Product(id,name,weight,size,category,image,price,stock));
                        productArrayList.get(i).toString();
                        getproductadapter.notifyDataSetChanged();




                    }


                    holderrv.setVisibility(View.INVISIBLE);
                    productrv.setVisibility(View.VISIBLE);


                }
                catch (JSONException e){

                    Log.d(TAG, "onResponse: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
                error.printStackTrace();
                holderrv.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(rl,"Connection error please try again later",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(getColor(R.color.red));
                snackbar.show();
            }
        });

        queue.add(request);


    }


    public void onSwipeRefresh(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                productrv.setVisibility(View.INVISIBLE);
                holderrv.setVisibility(View.VISIBLE);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getproducts();
                    }
                },500);



            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}