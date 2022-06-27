package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.habuonlineshop.R;

public class TOSDialogue extends Dialog implements View.OnClickListener {

   private Activity activity;
    private Dialog dialog;

    public TOSDialogue(Activity a){
        super(a);
        this.activity=a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_tos);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WebView webView = (WebView) findViewById(R.id.tostv);
        webView.loadUrl("file:///android_asset/tos.html");

        TextView goback  = findViewById(R.id.gobackbtn);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
