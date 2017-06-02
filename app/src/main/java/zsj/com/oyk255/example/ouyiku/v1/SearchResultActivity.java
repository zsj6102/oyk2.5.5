package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.SearchResult;
import zsj.com.oyk255.example.ouyiku.homejson.SearchResultDatum;
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

public class SearchResultActivity extends OykActivity implements OnClickListener{

	
	
	boolean isTouch;
	boolean isTouch2;
	private MyGridView mGridview;
	private TextView mSearchresult_comprehensive;
	private TextView mSearchresult_sales;
	private View mSearchresult_price;
	private View mSearchresult_discount;
	private String key;
	ArrayList<SearchResultDatum> mData=new ArrayList<SearchResultDatum>();
	private TextView msearchresult_pricetv;
	private TextView msearchresult_discounttv;
	private ImageView msearchresult_priceimg;
	private ImageView msearchresult_discountim;
	private EditText mEt_search;
	String type="1";
	private SearchAdapter searchAdapter;
	private PullableScrollView mScroll;
	private String pcategory_id;
	private int screenWidth;
	private ImageView mNull;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout)findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		screenWidth = ScreenUtils.getScreenWidth(this);
		Intent intent = getIntent();
		key = intent.getStringExtra("key");
		pcategory_id = intent.getStringExtra("pcategory_id");
	        initUI();
	        if(pcategory_id==null){
	        	initList(key,"1");
	        	
	        }else{
	        	initGroupResult(pcategory_id);
	        }
	        
	}
	
	
	



	private void initGroupResult(String pcategory_id2) {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.GroupLastURL+"&pcategory_id="+pcategory_id2+"&type="+type;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SearchResult fromJson = gson.fromJson(arg0, SearchResult.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SearchResultDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					if(mData.size()==0){
						mNull.setVisibility(View.VISIBLE);
						searchAdapter = new SearchAdapter();
						mGridview.setAdapter(searchAdapter);
						mScroll.smoothScrollTo(0, 0);
					}else{
						mNull.setVisibility(View.GONE);
						searchAdapter = new SearchAdapter();
						mGridview.setAdapter(searchAdapter);
						mScroll.smoothScrollTo(0, 0);
					}
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}






	private void initList(String key,String type) {
		
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.SearchURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.clear();
		map.put("key", key);
		map.put("num", "0");
		map.put("type", type);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SearchResult fromJson = gson.fromJson(arg0, SearchResult.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SearchResultDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					if(mData.size()==0){
						searchAdapter = new SearchAdapter();
						mGridview.setAdapter(searchAdapter);
						mScroll.smoothScrollTo(0, 0);
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						searchAdapter = new SearchAdapter();
						mGridview.setAdapter(searchAdapter);
						mScroll.smoothScrollTo(0, 0);
					}
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
		
	}

	
	private void initUI() {
		
		mNull = (ImageView) findViewById(R.id.searchresult_null);
		mScroll = (PullableScrollView) findViewById(R.id.search_scroll);
		mScroll.smoothScrollTo(0, 0);
		mGridview = (MyGridView) findViewById(R.id.rearch_gridview);
		findViewById(R.id.result_tomain).setOnClickListener(this);
		mEt_search = (EditText) findViewById(R.id.et_search);
		findViewById(R.id.tv_search).setOnClickListener(this);
		
		mSearchresult_comprehensive = (TextView) findViewById(R.id.searchresult_comprehensive);
		mSearchresult_sales = (TextView) findViewById(R.id.searchresult_sales);
		mSearchresult_price = findViewById(R.id.searchresult_price);
		mSearchresult_discount =findViewById(R.id.searchresult_discount);
		
		msearchresult_pricetv = (TextView) findViewById(R.id.searchresult_pricetv);
		msearchresult_discounttv = (TextView) findViewById(R.id.searchresult_discounttv);
		msearchresult_priceimg = (ImageView) findViewById(R.id.searchresult_priceimg);
		msearchresult_discountim = (ImageView) findViewById(R.id.searchresult_discountim);
		
		
		
		
		mSearchresult_comprehensive.setOnClickListener(this);
		mSearchresult_sales.setOnClickListener(this);
		mSearchresult_price.setOnClickListener(this);
		mSearchresult_discount.setOnClickListener(this);
		
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchResultActivity.this, DetailActivity.class);
				String productId = mData.get(position).getProductId();
				intent.putExtra("product_id", productId);
				startActivity(intent);
			}
		});
	}
	
	class SearchAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mData.size();
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
			 SearchResultDatum searchResultDatum = mData.get(position);
			
			holder.OldPrice.setText("¥"+searchResultDatum.getOldPrice());
			holder.NewPrice.setText("¥"+searchResultDatum.getNewPrice());
			holder.GoodsInfo.setText(searchResultDatum.getTitle());
			holder.Discount.setText(searchResultDatum.getRebate()+"折");
			UILUtils.displayImageNoAnim(searchResultDatum.getPicUrl(), holder.Imginfo);
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchresult_comprehensive:
			mSearchresult_comprehensive.setTextColor(Color.parseColor("#EC407A"));
			mSearchresult_sales.setTextColor(Color.parseColor("#666666"));
			msearchresult_pricetv.setTextColor(Color.parseColor("#666666"));
			msearchresult_discounttv.setTextColor(Color.parseColor("#666666"));
			msearchresult_priceimg.setImageResource(R.mipmap.arrow_default);
			msearchresult_discountim.setImageResource(R.mipmap.arrow_default);
			type="1";
			if(pcategory_id==null){
	        	initList(key,"1");
	        	
	        }else{
	        	initGroupResult(pcategory_id);
			}
			break;
		case R.id.searchresult_sales:
			mSearchresult_comprehensive.setTextColor(Color.parseColor("#666666"));
			mSearchresult_sales.setTextColor(Color.parseColor("#EC407A"));
			msearchresult_pricetv.setTextColor(Color.parseColor("#666666"));
			msearchresult_discounttv.setTextColor(Color.parseColor("#666666"));
			msearchresult_priceimg.setImageResource(R.mipmap.arrow_default);
			msearchresult_discountim.setImageResource(R.mipmap.arrow_default);
			type="2";
			if(pcategory_id==null){
	        	initList(key,"2");
	        	
	        }else{
	        	initGroupResult(pcategory_id);
	        }
			break;
		case R.id.searchresult_price:
			mSearchresult_comprehensive.setTextColor(Color.parseColor("#666666"));
			mSearchresult_sales.setTextColor(Color.parseColor("#666666"));
			msearchresult_pricetv.setTextColor(Color.parseColor("#EC407A"));
			msearchresult_discounttv.setTextColor(Color.parseColor("#666666"));
			msearchresult_discountim.setImageResource(R.mipmap.arrow_default);
			
			isTouch=!isTouch;
			if(isTouch){
				msearchresult_priceimg.setImageResource(R.mipmap.arrow);
				type="3";
				if(pcategory_id==null){
		        	initList(key,"3");
		        	
		        }else{
		        	initGroupResult(pcategory_id);
		        }
			}else{
				msearchresult_priceimg.setImageResource(R.mipmap.arrow_opposite);
				type="4";
				if(pcategory_id==null){
		        	initList(key,"4");
		        	
		        }else{
		        	initGroupResult(pcategory_id);
		        }
				
			}
			
			break;
		case R.id.searchresult_discount:
			mSearchresult_comprehensive.setTextColor(Color.parseColor("#666666"));
			mSearchresult_sales.setTextColor(Color.parseColor("#666666"));
			msearchresult_pricetv.setTextColor(Color.parseColor("#666666"));
			msearchresult_discounttv.setTextColor(Color.parseColor("#EC407A"));
			msearchresult_priceimg.setImageResource(R.mipmap.arrow_default);
			isTouch2=!isTouch2;
			if(isTouch2){
				msearchresult_discountim.setImageResource(R.mipmap.arrow);
				type="5";
				if(pcategory_id==null){
		        	initList(key,"5");
		        	
		        }else{
		        	initGroupResult(pcategory_id);
		        }
			}else{
				msearchresult_discountim.setImageResource(R.mipmap.arrow_opposite);
				type="6";
				if(pcategory_id==null){
		        	initList(key,"6");
		        	
		        }else{
		        	initGroupResult(pcategory_id);
		        }
				
			}
			break;
		case R.id.result_tomain:
			finish();
			break;
		case R.id.tv_search:
			pcategory_id=null;
			key = mEt_search.getText().toString().trim();
			hideSoftInput();
			initList(key, "1");
			mSearchresult_comprehensive.setTextColor(Color.parseColor("#EC407A"));
			mSearchresult_sales.setTextColor(Color.parseColor("#666666"));
			msearchresult_pricetv.setTextColor(Color.parseColor("#666666"));
			msearchresult_discounttv.setTextColor(Color.parseColor("#666666"));
			msearchresult_priceimg.setImageResource(R.mipmap.arrow_default);
			msearchresult_discountim.setImageResource(R.mipmap.arrow_default);
			type="1";
			break;

		default:
			break;
		}
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
			}.sendEmptyMessageDelayed(0, 3000);
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
					if(pcategory_id==null){
						if(mData.size()==0){
							 initList(key,"1");
							 pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
						}else{
							int size = mData.size();
							String url=Constant.URL.SearchURL;
							HashMap<String, String> map=new HashMap<String, String>();
							map.put("key", key);
							map.put("num", size+"");
							map.put("type", type);
							HTTPUtils.post(SearchResultActivity.this, url, map, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Gson gson = new Gson();
									SearchResult fromJson = gson.fromJson(arg0, SearchResult.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<SearchResultDatum> data = fromJson.getData();
										mData.addAll(data);
										searchAdapter.notifyDataSetChanged();
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
							
							
							
							
					}else{
						
						//分类传过来
						
						if(mData.size()==0){
							 initGroupResult(pcategory_id);
							 pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
							 
						}else{
							int size = mData.size();
							
							String url=Constant.URL.GroupLastURL+"&pcategory_id="+pcategory_id+"&type="+type+"&num="+size;
							HTTPUtils.get(SearchResultActivity.this, url, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Gson gson = new Gson();
									SearchResult fromJson = gson.fromJson(arg0, SearchResult.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<SearchResultDatum> data = fromJson.getData();
										mData.addAll(data);
										searchAdapter.notifyDataSetChanged();
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
					
					
				}
			}.sendEmptyMessageDelayed(0, 3000);
		}

	}
	//关闭键盘
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
