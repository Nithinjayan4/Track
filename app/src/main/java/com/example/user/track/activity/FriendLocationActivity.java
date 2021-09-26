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

public class FriendLocationActivity extends AppCompatActivity {
WebView wv;
    GlobalPreference mGlobalPreference;
    Button bl;
    String url="",ip="",uid="",fid="",flag="0",type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_location);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
        fid=getIntent().getStringExtra("fid");
       // type=getIntent().getStringExtra("friend");
       // Toast.makeText(this, ""+uid+fid, Toast.LENGTH_SHORT).show();
        wv=findViewById(R.id.webview1);
        bl=findViewById(R.id.bl);

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
        String url = ("http://"+ip+"/navigation/map_double.php");


        String postData = null;
        try {
            postData = "uid=" + URLEncoder.encode(uid+"_"+fid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       // Toast.makeText(this, ""+postData, Toast.LENGTH_SHORT).show();
       // wv.loadUrl("http://www.google.com");
       wv.postUrl(url,postData.getBytes());
      //  Toast.makeText(this, "flag:::"+flag, Toast.LENGTH_SHORT).show();
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
String t=bl.getText().toString();
              //  Toast.makeText(FriendLocationActivity.this, ""+t, Toast.LENGTH_SHORT).show();
                if(bl.getText().toString().contains("Get Direction")){
                    bl.setText("Stop");
                    flag="1";
                    Intent mIntent = new Intent(getApplicationContext(), direction_service.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("fid", fid);
                    bundle.putCharSequence("type","friend");
                    mIntent.putExtras(bundle);
                    startService(mIntent);
                }
                else if(bl.getText().toString().contains("Stop")){
                    bl.setText("Get Direction");
                    flag="0";
                    Intent mIntent = new Intent(getApplicationContext(), direction_service.class);

                    stopService(mIntent);
                }

            }
        });
    }
}
