package com.example.habuonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.admin.UserAdapter;
import com.example.habuonlineshop.Classes.User;
import com.example.habuonlineshop.Dialogues.SendNotificationDialogue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Users extends AppCompatActivity {


    private ArrayList<User> usersArrayList;
    private RecyclerView rv;
    private UserAdapter adapter;
    private RequestQueue queue;
    private TextView userscount;
    private ImageButton goback;
    private SendNotificationDialogue dialogue;
    private FloatingActionButton send;

    private static final String TAG = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        rv = findViewById(R.id.rv);

        dialogue = new SendNotificationDialogue(this);
        userscount = findViewById(R.id.salestv);
        usersArrayList = new ArrayList<>();
        goback = findViewById(R.id.bck);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        queue = Volley.newRequestQueue(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(usersArrayList,this,this);

        send = findViewById(R.id.fab);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = dialogue.getWindow();
                window.setBackgroundDrawableResource(R.color.transperent);
                dialogue.show();
            }
        });

        rv.setAdapter(adapter);

        getUsers();
    }

    private void getUsers(){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.getusers);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

               try {

                   String count = response.length() + "";
                   userscount.setText(count);

                  for(int i = 0;i<response.length();i++){

                      JSONObject row = response.getJSONObject(i);
                      String name = row.getString("name");
                      String email = row.getString("email");

                      usersArrayList.add(new User(email,name));
                      adapter.notifyDataSetChanged();
                  }
               }
               catch (JSONException e){
                   Log.d(TAG, "onResponse() called with: response = [" + response + "]");
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        });
        queue.add(request);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}