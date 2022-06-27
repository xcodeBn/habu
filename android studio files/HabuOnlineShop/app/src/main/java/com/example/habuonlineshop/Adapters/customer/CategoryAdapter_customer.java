package com.example.habuonlineshop.Adapters.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Customer_ViewProductActivity;
import com.example.habuonlineshop.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;


import java.util.ArrayList;

public class CategoryAdapter_customer extends RecyclerView.Adapter<CategoryAdapter_customer.FeaturedViewHolder> {

    private ArrayList<Category> itemlist;
    private Context c;
    private Activity activity;
    private SharedInformation sharedInformation;
    private ImageView imageView;
    private TextView textView;
    private LinearLayoutManager linearLayoutManager ;
    private CardView cardViewl;



    private static final String TAG = "CategoryAdapter_customer";

    public CategoryAdapter_customer(ArrayList<Category> itemlist, Context c,Activity activity) {
        this.itemlist = itemlist;
        this.c = c;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customer_categories,parent,false);
        FeaturedViewHolder viewHolder = new FeaturedViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

            String url =c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getcategoryimage) +itemlist.get(position).getImagename();

            holder.itemname.setText(itemlist.get(position).getName());
        Log.d(TAG, "onBindViewHolder: url=: " + url);
            Picasso.get().load(url).into(holder.itemimage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: Image Loaded Succefully");
                        }

                        @Override
                        public void onError(Exception e) {

                            Log.d(TAG, "onError: loading error "+ e.getMessage());


                        }

                    });


                    holder.parentlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(activity, Customer_ViewProductActivity.class);

                            i.putExtra("categoryname", holder.itemname.getText());
                            i.putExtra("categoryid",itemlist.get(position).getId());
                            Log.d(TAG, "onClick: " + holder.itemname.getText());
                            activity.startActivityForResult(i, 2);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);




                        }
                    });


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class FeaturedViewHolder extends ViewHolder{


        CardView parentlayout;
        ImageView itemimage;
        TextView itemname;
        public FeaturedViewHolder(View itemview){
            super(itemview);
            parentlayout=itemview.findViewById(R.id.cardview_cat_customer_adapter);
            itemimage = itemview.findViewById(R.id.circularimageview_category_customer_adapter);
            itemname = itemview.findViewById(R.id.tv_customercategoryadapter);


        }
    }




}
