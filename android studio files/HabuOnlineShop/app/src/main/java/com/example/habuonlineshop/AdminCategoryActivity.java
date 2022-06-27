package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.CategoryAdapteradmin;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private SharedInformation sharedInformation;
    private CircleImageView goback;
    private RequestQueue queue;
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapteradmin categoryAdapteradmin;
    private RecyclerView rv;
    private static final String TAG = "AdminCategoryActivity";
    private FloatingActionButton add;
    private SwipeRefreshLayout swipe;
    private Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        swipe = findViewById(R.id.refresh);
        categoryArrayList = new ArrayList<>();
        sharedInformation = new SharedInformation(this);

        a=this;
        rv = findViewById(R.id.rv);
        categoryAdapteradmin = new CategoryAdapteradmin(categoryArrayList,this,this);
        queue = Volley.newRequestQueue(this);
        goback = findViewById(R.id.gobackiv);

        add= findViewById(R.id.add);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminCategoryActivity.this,AddCategory.class);
                startActivity(i);
                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        rv.setAdapter(categoryAdapteradmin);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getCategories();

        refresh();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategories();
    }

    private void getCategories(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getcategory);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                categoryArrayList.clear();

                try {
                    for(int i=0;i<response.length();i++) {

                        JSONObject row = response.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String image = row.getString("image");

                        categoryArrayList.add(new Category(id, name, image));
                        //arrayList.notify();
                        categoryAdapteradmin.notifyDataSetChanged();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    private void refresh(){

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCategories();
                        swipe.setRefreshing(false);
                    }
                },500);
            }
        });





    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        Log.d(TAG, "finish() called");

    }


}