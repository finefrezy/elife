package com.elife.easy.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.elife.easy.ElifeApplication;
import com.elife.easy.R;
import com.elife.easy.cache.BitmapCache;
import com.elife.easy.constant.ConstantData;
import com.elife.easy.model.ForumModel;
import com.elife.easy.recycler.vh.FooterViewHolder;
import com.elife.easy.recycler.vh.FroumViewHolder;
import com.elife.easy.util.CommonUtil;
import com.elife.easy.util.ImageCropUtil;

public class ForumAdapter extends RecyclerView.Adapter<ViewHolder> {
	List<ForumModel> mForumList;
	Context mContext;
	private int mScreenWidth;

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;

	// private ImageLoader mImageLoader;

	public ForumAdapter(List<ForumModel> forumList, Context ctx, int width) {
		this.mForumList = forumList;
		mContext = ctx;
		mScreenWidth = width;
		// mImageLoader = new ImageLoader(ElifeApplication.getRequestQueue(),
		// BitmapCache.getInstance());
	}

	@Override
	public int getItemViewType(int position) {
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}

	@Override
	public int getItemCount() {
		if (null == mForumList) {
			return 0;
		}
		return mForumList.size() + 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, int position) {
		if (!(vh instanceof FroumViewHolder)) {
			return;
		}

		Log.d("onBindViewHolder", position + "");

		FroumViewHolder viewHolder = (FroumViewHolder) vh;
		viewHolder.mPosition = position;
		viewHolder.mContext = mContext;

		viewHolder.mNameText.setText(mForumList.get(position).getUserName());

		ImageListener listener = ImageLoader.getImageCornerListener(
				viewHolder.mImageIcon, ConstantData.DEFAULT_IMAGE,
				ConstantData.DEFAULT_IMAGE, mContext);

		// 头像信息
		// mImageLoader.get(ConstantData.SERVER_URL +
		// mForumList.get(position).getUserIcon(), listener,
		// CommonUtil.dip2px(mContext, 30), CommonUtil.dip2px(mContext, 30));
		BitmapCache.getImageLoader().get(
				ConstantData.SERVER_URL
						+ mForumList.get(position).getUserIcon(), listener,
				CommonUtil.dip2px(mContext, 30),
				CommonUtil.dip2px(mContext, 30));

		viewHolder.mMesgText.setText(mForumList.get(position).getMsgContent());
		viewHolder.mTimeText.setText(mForumList.get(position).getTimeValue());

		viewHolder.mLikeCountText.setText(String.valueOf(mForumList.get(
				position).getLikeCount()));

		if (!TextUtils.isEmpty(mForumList.get(position).getImgs())) {

			String[] imgs = mForumList.get(position).getImgs().split(",");

			if (null != imgs && imgs.length > 0) {
				ImageAdapter imageAdapter = new ImageAdapter(imgs, mContext,
						mScreenWidth, BitmapCache.getImageLoader());

				int tempWidth = 0;
				if (imgs.length <= 3) {
					tempWidth = mScreenWidth / 3;
				} else if (imgs.length <= 6) {
					tempWidth = mScreenWidth * 2 / 3;
				} else {
					tempWidth = mScreenWidth;
				}
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, tempWidth);
				params.rightMargin = CommonUtil.dip2px(mContext, 10);
				params.leftMargin = CommonUtil.dip2px(mContext, 10);
				params.topMargin = CommonUtil.dip2px(mContext, 3);
				viewHolder.mImageRecyclerView.setLayoutParams(params);

				viewHolder.mImageRecyclerView.setAdapter(imageAdapter);
			}
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		Log.d("onCreateViewHolder", "-------");
		if (viewType == TYPE_ITEM) {
			View view = View
					.inflate(vg.getContext(), R.layout.forum_item, null);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(lp);

			return new FroumViewHolder(view, mForumList);
		} else if (viewType == TYPE_FOOTER) {
			View view = View.inflate(vg.getContext(),
					R.layout.footer_load_more, null);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(lp);
			return new FooterViewHolder(view);
		}

		return null;
	}

}
