package com.example.habuonlineshop.Adapters.General;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Review;
import com.example.habuonlineshop.R;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context c;
    private ArrayList<Review> itemlist;

    private static final String TAG = "ReviewAdapter";
    public ReviewAdapter(Context c,ArrayList<Review> list) {

        this.itemlist = list;
        this.c = c;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_reviews,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Double rated = itemlist.get(position).getRate();



        holder.rate.setRating( itemlist.get(position).getRate().floatValue());
        settitle(itemlist.get(position).getRate().floatValue(),holder.title);

        holder.customername.setText("Customer");
        getcustomer(itemlist.get(position).getEmail(),holder.customername);

        holder.body.setText(itemlist.get(position).getText());








    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public void getcustomer(String email,TextView tvname){

        String url = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getcustomer);
        RequestQueue q = Volley.newRequestQueue(c);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject row = new JSONObject(response);
                    String name = row.getString("name");
                    tvname.setText(name);


                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();


                params.put("email",email);
                return params;
            }


        };

        q.add(request);

    }

    public void settitle(Float rate,TextView title){

        if(rate==0f){

            title.setText("Very Bad");
        }
        else if(rate==1f){
            title.setText("Very Bad");
        }
        else if(rate==2f){
            title.setText("Bad");
        }
        else if(rate==3f){
            title.setText("Average");
        }
        else if(rate==4f){
            title.setText("Good");
        }
        else if(rate==5f){
            title.setText("Very Good");
        }

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{



        TextView customername;
        TextView title;
        TextView body;
        RatingBar rate;
        public ReviewViewHolder(View itemview){


            super(itemview);
            customername = itemview.findViewById(R.id.username);
            title = itemview.findViewById(R.id.title);
            body = itemview.findViewById(R.id.body);
            rate = itemview.findViewById(R.id.rate);


        }

    }
}
