package com.elife.easy.pager;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.elife.easy.ElifeApplication;
import com.elife.easy.R;
import com.elife.easy.activity.MainActivity;
import com.elife.easy.adapter.ForumAdapter;
import com.elife.easy.base.BasePager;
import com.elife.easy.constant.ConstantData;
import com.elife.easy.model.ForumModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ForumPager extends BasePager implements OnRefreshListener {

	private RecyclerView mRecyclerView;
	private ForumAdapter mForumAdapter;
	private List<ForumModel> mForumList;
	private int mScreenWidth;
	private SwipeRefreshLayout mSwipeRefreeshLayout;
	private int mLastVisibleItem;
	private int mFirsrtVisibleItem;
	private LinearLayoutManager mLayoutManager;

	private LinearLayout mTopLinear;

	private static final int LOAD_MORE = 0X100;

	private int mRawY = 0;

	public ForumPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		mRootView = View.inflate(mContext, R.layout.pager_forum, null);
		mSwipeRefreeshLayout = (SwipeRefreshLayout) mRootView
				.findViewById(R.id.swipe_refresh);
		mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.forum_list);
		mTopLinear = (LinearLayout) mRootView.findViewById(R.id.top_height);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		mTopLinear.setLayoutParams(params);

		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		((MainActivity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
		mScreenWidth = mDisplayMetrics.widthPixels;

		// 进度条的颜色变化，最多可以设置4种颜色
		mSwipeRefreeshLayout.setColorSchemeResources(R.color.blue, R.color.red,
				R.color.green);
		mSwipeRefreeshLayout.setOnRefreshListener(this);
		// 第一次进入页面的时候显示加载进度条
		mSwipeRefreeshLayout.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, mContext
						.getResources().getDisplayMetrics()));

		// 使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(mContext);
		// 如果你需要显示的是横向滚动的列表或者竖直滚动的列表，则使用这个LayoutManager。
		// 生成这个LinearLayoutManager之后可以设置他滚动的方向，默认竖直滚动，所以这里没有显式地设置。
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (ConstantData.DEBUG) {
					Log.d(ConstantData.TAG, "newState = " + newState);
				}
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& mLastVisibleItem + 1 == mForumAdapter.getItemCount()) {
					// mSwipeRefreeshLayout.setRefreshing(true);

					// mLayoutManager.getChildAt(mLastVisibleItem).findViewById(R.id.footer_progressbar).setVisibility(View.VISIBLE);
					// mLayoutManager.getChildAt(mLastVisibleItem).findViewById(R.id.footer_progressbar).setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (ConstantData.DEBUG) {
					// Log.d(ConstantData.TAG, "dx = " + dx + ", dy = " + dy);
				}

				mFirsrtVisibleItem = mLayoutManager
						.findFirstVisibleItemPosition();
				mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

			}

		});

		mSwipeRefreeshLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mRawY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					setTopHeight((int) event.getRawY() - mRawY);
					mRawY = (int) event.getRawY();

					break;
				case MotionEvent.ACTION_UP:
					if (mFirsrtVisibleItem == 0) {
						resetTopHeight();
					}

					break;
				}
				return false;
			}
		});

		/**
		 * setColorSchemeColors(int... colors) 设置 进度条的颜色变化，最多可以设置4种颜色
		 * 设置SwipeRefreshLayout当前是否处于刷新状态
		 * ，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false
		 * setProgressViewOffset(boolean scale, int start, int end)
		 * 调整进度条距离屏幕顶部的距离
		 */
		// mSwipeRefreeshLayout.setRefreshing(true);
		mSwipeRefreeshLayout.setProgressViewOffset(false, -80, 100);
		return mRootView;
	}

	@Override
	public void initData() {
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.s);

		((MainActivity) mContext).beautyTitle(bitmap);
		getDataFromNet();

	}

	private void getDataFromNet() {
		StringRequest request = new StringRequest(ConstantData.SERVER_URL
				+ ConstantData.FORUM_SUFFIX, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

				mSwipeRefreeshLayout.setRefreshing(false);

				if (!TextUtils.isEmpty(response)) {
					Type type = new TypeToken<List<ForumModel>>() {
					}.getType();
					Gson gson = new Gson();
					mForumList = gson.fromJson(response, type);

					mForumAdapter = new ForumAdapter(mForumList, mContext,
							mScreenWidth);
					mRecyclerView.setAdapter(mForumAdapter);
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});

		ElifeApplication.getRequestQueue().add(request);
	}

	public void refreshData(int addFlag) {
		// FroumModel fm = getFm(addFlag);/
		// mForumList.add(0, fm);
	}

	@Override
	public void onRefresh() {
		mSwipeRefreeshLayout.setRefreshing(true);
		refreshData(0);
	}

	public void setTopHeight(int dy) {
		if (dy > 0) {
			RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mTopLinear
					.getLayoutParams();
			params.height = params.height + dy / 3;
			mTopLinear.setLayoutParams(params);
		}
	}

	public void resetTopHeight() {
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mTopLinear
				.getLayoutParams();
		params.height = 0;
		mTopLinear.setLayoutParams(params);
	}

}
