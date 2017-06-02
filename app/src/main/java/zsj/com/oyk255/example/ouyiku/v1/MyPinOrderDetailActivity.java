package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class MyPinOrderDetailActivity extends OykActivity implements OnClickListener{
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String orderId;
	ArrayList<Good> mGoodsData=new ArrayList<Good>();
	private ImageView mBrandLogo;
	private TextView mBrandTitle;
	private TextView mName;
	private TextView mPhone;
	private TextView mAddress;
	private TextView mStatus;
	private TextView mBtn1;
	private TextView mMoney;
	private TextView mLiuYan;
	private TextView mOrder_wuliu_num;
	private TextView mCopy;
	private TextView mOrder_wuliu_creattime;
	private TextView mOrder_wuliu_paytime;
	private TextView mOrder_wuliu_deliverytime;
	private TextView mOrder_wuliu_company;
	private TextView mOrder_wuliu_wuliunum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pin_order_detail);
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		initUI();
		initData();
		Log.e("orderId", orderId);
		
	}
	private String brandmerchantId;
	private ScrollView mScroll;
	private ScrollViewWithListView mListView;
	private LinearLayout pintuandetil_bottom1;
	private LinearLayout pintuandetil_bottom2;
	private LinearLayout pintuandetil_bottom3;

	private void initData() {
		String url= Constant.URL.OrderDetailURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("order_id", orderId);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
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
					final String orderId2 = data.getOrderId();//订单id
					String ordernum = data.getOrdernum();//订单编号
					String payTime = data.getPayTime();//付款时间
					String remark = data.getRemark();//买家留言
					String status2 = data.getStatus();//快递状态
					final String price = data.getPrice();//总价
					Log.e("orderId2", orderId2);
					 String address = data.getAddress();//收货地址
					 String delivery_name = data.getDelivery_name();//收货人
					 String delivery_postcode = data.getDelivery_postcode();//邮编
					 String delivery_telephone = data.getDelivery_telephone();//收货人电话
					 List<Good> goods = data.getGoods();
					 mGoodsData.addAll(goods);
					 	Good good = mGoodsData.get(0);
					 	
						String brandlogo = good.getBrandlogo();
						String brandtitle = good.getBrandtitle();
						String goodsId = good.getGoodsId();
						brandmerchantId = good.getBrandmerchantId();
						UILUtils.displayImageNoAnim(brandlogo, mBrandLogo);
						mBrandTitle.setText(brandtitle);
					 
						mName.setText(delivery_name);
						mPhone.setText(delivery_telephone);
						mAddress.setText(address);
					 
						mOrder_wuliu_num.setText(ordernum);
						mOrder_wuliu_creattime.setText(createTime);
						mOrder_wuliu_paytime.setText(payTime);
						mOrder_wuliu_deliverytime.setText(deliverytime);
						mOrder_wuliu_company.setText(expressname);
						mOrder_wuliu_wuliunum.setText(expressnum);
						mMoney.setText("¥"+price);
						mLiuYan.setText(remark);
						
						String tstatusinfo = data.getTstatusinfo();
						String tstatus = data.getTstatus();
						String endtime_s = data.getEndtime_s();
						recLen = Integer.valueOf(endtime_s);
						mStatus.setText(tstatusinfo);
						timer.schedule(task, 1000, 1000);
						//status :1末付款2待发货3待收货4交易成功5交易关闭6售后申请中7售后成功

						if(status2.equals("1")){
							//未付款
							mBtn1.setVisibility(View.VISIBLE);
							mBtn1.setText("付款");
							pintuandetil_bottom1.setVisibility(View.VISIBLE);
							pintuandetil_bottom2.setVisibility(View.GONE);
							pintuandetil_bottom3.setVisibility(View.GONE);
							mBtn1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(MyPinOrderDetailActivity.this, PayActivity.class);
									intent.putExtra("oids", orderId2);
									intent.putExtra("total", price);
									startActivity(intent);
									
								}
							});
						}else if(status2.equals("2")){
							mBtn1.setVisibility(View.VISIBLE);
							mBtn1.setText("提醒发货");
							pintuandetil_bottom1.setVisibility(View.GONE);
							pintuandetil_bottom2.setVisibility(View.VISIBLE);
							pintuandetil_bottom3.setVisibility(View.GONE);
							mBtn1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									ToastUtils.toast(MyPinOrderDetailActivity.this, "提醒成功");
								}
							});
						}else if(status2.equals("3")){
							mBtn1.setVisibility(View.VISIBLE);
							mBtn1.setText("确认收货");
							pintuandetil_bottom1.setVisibility(View.VISIBLE);
							pintuandetil_bottom2.setVisibility(View.GONE);
							pintuandetil_bottom3.setVisibility(View.GONE);
							mBtn1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									String url=Constant.URL.CheckDeliveryURL;
									HashMap<String, String> map=new HashMap<String, String>();
									map.put("user_id", userid);
									map.put("order_id", orderId2);
									HTTPUtils.post(MyPinOrderDetailActivity.this, url, map, new VolleyListener() {
										
										@Override
										public void onResponse(String arg0) {
											Gson gson = new Gson();
											
											IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
											zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
											String succeed = status.getSucceed();
											if(succeed.equals("1")){
												Toast.makeText(MyPinOrderDetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
												finish();
											}
											
										}
										
										@Override
										public void onErrorResponse(VolleyError arg0) {
											// TODO Auto-generated method stub
											
										}
									});
								}
							});
						}else{
							mBtn1.setVisibility(View.GONE);
							//团状态0 失败 1 已成团,可发货 2 组团中
							if(tstatus.equals("2")){
								pintuandetil_bottom3.setVisibility(View.VISIBLE);
								pintuandetil_bottom1.setVisibility(View.GONE);
								pintuandetil_bottom2.setVisibility(View.GONE);
							}else{
								pintuandetil_bottom1.setVisibility(View.VISIBLE);
								pintuandetil_bottom2.setVisibility(View.GONE);
								pintuandetil_bottom3.setVisibility(View.GONE);
							}
						}
						
						
						
						
						mListView.setAdapter(new AllAdapter());
						mScroll.smoothScrollTo(0, 0);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		
	}
	
	 //秒数转换为00:00:00
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    
    private int recLen ;    
	Timer timer = new Timer();

	TimerTask task = new TimerTask() {  
        @Override  
        public void run() {  
 
            runOnUiThread(new Runnable() {      // UI thread  
                @Override  
                public void run() {  
                    recLen--;  
                    String secToTime = secToTime(recLen);
                    
                    mShengYUtIME.setText(secToTime);  
                    if(recLen < 0){  
                        timer.cancel();  
                    }  
                }  
            });  
        }  
    };
	private TextView mShengYUtIME;
	private TextView mDeleteOrder;
	private TextView pintuandetail_btn2;

	private void initUI() {
		findViewById(R.id.pintuanorder_back).setOnClickListener(this);
		mName = (TextView) findViewById(R.id.pintuandetail_name);
		mPhone = (TextView) findViewById(R.id.pintuandetail_phone);
		mAddress = (TextView) findViewById(R.id.pintuandetail_address);
		mBrandLogo = (ImageView) findViewById(R.id.pintuandetail_brandimg);
		mBrandTitle = (TextView) findViewById(R.id.pintuandetail_brandname);
		
		mStatus = (TextView) findViewById(R.id.pintuandetail_status);
		mBtn1 = (TextView) findViewById(R.id.pintuandetail_btn1);
		mMoney = (TextView) findViewById(R.id.pintuandetail_money);
		mLiuYan = (TextView) findViewById(R.id.pintuandetail_liuyan);
		
		mOrder_wuliu_num = (TextView) findViewById(R.id.order_wuliu_num);
		mCopy = (TextView) findViewById(R.id.order_wuliu_copy);
		mCopy.setOnClickListener(this);
		mOrder_wuliu_creattime = (TextView) findViewById(R.id.order_wuliu_creattime);
		mOrder_wuliu_paytime = (TextView) findViewById(R.id.order_wuliu_paytime);
		mOrder_wuliu_deliverytime = (TextView) findViewById(R.id.order_wuliu_deliverytime);
		mOrder_wuliu_company = (TextView) findViewById(R.id.order_wuliu_company);
		mOrder_wuliu_wuliunum = (TextView) findViewById(R.id.order_wuliu_wuliunum);
		findViewById(R.id.order_wuliu_copy).setOnClickListener(this);
		findViewById(R.id.tobrand).setOnClickListener(this);
		mListView = (ScrollViewWithListView) findViewById(R.id.scrollViewWithListView_all);
		mScroll = (ScrollView) findViewById(R.id.pintuandetail_scrollView);
		pintuandetil_bottom1 = (LinearLayout) findViewById(R.id.pintuandetil_bottom1);
		pintuandetil_bottom2 = (LinearLayout) findViewById(R.id.pintuandetil_bottom2);
		pintuandetil_bottom3 = (LinearLayout) findViewById(R.id.pintuandetil_bottom3);
		mShengYUtIME = (TextView) findViewById(R.id.cantuan_time);
		mDeleteOrder = (TextView) findViewById(R.id.order_all_deleteorder);
		mDeleteOrder.setOnClickListener(this);
		pintuandetail_btn2 = (TextView) findViewById(R.id.pintuandetail_btn2);
		pintuandetail_btn2.setOnClickListener(this);
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pintuanorder_back:
			finish();
			break;
		case R.id.order_all_deleteorder:
			DeleteOrder();
			break;
		case R.id.pintuandetail_btn2:
			ToastUtils.toast(MyPinOrderDetailActivity.this, "提醒成功");
			break;
		case R.id.tobrand:
			Intent intent = new Intent(this, Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", brandmerchantId);
			startActivity(intent);
			break;
		case R.id.order_wuliu_copy:
			String text = mOrder_wuliu_num.getText().toString();
			
			ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			clip.setText(text);
			ToastUtils.toast(MyPinOrderDetailActivity.this, "已复制到剪贴板");
			break;

		default:
			break;
		}
		
	}

}
