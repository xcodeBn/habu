package com.example.habuonlineshop.Fragments.Customer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.customer.CategoryAdapter_customer;
import com.example.habuonlineshop.Adapters.Design.Category_AdapterHolder;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.Holder;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {

    private static final String TAG = "CustomerHomeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private SharedInformation sharedInformation;
    private RecyclerView category_rv;
    ShimmerFrameLayout shimmerFrameLayout ;
    private String dblink;
    private String getimage;
    private String categoryimageurl;
    private ViewFlipper viewFlipper;
    CategoryAdapter_customer getcategoryadapter;
    String url ;
    private Category_AdapterHolder holderadapter;
    private RecyclerView holder_rv;
    private ArrayList<Category> arrayList;


    private RequestQueue queue;

    public CustomerHomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_customerhome,container,false);

        dblink = view.getResources().getString(R.string.dblink);
        String getcategory = view.getResources().getString(R.string.getcategory);

        url = dblink + getcategory;

        queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById((R.id.swiperefresh_customerhome_fragment));
         sharedInformation = new SharedInformation(getActivity().getApplicationContext());

         //viewFlipper = (ViewFlipper) view.findViewById(R.id.)
         shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.custmerfragmentshrimmerframelayout);


          viewFlipper = (ViewFlipper) view.findViewById(R.id.vf);





         category_rv = (RecyclerView) view.findViewById(R.id.recyclercategory_customerhome_fragment);
      //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        //category_rv.setLayoutManager(linearLayoutManager);

        category_rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));

         arrayList = new ArrayList<>();

        ArrayList<Holder> animationarrayList = new ArrayList<>();

        for(int i =0;i<9;i++) {
            animationarrayList.add(new Holder(1, "2"));
        }




        Log.d(TAG, "onCreateView: " + arrayList);
       // arrayList.add(new Category(2,"Wawa",R.drawable.welcome));

         getcategoryadapter = new CategoryAdapter_customer(arrayList,getActivity().getApplicationContext(),getActivity());

         holder_rv = (RecyclerView) view.findViewById(R.id.recyclerviewcategoryholder_customerhomefragment);
         holderadapter = new Category_AdapterHolder(getActivity().getApplicationContext(),animationarrayList);


         Category_AdapterHolder animholder = new Category_AdapterHolder(getActivity().getApplicationContext(),animationarrayList);


        holder_rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));

        holder_rv.setAdapter(animholder);
        holderadapter.notifyDataSetChanged();

        category_rv.setAdapter(getcategoryadapter);




        getcategories();


        SwipeRefresh();
//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getcategoryadapter.notifyDataSetChanged();
//                category_rv.setVisibility(View.VISIBLE);
//            }
//        },500);


        TextView tryagain = (TextView) view.findViewById(R.id.tryagain_btn_error);

        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcategories();
            }
        });


        return view;


    }

public void getcategories(){


        viewFlipper.setVisibility(View.GONE);
        holder_rv.setVisibility(View.VISIBLE);


    Log.d(TAG, "getcategories: " + url);
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            holder_rv.setAdapter(holderadapter);

            arrayList.clear();

            swipeRefreshLayout.setRefreshing(false);

            try {
                for(int i=0;i<response.length();i++){

                    JSONObject row = response.getJSONObject(i);
                    Integer id = row.getInt("id");
                    String name = row.getString("name");
                    String image = row.getString("image");

                    arrayList.add(new Category(id,name,image));
                    //arrayList.notify();
                    getcategoryadapter.notifyDataSetChanged();
                    holderadapter.notifyDataSetChanged();
                    category_rv.setVisibility(View.INVISIBLE);

//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            holder_rv.setVisibility(View.INVISIBLE);
//                            category_rv.setVisibility(View.VISIBLE);
//
//                        }
//                    },500);

                    category_rv.setVisibility(View.VISIBLE);
                    holder_rv.setVisibility(View.INVISIBLE);

                }





            }

            catch (JSONException e){
                Log.d(TAG, "onResponse: " + e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            swipeRefreshLayout.setRefreshing(false);

            viewFlipper.setVisibility(View.VISIBLE);
            viewFlipper.setDisplayedChild(0);
            holder_rv.setVisibility(View.INVISIBLE);
            category_rv.setVisibility(View.INVISIBLE);

            Log.d(TAG, "onErrorResponse: " + error);
        }
    });
    queue.add(jsonArrayRequest);
}



public void SwipeRefresh(){


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        category_rv.setVisibility(View.INVISIBLE);
                        getcategories();
                    }
                },500);
            }
        });
}

}
