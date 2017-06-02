package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.fragment.DetailGoodsinfoFragment;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetail;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetailData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.CustomViewPager;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

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

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailTopFragment extends Fragment implements OnClickListener{

	private View view;
	int[] mImgRes=new int[]{R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	private ViewpageIndicator viewpageIndicator;
	ArrayList<String> mBannerData=new ArrayList<String>();
	String productId;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private TextView mTitle;
	private TextView detail_newprice;
	private TextView detail_oldprice;
	private TextView timedetail_attr;
	private CustomViewPager mLunBoPager;
	private ImageView mBrandImg;
	private TextView mbrandName;
	private ImageView mCollImg;
	private TextView detail_goodsnum;
	private TextView detail_colletesnum;
	private TextView detail_buynum;
	private IWXAPI api;
	private static final String APP_ID= Constant.APPID.WXAPPID;
	private static final String APP_QQID=Constant.APPID.QQAPPID;
	private Tencent mTencent;
	String shareurl="http://m.ouyiku.com/index.php?m=home&c=newbuy&a=detail&id=";
	private String ShareTitle0="疯了，真的是0元还免运费，欧衣库免费商品送到家！";
	private String ShareTitle1="豪气冲天！只要1元还免运费，欧衣库疯狂抢购！";
	
	public DetailTopFragment(String productId) {
		this.productId=productId;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_detail_top, container, false);
			api = WXAPIFactory.createWXAPI(getActivity(), APP_ID, false);
			api.registerApp(APP_ID);
		    mTencent = Tencent.createInstance(APP_QQID, getActivity().getApplicationContext());
		    
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
		}
		return view;
	}

	private String brandId;
	private ImageView mShareTop;
	private String sharetitle;
	private String sharepic;
	private Share_pop share_pop;
	private String currprice;
	
	private void initData() {
		String url=Constant.URL.TimeDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("product_id", productId);
		map.put("user_id", userid);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			




			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				Gson gson = new Gson();
				TimeSaleDetail fromJson = gson.fromJson(arg0, TimeSaleDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					TimeSaleDetailData data = fromJson.getData();
					List<String> productImage = data.getProductImage();
					mBannerData.addAll(productImage);
					sharepic = mBannerData.get(0);
					sharetitle = data.getTitle();
					
					
					String attr = data.getAttr();
					currprice = data.getCurrprice();
					String marketprice = data.getMarketprice();
					brandId = data.getBrandId();
					String brandtitle = data.getBrandtitle();
					String brandtotal = data.getBrandtotal();
					String brandsalenum = data.getBrandsalenum();
					String isbcollect = data.getIsbcollect();
					String collectnum = data.getCollectnum();
					String logo = data.getLogo();
					
					mTitle.setText(sharetitle);
					detail_newprice.setText("¥"+currprice);
					detail_oldprice.setText("¥"+marketprice);
					timedetail_attr.setText(attr);
					
					UILUtils.displayImageNoAnim(logo, mBrandImg);
					mbrandName.setText(brandtitle);
					detail_goodsnum.setText(brandtotal);
					detail_colletesnum.setText(collectnum);
					detail_buynum.setText(brandsalenum);
					if(isbcollect.equals("1")){
						mCollImg.setImageResource(R.mipmap.collect_click);
					}else{
						mCollImg.setImageResource(R.mipmap.collect);
					}
					
					
					FragmentManager fragmentManager = getFragmentManager();
					mLunBoPager.setOnPageChangeListener(new ViewPageChangedListener());
					mLunBoPager.setAdapter(new ViewPageAdapter(fragmentManager));
				}
				
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initUI() {
		mLunBoPager = (CustomViewPager) view.findViewById(R.id.detail_lunbo);
		viewpageIndicator = (ViewpageIndicator) view.findViewById(R.id.viewpageIndicator2);
		
		   
			
			mTitle = (TextView) view.findViewById(R.id.detai_name);
			mShareTop = (ImageView) view.findViewById(R.id.share_topview);
			mShareTop.setOnClickListener(this);
			detail_newprice = (TextView) view.findViewById(R.id.detail_newprice);
			detail_oldprice = (TextView) view.findViewById(R.id.detail_oldprice);
			detail_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			timedetail_attr = (TextView) view.findViewById(R.id.timedetail_attr);
			mBrandImg = (ImageView) view.findViewById(R.id.detail_brand_img);
			mbrandName = (TextView) view.findViewById(R.id.detail_brandname);
			mCollImg = (ImageView) view.findViewById(R.id.detail_collect);
			detail_goodsnum = (TextView) view.findViewById(R.id.detail_goodsnum);
			detail_colletesnum = (TextView) view.findViewById(R.id.detail_colletesnum);
			detail_buynum = (TextView) view.findViewById(R.id.detail_haoping);
			
			
			view.findViewById(R.id.detail_collectview).setOnClickListener(this);
			view.findViewById(R.id.detailstore).setOnClickListener(this);
			share_pop = new Share_pop(getActivity());
			
			
		
	}
	
class ViewPageChangedListener implements OnPageChangeListener{
		

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			
			if(mBannerData.size()==0){
				position%=mImgRes.length;
				viewpageIndicator.move(position, arg1,4);
			}else{
				position%=mBannerData.size();
				viewpageIndicator.move(position, arg1,mBannerData.size());
				
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
		
	}

class ViewPageAdapter extends FragmentPagerAdapter{

	public ViewPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		int index= position %mImgRes.length;
		if(mBannerData.size()==0){
			DetailGoodsinfoFragment bannerItemFragment = new DetailGoodsinfoFragment(index, mImgRes[index]);
			return bannerItemFragment;
		}
		else{
			String bannerDatum = mBannerData.get(index);
			DetailGoodsinfoFragment bannerItemFragment2 = new DetailGoodsinfoFragment(index, bannerDatum);
			return bannerItemFragment2;
		}
	}

	@Override
	public int getCount() {
		//TODO
		if(mBannerData.size()==0){
			return 4;
		}else{
			return mBannerData.size();
			
		}
	}
	
}
			
//收藏品牌
private void ClickCollect(){
	final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
	progressHUD.setMessage("加载中");
	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
	progressHUD.show();
	sp = getActivity().getSharedPreferences("userdata", 0);
	userid = sp.getString("userid", "");
	String BrandCollectUrl=Constant.URL.DetailBrandCollectURL+"&user_id="+userid+"&brand_id="+brandId;
	HTTPUtils.get(getActivity(), BrandCollectUrl, new VolleyListener() {
		
		@Override
		public void onResponse(String arg0) {
			
			progressHUD.dismiss();
			Gson gson = new Gson();
			IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
			zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
			String succeed = status.getSucceed();
			if(succeed.equals("1")){
				initData();
				
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
		}
		
		@Override
		public void onErrorResponse(VolleyError arg0) {
			progressHUD.dismiss();
		}
	});
}


		
		

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.detail_collectview:
		ClickCollect();
		break;
	case R.id.share_topview:
		//分享
		share_pop.showAtLocation(view.findViewById(R.id.newpeoplelayout), Gravity.CENTER, 0, 0);
		share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable(){

					@Override
			        public void run() {
						Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharepic), 120, 120, true);
						api.registerApp(APP_ID);
						if(currprice.equals("0.00")){
							api.sendReq(createReq(false,thumb,ShareTitle0));
							
						}else{
							api.sendReq(createReq(false,thumb,ShareTitle1));
							
						}
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
						Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharepic), 120, 120, true);
						api.registerApp(APP_ID);
						if(currprice.equals("0.00")){
							api.sendReq(createReq(true,thumb,ShareTitle0));
							
						}else{
							api.sendReq(createReq(true,thumb,ShareTitle1));
							
						}
			        }
			    }).start();
				
			}
		});
		
		share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currprice.equals("0.00")){
					share(ShareTitle0);
					
				}else{
					share(ShareTitle1);
					
				}
				
			}
		});
		
		share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currprice.equals("0.00")){
					ShareQQZone(ShareTitle0);
					
				}else{
					ShareQQZone(ShareTitle1);
					
				}
			}
		});
		break;
	case R.id.detailstore:
		//跳转品牌
		Intent intent2 = new Intent(getActivity(), Brand_detailActivity.class);
		intent2.putExtra("mSevenShop1", brandId);
		startActivity(intent2);
		break;

	default:
		break;
	}
	
}

public SendMessageToWX.Req createReq(boolean timeLine,Bitmap thumb,String shareTitle) {
	WXWebpageObject webpage = new WXWebpageObject();
	webpage.webpageUrl = shareurl+productId;
	final WXMediaMessage msg = new WXMediaMessage(webpage);
//	String des = shareContent;
//	String title = title;
//	if(!timeLine) {
//		title = listDatum.getName();
//		msg.description = des;
//	} 
	msg.title = shareTitle;
//	Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.logo120);
//	new Thread(new Runnable(){
//
//		@Override
//        public void run() {
//			thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharepic), 120, 120, true);
			msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//        }
//    }).start();
	
	SendMessageToWX.Req req = new SendMessageToWX.Req();
	req.transaction = buildTransaction("webpage");
	req.message = msg;
//	req.scene = SendMessageToWX.Req.WXSceneSession;
	req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
	return req;
}

 private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
 
//分享QQ
	public void share(String title)
	{
	Bundle bundle = new Bundle();
	//这条分享消息被好友点击后的跳转URL。
	bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareurl+productId);
	//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
	bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
	//分享的图片URL
	bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,sharepic);
	//分享的消息摘要，最长50个字
//	bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent);
	//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
	//标识该消息的来源应用，值为应用名称+AppId。
//	bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

	mTencent.shareToQQ(getActivity(), bundle , qqShareListener);
	}
	
//	 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
	 
	 //分享QQ空间
	 public void ShareQQZone(String title){
		 Bundle params = new Bundle();
//		 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//		 
		  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
		  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
		  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareurl+productId);
			//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//		  params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent);
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
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
    }
	
    private void doShareToQzone(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQzone(getActivity(), params, qqShareListener);
                }
            }
        });
    }
 





















}
