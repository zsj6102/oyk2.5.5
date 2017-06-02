package zsj.com.oyk255.example.ouyiku.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.NewsBrandTime;
import zsj.com.oyk255.example.ouyiku.brandjson.NewsBrandTimeData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijian;
import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijianData;
import zsj.com.oyk255.example.ouyiku.homejson.NewsBanner;
import zsj.com.oyk255.example.ouyiku.homejson.NewsBannerDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.pullableview.PullableScrollView;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class NewsBrandDetailActivity extends OykActivity implements OnClickListener {

	private MyGridView mGridView;
	private String title;
	private String brandId;
	private String tab;
	private String brandmerchantId;
	private PullableScrollView mScroll;
	ArrayList<HomeTuijianData> mAllData=new ArrayList<HomeTuijianData>();
	private MyGridview myGridview;
	private TextView mTime_Tv;
	private int screenWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_brand_detail);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		screenWidth = ScreenUtils.getScreenWidth(this);
		Intent intent = getIntent();
		title = intent.getStringExtra("newstitle");
		brandId = intent.getStringExtra("brandId");
		tab = intent.getStringExtra("tab");
		brandmerchantId = intent.getStringExtra("brandmerchantId");
		activiteId = intent.getStringExtra("activiteId");
		initUI();
		initTime();
		initBanner();
		if(tab.equals("0")){
			initList();
			mIsBigin.setText("距离本场结束还剩");
			
		}else{
			initTommorrow();
			mIsBigin.setText("距离本场开始还剩");
		}
	}
	private void initBanner() {
		String url="http://a.ouyiku.com/?c=Tomorrow&a=brandimage"+"&cat_id="+activiteId;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				NewsBanner fromJson = gson.fromJson(arg0, NewsBanner.class);
				zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<NewsBannerDatum> data = fromJson.getData();
					String picUrl = data.get(0).getPicUrl();
					UILUtils.displayImageNoAnim(picUrl, mTopImg);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	@Override
	protected void onDestroy() {
		
		mAllData.clear();
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		mAllData.clear();
		super.onStop();
	}
	private void initTommorrow() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.BrandTommorrowURL+"&brandmerchant_id="+brandmerchantId;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("明日", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
				zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HomeTuijianData> data = fromJson.getData();
					mAllData.addAll(data);
					myGridview = new MyGridview();
					mGridView.setAdapter(myGridview);
					myGridview.notifyDataSetChanged();
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

	private void initTime() {
		String url=Constant.URL.DrandNewsTimeURL;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				NewsBrandTime fromJson = gson.fromJson(arg0, NewsBrandTime.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					NewsBrandTimeData data = fromJson.getData();
					String ltime = data.getLtime();
					recLen = Integer.valueOf(ltime);
					 timer.schedule(task, 1000, 1000);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	
	private void initList() {
		String url=Constant.URL.DrandNewsURL+"&brand_id="+brandId;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Log.e("今日", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
				zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HomeTuijianData> data = fromJson.getData();
					mAllData.addAll(data);
					myGridview = new MyGridview();
					mGridView.setAdapter(myGridview);
					myGridview.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		findViewById(R.id.news_detail_back).setOnClickListener(this);
		TextView mTitle = (TextView) findViewById(R.id.news_detail_title);
		mTitle.setText(title);
		mGridView = (MyGridView) findViewById(R.id.news_detail_gridview);
		mTopImg = (ImageView) findViewById(R.id.news_detail_banner);
		
		mScroll = (PullableScrollView) findViewById(R.id.news_detail_scroll);
		mScroll.smoothScrollTo(0, 0);
		
		mTime_Tv = (TextView) findViewById(R.id.news_detail_time);
		
		mIsBigin = (TextView) findViewById(R.id.news_detail_begin);
		
		if(tab.equals("0")){
			
			mGridView.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(NewsBrandDetailActivity.this, DetailActivity.class);
					String product_id = mAllData.get(position).getProduct_id();
					intent.putExtra("product_id", product_id);
					startActivity(intent);
				}
			});
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_detail_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	class MyGridview extends BaseAdapter{


		@Override
		public int getCount() {
			return mAllData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if(convertView == null){
				view=getLayoutInflater().inflate(R.layout.gridview_item, null);
				holder = new ViewHolder();
				holder.OldPrice = (TextView) view.findViewById(R.id.gridview_oldprice);
				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.Imginfo = (ImageView) view.findViewById(R.id.gridview_imginfo);
				 holder.Imginfo.getLayoutParams().height=(screenWidth-10)/2;
				holder.GoodsInfo = (TextView) view.findViewById(R.id.gridview_tv_title);
				holder.NewPrice = (TextView) view.findViewById(R.id.gridview_price);
				holder.Discount = (TextView) view.findViewById(R.id.discount);
				view.setTag(holder);
			}else{
				view  = convertView;
				//通过tag把layout对应的viewholder找到
				holder = (ViewHolder) view.getTag();
			}
			 HomeTuijianData homeTuijianData = mAllData.get(position);
//			
			holder.OldPrice.setText("¥"+homeTuijianData.getOld_price());
			holder.NewPrice.setText("¥"+homeTuijianData.getNew_price());
			holder.GoodsInfo.setText(homeTuijianData.getTitle());
			holder.Discount.setText(homeTuijianData.getRebate()+"折");
			UILUtils.displayImageNoAnim(homeTuijianData.getPic_url(), holder.Imginfo);
			return view;
		}
		
	}
	
	class ViewHolder
	{
		ImageView Imginfo;
		TextView OldPrice;
		TextView GoodsInfo;
		TextView NewPrice;
		TextView Discount;
		
	}
	
	public class MyListener implements PullToRefreshLayout.OnRefreshListener
	{

		
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 下拉刷新操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					// 千万别忘了告诉控件刷新完毕了哦！
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 5000);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 加载操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					
					if(tab.equals("0")){
						
						if(mAllData.size()==0){
							initList();
							pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
						}else{
							int size = mAllData.size();
							String product_id = mAllData.get(size-1).getProduct_id();
							String url=Constant.URL.DrandNewsURL+"&brand_id="+brandId+"&product_id="+product_id;
							
							HTTPUtils.get(NewsBrandDetailActivity.this, url, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Log.e("今日加载操作", arg0);
									Gson gson = new Gson();
									HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
									zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<HomeTuijianData> data = fromJson.getData();
										mAllData.addAll(data);
										myGridview.notifyDataSetChanged();
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
									}else{
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
										
									}
								}
								
								@Override
								public void onErrorResponse(VolleyError arg0) {
									
								}
							});
						}
						
						
					}else{
						
						if(mAllData.size()==0){
							initTommorrow();
							pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
						}else{
							int size = mAllData.size();
							
							String url=Constant.URL.BrandTommorrowURL+"&brandmerchant_id="+brandmerchantId+"&num="+size;
							
							HTTPUtils.get(NewsBrandDetailActivity.this, url, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Gson gson = new Gson();
									Log.e("明日加载操作", arg0);
									HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
									zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<HomeTuijianData> data = fromJson.getData();
										mAllData.addAll(data);
										myGridview.notifyDataSetChanged();
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
									}else{
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
									}
								}
								
								@Override
								public void onErrorResponse(VolleyError arg0) {
									
								}
							});
						}
						
					}
					
					
				}
			}.sendEmptyMessageDelayed(0, 3000);
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
                    mTime_Tv.setText(times);  
                    if(recLen < 0){  
                        timer.cancel();  
                    }  
                }  
            });  
        }  
    };
	private TextView mIsBigin;
	private ImageView mTopImg;
	private String activiteId;  

}
