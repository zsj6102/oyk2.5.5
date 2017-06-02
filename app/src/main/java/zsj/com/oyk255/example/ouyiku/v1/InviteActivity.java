package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.invitejson.Data;
import zsj.com.oyk255.example.ouyiku.invitejson.Invite;
import zsj.com.oyk255.example.ouyiku.invitejson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class InviteActivity extends OykActivity implements OnClickListener{
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private TextView mInviteCord;
	private ImageView mImg;
	private Share_pop share_pop;
	private IWXAPI api;
	ArrayList<String> mBannerData=new ArrayList<String>();
	
	private static final String APP_ID=Constant.APPID.WXAPPID;
	private static final String APP_QQID= Constant.APPID.QQAPPID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_main);
		PushAgent.getInstance(this).onAppStart();
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
	}
	private String icode;
	private String photo;
	private String shareTitle;
	private String shareUrl;
	private Tencent mTencent;

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.InviteURL+"&user_id="+userid+"&token="+token;
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				Invite fromJson = gson.fromJson(arg0, Invite.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					icode = data.getIcode();
					photo = data.getPhoto();
					shareTitle = data.getShareTitle();
					shareUrl = data.getShareUrl();
					String image = data.getImage();
					mBannerData.clear();
					mBannerData.add(photo);
					UILUtils.displayImageNoAnim(image, mImg);
					mInviteCord.setText(icode);
				}
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		findViewById(R.id.invite_back).setOnClickListener(this);
		findViewById(R.id.invite_record).setOnClickListener(this);
		mInviteCord = (TextView) findViewById(R.id.invite_yaoqingma);
		mImg = (ImageView) findViewById(R.id.invite_erweima);
		findViewById(R.id.invite_copy).setOnClickListener(this);
		findViewById(R.id.invite_share).setOnClickListener(this);
		share_pop = new Share_pop(this);
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invite_back:
			finish();
			break;
		case R.id.invite_share:
			//分享
			share_pop.showAtLocation(findViewById(R.id.invitelayout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					api.registerApp(APP_ID);
					api.sendReq(createReq(false));
					
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					api.registerApp(APP_ID);
					api.sendReq(createReq(true));
					
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
		case R.id.invite_copy:
			String text = mInviteCord.getText().toString();
			ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			clip.setText(text);
			Toast.makeText(InviteActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
			break;
		case R.id.invite_record:
			startActivity(new Intent(this, Invite_recordActivity.class));
			break;
		default:
			break;
		}
		
	}
	//微信分享
	public SendMessageToWX.Req createReq(boolean timeLine) {
		
    	WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = shareUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		String title = "欧衣库注册邀请码:"+icode;
		msg.description = shareTitle;
		msg.title = title;
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao3);
		msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
//		req.scene = SendMessageToWX.Req.WXSceneSession;
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
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "欧衣库注册邀请码:"+icode);
		//分享的图片URL
		
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,photo);
		
		//分享的消息摘要，最长50个字
		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareTitle);
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//		bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
//		bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

		mTencent.shareToQQ(this, bundle , qqShareListener);
		}
		
		 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
		 
		 
		 //分享QQ空间
		 public void ShareQQZone(){
			 Bundle params = new Bundle();
//			 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//			 
			  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
			  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "欧衣库注册邀请码:"+icode);
			  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
				//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
			  params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareTitle);
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
	                    mTencent.shareToQzone(InviteActivity.this, params, qqShareListener);
	                }
	            }
	        });
	    }
	 
	 
	 
	 
}
