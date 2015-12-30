package com.elife.easy.base;

import android.content.Context;
import android.view.View;

public abstract class BasePager {
	public View mRootView;
	public Context mContext;

	public BasePager(Context context) {
		this.mContext = context;
		initView();
	}

	public abstract View initView();

	public abstract void initData();

	public View getRootView() {
		return mRootView;
	}

}
