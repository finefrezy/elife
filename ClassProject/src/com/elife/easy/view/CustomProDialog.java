package com.elife.easy.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.elife.easy.R;

/**
 * 通用dialog
 * @author admin
 *
 */
public class CustomProDialog extends Dialog {
	private Context context = null;
	private static CustomProDialog customProgressDialog = null;

	public CustomProDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public static CustomProDialog createDialog(Context context) {
		customProgressDialog = new CustomProDialog(context,
				R.style.custom_progress_dialog);
		customProgressDialog.setContentView(R.layout.progress_loading);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCancelable(true);

		return customProgressDialog;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		if (customProgressDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog
				.findViewById(R.id.progress_loading_image);
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.progress_loading);
		imageView.startAnimation(hyperspaceJumpAnimation);
	}

}
