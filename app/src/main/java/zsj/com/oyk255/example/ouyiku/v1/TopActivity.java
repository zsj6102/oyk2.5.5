package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.TopFragment;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TopTabs;
import zsj.com.oyk255.example.ouyiku.homejson.TopTabsDatum;
import zsj.com.oyk255.example.ouyiku.pagerslidingstrip.PagerSlidingTabStrip;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class TopActivity extends FragmentActivity {
	InputMethodManager im;
	private PagerSlidingTabStrip tabs;
	private ViewPager mViewpage;
	ArrayList<TopTabsDatum> mTabsData=new ArrayList<TopTabsDatum>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
		initTabs();
	}
	private void initTabs() {
		String url= Constant.URL.TopTabslUrl;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				TopTabs fromJson = gson.fromJson(arg0, TopTabs.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<TopTabsDatum> data = fromJson.getData();
					mTabsData.clear();
					mTabsData.addAll(data);
					
					FragmentManager fm = getSupportFragmentManager();
					mViewpage.setAdapter(new ViewPageAdapter2(fm));
					tabs.setViewPager(mViewpage);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
				
		
	}
	private void initUI() {
		
			findViewById(R.id.top_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
			
			
			tabs = (PagerSlidingTabStrip) findViewById(R.id.top_tabs);
			mViewpage = (ViewPager) findViewById(R.id.top_pages);
			
			
	}
	
	class ViewPageAdapter2 extends FragmentPagerAdapter{


		public ViewPageAdapter2(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
			String pcategoryId = mTabsData.get(position).getPcategoryId();
			TopFragment TopFragment = new TopFragment(pcategoryId);
					return TopFragment;
			
		}

		@Override
		public int getCount() {
			return mTabsData.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			
			return mTabsData.get(position).getMName();
		}
	}
	
	@Override
	public void finish() {
		hideSoftInput();
		super.finish();
		overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
	}
	//关闭输入法
			protected void hideSoftInput() {
				View view = getCurrentFocus();
				if(view != null) {
					IBinder binder = view.getWindowToken();
					if(binder != null) {
						im.hideSoftInputFromWindow(binder, 0);
					}
				}
			}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
