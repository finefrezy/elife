package com.elife.easy.pager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.elife.easy.R;
import com.elife.easy.activity.MainActivity;
import com.elife.easy.base.BasePager;

public class ChatPager extends BasePager {

	public ChatPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		mRootView = View.inflate(mContext, R.layout.pager_chat, null);

		return mRootView;
	}

	@Override
	public void initData() {
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.fi);

		((MainActivity) mContext).beautyTitle(bitmap);
	}

}
