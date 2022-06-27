package com.example.habuonlineshop.Adapters.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.EditCategory;
import com.example.habuonlineshop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CategoryAdapteradmin extends RecyclerView.Adapter<CategoryAdapteradmin.ViewHolder> {

    ArrayList<Category> itemlist;
    Activity activity;
    Context c;
    private static final String TAG = "CategoryAdapteradmin";

    public CategoryAdapteradmin(ArrayList<Category> itemlist, Activity activity, Context c) {
        this.itemlist = itemlist;
        this.activity = activity;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_admin,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url =c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getcategoryimage) +itemlist.get(position).getImagename();
        holder.categoryname.setText(itemlist.get(position).getName());
        Picasso.get().load(url).into(holder.categoryimage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess() called");
            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }
        });


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, EditCategory.class);
                Gson gson = new Gson();
                String json = gson.toJson(itemlist.get(position));

                i.putExtra("category",json);

                activity.startActivityForResult(i,2);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryimage;
        TextView categoryname;

        ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryimage = itemView.findViewById(R.id.categoryiv);
            categoryname = itemView.findViewById(R.id.categoryname);

            button = itemView.findViewById(R.id.btn);
        }
    }
}
