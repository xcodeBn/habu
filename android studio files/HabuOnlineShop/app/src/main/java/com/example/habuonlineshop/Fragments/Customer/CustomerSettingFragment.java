package com.example.habuonlineshop.Fragments.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.example.habuonlineshop.ChangePassword;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.AboutUsDialogue;
import com.example.habuonlineshop.Dialogues.ChangePasswordDialogue;
import com.example.habuonlineshop.Dialogues.Privacypolictdialogue;
import com.example.habuonlineshop.Dialogues.TOSDialogue;
import com.example.habuonlineshop.R;
import com.example.habuonlineshop.ReportCustomer;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class CustomerSettingFragment extends Fragment {

    private View view;

    private ImageView privacybtn;
    private ImageView aboutusbtn;
    private ImageView tosbtn;
    private ImageView changepasswordbtn;
    private TextView username;
    private TextView userpassword;
    private TextView useremail;
    private SharedInformation sharedInformation;
    private Customer customer;
    private RequestQueue q;
    private SwitchMaterial notificaitonsswtich;
    private ImageView report;
    TOSDialogue tosDialogue;
    Privacypolictdialogue p;
    AboutUsDialogue aboutUsDialogue;
    ChangePasswordDialogue changePasswordDialogue;
    public CustomerSettingFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_customer_setting,container,false);


        notificaitonsswtich = (SwitchMaterial) view.findViewById(R.id.switch_favnotf);

        sharedInformation = new SharedInformation(getActivity().getApplicationContext());


        privacybtn= (ImageView) view.findViewById(R.id.privacypolicy_btn);


        notificaitonsswtich.setChecked(sharedInformation.getnotificaiton());
        p = new Privacypolictdialogue(getActivity());
        tosDialogue = new TOSDialogue(getActivity());

        aboutUsDialogue = new AboutUsDialogue(getActivity());
        notificaitonsswtich.setSoundEffectsEnabled(true);

        notificaitonsswtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedInformation.enablenotifcation(isChecked);
            }
        });

        privacybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window privacywindow = p.getWindow();

                privacywindow.setBackgroundDrawableResource(R.color.transperent);
                p.show();
            }
        });

        tosbtn = (ImageView) view.findViewById(R.id.tosbtn);
        tosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window toswindow = tosDialogue.getWindow();
                toswindow.setBackgroundDrawableResource(R.color.transperent);
                tosDialogue.show();
            }
        });

        customer = sharedInformation.getUserCustomer();
        username = view.findViewById(R.id.usernametv);
        useremail = view.findViewById(R.id.emailtvsetting);

        username.setText(customer.getName());
        useremail.setText(customer.getEmail());

        aboutusbtn = view.findViewById(R.id.aboutusbtn);
        aboutusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window aboutis = aboutUsDialogue.getWindow();
                aboutis.setBackgroundDrawableResource(R.color.transperent);
                aboutUsDialogue.show();
            }
        });

        changePasswordDialogue = new ChangePasswordDialogue(getActivity());
        changepasswordbtn = view.findViewById(R.id.changepasswordbtn);

        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ChangePassword.class);



                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        report = view.findViewById(R.id.reportbtn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReportCustomer.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });





        return view;
    }
}
