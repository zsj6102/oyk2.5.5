package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetail;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetailData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.draglayout.DragLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PinDetailActivity extends FragmentActivity implements OnClickListener{

	private IWXAPI api;
	private static final String APP_ID= Constant.APPID.WXAPPID;
	InputMethodManager im;
	private PinDetailTopFragment pinDetailTopFragment;
	private PinDetailBottomFragment pinDetailBottomFragment;
	private String groupsId;
	private String photo;//分享用的图片
	private Share_pop share_pop;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private static final String APP_QQID=Constant.APPID.QQAPPID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_detail);
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		Intent intent = getIntent();
		groupsId = intent.getStringExtra("groupsId");
		photo = intent.getStringExtra("photo");
		initUI();
		initData();
	}
	
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	


	private String singlePrice;
	private String brandId;
	private String groupsPrice;
	private String iskaituan;
	private String tuanId;
	
	private void initData() {
		String url=Constant.URL.PinTuanDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groupsId);
		HTTPUtils.post(this, url, map, new VolleyListener() {



			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanDetail fromJson = gson.fromJson(arg0, PinTuanDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					PinTuanDetailData data = fromJson.getData();
					brandId = data.getBrandId();
					singlePrice = data.getSinglePrice();
					singleMoney.setText("¥"+singlePrice+"/每份");
					groupsPrice = data.getGroupsPrice();
					iskaituan = data.getIskaituan();
					tuanId = data.getTuanId();
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}




	private void initUI() {
		share_pop = new Share_pop(this);
		pinDetailTopFragment = new PinDetailTopFragment(groupsId);
		pinDetailBottomFragment = new PinDetailBottomFragment(groupsId);
		
		getSupportFragmentManager().beginTransaction()
		.add(R.id.first, pinDetailTopFragment).add(R.id.second, pinDetailBottomFragment)
		.commit();
		
		DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
			@Override
			public void onDragNext() {
				pinDetailBottomFragment.initView();
			}
		};
		DragLayout draglayout = (DragLayout) findViewById(R.id.draglayout);
		draglayout.setNextPageListener(nextIntf);
		mDelete = (ImageView) findViewById(R.id.delete_num);
		mAdd = (ImageView) findViewById(R.id.add_num);
		mNum = (TextView) findViewById(R.id.num);
		mDelete.setTag("+");
		mAdd.setTag("-");
		mDelete.setOnClickListener(new OnButtonClickListener());
		mAdd.setOnClickListener(new OnButtonClickListener());
		
		findViewById(R.id.pintuandetail_singlebuy).setOnClickListener(this);
		findViewById(R.id.pintuandetail_back).setOnClickListener(this);
		singleMoney = (TextView) findViewById(R.id.pintuandetail_singlemoney);
		mKaiTuan = (TextView) findViewById(R.id.pintuandetail_kaituan);
		mKaiTuan.setOnClickListener(this);
		
	}




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pintuandetail_singlebuy:
//			单独购买
			if(userid.equals("")){
				startActivity(new Intent(this, LoginActivity.class));
			}else{
				String num = mNum.getText().toString();
				Intent intent = new Intent(this, ConfirmOrderPinTuanActivity.class);
				intent.putExtra("groups_id", groupsId);
				intent.putExtra("pnum", num);
				intent.putExtra("brand_id", brandId);
				intent.putExtra("isSign", "yes");//是否单独偶买
				startActivity(intent);
				
			}
			break;
		case R.id.pintuandetail_back:
			finish();
			break;
		case R.id.pintuandetail_kaituan:
			if(userid.equals("")){
				startActivity(new Intent(this, LoginActivity.class));
			}else{
				
				if(iskaituan.equals("1")){
					Intent intent = new Intent(this, ConfirmOrderPinTuanActivity.class);
					intent.putExtra("groups_id", groupsId);
					intent.putExtra("tuanPrice", groupsPrice);
					intent.putExtra("brand_id", brandId);
					intent.putExtra("isSign", "no");
					intent.putExtra("tuanId", "kong");
					startActivity(intent);
				}else{
					Toast.makeText(this, "已有人开团,快加入他的团吧", Toast.LENGTH_LONG).show();
				}
				
			}
			break;

		default:
			break;
		}
		
	}
	
//	public SendMessageToWX.Req createReq(boolean timeLine) {
//		String ArticleUrl="http://ouyiku.com/specialselling/view?id="+product_id;
//		String title2 = data.getTitle();
//		
//    	WXWebpageObject webpage = new WXWebpageObject();
//		webpage.webpageUrl = ArticleUrl;
//		WXMediaMessage msg = new WXMediaMessage(webpage);
////		String title = title2;
//		msg.description = title2;
////		msg.title = title;
////		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
//		msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//		
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = buildTransaction("webpage");
//		req.message = msg;
////		req.scene = SendMessageToWX.Req.WXSceneSession;
//		req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//		return req;
//    }
//	
//	 private String buildTransaction(final String type) {
//			return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//		}
	
	
	/*//分享QQ
		public void share()
		{
		Bundle bundle = new Bundle();
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://ouyiku.com/specialselling/view?id="+product_id);
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, data.getTitle());
		//分享的图片URL
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,mBannerData.get(0));

		mTencent.shareToQQ(this, bundle , qqShareListener);
		}
		
		 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
		 
		 //分享QQ空间
		 public void ShareQQZone(){
			 Bundle params = new Bundle();
//			 
			  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
			  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
			  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://ouyiku.com/specialselling/view?id="+product_id);
			  
			  params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mBannerData);
				doShareToQzone(params);
				
		 }*/
		
		IUiListener qqShareListener = new IUiListener() {
	        @Override
	        public void onCancel() {
//	            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//	                Util.toastMessage(DetailActivity.this, "onCancel: ");
//	                Toast.makeText(DetailActivity.this, "onCancel:", Toast.LENGTH_SHORT).show();
//	            }
	        }
	        @Override
	        public void onComplete(Object response) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onComplete: " + response.toString());
//	            Toast.makeText(DetailActivity.this, "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
	        }
	        @Override
	        public void onError(UiError e) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onError: " + e.errorMessage, "e");
//	            Toast.makeText(DetailActivity.this, "onError: " + e.errorMessage, Toast.LENGTH_SHORT).show();
	        }
	    };
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
	    }
		
	    private void doShareToQzone(final Bundle params) {
	        // QZone分享要在主线程做
	        ThreadManager.getMainHandler().post(new Runnable() {

	            @Override
	            public void run() {
	                if (null != mTencent) {
	                    mTencent.shareToQzone(PinDetailActivity.this, params, qqShareListener);
	                }
	            }
	        });
	    }
	
	
	
	int num=1;
	private ImageView mDelete;
	private ImageView mAdd;
	private TextView mNum;
	private TextView singleMoney;
	private TextView mKaiTuan;
	private Tencent mTencent;
	//加减判断
		class OnButtonClickListener implements OnClickListener
		{

			@Override
			public void onClick(View v)
			{
				String numString = mNum.getText().toString();
				if (numString == null || numString.equals(""))
				{
					num = 0;
					mNum.setText("0");
				} else
				{
					if (v.getTag().equals("-"))
					{
						if (++num <= 0)  //先加，再判断
						{
							num--;
//							Toast.makeText(DetailActivity.this, "请输入一个大于0的数字",
//									Toast.LENGTH_SHORT).show();
						} else
						{
							mNum.setText(String.valueOf(num));
							
							
						}
					} else if (v.getTag().equals("+"))
					{
						if (--num <= 0)  //先减，再判断
						{
							num++;
//							Toast.makeText(DetailActivity.this, "请输入一个大于0的数字",
//									Toast.LENGTH_SHORT).show();
						} else
						{
							mNum.setText(String.valueOf(num));

						}
					}
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
