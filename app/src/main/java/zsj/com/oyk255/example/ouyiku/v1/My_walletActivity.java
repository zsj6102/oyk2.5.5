package zsj.com.oyk255.example.ouyiku.v1;


import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTop;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTopData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.fragment.ChongzhiFragment;
import zsj.com.oyk255.example.ouyiku.fragment.TixianFragment;
import zsj.com.oyk255.example.ouyiku.fragment.TradingFragment;
import zsj.com.oyk255.example.ouyiku.fragment.YinhangkaFragment;
import zsj.com.oyk255.example.ouyiku.pagerslidingstrip.PagerSlidingTabStrip;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class My_walletActivity extends FragmentActivity {
	private String[] mTitlers = new String[] {"充值","提现","银行卡","交易记录"};
	private  ViewPager viewpage2;
	private PagerSlidingTabStrip tabs;
	InputMethodManager im;
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private TextView mWallAll;
	private TextView mWallTiXian;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wallet);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.WalletTopURL+"&user_id="+userid+"&token="+token;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MyWalletTop fromJson = gson.fromJson(arg0, MyWalletTop.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					MyWalletTopData data = fromJson.getData();
					String allMoney = data.getAllMoney();
					String usefullMoney = data.getUsefullMoney();
					mWallAll.setText(allMoney);
					mWallTiXian.setText(usefullMoney);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		mWallAll = (TextView) findViewById(R.id.wallet_zongzichan);
		mWallTiXian = (TextView) findViewById(R.id.wallet_tixian);
		
		FragmentManager fragmentManager2 = getSupportFragmentManager();
		tabs = (PagerSlidingTabStrip) findViewById(R.id.wallet_tabs);
		viewpage2 = (ViewPager) findViewById(R.id.wallet_pages);
		viewpage2.setAdapter(new ViewPageAdapter2(fragmentManager2));
		tabs.setViewPager(viewpage2);
		findViewById(R.id.wallet_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	public void toTixian(View view){
		viewpage2.setCurrentItem(1);
	}
	
	
	class ViewPageAdapter2 extends FragmentPagerAdapter{

		public ViewPageAdapter2(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				ChongzhiFragment ChongzhiFragment = new ChongzhiFragment();
				return ChongzhiFragment;
			case 1:
				TixianFragment TixianFragment = new TixianFragment();
				return TixianFragment;
			case 2:
				YinhangkaFragment YinhangkaFragment = new YinhangkaFragment();
				return YinhangkaFragment;
			case 3:
				TradingFragment TradingFragment = new TradingFragment();
				return TradingFragment;
			default:
				return null;
			}
			
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
