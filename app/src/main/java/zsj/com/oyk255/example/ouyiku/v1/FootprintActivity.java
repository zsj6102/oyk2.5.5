package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.footjson.Datum;
import zsj.com.oyk255.example.ouyiku.footjson.FootPrint;
import zsj.com.oyk255.example.ouyiku.footjson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.ProductInfo;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class FootprintActivity extends OykActivity implements OnClickListener{

	private ExpandableListView mListView;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<Datum> mDatumData=new ArrayList<Datum>();
	private ImageView mNull;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_footprint);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.FootprintURL+"&user_id="+userid;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				FootPrint fromJson = gson.fromJson(arg0, FootPrint.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<Datum> data = fromJson.getData();
					mDatumData.clear();
					mDatumData.addAll(data);
					if(mDatumData.size()==0){
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						MyFootAdapter myFootAdapter = new MyFootAdapter();
						//默认展开
						mListView.setAdapter(new MyFootAdapter());
						for(int i = 0; i < myFootAdapter.getGroupCount(); i++){  
							
							mListView.expandGroup(i);  
							
						}  
					}
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	//清空足迹
	private void deletefoot(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.DeleteFootprintURL+"&user_id="+userid;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					initData();
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		findViewById(R.id.foot_back).setOnClickListener(this);
		mListView = (ExpandableListView) findViewById(R.id.foot_listview);
		mListView.setGroupIndicator(null);
		mNull = (ImageView) findViewById(R.id.footprint_kong);
		findViewById(R.id.foot_delete).setOnClickListener(this);
		
		//不可缩放
		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				return true;
			}
		});
		mListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Datum datum = mDatumData.get(groupPosition);
				List<zsj.com.oyk255.example.ouyiku.footjson.List> list = datum.getList();
				String productId = list.get(childPosition).getProductId();
				
				Intent intent = new Intent(FootprintActivity.this, DetailActivity.class);
				intent.putExtra("product_id", productId);
				startActivity(intent);
				
				return false;
			}
		});
	}
	
	
	class MyFootAdapter extends BaseExpandableListAdapter{

	@Override
	public int getGroupCount() {
		return mDatumData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Datum datum = mDatumData.get(groupPosition);
		List<zsj.com.oyk255.example.ouyiku.footjson.List> list = datum.getList();
        return list.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		Datum datum = mDatumData.get(groupPosition);
		String time = datum.getTime();
		return time;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Datum datum = mDatumData.get(groupPosition);
		List<zsj.com.oyk255.example.ouyiku.footjson.List> list = datum.getList();
		zsj.com.oyk255.example.ouyiku.footjson.List list2 = list.get(childPosition);
		
		return list2;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		 if (convertView == null) {
             LayoutInflater inflater = (LayoutInflater) FootprintActivity.this
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.foot_parent_item, null);
         }
         TextView tv = (TextView) convertView
                 .findViewById(R.id.foot_parent_time);
         Datum datum = mDatumData.get(groupPosition);
 		String time = datum.getTime();
         tv.setText(time);
         return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Datum datum = mDatumData.get(groupPosition);
		List<zsj.com.oyk255.example.ouyiku.footjson.List> list = datum.getList();
		zsj.com.oyk255.example.ouyiku.footjson.List list2 = list.get(childPosition);
         if (convertView == null) {
        	 
             LayoutInflater inflater = (LayoutInflater) FootprintActivity.this
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.coll_baby_item, null);
         }
         CheckBox mIschickBox = (CheckBox) convertView.findViewById(R.id.checkcollbaby);
         mIschickBox.setVisibility(View.GONE);
         
         ImageView mLogo = (ImageView) convertView.findViewById(R.id.babylogo);
         TextView mTitle = (TextView) convertView.findViewById(R.id.babytitle);
         TextView mNew_price = (TextView) convertView.findViewById(R.id.textView4);
         TextView mOld_price = (TextView) convertView.findViewById(R.id.textView5);
			mOld_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			TextView mRebate = (TextView) convertView.findViewById(R.id.textView3);
			
			String id = list2.getId();//足迹id
			String lasttime = list2.getLasttime();
			String marketprice = list2.getMarketprice();
			String phone1 = list2.getPhone1();
			String price = list2.getPrice();
			String productId = list2.getProductId();//商品id
			String rebate = list2.getRebate();
			String title = list2.getTitle();
			
			UILUtils.displayImageNoAnim(phone1, mLogo);
			mTitle.setText(title);
			mNew_price.setText("￥"+price);
			mOld_price.setText("￥"+marketprice);
			mRebate.setText(rebate+"折");
			
         return convertView;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
		
	}
	
	
	class ViewHoler{
		ImageView mLogo;
		TextView mTitle;
		TextView mNew_price;
		TextView mOld_price;
		TextView mRebate;
		CheckBox mIschickBox;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.foot_back:
			finish();
			break;
		case R.id.foot_delete:
			deletefoot();
			break;

		default:
			break;
		}
	}

}
