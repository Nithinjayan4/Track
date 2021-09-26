package com.example.user.track.service;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ListView;
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

public class wifi_service extends Service  {
	String url="",ip="",uid="",provider="";
	GlobalPreference mGlobalPreference;
	WifiManager wifiManager;
TextToSpeech tt;
	List<ScanResult> wifiList;

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
		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		//scaning();
		tt=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					tt.setLanguage(Locale.UK);
				}
			}
		});
		m_Handler = new Handler();
		mRunnable = new Runnable(){
			@Override
			public void run() {
				if (wifiManager.isWifiEnabled() == true) {
					//Toast.makeText(getApplicationContext(), "handler", Toast.LENGTH_SHORT).show();
					wifiManager.startScan();
					wifiList = wifiManager.getScanResults();
					/* sorting of wifi provider based on level */
					Collections.sort(wifiList, new Comparator<ScanResult>() {
						@Override
						public int compare(ScanResult lhs, ScanResult rhs) {
							return (lhs.level > rhs.level ? -1
									: (lhs.level == rhs.level ? 0 : 1));
						}
					});

					/*for (int i = 0; i < wifiList.size(); i++) {
						*//* to get SSID and BSSID of wifi provider*/
					if(wifiList.isEmpty()){
						//Toast.makeText(getApplicationContext(), "providername::::::", Toast.LENGTH_SHORT).show();
					}
					else {
						provider = (wifiList.get(0).SSID).toString();

						insert_provider();
					}
					/*}*/

				}
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
				if (wifiManager.isWifiEnabled() == true) {
					//Toast.makeText(getApplicationContext(), "handler1", Toast.LENGTH_SHORT).show();

					wifiManager.startScan();
					wifiList = wifiManager.getScanResults();
					/* sorting of wifi provider based on level */
					Collections.sort(wifiList, new Comparator<ScanResult>() {
						@Override
						public int compare(ScanResult lhs, ScanResult rhs) {
							return (lhs.level > rhs.level ? -1
									: (lhs.level == rhs.level ? 0 : 1));
						}
					});

					/*for (int i = 0; i < wifiList.size(); i++) {
					 *//* to get SSID and BSSID of wifi provider*/
					if(wifiList.isEmpty()){
						//Toast.makeText(getApplicationContext(), "providername::::::", Toast.LENGTH_SHORT).show();
					}
					else {
						provider = (wifiList.get(0).SSID).toString();

						insert_provider();
					}
					/*}*/

				}
				handler.postDelayed(this, 3000);

                    }

			
	            };

	   handler.postDelayed(r,3000);
	  

	}



	public void insert_provider() {
url="http://" + ip + "/navigation/wifi_insert.php";
	//	Toast.makeText(getApplicationContext(), "providername::::::"+provider, Toast.LENGTH_SHORT).show();
		//Toast.makeText(NavigationActivity.this, "pppp::"+p, Toast.LENGTH_SHORT).show();
		final StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("res",response);
				// Toast.makeText(NavigationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
				if(response.contains("restrict")){
					tt.speak("You Are Entered in a restricted area    \\please go back  ", TextToSpeech.QUEUE_FLUSH, null);
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
				params.put("provider",""+provider);


				return params;

			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);
	}
}
