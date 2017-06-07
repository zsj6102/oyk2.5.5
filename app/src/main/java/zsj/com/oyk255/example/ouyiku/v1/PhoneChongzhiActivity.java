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
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.Nine0Nine;
import zsj.com.oyk255.example.ouyiku.brandjson.Nine0NineDatum;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.detailjson.DataNetshare;
import zsj.com.oyk255.example.ouyiku.detailjson.NetShare;
import zsj.com.oyk255.example.ouyiku.detailjson.NineBanner;
import zsj.com.oyk255.example.ouyiku.detailjson.NineBannerDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.pullableview.PullableScrollView;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PhoneChongzhiActivity extends OykActivity implements OnClickListener{

	private MyGridView mGridview;
	private ImageView mTopView;
	private PullableScrollView mScroll;
	ArrayList<NineBannerDatum> mBannerData=new ArrayList<NineBannerDatum>();
	ArrayList<Nine0NineDatum> mListData=new ArrayList<Nine0NineDatum>();
	private Share_pop share_pop;
	private Tencent mTencent;
	private IWXAPI api;
	private static final String APP_ID = Constant.APPID.WXAPPID;
	private static final String APP_QQID = Constant.APPID.QQAPPID;
	private String sharePicUrl;
	private String share_url;
	private String share_title;
	private String content;
	//1元包邮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_chongzhi);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout) findViewById(R.id.refresh_view1))
		.setOnRefreshListener(new MyListener());
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);

		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		initUI();
		initList();
		initShare();
		initBanner();
	}
	private void initShare(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
		progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
		progressHUD.show();
		String netshareUrl = Constant.URL.NineShare;
		HTTPUtils.get(this, netshareUrl, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				DataNetshare fromJson = gson.fromJson(arg0, DataNetshare.class);
				NetShare data = fromJson.getNetShare();
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if (succeed.equals("1")) {

					sharePicUrl = data.getShare_img();
					share_url=data.getShare_url();
					share_title=data.getShare_title();
					content =data.getContent();
//                    String url = data.getShare_url();

				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}
	private void initBanner() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.NineBannerURL;
		HTTPUtils.get(this, url, new VolleyListener() {

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				NineBanner fromJson = gson.fromJson(arg0, NineBanner.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<NineBannerDatum> data = fromJson.getData();
					mBannerData.clear();
					mBannerData.addAll(data);
					String url2 = mBannerData.get(0).getUrl();
					UILUtils.displayImageNoAnim(url2, mTopView);
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();

			}
		});

	}

	private NineAdapter nineAdapter;
	private ImageView mImgNull;

	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.NineListURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("type", "1");
		map.put("num", "0");
		HTTPUtils.post(this, url, map, new VolleyListener() {


			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				Nine0Nine fromJson = gson.fromJson(arg0, Nine0Nine.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<Nine0NineDatum> data = fromJson.getData();
					mListData.addAll(data);

					if(mListData.size()==0){
						mImgNull.setVisibility(View.VISIBLE);
					}else{
						mImgNull.setVisibility(View.GONE);
						nineAdapter = new NineAdapter();
						mGridview.setAdapter(nineAdapter);

					}

				}
				Log.e("arg0", arg0);

			}

			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();

			}
		});

	}

	private void initUI() {
		mScroll = (PullableScrollView) findViewById(R.id.nine_scrollView);
		mScroll.smoothScrollTo(0, 0);
		findViewById(R.id.share_text).setOnClickListener(this);
		findViewById(R.id.nine_back).setOnClickListener(this);
		mGridview = (MyGridView) findViewById(R.id.nine_gridview);
		mTopView = (ImageView) findViewById(R.id.nine_img);
		mImgNull = (ImageView) findViewById(R.id.nineimg_null);
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String stock = mListData.get(position).getStock();
				if(!stock.equals("0")){
					Intent intent = new Intent(PhoneChongzhiActivity.this, NewsPeopleDetailActivity.class);
					String productId = mListData.get(position).getProductId();
					String phone1 = mListData.get(position).getPhone1();
					intent.putExtra("productId", productId);
					intent.putExtra("phone1", phone1);
					startActivity(intent);
				}else{
					Toast.makeText(PhoneChongzhiActivity.this, "已被抢光啦", Toast.LENGTH_SHORT).show();
				}


			}
		});
		share_pop = new Share_pop(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nine_back:
			finish();
			break;
			case R.id.share_text:
			share_pop.showAtLocation(findViewById(R.id.LinearLayout2), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					new Thread(new Runnable() {

						@Override
						public void run() {

							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(false, thumb));



						}
					}).start();


				}
			});

			share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new Thread(new Runnable() {

						@Override
						public void run() {

							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap( sharePicUrl), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(false, thumb));

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
	public SendMessageToWX.Req createReq(boolean timeLine, Bitmap thumb) {
//        String ArticleUrl = "http://m.ouyiku.com/?c=good&a=info&id=" + product_id;
//        String title2 = data.getTitle();

		WXWebpageObject webpage = new WXWebpageObject();

		webpage.webpageUrl = share_url;
		final WXMediaMessage msg = new WXMediaMessage(webpage);
//		String title = title2;
//		msg.description = title2;
		msg.title = share_title;
		msg.description = content;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
		//子线程执行获取分享要用的图片
//		new Thread(new Runnable(){
//
//			@Override
//            public void run() {
//            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
		msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//            }
//        }).start();

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
	public void share() {
		Bundle bundle = new Bundle();
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_url);
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE,  share_title);
		//分享的图片URL
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, sharePicUrl);
		//分享的消息摘要，最长50个字
//	bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
//	bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

		mTencent.shareToQQ(this, bundle, qqShareListener);
	}

	//	 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

	//分享QQ空间
	public void ShareQQZone() {
		Bundle params = new Bundle();
//		 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE,  share_title);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  share_url);
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//			bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//			//分享的图片URL

//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, content);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
	}

	private void doShareToQzone(final Bundle params) {
		// QZone分享要在主线程做
		ThreadManager.getMainHandler().post(new Runnable() {

			@Override
			public void run() {
				if (null != mTencent) {
					mTencent.shareToQzone(PhoneChongzhiActivity.this, params, qqShareListener);
				}
			}
		});
	}

	class NineAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size();
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
			View view = null;
			ViewHolder holder;
			if(convertView==null){
				view=getLayoutInflater().inflate(R.layout.nine_gridview_item, null);
				holder=new ViewHolder();
				holder.Imginfo = (AspectRatioImageView) view.findViewById(R.id.nineitem_img);
				holder.OldPrice = (TextView) view.findViewById(R.id.nineitem_oldprice);
				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.NewPrice = (TextView) view.findViewById(R.id.nineitem_price);
				holder.GoodsInfo = (TextView) view.findViewById(R.id.nineitem_tv_title);
				holder.Discount = (TextView) view.findViewById(R.id.nineitem_discount);
				view.setTag(holder);
			}else{
				view=convertView;
				holder= (ViewHolder) view.getTag();
			}
			Nine0NineDatum nine0NineDatum = mListData.get(position);
			String marketprice = nine0NineDatum.getMarketprice();//市场价格
			String currprice = nine0NineDatum.getCurrprice();//9.9
			String title = nine0NineDatum.getTitle();
			String phone1 = nine0NineDatum.getPhone1();//图片
			String productId = nine0NineDatum.getProductId();//商品id
			String rebate = nine0NineDatum.getRebate();

			holder.OldPrice.setText("￥"+marketprice);
			holder.NewPrice.setText("￥"+currprice);
			holder.GoodsInfo.setText(title);
			UILUtils.displayImageNoAnim(phone1, holder.Imginfo);
//			计算折扣
//	        DecimalFormat df = new DecimalFormat("0.00");
//			float i = Float.parseFloat(marketprice);
//			
//			float i2 = Float.parseFloat(currprice);
//			float f=i2/i;
//			float  b   =  (float)(Math.round(f*100))/100;
//	        float b2=b/10;
//	        
//	        String valueOf = String.valueOf(df.format((b2 * 100)));

			holder.Discount.setText(rebate+"折");
			return view;
		}

	}

	class ViewHolder
	{
		AspectRatioImageView Imginfo;
		TextView OldPrice;
		TextView GoodsInfo;
		TextView NewPrice;
		TextView Discount;

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
			}.sendEmptyMessageDelayed(0, 3000);
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
					if(mListData.size()==0){
						initList();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mListData.size();

						String url=Constant.URL.NineListURL;
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("type", "1");
						map.put("num", size+"");
						HTTPUtils.post(PhoneChongzhiActivity.this, url, map, new VolleyListener() {


							@Override
							public void onResponse(String arg0) {
								Log.e("加载操作", arg0);
								Gson gson = new Gson();
								Nine0Nine fromJson = gson.fromJson(arg0, Nine0Nine.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<Nine0NineDatum> data = fromJson.getData();
									mListData.addAll(data);
									nineAdapter.notifyDataSetChanged();

									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								}
								else{
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);

								}

							}

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub

							}
						});
					}


				}
			}.sendEmptyMessageDelayed(0, 3000);
		}

	}

}
