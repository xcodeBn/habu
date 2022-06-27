package com.example.habuonlineshop.Adapters.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habuonlineshop.Classes.User;
import com.example.habuonlineshop.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserV> {

    private ArrayList<User> users;
    private Context c;
    private Activity a;


    public UserAdapter(ArrayList<User> users, Context c, Activity a) {
        this.users = users;
        this.c = c;
        this.a = a;
    }

    @NonNull
    @Override
    public UserV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_user,parent,false);
        UserV v = new UserV(view);


        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull UserV holder, int position) {
        User user = users.get(position);

        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserV extends RecyclerView.ViewHolder{

        TextView name;
        TextView email;
        public UserV(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvcash);
            email = itemView.findViewById(R.id.id);


        }
    }
}
