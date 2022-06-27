package com.example.habuonlineshop.Fragments.Admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.ReportAdapter;
import com.example.habuonlineshop.Classes.Reports;
import com.example.habuonlineshop.Dialogues.DeleteReportDialogue;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.ViewModels.ReportViewModel;
import com.google.common.escape.Escaper;
import com.google.common.escape.UnicodeEscaper;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminReportsFragment extends Fragment  {



    private static final String TAG = "AdminReportsFragment";
    private RecyclerView rv;
    private RequestQueue queue;
    private Context c;
    private ArrayList<Reports> test;
    private ReportAdapter testadapter;
    private ArrayList<Reports> reportsArrayList;

    public AdminReportsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_report,container,false);


//        ReportViewModel reportViewModel = new ReportViewModel(getActivity().getApplication());
//        MutableLiveData<ArrayList<Reports>>  arrayListMutableLiveData= new MutableLiveData<>();
//
//        arrayListMutableLiveData = reportViewModel.getProductLiveData();



//        ArrayList<Reports> testa = new ArrayList<>();
//
//        testa =arrayListMutableLiveData.getValue();
//
//
//        testa.add(new Reports(1,"a","a","a","a"));
//
//        Log.d(TAG, "onCreateView: LIVE DATA TEST:" + testa.toString());

        reportsArrayList = new ArrayList<>();
        c = getContext();
        rv = view.findViewById(R.id.rv);
        queue = Volley.newRequestQueue(c);
        test = new ArrayList<>();
        testadapter = new ReportAdapter(c,getActivity(),reportsArrayList,rv);


        test.add(new Reports(1,"test",
                "Good",
                "AIDSJASDJ AISD JAID JAID JAID JAISDJ AISDJAIDJAISD JAISD JAIDJAIDJ AIS JD",
                "9/10/2020"));
        test.add(new Reports(1,"test",
                "Weewe",
                "AIDSJASDJ Aadas d asdsa dwac a dsaAISD JAIDJAIDJ AIS JD",
                "9/10/2020"));
        test.add(new Reports(1,"test",
                "Baddd ",
                "AIDSJASDJ AISD JAID JAID JAID JAISDJ AISDJAIDJAISD JAISD JAIDJAIDJ AIS JD",
                "9/10/2020"));


        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        testadapter.notifyDataSetChanged();
        rv.setAdapter(testadapter);
        rv.setLayoutManager(new LinearLayoutManager(c));

        ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(true);
       // rv.setHasFixedSize(true);


        getReports();



        return view;
    }

    private String date(){

        String a = "a/a";
        String b = "";

        //a.replace("\/","/");
        return null;
    }

    private void getReports(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getreports);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    reportsArrayList.clear();
                    for (int i = 0 ; i<response.length() ; i++ ){


                        JSONObject row = response.getJSONObject(i);


                        Integer id = row.getInt("id");

                        String title = row.getString("title");
                        String text = row.getString("text");



                        String date = row.getString("date");
                        date.replace("/","");
                        String name = row.getString("name");

                        Reports reports = new Reports(id,name,title,text,date);

                        Log.d(TAG, "onResponse: " + date);

                        Log.d(TAG, "onResponse() called with: response = [" + response + "]");

                        reportsArrayList.add(reports);
                        testadapter.notifyDataSetChanged();


                    }

                }

                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });
        queue.add(request);


    }


    @Override
    public void onResume() {
        super.onResume();
        getReports();
    }
}
