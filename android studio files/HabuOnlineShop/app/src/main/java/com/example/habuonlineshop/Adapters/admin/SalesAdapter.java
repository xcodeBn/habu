package com.example.habuonlineshop.Adapters.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.Sales;
import com.example.habuonlineshop.R;

import java.util.ArrayList;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.Salesvh> {

    private ArrayList<Sales> sales;
    private Context c;

    public SalesAdapter(ArrayList<Sales> sales, Context c) {
        this.sales = sales;
        this.c = c;
    }

    @NonNull
    @Override
    public Salesvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate( R.layout.card_sales,parent,false);
        Salesvh salesvh = new Salesvh(v);
        return salesvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Salesvh holder, int position) {

        Sales sales1 = sales.get(position);

        holder.id.setText(sales1.getId().toString());
        holder.price.setText(sales1.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public class Salesvh extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView price;

        public Salesvh(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tvcash);
            id = itemView.findViewById(R.id.id);
        }
    }
}
