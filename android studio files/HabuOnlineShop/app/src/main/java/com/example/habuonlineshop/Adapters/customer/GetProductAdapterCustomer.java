package com.example.habuonlineshop.Adapters.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.AdminMainActivity;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.CustomerMainActivity;
import com.example.habuonlineshop.CustomerViewProduct;
import com.example.habuonlineshop.Customer_ViewProductActivity;
import com.example.habuonlineshop.EditPro;
import com.example.habuonlineshop.Fragments.Admin.AdminSearchFragment;
import com.example.habuonlineshop.R;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class GetProductAdapterCustomer extends RecyclerView.Adapter<GetProductAdapterCustomer.FeuteredViewHolder> {


    private Activity a;
    private Context c;
    private static final String TAG = "GetProductAdapterCustom";
    private ArrayList<Product> itemlist;
    //private RecyclerView rv;




    public GetProductAdapterCustomer(Context c, ArrayList<Product> list,Activity a) {
        this.c = c;
        this.itemlist = list;
        this.a=a;


    }




    @NonNull
    @Override
    public FeuteredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_getproducts_customer,parent,false);

        FeuteredViewHolder viewHolder = new FeuteredViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeuteredViewHolder holder, int position) {


        String url = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getproductimage) + itemlist.get(position).getImage() ;



        Picasso.get().load(url).into(holder.productimage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError: " + e.getMessage());
            }
        });
        holder.productname.setText(itemlist.get(position).getName());
        String price = ""  + itemlist.get(position).getPrice() + "$";
        holder.productprice.setText(price);


        Intent i = new Intent(a, CustomerViewProduct.class);
        Intent j = new Intent(a, EditPro.class);


        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String pjson = gson.toJson(itemlist.get(position));
                i.putExtra("product",  pjson);
                j.putExtra("product",pjson);

                i.putExtra("id",itemlist.get(position).getId().toString());
                Log.d(TAG, "onClick:RRR " + a.toString());


               if(a.getClass().getName().equals(AdminMainActivity.class.getName())){
                   a.startActivityForResult(j,2);
                   a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

               }

                else if(a.getClass().getName().equals(Customer_ViewProductActivity.class.getName())){
                    a.startActivityForResult(i,2);
                    a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public class FeuteredViewHolder extends RecyclerView.ViewHolder{


        CardView parentlayout;
        TextView productname;
        TextView productprice;
        ImageView productimage;

        public FeuteredViewHolder(View view){

            super(view);

            parentlayout = view.findViewById(R.id.getproducts_cv);
            productname = view.findViewById(R.id.productname_viewproductadapter);
            productprice = view.findViewById(R.id.productprice_getproductsadapter);
            productimage = view.findViewById(R.id.viewproducts_productimage);

        }

    }
}
