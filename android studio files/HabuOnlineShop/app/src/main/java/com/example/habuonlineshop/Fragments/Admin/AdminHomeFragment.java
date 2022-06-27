package com.example.habuonlineshop.Fragments.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.habuonlineshop.AdminCategoryActivity;
import com.example.habuonlineshop.AdminProductActivity;
import com.example.habuonlineshop.AdminViewProCat;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.Sales;
import com.example.habuonlineshop.Users;

import java.lang.reflect.Method;

public class AdminHomeFragment extends Fragment {

    private CardView categorycard;
    private CardView productcard;
    private CardView salescard;
    private CardView userscard;
    public AdminHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_home,container,false);

        categorycard = view.findViewById(R.id.categorycard);

        userscard = view.findViewById(R.id.users);
        userscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss = new Intent(getActivity(), Users.class);
                startActivity(ss);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        salescard = view.findViewById(R.id.Salescard);
        salescard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), Sales.class);
                startActivity(k);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        productcard = view.findViewById(R.id.procard);

        productcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getActivity(), AdminViewProCat.class);
                startActivity(j);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


        categorycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminCategoryActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });



        return view;
    }

    private  void dosomething(){

    }
}
