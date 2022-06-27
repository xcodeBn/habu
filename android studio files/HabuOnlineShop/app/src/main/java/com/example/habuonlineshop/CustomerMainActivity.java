package com.example.habuonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.Logout_Dialogue;
import com.example.habuonlineshop.Fragments.Customer.CustomerCartFragment;
import com.example.habuonlineshop.Fragments.Customer.CustomerHomeFragment;
import com.example.habuonlineshop.Fragments.Customer.CustomerSettingFragment;
import com.example.habuonlineshop.Fragments.Customer.GeneralSearchFragment;
import com.example.habuonlineshop.Functions.FragmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;

public class CustomerMainActivity extends AppCompatActivity {





    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private SharedInformation sharedInformation;
    private static final String TAG = "CustomerMainActivity";
    private ImageView logout_btn;
    private Logout_Dialogue logout_dialogue;





    private int[] tabicons = {

            R.drawable.ic_baseline_home_24,

            R.drawable.searchvector,
            R.drawable.cart_vector2d,
            R.drawable.accountvector

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);


        SharedInformation sharedInformation = new SharedInformation(this);





        logout_btn = (ImageView) findViewById(R.id.logoutBtn);
        logout_dialogue = new Logout_Dialogue(this);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Window logoutwindow = logout_dialogue.getWindow();

                logoutwindow.setBackgroundDrawableResource(R.color.transperent);
                logout_dialogue.show();

            }
        });

        getToken();
        tabLayout = (TabLayout) findViewById(R.id.customermain_tabs);
        viewPager2 = (ViewPager2) findViewById(R.id.customermain_viewpager);


        int tabCount =tabLayout.getTabCount();
        Log.d(TAG, "onCreate: tabcount=" +tabCount);

setupViewpager();
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();



        sharedInformation = new SharedInformation(this);
//        Customer c = sharedInformation.getUserCustomer();
//        c.setIsverified(false);
//        sharedInformation.setCustomer(c);
        viewPager2.setUserInputEnabled(false);
viewPager2.setOffscreenPageLimit(3);
setupTabIcons();



        sharedInformation = new SharedInformation(CustomerMainActivity.this);

//        Customer current = sharedInformation.getUserCustomer();
//
//        if(!current.getIsverified())
//        {
//
//            Intent goverify = new Intent(CustomerMainActivity.this,VerifiyAccount.class);
//            startActivity(goverify);
//        }







    }



    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: DENIED");
    }


    private void setupViewpager(){

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
adapter.addFragment(new CustomerHomeFragment());
adapter.addFragment(new GeneralSearchFragment());
        adapter.addFragment(new CustomerCartFragment());
        adapter.addFragment(new CustomerSettingFragment());
viewPager2.setAdapter(adapter);

    }


    private void setupTabIcons() {


        tabLayout.getTabAt(0).setIcon(tabicons[0]);
        tabLayout.getTabAt(1).setIcon(tabicons[1]);
        tabLayout.getTabAt(2).setIcon(tabicons[2]);
        tabLayout.getTabAt(3).setIcon(tabicons[3]);

    }

    private void getToken(){
        
        
        
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, "kkk"+msg);
                     //   Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}