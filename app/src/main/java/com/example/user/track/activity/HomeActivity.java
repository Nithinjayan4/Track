package com.example.user.track.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.user.track.R;
import com.example.user.track.service.wifi_service;
import com.example.user.track.util.GlobalPreference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    WebView wv;
    CardView card_shop,card_offer,card_track,card_add;
    GlobalPreference mGlobalPreference;
    String url="",ip="",uid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
        card_add=findViewById(R.id.card_add);
        card_offer=findViewById(R.id.card_offers);
        card_track=findViewById(R.id.card_track);
        card_shop=findViewById(R.id.card_shops);
        card_add.setOnClickListener(this);
        card_offer.setOnClickListener(this);
        card_shop.setOnClickListener(this);
        card_track.setOnClickListener(this);
        wv=findViewById(R.id.webview);
        Intent in=new Intent(getApplicationContext(), wifi_service.class);
        startService(in);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new JavaScriptInterface(this), "Android");
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
        String url = ("http://"+ip+"/navigation/map.php");

        /*String postData = "uid="+uid;
        byte[] bytes = new byte[0];
        try {
            bytes = postData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        wv.postUrl(url, bytes);
*/

        String postData = null;
        try {
            postData = "uid=" + URLEncoder.encode(uid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        wv.postUrl(url,postData.getBytes());
    }
    public class JavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.card_add:
                startActivity(new Intent(HomeActivity.this, UserListActivity.class));
                break;
            case R.id.card_offers:

                startActivity(new Intent(HomeActivity.this,OffersActivity.class));
                break;
            case R.id.card_shops:

                startActivity(new Intent(HomeActivity.this, ShopListActivity.class));
                break;
            case R.id.card_track:
                startActivity(new Intent(HomeActivity.this,FriendListActivity.class));

                break;


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.id_request) {
            Intent i= new Intent(getApplicationContext(),RequestActivity.class);

            startActivity(i);
        }
        if (id == R.id.id_logout) {
            Intent i= new Intent(getApplicationContext(),LoginActivity.class);

            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
