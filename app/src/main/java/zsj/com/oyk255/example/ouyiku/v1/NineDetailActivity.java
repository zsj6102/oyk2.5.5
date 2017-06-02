package zsj.com.oyk255.example.ouyiku.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;


import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.fragment.DetailGoodsinfoFragment;
import zsj.com.oyk255.example.ouyiku.huodong.HuoDongDetail;
import zsj.com.oyk255.example.ouyiku.huodong.HuoDongDetailData;
import zsj.com.oyk255.example.ouyiku.huodong.MContent;
import zsj.com.oyk255.example.ouyiku.huodong.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.CustomViewPager;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

import com.google.gson.Gson;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NineDetailActivity extends FragmentActivity implements OnClickListener{

	private ListView mListView;
	private CustomViewPager mLunBoPager;
	private ViewpageIndicator viewpageIndicator;
	InputMethodManager im;
	
	int[] mImgRes=new int[]{R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	private String productId;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private TextView mTitle;
	private TextView mAttr;
	private TextView mTime;
	private TextView mNewPrice;
	private TextView mOldPrice;
	private TextView mSku;
	private TextView mZhekou;
//	ArrayList<String> mLunboData=new ArrayList<String>();//轮播图片
	private String zeronine;
	
	ArrayList<MContent>  mGraphicData=new ArrayList<MContent>();//图文详情
	ArrayList<String> mBannerData=new ArrayList<String>();
	private IWXAPI api;
	
	private static final String APP_ID="wx246edc55db723ecb";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nine_detail);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		Intent intent = getIntent();
		productId = intent.getStringExtra("productId");
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		
		zeronine = intent.getStringExtra("zeronine");
		initUI();
		initData();
	}
	
	private String fproId;
	private String isbuy;
	private String title;
@Override
protected void onStart() {
	sp = getSharedPreferences("userdata", 0);
	userid = sp.getString("userid", "");
	token = sp.getString("token", "");
	super.onStart();
}
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.NineDetailURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("product_id", productId);
		map.put("user_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			


			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				progressHUD.dismiss();
				HuoDongDetail fromJson = gson.fromJson(arg0, HuoDongDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					HuoDongDetailData data = fromJson.getData();
					List<MContent> mContent = data.getMContent();
					mGraphicData.addAll(mContent);
					List<String> productImage = data.getProductImage();
					mBannerData.addAll(productImage);
					
					title = data.getTitle();
					String marketprice = data.getMarketprice();
					String currprice = data.getCurrprice();
					String stock = data.getStock();
					String attr = data.getAttr();
					String ltime = data.getLtime();
					fproId = data.getFproId();
					isbuy = data.getIsbuy();
					Log.e("isbuy", isbuy);
					
					mTitle.setText(title);
					mAttr.setText(attr);
					mNewPrice.setText("￥"+currprice);
					mOldPrice.setText("￥"+marketprice);
					mSku.setText("库存:"+stock);
					recLen = Integer.valueOf(ltime);
					 timer.schedule(task, 1000, 1000);
					 
					 float i = Float.parseFloat(marketprice);
						
						float i2 = Float.parseFloat(currprice);
						float f=i2/i;
						float  b   =  (float)(Math.round(f*100))/100;
				        float b2=b*10;
				        mZhekou.setText(b2+"折");
				        
				        FragmentManager fragmentManager = getSupportFragmentManager();
						mLunBoPager.setOnPageChangeListener(new ViewPageChangedListener());
						mLunBoPager.setAdapter(new ViewPageAdapter(fragmentManager));
						
						mListView.setAdapter(new GraphicAdaptr());
					
				}
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	private int recLen ;    
    Timer timer = new Timer();
    
	private void initUI() {
		findViewById(R.id.ninedetail_back).setOnClickListener(this);
		View mShare = findViewById(R.id.ninedetail_share);
		mShare.setOnClickListener(this);
		
		mListView = (ListView) findViewById(R.id.ninedetaillistview);
		
		TextView mBuy = (TextView) findViewById(R.id.ninedetail_buy);
		mBuy.setOnClickListener(this);
		View topView = getLayoutInflater().inflate(R.layout.nine_detail_topview, null);
		mLunBoPager = (CustomViewPager) topView.findViewById(R.id.detail_lunbo);
		viewpageIndicator = (ViewpageIndicator) topView.findViewById(R.id.viewpageIndicator2);
		
		mTitle = (TextView) topView.findViewById(R.id.detai_name);
		mAttr = (TextView) topView.findViewById(R.id.detail_attr);
		mTime = (TextView) topView.findViewById(R.id.detail_daojishi);
		mNewPrice = (TextView) topView.findViewById(R.id.detail_newprice);
		mOldPrice = (TextView) topView.findViewById(R.id.detail_oldprice);
		mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		mSku = (TextView) topView.findViewById(R.id.detail_sku);
		mZhekou = (TextView) topView.findViewById(R.id.detail_zhekou);
		if(zeronine.equals("zero")){
			mTime.setVisibility(View.GONE);
			mSku.setVisibility(View.GONE);
			mZhekou.setVisibility(View.GONE);
			mShare.setVisibility(View.INVISIBLE);
			
		}
		
		
		mListView.addHeaderView(topView);
		share_pop = new Share_pop(this);
	}
	
class ViewPageChangedListener implements OnPageChangeListener{
		

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			position%=mBannerData.size();
			viewpageIndicator.move(position, arg1,mBannerData.size());
		}

		@Override
		public void onPageSelected(int arg0) {
			
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
		
	}

class ViewPageAdapter extends FragmentPagerAdapter{

	public ViewPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		int index= position %mImgRes.length;
		if(mBannerData.size()==0){
			DetailGoodsinfoFragment bannerItemFragment = new DetailGoodsinfoFragment(index, mImgRes[index]);
			return bannerItemFragment;
		}else{
			String bannerDatum = mBannerData.get(index);
			DetailGoodsinfoFragment bannerItemFragment2 = new DetailGoodsinfoFragment(index, bannerDatum);
			return bannerItemFragment2;
		}
	}

	@Override
	public int getCount() {
		//TODO
		return mBannerData.size();
	}
	
}


class GraphicAdaptr extends BaseAdapter{

	@Override
	public int getCount() {
		return mGraphicData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout =null;
		ViewHolder holder;
		if(convertView==null){
			layout=getLayoutInflater().inflate(R.layout.graphic_item, null);
			holder=new ViewHolder();
			holder.mGraphic_img = (AspectRatioImageView) layout.findViewById(R.id.graphic_img);
			layout.setTag(holder);
		}else{
			layout=convertView;
			holder = (ViewHolder) layout.getTag();
		}
		 MContent mContent = mGraphicData.get(position);
		 String url = mContent.getUrl();
		UILUtils.displayImageNoAnim(url, holder.mGraphic_img);
		
		return layout;
	}
	
}
class ViewHolder{
	AspectRatioImageView mGraphic_img;
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ninedetail_back:
			finish();
			break;
		case R.id.ninedetail_share:
			
			share_pop.showAtLocation(findViewById(R.id.ninedetaillayout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					api.registerApp(APP_ID);
					api.sendReq(createReq(false));
					
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					api.registerApp(APP_ID);
					api.sendReq(createReq(true));
					
				}
			});
			
			break;
		case R.id.ninedetail_buy:
			if(!userid.equals("")){
				if(isbuy.equals("0")){
					Toast.makeText(NineDetailActivity.this, "已购买过此商品", Toast.LENGTH_SHORT).show();
				} else if(isbuy.equals("2")){
					Toast.makeText(NineDetailActivity.this, "没有库存了", Toast.LENGTH_SHORT).show();
				} else if(isbuy.equals("1")){
					Intent intent = new Intent(NineDetailActivity.this, ConfirmOrder9and0Activity.class);
					intent.putExtra("productId", productId);
					startActivity(intent);
				} else if(isbuy.equals("3")){
					Toast.makeText(NineDetailActivity.this, "活动未开始", Toast.LENGTH_SHORT).show();
				}
				else  if(isbuy.equals("4")){
					Toast.makeText(NineDetailActivity.this, "活动已结束", Toast.LENGTH_SHORT).show();
				}
				
			}else{
				startActivity(new Intent(this, LoginActivity.class));
			}
			
			break;

		default:
			break;
		}
		
	}
	
	//时间戳转换
		public String times(long time) {
	        SimpleDateFormat sdr = new SimpleDateFormat("dd天HH时mm分ss秒");
	        String times = sdr.format(new Date(time * 1000L));
	        return times;
		}
		
		TimerTask task = new TimerTask() {  
	        @Override  
	        public void run() {  
	 
	            runOnUiThread(new Runnable() {      // UI thread  
	                @Override  
	                public void run() {  
	                    recLen--;  
	                    String times = times(recLen);
	                    mTime.setText("剩余："+times);  
	                    if(recLen < 0){  
	                        timer.cancel();  
	                    }  
	                }  
	            });  
	        }  
	    };
		private Share_pop share_pop;
		
		public SendMessageToWX.Req createReq(boolean timeLine) {
			String ArticleUrl="http://ouyiku.com/jiubuy/view?pid="+productId;
			String title2 = title;
			
	    	WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = ArticleUrl;
			WXMediaMessage msg = new WXMediaMessage(webpage);
//			String title = title2;
			msg.description = title2;
//			msg.title = title;
			Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao3);
			msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
			
			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("webpage");
			req.message = msg;
//			req.scene = SendMessageToWX.Req.WXSceneSession;
			req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
			return req;
	    }
		
		 private String buildTransaction(final String type) {
				return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
			}
	
	
	@Override
	public void finish() {
		hideSoftInput();
		super.finish();
		overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
	}
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
