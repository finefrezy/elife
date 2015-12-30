package com.elife.easy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.elife.easy.R;
import com.elife.easy.base.BaseFragment;
import com.elife.easy.base.BasePager;
import com.elife.easy.pager.ChatPager;
import com.elife.easy.pager.ForumPager;
import com.elife.easy.pager.FriendPager;
import com.elife.easy.pager.HomePager;
import com.elife.easy.pager.MsgPager;

public class HomeFragment extends BaseFragment {

	private ViewPager mViewPager;
	private RadioGroup mRadioGroup;
	private List<BasePager> mListPager;
	
	@Override
	public View initView(LayoutInflater inflater) {
		
		View view = inflater.inflate(R.layout.fragment_home, null);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mRadioGroup = (RadioGroup) view.findViewById(R.id.home_radio);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		
		mListPager = new ArrayList<BasePager>();
		mListPager.add(new HomePager(getActivity()));
		mListPager.add(new ChatPager(getActivity()));
		mListPager.add(new MsgPager(getActivity()));
		mListPager.add(new FriendPager(getActivity()));
		mListPager.add(new ForumPager(getActivity()));
		
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					mRadioGroup.check(R.id.rb_main_home);
					break;
				case 1:
					mRadioGroup.check(R.id.rb_main_chat);
					break;
				case 2:
					mRadioGroup.check(R.id.rb_main_letter);
					break;
				case 3:
					mRadioGroup.check(R.id.rb_main_friend);
					break;
				case 4:
					mRadioGroup.check(R.id.rb_main_forum);
					break;
				}
				
				mListPager.get(position).initData();
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
		
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_main_home:
					//设置当前viewpager选中的界面
					mViewPager.setCurrentItem(0);
					break;
				case R.id.rb_main_chat:
					mViewPager.setCurrentItem(1);
					break;
				case R.id.rb_main_letter:
					mViewPager.setCurrentItem(2);
					break;
				case R.id.rb_main_friend:
					mViewPager.setCurrentItem(3);
					break;
				case R.id.rb_main_forum:
					mViewPager.setCurrentItem(4);
					break;
				}
			}
		});
		
		
		MainPagerAdapter mainPagerAdapter = new MainPagerAdapter();
		
		mViewPager.setAdapter(mainPagerAdapter);
		
		mListPager.get(0).initData();
	}
	
	class MainPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListPager.get(position).getRootView());
			return mListPager.get(position).getRootView();
		}
		
		
		
	}

}
