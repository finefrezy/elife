package com.elife.easy.pager;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.elife.easy.R;
import com.elife.easy.activity.MainActivity;
import com.elife.easy.base.BasePager;
import com.elife.easy.constant.ConstantData;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class HomePager extends BasePager {

	ImageView mImageOne;
	ImageView mImageTwo;
	ImageView mImageThree;
	ImageView mImageFour;

	Button mUploadOneBtn;
	Button mUploadMultiBtn;
	String mSdPath;

	public HomePager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		mRootView = View.inflate(mContext, R.layout.pager_home, null);
		mImageOne = (ImageView) mRootView.findViewById(R.id.image1);
		mImageTwo = (ImageView) mRootView.findViewById(R.id.image2);
		mImageThree = (ImageView) mRootView.findViewById(R.id.image3);
		mImageFour = (ImageView) mRootView.findViewById(R.id.image4);

		mUploadOneBtn = (Button) mRootView.findViewById(R.id.upload_only_one);
		mUploadMultiBtn = (Button) mRootView.findViewById(R.id.upload_multiply);
		mUploadOneBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uploadHost = ConstantData.SERVER_URL + "userhead";
				RequestParams params = new RequestParams();
				params.addBodyParameter("userid", "2");
				params.addBodyParameter("head_image",
						new File(mSdPath + "/banner_aisi.png"));
				
				uploadMethod(params, uploadHost);
			}
		});

		mUploadMultiBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uploadHost = ConstantData.SERVER_URL + "userhead";
				RequestParams params = new RequestParams();
				params.addBodyParameter("msg", "GGGGG");
				
				params.addBodyParameter(mSdPath + "/test.png".replace("/", ""),
						new File(mSdPath + "/test.png"));
				params.addBodyParameter(mSdPath + "/banner_kitty.png".replace("/", ""),
						new File(mSdPath + "/banner_kitty.png"));
				params.addBodyParameter(mSdPath + "/banner_aisi.png".replace("/", ""),
						new File(mSdPath + "/banner_aisi.png"));
				params.addBodyParameter(mSdPath + "/banner_xiaoxin.png".replace("/", ""),
						new File(mSdPath + "/banner_xiaoxin.png"));
				uploadMethod(params, uploadHost);
			}
		});

		return mRootView;
	}

	@Override
	public void initData() {
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.f);
		((MainActivity) mContext).beautyTitle(bitmap);

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mSdPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();

			Log.d("HomePager", mSdPath);

			mImageOne.setImageBitmap(BitmapFactory.decodeFile(mSdPath
					+ "/test.png"));

			mImageTwo.setImageBitmap(BitmapFactory.decodeFile(mSdPath
					+ "/banner_kitty.png"));

			mImageThree.setImageBitmap(BitmapFactory.decodeFile(mSdPath
					+ "/banner_aisi.png"));
			mImageFour.setImageBitmap(BitmapFactory.decodeFile(mSdPath
					+ "/banner_xiaoxin.png"));
		}
	}

	/**
	 * 
	 * @param params 
	 * 请求参数
	 * @param uploadHost
	 * 服务器上传图片地址
	 */
	public void uploadMethod(final RequestParams params, final String uploadHost) {
		// 60 * 1000超时时间，可以不设置
		HttpUtils http = new HttpUtils(60 * 1000);
		http.send(HttpRequest.HttpMethod.POST, uploadHost, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						Toast.makeText(mContext, "start", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						if (isUploading) {
							Toast.makeText(mContext, "isUploading", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(mContext, "not isUploading", Toast.LENGTH_LONG).show();
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Toast.makeText(mContext, responseInfo.reasonPhrase, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
					}
				});
	}
}
