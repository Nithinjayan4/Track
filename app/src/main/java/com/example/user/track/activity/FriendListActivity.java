package com.example.user.track.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.track.R;
import com.example.user.track.activity.HomeActivity;
import com.example.user.track.adapter.FriendListAdapter;
import com.example.user.track.pojo.friendlist;
import com.example.user.track.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class
FriendListActivity extends AppCompatActivity {
    GlobalPreference mGlobalPreference;
    String url="",ip="",uid="",url_imgpath="",name="",fid="",imgname="",status="";
    private RecyclerView RvItem;
    FriendListAdapter adapter=null;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_friend_list);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
        url = "http://" + ip + "/navigation/get_myfriends.php";
        url_imgpath = "http://" + ip + "/navigation/";
        GetmyFriends();

    }
    public void GetmyFriends() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
              //  Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();

                if(response.equals("failed")){

                    Toast.makeText(getApplicationContext(), "No friends", Toast.LENGTH_SHORT).show();
                }
                else{
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

                params.put("uid",uid);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public  void showlist(String s){

        try{
            ArrayList<friendlist> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name");
                fid = jsonObject.getString("id");
                 imgname= jsonObject.getString("image");



                friendlist item=new friendlist();

                String url1=url_imgpath+""+imgname;

                Log.d("res", "showlist: "+name+fid+imgname);
                item.setName(name);
                item.setFid(Integer.parseInt(fid));
                item.setUid(Integer.parseInt(uid));
                item.setImage_url(url1);


                list.add(item);



                RvItem = (RecyclerView) findViewById(R.id.recycler_card);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                RvItem.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RvItem.setLayoutManager(mLayoutManager);


                adapter = new FriendListAdapter(getApplicationContext(), list);

                RvItem.setAdapter(adapter);




            }

//
//            RvItem = (RecyclerView) findViewById(R.id.recycler_view);
//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            RvItem.setHasFixedSize(true);
//
//            // use a linear layout manager
//            int numberOfColumns = 2;
//            RvItem.setLayoutManager(new LinearLayoutManager());
//
//
//            adapter = new CategoryAdapter(getApplicationContext(), list);
//
//            RvItem.setAdapter(adapter);
//





        }catch (Exception e){


        }





    }
}
