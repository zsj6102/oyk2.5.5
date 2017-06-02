package zsj.com.oyk255.example.ouyiku.v1;


import com.umeng.message.PushAgent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;

import zsj.com.oyk255.R;

public class YinDaoActivity extends FragmentActivity {
	int[] mImgRes=new int[]{R.mipmap.yin1, R.mipmap.yin2,R.mipmap.yin3,R.mipmap.yin4};
//	private ViewpageIndicator mIndicator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yin_dao);
		PushAgent.getInstance(this).onAppStart();
		initUI();
	}

	private void initUI() {
		ViewPager mPage = (ViewPager) findViewById(R.id.yindao);
//		mIndicator = (ViewpageIndicator) findViewById(R.id.yindao_indicator);
		
		FragmentManager fm = getSupportFragmentManager();
		
		mPage.setAdapter(new ViewPageAdapter(fm));
//		mPage.setOnPageChangeListener(new BannerPagerChangedListener());
		
	}

	class ViewPageAdapter extends FragmentPagerAdapter{

		public ViewPageAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			
			YindAOFragment yindaoFragment = new YindAOFragment(arg0);
			
			return yindaoFragment;
		}

		@Override
		public int getCount() {
			return mImgRes.length;
		}
		
	}
	
//	class BannerPagerChangedListener implements OnPageChangeListener{
//		@Override
//			public void onPageScrollStateChanged(int state) {
//		}
//		
//
//			@Override
//			public void onPageScrolled(int position, float arg1, int arg2) {
//				//自定义viewpageIndicator
//				position%=4;
//				mIndicator.move(position, arg1,4);
//			}
//
//			@Override
//			public void onPageSelected(int arg0) {
//			}
//
//	}
}
