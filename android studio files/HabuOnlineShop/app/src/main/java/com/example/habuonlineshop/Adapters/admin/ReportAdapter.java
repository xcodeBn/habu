package com.example.habuonlineshop.Adapters.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Reports;
import com.example.habuonlineshop.Dialogues.ChangeProWeight;
import com.example.habuonlineshop.Dialogues.DeleteReportDialogue;
import com.example.habuonlineshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportV>  {


    private static final String TAG = "ReportAdapter";
    private Context c;
    private Activity a;
    private ArrayList<Reports> reportsArrayList;
    private Integer previousExpandedPosition = -1;
    private Integer mExpandedPosition = -1;
    private RecyclerView recyclerView;


    public ReportAdapter(Context c, Activity a, ArrayList<Reports> reportsArrayList,RecyclerView recyclerView) {
        this.c = c;
        this.a = a;
        this.recyclerView = recyclerView;
        this.reportsArrayList = reportsArrayList;
    }

    @NonNull
    @Override
    public ReportV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.adapter_reports,parent,false);
        ReportV v = new ReportV(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportV holder, int position) {


        Reports reports = reportsArrayList.get(position);

        holder.bind(reports);




        //holder.details.setVisibility(reports.getExpanded()?View.VISIBLE : View.GONE);




       // final boolean isExpanded = position==mExpandedPosition;
        //holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
     //   holder.itemView.setActivated(isExpanded);

       // if (isExpanded)
          //  previousExpandedPosition = position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDeleteDialogue();

                new AlertDialog.Builder(c)
                        .setTitle("Delete Report?")
                        .setMessage("Do you really want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d(TAG, "onClick: yes");

                                deleteReport(reports);

                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                Log.d(TAG, "onClick: ");

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    mExpandedPosition = isExpanded ? -1:position;
//                notifyItemChanged(previousExpandedPosition);
//                notifyItemChanged(position);
//                TransitionManager.beginDelayedTransition(recyclerView);



                boolean expanded = reports.getExpanded();
                reports.setExpanded(!expanded);
                notifyItemChanged(position);
                if(expanded){
                    holder.expand.setText("click to collapse");
                }

                else {
                    holder.expand.setText("click to expand");
                }
               // notifyItemChanged(previousExpandedPosition);
            }
        });


    }

    @Override
    public int getItemCount() {
        return reportsArrayList.size();
    }
    private void deleteReport(Reports reports){

        String id = reports.getId().toString();
        String url = c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.deletereport);
        final ProgressDialog loading = ProgressDialog.show(c,"Deleting...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse() called with: response = [" + response + "]");

                loading.dismiss();
                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    if(error){


                        Toast.makeText(c,"Error",Toast.LENGTH_SHORT).show();


                    }
                    else {
                        Toast.makeText(c,"Success",Toast.LENGTH_SHORT).show();
                        reportsArrayList.remove(reports);
                        notifyDataSetChanged();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("id",id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(request);

    }


    public class ReportV extends RecyclerView.ViewHolder {

        TextView name;
        TextView title;
        TextView details;
        CardView parent;
        TextView expand;
        ImageButton button;
        Reports reports;
        TextView date;



        public ReportV(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            parent = itemView.findViewById(R.id.card);
            expand = itemView.findViewById(R.id.exp);
            button = itemView.findViewById(R.id.btn);
            date = itemView.findViewById(R.id.date);

        }

        private void bind(Reports r) {

            this.reports = r;
            boolean expanded = reports.getExpanded();


            details.setVisibility(expanded ? View.VISIBLE : View.GONE);


            date.setText(reports.getDate());

            title.setText(reports.getTitle());
            name.setText(reports.getName());
            details.setText(reports.getText());
            if(expanded){
                expand.setText("click to collapse");
            }

            else {
                expand.setText("click to expand");
            }





        }








    }


}
