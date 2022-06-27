package com.example.habuonlineshop.Dialogues;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.habuonlineshop.R;

public class ChangeProSize extends AppCompatDialogFragment {


    private EditText name;
    private ChangeProSizeListener listener;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_change_cat_name,null);

        builder.setView(view).setTitle("New Size").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String newname = name.getText().toString();

                listener.changeProSize(newname);

            }
        });

        name = view.findViewById(R.id.catname);

        name.setHint("Enter New Size");

       name.setInputType(InputType.TYPE_CLASS_TEXT);
        name.setMaxLines(1);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChangeProSizeListener) context;
        }
        catch (ClassCastException e){
            throw  new  ClassCastException(context.toString() + "Must implement the dialogue listener");



        }
    }



    public interface ChangeProSizeListener {
        void changeProSize(String size);
    }

}