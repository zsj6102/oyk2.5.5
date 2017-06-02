package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.BrandInfo;
import zsj.com.oyk255.example.ouyiku.brandjson.BrandNews;
import zsj.com.oyk255.example.ouyiku.brandjson.BrandNewsDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.Data;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.homejson.Datum;
import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijian;
import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijianData;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.pullableview.PullableScrollView;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.HorizontalListView;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Brand_detailActivity extends OykActivity implements OnClickListener{

	private MyGridView mGrid;
//	private View mHeadview;
	private LinearLayout line_layout;
	Datum datum;
	private String merchant_id;//品牌id
	private ImageView mBackGround;
	private ImageView mLogo;
	private TextView mTitle;
	private TextView mGoodsNum;
	private ImageView mIsCollect;
	private int goodsNum;
	int screenWidth;
	String token;
	String userid;
	ArrayList<BrandNewsDatum> mBrandNewsData=new ArrayList<BrandNewsDatum>();
	private HorizontalListView brand_horizon_Listview;
	private BrandHorListAdapter brandHorListAdapter;
	ArrayList<HomeTuijianData> mAllData=new ArrayList<HomeTuijianData>();
	private BrandGridAdapter brandGridAdapter;
	private SharedPreferences sp;
	private PullableScrollView mScroll;
	private IWXAPI api;
	private Tencent mTencent;
	private static final String APP_ID= Constant.APPID.WXAPPID;
	private static final String APP_QQID=Constant.APPID.QQAPPID;
	private Share_pop share_pop;
	ArrayList<String> mBannerData=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_detail);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout)findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		merchant_id = intent.getStringExtra("mSevenShop1");
		screenWidth = ScreenUtils.getScreenWidth(this);
		initUI();
		initTopData();
		initNew();
		initdata();
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	private void ClickCollect(){
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String BrandCollectUrl=Constant.URL.DetailBrandCollectURL+"&user_id="+userid+"&brand_id="+merchant_id;
		HTTPUtils.get(this, BrandCollectUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					String BrandUrl=Constant.URL.BrandDetailURL;
					HashMap<String, String> params=new HashMap<String, String>();
					params.put("brand_id", merchant_id);
			//用户id
					params.put("user_id", userid);
					HTTPUtils.post(Brand_detailActivity.this, BrandUrl, params, new VolleyListener() {
						

						@Override
						public void onResponse(String arg0) {
							
							Gson gson = new Gson();
							
							BrandInfo fromJson = gson.fromJson(arg0, BrandInfo.class);
							Status status = fromJson.getStatus();
							String succeed = status.getSucceed();
							if(succeed.equals("1")){
								Data data = fromJson.getData();
								String background = data.getBackground();
								String isCollect = data.getIsCollect();
								String logo = data.getLogo();
								String number = data.getNumber();
								String title = data.getTitle();
								//转换为int
								goodsNum = Integer.valueOf(number);
								
								mTitle.setText(title);
								mGoodsNum.setText("在售商品（"+number+"）");
//								if(logo!=null){
//									UILUtils.displayImageNoAnim(logo, mLogo);
//								}
//								if(background!=null){
//									UILUtils.displayImageNoAnim(background, mBackGround);
//								}
								if(isCollect.equals("1")){
									mIsCollect.setImageResource(R.mipmap.collect_w_click);
								}else{
									mIsCollect.setImageResource(R.mipmap.collect_w);
								}
								
								
								
							}else if(succeed.equals("0")){
								ToastUtils.toast(Brand_detailActivity.this, "暂无数据");
							}
						}
						
						@Override
						public void onErrorResponse(VolleyError arg0) {
							
//							Toast.makeText(Brand_detailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
							
						}
					});
				}else{
					startActivity(new Intent(Brand_detailActivity.this, LoginActivity.class));
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initdata() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String BrandAllUrl=Constant.URL.BrandAllBrandURL+merchant_id;
		HTTPUtils.get(this, BrandAllUrl, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
				zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HomeTuijianData> data = fromJson.getData();
					mAllData.addAll(data);
					brandGridAdapter = new BrandGridAdapter();
					mGrid.setAdapter(brandGridAdapter);
					mScroll.smoothScrollTo(0, 0);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}


	private void initNew() {
		String 	BrandNewUrl=Constant.URL.BrandUnloginBrandURL+merchant_id;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, BrandNewUrl, new VolleyListener() {
			
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				BrandNews fromJson = gson.fromJson(arg0, BrandNews.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<BrandNewsDatum> data = fromJson.getData();
					mBrandNewsData.addAll(data);
					
					brandHorListAdapter = new BrandHorListAdapter();
					brand_horizon_Listview.setAdapter(brandHorListAdapter);
					mScroll.smoothScrollTo(0, 0);
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}
	private String link;//分享链接

	private String active_title;//小标题
	private String title;//品牌名称
	private String logo;//品牌Logo

	private void initTopData() {
		//品牌topview数据
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String BrandUrl=Constant.URL.BrandDetailURL;
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("brand_id", merchant_id);
//用户id
		params.put("user_id", userid);
		HTTPUtils.post(this, BrandUrl, params, new VolleyListener() {


			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				Gson gson = new Gson();
				progressHUD.dismiss();
				BrandInfo fromJson = gson.fromJson(arg0, BrandInfo.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					String background = data.getBackground();
					String isCollect = data.getIsCollect();
					logo = data.getLogo();
					String number = data.getNumber();
					title = data.getTitle();
					link = data.getLink();
					active_title = data.getActive_title();
					
					//转换为int
					goodsNum = Integer.valueOf(number);
					
					if(goodsNum==0){
						mPic_Null.setVisibility(View.VISIBLE);
						allView.setVisibility(View.GONE);
						brand_horizon_Listview.setVisibility(View.GONE);
					}else{
						mPic_Null.setVisibility(View.GONE);
						allView.setVisibility(View.VISIBLE);
						brand_horizon_Listview.setVisibility(View.VISIBLE);
					}
					mTitle.setText(title);
					mGoodsNum.setText("在售商品（"+number+"）");
					mBannerData.clear();
					mBannerData.add(logo);
					
//					if(logo!=null){
						UILUtils.displayImageNoAnim(logo, mLogo);
//						new Thread(new Runnable(){
//
//							
//
//							@Override
//				            public void run() {
//				            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(logo), 120, 120, true);
//				            }
//				        }).start();
//					}
					if(background!=null){
						UILUtils.displayImageNoAnim(background, mBackGround);
					}
					if(isCollect.equals("1")){
						mIsCollect.setImageResource(R.mipmap.collect_w_click);
					}else{
						mIsCollect.setImageResource(R.mipmap.collect_w);
					}
					mScroll.smoothScrollTo(0, 0);
					
					
				}else if(succeed.equals("0")){
					ToastUtils.toast(Brand_detailActivity.this, "暂无数据");
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		mPic_Null = (ImageView) findViewById(R.id.brand_detail_null);
		allView = (LinearLayout) findViewById(R.id.allgoods);
		
		mGrid= (MyGridView) findViewById(R.id.gridViewWithHeaderAndFooter1);
		mScroll = (PullableScrollView) findViewById(R.id.PullableScrollView);
		mScroll.smoothScrollTo(0, 0);
		brand_horizon_Listview = (HorizontalListView) findViewById(R.id.brand_horizontallistview);
		
		
		
		mBackGround = (ImageView) findViewById(R.id.brand_background);
		mLogo = (ImageView) findViewById(R.id.brand_logo);
		mTitle = (TextView) findViewById(R.id.branddetail_name);
		mGoodsNum = (TextView) findViewById(R.id.branddetail_goodsnum);
		mIsCollect = (ImageView) findViewById(R.id.branddetail_collect);
		mIsCollect.setOnClickListener(this);
		brand_horizon_Listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String productId = mBrandNewsData.get(position).getProductId();
				Intent intent = new Intent(Brand_detailActivity.this, DetailActivity.class);
				intent.putExtra("product_id", productId);
				startActivity(intent);
			}
		});
		mGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product_id = mAllData.get(position).getProduct_id();
				Intent intent = new Intent(Brand_detailActivity.this, DetailActivity.class);
				intent.putExtra("product_id", product_id);
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.branddetail_back).setOnClickListener(this);
		findViewById(R.id.branddetail_share).setOnClickListener(this);
		share_pop = new Share_pop(this);
		
		
	}
	
	
	
	
	class BrandGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mAllData.size();
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
			View layout =null;
			ViewHolder holder = null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.gridview_item, null);
				holder=new ViewHolder();
				holder.OldPrice = (TextView) layout.findViewById(R.id.gridview_oldprice);
				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.Imginfo = (ImageView) layout.findViewById(R.id.gridview_imginfo);
				 holder.Imginfo.getLayoutParams().height=(screenWidth-10)/2;
				holder.GoodsInfo = (TextView) layout.findViewById(R.id.gridview_tv_title);
				holder.NewPrice = (TextView) layout.findViewById(R.id.gridview_price);
				holder.Discount = (TextView) layout.findViewById(R.id.discount);
				layout.setTag(holder);
			}else{
				layout  = convertView;
				holder = (ViewHolder) layout.getTag();
			}
			HomeTuijianData homeTuijianData = mAllData.get(position);
			
			holder.OldPrice.setText("¥"+homeTuijianData.getOld_price());
			holder.NewPrice.setText("¥"+homeTuijianData.getNew_price());
			holder.GoodsInfo.setText(homeTuijianData.getTitle());
			holder.Discount.setText(homeTuijianData.getRebate()+"折");
			UILUtils.displayImageNoAnim(homeTuijianData.getPic_url(), holder.Imginfo);
			return layout;
		}
		
	}
	class BrandHorListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mBrandNewsData.size();
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
			View layout = getLayoutInflater().inflate(R.layout.branddetail_grid_item, null);
			ImageView mbrand_New_img = (ImageView) layout.findViewById(R.id.branddetail_new_img);
			TextView branddetail_new_tvname = (TextView) layout.findViewById(R.id.branddetail_new_tvname);
			layout.findViewById(R.id.view1).setVisibility(View.GONE);
			branddetail_new_tvname.setVisibility(View.GONE);
			UILUtils.displayImageNoAnim(mBrandNewsData.get(position).getPhone1(), mbrand_New_img);
			return layout;
		}
		
	}
	
	
	class ViewHolder
	{
		ImageView Imginfo;
		TextView OldPrice;
		TextView GoodsInfo;
		TextView NewPrice;
		TextView Discount;
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.branddetail_collect:
			ClickCollect();
			break;
		case R.id.branddetail_back:
			finish();
			break;
		case R.id.branddetail_share:
			share_pop.showAtLocation(findViewById(R.id.branddetaillayout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new Thread(new Runnable(){
						
						
										@Override
							            public void run() {
											api.registerApp(APP_ID);
											Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(logo), 120, 120, true);
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
							api.registerApp(APP_ID);
							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(logo), 120, 120, true);
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

		default:
			break;
		}
		
	}
	
	
	public SendMessageToWX.Req createReq(boolean timeLine,Bitmap thumb) {
		String ArticleUrl=link;
		String title2 = title;
		
    	WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = ArticleUrl;
		final WXMediaMessage msg = new WXMediaMessage(webpage);
//		String title = title2;
		msg.description = active_title;
		msg.title = title2;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
//		if(logo!=null){
//			UILUtils.displayImageNoAnim(logo, mLogo);
//			new Thread(new Runnable(){
//
//				
//
//				@Override
//	            public void run() {
//	            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(logo), 120, 120, true);
	            	msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//	            }
//	        }).start();
//		}
		
		
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
	bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, link);
	//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
	bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
	//分享的图片URL
	bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,logo);
	//分享的消息摘要，最长50个字
	bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, active_title);
	//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
	//标识该消息的来源应用，值为应用名称+AppId。
//	bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

	mTencent.shareToQQ(this, bundle , qqShareListener);
	}
	
//	 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
	 
	 //分享QQ空间
	 public void ShareQQZone(){
		 Bundle params = new Bundle();
//		 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//		 
		  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
		  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, link);
		  params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, active_title);
			//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_RL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//			bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//			//分享的图片URL
		  
		  params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mBannerData);
			doShareToQzone(params);
			
	 }
	
	IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
//            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//                Util.toastMessage(DetailActivity.this, "onCancel: ");
//                Toast.makeText(DetailActivity.this, "onCancel:", Toast.LENGTH_SHORT).show();
//            }
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
//            Util.toastMessage(DetailActivity.this, "onComplete: " + response.toString());
//            Toast.makeText(DetailActivity.this, "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
//            Util.toastMessage(DetailActivity.this, "onError: " + e.errorMessage, "e");
//            Toast.makeText(DetailActivity.this, "onError: " + e.errorMessage, Toast.LENGTH_SHORT).show();
        }
    };
	private ImageView mPic_Null;
	private LinearLayout allView;
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
                    mTencent.shareToQzone(Brand_detailActivity.this, params, qqShareListener);
                }
            }
        });
    }
	
	
	
	
	public class MyListener implements PullToRefreshLayout.OnRefreshListener
	{

		
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 下拉刷新操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					// 千万别忘了告诉控件刷新完毕了哦！
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 2000);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 加载操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					if(mAllData.size()==0){
						initdata();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mAllData.size();
						String product_id = mAllData.get(size-1).getProduct_id();
						final ZProgressHUD progressHUD = ZProgressHUD.getInstance(Brand_detailActivity.this); 
						progressHUD.setMessage("加载中");
				    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
				    	progressHUD.show();
						String BrandAllUrl=Constant.URL.BrandAllBrandURL+merchant_id+"&product_id="+product_id;
						HTTPUtils.get(Brand_detailActivity.this, BrandAllUrl, new VolleyListener() {

							@Override
							public void onResponse(String arg0) {
								progressHUD.dismiss();
								Gson gson = new Gson();
								HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
								zsj.com.oyk255.example.ouyiku.homejson.Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<HomeTuijianData> data = fromJson.getData();
									mAllData.addAll(data);
									brandGridAdapter.notifyDataSetChanged(); 
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								}else{
									
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
								}
								
							}
							
							@Override
							public void onErrorResponse(VolleyError arg0) {
								progressHUD.dismiss();
							}
						});
					}
				}
			}.sendEmptyMessageDelayed(0, 2000);
		}

	}
	
}
