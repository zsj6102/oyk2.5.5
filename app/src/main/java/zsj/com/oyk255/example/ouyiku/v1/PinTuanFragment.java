package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.Notable;
import zsj.com.oyk255.example.ouyiku.brandjson.NotableDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanBanner;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanBannerData;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanList;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanListDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanSearch;
import zsj.com.oyk255.example.ouyiku.brandjson.SearchData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.AutoTextView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PinTuanFragment extends Fragment {

	private View view;
	private int position;
	private ListView mListView;
	private ImageView mImg;
	ArrayList<PinTuanListDatum> mListData=new ArrayList<PinTuanListDatum>();
	private PinTuanAdapter pinTuanAdapter;
//	private List<String> mList;
	private int mTextCount;
	private Handler mHandler = new Handler();
	ArrayList<NotableDatum> mData=new ArrayList<NotableDatum>();
	public PinTuanFragment() {
		// Required empty public constructor
	}
	public PinTuanFragment(int position) {
		this.position=position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_pin_tuan, container, false);
			//上拉加载下拉刷新
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			initUI();
			initBanner();
			initNotable();
			initList();
		}
		return view;
	}

	private void initNotable() {
		String url= Constant.URL.NotableUrl;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				Notable fromJson = gson.fromJson(arg0, Notable.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<NotableDatum> data = fromJson.getData();
					mData.addAll(data);
					
					 mTextView.setText("恭喜 "+mData.get(0).getUname()+",拼团成功!");
				 		mTextCount = mData.size();
				 		mHandler.postDelayed(runnable, 5000);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
				
		
	}
	private void initList() {
		String url=Constant.URL.PinTuanListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("num", "0");
		map.put("type", ""+position);
		
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("pintuanlist", arg0);
				Gson gson = new Gson();
				PinTuanList fromJson = gson.fromJson(arg0, PinTuanList.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<PinTuanListDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					
					pinTuanAdapter = new PinTuanAdapter();
					mListView.setAdapter(pinTuanAdapter);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private void initBanner() {
		String	url=Constant.URL.PinTuanBannerUrl;
		
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanBanner fromJson = gson.fromJson(arg0, PinTuanBanner.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					PinTuanBannerData data = fromJson.getData();
					String picUrl = data.getPicUrl();
					UILUtils.displayImageNoAnim(picUrl, mImg);
					
				}
					
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private void initUI() {
		
		
		mListView = (ListView) view.findViewById(R.id.pintuan_listview);
		View topview = getActivity().getLayoutInflater().inflate(R.layout.item_top_headview, null);
		mImg = (ImageView) topview.findViewById(R.id.top_img);
		//喇叭动画
		ImageView animationIV = (ImageView) topview.findViewById(R.id.pintuan_guangbo_ani);
		 animationIV.setImageResource(R.drawable.pintuan_guangbo);  
		 AnimationDrawable  animationDrawable = (AnimationDrawable) animationIV.getDrawable();  
         animationDrawable.start();  
         //走马灯
         mTextView = (AutoTextView) topview.findViewById(R.id.autoTextView1);
        
 		
 		mEt_search = (EditText) topview.findViewById(R.id.pin_et_search);
 		topview.findViewById(R.id.pin_go).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String oids = mEt_search.getText().toString().trim();
				SearchTuan(oids);
				
			}
		});
 		
		mListView.addHeaderView(topview);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position>0){
					PinTuanListDatum pinTuanListDatum = mListData.get(position-1);
					String groupsId = pinTuanListDatum.getGroupsId();
					String photo = pinTuanListDatum.getPhoto();
					Intent intent = new Intent(getActivity(), PinDetailActivity.class);
					intent.putExtra("groupsId", groupsId);
					intent.putExtra("photo", photo);
					startActivity(intent);
				}
				
			}
		});
		
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			mTextView.next();
			mTextCount++;
			if(mTextCount >= Integer.MAX_VALUE){
				mTextCount = mData.size();
			}
			mTextView.setText("恭喜 "+mData.get(mTextCount % (mData.size())).getUname()+",拼团成功！");
			if (mData.size() > 1) {
				mHandler.postDelayed(this, 3000);
			}
			
		}
	};
	private AutoTextView mTextView;
	private EditText mEt_search;
	
	//搜索团
	private void SearchTuan(String oids){
		String url=Constant.URL.PinSearchUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("tid",oids );
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanSearch fromJson = gson.fromJson(arg0, PinTuanSearch.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					SearchData data = fromJson.getData();
					String brandId = data.getBrandId();
					String tid = data.getTid();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						Intent intent = new Intent(getActivity(), CanTuanDetailActivity.class);
						intent.putExtra("tuanId", tid);
						intent.putExtra("brandId", brandId);
						intent.putExtra("isOrder", "no");//是否是从我的拼团过来的
						startActivity(intent);
						mEt_search.setText("");
					}else{
						ToastUtils.toast(getActivity(), "该团不存在!");
						
					}
				}else{
					ToastUtils.toast(getActivity(), "请求失败!");
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class PinTuanAdapter extends BaseAdapter{

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
				layout =getActivity().getLayoutInflater().inflate(R.layout.item_pintuan, null);
				holder=new ViewHolder();
				holder.pintuan_item_img = (ImageView) layout.findViewById(R.id.pintuan_item_img);
				holder.pintuan_item_people = (TextView) layout.findViewById(R.id.pintuan_item_people);
				holder.pintuan_item_title = (TextView) layout.findViewById(R.id.pintuan_item_title);
				holder.pintuan_item_price = (TextView) layout.findViewById(R.id.pintuan_item_price);
				holder.pintuan_item_oldprice = (TextView) layout.findViewById(R.id.pintuan_item_oldprice);
				holder.pintuan_item_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.pintuan_item_btn = (TextView) layout.findViewById(R.id.pintuan_item_btn);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
				PinTuanListDatum pinTuanListDatum = mListData.get(position);
				String buy_number = pinTuanListDatum.getBuy_number();
				String groupsId = pinTuanListDatum.getGroupsId();
				String groupsPrice = pinTuanListDatum.getGroupsPrice();
				String photo = pinTuanListDatum.getPhoto();
				String singlePrice = pinTuanListDatum.getSinglePrice();
				String title = pinTuanListDatum.getTitle();
				
				UILUtils.displayImageNoAnim(photo, holder.pintuan_item_img);
				holder.pintuan_item_people.setText("("+buy_number+"人团)");
				holder.pintuan_item_title.setText(title);
				holder.pintuan_item_price.setText("￥"+groupsPrice);
				holder.pintuan_item_oldprice.setText("单卖价： ￥"+singlePrice);
				
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		ImageView pintuan_item_img;
		TextView pintuan_item_people;
		TextView pintuan_item_title;
		TextView pintuan_item_price;
		TextView pintuan_item_oldprice;
		TextView pintuan_item_btn;
		
		
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
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mListData.size();
						String url=Constant.URL.PinTuanListUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("num", ""+size);
						map.put("type", ""+position);
						
						HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								PinTuanList fromJson = gson.fromJson(arg0, PinTuanList.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<PinTuanListDatum> data = fromJson.getData();
									mListData.addAll(data);
									pinTuanAdapter.notifyDataSetChanged();
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
