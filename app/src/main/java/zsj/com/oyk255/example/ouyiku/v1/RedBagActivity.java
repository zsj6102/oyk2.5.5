package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.IsGetHB;
import zsj.com.oyk255.example.ouyiku.brandjson.IsGetHBData;
import zsj.com.oyk255.example.ouyiku.brandjson.Product;
import zsj.com.oyk255.example.ouyiku.brandjson.RedBagList;
import zsj.com.oyk255.example.ouyiku.brandjson.RedBagListDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class RedBagActivity extends OykActivity implements OnClickListener {

	ArrayList<RedBagListDatum> mListData=new ArrayList<RedBagListDatum>();
	private ListView mListView;
	private SharedPreferences sp;
	String token;
	String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_red_bag);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initList();
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	private void GetHongBao(String RedBagID){
		String url= Constant.URL.GetHongBaoUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("id", RedBagID);
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				IsGetHB fromJson = gson.fromJson(arg0, IsGetHB.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					IsGetHBData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						Toast.makeText(RedBagActivity.this, "领取成功", Toast.LENGTH_LONG).show();
					}else if(status2.equals("0")){
						Toast.makeText(RedBagActivity.this, "已经领取过该红包了", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(RedBagActivity.this, "红包已经被抢光啦", Toast.LENGTH_LONG).show();
					}
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private RedBagAdapter redBagAdapter;
	private ImageView mImg_NULL;
	private void initList() {
		String url=Constant.URL.RedBagListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("num", "0");
		HTTPUtils.post(this, url, map, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				Gson gson = new Gson();
				RedBagList fromJson = gson.fromJson(arg0, RedBagList.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<RedBagListDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					if(mListData.size()==0){
						mImg_NULL.setVisibility(View.VISIBLE);
					}else{
						
						redBagAdapter = new RedBagAdapter();
						mListView.setAdapter(redBagAdapter);
						mImg_NULL.setVisibility(View.GONE);
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
		findViewById(R.id.redbag_back).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.redbag_listview);
		mImg_NULL = (ImageView) findViewById(R.id.redbag_null);
		
	}
	
	class RedBagAdapter extends BaseAdapter{

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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder holder;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.item_redbag, null);
				holder=new ViewHolder();
				holder.redbad_brandlogo = (ImageView) layout.findViewById(R.id.redbad_brandlogo);
				holder.redbag_img1 = (ImageView) layout.findViewById(R.id.redbag_img1);
				holder.redbag_img2 = (ImageView) layout.findViewById(R.id.redbag_img2);
				holder.redbad_brandtitle = (TextView) layout.findViewById(R.id.redbad_brandtitle);
				holder.redbad_secondtitle = (TextView) layout.findViewById(R.id.redbad_secondtitle);
				holder.redbag_money = (TextView) layout.findViewById(R.id.redbag_money);
				holder.wumenkan = (TextView) layout.findViewById(R.id.wumenkan);
				holder.redbag_get = (TextView) layout.findViewById(R.id.redbag_get);
				holder.redbag_newpirce1 = (TextView) layout.findViewById(R.id.redbag_newpirce1);
				holder.redbag_oldpirce1 = (TextView) layout.findViewById(R.id.redbag_oldpirce1);
				holder.redbag_oldpirce1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.redbag_newpirce2 = (TextView) layout.findViewById(R.id.redbag_newpirce2);
				holder.redbag_oldpirce2 = (TextView) layout.findViewById(R.id.redbag_oldpirce2);
				holder.redbag_oldpirce2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.redbag_thridview = (LinearLayout) layout.findViewById(R.id.redbag_thridview);
				holder.redbag_secondview = (LinearLayout) layout.findViewById(R.id.redbag_secondview);
			
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
				RedBagListDatum redBagListDatum = mListData.get(position);
				String btitle = redBagListDatum.getBtitle();
				String activeTitle = redBagListDatum.getActiveTitle();
				String blogo = redBagListDatum.getBlogo();
				String money = redBagListDatum.getMoney();
				String name = redBagListDatum.getName();
				final String brandId = redBagListDatum.getBrandId();
				final String id = redBagListDatum.getId();
				
				
				
				List<Product> product = redBagListDatum.getProduct();
				Log.e("product", "product:"+product);
				if(product.size()!=0){
					
					Product product2 = product.get(0);
					if(product2!=null){
						holder.redbag_secondview.setVisibility(View.VISIBLE);
						String phone1 = product2.getPhone1();
						String price = product2.getPrice();
						String marketprice = product2.getMarketprice();
						UILUtils.displayImageNoAnim(phone1, holder.redbag_img1);
						holder.redbag_newpirce1.setText("￥"+price);
						holder.redbag_oldpirce1.setText("￥"+marketprice);
						
						
					}
					
					Product product3 = product.get(1);
					if(product3!=null){
						holder.redbag_thridview.setVisibility(View.VISIBLE);
						String phone2 = product3.getPhone1();
						String price2 = product3.getPrice();
						String marketprice2 = product3.getMarketprice();
						UILUtils.displayImageNoAnim(phone2, holder.redbag_img2);
						holder.redbag_newpirce2.setText("￥"+price2);
						holder.redbag_oldpirce2.setText("￥"+marketprice2);
						
					}else{
						holder.redbag_thridview.setVisibility(View.INVISIBLE);
					}
				}else{
					holder.redbag_secondview.setVisibility(View.INVISIBLE);
					holder.redbag_thridview.setVisibility(View.INVISIBLE);
				}
				
				UILUtils.displayImageNoAnim(blogo, holder.redbad_brandlogo);
				holder.redbad_brandtitle.setText(btitle);
				holder.redbad_secondtitle.setText(activeTitle);
				holder.redbag_money.setText(money);
				holder.wumenkan.setText(name);
				
				holder.redbag_thridview.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(RedBagActivity.this, Brand_detailActivity.class);
						intent.putExtra("mSevenShop1", brandId);
						startActivity(intent);
						
					}
				});
				holder.redbag_secondview.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(RedBagActivity.this, Brand_detailActivity.class);
						intent.putExtra("mSevenShop1", brandId);
						startActivity(intent);
						
					}
				});
				holder.redbad_brandlogo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(RedBagActivity.this, Brand_detailActivity.class);
						intent.putExtra("mSevenShop1", brandId);
						startActivity(intent);
						
					}
				});
				
				holder.redbag_get.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!userid.equals("")){
							GetHongBao(id);
						}else{
							startActivity(new Intent(RedBagActivity.this, LoginActivity.class));
						}
						
					}
				});
				
			
			return layout;
		}
		
	}

	class ViewHolder{
		ImageView redbad_brandlogo;
		TextView redbad_brandtitle;
		TextView redbad_secondtitle;
		TextView redbag_money;
		TextView wumenkan;
		TextView redbag_get;
		ImageView redbag_img1;
		TextView redbag_newpirce1;
		TextView redbag_oldpirce1;
		ImageView redbag_img2;
		TextView redbag_newpirce2;
		TextView redbag_oldpirce2;
		LinearLayout redbag_secondview;
		LinearLayout redbag_thridview;
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.redbag_back:
			finish();
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
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mListData.size();
						
						String url=Constant.URL.RedBagListUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("num", ""+size);
						HTTPUtils.post(RedBagActivity.this, url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								RedBagList fromJson = gson.fromJson(arg0, RedBagList.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<RedBagListDatum> data = fromJson.getData();
									mListData.addAll(data);
									redBagAdapter.notifyDataSetChanged();
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
			}.sendEmptyMessageDelayed(0, 2000);
		}

	}


}
