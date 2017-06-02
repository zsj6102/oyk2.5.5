package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.CancleAfter;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleAfterDetail;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleAfterDetailData;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleAfterList;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleAfterListDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Text_Pop;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SaleAfter_ingActivity extends OykActivity implements OnClickListener{

	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<SaleAfterListDatum> mData=new ArrayList<SaleAfterListDatum>();
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_after_ing);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		status = intent.getStringExtra("status");
		title = intent.getStringExtra("title");
		initUI();
		if(status.equals("saleing")){
			initData("1");
		}else if(status.equals("success")){
			initData("2");
		}else if(status.equals("false")){
			initData("3");
		}
	}

	private SaleAfteringAdapter saleAfteringAdapter;
	private String status;
	private String title;
	private TextView mTITLE;
	private Text_Pop text_Pop;
	
	private void initData(String status) {
		String url= Constant.URL.SaleAfterListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("num", "0");
		map.put("status", status);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				SaleAfterList fromJson = gson.fromJson(arg0, SaleAfterList.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SaleAfterListDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					saleAfteringAdapter = new SaleAfteringAdapter();
					
					mListView.setAdapter(saleAfteringAdapter);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void Refresh(String size,String status,final PullToRefreshLayout pullToRefreshLayout){
		String url=Constant.URL.SaleAfterListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("num", size);
		map.put("status", status);
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				SaleAfterList fromJson = gson.fromJson(arg0, SaleAfterList.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SaleAfterListDatum> data = fromJson.getData();
					mData.addAll(data);
					saleAfteringAdapter.notifyDataSetChanged();
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
	
	

	private void initUI() {
		findViewById(R.id.back_saleing).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.saleing_listview);
		mTITLE = (TextView) findViewById(R.id.saleing_title);
		mTITLE.setText(title);
		text_Pop = new Text_Pop(this);
	}
	
	class SaleAfteringAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return mData.size();
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
				layout=getLayoutInflater().inflate(R.layout.item_saleafter_ing, null);
				holder=new ViewHolder();
				holder.mLogo=(ImageView) layout.findViewById(R.id.saleing_logo);
				holder.mTitle=(TextView) layout.findViewById(R.id.saleing_title);
				holder.mAttr=(TextView) layout.findViewById(R.id.saleing_attr);
				holder.mQuXiao=(TextView) layout.findViewById(R.id.saleing_quxiao);
				holder.mSaleing_state=(TextView) layout.findViewById(R.id.saleing_state);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			SaleAfterListDatum saleAfterListDatum = mData.get(position);
			String productTitle = saleAfterListDatum.getProductTitle();
			String productImg = saleAfterListDatum.getProductImg();
			String productSku = saleAfterListDatum.getProductSku();
			final String applyId = saleAfterListDatum.getApplyId();
			UILUtils.displayImageNoAnim(productImg, holder.mLogo);
			holder.mTitle.setText(productTitle);
			holder.mAttr.setText(productSku);
			if(status.equals("saleing")){
				holder.mSaleing_state.setVisibility(View.GONE);
				holder.mQuXiao.setText("取消申请");
				holder.mQuXiao.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CancleApply(applyId);
						
					}
				});
				
			}else if(status.equals("success")){
				holder.mSaleing_state.setText("商家同意售后申请");
				holder.mSaleing_state.setTextColor(Color.parseColor("#666666"));
				holder.mQuXiao.setText("查看详情");
				
				holder.mQuXiao.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(SaleAfter_ingActivity.this, SaleAfterSuccessActivity.class);
						intent.putExtra("applyId", applyId);
						startActivity(intent);
					}
				});
				
				
			}else{
				holder.mSaleing_state.setText("商家拒绝售后申请");
				holder.mSaleing_state.setTextColor(Color.parseColor("#EC407A"));
				holder.mQuXiao.setText("查看详情");
				holder.mQuXiao.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String url=Constant.URL.SaleAfterDetailUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("user_id", userid);
						map.put("token", token);
						map.put("id", applyId);
						HTTPUtils.post(SaleAfter_ingActivity.this, url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								SaleAfterDetail fromJson = gson.fromJson(arg0, SaleAfterDetail.class);
								Status status2 = fromJson.getStatus();
								if(status2.getSucceed().equals("1")){
									SaleAfterDetailData data = fromJson.getData();
									String reason = data.getReason();
									text_Pop.showAtLocation(findViewById(R.id.LinearLayout1), Gravity.CENTER, 0, 0);
									text_Pop.view.findViewById(R.id.pop_after_back).setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											text_Pop.dismiss();
											
										}
									});
									
									TextView mContent = (TextView) text_Pop.view.findViewById(R.id.pop_saleafter_content);
									mContent.setText("售后失败原因:"+reason);
								}
								
								
								
								
								
								
							}
							
							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				});
			}
			
			
			
			return layout;
		}
		
	}
	private void CancleApply(String applyid){
		String url=Constant.URL.NOApplySaleAfterUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("id", applyid);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				CancleAfter fromJson = gson.fromJson(arg0, CancleAfter.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					initData("1");
				}else{
					ToastUtils.toast(SaleAfter_ingActivity.this, "取消失败");
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class ViewHolder{
		ImageView mLogo;
		TextView mTitle;
		TextView mAttr;
		TextView mQuXiao;
		TextView mSaleing_state;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_saleing:
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
					// 千万别忘了告诉控件加载完毕了哦！
					
					if(mData.size()==0){
						if(status.equals("saleing")){
							initData("1");
						}else if(status.equals("success")){
							initData("2");
						}else if(status.equals("false")){
							initData("3");
						}
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mData.size();
						
						if(status.equals("saleing")){
							Refresh(""+size, "1",pullToRefreshLayout);
						}else if(status.equals("success")){
							Refresh(""+size, "2",pullToRefreshLayout);
						}else if(status.equals("false")){
							Refresh(""+size, "3",pullToRefreshLayout);
						}
						
					}
				}
			}.sendEmptyMessageDelayed(0, 2000);
		}

	}


}
