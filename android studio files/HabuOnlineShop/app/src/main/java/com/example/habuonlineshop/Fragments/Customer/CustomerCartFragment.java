package com.example.habuonlineshop.Fragments.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter2;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.Cart2adapter;
import com.example.habuonlineshop.Adapters.customer.CartAdapter;
import com.example.habuonlineshop.Classes.Cart;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.MutableCart;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.ViewModels.CartViewModel;
import com.example.habuonlineshop.ViewOrders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CustomerCartFragment extends Fragment {

    private View view;
    private Customer customer;
    private SharedInformation sharedInformation;
    private static final String TAG = "CustomerCartFragment";
    private Context context;
    private Activity activity;
    private RecyclerView productrv;
    private CartAdapter cartAdapter;
    private Product product;
    private ArrayList<Product> productArrayList;
    private ArrayList<Cart> cartArrayList;
    private ViewFlipper viewFlipper;
    private RequestQueue queue;
    private Button orders;
    private ArrayList<MutableCart> mutableCartArrayList;
    private Cart2adapter cart2adapter;
    //private RecyclerView getProductrv;

    private CartViewModel cartViewModel;



    public CustomerCartFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_customer_cart,container,false);

        context = getActivity().getApplicationContext();
        activity = getActivity();
        sharedInformation = new SharedInformation(context);
        viewFlipper = view.findViewById(R.id.vf_cart);



        mutableCartArrayList = new ArrayList<>();
        cartViewModel =  new ViewModelProvider(this).get(CartViewModel.class);
//        cartViewModel.getCartArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cart>>() {
//            @Override
//            public void onChanged(ArrayList<Cart> carts) {
//
//
//
//            }
//        });

        orders = view.findViewById(R.id.orders);
        cartArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();

        productrv = view.findViewById(R.id.cartrv);


        if(Objects.isNull(sharedInformation.getUserCustomer())){

            getActivity().finish();
            customer = new Customer(" "," "," "," "," ",true);
        }

        else {
            customer = sharedInformation.getUserCustomer();
        }



        queue = Volley.newRequestQueue(context);
       // getCart();


        cartAdapter = new CartAdapter(activity,getContext(),productArrayList);

        getMutableCart();

        cart2adapter = new Cart2adapter(getContext(),getActivity(),getContext(),mutableCartArrayList);
        productrv.setAdapter(cart2adapter);
        productrv.setLayoutManager(new LinearLayoutManager(context));

        //cartAdapter.notifyDataSetChanged();
        cartAdapter.notifyDataSetChanged();

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(activity, ViewOrders.class);
                startActivity(i);

                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Refresh();





        check();


        return view;
    }

    private void check(){
        Log.d(TAG, "check() called");

        if(mutableCartArrayList.isEmpty()){
            empty();
        }
    }

    private void getMutableCart(){


        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.advancedcart) +"?email=" + customer.getEmail();

        loading();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mutableCartArrayList.clear();

                try {

                    JSONArray array = new JSONArray(response);
                    for (Integer i = 0;i<array.length();i++){

                        JSONObject row = array.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String image = row.getString("image");
                        String email = row.getString("email");
                        String name = row.getString("name");
                        Integer price = row.getInt("price");

                        mutableCartArrayList.add(new MutableCart(email,id,name,image,price));

                        Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                        Log.d(TAG, "onResponse: " );
                        cart2adapter.notifyDataSetChanged();

                    }

                    if(mutableCartArrayList.isEmpty()){
                        empty();
                    }
                    else {
                        notempty();
                    }

                }
                catch (JSONException e){
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }

                cart2adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error();
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });
        queue.add(request);

    }


    private void fillcart(){


        loading();
        if(cartArrayList.isEmpty()){
            empty();
            return;

        }
        notempty();

    }


    private void loading(){

        viewFlipper.setDisplayedChild(2);
    }

    private void error(){
        viewFlipper.setDisplayedChild(1);
    }

    private void empty(){
        viewFlipper.setDisplayedChild(3);
    }

    private void notempty(){
        viewFlipper.setDisplayedChild(0);
    }


    private void refreshCart(){
        Log.d(TAG, "refreshCart: Refreshed");
        // TODO: 6/2/2021 Cart refresh

        getMutableCart();

    }
    private void getCart(){



        cartArrayList.clear();
        loading();
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.viewcart);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONArray array = new JSONArray(response);



                    if(array.length()==0){

                        empty();
                    }

                    else {

                        for(int i =0;i<array.length();i++){
                            JSONObject row = array.getJSONObject(i);

                            Integer id = row.getInt("id");
                            String email = row.getString("email");
                            Integer pid = row.getInt("product");

                            cartArrayList.add(new Cart(id,email,pid));

                            Log.d(TAG, "onResponse: " + cartArrayList.get(i).toString());

                        }

                        Log.d(TAG, "onResponse: Cart:" +cartArrayList.size() );
                        fillcart();

                        Log.d(TAG, "onResponse: Card" + productArrayList.size());


                    }
                }
                catch (JSONException e){

                    Log.d(TAG, "onResponse: ");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshCart();
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshCart();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCart();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshCart();
    }
}
