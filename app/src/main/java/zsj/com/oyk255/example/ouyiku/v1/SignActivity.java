package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.SignDialog;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.SignRecord_pop;
import zsj.com.oyk255.example.ouyiku.homejson.IsSign;
import zsj.com.oyk255.example.ouyiku.homejson.IsSignData;
import zsj.com.oyk255.example.ouyiku.homejson.SignCoupon;
import zsj.com.oyk255.example.ouyiku.homejson.SignCouponDatum;
import zsj.com.oyk255.example.ouyiku.homejson.SignData;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SignActivity extends OykActivity implements OnClickListener{

	private ImageView mImg_sign;
//	private TextView mImg_lingqu1;
//	private RelativeLayout sign_youhui_layout1;
	private SignDialog signDialog;
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	
	
	private TextView mTv_time;
	private ImageView mTopIMG;
	private TextView mLianxu;
	private TextView mTotal;
	
	ArrayList<SignCouponDatum> mCouponData=new ArrayList<SignCouponDatum>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		isSign();
		initCoupon();
		
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		isSign();
		initCoupon();
		super.onStart();
	}
	
	private void initCoupon() {
		String url= Constant.URL.SignCouponUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("usr_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				SignCoupon fromJson = gson.fromJson(arg0, SignCoupon.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<SignCouponDatum> data = fromJson.getData();
					mCouponData.clear();
					mCouponData.addAll(data);
					mListView.setAdapter(new SignAdapter());
					
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void Sign(){
		String url=Constant.URL.SignInUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				zsj.com.oyk255.example.ouyiku.homejson.Sign fromJson = gson.fromJson(arg0, zsj.com.oyk255.example.ouyiku.homejson.Sign.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					SignData data = fromJson.getData();
					String money = data.getMoney();
					
					signDialog.showAtLocation(findViewById(R.id.sign_layout), Gravity.CENTER, 0, 0);
					ImageView mClose = (ImageView) signDialog.view.findViewById(R.id.dialog_close);
					mClose.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							signDialog.dismiss();
							isSign();
						}
					});
					TextView mMoney = (TextView) signDialog.view.findViewById(R.id.sign_money);
					mMoney.setText(money+"元");
					mTv_time = (TextView) signDialog.view.findViewById(R.id.dialog_tv);
					
					  timer.schedule(task, 1000, 1000);  
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		
	}

	private void isSign() {
		String url=Constant.URL.IsSingUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				IsSign fromJson = gson.fromJson(arg0, IsSign.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					IsSignData data = fromJson.getData();
					String img = data.getImg();
					String issign = data.getIssign();
					String monthtotal = data.getMonthtotal();
					String signsum = data.getSignsum();
					
					UILUtils.displayImageNoAnim(img, mTopIMG);
					mLianxu.setText("已连续签到"+signsum+"天");
					mTotal.setText("已累计签到"+monthtotal+"天");
					
					if(issign.equals("1")){
						mImg_sign.setImageResource(R.mipmap.isok);
						mImg_sign.setEnabled(false);
					}else{
						mImg_sign.setImageResource(R.mipmap.no);
						mImg_sign.setEnabled(true);
					}
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	private void initUI() {
		findViewById(R.id.back_sign).setOnClickListener(this);
		mImg_sign = (ImageView) findViewById(R.id.sign_img);
		mImg_sign.setOnClickListener(this);
		
		signDialog = new SignDialog(this);
		
		signRecord_pop = new SignRecord_pop(this);
		
		mTopIMG = (ImageView) findViewById(R.id.sign_topimg);
		mLianxu = (TextView) findViewById(R.id.sign_lianxu);
		mTotal = (TextView) findViewById(R.id.sign_leiji);
		
		findViewById(R.id.sign_gonglue).setOnClickListener(this);
		
		mListView = (ScrollViewWithListView) findViewById(R.id.sign_listview);
		
		
		
		
	}
	
	class SignAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCouponData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout =null;
			ViewHolder holder;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.item_sign_coupon, null);
				holder=new ViewHolder();
				holder.mImg = (ImageView) layout.findViewById(R.id.signcoupon_img);
				holder.mContent = (TextView) layout.findViewById(R.id.signcoupon_content);
				holder.msign_lingqu = (TextView) layout.findViewById(R.id.sign_lingqu);
				holder.sign_youhui_layout = (RelativeLayout) layout.findViewById(R.id.sign_youhui_layout);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
			SignCouponDatum signCouponDatum = mCouponData.get(position);
			String img = signCouponDatum.getImg();
			String content = signCouponDatum.getContent();
			final String getid = signCouponDatum.getGetid();
			String status = signCouponDatum.getStatus();
			
			UILUtils.displayImageNoAnim(img, holder.mImg);
			holder.mContent.setText(content);
			if(status.equals("0")){
				holder.sign_youhui_layout.setBackgroundResource(R.mipmap.qd_btn_ok2);
				holder.msign_lingqu.setText("领取");
				holder.msign_lingqu.setEnabled(false);
			}else if(status.equals("1")){
				holder.sign_youhui_layout.setBackgroundResource(R.mipmap.qd_btnw);
				holder.msign_lingqu.setText("领取");
				holder.msign_lingqu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						GetCoupon(getid);
						
					}
				});
			}else if(status.equals("2")){
				holder.sign_youhui_layout.setBackgroundResource(R.mipmap.qd_btn_ok2);
				holder.msign_lingqu.setText("已领取");
				holder.msign_lingqu.setEnabled(false);
			}
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		ImageView mImg;
		TextView mContent;
		TextView msign_lingqu;
		RelativeLayout sign_youhui_layout;
		
	}
	//领取优惠券
	private void GetCoupon(String getid){
		String url=Constant.URL.GetSignCouponUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("getid", getid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				Log.e("领取优惠券", arg0);
				IsSign fromJson = gson.fromJson(arg0, IsSign.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					IsSignData data = fromJson.getData();
					String money = data.getMoney();
					String isget = data.getIsget();
					initCoupon();
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	Handler handler=new Handler();
	int i=4;
	Timer timer = new Timer();  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_sign:
			finish();
			break;
		case R.id.sign_img:
			if(!userid.equals("")){
				Sign();
			}else{
				startActivity(new Intent(SignActivity.this, LoginActivity.class));
			}
			
			
			break;
		case R.id.sign_gonglue:
			if(!userid.equals("")){
				
				signRecord_pop.showAtLocation(findViewById(R.id.sign_layout), Gravity.CENTER, 0, 0);
				WebView mWebView = (WebView) signRecord_pop.view.findViewById(R.id.sign_webview);
				mWebView.setWebViewClient(new webViewClient()); 

		        //加载地址 
				WebSettings settings = mWebView.getSettings();
				settings.setJavaScriptEnabled(true);
				mWebView.loadUrl("http://a.ouyiku.com/?c=sign&a=signdes&type=0&user_id="+userid);
				
			}else{
				startActivity(new Intent(SignActivity.this, LoginActivity.class));
			}
			
			break;

		default:
			break;
		}
		
	}
	
	  class webViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

	        view.loadUrl(url+"&user_id="+userid); 

	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 

	        return true; 

	    } 

	        

	   } 
	
	TimerTask task = new TimerTask() {  
        @Override  
        public void run() {  
  
            runOnUiThread(new Runnable() {      
                @Override  
                public void run() {  
                    i--;  
                    mTv_time.setText(i+"秒后自动关闭");  
                    if(i < 1){  
                    	signDialog.dismiss();
                    	isSign();
                    }  
                }  
            });  
        }  
    };
	private SignRecord_pop signRecord_pop;
	private ScrollViewWithListView mListView;
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {  
        	task.cancel();
        	finish();
        	isSign();
             return false;  
        }else {  
            return super.onKeyDown(keyCode, event);  
        }  
          
    }  

}
