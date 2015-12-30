package com.elife.easy.activity;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.elife.easy.ElifeApplication;
import com.elife.easy.R;
import com.elife.easy.cache.BitmapCache;
import com.elife.easy.constant.ConstantData;
import com.elife.easy.model.WelcomeModel;
import com.elife.easy.view.CustomProDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WelcomeActivity extends Activity {

	private static final String TAG = WelcomeActivity.class.getSimpleName();

	
	private ViewPager mViewPager;
	private List<WelcomeModel> mWelList;
//	ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		mViewPager = (ViewPager) findViewById(R.id.wel_viewpager);

		getWelcomeData();
	}

	private void getWelcomeData() {
		

		StringRequest stringRequest = new StringRequest(Method.GET,
				ConstantData.SERVER_URL + ConstantData.WELCOME_SUFFIX,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (!response.equalsIgnoreCase("0")) {
//							mWelList = GsonUtil.jsonToList(response,
//									WelcomeModel.class);
							Type type = new TypeToken<List<WelcomeModel>>(){}.getType();
							Gson gson = new Gson();
							mWelList = gson.fromJson(response, type);
							
							updateUi();
						}
						Log.d(TAG, mWelList.toString());
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG,
								"report error!" + error.getLocalizedMessage());
					}

				});
		ElifeApplication.getRequestQueue().add(stringRequest);
	}

	private void updateUi() {
		if (null != mWelList && mWelList.size() > 0) {
			WelAdapter welAdapter = new WelAdapter();
			mViewPager.setAdapter(welAdapter);
		}

	}

	class WelAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mWelList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(WelcomeActivity.this);
			
			if (null != mWelList) {
				ImageListener listener = ImageLoader.getImageListener(
						imageView, R.drawable.ic_launcher,
						R.drawable.ic_launcher);
//				mImageLoader = new ImageLoader(
//						ElifeApplication.getRequestQueue(), BitmapCache.getInstance());
				String imageUrl = mWelList.get(position).welImgUrl;
//				mImageLoader.get(imageUrl, listener);
				BitmapCache.getImageLoader().get(imageUrl, listener);
				
				if (position == (mWelList.size()-1)) {
					imageView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getApplicationContext(), MainActivity.class);
							startActivity(intent);
							WelcomeActivity.this.finish();
						}
					});
				}
			}
			container.addView(imageView);
			return imageView;
		}

	}
}
