package zsj.com.oyk255.example.ouyiku.v1;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.pagerslidingstrip.PagerSlidingTabStrip;
import zsj.com.oyk255.example.ouyiku.v1.PinTuanActivity.ViewPageAdapter2;
import com.umeng.message.PushAgent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class MyPinTuanActivity extends FragmentActivity {
	private String[] mTitlers = new String[] {"全部","组团中","已成团","组团失败"};
	InputMethodManager im;
	private PagerSlidingTabStrip tabs;
	private ViewPager mViewpage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pin_tuan);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
	}

	private void initUI() {
		findViewById(R.id.mytuan_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
			FragmentManager fm = getSupportFragmentManager();
			tabs = (PagerSlidingTabStrip) findViewById(R.id.mytuan_tabs);
			mViewpage = (ViewPager) findViewById(R.id.mytuan_pages);
			mViewpage.setAdapter(new ViewPageAdapter2(fm));
			tabs.setViewPager(mViewpage);
		
	}
	
	
	
	class ViewPageAdapter2 extends FragmentPagerAdapter{


		public ViewPageAdapter2(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
				
			MyPinFragment myPinFragment = new MyPinFragment(position);
					return myPinFragment;
			
		}

		@Override
		public int getCount() {
			return mTitlers.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			
			return mTitlers[position];
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
