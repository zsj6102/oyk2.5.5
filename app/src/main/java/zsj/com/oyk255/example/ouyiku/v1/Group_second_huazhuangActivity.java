package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.BrandMeizhuangFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Group_second_huazhuangFragment;
import zsj.com.oyk255.example.ouyiku.groupjson.SecondGroupImg;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.groupjson.TopDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.SimpleViewPagerIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class Group_second_huazhuangActivity extends FragmentActivity {
	InputMethodManager im;
	private SimpleViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
	private String[] mTitles = new String[] {"热门分类","时尚品牌"};
	private FragmentPagerAdapter mAdapter;
	private ImageView mTop;
	String pcategoryId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_second_huazhuang);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		Intent intent = getIntent();
		pcategoryId = intent.getStringExtra("pcategoryId");
		initUI();
		initData();
		initEvents();
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
		mIndicator.setTitles(mTitles);


		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTitles.length;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
				case 0:
					Group_second_huazhuangFragment goodsFragment = new Group_second_huazhuangFragment(pcategoryId);
					return goodsFragment;
				case 1:
					BrandMeizhuangFragment brandFragment = new BrandMeizhuangFragment();
					return brandFragment;
				default:
					return null;
				}
			}
		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		
		//二级分类头布局图片
		String TopImgUrl= Constant.URL.GroupSecondTopURL;
		HashMap<String, String> imgmap=new HashMap<String, String>();
		imgmap.put("cat_id", pcategoryId);
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.post(this, TopImgUrl, imgmap, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SecondGroupImg fromJson = gson.fromJson(arg0, SecondGroupImg.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<TopDatum> data = fromJson.getData();
					TopDatum topDatum = data.get(0);
					String picUrl = topDatum.getPicUrl();
					UILUtils.displayImageNoAnim(picUrl, mTop);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
		mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
		mTop = (ImageView) findViewById(R.id.group_second_title_meizhuang_img);
		findViewById(R.id.group_second_title_meizhuang_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
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
