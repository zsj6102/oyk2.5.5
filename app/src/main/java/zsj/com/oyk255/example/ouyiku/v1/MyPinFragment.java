package zsj.com.oyk255.example.ouyiku.v1;

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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.Brand;
import zsj.com.oyk255.example.ouyiku.brandjson.MyPinTuan;
import zsj.com.oyk255.example.ouyiku.brandjson.MyPinTuanDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyPinFragment extends Fragment {

	private View view;
	private int pos;
	private ListView mListView;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<MyPinTuanDatum> mData= new ArrayList<MyPinTuanDatum>();
	
	public MyPinFragment() {
		// Required empty public constructor
	}
	public MyPinFragment(int position) {
		pos=position;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_my_pin, container, false);
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view1))
			.setOnRefreshListener(new MyListener());
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
		}
		return view;
	}
	private MyPinAdapter myPinAdapter;

	@Override
	public void onStart() {
		initData();
		super.onStart();
	}
	
	private void initData() {
		String url= Constant.URL.MyPinTuanUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("num", "0");
		map.put("user_id", userid);
		map.put("type", ""+pos);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				MyPinTuan fromJson = gson.fromJson(arg0, MyPinTuan.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<MyPinTuanDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					myPinAdapter = new MyPinAdapter();
					mListView.setAdapter(myPinAdapter);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private void initUI() {
		mListView = (ListView) view.findViewById(R.id.mypin_listview);
		
		
	}
	
	class MyPinAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
				layout=getActivity().getLayoutInflater().inflate(R.layout.item_mytuan_all, null);
				holder=new ViewHolder();
				holder.mypin_status=(TextView) layout.findViewById(R.id.mypin_status);
				holder.mypin_brandname=(TextView) layout.findViewById(R.id.mypin_brandname);
				holder.mypin_goodstitle=(TextView) layout.findViewById(R.id.mypin_goodstitle);
				holder.mypin_attr=(TextView) layout.findViewById(R.id.mypin_attr);
				holder.mypin_price=(TextView) layout.findViewById(R.id.mypin_price);
				holder.mypin_people=(TextView) layout.findViewById(R.id.mypin_people);
				holder.mypin_lookorder=(TextView) layout.findViewById(R.id.mypin_lookorder);
				holder.mypin_lookdetail=(TextView) layout.findViewById(R.id.mypin_lookdetail);
				holder.mypin_brandlogo=(ImageView) layout.findViewById(R.id.mypin_brandlogo);
				holder.mypin_goodspic=(ImageView) layout.findViewById(R.id.mypin_goodspic);
				holder.mypin_tobrand=(LinearLayout) layout.findViewById(R.id.mypin_tobrand);
				holder.todetail=(LinearLayout) layout.findViewById(R.id.todetail);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
			MyPinTuanDatum myPinTuanDatum = mData.get(position);
			Brand brand = myPinTuanDatum.getBrand();
			final String brandId = brand.getBrandId();
			String logo = brand.getLogo();
			String title = brand.getTitle();
			holder.mypin_brandname.setText(title);
			UILUtils.displayImageNoAnim(logo, holder.mypin_brandlogo);
			
			holder.mypin_tobrand.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
					intent.putExtra("mSevenShop1", brandId);
					startActivity(intent);
				}
			});
			
			
			
			
			String buyNumber = myPinTuanDatum.getBuyNumber();
			String groupsPrice = myPinTuanDatum.getGroupsPrice();
			final String photo = myPinTuanDatum.getPhoto();
			String title2 = myPinTuanDatum.getTitle();
			String sku = myPinTuanDatum.getSku();
			String strstatus = myPinTuanDatum.getStrstatus();
			final String tuan_id = myPinTuanDatum.getTuan_id();
			final String groupsId = myPinTuanDatum.getGroupsId();
			final String orderId = myPinTuanDatum.getOrderId();
			
			
			holder.mypin_status.setText(strstatus);
			holder.mypin_goodstitle.setText(title2);
			holder.mypin_attr.setText(sku);
			holder.mypin_price.setText(groupsPrice);
			holder.mypin_people.setText(buyNumber+"人团");
			UILUtils.displayImageNoAnim(photo, holder.mypin_goodspic);
			
			holder.mypin_lookdetail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), CanTuanDetailActivity.class);
					intent.putExtra("tuanId", tuan_id);
					intent.putExtra("brandId", brandId);
					intent.putExtra("isOrder", "yes");//是从我的拼团过来的
					startActivity(intent);
					
				}
			});
			
			
			holder.mypin_lookorder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MyPinOrderDetailActivity.class);
				intent.putExtra("orderId", orderId);
				startActivity(intent);
					
				}
			});
			
			
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		TextView mypin_status;
		ImageView mypin_brandlogo;
		TextView mypin_brandname;
		ImageView mypin_goodspic;
		TextView mypin_goodstitle;
		TextView mypin_attr;
		TextView mypin_price;
		TextView mypin_people;
		TextView mypin_lookorder;
		TextView mypin_lookdetail;
		LinearLayout mypin_tobrand;
		LinearLayout todetail;
		
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
					if(mData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mData.size();
						String url=Constant.URL.MyPinTuanUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("num", ""+size);
						map.put("user_id", userid);
						map.put("type", ""+pos);
						HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								MyPinTuan fromJson = gson.fromJson(arg0, MyPinTuan.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<MyPinTuanDatum> data = fromJson.getData();
									mData.addAll(data);
									myPinAdapter.notifyDataSetChanged();
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
