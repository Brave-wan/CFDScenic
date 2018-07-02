package com.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.demo.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LeadPageActivity extends Activity {
	ViewPager vp;
	List<View> views = new ArrayList<View>();
	MyAdapter adapter;
	private Handler handler;
	ImageView miv;
	String token;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.lead_page);
		init();
	}

	private void init() {
		vp = (ViewPager) findViewById(R.id.vp_welcome);
		View view;
		view = getLayoutInflater().inflate(R.layout.page4, null);
		views.add(getLayoutInflater().inflate(R.layout.page1, null));
		views.add(getLayoutInflater().inflate(R.layout.page2, null));
		views.add(view);
		adapter = new MyAdapter(views,this);
		vp.setAdapter(adapter);
		miv = (ImageView) view.findViewById(R.id.iv_page4_login_register);

	}



	private class MyAdapter extends PagerAdapter {
		// 界面列表
		private List<View> views;
		private Activity activity;
		private static final String SHAREDPREFERENCES_NAME = "first_pref";
		public MyAdapter(List<View> views, Activity activity) {
			this.views = views;
			this.activity = activity;
		}


		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = views.get(position);
			container.addView(view);
			if(position==views.size()-1){
				ImageView mStartWeiboImageButton = (ImageView) container.findViewById(R.id.lijitiyan);
				mStartWeiboImageButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 设置已经引导
						setGuided();
						goHome();

					}

				});
			}
			return view;
		}
		private void goHome() {
			// 跳转
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}

		/**
		 *
		 * method desc：设置已经引导过了，下次启动不用再次引导
		 */
		private void setGuided() {
			SharedPreferences preferences = activity.getSharedPreferences(
					SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			// 存入数据
			editor.putBoolean("isFirstIn", false);
			// 提交修改
			editor.commit();
		}

	}
}
