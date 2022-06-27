package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Category;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.ChangeCatNameDialogue;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class EditCategory extends AppCompatActivity implements ChangeCatNameDialogue.ChangeCatListener {

    private static final String TAG = "EditCategory";
    private static final int PICK_IMAGE_REQUEST = 1;
    private SharedInformation sharedInformation;
    private Category category;
    private RequestQueue queue;
    private ImageView cimage;
    private TextView cname;
    private FloatingActionButton delete;
    private FloatingActionButton changename;
    private FloatingActionButton changeimage;
    private Bitmap bitmap;
    private Button upload;
    private Button cancel;
    private Activity a;
    private ImageView goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);


        queue = Volley.newRequestQueue(this);
        cimage = findViewById(R.id.cativ);
        cname = findViewById(R.id.catname);
        delete = findViewById(R.id.deletefab);
        changename = findViewById(R.id.namefab);
        changeimage = findViewById(R.id.imagefab);
        upload = findViewById(R.id.upload);
        cancel = findViewById(R.id.cancel);

        goback = findViewById(R.id.gobackiv);

        a=this;
        Gson gson = new Gson();

        String json = getIntent().getStringExtra("category");

        category = gson.fromJson(json,Category.class);
        loadcategory();



        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();




            }
        });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadcategory();
                upload.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }
        });

        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCat();
            }
        });







    }

    private void deleteCat(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        String key = getResources().getString(R.string.securitykey);
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.deletecat);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                loading.dismiss();
                
                try {
                    JSONObject row = new JSONObject(response);
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){

                        Log.d(TAG, "onResponse: " + error_message);
                    }

                    else {


                        finish();
                    }

                    
                }
                catch (JSONException e){
                    
                    e.printStackTrace();
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }
                
                
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();

                Log.e(TAG, "onErrorResponse: ",error );
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                
                params.put("key", key);
                params.put("id",category.getId()+"");
                return params;
            }
        };
        
        queue.add(request);

    }


    private void openDialog(){
        ChangeCatNameDialogue dialogue = new ChangeCatNameDialogue();
        dialogue.show(getSupportFragmentManager(),"changename");
    }

    @Override
    public void applyTexts(String name) {

        changeName(name);
        //cname.setText(name);
    }

    private void changeName(String name){

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.changecatname);

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                loading.dismiss();

                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    if(error){
                        Log.d(TAG, "onResponse: Error");
                    }
                    else {
                        cname.setText(name);
                        Log.d(TAG, "onResponse: Success");
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: ",e );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.e(TAG, "onErrorResponse: ",error );
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("name",name);
                params.put("id",category.getId()+"");
                return params;
            }
        };
        queue.add(request);



    }


    private void uploadImage(){
        //Showing the progress dialog


        String url =getResources().getString(R.string.dblink) + getResources().getString(R.string.changecatimage);


        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Log.d(TAG, "onResponse() called with: s = [" + s + "]");
                        Toast.makeText(EditCategory.this, s, Toast.LENGTH_LONG).show();

                        upload.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast

                        upload.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                        Log.d(TAG, "onErrorResponse() called with: volleyError = [" + volleyError + "]");

                        Toast.makeText(EditCategory.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = category.getName().toString().trim();

                //Creating parameters


                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put("id",category.getId()+"");
                params.put("image", image);
                params.put("name", name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



    private void loadcategory(){

        Log.d(TAG, "loadcategory() called");
        Context c = getApplicationContext();
       String url =c.getResources().getString(R.string.dblink) + c.getResources().getString(R.string.getcategoryimage) +category.getImagename();
        Picasso.get().load(url).into(cimage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess() called");
            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError() called with: e = [" + e + "]");
                Log.e(TAG, "onError: ", e );
                e.printStackTrace();

            }


        });


        cname.setText(category.getName());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(category!=null){
            category=null;
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            CropImage.activity(filePath).start(a);

            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                cimage.setImageBitmap(bitmap);


                upload.setVisibility(View.VISIBLE);

                cancel.setVisibility(View.VISIBLE);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    cimage.setImageBitmap(bitmap);
                    upload.setVisibility(View.VISIBLE);

                    cancel.setVisibility(View.VISIBLE);


                }
                catch (IOException e){
                    Log.e(TAG, "onActivityResult: ", e );
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }




    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        Log.d(TAG, "finish() called");
    }
}