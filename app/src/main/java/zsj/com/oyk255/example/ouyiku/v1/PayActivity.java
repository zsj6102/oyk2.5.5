package zsj.com.oyk255.example.ouyiku.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.alipay.PayResult;
import zsj.com.oyk255.example.ouyiku.alipay.SignUtils;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTop;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTopData;
import zsj.com.oyk255.example.ouyiku.huodong.AliPayArg;
import zsj.com.oyk255.example.ouyiku.huodong.AliPayArgData;
import zsj.com.oyk255.example.ouyiku.huodong.Status;
import zsj.com.oyk255.example.ouyiku.huodong.WeChatPay;
import zsj.com.oyk255.example.ouyiku.huodong.WeChatPayData;
import zsj.com.oyk255.example.ouyiku.orderjson.IsSuccess;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.NetWorkUtils;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PayActivity extends OykActivity implements OnClickListener{
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String oids;
	private String total;
	private ImageView yue_img;
	private ImageView ali_img;
	private ImageView weChat_img;
	HashMap<Integer, Boolean> isCheck=new HashMap<Integer, Boolean>();
	private TextView mTotal;
	private TextView mQitaTotal;
	private static final String APP_ID= Constant.APPID.WXAPPID;
	private IWXAPI api;
	// 商户PID
		public static  String PARTNER = "";
		// 商户收款账号
		public static  String SELLER = "";
		// 商户私钥，pkcs8格式
		public static  String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIZQdqbDVcJwvDhztAM1LWww5pc0Iyk8cSOzw6c2P9Ijhb3i1pFiqwl80f"
				+ "ria8yO+hQ0dGjTRRsxkHgappLu0dNCfZMI+CY/5fLPFzZs3+dcvZJ4tsUGUJEWaGt4A7T"
				+ "+7Gpk5oZ9+5cBdPIMB4pqDdSJ42Kr5EOgxJ58Oa34oUfAgMBAAECgYAib7/MBS8PJVQMP509nT2D5CnuLwQkLcCRwQtISAutUNoolrTbP5c"
				+ "QskXYIjL9LTloMjhf0LOWv3GWS05Rh1Muo2bBiZg3SKmwJFPMRtYvTpQ7i/uaY0nzsSGmU7NbvwUTdsv0K9yR9YcVS1fs17cUVlzRCNYsNw"
				+ "mrr97LwTQIAQJBAOZfRUTfTN4CIZYk1g5qtpVtclpLpOIgcLKnLUsx2OCvt0ZlrQZjoDU1mPE3LVnkvT5r52e5/fUCOtqDwbNA5icCQQDF6Ux+/XK+M+j+tZMvb8Wz6N3KRkZhE4GBwDP0QztltFT50UKTPbkZYNG9fkUutvNXQ+dQtnE3ceslreEjqnxJAkEA5ADAzwRu0H"
				+ "+tfCujDAvD/6qd9g5N91wCDl91bam9K8vdibgSAnPR2Tfd0kc5BHJmdoRXOAsV9UsqsqCasm32GwJBAJgpJbIhBJ4t5P4n9jCre9I1wIfq/eNXDuQGNSugoUMxoX+snsX715smcDuDoIcpC/BU+bx5eJRLVqJSjF1Sj1kCQBB/4QinAamjoAN7+rmEx"
				+ "+8zsd05kOx6qt0p0BmSZRZIUmGzOS/d+lQkuNNXbVHeqHf8+uYsxVGHU59AVRWPN9g=";
		// 支付宝公钥
		public static  String RSA_PUBLIC = "";
		private static final int SDK_PAY_FLAG = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		PushAgent.getInstance(this).onAppStart();
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		oids = intent.getStringExtra("oids");
		total = intent.getStringExtra("total");
		initUI();
		GetYuE();
	}
	private void GetYuE() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.WalletTopURL+"&user_id="+userid+"&token="+token;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MyWalletTop fromJson = gson.fromJson(arg0, MyWalletTop.class);
				zsj.com.oyk255.example.ouyiku.brandjson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					MyWalletTopData data = fromJson.getData();
					String allMoney = data.getAllMoney();
					String usefullMoney = data.getUsefullMoney();
					mYuE.setText("可用余额：￥"+usefullMoney);
//					mWallAll.setText(allMoney);
//					mWallTiXian.setText(usefullMoney);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	private void initUI() {
//		mCoupon = (TextView) findViewById(R.id.pay_ifhavecoupon);
//		mCoupon.setOnClickListener(this);
		findViewById(R.id.pay_back).setOnClickListener(this);
		RelativeLayout mYuE_pay = (RelativeLayout) findViewById(R.id.qita_check);
		RelativeLayout mAli_pay = (RelativeLayout) findViewById(R.id.chongzhi_alipay);
		RelativeLayout mWeChat_pay = (RelativeLayout) findViewById(R.id.chongzhi_wechat);
		mYuE_pay.setOnClickListener(this);
		mAli_pay.setOnClickListener(this);
		mWeChat_pay.setOnClickListener(this);
		yue_img = (ImageView) findViewById(R.id.pay_radiobutton);
		ali_img = (ImageView) findViewById(R.id.img_alipay);
		weChat_img = (ImageView) findViewById(R.id.img_wechat);
		findViewById(R.id.chongzhi_btn).setOnClickListener(this);
		mTotal = (TextView) findViewById(R.id.pay_total);
		mQitaTotal = (TextView) findViewById(R.id.pay_qita);
		mTotal.setText(total);
		mQitaTotal.setText(total);
		mYuE = (TextView) findViewById(R.id.pay_remainmoney);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_back:
			finish();
			break;
		case R.id.chongzhi_btn:
			if(yue_img.isClickable()){
				WalletPay();
			}
			else if(ali_img.isClickable()){
				AliPay();
			}
			else if(weChat_img.isClickable()){
				WeChatPay();
			}else{
				Toast.makeText(PayActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.qita_check:
			yue_img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			ali_img.setImageResource(R.mipmap.fukuan_pitch_on);
			weChat_img.setImageResource(R.mipmap.fukuan_pitch_on);
			yue_img.setClickable(true);
			ali_img.setClickable(false);
			weChat_img.setClickable(false);
			break;
		case R.id.chongzhi_alipay:
			yue_img.setImageResource(R.mipmap.fukuan_pitch_on);
			ali_img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			weChat_img.setImageResource(R.mipmap.fukuan_pitch_on);
			yue_img.setClickable(false);
			ali_img.setClickable(true);
			weChat_img.setClickable(false);
			break;
		case R.id.chongzhi_wechat:
			yue_img.setImageResource(R.mipmap.fukuan_pitch_on);
			ali_img.setImageResource(R.mipmap.fukuan_pitch_on);
			weChat_img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			yue_img.setClickable(false);
			ali_img.setClickable(false);
			weChat_img.setClickable(true);
			break;
//		case R.id.pay_ifhavecoupon:
//			startActivity(new Intent(PayActivity.this, CouponsActivity.class));
//			break;

		default:
			break;
		}
		
	}
	//余额支付
	private void WalletPay(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.WalletPayUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("oids", oids);
		HTTPUtils.post(this, url, map, new VolleyListener(
				) {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				IsSuccess fromJson = gson.fromJson(arg0, IsSuccess.class);
				zsj.com.oyk255.example.ouyiku.orderjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Intent intent = new Intent(PayActivity.this, PaySuccessActivity.class);
					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					intent.putExtra("money", total);
					intent.putExtra("oids", oids);
					startActivity(intent);
					finish();
					
					
				}else{
					Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}
	Handler handler=new Handler();
//	private ZProgressHUD progressHUD;
	//微信支付参数获取
	private void WeChatPay() {
		String localIpAddress = NetWorkUtils.getLocalIpAddress(this);
		String url=Constant.URL.WeChatUrl;
		HashMap<String , String> map=new HashMap<String, String>();
		map.put("ip", localIpAddress);
		map.put("user_id", userid);
		map.put("oids", oids);
		map.put("total", total);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
		

			@Override
			public void onResponse(String arg0) {
					Gson gson = new Gson();
					WeChatPay fromJson = gson.fromJson(arg0, zsj.com.oyk255.example.ouyiku.huodong.WeChatPay.class);
					Status status = fromJson.getStatus();
					if(status.getSucceed().equals("1")){
						WeChatPayData data = fromJson.getData();
						String appid = data.getAppid();
						String noncestr = data.getNoncestr();
						String package1 = data.getPackage();
						String partnerid = data.getPartnerid();
						String prepayid = data.getPrepayid();
						String sign = data.getSign();
						Integer timestamp = data.getTimestamp();
						String valueOf = String.valueOf(timestamp);
						
						SharedPreferences sp = getSharedPreferences("WeChatPay", 0);
						 Editor edit = sp.edit();
						 edit.putString("WCoids", oids);
						 edit.putString("WCtotal", total);
						 edit.commit();
						
						api.registerApp(APP_ID);
						PayReq request = new PayReq();

						request.appId = APP_ID;

						request.partnerId = partnerid;

						request.prepayId= prepayid;

						request.packageValue = package1;

						request.nonceStr= noncestr;

						request.timeStamp= valueOf;

						request.sign= sign;

						api.sendReq(request);
						
						final ZProgressHUD progressHUD = ZProgressHUD.getInstance(PayActivity.this); 
					    	progressHUD.setMessage("加载中");
					    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
					    	progressHUD.show();
					    	
					    	handler.postDelayed(new Runnable() {
								
								@Override
								public void run() {
									
									progressHUD.dismiss();
									finish();
					
								}
							}, 2000);
//						if(api.sendReq(request)){
//							finish();
//						}
						
					}
					
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
				
		
	}
	//支付宝支付
	private void AliPay() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(PayActivity.this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.AliPayARGUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("oids", oids);
		map.put("type", "1");
		map.put("total", total);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				AliPayArg fromJson = gson.fromJson(arg0, AliPayArg.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					AliPayArgData data = fromJson.getData();
					String body = data.getBody();
					String inputCharset = data.getInputCharset();
					String notifyUrl = data.getNotifyUrl();
					String outTradeNo = data.getOutTradeNo();
					String partner = data.getPartner();
					String paymentType = data.getPaymentType();
					String sellerId = data.getSellerId();
					String service = data.getService();
//					String sign = data.getSign();
					String signType = data.getSignType();
					String subject = data.getSubject();
					String totalFee = data.getTotalFee();
					PARTNER=partner;
					SELLER=sellerId;
					String orderInfo = getOrderInfo(subject, body, totalFee, outTradeNo, notifyUrl, service);
					
					String sign = sign(orderInfo);
					try {
						/**
						 * 仅需对sign 做URL编码
						 */
						sign = URLEncoder.encode(sign, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					Log.e("sign", sign);
					
					final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
					
					Runnable payRunnable = new Runnable() {

						@Override
						public void run() {
							// 构造PayTask 对象
							PayTask alipay = new PayTask(PayActivity.this);
							// 调用支付接口，获取支付结果
							String result = alipay.pay(payInfo, true);

							Message msg = new Message();
							msg.what = SDK_PAY_FLAG;
							msg.obj = result;
							mHandler.sendMessage(msg);
						}
					};
					
					// 必须异步调用
					Thread payThread = new Thread(payRunnable);
					payThread.start();
					
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Intent intent = new Intent(PayActivity.this, PaySuccessActivity.class);
					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					intent.putExtra("money", total);
					intent.putExtra("oids", oids);
					startActivity(intent);
					finish();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};
	private TextView mYuE;
//	private TextView mCoupon;
	
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price,String out_trade_no,String notify_url,String service ) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}
	
		
		
	
	
	
	
	
	
	
	
}
