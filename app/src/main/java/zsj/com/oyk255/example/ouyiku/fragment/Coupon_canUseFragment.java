package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;
 import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.brandjson.UserHB;
import zsj.com.oyk255.example.ouyiku.brandjson.UserHBDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
import zsj.com.oyk255.example.ouyiku.v1.RedBagActivity;
import zsj.com.oyk255.example.ouyiku.v1.WebviewActivity;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Coupon_canUseFragment extends Fragment implements OnClickListener{

	private View view;
	private ListView mListView;
	private SharedPreferences sp;
	String token;
	String userid;
	ArrayList<UserHBDatum> mListData=new ArrayList<UserHBDatum>();
	private TextView coupon_total;
	
	public Coupon_canUseFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_coupon_can_use, container,
					false);
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
		}
		return view;
	}

	private CouponAdapter couponAdapter;
	private ImageView mNullImg;
	private void initData() {
		String url= Constant.URL.UserRedBagListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("num", "0");
		map.put("type", "0");
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				UserHB fromJson = gson.fromJson(arg0, UserHB.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<UserHBDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					
					if(mListData.size()==0){
						mNullImg.setVisibility(View.VISIBLE);
						mNullImg.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), RedBagActivity.class);
								startActivity(intent);
								
							}
						});
					}else{
						
						int size = mListData.size();
						couponAdapter = new CouponAdapter();
						mListView.setAdapter(couponAdapter);
						coupon_total.setText("共有"+size+"个现金红包");
						mNullImg.setVisibility(View.GONE);
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
		View topview = getActivity().getLayoutInflater().inflate(R.layout.top_coupon_canuse, null);
		mListView = (ListView) view.findViewById(R.id.coupon_listview_canuse);
		mListView.addHeaderView(topview);
		TextView mRule = (TextView) topview.findViewById(R.id.coupon_rule);
		mRule.setOnClickListener(this);
		coupon_total = (TextView) topview.findViewById(R.id.coupon_total);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position>0){
					UserHBDatum userHBDatum = mListData.get(position-1);
					String bmId = userHBDatum.getBmId();
					Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
					intent.putExtra("mSevenShop1", bmId);
					startActivity(intent);
					
					
				}
				
			}
		});
		
		mNullImg = (ImageView) view.findViewById(R.id.hb_nullimg);
		
	}
	
	
	class CouponAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
				layout=getActivity().getLayoutInflater().inflate(R.layout.item_coupon_canuse, null);
				holder=new ViewHolder();
				holder.item_coupon_money = (TextView) layout.findViewById(R.id.item_coupon_money);
				holder.coupon_title = (TextView) layout.findViewById(R.id.coupon_title);
				holder.coupon_time = (TextView) layout.findViewById(R.id.coupon_time);
				holder.coupon_manjian = (TextView) layout.findViewById(R.id.coupon_manjian);
				holder.touse = (TextView) layout.findViewById(R.id.touse);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
				
			UserHBDatum userHBDatum = mListData.get(position);
			String money = userHBDatum.getMoney();
			String name = userHBDatum.getName();
			String condition = userHBDatum.getCondition();
			String userTime = userHBDatum.getUserTime();
			holder.item_coupon_money.setText(money);
			holder.coupon_title.setText(name);
			holder.coupon_time.setText(userTime);
			holder.coupon_manjian.setText(condition);
			
			
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		TextView item_coupon_money;
		TextView coupon_title;
		TextView coupon_time;
		TextView coupon_manjian;
		TextView touse;
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coupon_rule:
			Intent intent = new Intent(getActivity(), WebviewActivity.class);
			intent.putExtra("title", "使用规则");
			intent.putExtra("url", "http://a.ouyiku.com/?c=Coupon&a=couponexplain");
			startActivity(intent);
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
					if(mListData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mListData.size();
						String url=Constant.URL.UserRedBagListUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("user_id", userid);
						map.put("num", ""+size);
						map.put("type", "0");
						HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								UserHB fromJson = gson.fromJson(arg0, UserHB.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<UserHBDatum> data = fromJson.getData();
									mListData.addAll(data);
									couponAdapter.notifyDataSetChanged();
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
