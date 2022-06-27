package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.AdminProAdapter;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminViewProCat extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue queue;
    private ArrayList<Product> productArrayList;
    private static final String TAG = "AdminViewProCat";
    private RecyclerView productrv;
    private RecyclerView holderrv;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView goback;
    private ArrayList<Category> categoryArrayList;
    private AdminProAdapter adminProAdapter;
    private FloatingActionButton addproduct;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_pro_cat);
        closeKeyboard();
        queue = Volley.newRequestQueue(this);
        productArrayList = new ArrayList<>();
        goback = findViewById(R.id.gobackiv);
        categoryArrayList = new ArrayList<>();
        adminProAdapter = new AdminProAdapter(productArrayList,categoryArrayList,this,this);

        swipeRefreshLayout = findViewById(R.id.rf);
        searchView = findViewById(R.id.search);
        closeKeyboard();
        productrv=findViewById(R.id.productsrv);
        goback.setOnClickListener(this::onClick);

        getcategories();
        getProducts();

        productrv.setLayoutManager(new LinearLayoutManager(this));
        productrv.setAdapter(adminProAdapter);

        //adminProAdapter.setTemp(productArrayList);

        setupSearchView();
        search();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //adminProAdapter.getFilter().filter(newText);

                searchProduct(newText);

                if(newText.isEmpty()){

                    getProducts();
                }



                return false;
            }



        });

        setSwipeRefreshLayout();


        addproduct = findViewById(R.id.fabadd);
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(AdminViewProCat.this,AddProduct.class);
                startActivity(j);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });





//        floatButton();



    }

//    private void floatButton(){
//
//
//        addproduct.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN){
//
//                    Float  oldXvalue = event.getX();
//                    Float oldYvalue = event.getY();
//
//                }
//                else if (event.getAction() == MotionEvent.ACTION_MOVE  ){
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(event.getRawX() - (v.getWidth() / 2)), (int)(event.getRawY() - (v.getHeight())));
//                    v.setLayoutParams(params);
//                }
//
//
//                return true;
//            }
//        });
////        addproduct.setOnTouchListener(new OnTouchListener() {
////            public boolean onTouch(View v, MotionEvent me){
////                if (me.getAction() == MotionEvent.ACTION_DOWN){
////                    oldXvalue = me.getX();
////                    oldYvalue = me.getY();
////                    Log.i(myTag, "Action Down " + oldXvalue + "," + oldYvalue);
////                }else if (me.getAction() == MotionEvent.ACTION_MOVE  ){
////                    LayoutParams params = new LayoutParams(v.getWidth(), v.getHeight(),(int)(me.getRawX() - (v.getWidth() / 2)), (int)(me.getRawY() - (v.getHeight())));
////                    v.setLayoutParams(params);
////                }
////                return true;
////            }
////        });
//    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(goback)){

            finish();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        getProducts();
        adminProAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getProducts();
        adminProAdapter.notifyDataSetChanged();
    }

    private void setupSearchView(){


        searchView.setQueryHint("Search for a product");
        searchView.getQuery();
        searchView.setIconifiedByDefault(true);


        searchView.setQueryHint(HtmlCompat.fromHtml("<font color = #BF0096>Search for a product </font>"  ,HtmlCompat.FROM_HTML_MODE_LEGACY) );
        searchView.setIconified(true);
        searchView.setSubmitButtonEnabled(true);

    }

    private void search(){

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

    }

    private void searchProduct(String name){


        if (name.isEmpty()){
            Log.d(TAG, "searchProduct() called with: name = [" + name + "]");
            return;
        }

        String url = getResources().getString(R.string.dblink)+getResources().getString(R.string.searchprocat) + "?name=" + name;
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    productArrayList.clear();
                    for(int i =0; i< response.length() ; i++){



                        JSONObject row = response.getJSONObject(i);

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
                        adminProAdapter.notifyDataSetChanged();

                    }
                }
                catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }

    private void getProducts(){


        String url = getResources().getString(R.string.dblink)+getResources().getString(R.string.getallproducts);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    productArrayList.clear();
                    adminProAdapter.notifyDataSetChanged();

                    for(int i =0; i< response.length() ; i++){

                        JSONObject row = response.getJSONObject(i);

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
                        adminProAdapter.notifyDataSetChanged();

                    }
                }
                catch (JSONException e){
                    Log.e(TAG, "onResponse: ", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void getcategories(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getcategory);


        //viewFlipper.setVisibility(View.GONE);
       // holder_rv.setVisibility(View.VISIBLE);


        Log.d(TAG, "getcategories: " + url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

               // holder_rv.setAdapter(holderadapter);

                categoryArrayList.clear();

             //   swipeRefreshLayout.setRefreshing(false);

               // displayHolder();

                try {
                    for(int i=0;i<response.length();i++){

                        JSONObject row = response.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String image = row.getString("image");



                        categoryArrayList.add(new Category(id,name,image));
                        Log.d(TAG, "onResponse: " + row.toString());
                        //arrayList.notify();
                        //getcategoryadapter.notifyDataSetChanged();
                        //holderadapter.notifyDataSetChanged();

                        adminProAdapter.notifyDataSetChanged();





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






                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void setSwipeRefreshLayout(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(searchView.getQuery().length()==0){
                            getcategories();
                        }

                        else {
                            searchProduct(searchView.getQuery().toString());
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                },500);
            }
        });
    }

    private void closeKeyboard() {
        View currentFocus = this.getCurrentFocus();
        if (currentFocus!=null){
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager)this.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}