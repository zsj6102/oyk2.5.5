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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.footjson.PaySuccessAddress;
import zsj.com.oyk255.example.ouyiku.footjson.PaySuccessAddressData;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupTuiJian;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupTuiJianDatum;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PaySuccessActivity extends OykActivity implements OnClickListener{

	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String money;
	private String oids;
	ArrayList<GroupTuiJianDatum> mTuijianData=new ArrayList<GroupTuiJianDatum>();
	int screenWidth;
	private MyGridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_success);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		money = intent.getStringExtra("money");
		oids = intent.getStringExtra("oids");
		screenWidth = ScreenUtils.getScreenWidth(this);
		initUI();
		initAddress();
		initList();
	}

	private TuijianGridview tuijianGridview;
	private ScrollView mScroll;
	private TextView m_Name;
	private TextView m_Phone;
	private TextView m_Address;
	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.GroupTuijianURL;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				GroupTuiJian fromJson = gson.fromJson(arg0, GroupTuiJian.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<GroupTuiJianDatum> data = fromJson.getData();
					mTuijianData.clear();
					mTuijianData.addAll(data);
					tuijianGridview = new TuijianGridview();
					mGridView.setAdapter(tuijianGridview);
					mScroll.smoothScrollTo(0, 0);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initAddress() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.PaySuccrssAddressUrl;
		HashMap<String , String> map=new HashMap<String, String>();
		map.put("money", money);
		map.put("user_id", userid);
		map.put("oids", oids);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				PaySuccessAddress fromJson = gson.fromJson(arg0, PaySuccessAddress.class);
				zsj.com.oyk255.example.ouyiku.footjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					PaySuccessAddressData data = fromJson.getData();
					String province = data.getProvince();
					String city = data.getCity();
					String country = data.getCountry();
					String address = data.getAddress();
					String mobile = data.getMobile();
					String name = data.getName();
					m_Name.setText(name);
					m_Phone.setText(mobile);
					m_Address.setText(province+city+country+address);
					
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		m_Name = (TextView) findViewById(R.id.success_name);
		m_Phone = (TextView) findViewById(R.id.success_phone);
		m_Address = (TextView) findViewById(R.id.success_address);
		mScroll = (ScrollView) findViewById(R.id.success_scroll);
		mScroll.smoothScrollTo(0, 0);
		mGridView = (MyGridView) findViewById(R.id.success_gridview);
		findViewById(R.id.paysuccess_back).setOnClickListener(this);
		TextView mTotal = (TextView) findViewById(R.id.paysuccess_total);
		mTotal.setText(money);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GroupTuiJianDatum groupTuiJianDatum = mTuijianData.get(position);
				String product_id = groupTuiJianDatum.getProductId();
				Intent intent = new Intent(PaySuccessActivity.this, DetailActivity.class);
				intent.putExtra("product_id", product_id);
				
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.paysuccess_order).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.paysuccess_back:
			Intent intent2 = new Intent(this, MainActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
			break;
		case R.id.paysuccess_order:
			Intent intent = new Intent(PaySuccessActivity.this, PaySuccessOrderActivity.class);
			intent.putExtra("oids", oids);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}
	
	class TuijianGridview extends BaseAdapter{

		@Override
		public int getCount() {
			return mTuijianData.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if(convertView == null){
				view=getLayoutInflater().inflate(R.layout.gridview_item, null);
                holder = new ViewHolder();
				holder.OldPrice = (TextView) view.findViewById(R.id.gridview_oldprice);
				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.Imginfo = (ImageView) view.findViewById(R.id.gridview_imginfo);
				 holder.Imginfo.getLayoutParams().height=(screenWidth-10)/2;
				holder.GoodsInfo = (TextView) view.findViewById(R.id.gridview_tv_title);
				holder.NewPrice = (TextView) view.findViewById(R.id.gridview_price);
				holder.Discount = (TextView) view.findViewById(R.id.discount);
				view.setTag(holder);
			}else{
				view  = convertView;
				holder = (ViewHolder) view.getTag();
			}
			GroupTuiJianDatum groupTuiJianDatum = mTuijianData.get(position);
			String newPrice = groupTuiJianDatum.getNewPrice();
			String oldPrice = groupTuiJianDatum.getOldPrice();
			String picUrl = groupTuiJianDatum.getPicUrl();
			String productId = groupTuiJianDatum.getProductId();
			String rebate = groupTuiJianDatum.getRebate();
			String title = groupTuiJianDatum.getTitle();
			
			holder.OldPrice.setText("￥"+oldPrice);
			holder.NewPrice.setText("￥"+newPrice);
			holder.GoodsInfo.setText(title);
			holder.Discount.setText(rebate+"折");
			UILUtils.displayImageNoAnim(picUrl, holder.Imginfo);
			
			
			return view;
		}
		
	}
	class ViewHolder
	{
		ImageView Imginfo;
		TextView OldPrice;
		TextView GoodsInfo;
		TextView NewPrice;
		TextView Discount;
		
	}
	

}
