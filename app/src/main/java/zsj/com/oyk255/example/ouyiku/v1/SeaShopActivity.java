package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.Product;
import zsj.com.oyk255.example.ouyiku.homejson.SeaShop;
import zsj.com.oyk255.example.ouyiku.homejson.SeaShopBanner;
import zsj.com.oyk255.example.ouyiku.homejson.SeaShopBannerData;
import zsj.com.oyk255.example.ouyiku.homejson.SeaShopDatum;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SeaShopActivity extends OykActivity {

	private ScrollViewWithListView mListView;
	ArrayList<SeaShopDatum> mListData=new ArrayList<SeaShopDatum>();
	HashMap<Integer, List<Product>> mHashMap=new HashMap<Integer, List<Product>>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sea_shop);
		PushAgent.getInstance(this).onAppStart();
		initUI();
		initData();
		initBanner();
		
	}

	private void initBanner() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.SeaShopBannerURL;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SeaShopBanner fromJson = gson.fromJson(arg0, SeaShopBanner.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					SeaShopBannerData data = fromJson.getData();
					String url2 = data.getUrl();
					UILUtils.displayImageNoAnim(url2, mTopView);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.SeaShopURL;
		HTTPUtils.post(this, url, null, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new  Gson();
				SeaShop fromJson = gson.fromJson(arg0, SeaShop.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SeaShopDatum> data = fromJson.getData();
					mListData.addAll(data);
					mListView.setAdapter(new ListViewAdapter());
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		mListView = (ScrollViewWithListView) findViewById(R.id.seashop_listview);
		ScrollView mScroll = (ScrollView) findViewById(R.id.seashop_scroll);
		mScroll.smoothScrollTo(0, 0);
		mTopView = (ImageView) findViewById(R.id.seashop_tpimg);
		findViewById(R.id.back_seashop).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	private MyGridView gridview;
	private ImageView mTopView;
	
	class ListViewAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
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
				view=getLayoutInflater().inflate(R.layout.seashop_listview_item, null);
				holder=new ViewHolder();
				holder.mTextImg = (ImageView) view.findViewById(R.id.seashop_textimg);
				gridview = (MyGridView) view.findViewById(R.id.seashopitem_gridview);
				view.setTag(holder);
			}else{
				view=convertView;
				holder = (ViewHolder) view.getTag();
			}
			SeaShopDatum seaShopDatum = mListData.get(position);
			String imgurl = seaShopDatum.getImgurl();
			UILUtils.displayImageNoAnim(imgurl, holder.mTextImg);
			
			List<Product> product = seaShopDatum.getProduct();
			mHashMap.put(position, product);
			gridview.setAdapter(new GridViewAdapter(position));
			
			return view;
		}
		
	}
	
	class ViewHolder{
		ImageView mTextImg;
		
	}
	
	class GridViewAdapter extends BaseAdapter{
		int position;
		public GridViewAdapter(int position){
			this.position=position;
		}
		@Override
		public int getCount() {
			return mHashMap.get(position).size();
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
		public View getView(int pos, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder2 holder2=null;
			if(convertView==null){
				view=getLayoutInflater().inflate(R.layout.seashop_gridview_item, null);
				holder2=new ViewHolder2();
				holder2.mLogo = (AspectRatioImageView) view.findViewById(R.id.gridview_imginfo);
				holder2.mTitle = (TextView) view.findViewById(R.id.gridview_tv_title);
				holder2.mNewPrice = (TextView) view.findViewById(R.id.gridview_price);
				holder2.mOldPrice = (TextView) view.findViewById(R.id.gridview_oldprice);
				holder2.mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder2.mDiscount = (TextView) view.findViewById(R.id.discount);
				view.setTag(holder2);
			}else{
				view=convertView;
				holder2 = (ViewHolder2) view.getTag();
			}
			Product product = mHashMap.get(position).get(pos);
			String marketprice = product.getMarketprice();
			String phone1 = product.getPhone1();
			String price = product.getPrice();
			String rebate = product.getRebate();
			String title = product.getTitle();
			String productId = product.getProductId();
			
			UILUtils.displayImageNoAnim(phone1, holder2.mLogo);
			holder2.mTitle.setText(title);
			holder2.mNewPrice.setText("￥"+price);
			holder2.mOldPrice.setText("￥"+marketprice);
			holder2.mDiscount.setText(rebate+"折");
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position2, long id) {
					String productId2 = mHashMap.get(position).get(position2).getProductId();
					Intent intent = new Intent(SeaShopActivity.this, DetailActivity.class);
					intent.putExtra("product_id", productId2);
					startActivity(intent);
					
					
				}
			});
			
			return view;
		}
		
	}
	class ViewHolder2{
		AspectRatioImageView mLogo;
		TextView mTitle;
		TextView mNewPrice;
		TextView mOldPrice;
		TextView mDiscount;
		
	}
	
}
