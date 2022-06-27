package com.example.habuonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.habuonlineshop.Classes.Admin;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Logout_Dialogue;
import com.example.habuonlineshop.Fragments.Admin.AdminHomeFragment;
import com.example.habuonlineshop.Fragments.Admin.AdminReportsFragment;
import com.example.habuonlineshop.Fragments.Admin.AdminSearchFragment;
import com.example.habuonlineshop.Fragments.Admin.AdminSettingFragment;
import com.example.habuonlineshop.Functions.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AdminMainActivity extends AppCompatActivity {

    private ImageView logout_btm;
    private Activity activity;
    private Admin admin;
    private static final String TAG = "AdminMainActivity";
    private SharedInformation sharedInformation;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private int[] tabicons = {

            R.drawable.ic_baseline_home_24,

            R.drawable.searchvector,
            R.drawable.ic_baseline_event_note_24,
            R.drawable.accountvector

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_main);

        sharedInformation = new SharedInformation(this);
        admin = sharedInformation.getAdmin();
        tabLayout = (TabLayout) findViewById(R.id.customermain_tabs);
        viewPager2 = (ViewPager2) findViewById(R.id.customermain_viewpager);
        Log.d(TAG, "onCreate: "  + admin);

        activity = this;

        logout_btm = findViewById(R.id.logoutBtn);

        logout_btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_Dialogue logout_dialogue = new Logout_Dialogue(activity);
                Window window = logout_dialogue.getWindow();
                window.setBackgroundDrawableResource(R.color.transperent);
                logout_dialogue.show();


                Log.d(TAG, "onClick() called with: v = [" + v + "]");

            }
        });

        setupViewPager();


        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                Log.d(TAG, "onConfigureTab() called with: tab = [" + tab + "], position = [" + position + "]");
            }
        }).attach();


        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(3);
        setupTabIcons();



    }




    private void setupViewPager(){
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        fragmentAdapter.addFragment(new AdminHomeFragment());
        fragmentAdapter.addFragment(new AdminSearchFragment());
        fragmentAdapter.addFragment(new AdminReportsFragment());
        fragmentAdapter.addFragment(new AdminSettingFragment());

        viewPager2.setAdapter(fragmentAdapter);
    }

    private void setupTabIcons() {


        tabLayout.getTabAt(0).setIcon(tabicons[0]);
        tabLayout.getTabAt(1).setIcon(tabicons[1]);
        tabLayout.getTabAt(2).setIcon(tabicons[2]);
        tabLayout.getTabAt(3).setIcon(tabicons[3]);

    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish() called");
    }



}