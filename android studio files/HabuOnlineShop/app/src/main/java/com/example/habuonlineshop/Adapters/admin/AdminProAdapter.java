package com.example.habuonlineshop.Adapters.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.EditPro;
import com.example.habuonlineshop.R;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminProAdapter extends RecyclerView.Adapter<AdminProAdapter.ViewHolder> {


    private ArrayList<Product> productArrayList;
    private ArrayList<Product> productArrayList_specific;
    private ArrayList<Category> categoryArrayList;
    private Context c;
    private Activity a;
    private static final String TAG = "AdminProAdapter";
    private ArrayList<Product> temp;


    public AdminProAdapter(ArrayList<Product> pr, ArrayList<Category> categoryArrayList, Context c, Activity a) {
        this.productArrayList_specific = pr;
        this.categoryArrayList = categoryArrayList;
        this.c = c;
        this.a = a;


    }

    public void setProductArrayList_specific(ArrayList<Product> productArrayList_specific) {
        this.productArrayList_specific = productArrayList_specific;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_admin_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //productArrayList = productArrayList_specific;

        String productimgurl = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getproductimage) + productArrayList_specific.get(position).getImage();


        String categoryimgurl = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getcategoryimage) +getCategoryImage(productArrayList_specific.get(position).getId()) ;



        Picasso.get().load(productimgurl).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError: ");
            }
        });




        holder.name.setText(productArrayList_specific.get(position).getName());

        holder.catname.setText(getCategoryName(productArrayList_specific.get(position).getCategory()));


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, EditPro.class);

                Product product = productArrayList_specific.get(position);

                Gson gson = new Gson();
                String json = gson.toJson(product);
                i.putExtra("product",json);


                a.startActivityForResult(i,2);

                a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });






    }

    @Override
    public int getItemCount() {
        return productArrayList_specific.size();
    }

    private String getCategoryImage(Integer id){

        for(int i=0;i<categoryArrayList.size();i++){

            if(categoryArrayList.get(i).getId()==id){


                return categoryArrayList.get(i).getImagename();
            }
        }

        return null;

    }


    private String getCategoryName(Integer id){

        for(int i=0;i<categoryArrayList.size();i++){

            if(categoryArrayList.get(i).getId()==id){


                return categoryArrayList.get(i).getName();
            }
        }

        return null;

    }





    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        //ImageView catimage;
        TextView catname;
        ImageButton edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivimage);
            name = itemView.findViewById(R.id.tvname);
            //catimage = itemView.findViewById(R.id.catimg);

            catname = itemView.findViewById(R.id.tvcatname);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
