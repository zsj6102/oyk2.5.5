package zsj.com.oyk255.example.ouyiku.v1;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.Coupon_canUseFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Coupon_unUseFragment;
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
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class CouponsActivity extends FragmentActivity implements OnClickListener{

	//优惠券红包
	private String[] mTitlers = new String[] {"可使用","已使用/过期"};
	InputMethodManager im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
	}

	private void initUI() {
		findViewById(R.id.coupons_back).setOnClickListener(this);
		FragmentManager fragmentManager2 = getSupportFragmentManager();
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.coupon_tabs);
		ViewPager viewpage = (ViewPager) findViewById(R.id.coupon_pages);
		
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
				 Coupon_canUseFragment coupon_canUseFragment = new Coupon_canUseFragment();
				return coupon_canUseFragment;
			case 1:
				 Coupon_unUseFragment coupon_unUseFragment = new Coupon_unUseFragment();
				return coupon_unUseFragment;
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coupons_back:
			finish();
			break;

		default:
			break;
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
