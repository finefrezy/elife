package com.elife.easy.pager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.elife.easy.R;
import com.elife.easy.activity.MainActivity;
import com.elife.easy.base.BasePager;

public class FriendPager extends BasePager {

	public FriendPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		mRootView = View.inflate(mContext, R.layout.pager_friend, null);

		return mRootView;
	}

	@Override
	public void initData() {
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.fo);

		((MainActivity) mContext).beautyTitle(bitmap);

	}

}
