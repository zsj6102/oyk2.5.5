package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.Re;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSale;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleData;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleList;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleListDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.MyScrollView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class TimeSaleActivity extends OykActivity implements OnClickListener,MyScrollView.OnScrollListener {

	private ScrollViewWithListView mListView;
	private ImageView mTopImg;
	private int searchLayoutTop;
	private LinearLayout timesale_timetitle;
	private LinearLayout search01;
	private LinearLayout search02;
	private MyScrollView mScroll;
	ArrayList<Re> mTimeData=new ArrayList<Re>();
	private LinearLayout view1;
	private LinearLayout view2;
	private LinearLayout view3;
	private LinearLayout view4;
	private LinearLayout view5;
	private TextView one_time;
	private TextView two_time;
	private TextView three_time;
	private TextView four_time;
	private TextView five_time;
	private TextView one_sale;
	private TextView two_sale;
	private TextView three_sale;
	private TextView four_sale;
	private TextView five_sale;
	
	private String btime;
	private String btime2;
	private String btime3;
	private String btime4;
	private String btime5;
	
	ArrayList<TimeSaleListDatum> mListData=new ArrayList<TimeSaleListDatum>();
	InputMethodManager im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_sale);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		initUI();
		initTime();
		
	}

//	private TimeSaleAdapter timeSaleAdapter;
	
	private void initList(String btime,final String iscur) {
		String url= Constant.URL.TimeListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("num", "0");
		map.put("btime", btime);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Log.e("initList", arg0);
				Log.e("iscur", iscur);
				Gson gson = new Gson();
				TimeSaleList fromJson = gson.fromJson(arg0, TimeSaleList.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<TimeSaleListDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					
					if(mListData.size()==0){
						mImgNull.setVisibility(View.VISIBLE);
						mListView.setAdapter(new TimeSaleAdapter(iscur));
						mScroll.smoothScrollTo(0, 0);
					}else{
						mImgNull.setVisibility(View.GONE);
//						timeSaleAdapter = new TimeSaleAdapter(iscur);
						mListView.setAdapter(new TimeSaleAdapter(iscur));
						
						mScroll.smoothScrollTo(0, 0);
						
					}
				
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private String iscur;
	private String iscur2;
	private String iscur3;
	private String iscur4;
	private String iscur5;
	private ImageView mImgNull;
	
	
	private void initTime() {
		String url=Constant.URL.TimeUrl;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("initTime", arg0);
				Gson gson = new Gson();
				TimeSale fromJson = gson.fromJson(arg0, TimeSale.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					TimeSaleData data = fromJson.getData();
					String etime = data.getEtime();
					String img = data.getImg();
					List<Re> res = data.getRes();
					mTimeData.addAll(res);
					UILUtils.displayImageNoAnim(img, mTopImg);
					Re re = mTimeData.get(0);
					btime = re.getBtime();
					iscur = re.getIscur();
					String name = re.getName();
					one_time.setText(name);
					if(iscur.equals("0")){
						one_sale.setText("已开抢");
						view1.setBackgroundColor(Color.parseColor("#999999"));
					}else if(iscur.equals("1")){
						one_sale.setText("进行中");
						view1.setBackgroundColor(Color.parseColor("#ff647c"));
						initList(btime,iscur);
					}else if(iscur.equals("2")){
						one_sale.setText("即将开始");
						view1.setBackgroundColor(Color.parseColor("#999999"));
					}
					
					Re re2 = mTimeData.get(1);
					btime2 = re2.getBtime();
					iscur2 = re2.getIscur();
					String name2 = re2.getName();
					two_time.setText(name2);
					if(iscur2.equals("0")){
						two_sale.setText("已开抢");
						view2.setBackgroundColor(Color.parseColor("#999999"));
					}else if(iscur2.equals("1")){
						two_sale.setText("进行中");
						view2.setBackgroundColor(Color.parseColor("#ff647c"));
						initList(btime2,iscur2);
					}else if(iscur2.equals("2")){
						two_sale.setText("即将开始");
						view2.setBackgroundColor(Color.parseColor("#999999"));
					}
					
					
					
					
					Re re3 = mTimeData.get(2);
					btime3 = re3.getBtime();
					iscur3 = re3.getIscur();
					String name3 = re3.getName();
					three_time.setText(name3);
					if(iscur3.equals("0")){
						three_sale.setText("已开抢");
						view3.setBackgroundColor(Color.parseColor("#999999"));
					}else if(iscur3.equals("1")){
						three_sale.setText("进行中");
						view3.setBackgroundColor(Color.parseColor("#ff647c"));
						initList(btime3,iscur3);
					}else if(iscur3.equals("2")){
						three_sale.setText("即将开始");
						view3.setBackgroundColor(Color.parseColor("#999999"));
					}
					
					
					
					Re re4 = mTimeData.get(3);
					btime4 = re4.getBtime();
					iscur4 = re4.getIscur();
					String name4 = re4.getName();
					four_time.setText(name4);
					if(iscur4.equals("0")){
						four_sale.setText("已开抢");
						view4.setBackgroundColor(Color.parseColor("#999999"));
					}else if(iscur4.equals("1")){
						four_sale.setText("进行中");
						view4.setBackgroundColor(Color.parseColor("#ff647c"));
						initList(btime4,iscur4);
					}else if(iscur4.equals("2")){
						four_sale.setText("即将开始");
						view4.setBackgroundColor(Color.parseColor("#999999"));
					}
					
					
					
					
					Re re5 = mTimeData.get(4);
					btime5 = re5.getBtime();
					iscur5 = re5.getIscur();
					String name5 = re5.getName();
					five_time.setText(name5);
					if(iscur5.equals("0")){
						five_sale.setText("已开抢");
						view5.setBackgroundColor(Color.parseColor("#999999"));
					}else if(iscur5.equals("1")){
						five_sale.setText("进行中");
						view5.setBackgroundColor(Color.parseColor("#ff647c"));
						initList(btime5,iscur5);
					}else if(iscur5.equals("2")){
						five_sale.setText("即将开始");
						view5.setBackgroundColor(Color.parseColor("#999999"));
					}
					
					Log.e("iscur1", iscur);
					Log.e("iscur2", iscur2);
					Log.e("iscur3", iscur3);
					Log.e("iscur4", iscur4);
					Log.e("iscur5", iscur5);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initUI() {
		mScroll = (MyScrollView) findViewById(R.id.timesale_scroll);
		mListView = (ScrollViewWithListView) findViewById(R.id.timesale_listview);
		mTopImg = (ImageView) findViewById(R.id.timesale_topimg);
		timesale_timetitle = (LinearLayout) findViewById(R.id.timesale_timetitle);
		search01 = (LinearLayout) findViewById(R.id.search01);
		search02 = (LinearLayout) findViewById(R.id.search02);
		
		
		findViewById(R.id.timesale_back).setOnClickListener(this);
		
		mScroll.smoothScrollTo(0, 0);
		mScroll.setOnScrollListener(this);
		
		view1 = (LinearLayout) findViewById(R.id.view1);
		view2 = (LinearLayout) findViewById(R.id.view2);
		view3 = (LinearLayout) findViewById(R.id.view3);
		view4 = (LinearLayout) findViewById(R.id.view4);
		view5 = (LinearLayout) findViewById(R.id.view5);
		view1.setOnClickListener(this);
		view2.setOnClickListener(this);
		view3.setOnClickListener(this);
		view4.setOnClickListener(this);
		view5.setOnClickListener(this);
		
		one_time = (TextView) findViewById(R.id.one_time);
		two_time = (TextView) findViewById(R.id.two_time);
		three_time = (TextView) findViewById(R.id.three_time);
		four_time = (TextView) findViewById(R.id.four_time);
		five_time = (TextView) findViewById(R.id.five_time);
		
		one_sale = (TextView) findViewById(R.id.one_sale);
		two_sale = (TextView) findViewById(R.id.two_sale);
		three_sale = (TextView) findViewById(R.id.three_sale);
		four_sale = (TextView) findViewById(R.id.four_sale);
		five_sale = (TextView) findViewById(R.id.five_sale);
		mImgNull = (ImageView) findViewById(R.id.timeimg_null);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(TimeSaleActivity.this, NewsPeopleDetailActivity.class);
				String productId = mListData.get(position).getProductId();
				String phone1 = mListData.get(position).getPhone1();
				
				intent.putExtra("phone1", phone1);
				intent.putExtra("productId", productId);
				
				startActivity(intent);
				
			}
		});
		
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);    
        if(hasFocus){  
        	searchLayoutTop = mTopImg.getBottom();
        	Log.e("searchLayoutTop",""+ searchLayoutTop);
        }
	}

	class TimeSaleAdapter extends BaseAdapter{
	
		String iscanbuy;
		public TimeSaleAdapter(String iscur){
			iscanbuy=iscur;
		}
		

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
				layout=getLayoutInflater().inflate(R.layout.item_timesale, null);
				holder=new ViewHolder();
				holder.zeroitem_logo = (ImageView) layout.findViewById(R.id.zeroitem_logo);
				holder.zeroitem_qianggouimg = (ImageView) layout.findViewById(R.id.zeroitem_qianggouimg);
				holder.zeroitemtitle = (TextView) layout.findViewById(R.id.zeroitemtitle);
				holder.zeroitem_newprice = (TextView) layout.findViewById(R.id.zeroitem_newprice);
				holder.zeroitem_oldprice = (TextView) layout.findViewById(R.id.zeroitem_oldprice);
				holder.zeroitem_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.zeroitem_zhekou = (TextView) layout.findViewById(R.id.zeroitem_zhekou);
				holder.zeroitem_num = (TextView) layout.findViewById(R.id.zeroitem_num);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
			
			TimeSaleListDatum timeSaleListDatum = mListData.get(position);
			String title = timeSaleListDatum.getTitle();
			String currprice = timeSaleListDatum.getCurrprice();
			String marketprice = timeSaleListDatum.getMarketprice();
			String phone1 = timeSaleListDatum.getPhone1();
			String rebate = timeSaleListDatum.getRebate();
			String stock = timeSaleListDatum.getStock();
			
			UILUtils.displayImageNoAnim(phone1, holder.zeroitem_logo);
			
			holder.zeroitemtitle.setText(title);
			holder.zeroitem_newprice.setText("¥"+currprice);
			holder.zeroitem_oldprice.setText("¥"+marketprice);
			holder.zeroitem_zhekou.setText(rebate+"折");
			
			if(iscanbuy.equals("2")){
				if(!stock.equals("0")){
					holder.zeroitem_qianggouimg.setImageResource(R.mipmap.time_remind);
					holder.zeroitem_num.setText("剩余"+stock);
					
				}
			
			}else{
				
				if(stock.equals("0")){
					holder.zeroitem_qianggouimg.setImageResource(R.mipmap.lose);
					holder.zeroitem_num.setVisibility(View.GONE);
				}else{
					holder.zeroitem_num.setText("剩余"+stock);
					
				}
				
			}
			
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		ImageView zeroitem_logo;
		TextView zeroitemtitle;
		TextView zeroitem_newprice;
		TextView zeroitem_oldprice;
		TextView zeroitem_zhekou;
		TextView zeroitem_num;
		ImageView zeroitem_qianggouimg;
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.timesale_back:
			finish();
			break;
		case R.id.view1:
			view1.setBackgroundColor(Color.parseColor("#ff647c"));
			view2.setBackgroundColor(Color.parseColor("#999999"));
			view3.setBackgroundColor(Color.parseColor("#999999"));
			view4.setBackgroundColor(Color.parseColor("#999999"));
			view5.setBackgroundColor(Color.parseColor("#999999"));
			initList(btime,iscur);
			mScroll.smoothScrollTo(0, 0);
			mScroll.setOnScrollListener(this);
			
			break;
		case R.id.view2:
			view1.setBackgroundColor(Color.parseColor("#999999"));
			view2.setBackgroundColor(Color.parseColor("#ff647c"));
			view3.setBackgroundColor(Color.parseColor("#999999"));
			view4.setBackgroundColor(Color.parseColor("#999999"));
			view5.setBackgroundColor(Color.parseColor("#999999"));
			initList(btime2,iscur2);
			mScroll.smoothScrollTo(0, 0);
			mScroll.setOnScrollListener(this);
			break;
		case R.id.view3:
			view1.setBackgroundColor(Color.parseColor("#999999"));
			view2.setBackgroundColor(Color.parseColor("#999999"));
			view3.setBackgroundColor(Color.parseColor("#ff647c"));
			view4.setBackgroundColor(Color.parseColor("#999999"));
			view5.setBackgroundColor(Color.parseColor("#999999"));
			initList(btime3,iscur3);
			mScroll.smoothScrollTo(0, 0);
			mScroll.setOnScrollListener(this);
			break;
		case R.id.view4:
			view1.setBackgroundColor(Color.parseColor("#999999"));
			view2.setBackgroundColor(Color.parseColor("#999999"));
			view3.setBackgroundColor(Color.parseColor("#999999"));
			view4.setBackgroundColor(Color.parseColor("#ff647c"));
			view5.setBackgroundColor(Color.parseColor("#999999"));
			initList(btime4,iscur4);
			mScroll.smoothScrollTo(0, 0);
			mScroll.setOnScrollListener(this);
			break;
		case R.id.view5:
			view1.setBackgroundColor(Color.parseColor("#999999"));
			view2.setBackgroundColor(Color.parseColor("#999999"));
			view3.setBackgroundColor(Color.parseColor("#999999"));
			view4.setBackgroundColor(Color.parseColor("#999999"));
			view5.setBackgroundColor(Color.parseColor("#ff647c"));
			initList(btime5,iscur5);
			mScroll.smoothScrollTo(0, 0);
			mScroll.setOnScrollListener(this);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onScroll(int scrollY) {
		if(scrollY >= searchLayoutTop){  
			if (timesale_timetitle.getParent()!=search01) {
				search02.removeView(timesale_timetitle);
				search01.addView(timesale_timetitle);
			}
        }else{
        	if (timesale_timetitle.getParent()!=search02) {
        		search01.removeView(timesale_timetitle);
        		search02.addView(timesale_timetitle);
			}
		
        }
	}
	
	@Override
	public void finish() {
		hideSoftInput();
		super.finish();
		overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
	}
	//关闭输入法
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
