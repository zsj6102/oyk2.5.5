package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;

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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.HomeHD;
import zsj.com.oyk255.example.ouyiku.brandjson.HomeHDData;
import zsj.com.oyk255.example.ouyiku.brandjson.IsShare;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.HorizontalProgressBarWithNumber;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class HomeHuoDongActivity extends OykActivity {
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private IWXAPI api;
	
	private static final String APP_ID=Constant.APPID.WXAPPID;
	private static final String APP_QQID= Constant.APPID.QQAPPID;
	ArrayList<String> mPicData=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_huo_dong);
		mContext = getApplicationContext();
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		
		
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
	}

	private String url2;
	private WebView mWebView;
	private HorizontalProgressBarWithNumber mBar;
	private Context mContext;
	private Tencent mTencent;
	
	private void initData() {
		String url=Constant.URL.HomeHuoDongUrl;
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				HomeHD fromJson = gson.fromJson(arg0, HomeHD.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					HomeHDData data = fromJson.getData();
					url2 = data.getUrl();
					mWebView.addJavascriptInterface(new JavaScriptObject(mContext), "app");
					mWebView.setWebViewClient(new webViewClient()); 
					mWebView.loadUrl(url2);
					mWebView.setWebChromeClient(new WebChromeClient() {

				          @Override
				           public void onProgressChanged(WebView view, int newProgress) {
				               if (newProgress == 100) {
				            	   mBar.setVisibility(View.GONE);
				               } else {
				                   if (View.INVISIBLE == mBar.getVisibility()) {
				                	   mBar.setVisibility(View.VISIBLE);
				                   }
				                   mBar.setProgress(newProgress);
				               }
				               super.onProgressChanged(view, newProgress);
				           }
				           
				       });
					
					
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	private void initUI() {
		mWebView = (WebView) findViewById(R.id.huodongwebview);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mBar = (HorizontalProgressBarWithNumber) findViewById(R.id.huodongProgressBar);
		share_pop = new Share_pop(this);
		
		
	}
	
	  class webViewClient extends WebViewClient{ 

	       //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 

	    @Override 

	    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

	        view.loadUrl(url); 

	        //如果不需要其他对点击链接事件的处理返回true，否则返回false 

	        return true; 

	    } 

	        

	   } 
	
	  private String id;
	  private String gid;
	  private String title;
	  private String imageUrl;
	  private String shareUrl;
	
	public class JavaScriptObject {
		
	    Context mContxt;


	    public JavaScriptObject(Context mContxt)
	    {
	        this.mContxt = mContxt;
	    }
	    
	    /** 与js交互时用到的方法，在js里直接调用的 */
	    @JavascriptInterface
		public void back() {
	    	finish();
	    	
		}
	    
	    /** 与js交互时用到的方法，在js里直接调用的 */
	    @JavascriptInterface
		public void shareDic(String url) {

	    	
			String[] split = url.split(",");
			id = split[0];
			gid = split[1];
			title = split[2];
			imageUrl = split[3];
			shareUrl = split[4];
			//子线程执行获取分享要用的图片
//			new Thread(new Runnable(){
//
//
//				@Override
//	            public void run() {
//	            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(imageUrl), 120, 120, true);
//	            }
//	        }).start();
			mPicData.add(imageUrl);
			
			
			String Checkurl=Constant.URL.CheckShareUrl;
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("user_id", userid);
			map.put("token", token);
			map.put("id", id);
			HTTPUtils.post(HomeHuoDongActivity.this, Checkurl, map, new VolleyListener() {
				
				@Override
				public void onResponse(String arg0) {
					Gson gson = new Gson();
					IsShare fromJson = gson.fromJson(arg0, IsShare.class);
					Status status = fromJson.getStatus();
					if(status.getSucceed().equals("1")){
						//分享
						share_pop.showAtLocation(findViewById(R.id.homehuodonglayout), Gravity.CENTER, 0, 0);
						share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								new Thread(new Runnable(){
									
										@Override
								            public void run() {
											Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(imageUrl), 120, 120, true);
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
										Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(imageUrl), 120, 120, true);
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
						
						
					}else{
						ToastUtils.toast(HomeHuoDongActivity.this, "您已分享过该红包");
					}
					
				}
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
				
	    	
		}
	    
	    
	}
	
	public SendMessageToWX.Req createReq(boolean timeLine,Bitmap thumb) {
		String ArticleUrl=shareUrl;
		String title2 = title;
		
    	WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = ArticleUrl;
		final WXMediaMessage msg = new WXMediaMessage(webpage);
//		String title = title2;
//		msg.description = title2;
		msg.title = title2;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
//		new Thread(new Runnable(){
//
//
//			@Override
//            public void run() {
//            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(imageUrl), 120, 120, true);
            	msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//            }
//        }).start();
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
//		req.scene = SendMessageToWX.Req.WXSceneSession;
		req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		ShareSuccess();
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
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		//分享的图片URL
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,mPicData.get(0));
		//分享的消息摘要，最长50个字
//		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//		bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
//		bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

		mTencent.shareToQQ(this, bundle , qqShareListener);
		ShareSuccess();
		}
		
//		 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
		 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
		 
		 //分享QQ空间
		 public void ShareQQZone(){
			 Bundle params = new Bundle();
//			 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//			 
			  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
			  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
			  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
				//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//				bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//				//分享的图片URL
			  
			  params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mPicData);
				doShareToQzone(params);
				ShareSuccess();
		 }
		
		IUiListener qqShareListener = new IUiListener() {
	        @Override
	        public void onCancel() {
//	            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//	                Util.toastMessage(DetailActivity.this, "onCancel: ");
//	                Toast.makeText(HomeHuoDongActivity.this, "onCancel:", Toast.LENGTH_SHORT).show();
//	            }
	        }
	        @Override
	        public void onComplete(Object response) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onComplete: " + response.toString());
//	            Toast.makeText(DetailActivity.this, "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
//	        	 Toast.makeText(HomeHuoDongActivity.this, "onComplete:", Toast.LENGTH_SHORT).show();
	        }
	        @Override
	        public void onError(UiError e) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onError: " + e.errorMessage, "e");
//	            Toast.makeText(HomeHuoDongActivity.this, "onError: ", Toast.LENGTH_SHORT).show();
	        }
	    };
		private Share_pop share_pop;
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
	                    mTencent.shareToQzone(HomeHuoDongActivity.this, params, qqShareListener);
	                }
	            }
	        });
	    }
	
	
	private void ShareSuccess(){
		String url=Constant.URL.ShareSuccessUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("id", id);
		map.put("gid", gid);
		map.put("user_id", userid);
		map.put("token", token);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	
	

}
