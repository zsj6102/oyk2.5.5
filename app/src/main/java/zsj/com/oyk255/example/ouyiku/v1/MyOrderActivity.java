package zsj.com.oyk255.example.ouyiku.v1;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.Order_allFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Order_daiFaFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Order_daiFuFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Order_daiPingFragment;
import zsj.com.oyk255.example.ouyiku.fragment.Order_daiShouFragment;
import com.umeng.message.PushAgent;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class MyOrderActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;
	InputMethodManager im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
	}

	private void initUI() {
		
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		 mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		 
		 mTabHost.addTab(mTabHost.newTabSpec("全部").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item_all, null)),
	                Order_allFragment.class, null);
	        mTabHost.addTab(mTabHost.newTabSpec("待付款").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item_fu, null)),
	        		Order_daiFuFragment.class, null);
	        mTabHost.addTab(mTabHost.newTabSpec("待发货").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item_fa, null)),
	        		Order_daiFaFragment.class, null);
	        mTabHost.addTab(mTabHost.newTabSpec("待收货").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item_shoul, null)),
	        		Order_daiShouFragment.class, null);
//	        mTabHost.addTab(mTabHost.newTabSpec("待评价").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item_ping, null)),
//	        		Order_daiPingFragment.class, null);
	        
	        findViewById(R.id.order_back).setOnClickListener(new OnClickListener() {
				
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
