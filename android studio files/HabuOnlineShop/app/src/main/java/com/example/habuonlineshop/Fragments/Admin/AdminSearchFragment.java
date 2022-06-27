package com.example.habuonlineshop.Fragments.Admin;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.Design.Product_AdapterHolder;
import com.example.habuonlineshop.Adapters.customer.GetProductAdapterCustomer;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminSearchFragment extends Fragment {

    private View view;
    private SearchView searchView;
    private ProgressBar progressBar;
    private Spinner spinner;
    private ArrayList<Category> categories;
    private ArrayList<String> categoryArrayList;
    private RequestQueue q;
    private static final String TAG = "GeneralSearchFragment";
    private String categoryurl;
    private ArrayAdapter<String> spinnerAdapter;
    private String producturl;
    private ArrayList<Product> productArrayList;
    private RecyclerView productsrv;
    private GetProductAdapterCustomer productAdapter;
    private Product_AdapterHolder holderadapter;
    private ArrayList<Holder> holderArrayList;
    private SwipeRefreshLayout swiperefresh;
    private ViewFlipper searchvf;
    private Context c;
    public AdminSearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_general_search,container,false);

        searchView = view.findViewById(R.id.searchview_customer);
        spinner = view.findViewById(R.id.searchspinner_cus);
        searchView.setQueryHint("Search for a product");
        searchView.getQuery();
        searchView.setIconifiedByDefault(false);

        c = getContext();
        searchView.setQueryHint(HtmlCompat.fromHtml("<font color = #BF0096>Search for a product </font>"  ,HtmlCompat.FROM_HTML_MODE_LEGACY) );
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(false);


        categoryArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();
        q= Volley.newRequestQueue(getContext());
        searchvf=view.findViewById(R.id.viewflipper_search);
        categoryurl = view.getResources().getString(R.string.dblink) + view.getResources().getString(R.string.getcategory);

        //  holderrv = view.findViewById(R.id.searchholder_rv);


        swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_searchfragment);



        holderArrayList = new ArrayList<>();

        holderArrayList.add(new Holder(1,"1"));
        holderArrayList.add(new Holder(1,"1"));
        holderArrayList.add(new Holder(1,"1"));
        holderadapter = new Product_AdapterHolder(getActivity().getApplicationContext(),holderArrayList);


        //holderrv.setAdapter(holderadapter);
        //holderadapter.notifyDataSetChanged();
        productsrv = (RecyclerView) view.findViewById(R.id.productsrv_search);
        producturl = view.getResources().getString(R.string.dblink) + view.getResources().getString(R.string.searchproducts);
        spinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinneritem,categoryArrayList);

        categories = new ArrayList<>();

        spinner.setAdapter(spinnerAdapter);
//        progressBar.setVisibility(View.INVISIBLE);

        productAdapter = new GetProductAdapterCustomer(c,productArrayList,getActivity());

        productsrv.setAdapter(productAdapter);
        productsrv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        holderrv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        holderrv.setVisibility(View.GONE);




        fillspinner();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //   search(query);
//                test(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()) {
                    productArrayList.clear();
                    productAdapter.notifyDataSetChanged();
                }
                else {
                    search(newText);
                }


                return false;
            }
        });

        String query = searchView.getQuery().toString();


        refresh(query);
        return view;
    }
    private void fillspinner(){


        JsonArrayRequest request= new JsonArrayRequest(categoryurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                categoryArrayList.clear();
                categories.clear();



                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject row = response.getJSONObject(i);
                        Log.d(TAG, "onResponse: " );

                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String image = row.getString("image");

                        categories.add(new Category(id,name,image));


                        categoryArrayList.add(name);
                        spinnerAdapter.notifyDataSetChanged();

                    }
                }
                catch (Exception e){

                    Log.d(TAG, "onResponse:catch error: " + e);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                fillspinner(); //retry until success

            }
        });

        q.add(request);



    }
    private void search(String query){

        searchvf.setVisibility(View.VISIBLE);
        searchvf.setDisplayedChild(0);

        swiperefresh.setRefreshing(false);
//        holderrv.setVisibility(View.VISIBLE);
        productsrv.setVisibility(View.INVISIBLE);
        Log.d(TAG, "onQueryTextSubmit:  submit : " + query );
        //progressBar.setVisibility(View.VISIBLE);
        Integer id =0;
        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getName().equals(spinner.getSelectedItem().toString()))
                id = categories.get(i).getId();
            Log.d(TAG, "onQueryTextSubmit: id" + id);
        }

        String url = producturl +"?name=" + query + "&category="+id;
        Log.d(TAG, "onQueryTextSubmit: url:" + url);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                searchvf.setVisibility(View.INVISIBLE);
                productArrayList.clear();
                productAdapter.notifyDataSetChanged();
                Log.d(TAG, "onResponseJSONARRAYRESPONSE: " + response.toString());

                try {
                    for (int i = 0; i<response.length();i++){
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
                        productAdapter.notifyDataSetChanged();


                    }

                    productsrv.setVisibility(View.VISIBLE);
//                    holderrv.setVisibility(View.INVISIBLE);
                }
                catch (JSONException e){
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

                error.printStackTrace();

                productsrv.setVisibility(View.INVISIBLE);
                searchvf.setVisibility(View.INVISIBLE);



            }
        });

        q.add(request);

    }
    private void  refresh(String query){

        productsrv.setVisibility(View.INVISIBLE);
//        holderrv.setVisibility(View.VISIBLE);
        Handler handler = new Handler();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(query.isEmpty())
                        {

                            swiperefresh.setRefreshing(false);
//                            holderrv.setVisibility(View.INVISIBLE);
                        }
                        else {
                            fillspinner();
                            search(query);
                        }
                    }
                },500);

            }
        });


    }
}
