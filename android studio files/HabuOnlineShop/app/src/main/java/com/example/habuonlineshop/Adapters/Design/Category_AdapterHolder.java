package com.example.habuonlineshop.Adapters.Design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.R;

import java.util.ArrayList;

public class Category_AdapterHolder extends RecyclerView.Adapter<Category_AdapterHolder.FeaturedViewHolder> {


    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;


    private ArrayList<Holder> holderlist;
    private CardView cardView;

    public Category_AdapterHolder(Context context, ArrayList<Holder> arrayList) {
        this.context = context;
        this.holderlist = arrayList;
    }

    @Override
    public int getItemCount() {
        return holderlist.size();
    }

    public Category_AdapterHolder(Context context, RecyclerView recyclerView, ArrayList<Holder> arrayList){


        this.context=context;
        this.recyclerView = recyclerView;
        this.holderlist = arrayList;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customer_home_recyclerviewholder,parent,false);
        FeaturedViewHolder viewHolder = new FeaturedViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

    }



    public class FeaturedViewHolder extends RecyclerView.ViewHolder {




        public FeaturedViewHolder(View itemview){

            super(itemview);


        }



    }
}
