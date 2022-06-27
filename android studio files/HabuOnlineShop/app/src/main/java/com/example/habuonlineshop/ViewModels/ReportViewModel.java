package com.example.habuonlineshop.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Reports;
import com.example.habuonlineshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class ReportViewModel extends AndroidViewModel {

    private static final String TAG = "ReportViewModel";
    private MutableLiveData<ArrayList<Reports>> reportslivedata;
    private ArrayList<Reports> reportsArrayList;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    //private Application application;

    public ReportViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Reports>> getProductLiveData () {
        if(reportsArrayList == null) {
            reportslivedata = new MutableLiveData<>();
            initialize();
        }
        return reportslivedata;
    }

    private void initialize(){
        reportJsonRequests();
        reportslivedata.setValue(reportsArrayList);
    }

    private void reportJsonRequests() {

        String url = getApplication().getResources().getString(R.string.dblink) + getApplication().getResources().getString(R.string.getreports);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    reportsArrayList = new ArrayList<>();
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

                        Log.d(TAG, "onResponse: LIVE DATA TEST");

                        //testadapter.notifyDataSetChanged();


                    }

                    Log.d(TAG, "onResponse: " + reportsArrayList.toString());

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
        RequestQueue queue = Volley.newRequestQueue(getApplication());

        queue.add(request);
    }


}
