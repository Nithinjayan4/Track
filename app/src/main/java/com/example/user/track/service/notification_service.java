package com.example.user.track.service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.track.R;
import com.example.user.track.activity.ChatActivity;
import com.example.user.track.adapter.MessageAdapter;
import com.example.user.track.pojo.msglist;
import com.example.user.track.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class notification_service extends Service  {
	String url="",ip="",uid="",name="",sid="";
	GlobalPreference mGlobalPreference;

	NotificationManager manager;

	Handler m_Handler;
	Runnable mRunnable;
	//TextToSpeech tts;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		mGlobalPreference= new GlobalPreference(getApplicationContext());
		ip=mGlobalPreference.RetriveIP();
		uid=mGlobalPreference.RetriveUID();

		m_Handler = new Handler();
		mRunnable = new Runnable(){
			@Override
			public void run() {
				getNotification();
				m_Handler.postDelayed(mRunnable, 1000);// move this inside the run method
			}
		};
		mRunnable.run();


	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//imei=intent.getStringExtra("imei");
		//ip=intent.getStringExtra("ip");
		final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {
				// code here what ever is required
				getNotification();
				handler.postDelayed(this, 3000);

                    }

			
	            };

	   handler.postDelayed(r,3000);
	  

	}

	public  void showNotification(String s) {
		//  Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

		try {

			JSONArray jsonArray = new JSONArray(s);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				name = jsonObject.getString("name");
				sid = jsonObject.getString("sender_id");
				NotificationCompat.Builder builder =
						new NotificationCompat.Builder(this)
								.setSmallIcon(R.mipmap.user)
								.setContentTitle("Track App Notification")
								.setContentText("You hav a message from "+name);

				Intent notificationIntent = new Intent(getApplicationContext(), ChatActivity.class);
				notificationIntent.putExtra("fid",sid);
				PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), i, notificationIntent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(contentIntent);

				// Add as notification
				manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				manager.notify(i, builder.build());
			}
		}

		catch (Exception e){


		}
	}


	public void getNotification() {
		url="http://" + ip + "/navigation/get_notification.php";

		final StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("res",response);
				// Toast.makeText(NavigationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
				if(response.contains("failed")){

					//tt.speak("You Are Entered in a restricted area    please go back  ", TextToSpeech.QUEUE_FLUSH, null);
				}
				else{
					showNotification(response);
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
				params.put("uid",""+uid);


				return params;

			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);
	}
}
