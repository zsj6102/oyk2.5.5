package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanComfirm;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanComfirmData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddress;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddressData;
import zsj.com.oyk255.example.ouyiku.invitejson.SubmitOrder;
import zsj.com.oyk255.example.ouyiku.invitejson.SubmitOrderData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class ConfirmOrderPinTuanActivity extends OykActivity implements OnClickListener {
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String groups_id;
	private String pnum;
	private String brand_id;
	private String tuanPrice;//团购价
	private TextView confirmorder_name;
	private TextView mBtn_pay;
	private ImageView confirmorder_brandimg;
	private ImageView goods_logo;
	private TextView confirmorder_phone;
	private TextView confirmorder_address;
	private TextView goods_title;
	private TextView goods_attr;
	private TextView goods_newprice;
	private TextView goods_oldprice;
	private TextView goods_num;
	private TextView liuyan;
	private TextView mTotal;
	private TextView xiaoji;
	private TextView hongbaoyouhui;
	private TextView order_total;
	private TextView hongbao_total;
	private TextView shangpin_total;
	private TextView mBrandTitle;
	String addressId;
	private String brandId;
	private String isSign;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order_pin_tuan);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		isSign = intent.getStringExtra("isSign");//是否单独购买
		groups_id = intent.getStringExtra("groups_id");
		pnum = intent.getStringExtra("pnum");
		brand_id = intent.getStringExtra("brand_id");
		
		initUI();
		initAddress();
		if(isSign.equals("yes")){
			initData(pnum);
		}else{
			tuanId = intent.getStringExtra("tuanId");
			Log.e("tuanId", ""+tuanId);
			tuanPrice = intent.getStringExtra("tuanPrice");
			KaiTuanData();
			initTuanData();
			
		}
	}

	
		private void KaiTuanData(){
			final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
			progressHUD.setMessage("加载中");
	    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
	    	progressHUD.show();
			String url= Constant.URL.PinTuanSingleBuyUrl;
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("groups_id", groups_id);
			map.put("user_id", userid);
			map.put("token", token);
			map.put("pnum", "1");
			map.put("brand_id", brand_id);
			HTTPUtils.post(this, url, map, new VolleyListener() {
				
				@Override
				public void onResponse(String arg0) {
					Log.e("KaiTuanData", arg0);
					Gson gson = new Gson();
					PinTuanComfirm fromJson = gson.fromJson(arg0, PinTuanComfirm.class);
					Status status = fromJson.getStatus();
					if(status.getSucceed().equals("1")){
						progressHUD.dismiss();
						PinTuanComfirmData data = fromJson.getData();
						String brandLogo = data.getBrandLogo();
						String brandTitle = data.getBrandTitle();
						String photo = data.getPhoto();
						pnum2 = "1";
						String singlePrice = data.getSinglePrice();
						String title = data.getTitle();
						total = tuanPrice;
						String xiaoji2 = data.getXiaoji();
						String youhui = data.getYouhui();
						String sku = data.getSku();
						brandId = data.getBrandId();
						
						UILUtils.displayImageNoAnim(brandLogo, confirmorder_brandimg);
						mBrandTitle.setText(brandTitle);
						UILUtils.displayImageNoAnim(photo, goods_logo);
						goods_title.setText(title);
						goods_attr.setText(sku);
						goods_newprice.setText("¥"+tuanPrice);
						goods_num.setText("x"+1);
						hongbaoyouhui.setText("¥0");
						xiaoji.setText(tuanPrice);
						order_total.setText(tuanPrice);
						shangpin_total.setText(tuanPrice);
						hongbao_total.setText("¥0");
						mTotal.setText(tuanPrice);
					}
					
				}
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	
		private String status2;
		private String oids;
//拼团提交订单
	private void initTuanData() {
		String url=Constant.URL.PinTuanSubMitTuanUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groups_id);
		map.put("user_id", userid);
		map.put("token", token);
		map.put("address_id", addressId);
		if(!tuanId.equals("kong")){
			map.put("tuan_id", tuanId);
			
		}
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Log.e("initTuanData", arg0);
				Gson gson = new Gson();
				SubmitOrder fromJson = gson.fromJson(arg0, SubmitOrder.class);
				zsj.com.oyk255.example.ouyiku.invitejson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					SubmitOrderData data = fromJson.getData();
					status2 = data.getStatus();
					oids = data.getOids();
					
					
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

//拼团提交订单
	private void initAddress() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.MoRenAddressUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				
				
				progressHUD.dismiss();
				Gson gson = new Gson();
				MoRenAddress fromJson = gson.fromJson(arg0, MoRenAddress.class);
				zsj.com.oyk255.example.ouyiku.invitejson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){}
				MoRenAddressData data = fromJson.getData();
				if(data!=null){
				String name = data.getName();
				String mobile = data.getMobile();
				String country = data.getCountry();
				String province = data.getProvince();
				String city = data.getCity();
				String address = data.getAddress();
				addressId = data.getAddressId();
				confirmorder_name.setText(name);
				confirmorder_phone.setText(mobile);
				confirmorder_address.setText(province+city+country+address);
					
				if(!isSign.equals("yes")){
					initTuanData();
				}
				
				
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private String pnum2;
	private String total;
	private String tuanId;

	//拼团单独购买
	private void initData(String pnum) {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.PinTuanSingleBuyUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groups_id);
		map.put("user_id", userid);
		map.put("token", token);
		map.put("pnum", pnum);
		map.put("brand_id", brand_id);
		HTTPUtils.post(this, url, map, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("initData", arg0);
				Gson gson = new Gson();
				PinTuanComfirm fromJson = gson.fromJson(arg0, PinTuanComfirm.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					progressHUD.dismiss();
					PinTuanComfirmData data = fromJson.getData();
					String brandLogo = data.getBrandLogo();
					String brandTitle = data.getBrandTitle();
					String photo = data.getPhoto();
					pnum2 = data.getPnum();
					String singlePrice = data.getSinglePrice();
					String title = data.getTitle();
					total = data.getTotal();
					String xiaoji2 = data.getXiaoji();
					String youhui = data.getYouhui();
					String sku = data.getSku();
					brandId = data.getBrandId();
					
					UILUtils.displayImageNoAnim(brandLogo, confirmorder_brandimg);
					mBrandTitle.setText(brandTitle);
					UILUtils.displayImageNoAnim(photo, goods_logo);
					goods_title.setText(title);
					goods_attr.setText(sku);
					goods_newprice.setText("¥"+singlePrice);
					goods_num.setText("x"+pnum2);
					hongbaoyouhui.setText(youhui);
					xiaoji.setText(xiaoji2);
					order_total.setText(total);
					shangpin_total.setText(total);
					hongbao_total.setText(youhui);
					mTotal.setText(total);
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
	}


	private void initUI() {
		confirmorder_name = (TextView) findViewById(R.id.confirmorder_name);
		confirmorder_phone = (TextView) findViewById(R.id.confirmorder_phone);
		confirmorder_address = (TextView) findViewById(R.id.confirmorder_address);
		LinearLayout to_address = (LinearLayout) findViewById(R.id.to_address);
		to_address.setOnClickListener(this);
		confirmorder_brandimg = (ImageView) findViewById(R.id.confirmorder_brandimg);
		mBrandTitle = (TextView) findViewById(R.id.confirmorder_goodsname);
		
		goods_logo = (ImageView) findViewById(R.id.confirmorder_img);
		goods_title = (TextView) findViewById(R.id.confirmorder_goods_name);
		goods_attr = (TextView) findViewById(R.id.confirmorder_goods_color);
		goods_newprice = (TextView) findViewById(R.id.confirmorder_goods_newprice);
		goods_oldprice = (TextView) findViewById(R.id.confirmorder_goods_oldprice);
		goods_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		goods_num = (TextView) findViewById(R.id.confirmorder_goods_num);
		liuyan = (TextView) findViewById(R.id.confirm_liuyan);
		mBtn_pay = (TextView) findViewById(R.id.comfrim_pay);
		mBtn_pay.setOnClickListener(this);
		mTotal = (TextView) findViewById(R.id.goods_total);
		findViewById(R.id.tobrand).setOnClickListener(this);
		findViewById(R.id.confirm_back).setOnClickListener(this);
		
		order_total = (TextView) findViewById(R.id.order_total);
		hongbao_total = (TextView) findViewById(R.id.hongbao_total);
		shangpin_total = (TextView) findViewById(R.id.shangpin_total);
		hongbaoyouhui = (TextView) findViewById(R.id.hongbaoyouhui);
		xiaoji = (TextView) findViewById(R.id.xiaoji);
		
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null){
			String province = data.getStringExtra("province");
			String city = data.getStringExtra("city");
			String country = data.getStringExtra("country");
			String mobile = data.getStringExtra("mobile");
			String name = data.getStringExtra("name");
			String address = data.getStringExtra("address");
			addressId = data.getStringExtra("address_id");
			
			confirmorder_name.setText(name);
			confirmorder_phone.setText(mobile);
			confirmorder_address.setText(province+city+country+address);
			if(!isSign.equals("yes")){
				initTuanData();
			}
			
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.to_address:
			Intent intent2 = new Intent(ConfirmOrderPinTuanActivity.this, Add_AaddressActivity.class);
			intent2.putExtra("isOrder", true);
			startActivityForResult(intent2, 110);
			break;
		case R.id.confirm_back:
			finish();
			break;
		case R.id.comfrim_pay:
			//提交订单
//			SubMitOrder();
			if(addressId!=null){
				
				if(!isSign.equals("yes")){
					
					
					if(status2.equals("1")){
						Intent intent = new Intent(ConfirmOrderPinTuanActivity.this, PayActivity.class);
						intent.putExtra("oids", oids);
						intent.putExtra("total", total);
						startActivity(intent);
						finish();
						
					}else if(status2.equals("3")){
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "不在活动期内", Toast.LENGTH_SHORT).show();
						
					}else if(status2.equals("4")){
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "没有库存了", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "购买失败", Toast.LENGTH_SHORT).show();
						finish();
					}
					
					
				}else{
					SubMitOrder();
				}
			}else{
				Intent intent3 = new Intent(ConfirmOrderPinTuanActivity.this, Add_AaddressActivity.class);
				intent3.putExtra("isOrder", true);
				startActivityForResult(intent3, 110);
			}
			
			break;
		case R.id.tobrand:
			Intent intent = new Intent(ConfirmOrderPinTuanActivity.this, Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", brandId);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}

//单独购买提交订单

	private void SubMitOrder() {
		String url=Constant.URL.PinTuanSubMitUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groups_id);
		map.put("user_id", userid);
		map.put("token", token);
		map.put("pnum", pnum2);
		map.put("address_id", addressId);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("SubMitOrder", arg0);
				Gson gson = new Gson();
				SubmitOrder fromJson = gson.fromJson(arg0, SubmitOrder.class);
				zsj.com.oyk255.example.ouyiku.invitejson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					SubmitOrderData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						String oids = data.getOids();
						Intent intent = new Intent(ConfirmOrderPinTuanActivity.this, PayActivity.class);
						intent.putExtra("oids", oids);
						intent.putExtra("total", total);
						startActivity(intent);
						finish();
						
					}else if(status2.equals("3")){
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "不在活动期内", Toast.LENGTH_SHORT).show();
						
					}else if(status2.equals("4")){
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "没有库存了", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(ConfirmOrderPinTuanActivity.this, "购买失败", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
