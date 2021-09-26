package com.example.user.track.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.user.track.R;
import com.example.user.track.service.direction_service;
import com.example.user.track.util.GlobalPreference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ShopLocationActivity extends AppCompatActivity {
    WebView wv;
    GlobalPreference mGlobalPreference;
    String url="",ip="",uid="",type="",fid="";
    Button bl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
       // type=getIntent().getStringExtra("shop");
       // String uid=getIntent().getStringExtra("uid");
        fid=getIntent().getStringExtra("fid");
       // Toast.makeText(this, ""+uid+fid, Toast.LENGTH_SHORT).show();
        wv=findViewById(R.id.webview1);
        bl1=findViewById(R.id.bl1);
        wv.getSettings().setUseWideViewPort(false);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(false);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        String url = ("http://"+ip+"/navigation/map_shops.php");


        String postData = null;
        try {
            postData = "uid=" + URLEncoder.encode(uid+"_"+fid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        wv.postUrl(url,postData.getBytes());
        bl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String t=bl.getText().toString();
                // Toast.makeText(FriendLocationActivity.this, ""+t, Toast.LENGTH_SHORT).show();
                if(bl1.getText().toString().contains("Get Direction")){
                    bl1.setText("Stop");

                    Intent mIntent = new Intent(getApplicationContext(), direction_service.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("fid", fid);
                    bundle.putCharSequence("type","shop");
                    mIntent.putExtras(bundle);
                    startService(mIntent);
                }
                else if(bl1.getText().toString().contains("Stop")){
                    bl1.setText("Get Direction");

                    Intent mIntent = new Intent(getApplicationContext(), direction_service.class);

                    stopService(mIntent);
                }

            }
        });
    }
}
