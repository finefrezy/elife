package com.elife.easy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.elife.easy.R;
import com.elife.easy.constant.ConstantData;
import com.elife.easy.recycler.vh.ImageViewHolder;
import com.elife.easy.util.ImageCropUtil;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

	private String[] mCyclerImages;
	private Context mContext;
	private int mScreenWidth;
	private ImageLoader mImageLoader;

	public ImageAdapter(String[] images, Context ctx, int width,
			ImageLoader imageLoader) {
		mCyclerImages = images;
		mContext = ctx;
		mScreenWidth = width - 15;
		mImageLoader = imageLoader;
	}

	@Override
	public int getItemCount() {
		if (null == mCyclerImages) {
			return 0;
		}
		if (ConstantData.DEBUG) {
			Log.d(ConstantData.TAG, "" + mCyclerImages.length);
		}

		return mCyclerImages.length;
	}

	@Override
	public void onBindViewHolder(ImageViewHolder ivh, int position) {
		if (ConstantData.DEBUG) {
			Log.d(ConstantData.TAG, "onBindViewHolder->" + position);
		}
		ivh.mPosition = position;

		ImageListener listener = ImageLoader.getImageListener(ivh.mImageView,
				ConstantData.DEFAULT_IMAGE, ConstantData.DEFAULT_IMAGE);

		mImageLoader.get(ConstantData.SERVER_URL + mCyclerImages[position],
				listener, 0, mScreenWidth / 3);

	}

	@Override
	public ImageViewHolder onCreateViewHolder(ViewGroup vg, int position) {

		View view = View.inflate(vg.getContext(), R.layout.image_item, null);

		return new ImageViewHolder(view, mCyclerImages, mContext, mScreenWidth,
				mImageLoader);
	}
}
