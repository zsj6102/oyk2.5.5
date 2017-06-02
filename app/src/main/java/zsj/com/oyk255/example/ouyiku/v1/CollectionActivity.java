package zsj.com.oyk255.example.ouyiku.v1;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.BabyFragment;
import zsj.com.oyk255.example.ouyiku.fragment.CollBrandFragment;
import zsj.com.oyk255.example.ouyiku.pagerslidingstrip.PagerSlidingTabStrip;

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
import android.view.inputmethod.InputMethodManager;

public class CollectionActivity extends FragmentActivity {
	boolean isTouch;
	InputMethodManager im;
	private PagerSlidingTabStrip tabs;
	private ViewPager viewpage;
	private String[] mTitlers = new String[] {"宝贝","品牌"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
	}

	private void initUI() {
		
		FragmentManager fragmentManager2 = getSupportFragmentManager();
		tabs = (PagerSlidingTabStrip) findViewById(R.id.coll_tabs);
		viewpage = (ViewPager) findViewById(R.id.coll_pages);
		
		viewpage.setAdapter(new ViewPageAdapter(fragmentManager2));
		tabs.setViewPager(viewpage);
	}
	
	
	class ViewPageAdapter extends FragmentPagerAdapter{



		public ViewPageAdapter(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				 BabyFragment babyFragment = new BabyFragment();
				return babyFragment;
			case 1:
				CollBrandFragment collBrandFragment = new CollBrandFragment();
				return collBrandFragment;
			default:
				return null;
			}
			
		}

		@Override
		public int getCount() {
			return 2;
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
