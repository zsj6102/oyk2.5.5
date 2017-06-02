package zsj.com.oyk255.example.ouyiku.v1;

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
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.huodong.HuoDongDetail;
import zsj.com.oyk255.example.ouyiku.huodong.HuoDongDetailData;
import zsj.com.oyk255.example.ouyiku.huodong.NineZeroBuy;
import zsj.com.oyk255.example.ouyiku.huodong.NineZeroBuyData;
import zsj.com.oyk255.example.ouyiku.huodong.Status;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddress;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddressData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class ConfirmOrder9and0Activity extends OykActivity implements OnClickListener{

	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private TextView confirmorder_name;
	private TextView mBtn_pay;
	private ImageView goods_logo;
	private TextView confirmorder_phone;
	private TextView confirmorder_address;
	private String addressId;
	private TextView goods_title;
	private TextView goods_attr;
	private TextView goods_newprice;
	private TextView goods_oldprice;
	private TextView goods_num;
//	private TextView liuyan;
	private TextView mTotal;
	private String productId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order9and0);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		productId = intent.getStringExtra("productId");
		initUI();
		initAddress();
		initData();
	}
	private String currprice;
	
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.NineDetailURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("product_id", productId);
		map.put("user_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				Log.e("活动订单", arg0);
				Gson gson = new Gson();
				progressHUD.dismiss();
				HuoDongDetail fromJson = gson.fromJson(arg0, HuoDongDetail.class);
				 Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					HuoDongDetailData data = fromJson.getData();
					List<String> productImage = data.getProductImage();
					String title = data.getTitle();
					String marketprice = data.getMarketprice();
					currprice = data.getCurrprice();
					String attr = data.getAttr();
					String imgurl = productImage.get(0);
					
					UILUtils.displayImageNoAnim(imgurl, goods_logo);
					goods_title.setText(title);  
					goods_attr.setText(attr);
					goods_newprice.setText("￥"+currprice);
					
					goods_oldprice.setText("￥"+marketprice);
					goods_num.setText("x"+1);
					
					mTotal.setText(currprice+"元");
				}
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		confirmorder_name = (TextView) findViewById(R.id.confirmorder_name);
		confirmorder_phone = (TextView) findViewById(R.id.confirmorder_phone);
		confirmorder_address = (TextView) findViewById(R.id.confirmorder_address);
		LinearLayout to_address = (LinearLayout) findViewById(R.id.to_address);
		to_address.setOnClickListener(this);
//		confirmorder_brandimg = (ImageView) findViewById(R.id.confirmorder_brandimg);
//		brand_name = (TextView) findViewById(R.id.confirmorder_goodsname);
		
		goods_logo = (ImageView) findViewById(R.id.confirmorder_img);
		goods_title = (TextView) findViewById(R.id.confirmorder_goods_name);
		goods_attr = (TextView) findViewById(R.id.confirmorder_goods_color);
		goods_newprice = (TextView) findViewById(R.id.confirmorder_goods_newprice);
		goods_oldprice = (TextView) findViewById(R.id.confirmorder_goods_oldprice);
		goods_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		goods_num = (TextView) findViewById(R.id.confirmorder_goods_num);
//		liuyan = (TextView) findViewById(R.id.confirm_liuyan);
		mBtn_pay = (TextView) findViewById(R.id.comfrim_pay);
		mBtn_pay.setOnClickListener(this);
		mTotal = (TextView) findViewById(R.id.goods_total);
//		findViewById(R.id.tobrand).setOnClickListener(this);
		findViewById(R.id.confirm_back).setOnClickListener(this);
	}

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
					
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.to_address:
			Intent intent2 = new Intent(ConfirmOrder9and0Activity.this, Add_AaddressActivity.class);
			intent2.putExtra("isOrder", true);
			startActivityForResult(intent2, 110);
			
			break;
		case R.id.confirm_back:
			finish();
			break;
		case R.id.comfrim_pay:
			SubMitOrder();

			break;

		default:
			break;
		}
		
	}
	private void SubMitOrder() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.NineBuyURL;
		HashMap<String, String> map=new HashMap<>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("product_id", productId);
		map.put("address_id", addressId);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("SubMitOrder", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				NineZeroBuy fromJson = gson.fromJson(arg0, NineZeroBuy.class);
				zsj.com.oyk255.example.ouyiku.huodong.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					NineZeroBuyData data = fromJson.getData();
					String oids = data.getOids();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						Intent intent = new Intent(ConfirmOrder9and0Activity.this, PayActivity.class);
						intent.putExtra("oids", oids);
						intent.putExtra("total", currprice);
						startActivity(intent);
						finish();
					}else if(status2.equals("2")){
						Toast.makeText(ConfirmOrder9and0Activity.this, "已经购买过此商品", Toast.LENGTH_SHORT).show();
					}else if(status2.equals("3")){
						Toast.makeText(ConfirmOrder9and0Activity.this, "活动已结束", Toast.LENGTH_SHORT).show();
					}else if(status2.equals("4")){
						Toast.makeText(ConfirmOrder9and0Activity.this, "没有库存了", Toast.LENGTH_SHORT).show();
					}else if(status2.equals("5")){
						Toast.makeText(ConfirmOrder9and0Activity.this, "购买成功", Toast.LENGTH_SHORT).show();
						finish();
						//0元购买成功
					}
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
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
			
		}
	}

}
