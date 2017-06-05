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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.Status;
import zsj.com.oyk255.example.ouyiku.detailjson.ZeroList;
import zsj.com.oyk255.example.ouyiku.detailjson.ZeroListDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class TryuseActivity extends OykActivity implements OnClickListener{

	//网红爆款
	ArrayList<ZeroListDatum> mListData=new ArrayList<ZeroListDatum>();
	private ListView mListView;
	private ZeroAdapter zeroAdapter;
	private ImageView mNull;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tryuse);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout) findViewById(R.id.refresh_view1))
		.setOnRefreshListener(new MyListener());
		initList();
		initUI();

	}

	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.NetRedURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("type", "1");
		map.put("num", "0");
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				ZeroList fromJson = gson.fromJson(arg0, ZeroList.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<ZeroListDatum> data = fromJson.getData();
					mListData.addAll(data);

					if(mListData.size()==0){
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						zeroAdapter = new ZeroAdapter();
						mListView.setAdapter(zeroAdapter);

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
		findViewById(R.id.zero_back).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.zero_listView);
		mNull = (ImageView) findViewById(R.id.zeroimg_null);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String stock = mListData.get(position).getStock();
				if(!stock.equals("0")){
					
					Intent intent = new Intent(TryuseActivity.this, DetailActivity.class);
					String productId = mListData.get(position).getProductId();
					String phone1 = mListData.get(position).getPhone1();
					intent.putExtra("phone1", phone1);
					intent.putExtra("product_id", productId);
					startActivity(intent);
				}else{
					Toast.makeText(TryuseActivity.this, "本期已结束", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zero_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	class ZeroAdapter extends BaseAdapter{

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
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder;
			if(convertView==null){
				view=getLayoutInflater().inflate(R.layout.zero_list_item, null);
				holder=new ViewHolder();
				holder.mLogo = (ImageView) view.findViewById(R.id.zeroitem_logo);
//				holder.mBackground = (ImageView) view.findViewById(R.id.zeroitem_qianggouimg);
				holder.mTitle = (TextView) view.findViewById(R.id.zeroitemtitle);
				holder.mNewPrice = (TextView) view.findViewById(R.id.zeroitem_newprice);
				holder.mOldPrice = (TextView) view.findViewById(R.id.zeroitem_oldprice);
				holder.mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
//				holder.mNum = (TextView) view.findViewById(R.id.zeroitem_num);
				view.setTag(holder);
			}else{
				view=convertView;
				holder = (ViewHolder) view.getTag();
			}
			ZeroListDatum zeroListDatum = mListData.get(position);
			String currprice = zeroListDatum.getCurrprice();
			String marketprice = zeroListDatum.getMarketprice();
			String stock = zeroListDatum.getStock();
			String phone1 = zeroListDatum.getPhone1();
			String title = zeroListDatum.getTitle();
			
			if(stock.equals("0")){
//				holder.mBackground.setImageResource(R.mipmap.lose);
//				holder.mNum.setVisibility(View.GONE);
			}else{
//				holder.mNum.setText("剩余"+stock);
				
			}
			holder.mTitle.setText(title);
			holder.mNewPrice.setText("￥"+currprice);
			holder.mOldPrice.setText("￥"+marketprice);
			UILUtils.displayImageNoAnim(phone1, holder.mLogo);
			return view;
		}
		
	}
	
	class ViewHolder{
		ImageView mLogo;
//		ImageView mBackground;
		TextView mTitle;
		TextView mNewPrice;
		TextView mOldPrice;
//		TextView mNum;
		
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
					
					if(mListData.size()==0){
						initList();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mListData.size();
						String url=Constant.URL.NineListURL;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("type", "2");
						map.put("num", ""+size);
						HTTPUtils.post(TryuseActivity.this, url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Log.e("initList", arg0);
								Gson gson = new Gson();
								ZeroList fromJson = gson.fromJson(arg0, ZeroList.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<ZeroListDatum> data = fromJson.getData();
									mListData.addAll(data);
									zeroAdapter.notifyDataSetChanged();
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
			}.sendEmptyMessageDelayed(0, 1000);
		}

	}
	

}
