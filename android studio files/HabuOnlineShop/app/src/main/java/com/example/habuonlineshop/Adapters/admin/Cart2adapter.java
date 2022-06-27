package com.example.habuonlineshop.Adapters.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.MutableCart;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.CustomerViewProduct;
import com.example.habuonlineshop.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart2adapter extends RecyclerView.Adapter<Cart2adapter.V> {

    private Context c;
    private Context context;
    private Activity a;
    private ArrayList<MutableCart> cartArrayList;

    private static final String TAG = "Cart2adapter";


    public Cart2adapter(Context c, Activity a,Context context, ArrayList<MutableCart> cartArrayList) {
        this.c = c;
        this.a = a;
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart,parent,false);
        V v = new V(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {





        String url = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getproductimage) +

                cartArrayList
                        .get(position)
                        .getImage();
      // getProduct(id,product);

        Picasso.get().load(url).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError: ");
            }
        });
        String pric = cartArrayList.get(position).getPrice().toString()+"$";
        holder.price.setText(pric);
        holder.name.setText(cartArrayList.get(position).getPname());
        String id = cartArrayList.get(position).getPid().toString();

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, CustomerViewProduct.class);
                i.putExtra("id",id);
                a.startActivityForResult(i, 2);
                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Remove Product?")
                        .setMessage("Do you really want to remove from cart?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d(TAG, "onClick: yes");

                                removeFromCart(cartArrayList.get(position));

                            }})
                        .setNegativeButton(android.R.string.no, null).show();





            }
        });
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    private void removeFromCart(MutableCart carts) {


        String url = a.getResources().getString(R.string.dblink) + a.getResources().getString(R.string.removefromcart);

        String id = carts.getPid().toString();

        SharedInformation sharedInformation = new SharedInformation(context);
        Customer customer = sharedInformation.getUserCustomer();

        final ProgressDialog loading = ProgressDialog.show(context,"Deleting...","Please wait...",false,false);
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
                        //dismiss();
                    }
                    else {
                        // dismiss();


                        cartArrayList.remove(carts);
                        notifyDataSetChanged();
                    }

                    loading.dismiss();


                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // dismiss();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

                loading.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params= new HashMap<>();
                params.put("pid",id);
                params.put("email",customer.getEmail());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public class V extends RecyclerView.ViewHolder{


        ImageButton remove;
        TextView name;
        TextView price;
        ImageView image;
        CardView parent;

        public V(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cartimg);
            name = itemView.findViewById(R.id.cartname);
            price = itemView.findViewById(R.id.cartprice);
            remove = itemView.findViewById(R.id.cartremove);
            parent = itemView.findViewById(R.id.parent);
        }


    }



}
