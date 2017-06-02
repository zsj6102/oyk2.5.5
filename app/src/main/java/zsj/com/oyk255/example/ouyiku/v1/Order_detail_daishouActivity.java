package zsj.com.oyk255.example.ouyiku.v1;


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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Data;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Good;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.OrderDetail;
import zsj.com.oyk255.example.ouyiku.orderdetailjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Order_detail_daishouActivity extends OykActivity implements OnClickListener{
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
		setContentView(R.layout.activity_order_detail_daishou);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		initUI();
		initData();
		
	}
	private String brandmerchantId;
	private String exphone;
	private String expressname;
	private String expressnum;
	private String ordernum;
	private String goodsId;
	private String status2;
	private LinearLayout mDeleteBottom;
	private LinearLayout mQuerenBottom;
	private TextView mDelete;
	private TextView mApplySaleAfter;
	
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String OrderInfoUrl= Constant.URL.OrderDetailURL+"&user_id="+userid+"&order_id="+orderId+"&token="+token;
		HTTPUtils.get(this, OrderInfoUrl, new VolleyListener() {
			



			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				OrderDetail fromJson = gson.fromJson(arg0, OrderDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					
					Data data = fromJson.getData();
					exphone = data.getExphone();
					expressname = data.getExpressname();
					expressnum = data.getExpressnum();
					String createTime = data.getCreateTime();//创建时间
					String colsetime = data.getColsetime();//结束时间
					String deliverytime = data.getDeliverytime();//发货时间
					String orderId2 = data.getOrderId();//订单id
					ordernum = data.getOrdernum();
					String payTime = data.getPayTime();//付款时间
					String remark = data.getRemark();//买家留言
					status2 = data.getStatus();
					String price = data.getPrice();//总价
					Log.e("orderId2", orderId2);
					if(status2.equals("3")){
						mDeleteBottom.setVisibility(View.GONE);
						mQuerenBottom.setVisibility(View.VISIBLE);
					}else{
						mDeleteBottom.setVisibility(View.VISIBLE);
						mQuerenBottom.setVisibility(View.GONE);
					}
					
					String address = data.getAddress();
					String delivery_name = data.getDelivery_name();
					String delivery_telephone = data.getDelivery_telephone();
					
					List<Good> goods = data.getGoods();
					mGoodsData.addAll(goods);
					Good good = mGoodsData.get(0);
					String brandlogo = good.getBrandlogo();
					brandmerchantId = good.getBrandmerchantId();
					String brandtitle = good.getBrandtitle();
					goodsId = good.getGoodsId();
					UILUtils.displayImageNoAnim(brandlogo, mOrder_all_img);
					mOrder_all_goodsname.setText(brandtitle);
					
					if(status2.equals("7")){
						mApplySaleAfter.setText("售后申请成功");
					}else if(status2.equals("6")){
						mApplySaleAfter.setText("售后申请中");
					}
					
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
		mOrder_wuliu_creattime = (TextView) findViewById(R.id.order_wuliu_creattime);
		mOrder_wuliu_paytime = (TextView) findViewById(R.id.order_wuliu_paytime);
		mOrder_wuliu_deliverytime = (TextView) findViewById(R.id.order_wuliu_deliverytime);
		mOrder_wuliu_company = (TextView) findViewById(R.id.order_wuliu_company);
		mOrder_wuliu_wuliunum = (TextView) findViewById(R.id.order_wuliu_wuliunum);
		
		mListView = (ScrollViewWithListView) findViewById(R.id.scrollViewWithListView_daishou);
		findViewById(R.id.order_daishou_back).setOnClickListener(this);
		
		findViewById(R.id.tobrand).setOnClickListener(this);
		
		mApplySaleAfter = (TextView) findViewById(R.id.order_all_shouhou);
		mApplySaleAfter.setOnClickListener(this);
		
		findViewById(R.id.order_wuliu_copy).setOnClickListener(this);
		findViewById(R.id.order_all_lookwuliu).setOnClickListener(this);
		findViewById(R.id.order_all_querenshouhuo).setOnClickListener(this);
		mDeleteBottom = (LinearLayout) findViewById(R.id.bottom_delete);
		mQuerenBottom = (LinearLayout) findViewById(R.id.bottom_queren);
		mDelete = (TextView) findViewById(R.id.order_all_deleteorder);
		mDelete.setOnClickListener(this);
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
		case R.id.order_daishou_back:
			finish();
			break;
		case R.id.order_all_shouhou:
			if(status2.equals("6")){
				Intent intent = new Intent(Order_detail_daishouActivity.this, Sale_afterActivity.class);
				startActivity(intent);
			}else if(status2.equals("7")){
				//售后成功
				Intent intent = new Intent(Order_detail_daishouActivity.this, Sale_afterActivity.class);
				startActivity(intent);
			}else{
				Intent intent2 = new Intent(Order_detail_daishouActivity.this, Apply_SaleAfterActivity.class);
				intent2.putExtra("goodsid", goodsId);
				intent2.putExtra("orderid", orderId);
				startActivity(intent2);
				
			}
			
			break;
		case R.id.order_wuliu_copy:
			String text = mOrder_wuliu_num.getText().toString();
			ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			clip.setText(text);
			Toast.makeText(Order_detail_daishouActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
			break;
		case R.id.order_all_lookwuliu:
			Intent intent23 = new Intent(this, WuliuActivity.class);
			intent23.putExtra("exphone", exphone);
			intent23.putExtra("expressname", expressname);
			intent23.putExtra("expressnum", expressnum);
			intent23.putExtra("ordernum", ordernum);
			startActivity(intent23);
			break;
		case R.id.order_all_querenshouhuo:
			CheckDelivery();
			break;
		case R.id.tobrand:
			Intent intent = new Intent(this, Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", brandmerchantId);
			startActivity(intent);
			break;
		case R.id.order_all_deleteorder:
			//删除订单
			deleteOrder(orderId);
			break;

		default:
			break;
		}
		
	}
	
	private void CheckDelivery(){
		String CheckDeliveryUrl=Constant.URL.CheckDeliveryURL+"&user_id="+userid+"&order_id="+orderId+"&token="+token;
		HTTPUtils.get(this, CheckDeliveryUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					ToastUtils.toast(Order_detail_daishouActivity.this, "收货成功");
					
					finish();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	} 

	private void deleteOrder(String orderId){
		
		String deleteUrl=Constant.URL.DeleteOrderURL+"&user_id="+userid+"&order_id="+orderId+"&token="+token;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, deleteUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				progressHUD.dismiss();
				
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
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
