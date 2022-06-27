package com.example.habuonlineshop.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;


public class SharedInformation {

private Context context;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
Customer customer;
    private static final String TAG = "SharedInformation";


public SharedInformation(Context c){


    Log.d(TAG, "SharedInformation: New Shared Info Object created in: " + c.getClass().getName());
context = c;
    sharedPreferences =context.getSharedPreferences("login.conf", Context.MODE_PRIVATE);
    editor=sharedPreferences.edit();

}

public void enablenotifcation(Boolean enable){
    editor.putBoolean("notificaiton",enable);
    editor.apply();
    editor.commit();
}

public Boolean getnotificaiton(){


    return sharedPreferences.getBoolean("notificaiton",false);
}

public void setusertype(String type){

    editor.putString("type",type);

    editor.apply();
    editor.commit();
}

public String getusertype(){

   return sharedPreferences.getString("type","");
}




public void loginDetails(){}

public void settest(String test)
{


editor.putString("testing",test);
editor.apply();
editor.commit();

}



public void setUserCustomer(String name,String email,String password,String location,String creationdate,Boolean isverified ){

    Customer customer = new Customer(name,email,password,location,creationdate,isverified);
    Gson gson = new Gson();
    String json = gson.toJson(customer);
    editor.putString("user_customer",json);
    editor.commit();

}

public void setAdmin(Admin admin){

    Gson gson = new Gson();
    String json = gson.toJson(admin);
    editor.putString("user_admin",json);
    editor.commit();

}

public Admin getAdmin(){

    Gson gson = new Gson();
    String json  = sharedPreferences.getString("user_admin","");
    Admin admin = gson.fromJson(json,Admin.class);
    return admin;
}

public void setCustomer(Customer customer){
    Gson gson = new Gson();
    String json = gson.toJson(customer);
    editor.putString("user_customer",json);
    editor.commit();
}

public Customer getUserCustomer(){

    Gson gson=new Gson();
    String json = sharedPreferences.getString("user_customer","");
    Customer customer = gson.fromJson(json,Customer.class);

    return customer;
}


public String gettest(){



return  sharedPreferences.getString("testing","");



}


public void clearinfo(){


    editor.clear();
    editor.commit();
}
}

