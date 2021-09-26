package com.example.user.track.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.track.util.GlobalPreference;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class direction_service extends Service  {
	String url="",ip="",uid="",fid="",type="";
	GlobalPreference mGlobalPreference;
	TextToSpeech t1;
	List<ScanResult> wifiList;
    private boolean isRunning  = false;
	int v1,v2,v3;
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

		t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					t1.setLanguage(Locale.UK);
				}
			}
		});
        isRunning = true;


	}
    @Override
    public void onDestroy() {
        isRunning = false;
        t1.stop();
		t1.shutdown();
      //  stopSelf();
       // Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show();
    }
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//imei=intent.getStringExtra("imei");
		//ip=intent.getStringExtra("ip");
		super.onStart(intent, startId);

		Bundle bundle = intent.getExtras();
		fid = (String) bundle.getCharSequence("fid");
		type= (String) bundle.getCharSequence("type");
		//Toast.makeText(getApplicationContext(), "sss"+fid+"uid::"+uid+"type::"+type, Toast.LENGTH_SHORT).show();
		final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {
				// code here what ever is required
getDirection();
					handler.postDelayed(this, 5000);

                    }

			
	            };

	   handler.postDelayed(r,5000);
	  

	}
	public void getDirection() {
		url="http://" + ip + "/navigation/get_direction.php";
		//Toast.makeText(getApplicationContext(), "providername::::::"+provider, Toast.LENGTH_SHORT).show();
		//Toast.makeText(NavigationActivity.this, "pppp::"+p, Toast.LENGTH_SHORT).show();
		final StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("res",response);
				// Toast.makeText(NavigationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
				t1.speak(response, TextToSpeech.QUEUE_FLUSH, null);

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
				params.put("fid",""+fid);
				params.put("type",""+type);



				return params;

			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);
	}


}
