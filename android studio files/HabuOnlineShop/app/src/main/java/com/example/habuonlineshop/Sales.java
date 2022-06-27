package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.SalesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Sales extends AppCompatActivity {

    private TextView datetv;
    private RecyclerView salesrv;
    private TextView salestv;
    private TextView revenues;
    private TextView counttv;
    private ImageButton bck;
    private SalesAdapter adapter;
    private ArrayList<com.example.habuonlineshop.Classes.Sales> salesArrayList;
    private static final String TAG = "Sales";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        salestv=findViewById(R.id.salestv);
        datetv= findViewById(R.id.dateTextView);

        salesArrayList = new ArrayList<>();
        adapter = new SalesAdapter(salesArrayList,this);

        bck = findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        counttv = findViewById(R.id.count);
        revenues = findViewById(R.id.tvrev);
        salesrv = findViewById(R.id.rv);
        String todaysdate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        salesrv.setAdapter(adapter);
        salesrv.setLayoutManager(new LinearLayoutManager(this));


        todaysdate = simpleDateFormat.format(new Date());
        datetv.setText(todaysdate);

        getSumPrice();
        ItemsSold();
        getSales();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void getSumPrice(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.sumprice);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONArray a = new JSONArray(response);

                    JSONObject row = a.getJSONObject(0);
                    Double sum = row.getDouble("SUM(price)");
                    Log.d(TAG, "onResponse: " + sum);

                    salestv.setText(moneyFormatter(sum));

                    Double xxx = getRevenue(sum);

                    revenues.setText(moneyFormatter(xxx));
                    Log.d(TAG, "onResponse: " + xxx);
                }
                catch (JSONException e){

                    Log.d(TAG, "onResponse: ERRROR: + " + e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });
        RequestQueue q = Volley.newRequestQueue(this);
        q.add(request);
    }

    private void ItemsSold(){

        String url = getResources().getString(R.string.dblink) +getResources().getString(R.string.itemsold);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject a = array.getJSONObject(0);
                    String get = a.getString("COUNT(id)");
                    counttv.setText(get);
                }
                catch (JSONException e){
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getSales(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.sales);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                try {

                    for(int i =0;i<response.length();i++){

                        JSONObject row = response.getJSONObject(i);
                        Integer id = row.getInt("id");
                        Double price = row.getDouble("price");

                        salesArrayList.add(new com.example.habuonlineshop.Classes.Sales(id,moneyFormatter(price)));
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private String moneyFormatter(Double d){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = d;
        String formattedNumber = formatter.format(myNumber);
        formattedNumber = "$" +formattedNumber;

        return formattedNumber;

    }
    private Double getRevenue(Double sum){

        Double rev = (sum * 15)/100;
        return rev;
    }


}