package com.elife.easy.util;

import com.elife.easy.R;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TitleBarUtil {

	public static void setSearchView(SearchView searchView, final Context ctx) {
		searchView.setQueryHint(ctx.getResources().getString(
				R.string.title_action_search));

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String subStr) {
				Toast.makeText(ctx, subStr, Toast.LENGTH_SHORT).show();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});
	}

}
