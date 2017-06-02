package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.HotSaleBanner;
import zsj.com.oyk255.example.ouyiku.detailjson.HotSaleBannerDatum;
import zsj.com.oyk255.example.ouyiku.homejson.HotSale;
import zsj.com.oyk255.example.ouyiku.homejson.HotSaleDatum;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.pullableview.PullableScrollView;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class HotSaleActivity extends OykActivity {

	private MyGridView mGridView;
	ArrayList<HotSaleDatum> mListData=new ArrayList<HotSaleDatum>();
	private HotAdapter hotAdapter;
	private ImageView mTopView;
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_sale);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout)findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		screenWidth = ScreenUtils.getScreenWidth(this);
		initUI();
		initTop();
		initList();
	}

	private void initTop() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.HorSaleBannerURL;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotSaleBanner fromJson = gson.fromJson(arg0, HotSaleBanner.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HotSaleBannerDatum> data = fromJson.getData();
					String picUrl = data.get(0).getPicUrl();
					UILUtils.displayImageNoAnim(picUrl, mTopView);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.HotSaleURL;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotSale fromJson = gson.fromJson(arg0, HotSale.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HotSaleDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					hotAdapter = new HotAdapter();
					
					mGridView.setAdapter(hotAdapter);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		PullableScrollView mScroll = (PullableScrollView) findViewById(R.id.hotsale_scroll);
		mScroll.smoothScrollTo(0, 0);
		
		mGridView = (MyGridView) findViewById(R.id.hotsale_gridview);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(HotSaleActivity.this,DetailActivity.class);
				String productId = mListData.get(position).getProductId();
				intent.putExtra("product_id", productId);
				
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.hotsale_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mTopView = (ImageView) findViewById(R.id.hotsale_topimg);
	}
	class HotAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size();
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
			ViewHolder holder;
			if(convertView==null){
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
				HotSaleDatum hotSaleDatum = mListData.get(position);
				String newPrice = hotSaleDatum.getNewPrice();
				String oldPrice = hotSaleDatum.getOldPrice();
				String picUrl = hotSaleDatum.getPicUrl();
				String productId = hotSaleDatum.getProductId();
				String rebate = hotSaleDatum.getRebate();
				String title = hotSaleDatum.getTitle();
				
				holder.OldPrice.setText("¥"+oldPrice);
				holder.NewPrice.setText("¥"+newPrice);
				holder.GoodsInfo.setText(title);
				holder.Discount.setText(rebate+"折");
				UILUtils.displayImageNoAnim(picUrl, holder.Imginfo);
			
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
			}.sendEmptyMessageDelayed(0, 2000);
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
					if(mListData.size()==0){
						initList();
					}else{
						int size = mListData.size();
						String productId = mListData.get(size-1).getProductId();
						
						String url=Constant.URL.HotSaleURL+"&num="+size;
						HTTPUtils.get(HotSaleActivity.this, url, new VolleyListener() {
							

							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								HotSale fromJson = gson.fromJson(arg0, HotSale.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<HotSaleDatum> data = fromJson.getData();
									mListData.addAll(data);
									hotAdapter.notifyDataSetChanged();
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								}else{
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
									
								}
							}
							
							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					
				}
			}.sendEmptyMessageDelayed(0, 1000);
		}

	}

}
