package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.brandjson.Trading;
import zsj.com.oyk255.example.ouyiku.brandjson.TradingDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class TradingFragment extends Fragment {
	
	
	/*
	 * 
	 * 接口问题*/

	private View view;
	private ListView mListView;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<TradingDatum> mData=new ArrayList<TradingDatum>();
	
	

	public TradingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_trading, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initList();
		}
		
		return view;
	}

	private void initList() {
		String url= Constant.URL.JiaoYiJiluUrl+"&user_id="+userid;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("initList", arg0);
				Gson gson = new Gson();
				Trading fromJson = gson.fromJson(arg0, Trading.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){}
					List<TradingDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
//					mListView.setAdapter(new MyTradAdapter());
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initUI() {
		mListView = (ListView) view.findViewById(R.id.trading_listview);
		
		
	}
	
	class MyTradAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
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

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder holder;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.tradingrecord_item, null);
				holder=new ViewHolder();
				holder.mContent = (TextView) layout.findViewById(R.id.trading_xiaofei);
				holder.mTime = (TextView) layout.findViewById(R.id.trading_time);
				holder.mMoney = (TextView) layout.findViewById(R.id.trading_money);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			
			
			
			return layout;
		}
		
	}
	class ViewHolder{
		TextView mContent;
		TextView mTime;
		TextView mMoney;
	}

}
