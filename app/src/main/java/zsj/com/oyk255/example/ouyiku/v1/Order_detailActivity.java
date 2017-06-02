package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Data;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Good;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.OrderDetail;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Order_detailActivity extends OykActivity implements OnClickListener{
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String orderId;//订单id
	private ScrollViewWithListView mListView;
	private TextView mOrder_wuliu_num;
	private TextView mOrder_wuliu_creattime;
	private TextView mOrder_wuliu_paytime;
	private TextView mOrder_wuliu_deliverytime;
	private TextView mOrder_wuliu_company;
	private TextView mOrder_wuliu_wuliunum;
	private TextView mOrder_all_moneye;
	private TextView mOrder_all_liuyan;
	private TextView mOrder_all_name;
	private TextView mOrder_all_phone;
	private TextView mOrder_all_address;
	ArrayList<Good> mGoodsData=new ArrayList<Good>();
	private ImageView mOrder_all_img;
	private TextView mOrder_all_goodsname;
	private ScrollView mScroll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		Log.e("orderId", orderId);
		Log.e("userid", userid);
		
		initUI();
		initData();
	}

	private String brandmerchantId;
	private TextView mOrder_saleAfter;
	private String goodsId;
	
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String OrderInfoUrl= Constant.URL.OrderDetailURL+"&user_id="+userid+"&order_id="+orderId+"&token="+token;
		HTTPUtils.get(this, OrderInfoUrl, new VolleyListener() {
			


			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				OrderDetail fromJson = gson.fromJson(arg0, OrderDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					String exphone = data.getExphone();//快递电话
					String expressname = data.getExpressname();//快递名称
					String expressnum = data.getExpressnum();//快递单号
					String createTime = data.getCreateTime();//创建时间
					String colsetime = data.getColsetime();//结束时间
					String deliverytime = data.getDeliverytime();//发货时间
					String orderId2 = data.getOrderId();//订单id
					String ordernum = data.getOrdernum();//订单编号
					String payTime = data.getPayTime();//付款时间
					String remark = data.getRemark();//买家留言
					String status2 = data.getStatus();//快递状态
					String price = data.getPrice();//总价
					
					 String address = data.getAddress();//收货地址
					 String delivery_name = data.getDelivery_name();//收货人
					 String delivery_postcode = data.getDelivery_postcode();//邮编
					 String delivery_telephone = data.getDelivery_telephone();//收货人电话
					 
					 
					List<Good> goods = data.getGoods();
					mGoodsData.addAll(goods);
					Good good = mGoodsData.get(0);
					String brandlogo = good.getBrandlogo();
					brandmerchantId = good.getBrandmerchantId();
					String brandtitle = good.getBrandtitle();
					goodsId = good.getGoodsId();
					UILUtils.displayImageNoAnim(brandlogo, mOrder_all_img);
					mOrder_all_goodsname.setText(brandtitle);
					
					
//					String addressId = address.getAddressId();//地址id
//					String city = address.getCity();//市
//					String country = address.getCountry();//县
//					String mobile = address.getMobile();//电话
//					String name = address.getName();//收货人
//					String province = address.getProvince();//省份
//					String address2 = address.getAddress();//区
					
					mOrder_all_name.setText(delivery_name);
					mOrder_all_phone.setText(delivery_telephone);
					mOrder_all_address.setText(address);
					
					mListView.setAdapter(new AllAdapter());
					mScroll.smoothScrollTo(0, 0);
					
					mOrder_wuliu_num.setText(ordernum);
					mOrder_wuliu_creattime.setText(createTime);
					mOrder_wuliu_paytime.setText(payTime);
					mOrder_wuliu_deliverytime.setText(deliverytime);
					mOrder_wuliu_company.setText(expressname);
					mOrder_wuliu_wuliunum.setText(expressnum);
					mOrder_all_moneye.setText(price);
					mOrder_all_liuyan.setText(remark);
					
					
					
				}
//				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		mScroll = (ScrollView) findViewById(R.id.orderdetail_scrollView);
		mOrder_all_name = (TextView) findViewById(R.id.order_all_name);
		mOrder_all_phone = (TextView) findViewById(R.id.order_all_phone);
		mOrder_all_address = (TextView) findViewById(R.id.order_all_address);
		mOrder_all_goodsname = (TextView) findViewById(R.id.order_all_goodsname);
		mOrder_all_moneye = (TextView) findViewById(R.id.order_all_money);
		mOrder_all_liuyan = (TextView) findViewById(R.id.order_all_liuyan);
		mOrder_all_img = (ImageView) findViewById(R.id.order_all_img);
		mOrder_wuliu_num = (TextView) findViewById(R.id.order_wuliu_num);
		TextView mOrder_wuliu_copy = (TextView) findViewById(R.id.order_wuliu_copy);
		mOrder_wuliu_creattime = (TextView) findViewById(R.id.order_wuliu_creattime);
		mOrder_wuliu_paytime = (TextView) findViewById(R.id.order_wuliu_paytime);
		mOrder_wuliu_deliverytime = (TextView) findViewById(R.id.order_wuliu_deliverytime);
		mOrder_wuliu_company = (TextView) findViewById(R.id.order_wuliu_company);
		mOrder_wuliu_wuliunum = (TextView) findViewById(R.id.order_wuliu_wuliunum);
		mOrder_saleAfter = (TextView) findViewById(R.id.order_all_shouhou);
		mOrder_saleAfter.setOnClickListener(this);
		mListView = (ScrollViewWithListView) findViewById(R.id.scrollViewWithListView_all);
		findViewById(R.id.order_all_back).setOnClickListener(this);
		findViewById(R.id.tobrand).setOnClickListener(this);
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Good good = mGoodsData.get(position);
//				String productId = good.getProductId();
//				Log.e("productId", productId);
//				Intent intent = new Intent(Order_detailActivity.this, DetailActivity.class);
//				intent.putExtra("product_id", productId);
//				startActivity(intent);
//				
//				
//			}
//		});
		findViewById(R.id.order_all_deleteorder).setOnClickListener(this);
		findViewById(R.id.order_wuliu_copy).setOnClickListener(this);
		
		
	}
	
	class AllAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mGoodsData.size();
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
			View layout = null;
			ViewHolder holder=null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.orderdetail_item, null);
				holder=new ViewHolder();
				holder.mGoods_img = (ImageView) layout.findViewById(R.id.order_goods_img);
				holder.mGoods_name = (TextView) layout.findViewById(R.id.order_goods_name);
				holder.mGoods_attr = (TextView) layout.findViewById(R.id.order_goods_color);
				holder.mGoods_price = (TextView) layout.findViewById(R.id.order_goods_newprice);
				holder.mGoods_num = (TextView) layout.findViewById(R.id.order_goods_num);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			Good good = mGoodsData.get(position);
			String brandlogo = good.getBrandlogo();
			String brandmerchantId = good.getBrandmerchantId();
			String brandtitle = good.getBrandtitle();
			String goodsId = good.getGoodsId();
			String image = good.getImage();
			String isReview = good.getIsReview();
			String num = good.getNum();
			String productId = good.getProductId();
			String attr = good.getAttr();
			String price = good.getPrice();
			String title = good.getTitle();
			
			holder.mGoods_name.setText(title);
			holder.mGoods_attr.setText(attr);
			holder.mGoods_price.setText("¥"+price);
			holder.mGoods_num.setText("x"+num);
			UILUtils.displayImageNoAnim(image, holder.mGoods_img);
			
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView mGoods_img;
		TextView mGoods_name;
		TextView mGoods_attr;
		TextView mGoods_price;
		TextView mGoods_num;
		
	}
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_all_back:
			finish();
			break;
		case R.id.order_all_shouhou:
			Intent intent2 = new Intent(Order_detailActivity.this, Apply_SaleAfterActivity.class);
			intent2.putExtra("goodsid", goodsId);
			intent2.putExtra("orderid", orderId);
			
			startActivity(intent2);
			break;
		case R.id.tobrand:
			Intent intent = new Intent(this, Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", brandmerchantId);
			startActivity(intent);
			break;
		case R.id.order_all_deleteorder:
			DeleteOrder();
			
			break;
		case R.id.order_wuliu_copy:
			String text = mOrder_wuliu_num.getText().toString();
			
			ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			clip.setText(text);
			Toast.makeText(Order_detailActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}

	private void DeleteOrder() {
		String url=Constant.URL.DeleteOrderURL+"&user_id="+userid+"&order_id="+orderId+"&token="+token;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				
				
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					finish();
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
	}
}
