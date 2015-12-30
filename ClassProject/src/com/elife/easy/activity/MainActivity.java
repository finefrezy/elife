package com.elife.easy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette.Swatch;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.elife.easy.LoginActivity;
import com.elife.easy.R;
import com.elife.easy.cache.BitmapCache;
import com.elife.easy.fragment.HomeFragment;
import com.elife.easy.util.PaletteColorUtil;
import com.elife.easy.util.PaletteColorUtil.ICorlorChangeListener;
import com.elife.easy.util.TitleBarUtil;

public class MainActivity extends AppCompatActivity implements
		ICorlorChangeListener {

	private static final String HOME_FRAGMENT_FLAG = "home_fragment";

	private HomeFragment mHomeFragment;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private Toolbar mTitleToolbar;
	private SearchView mSearchView;

	private ImageView mHeadImage;
	private TextView mUserName;
	private TextView mUserMessage;
	private TextView mUserGrade;
	private TextView mUserSetting;
	private TextView mUserSkin;
	private Button mUserLogin;
	private RelativeLayout mUserBaseInfo;

	private Window mWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {

		mWindow = getWindow();
		PaletteColorUtil.setListener(this);

		mTitleToolbar = (Toolbar) findViewById(R.id.toolbar);
		// mTitleToolbar.setLogo(R.drawable.ic_launcher);
		mTitleToolbar.setTitle("Fine Frenzy");// 标题的文字需在setSupportActionBar之前，不然会无效
		// mTitleToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		// mTitleToolbar.setSubtitle("副标题");

		setSupportActionBar(mTitleToolbar);

		mTitleToolbar
				.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {

						// case R.id.action_share:
						// Toast.makeText(MainActivity.this, "action_share", 0)
						// .show();
						// break;
						default:
							break;
						}
						return true;
					}
				});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		/* findView */
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				mTitleToolbar, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

				initUserInfo();

			}

		};

		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mHomeFragment = new HomeFragment();
		// replace add remove show hide
		// add 添加 remove移除 show显示 hide隐藏 replace-》remove，add
		// 你只能在activity保存它的状态(当用户离开activity)之前使用commit()提交事务.

		// 如果你试图在那个点之后提交, 会抛出一个异常.这是因为如果activity需要被恢复, 提交之后的状态可能会丢失.
		// 对于你觉得可以丢失提交的状况, 使用 commitAllowingStateLoss()
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content, mHomeFragment, HOME_FRAGMENT_FLAG)
				.commit();

		initDrawerView();

	}

	private void initDrawerView() {
		mHeadImage = (ImageView) findViewById(R.id.user_head);
		mUserName = (TextView) findViewById(R.id.user_name);
		mUserGrade = (TextView) findViewById(R.id.user_grade);
		mUserMessage = (TextView) findViewById(R.id.user_message);
		mUserSetting = (TextView) findViewById(R.id.user_setting);
		mUserSkin = (TextView) findViewById(R.id.user_skin);
		mUserLogin = (Button) findViewById(R.id.user_login);
		mUserBaseInfo = (RelativeLayout) findViewById(R.id.user_base_info);

		mUserLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	public void beautyTitle(Bitmap bitmap) {

		PaletteColorUtil.colorChange(bitmap);
	}

	public HomeFragment getHomeFragment() {
		return mHomeFragment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem menuSearch = menu.findItem(R.id.ab_search);
		mSearchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
		TitleBarUtil.setSearchView(mSearchView, getApplicationContext());

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 界面发生变化时改变头部颜色的回调
	 */
	@Override
	public void changeCompColor(Swatch vibrant) {
		mTitleToolbar.setBackgroundColor(vibrant.getRgb());
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			// 很明显，这两货是新API才有的。
			mWindow.setStatusBarColor(PaletteColorUtil.colorBurn(vibrant
					.getRgb()));
			mWindow.setNavigationBarColor(PaletteColorUtil.colorBurn(vibrant
					.getRgb()));
		}
	}

	public void initUserInfo() {

		ImageListener listener = ImageLoader.getImageCornerListener(mHeadImage,
				R.drawable.default_img, R.drawable.default_img,
				getApplicationContext());

		BitmapCache
				.getImageLoader()
				.get("http://img4.duitang.com/uploads/blog/201307/03/20130703212525_JBtjC.jpeg",
						listener,
						com.elife.easy.util.CommonUtil.px2dip(
								getApplicationContext(), 90),
						com.elife.easy.util.CommonUtil.px2dip(
								getApplicationContext(), 90));

	}

	// @Override
	// protected void onPostCreate(Bundle savedInstanceState) {
	// super.onPostCreate(savedInstanceState);
	// // Sync the toggle state after onRestoreInstanceState has occurred.
	// mDrawerToggle.syncState();
	// }
	//
	// @Override
	// public void onConfigurationChanged(Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	// mDrawerToggle.onConfigurationChanged(newConfig);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Pass the event to ActionBarDrawerToggle, if it returns
	// // true, then it has handled the app icon touch event
	// if (mDrawerToggle.onOptionsItemSelected(item)) {
	// return true;
	// }
	// // Handle your other action bar items...
	//
	// return super.onOptionsItemSelected(item);
	// }

}
