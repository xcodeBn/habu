package com.example.habuonlineshop.Adapters.General;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpinnerSearchAdapter extends ArrayAdapter<Category> {

    private static final String TAG = "SpinnerSearchAdapter";


    public SpinnerSearchAdapter(Context c, ArrayList<Category> itemlist){
        super(c,0,itemlist);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return super.getItem(position);
    }


    private View initView(int position, View convertview, ViewGroup parent){

        if(convertview==null){
            convertview= LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_search_spinner,parent,false
            );

        }


        TextView name = convertview.findViewById(R.id.categoryname_sp);
        Category currentcategory = getItem(position);

        String url =getContext().getResources().getString(R.string.dblink) + getContext().getResources().getString(R.string.getcategoryimage) +currentcategory.getImagename();

        if(!currentcategory.equals(null)) {
//            Picasso.get().load(url).into(catimage, new Callback() {
//                @Override
//                public void onSuccess() {
//                    Log.d(TAG, "onSuccess: Success");
//                }
//
//                @Override
//                public void onError(Exception e) {
//
//                    e.printStackTrace();
//                    Log.d(TAG, "onError: " + e.getMessage());
//                }
//            });


            name.setText(currentcategory.getName());
        }


        return convertview;


    }
}
