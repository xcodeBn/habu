package com.example.habuonlineshop.Adapters.customer;

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
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Cart;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.CustomerViewProduct;
import com.example.habuonlineshop.Dialogues.RemoveCartDialogue;
import com.example.habuonlineshop.R;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private static final String TAG = "CartAdapter";
private Context context;
private ArrayList<Product> productArrayList;
//private ArrayList<Cart> cartArrayList;
private Activity a;

    public CartAdapter(Activity a,Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
      //  this.cartArrayList = cartArrayList;
        this.a=a;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart,parent,false);

        CartHolder holder = new CartHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        String url = context.getResources().getString(R.string.dblink) + context.getResources().getString(R.string.getproductimage) +

                productArrayList
                        .get(position)
                        .getImage();

        RemoveCartDialogue dialogue = new RemoveCartDialogue(a,productArrayList.get(position));
        Window window = dialogue.getWindow();

        window.setBackgroundDrawableResource(R.color.transperent);

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

                                removeFromCart(productArrayList.get(position));

                            }})
                        .setNegativeButton(android.R.string.no, null).show();





            }
        });

        Picasso.get().load(url).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {

                Log.d(TAG, "onSuccess: Success");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        holder.name.setText(productArrayList.get(position).getName());

        String pric = productArrayList.get(position).getPrice().toString()+"$";
        holder.price.setText(pric);

        Intent i = new Intent(a, CustomerViewProduct.class);
        Gson gson = new Gson();
        String pjson = gson.toJson(productArrayList.get(position));

        i.putExtra("product",pjson);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a.startActivityForResult(i, 2);
                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void removeFromCart(Product product) {


        String url = a.getResources().getString(R.string.dblink) + a.getResources().getString(R.string.removefromcart);

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

                        productArrayList.remove(product);
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
                params.put("pid",product.getId().toString());
                params.put("email",customer.getEmail());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {

        ImageButton remove;
        TextView name;
        TextView price;
        ImageView image;
        CardView parent;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cartimg);
            name = itemView.findViewById(R.id.cartname);
            price = itemView.findViewById(R.id.cartprice);
            remove = itemView.findViewById(R.id.cartremove);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
