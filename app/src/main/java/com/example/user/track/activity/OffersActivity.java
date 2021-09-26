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
import com.example.user.track.adapter.OfferListAdapter;
import com.example.user.track.adapter.ShopListAdapter;
import com.example.user.track.pojo.friendlist;
import com.example.user.track.pojo.offerlist;
import com.example.user.track.pojo.shoplist;
import com.example.user.track.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OffersActivity extends AppCompatActivity {
    GlobalPreference mGlobalPreference;
    String url="",ip="",uid="",itemname="",shopname="",sid="",offerprice="",originalprice="",offer_id="",url_imgpath,imgname="";
    private RecyclerView RvItem;
    OfferListAdapter adapter=null;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        ip=mGlobalPreference.RetriveIP();
        uid=mGlobalPreference.RetriveUID();
        url = "http://" + ip + "/navigation/get_offers.php";
         url_imgpath = "http://" + ip + "/navigation/items/";
        GetOffers();
    }
    public void GetOffers() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
              //  Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();

                if(response.equals("failed")){

                    Toast.makeText(getApplicationContext(), "No Offers", Toast.LENGTH_SHORT).show();
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
        }) /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid",uid);

                return params;

            }

        }*/;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public  void showlist(String s){
       // Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

        try{
            ArrayList<offerlist> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                shopname = jsonObject.getString("shop_name");
                sid = jsonObject.getString("sid");
                itemname = jsonObject.getString("item");
                offerprice = jsonObject.getString("offer_price");
                originalprice = jsonObject.getString("original_price");
                offer_id = jsonObject.getString("id");
              imgname= jsonObject.getString("img_id");


                offerlist item=new offerlist();

                 String url1=url_imgpath+""+imgname;

                // Log.d("res", "showlist: "+name+sid+imgname);
                item.setShop_name(shopname);
                item.setSid(Integer.parseInt(sid));
                item.setUid(Integer.parseInt(uid));
                item.setItem(itemname);
                item.setOffer_price(offerprice);
                item.setOriginal_price(originalprice);
                item.setOfferid(Integer.parseInt(offer_id));
               item.setImg_url(url1);

                list.add(item);



                RvItem = (RecyclerView) findViewById(R.id.recycler_offer);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                RvItem.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RvItem.setLayoutManager(mLayoutManager);


                adapter = new OfferListAdapter(getApplicationContext(),list);

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
