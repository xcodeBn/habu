package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.habuonlineshop.Classes.Reports;
import com.example.habuonlineshop.R;

public class DeleteReportDialogue extends AppCompatDialogFragment {

    private Activity a;
    private Boolean delete;
    private DeleteReport listener;
    private Reports reports;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_dlt_report,null);

        delete = false;


        builder.setView(view).setTitle("Delete Report?").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delete = true;


              // listener.dltReport(reports.getId().toString());

            }
        });
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteReport) context;
        }
        catch (ClassCastException e){
            throw  new  ClassCastException(context.toString() + "Must implement the dialogue listener");



        }

    }

    public interface DeleteReport {
        void dltReport(String id);
    }
}
