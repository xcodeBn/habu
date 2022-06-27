package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {

    private ImageView goback;
    private TextInputEditText size;
    private TextInputEditText weight;
    private TextInputEditText price;
    private TextInputEditText name;
    private TextInputEditText stock;
    private Spinner spinner;
    private ArrayList<String> cateogryArray;
    private FloatingActionButton add;
    private ImageView pimage;
    private Bitmap bitmap;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Boolean imageselected=false;
    private ImageView imageView;
    private Activity a;
    private static final String TAG = "AddProduct";
    private Button chooseimage;
    private ArrayList<Category> categories;
    private ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        a=this;
        categories=new ArrayList<>();
        cateogryArray = new ArrayList<>();

        imageView = findViewById(R.id.image);
        goback = findViewById(R.id.gobackiv);
        add = findViewById(R.id.add);


        add.setOnClickListener(this::onClick);


        size = findViewById(R.id.size);
        weight = findViewById(R.id.weight);
        price = findViewById(R.id.price);
        name = findViewById(R.id.name);
        stock = findViewById(R.id.stock);
        spinner = findViewById(R.id.spinner);
        chooseimage = findViewById(R.id.imgbtn);

        chooseimage.setOnClickListener(this::onClick);
        goback.setOnClickListener(this::onClick);

        spinnerAdapter = new ArrayAdapter<>(this,R.layout.spinneritem,cateogryArray);
        spinner.setAdapter(spinnerAdapter);

        fillspinner();





    }


    private void fillspinner(){

       String categoryurl = getResources().getString(R.string.dblink) + getResources().getString(R.string.getcategory);
        JsonArrayRequest request= new JsonArrayRequest(categoryurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                cateogryArray.clear();
                categories.clear();



                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject row = response.getJSONObject(i);
                        Log.d(TAG, "onResponse: " );

                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String image = row.getString("image");

                        categories.add(new Category(id,name,image));


                        cateogryArray.add(name);
                        spinnerAdapter.notifyDataSetChanged();

                    }
                }
                catch (Exception e){

                    Log.d(TAG, "onResponse:catch error: " + e);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                fillspinner(); //retry until success

            }
        });

        RequestQueue q = Volley.newRequestQueue(this);
        q.add(request);



    }

    private void addProduct(){
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.addproduct);
        RequestQueue queue = Volley.newRequestQueue(this);
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject row = new JSONObject(response);
                    Boolean error = row.getBoolean("error");

                    if(error){

                    }
                    else {
                        loading.dismiss();
                        finish();
                    }
                }

                catch (JSONException e){
                    Log.e(TAG, "onResponse: ",e );
                    e.printStackTrace();
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String getname = name.getText().toString();
                String getstock = stock.getText().toString();
                String getweight = weight.getText().toString();
                String getsize = size.getText().toString();
                String getcategory = getCategory().toString();
                String image = getStringImage(bitmap);
                String getprice = price.getText().toString();

                Map<String,String> params = new HashMap<>();

                params.put("name",getname);
                params.put("stock",getstock);
                params.put("weight",getweight);
                params.put("size",getsize);
                params.put("image",image);
                params.put("category",getcategory);
                params.put("price",getprice);

                return params;


            }
        };

        queue.add(request);
    }

    private Integer getCategory(){


        Integer id =0;
        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getName().equals(spinner.getSelectedItem().toString()))
                id = categories.get(i).getId();
            Log.d(TAG, "onQueryTextSubmit: id" + id);
        }

        return id;


    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
                imageView.setImageBitmap(bitmap);





            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();




                try{

                    imageselected=true;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    imageView.setImageBitmap(bitmap);




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
    public void onClick(View v) {

        if(v.equals(add)){


            if(stock.getText().toString().isEmpty() ||
                    size.getText().toString().isEmpty()
            || weight.getText().toString().isEmpty() ||
                    name.getText().toString().isEmpty()
                    || price.getText().toString().isEmpty() ||!imageselected){


                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }

            else {

                addProduct();
            }
        }
        if(v.equals(goback)){
            finish();
        }
        if(v.equals(chooseimage)){
            showFileChooser();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}