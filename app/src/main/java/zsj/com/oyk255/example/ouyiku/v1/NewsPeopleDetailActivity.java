package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.draglayout.DragLayout;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetail;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetailData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class NewsPeopleDetailActivity extends FragmentActivity implements IWXAPIEventHandler,OnClickListener{

	private DetailTopFragment detailTopFragment;
	private DetailBottomFragment detailBottomFragment;
	InputMethodManager im;
	private String productId;
	private String phone1;

	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;

	private IWXAPI api;
	private static final String APP_ID=Constant.APPID.WXAPPID;
	private static final String APP_QQID= Constant.APPID.QQAPPID;
	private Share_pop share_pop;
	ArrayList<String> mBannerData=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_people_detail);
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
	    mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());


		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		Intent intent = getIntent();
		productId = intent.getStringExtra("productId");
		phone1 = intent.getStringExtra("phone1");
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		mBannerData.add(phone1);
		initUI();
		GetIsBuy();//获取数据
		//子线程执行获取分享要用的图片

	}

	private String isbuy;
	private String shareUrl;
	private String fproId;
	private String dtitle;
	private Tencent mTencent;

	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	private void GetIsBuy() {
		String url=Constant.URL.TimeDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("product_id", productId);
		map.put("user_id", userid);
		HTTPUtils.post(this, url, map, new VolleyListener() {





			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				TimeSaleDetail fromJson = gson.fromJson(arg0, TimeSaleDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					TimeSaleDetailData data = fromJson.getData();
					isbuy = data.getIsbuy();
					shareUrl = data.getShareUrl();
					fproId = data.getFproId();
					dtitle = data.getTitle();
					Log.e("shareUrl", shareUrl);
					Log.e("productId", productId);
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});

	}


	private void initUI() {
		detailTopFragment = new DetailTopFragment(productId);
		detailBottomFragment = new DetailBottomFragment(productId);

		getSupportFragmentManager().beginTransaction()
		.add(R.id.first, detailTopFragment).add(R.id.second, detailBottomFragment)
		.commit();

		DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
			@Override
			public void onDragNext() {
				detailBottomFragment.initView();
			}
		};
		DragLayout draglayout = (DragLayout) findViewById(R.id.draglayout);
		draglayout.setNextPageListener(nextIntf);

		findViewById(R.id.ninedetail_back).setOnClickListener(this);
		findViewById(R.id.ninedetail_buy).setOnClickListener(this);
		findViewById(R.id.ninedetail_share).setOnClickListener(this);
		share_pop = new Share_pop(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ninedetail_back:
			finish();
			break;
		case R.id.ninedetail_share:

			//分享
			share_pop.showAtLocation(findViewById(R.id.newpeoplelayout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new Thread(new Runnable(){

						@Override
			            public void run() {
							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(phone1), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(false,thumb));
			            }
			        }).start();

				}
			});

			share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new Thread(new Runnable(){

						@Override
			            public void run() {
							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(phone1), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(true,thumb));
			            }
			        }).start();

				}
			});

			share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					share();

				}
			});

			share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ShareQQZone();

				}
			});


			break;
		case R.id.ninedetail_buy:
			if(!userid.equals("")){
//				if(isbuy.equals("0")){
//					Toast.makeText(NewsPeopleDetailActivity.this, "已购买过此商品", Toast.LENGTH_SHORT).show();
//				} else if(isbuy.equals("2")){
//					Toast.makeText(NewsPeopleDetailActivity.this, "没有库存了", Toast.LENGTH_SHORT).show();
//				} else if(isbuy.equals("1")){
					Intent intent = new Intent(NewsPeopleDetailActivity.this, ConfirmOrder9and0Activity.class);
					intent.putExtra("productId", productId);
					startActivity(intent);
//				} else if(isbuy.equals("3")){
//					Toast.makeText(NewsPeopleDetailActivity.this, "活动未开始", Toast.LENGTH_SHORT).show();
//				}
//				else  if(isbuy.equals("4")){
//					Toast.makeText(NewsPeopleDetailActivity.this, "活动已结束", Toast.LENGTH_SHORT).show();
//				}

			}else{
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;

		default:
			break;
		}

	}

public SendMessageToWX.Req createReq(boolean timeLine,Bitmap thumb) {

    	WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = shareUrl;
		final WXMediaMessage msg = new WXMediaMessage(webpage);
		String title = dtitle;
//		msg.description = talentContent;
		msg.title = title;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
//		new Thread(new Runnable(){
//
//			@Override
//            public void run() {
//				thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(phone1), 120, 120, true);
				msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//            }
//        }).start();

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		return req;
    }

	 private String buildTransaction(final String type) {
			return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
		}


	//分享QQ
		public void share()
		{
		Bundle bundle = new Bundle();
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, dtitle);
		//分享的图片URL
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,phone1);
		//分享的消息摘要，最长50个字
//		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//		bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
//		bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

		mTencent.shareToQQ(this, bundle , qqShareListener);
		}

//		 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
		 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

		 //分享QQ空间
		 public void ShareQQZone(){
			 Bundle params = new Bundle();
//			 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//			 
			  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
			  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, dtitle);
			  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
				//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//				bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//				//分享的图片URL

			  params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mBannerData);
				doShareToQzone(params);

		 }

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
	                    mTencent.shareToQzone(NewsPeopleDetailActivity.this, params, qqShareListener);
	                }
	            }
	        });
	    }


	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {
		String result;
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = "分享成功";
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = "取消分享";
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = "分享被拒绝";
				break;
			default:
				result = "发送返回";
				break;
		}
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();


	}
}
