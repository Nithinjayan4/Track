package com.example.user.track.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.user.track.R;
import com.example.user.track.adapter.MessageAdapter;
import com.example.user.track.adapter.OfferListAdapter;
import com.example.user.track.pojo.msglist;
import com.example.user.track.pojo.offerlist;
import com.example.user.track.util.GlobalPreference;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String uid="",ip="",nam="",url="",fid="",type="",s_id="",r_id="",sender="",recipient="",message="",url1="";
    ListView listt;
    EditText emsg;
    private RecyclerView RvItem;
    MessageAdapter adapter=null;
    private RecyclerView.LayoutManager mLayoutManager;
ScrollView sc;
    Handler m_Handler;
    Runnable mRunnable;
    ArrayList<msglist> list;
    ImageView img;
    ArrayList<HashMap<String, String>> pdtlist;
    GlobalPreference mGlobalPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
sc=findViewById(R.id.scroll);
        fid=getIntent().getStringExtra("fid");

       // Toast.makeText(ChatActivity.this, ""+uid+fid, Toast.LENGTH_SHORT).show();
        url = "http://" + ip +"/navigation/getmsg.php";
        url1 = "http://" + ip +"/navigation/sendmsg.php";

        emsg=(EditText) findViewById(R.id.edit_text_out);
        //  img=(ImageView) findViewById(R.id.imgsnd);
        Button bimg=(Button) findViewById(R.id.button_send);
        bimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emsg.getText().toString().isEmpty()){

                }
                else{
                    SendMsg();
                }


            }
        });
        pdtlist=new ArrayList<HashMap<String,String>>();
        // getTime();
        sc.postDelayed(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(ScrollView.FOCUS_UP);
            }
        },1000);
        m_Handler = new Handler();
        mRunnable = new Runnable(){
            @Override
            public void run() {

                getMsg();

                m_Handler.postDelayed(mRunnable, 5000);// move this inside the run method
            }
        };
        mRunnable.run();

    }

    public  void showlist(String s) {
       //  Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

        try {
           list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nam = jsonObject.getString("name");
                s_id = jsonObject.getString("sender_id");
                r_id = jsonObject.getString("recipient_id");
                message = jsonObject.getString("message");

                msglist item = new msglist();


                item.setName(nam);
                item.setSid(Integer.parseInt(s_id));
                item.setUid(Integer.parseInt(uid));
                item.setRid(Integer.parseInt(r_id));
                item.setMsg(message);


                list.add(item);


                RvItem = (RecyclerView) findViewById(R.id.recycler_msg);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                RvItem.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLayoutManager.scrollToPosition(list.size() - 1);
                RvItem.setLayoutManager(mLayoutManager);


                adapter = new MessageAdapter(ChatActivity.this, list);

                RvItem.setAdapter(adapter);


            }
        }

    catch (Exception e){


    }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        m_Handler.removeCallbacks(mRunnable);
    }

    public void getMsg() {

      //  sc.fullScroll(View.FOCUS_DOWN);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
               // Toast.makeText(ChatActivity.this, "ee"+response, Toast.LENGTH_SHORT).show();
                if(response.equals("failed")){
                    // Toast.makeText(BustimeActivity.this, "NO BUS AVAILABLE", Toast.LENGTH_SHORT).show();
                }
                else{
                   // pdtlist.clear();
                    showlist(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("res", "onErrorResponse: "+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid",uid);

                params.put("rid",fid);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
        requestQueue.add(stringRequest);
    }
    public void SendMsg() {



        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                // Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                emsg.setText("");
                if(response.equals("failed")){
                    // Toast.makeText(BustimeActivity.this, "NO BUS AVAILABLE", Toast.LENGTH_SHORT).show();
                }
                else{
                    pdtlist.clear();
                    getMsg();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("res", "onErrorResponse: "+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid",uid);
                params.put("msg",emsg.getText().toString());
                params.put("rid",fid);

                return params;

            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(ChatActivity.this);
        requestQueue1.add(stringRequest1);
    }

}
