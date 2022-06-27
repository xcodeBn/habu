package com.example.habuonlineshop.Adapters.Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Adapters.customer.GetProductAdapterCustomer;
import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Product_AdapterHolder extends RecyclerView.Adapter<Product_AdapterHolder.FeutredViewHolder> {

    private ArrayList<Holder> itemlist;
    private Context c;

    public Product_AdapterHolder( Context c,ArrayList<Holder> itemlist) {
        this.itemlist = itemlist;
        this.c = c;
    }

    @NonNull
    @Override
    public FeutredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_products_holder,parent,false);
        FeutredViewHolder viewHolder = new FeutredViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeutredViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class FeutredViewHolder extends RecyclerView.ViewHolder {

        public FeutredViewHolder(View itemview){

            super(itemview);
        }


    }
}
