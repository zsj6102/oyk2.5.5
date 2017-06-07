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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.invitejson.ComfirmOrder;
import zsj.com.oyk255.example.ouyiku.invitejson.ComfirmOrderDatum;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddress;
import zsj.com.oyk255.example.ouyiku.invitejson.MoRenAddressData;
import zsj.com.oyk255.example.ouyiku.invitejson.Product;
import zsj.com.oyk255.example.ouyiku.invitejson.Status;
import zsj.com.oyk255.example.ouyiku.invitejson.SubmitOrder;
import zsj.com.oyk255.example.ouyiku.invitejson.SubmitOrderData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class ConfirmOrderActivity extends OykActivity implements OnClickListener{

	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String cart_id;
	private String total;
	private TextView confirmorder_name;
	private TextView mBtn_pay;
	private ImageView confirmorder_brandimg;
	private ImageView goods_logo;
	private TextView confirmorder_phone;
	private TextView confirmorder_address;
	private TextView brand_name;
	private TextView goods_title;
	private TextView goods_attr;
	private TextView goods_newprice;
	private TextView goods_oldprice;
	private TextView goods_num;
	private TextView liuyan;
	private TextView mTotal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		cart_id = intent.getStringExtra("cart_id");
		
		initUI();
		initData();
		initAddress();
	}

	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	@Override
	protected void onResume() {
//		initAddress();
		super.onResume();
	}
	private String addressId;
	
	private void initAddress() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.MoRenAddressUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MoRenAddress fromJson = gson.fromJson(arg0, MoRenAddress.class);
				Status status = fromJson.getStatus();
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

	
	private String brandId;
	private String brandmerchantId;
	private TextView xiaoji;
	private TextView hongbaoyouhui;
	private TextView order_total;
	private TextView hongbao_total;
	private TextView shangpin_total;
	
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.ConfirmOrderListUrl;
		HashMap<String , String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("cart_id", cart_id);
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Log.e("常规订单", arg0);
				Log.e("cart_id", cart_id);
				Log.e("userid", userid);
				Log.e("token", token);
				
				
				progressHUD.dismiss();
				Gson gson = new Gson();
				ComfirmOrder fromJson = gson.fromJson(arg0, ComfirmOrder.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<ComfirmOrderDatum> data = fromJson.getData();
					if(data!=null){
						total = fromJson.getTotal();
						String alltotal = fromJson.getAlltotal();
						String houbaototal = fromJson.getHoubaototal();
						
						mTotal.setText(total+"元");
						brandId = data.get(0).getBrandId();
						ComfirmOrderDatum comfirmOrderDatum = data.get(0);
						String logo = comfirmOrderDatum.getLogo();
						List<Product> product = comfirmOrderDatum.getProduct();
						brandmerchantId = comfirmOrderDatum.getBrandmerchantId();
						String attr = product.get(0).getAttr();
						String brandname = product.get(0).getBrandname();
						String image = product.get(0).getImage();
						String marketprice = product.get(0).getMarketprice();
						String number = product.get(0).getNumber();
						String price = product.get(0).getPrice();
						String title2 = product.get(0).getTitle();
						String hongbao = data.get(0).getHongbao();
						String brandTotal = data.get(0).getBrandTotal();
						String brand_pay = data.get(0).getBrand_pay();
						
						UILUtils.displayImageNoAnim(logo, confirmorder_brandimg);
						UILUtils.displayImageNoAnim(image, goods_logo);
						brand_name.setText(brandname);
						goods_title.setText(title2);
						goods_attr.setText(attr);
						goods_newprice.setText("¥"+price);
						goods_oldprice.setText("¥"+marketprice);
						goods_num.setText("x"+number);
						
						xiaoji.setText("¥"+brand_pay);
						hongbaoyouhui.setText(hongbao);
						order_total.setText(alltotal);
						hongbao_total.setText(houbaototal);
						shangpin_total.setText(alltotal);
						
					}else{
						Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class);
						startActivity(intent);
					}
					
					
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
		confirmorder_brandimg = (ImageView) findViewById(R.id.confirmorder_brandimg);
		brand_name = (TextView) findViewById(R.id.confirmorder_goodsname);
		
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.to_address:
			Intent intent2 = new Intent(ConfirmOrderActivity.this, Add_AaddressActivity.class);
			intent2.putExtra("isOrder", true);
			startActivityForResult(intent2, 110);
			
			break;
		case R.id.tobrand:
			Intent intent = new Intent(ConfirmOrderActivity.this, Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", brandId);
			startActivity(intent);
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
	
	
	//提交订单
	private void SubMitOrder() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();

		String LiuYan = liuyan.getText().toString().trim();
		String Url=Constant.URL.SubmitOrderUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("address_id", addressId);
		map.put("remark", LiuYan);
		map.put("cart_id", cart_id);
		if(TextUtils.isEmpty(addressId)){
			Toast.makeText(this,"请选择收货地址",Toast.LENGTH_SHORT).show();
			return;
		}
		HTTPUtils.post(this, Url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SubmitOrder fromJson = gson.fromJson(arg0, SubmitOrder.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					SubmitOrderData data = fromJson.getData();
					String oids = data.getOids();
					Intent intent = new Intent(ConfirmOrderActivity.this, PayActivity.class);
					intent.putExtra("oids", oids);
					intent.putExtra("total", total);
					startActivity(intent);
					finish();
				}
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

}
