package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.Design.Category_AdapterHolder;
import com.example.habuonlineshop.Adapters.admin.CatProAdapter;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AdminProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView goback;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<Product> productArrayList;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private SharedInformation sharedInformation;
    private RecyclerView category_rv;
    private RecyclerView holder_rv;
    private Category_AdapterHolder holderadapter;
    private ArrayList<Holder> holderArrayList;
    private ArrayList<Category> arrayList;
    private ViewFlipper viewFlipper;
    private RequestQueue queue;
    private CatProAdapter catProAdapter;



    ShimmerFrameLayout shimmerFrameLayout ;

    private static final String TAG = "AdminProductActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        categoryArrayList = new ArrayList<>();
        goback = findViewById(R.id.gobackiv);
        sharedInformation = new SharedInformation(this);
        category_rv  = findViewById(R.id.categoryrv);
        holder_rv = findViewById(R.id.holderrv);
        viewFlipper = findViewById(R.id.vf);
        arrayList = new ArrayList<>();
        holderArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        holderadapter = new Category_AdapterHolder(this,holderArrayList);

        catProAdapter = new CatProAdapter(arrayList,this,this);

        holderArrayList.add(new Holder(1,""));


        category_rv.setLayoutManager(new GridLayoutManager(this,3));
        category_rv.setAdapter(catProAdapter);

        holder_rv.setAdapter(holderadapter);
        holder_rv.setLayoutManager(new GridLayoutManager(this,3));
        fillHolder();







        goback.setOnClickListener(this::onClick);

        try {
            getcategories();
            swipeRefresh();
        }
        catch (Exception e){
            Log.e(TAG, "onCreate: ", e );
            e.printStackTrace();
            Log.d(TAG, "onCreate: Error" +e.getMessage());
        }




    }






    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        Log.d(TAG, "finish() called");

    }

    public void getcategories(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getcategory);


        viewFlipper.setVisibility(View.GONE);
        holder_rv.setVisibility(View.VISIBLE);


        Log.d(TAG, "getcategories: " + url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                holder_rv.setAdapter(holderadapter);

                arrayList.clear();

                swipeRefreshLayout.setRefreshing(false);

                displayHolder();

                try {
                    for(int i=0;i<response.length();i++){

                        JSONObject row = response.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String image = row.getString("image");



                        arrayList.add(new Category(id,name,image));
                        Log.d(TAG, "onResponse: " + row.toString());
                        //arrayList.notify();
                        //getcategoryadapter.notifyDataSetChanged();
                        //holderadapter.notifyDataSetChanged();

                        catProAdapter.notifyDataSetChanged();
                        category_rv.setVisibility(View.INVISIBLE);

                        hideHolder();




                    }





                }

                catch (JSONException e){
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    Log.e(TAG, "onResponse: ",e );
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);



                onError();
                holder_rv.setVisibility(View.INVISIBLE);
                category_rv.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void fillHolder(){

        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderArrayList.add(new Holder(1,""));
        holderadapter.notifyDataSetChanged();
    }

    private void displayHolder(){
        holder_rv.setVisibility(View.VISIBLE);
        category_rv.setVisibility(View.INVISIBLE);
    }

    private void hideHolder(){


        category_rv.setVisibility(View.VISIBLE);
        holder_rv.setVisibility(View.INVISIBLE);
    }
    private void onError(){

        viewFlipper.setVisibility(View.VISIBLE);
        viewFlipper.setDisplayedChild(0);


    }

    private void swipeRefresh(){


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                displayHolder();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        getcategories();
                    }
                },500);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v.equals(goback)){

            finish();
        }

    }
}