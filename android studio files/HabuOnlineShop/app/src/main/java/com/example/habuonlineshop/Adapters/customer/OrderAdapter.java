package com.example.habuonlineshop.Adapters.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.Order;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewH> {

    private Context c;

    private ArrayList<Order> ordersArrayList;

    public OrderAdapter(Context c,  ArrayList<Order> ordersArrayList) {
        this.c = c;

        this.ordersArrayList = ordersArrayList;
    }



    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_orders,parent,false);

        ViewH viewH = new ViewH(view);
        return viewH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH holder, int position) {


        String price = ordersArrayList.get(position).getPrice() +"$";
        holder.price.setText(price);
        holder.progress.setText(ordersArrayList.get(position).getProgress());

        String del =ordersArrayList.get(position).getProvince() +"," + ordersArrayList.get(position).getCity() + "," + ordersArrayList.get(position).getStreet();
        holder.deli.setText(del);

        holder.recipient.setText(ordersArrayList.get(position).getRecipient());
        holder.name.setText(ordersArrayList.get(position).getPname());

    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }



    public class ViewH extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        TextView deli;
        TextView progress;
        TextView recipient;
        public ViewH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            deli = itemView.findViewById(R.id.deli);
            progress = itemView.findViewById(R.id.progress);
        recipient=itemView.findViewById(R.id.rec);

        }
    }
}
