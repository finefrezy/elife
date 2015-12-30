package com.elife.easy;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class ElifeApplication extends Application {

	private static RequestQueue sRequestQueue;
	@Override
	public void onCreate() {
		super.onCreate();
		
		sRequestQueue = Volley.newRequestQueue(getApplicationContext());
	}
	
	public static RequestQueue getRequestQueue() {
		return sRequestQueue;
	}

	
}
