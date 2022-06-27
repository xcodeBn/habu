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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.ChangeCatNameDialogue;
import com.example.habuonlineshop.Dialogues.ChangeProName;
import com.example.habuonlineshop.Dialogues.ChangeProPrice;
import com.example.habuonlineshop.Dialogues.ChangeProSize;
import com.example.habuonlineshop.Dialogues.ChangeProStock;
import com.example.habuonlineshop.Dialogues.ChangeProWeight;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class EditPro extends AppCompatActivity implements View.OnClickListener,
        ChangeProName.ChangeProNameListener,
        ChangeProStock.ChangeProStockListener, ChangeProSize.ChangeProSizeListener,
        ChangeProWeight.ChangeProWeightListener, ChangeProPrice.ChangeProPriceListener {

    private static final String TAG = "EditPro";
    private Product product;
    private TextView weight;
    private TextView size;
    private TextView name;
    private TextView stock;
    private TextView price;
    private ImageView productimg;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Button upload;
    private Button cancel;
    private ImageButton changeproname;
    private ImageButton changeprostock;
    private FloatingActionButton fabselectimage;
    private Activity a;
    private ImageButton changeprosize;
    private ImageButton changeproweight;
    private ImageButton changeproprice;
    private Button deletebtn;
    private ImageView goback;

    private SharedInformation sharedInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro);

        Gson gson = new Gson();
        String json = getIntent().getStringExtra("product");
        product = gson.fromJson(json,Product.class);
        weight = findViewById(R.id.tvweight);
        size = findViewById(R.id.tvsize);
        name = findViewById(R.id.tvname);
        stock = findViewById(R.id.tvstock);
        price = findViewById(R.id.tvprice);
        productimg = findViewById(R.id.img);
        upload = findViewById(R.id.changeiv);
        cancel = findViewById(R.id.cancel);
        fabselectimage = findViewById(R.id.fabimage);
        changeproname = findViewById(R.id.ibname);
changeprosize= findViewById(R.id.ibsize);
        changeprosize.setOnClickListener(this::onClick);
        changeprostock = findViewById(R.id.ibstock);
        changeproprice = findViewById(R.id.ibprice);
        deletebtn = findViewById(R.id.dltbtn);


        goback = findViewById(R.id.gobackiv);
        changeproprice.setOnClickListener(this::onClick);
        a=this;
        changeproweight = findViewById(R.id.ibweight);
        setupProduct();

        fabselectimage.setOnClickListener(this::onClick);

        upload.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(this::onClick);
        upload.setOnClickListener(this::onClick);
        changeproname.setOnClickListener(this::onClick);
        changeprostock.setOnClickListener(this::onClick);

        changeproweight.setOnClickListener(this::onClick);
        deletebtn.setOnClickListener(this::onClick);
        goback.setOnClickListener(this::onClick);
    }


    private void setupProduct(){

        String url = getResources().getString(R.string.dblink)+getResources().getString(R.string.getproductimage)+product.getImage();
        name.setText(product.getName());
        weight.setText(product.getWeight());
        size.setText(product.getSize());
        stock.setText(product.getStock().toString());
        price.setText(product.getPrice().toString()+"$");
        Picasso.get().load(url).into(productimg, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(Exception e) {

                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }
        });

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


    private void uploadImage(){
        //Showing the progress dialog


        String url =getResources().getString(R.string.dblink) + getResources().getString(R.string.updateproimg);


        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Log.d(TAG, "onResponse() called with: s = [" + s + "]");
                        Toast.makeText(EditPro.this, s, Toast.LENGTH_LONG).show();

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

                        Toast.makeText(EditPro.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = "pr";

                //Creating parameters


                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put("id",product.getId()+"");
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
                productimg.setImageBitmap(bitmap);


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
                    productimg.setImageBitmap(bitmap);
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
    public void onClick(View v) {

        
        if(v.equals(goback)){
            finish();
        }

        if(v.equals(cancel)){
            cancel.setVisibility(View.GONE);
            upload.setVisibility(View.GONE);
            setupProduct();
        }
        if(v.equals(upload)){

            uploadImage();
        }

        if(v.equals(fabselectimage)){

            showFileChooser();

        }
        if(v.equals(changeproname)){
            showChangeNameDialogue();
        }

        if(v.equals(changeprostock)){
            showChangeStockDialogue();
        }

        if(v.equals(changeprosize)){
            showChangeSizeDialogue();
        }

        if(v.equals(changeproweight)){

            showChangeWeightDialogue();
        }

        if(v.equals(changeproprice)){
            showChangeProPriceDialgoue();
        }


        if(v.equals(deletebtn)){

            deleteProduct();
        }
    }

    private void deleteProduct() {

        String url = getResources().getString(R.string.dblink)  + getResources().getString(R.string.deleteproduct);
        final ProgressDialog loading = ProgressDialog.show(this,"Deleting...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Boolean error = jsonObject.getBoolean("error");
                    if(error){

                    }
                    else {
                        finish();
                    }
                }
                catch (JSONException e){
                    Log.e(TAG, "onResponse: ",e );
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",product.getId().toString());
                return params;
            }
        };
        RequestQueue q  = Volley.newRequestQueue(this);
        q.add(request);

    }

    private void showChangeProPriceDialgoue() {

        ChangeProPrice dialogue = new ChangeProPrice();
        dialogue.show(getSupportFragmentManager(),"chageprostock");
    }

    private void changeName(String nam){



        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.changeproname);
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                        name.setText(nam);
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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String id = product.getId().toString();
                params.put("id",id);
                params.put("name",nam);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
    private void changeSize(String siz){



        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.changeprosize);
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                        size.setText(siz);
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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String id = product.getId().toString();
                params.put("id",id);
                params.put("size",siz);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
    private void changeStock(String stok){


        String url = getResources().getString(R.string.dblink)+getResources().getString(R.string.changeprostock);
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
                        stock.setText(stok);
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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=  new HashMap<>();
                String id = product.getId().toString();
                params.put("id",id);
                params.put("stock",stok);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


    private void showChangeStockDialogue(){

        ChangeProStock dialogue = new ChangeProStock();
        dialogue.show(getSupportFragmentManager(),"chageprostock");
    }
    private void showChangeNameDialogue(){
        ChangeProName dialogue = new ChangeProName();
        dialogue.show(getSupportFragmentManager(),"changeproname");
    }
    private void showChangeSizeDialogue(){

        ChangeProSize dialogue = new ChangeProSize();
        dialogue.show(getSupportFragmentManager(),"chageprosize");
    }

    private void showChangeWeightDialogue(){

        ChangeProWeight dialogue = new ChangeProWeight();
        dialogue.show(getSupportFragmentManager(),"chageproweight");
    }
    @Override
    public void changeProSize(String size) {
        changeSize(size);
    }

    @Override
    public void applyTexts(String name) {

        changeName(name);
    }

    @Override
    public void changeProStock(String stock) {
        changeStock(stock);
    }


    @Override
    public void changeProWeight(String stock) {
        changeWeight(stock);
    }

    @Override
    public void changeProPrice(String price) {
        changePrice(price);
    }

    private void changePrice(String pric){
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.changeproprice);
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest request =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                        price.setText(pric);
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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String id = product.getId().toString();
                params.put("id",id);
                params.put("price",pric);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void changeWeight(String stock) {

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.changeproweight);

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
                        weight.setText(stock);
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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=  new HashMap<>();
                String id = product.getId().toString();
                params.put("id",id);
                params.put("weight",stock);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        Log.d(TAG, "finish() called");
    }
}