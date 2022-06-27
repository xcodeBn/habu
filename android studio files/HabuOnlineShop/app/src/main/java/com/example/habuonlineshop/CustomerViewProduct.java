package com.example.habuonlineshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.habuonlineshop.Adapters.General.ReviewAdapter;
import com.example.habuonlineshop.Classes.Customer;
import com.example.habuonlineshop.Classes.Product;
import com.example.habuonlineshop.Classes.Review;
import com.example.habuonlineshop.Classes.SharedInformation;
import com.example.habuonlineshop.Dialogues.AddReview;
import com.example.habuonlineshop.Functions.XAxisValueFormatter;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.inappmessaging.dagger.Reusable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.android.horizontalratinglibrary.horizontalRatingLibrary;

public class CustomerViewProduct extends AppCompatActivity {

    private Product product;
    private ImageView productimage;
    private TextView size;
    private TextView weight;
    private TextView price;
    private TextView stock;
    private TextView name;
    private String image;
    private TextView reviewTitle;
    private Gson gson;
    private String getjson;
    private String dblink;
    private String imagelink;
    private String imageurl;
    private ImageView goback;
    private RequestQueue queue;
    private String productid;
    private LinearLayout parent;
    private static final String TAG = "CustomerViewProduct";
    private ArrayList<Review> reviewsAL;
    private RatingBar ratingBar;
    private RecyclerView reviewRv;
    private ReviewAdapter reviewAdapter;
    private PieChart pieChart;
   // private horizontalRatingLibrary bar;
    private HorizontalBarChart bar;
    //private ViewFlipper vf_err;
    private ViewFlipper vf_reviews;
    private AddReview addReview;
    private Button review_btn;
    private Button cart_btn;
    private  Button order_btn;
    private Customer customer;
    private SharedInformation sharedInformation;
    private Boolean incart;
    private ViewFlipper vf_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_product);

        reviewRv = findViewById(R.id.reviewrv);
        incart = false;




        //pieChart = findViewById(R.id.reviewpiechart);


//        vf_err = findViewById(R.id.vf);

        vf_cart = findViewById(R.id.vf_cart);
        cart_btn = findViewById(R.id.cart_btn);
        order_btn = findViewById(R.id.buy_btn);
        vf_reviews = findViewById(R.id.vf_reviews);
        bar = findViewById(R.id.bar);
        reviewTitle = findViewById(R.id.tvreviewtitle);
        productimage = findViewById(R.id.productimage);
        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        weight = findViewById(R.id.weight);
        price = findViewById(R.id.price);
        stock = findViewById(R.id.stock);
        reviewsAL = new ArrayList<>();
        sharedInformation = new SharedInformation(this);
        customer = sharedInformation.getUserCustomer();

        review_btn=findViewById(R.id.review_btn);
        parent = findViewById(R.id.parent);

        vf_reviews.setVisibility(View.VISIBLE);


        product = new Product(0,"","","",0,"a",0,0);

        String id = getIntent().getStringExtra("id");

        getProduct(id);
        ratingBar = findViewById(R.id.rate);
        queue = Volley.newRequestQueue(this);
        goback = (ImageView) findViewById(R.id.gobackiv);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });









        //ratingBar.setRating((float) 3.5);

        gson = new Gson();
        getjson = getIntent().getStringExtra("product");

       // product = gson.fromJson(getjson,Product.class);





//        Log.d(TAG, "onCreate: product:"+product.toString());



        



        reviewsAL = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this,reviewsAL);


        reviewRv.setAdapter(reviewAdapter);

        reviewRv.setLayoutManager(new LinearLayoutManager(this));




        Log.d(TAG, "onClick: incart:" + incart.toString());
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!incart){




                    addToCart();

                    Log.d(TAG, "onClick: incart-addtocart:" + incart.toString() );
                }
                else {
                    removeFromCart();

                    Log.d(TAG, "onClick: incart-removefromcart:" + incart.toString() );
                }
            }
        });


        reviewRv.setVisibility(View.VISIBLE);





        Log.d(TAG, "onCreatekkk: " + product.toString());
        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Window addwindow = addReview.getWindow();
                addwindow.setBackgroundDrawableResource(R.color.transperent);
                addReview.show();
            }
        });

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerViewProduct.this,OrderActivity.class);



                Gson gson = new Gson();
                String pjson = gson.toJson(product);
                i.putExtra("product",  pjson);


                startActivity(i);


            }
        });
        Refresh();





    }

    private void addToCart() {

        vf_cart.setDisplayedChild(1);
        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.addtocart);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                vf_cart.setDisplayedChild(0);
                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){

                        Snackbar snackbar = Snackbar.make(parent,error_message,Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(getResources().getColor(R.color.red));

                        snackbar.show();

                    }
                    else {
                        incart = !incart;
                        cart_btn.setText("Remove from cart");
                    }

                }

                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                vf_cart.setDisplayedChild(0);
                Snackbar snackbar = Snackbar.make(parent,"Error",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(getResources().getColor(R.color.red));

                snackbar.show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("pid",product.getId().toString());
                params.put("email",customer.getEmail());
                return params;
            }
        };

        queue.add(request);

    }

    private void removeFromCart() {

        String url = getResources().getString(R.string.dblink) + getResources().getString(R.string.removefromcart);

        vf_cart.setDisplayedChild(1);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                vf_cart.setDisplayedChild(0);

                try {

                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");

                    if(error){
                        Log.d(TAG, "onResponse: " + error_message);
                    }

                    else {
                        incart = !incart;
                        cart_btn.setText("Add to Cart");
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vf_cart.setDisplayedChild(0);

               // vf_cart.setDisplayedChild(0);
                Snackbar snackbar = Snackbar.make(parent,"Error",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(getResources().getColor(R.color.red));

                snackbar.show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params= new HashMap<>();
                params.put("pid",product.getId().toString());
                params.put("email",customer.getEmail());
                return params;
            }
        };
        queue.add(request);
    }

    private void loadProduct(){
        Log.d(TAG, "loadProduct() called");
        if(product.getStock()==0){

            order_btn.setEnabled(false);
            order_btn.setText("Out of stock");
        }
        name.setText(product.getName());
        size.setText(product.getSize());
        weight.setText(product.getWeight());
        dblink = getResources().getString(R.string.dblink);
        imagelink = getResources().getString(R.string.getproductimage);

        productid = product.getId().toString();



        String priceS = "$" + product.getPrice().toString() ;
        stock.setText(product.getStock().toString());

        image = product.getImage();
        imageurl = dblink + imagelink+image;

        Picasso.get().load(imageurl).into(productimage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Success");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();

            }
        });
        price.setText(priceS);
        checkCart();
        getreviews();
        addReview = new AddReview(this,product);

    }
    private void Refresh(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateReviews();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doTask, 0, 10000); //checks for new upadtes every 5 seconds
    }
    private void getProduct(String id){

        //   Product product ;
        String url = getResources().getString(R.string.dblink) +getResources().getString(R.string.getcartproduct);


        final ProgressDialog loading = ProgressDialog.show(this,"Deleting...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject row = array.getJSONObject(i);
                        Integer id = row.getInt("id");
                        String name = row.getString("name");
                        String weight = row.getString("weight");
                        String size = row.getString("size");
                        Integer category = row.getInt("category");
                        String image = row.getString("image");
                        Integer price = row.getInt("price");
                        Integer stock = row.getInt("stock");

                        product = new Product(id,name,weight,size,category,image,price,stock);

                        product.setId(id);
                        product.setName(name);
                        product.setPrice(price);
                        product.setSize(size);
                        product.setStock(stock);
                        product.setImage(image);
                        product.setCategory(category);
                        product.setWeight(weight);

                        loading.dismiss();
                        loadProduct();






                    }

                }
                catch (JSONException e){
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }


    private void checkCart(){

        vf_cart.setDisplayedChild(1);

        String url = getResources().getString(R.string.dblink)+ getResources().getString(R.string.checkcart);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject row = new JSONObject(response);

                    Boolean error = row.getBoolean("error");
                    String error_message = row.getString("error_message");
                    Boolean in = row.getBoolean("in");

                    if(!error){

                        vf_cart.setDisplayedChild(0);
                        incart = in;
                        if(in){
                            cart_btn.setText("Remove from Cart");
                        }
                        else {
                            cart_btn.setText("Add to Cart");
                        }
                    }

                    else {
                        Snackbar s = Snackbar.make(parent,error_message,Snackbar.LENGTH_SHORT);
                        s.setBackgroundTint(getResources().getColor(R.color.red));
                        s.show();
                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Snackbar s = Snackbar.make(parent,"error",Snackbar.LENGTH_SHORT);
                s.setBackgroundTint(getResources().getColor(R.color.red));
                s.show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",product.getId().toString());
                params.put("email",customer.getEmail());
                return params;
            }
        };

        queue.add(request);

    }

    public void updateReviews(){
        String url=getResources().getString(R.string.dblink) + getResources().getString(R.string.reviews);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {




                    JSONArray   reviews = new JSONArray(response);

                    Double general =0.0;
                    Float one = 0f;
                    Float two = 0f;
                    Float three = 0f;
                    Float four = 0f;
                    Float five = 0f;
                    Integer onecount =0;
                    Integer twocount =0;
                    Integer threecount =0;
                    Integer fourcount =0;
                    Integer fivecount =0;

                    if(reviews.length()==reviewsAL.size()){
                        return;
                    }

                    for(int i = 0; i<reviews.length();i++){
                        JSONObject row = reviews.getJSONObject(i);

                        Integer id = row.getInt("id");
                        Integer pid = row.getInt("productid");
                        String email = row.getString("email");
                        String title = row.getString("title");
                        //Integer rate = row.getInt("rate");
                        Double rate = row.getDouble("rate");
                        String text = row.getString("text");


                        reviewsAL.add(new Review(id,pid,email,title,text,rate));


                        Log.d(TAG, "onResponse: Successsfully add " + i );
                        reviewAdapter.notifyDataSetChanged();

                        general = general + rate;

                        if(rate==1.0){
                            //  one = one + rate;
                            onecount++;

                        }
                        if(rate ==2.0){
                            //    two = two+ rate;
                            twocount++;
                        }
                        if(rate == 3.0){
                            //    three =rate+ three;
                            threecount++;
                        }
                        if(rate==4.0){
                            //  four =rate+four;
                            fivecount++;
                        }
                        if(rate == 5.0){
                            //five= rate+five;
                            fivecount++;
                        }





                    }



                    general = general/reviewsAL.size();

                    Log.d(TAG, "onkkResponse: 1:" + one +
                            "2:" + two +
                            "3:" + three +
                            "4:" + four +
                            "5:" +
                            five);


                    Integer reviewnum = reviewsAL.size();
                    Float oneval =(float) 100/reviewnum;

                    one = oneval * onecount;
                    two = oneval * twocount;
                    three = oneval * threecount;
                    four = oneval * fourcount;
                    five = oneval * fivecount;








                    Log.d(TAG, "onkkResponse: 1:" + one +
                            "2:" + two +
                            "3:" + three +
                            "4:" + four +
                            "5:" +
                            five);
                    ratingBar.setRating(general.floatValue());

                    Float roundrate =(float) Math.round(ratingBar.getRating());

                    Log.d(TAG, "onResponse:Rating:  " + roundrate);
                    settitle(roundrate,reviewTitle);

                    Log.d(TAG, "onResponse: Rating:" + roundrate );



                    setSkillGraph(one,two,three,four,five);

                    if(reviewsAL.size()==0){
                        vf_reviews.setDisplayedChild(1);
                    }

                    else {
                        vf_reviews.setDisplayedChild(2);
                    }










                }
                catch (JSONException e){

                    e.printStackTrace();
                    Log.d(TAG, "onResponse: " + e.getMessage());

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

                params.put("id",productid);

                return params;
            }
        };
        queue.add(request);

    }
    public void getreviews(){

        vf_reviews.setDisplayedChild(0);
        String url=getResources().getString(R.string.dblink) + getResources().getString(R.string.reviews);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray   reviews = new JSONArray(response);

                    Double general =0.0;
                    Float one = 0f;
                    Float two = 0f;
                    Float three = 0f;
                    Float four = 0f;
                    Float five = 0f;
                    Integer onecount =0;
                    Integer twocount =0;
                    Integer threecount =0;
                    Integer fourcount =0;
                    Integer fivecount =0;

                    for(int i = 0; i<reviews.length();i++){
                        JSONObject row = reviews.getJSONObject(i);

                        Integer id = row.getInt("id");
                        Integer pid = row.getInt("productid");
                        String email = row.getString("email");
                        String title = row.getString("title");
                        //Integer rate = row.getInt("rate");
                        Double rate = row.getDouble("rate");
                        String text = row.getString("text");
                        reviewsAL.add(new Review(id,pid,email,title,text,rate));


                        Log.d(TAG, "onResponse: Successsfully add " + i );
                        reviewAdapter.notifyDataSetChanged();

                        general = general + rate;

                        if(rate==1.0){
                          //  one = one + rate;
                            onecount++;

                        }
                        if(rate ==2.0){
                        //    two = two+ rate;
                            twocount++;
                        }
                        if(rate == 3.0){
                        //    three =rate+ three;
                            threecount++;
                        }
                        if(rate==4.0){
                          //  four =rate+four;
                            fivecount++;
                        }
                        if(rate == 5.0){
                            //five= rate+five;
                            fivecount++;
                        }





                    }



                    general = general/reviewsAL.size();

                    Log.d(TAG, "onkkResponse: 1:" + one +
                            "2:" + two +
                            "3:" + three +
                            "4:" + four +
                            "5:" +
                            five);


                    Integer reviewnum = reviewsAL.size();
                    Float oneval =(float) 100/reviewnum;

                    one = oneval * onecount;
                    two = oneval * twocount;
                    three = oneval * threecount;
                    four = oneval * fourcount;
                    five = oneval * fivecount;








                    Log.d(TAG, "onkkResponse: 1:" + one +
                            "2:" + two +
                            "3:" + three +
                            "4:" + four +
                            "5:" +
                            five);
                   ratingBar.setRating(general.floatValue());

                   Float roundrate =(float) Math.round(ratingBar.getRating());

                    Log.d(TAG, "onResponse:Rating:  " + roundrate);
                    settitle(roundrate,reviewTitle);

                    Log.d(TAG, "onResponse: Rating:" + roundrate );



                        setSkillGraph(one,two,three,four,five);

                        if(reviewsAL.size()==0){
                            vf_reviews.setDisplayedChild(1);
                        }

                        else {
                            vf_reviews.setDisplayedChild(2);
                        }










                }
                catch (JSONException e){

                    e.printStackTrace();
                    Log.d(TAG, "onResponse: " + e.getMessage());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Snackbar snackbar = Snackbar.make()

                error.printStackTrace();
                vf_reviews.setDisplayedChild(4);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",productid);
                return params;
            }
        };
        queue.add(request);
    }

    private void setSkillGraph(Float one,Float two,Float three, Float four , Float five){
        bar.setDrawBarShadow(false);
        Description d = new Description();
        d.setText("");
        bar.setDescription(d);
        bar.getLegend().setEnabled(false);
        bar.setPinchZoom(false);
        bar.setDrawValueAboveBar(false);
        bar.setTouchEnabled(false);

        //Display the axis on the left (contains the labels 1*, 2* and so on)
        XAxis xAxis = bar.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);

        YAxis yleft = bar.getAxisLeft();
        yleft.mAxisMaximum=100f;
        yleft.mAxisMinimum = 0f;
        yleft.setEnabled(false);

        xAxis.setLabelCount(5);
        List<String> values = Arrays.asList("1 *", "2 *", "3 *", "4 *", "5 *");

        String[] val = {"1 *", "2 *", "3 *", "4 *", "5 *"};




        xAxis.setValueFormatter(new XAxisValueFormatter(val));

        YAxis yRight = bar.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);

        setGraphData(one,two,three,four,five);


    }

    private void setGraphData(Float one,Float two,Float three, Float four , Float five) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        //Integer[] average = new Integer[5];


        if(one==0){
            one++;
        }

        if(two==0){
            two++;
        }
        if(three==0){
            three++;
        }
        if(four==0){
            four++;
        }
        if(five==0){
            five++;
        }




      //  Log.d(TAG, "setGraphData: " + average.toString());
        entries.add(new BarEntry(1f, one.floatValue()));
        entries.add(new BarEntry(2f, two.floatValue()));
        entries.add(new BarEntry(3f, three.floatValue()));
        entries.add(new BarEntry(4f, four.floatValue()));
        entries.add(new BarEntry(5f, five.floatValue()));



        BarDataSet barDataSet = new BarDataSet(entries,"Bar Data Set");

        barDataSet.setValueTextSize(0f);

        barDataSet.setColors(ContextCompat.getColor(bar.getContext(),R.color.red),
                ContextCompat.getColor(bar.getContext(),R.color.darkred),
                ContextCompat.getColor(bar.getContext(),R.color.yello),
                ContextCompat.getColor(bar.getContext(),R.color.darkgreen),
                ContextCompat.getColor(bar.getContext(),R.color.green));


        bar.setDrawBarShadow(false);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));

        BarData data = new BarData(barDataSet);

        data.setBarWidth(0.9f);
        bar.setData(data);
        bar.invalidate();


    }

    public void settitle(Float rate,TextView title){

        Log.d(TAG, "settitle: Ratingrate:" + rate );
        if(rate==0f){

            title.setText("Very Bad");
        }
        else if(rate.floatValue()==1f){
            title.setText("Very Bad");
        }
        else if(rate==2f){
            title.setText("Bad");
        }
        else if(rate.floatValue()==3f){
            title.setText("Average");
        }
        else if(rate==4f){
            title.setText("Good");
        }
        else if(rate==5f){
            title.setText("Very Good");
        }

    }

    public  void reload(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void loadpiechartdata(){
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i =0;i<reviewsAL.size();i++){

            entries.add(new PieEntry(reviewsAL.get(i).getRate().floatValue(),reviewsAL.get(i).getTitle()));

        }

        ArrayList<Integer> colors  = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }


    }
    @Override
    public void finish() {

        super.finish();
        super.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}