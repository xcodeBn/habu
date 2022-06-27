package com.example.habuonlineshop.Adapters.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.MutableCart;
import com.example.habuonlineshop.CustomerViewProduct;
import com.example.habuonlineshop.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CartMutableAdapter extends RecyclerView.Adapter<CartMutableAdapter.CartMutableHolder> {


    private Activity a;
    private Context c;
    private ArrayList<MutableCart> mutableCartArrayList;

    @NonNull
    @Override
    public CartMutableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart,parent,false);
        CartMutableHolder cartMutableHolder = new CartMutableHolder(view);

        return cartMutableHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartMutableHolder holder, int position) {

        Intent i = new Intent(a, CustomerViewProduct.class);
        Gson gson = new Gson();
        String pjson = gson.toJson(mutableCartArrayList.get(position));

        i.putExtra("product",pjson);

//        holder.parent.setOnClickListener();
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a.startActivityForResult(i, 2);
                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mutableCartArrayList.size();
    }

    public class CartMutableHolder extends RecyclerView.ViewHolder {

        ImageButton remove;
        TextView name;
        TextView price;
        ImageView image;
        CardView parent;

        public CartMutableHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cartimg);
            name = itemView.findViewById(R.id.cartname);
            price = itemView.findViewById(R.id.cartprice);
            remove = itemView.findViewById(R.id.cartremove);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
