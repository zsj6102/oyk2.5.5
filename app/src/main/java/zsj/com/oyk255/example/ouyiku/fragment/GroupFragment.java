package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.groupjson.Datum;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupBanner;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.SimpleViewPagerIndicator;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GroupFragment extends Fragment {
	boolean mDragging;
	private View rootview;
	private ViewPager pager;
	int[] mImgRes=new int[]{R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	private ViewpageIndicator viewpageIndicator;
	private String[] mTitles = new String[] {"商品","品牌"};
	private SimpleViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	ArrayList<Datum> mBannerData =new ArrayList<Datum>();
	public GroupFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootview==null){
			rootview = inflater.inflate(R.layout.fragment_group, container, false);
			initUI();
			initData();
			initEvents();
			
		}
		return rootview;
	}
@Override
public void onStart() {
	super.onStart();
}
	private void initEvents() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				mIndicator.scroll(position, positionOffset);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String GroupBannerURL= Constant.URL.GroupbannerURL;
		HTTPUtils.post(getActivity(), GroupBannerURL, null, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				GroupBanner fromJson = gson.fromJson(arg0, GroupBanner.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if (succeed.equals("1")) {
					mBannerData.clear();
					List<Datum> data = fromJson.getData();
					mBannerData.addAll(data);
					FragmentManager fm = getChildFragmentManager();
					pager.setAdapter(new BannerAdapter(fm));
					pager.setOnPageChangeListener(new BannerPagerChangedListener());
					pager.setCurrentItem(50000);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
		mIndicator.setTitles(mTitles);
		
		mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
			@Override
			public int getCount() {
				return mTitles.length;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
				case 0:
					GoodsFragment goodsFragment = new GoodsFragment();
					return goodsFragment;
				case 1:
					BrandFragment brandFragment = new BrandFragment();
					return brandFragment;
				default:
					return null;
				}
			}
		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
	}

	private void initUI() {
		pager = (ViewPager) rootview.findViewById(R.id.group_lunbo);
		viewpageIndicator = (ViewpageIndicator) rootview.findViewById(R.id.viewpageIndicator3);
		
		// 自动滑动
		autoScroll();
		
		mIndicator = (SimpleViewPagerIndicator) rootview.findViewById(R.id.id_stickynavlayout_indicator);
		mViewPager = (ViewPager) rootview.findViewById(R.id.id_stickynavlayout_viewpager);
		
		
		
	}
	
	private void autoScroll() {
		pager.postDelayed( new Runnable() {
			public void run() {
				int position = pager.getCurrentItem();
				if(!mDragging ){//如果手指没有触摸
					pager.setCurrentItem(position + 1);
				}
				pager.postDelayed(this, 4000);
			}
		}, 4000);
	}

	class BannerAdapter extends FragmentPagerAdapter{
		public BannerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position ) {
			int index= position %mImgRes.length;
			if(mBannerData.size()==0){
				BannerItemFragment bannerItemFragment = new BannerItemFragment(index, mImgRes[index]);
				return bannerItemFragment;
			}else{
				Datum bannerDatum = mBannerData.get(index);
				BannerItemFragment bannerItemFragment2 = new BannerItemFragment(index, bannerDatum);
				return bannerItemFragment2;
			}
		}

		@Override
		public int getCount() {
			return 100000;
		}
		
	}
	class BannerPagerChangedListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_IDLE://手指松开的时候
				
				mDragging = false;
				break;
			case ViewPager.SCROLL_STATE_DRAGGING://手指触摸的时候
				mDragging = true;
				break;
			case ViewPager.SCROLL_STATE_SETTLING://默认状态
				mDragging = false;
				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			position%=4;
			viewpageIndicator.move(position, arg1,4);
		}

		@Override
		public void onPageSelected(int arg0) {
		}
		
	}
	
}
