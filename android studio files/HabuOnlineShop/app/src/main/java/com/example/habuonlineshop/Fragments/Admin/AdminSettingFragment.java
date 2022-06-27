package com.example.habuonlineshop.Fragments.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.habuonlineshop.AdminChangePassword;
import com.example.habuonlineshop.Classes.Admin;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.AboutUsDialogue;
import com.example.habuonlineshop.Dialogues.Privacypolictdialogue;
import com.example.habuonlineshop.Dialogues.TOSDialogue;
import com.example.habuonlineshop.R;

public class AdminSettingFragment extends Fragment {

    private TextView username ;
    private SharedInformation sharedInformation;
    private Admin admin;
    private Context c;
    private TextView email;
    private ImageButton changepassword;
    private Privacypolictdialogue p;
    private ImageView privacybtn;
    private ImageView tosbtn;
    private TOSDialogue tosDialogue;
    private ImageView aboutusbtn;
    private AboutUsDialogue aboutUsDialogue;
    public AdminSettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_setting,container,false);

        username = view.findViewById(R.id.usernametv);
        email = view.findViewById(R.id.emailtvsetting);
        c = getContext();
        sharedInformation = new SharedInformation(c);

        p=new Privacypolictdialogue(getActivity());

        privacybtn= (ImageView) view.findViewById(R.id.privacypolicy_btn);

        privacybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window privacywindow = p.getWindow();

                privacywindow.setBackgroundDrawableResource(R.color.transperent);
                p.show();
            }
        });
        changepassword = view.findViewById(R.id.changepasswordbtn);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminChangePassword.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        tosDialogue = new TOSDialogue(getActivity());
        tosbtn = (ImageView) view.findViewById(R.id.tosbtn);
        tosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window toswindow = tosDialogue.getWindow();
                toswindow.setBackgroundDrawableResource(R.color.transperent);
                tosDialogue.show();
            }
        });

        aboutUsDialogue = new AboutUsDialogue(getActivity());
        aboutusbtn = view.findViewById(R.id.aboutusbtn);
        aboutusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window aboutis = aboutUsDialogue.getWindow();
                aboutis.setBackgroundDrawableResource(R.color.transperent);
                aboutUsDialogue.show();
            }
        });


        admin = sharedInformation.getAdmin();

        bindValues(admin);
        return view;
    }

    private void bindValues(Admin admin){
        username.setText(admin.getName());
        email.setText(admin.getEmail());
    }
}
