package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanTuiJian;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanTuiJianDatum;
import zsj.com.oyk255.example.ouyiku.collectjson.CanTuanDetail;
import zsj.com.oyk255.example.ouyiku.collectjson.CanTuanDetailData;
import zsj.com.oyk255.example.ouyiku.collectjson.Product;
import zsj.com.oyk255.example.ouyiku.collectjson.Status;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.pullableview.PullableScrollView;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.CircleImageView;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class CanTuanDetailActivity extends OykActivity implements OnClickListener{

	private ScrollViewWithListView mListView;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private String tuanId;
	private String brandId;
	private String isOrder;
	private TextView mShengYUtIME;
	ArrayList<String> mHeadData=new ArrayList<String>();
	ArrayList<PinTuanTuiJianDatum> mTuiJianData=new ArrayList<PinTuanTuiJianDatum>();
	private IWXAPI api;
	private Tencent mTencent;
	private static final String APP_ID=Constant.APPID.WXAPPID;
	private static final String APP_QQID= Constant.APPID.QQAPPID;
	ArrayList<String> mBannerData=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_can_tuan_detail);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		tuanId = intent.getStringExtra("tuanId");
		brandId = intent.getStringExtra("brandId");
		isOrder = intent.getStringExtra("isOrder");
		initUI();
		initData();
		initTuiJian();
	}
	private mListViewAdapter mListViewAdapter;
	
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	private void initTuiJian() {
		String url=Constant.URL.PinTuanTuijianUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("num", "0");
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanTuiJian fromJson = gson.fromJson(arg0, PinTuanTuiJian.class);
				zsj.com.oyk255.example.ouyiku.brandjson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<PinTuanTuiJianDatum> data = fromJson.getData();
					mTuiJianData.clear();
					mTuiJianData.addAll(data);
					mListViewAdapter = new mListViewAdapter();
					
					mListView.setAdapter(mListViewAdapter);
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	private int peopleINT;
	private String groupsPrice;
	private String groupsId;
	private String link;
	private String title;
	private String photo;
	private String tcode;

	private void initData() {
		String url=Constant.URL.CanTuanDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("tuan_id", tuanId);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			


			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				CanTuanDetail fromJson = gson.fromJson(arg0, CanTuanDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					CanTuanDetailData data = fromJson.getData();
					Product product = data.getProduct();
					title = product.getTitle();
					photo = product.getPhoto();
					groupsPrice = product.getGroupsPrice();
					String endmin = data.getEndmin();
					String buyNumber = product.getBuyNumber();
					recLen = Integer.valueOf(endmin);
					peopleINT = Integer.valueOf(buyNumber);
					List<String> user = data.getUser();
					link = data.getLink();//分享链接
					tcode = data.getTcode();
					
					mHeadData.clear();
					mHeadData.addAll(user);
					mBannerData.clear();
					mBannerData.add(photo);
					//子线程执行获取分享要用的图片
//					new Thread(new Runnable(){
//
//						
//
//						@Override
//			            public void run() {
//			            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
//			            }
//			        }).start();
					
					mGridView.setAdapter(new mGridViewAdapter());
					
					 timer.schedule(task, 1000, 1000);
					 mTitle.setText(title);
					 UILUtils.displayImageNoAnim(photo, mGoodsPic);
					 mPrice.setText("¥"+groupsPrice);
					 mPeople.setText(buyNumber+"人团:");
					 
					 
					 if(mHeadData.size()==peopleINT){
						 mCha.setText("拼团成功!");
						 mDaojishiView.setVisibility(View.GONE);
						 cantuan_bottom.setVisibility(View.GONE);
					 }else{
						int cha= peopleINT-mHeadData.size();
						 mCha.setVisibility(View.VISIBLE);
						 mCha.setText("还差"+cha+"人，期待您的加入");
						 mDaojishiView.setVisibility(View.VISIBLE);
						 
						 
							if(isOrder.equals("yes")){
						bottom_right.setImageResource(R.mipmap.yaoqingpt);
						pin_tv.setVisibility(View.VISIBLE);
						pin_code.setVisibility(View.VISIBLE);
						pin_code.setText(tcode);
						bottom_left.setVisibility(View.GONE);
					}else{
						bottom_right.setImageResource(R.mipmap.liji_buy);
						pin_tv.setVisibility(View.GONE);
						pin_code.setVisibility(View.GONE);
						bottom_left.setVisibility(View.VISIBLE);
					}
						 
					 }
					 if(mHeadData.get(0)!=null){
						 String imageUrls = mHeadData.get(0);
						 UILUtils.displayImageNoAnim(imageUrls, mTuanPic);
						 
					 }
					 
					 String startTime = data.getStartTime();
					 mBeginTime.setText(startTime);
					 
					 groupsId = product.getGroupsId();
					 
					 
					 
					 mScroll.smoothScrollTo(0, 0);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
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
//                    String secToTime = secToTime(recLen);
                    String formatSecond = formatSecond(recLen);
                    mShengYUtIME.setText(formatSecond);  
                    if(recLen < 0){  
                        timer.cancel();  
                    }  
                }  
            });  
        }  
    };
    
    //秒数转换为天时分秒
    public String formatSecond(int second){
        String  timeStr = "0秒";
        if(second!=0){
//            Double s=(Double) second;
        	Integer s = Integer.valueOf(second);
            String format;
            Object[] array;
            Integer days = (int)(s /(60 * 60 * 24));
            Integer hours =(int)(s/(60*60) - days * 24);
            Integer minutes = (int)(s/60 - hours*60 - days * 24 * 60);
            Integer seconds = (int)(s-minutes*60-hours*60*60 - days * 24 * 60 * 60);
            if(days>0){
                format="%1$,d天%2$,d时%3$,d分%4$,d秒";
                array=new Object[]{days,hours,minutes,seconds};
            } else if(hours>0){
                format="%1$,d时%2$,d分%3$,d秒";
                array=new Object[]{hours,minutes,seconds};
            }else if(minutes>0){
                format="%1$,d分%2$,d秒";
                array=new Object[]{minutes,seconds};
            }else{
                format="%1$,d秒";
                array=new Object[]{seconds};
            }
            timeStr = String.format(format, array);
        }
        return timeStr ;
    }
    
	private ImageView mGoodsPic;
	private TextView mTitle;
	private TextView mPeople;
	private TextView mPrice;
	private TextView mCha;
	private MyGridView mGridView;
	private PullableScrollView mScroll;
	private CircleImageView mTuanPic;
	private TextView mBeginTime;
	private LinearLayout mDaojishiView;
	private LinearLayout cantuan_bottom;
	private Share_pop share_pop;

	private void initUI() {
		mScroll = (PullableScrollView) findViewById(R.id.cantuan_scroll);
		mScroll.smoothScrollTo(0, 0);
		mGridView = (MyGridView) findViewById(R.id.cantuan_gridview);
		
		
		mListView = (ScrollViewWithListView) findViewById(R.id.cantuan_listview);
		findViewById(R.id.cantuan_back).setOnClickListener(this);
		LinearLayout mToDetail = (LinearLayout) findViewById(R.id.todetail);
		mToDetail.setOnClickListener(this);
		mShengYUtIME = (TextView) findViewById(R.id.cantuan_time);
		
		mGoodsPic = (ImageView) findViewById(R.id.cantuan_goodsimg);
		mTitle = (TextView) findViewById(R.id.cantuan_goodsname);
		mPeople = (TextView) findViewById(R.id.cantuan_people);
		mPrice = (TextView) findViewById(R.id.cantuan_goodsprice);
		mCha = (TextView) findViewById(R.id.cantuan_cha);
		
		mTuanPic = (CircleImageView) findViewById(R.id.cantuan_tuanzhang_pic);
		mBeginTime = (TextView) findViewById(R.id.cantuan_year);
		bottom_left = (ImageView) findViewById(R.id.cantuan_more);
		bottom_left.setOnClickListener(this);
		bottom_right = (ImageView) findViewById(R.id.cantuan_join);
		bottom_right.setOnClickListener(this);
		mDaojishiView = (LinearLayout) findViewById(R.id.cantuan_daojishi);
		cantuan_bottom = (LinearLayout) findViewById(R.id.cantuan_bottom);
		pin_tv = (TextView) findViewById(R.id.pin_tv);
		pin_code = (TextView) findViewById(R.id.pin_code);
		
	
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 PinTuanTuiJianDatum pinTuanTuiJianDatum = mTuiJianData.get(position);
				String groupsId = pinTuanTuiJianDatum.getGroupsId();
				String photo = pinTuanTuiJianDatum.getPhoto();
				Intent intent = new Intent(CanTuanDetailActivity.this, PinDetailActivity.class);
				intent.putExtra("groupsId", groupsId);
				intent.putExtra("photo", photo);
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.cantuandetail_share).setOnClickListener(this);
		share_pop = new Share_pop(this);
	}

	class mGridViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if(mHeadData.size()==peopleINT){
				return peopleINT;
			}else{
				return mHeadData.size()+1;
			}
			
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder holder;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.item_cantuan_headimg, null);
				holder=new ViewHolder();
				holder.item_cantuan_img = (CircleImageView) layout.findViewById(R.id.item_cantuan_img);
				holder.item_cantuan_name = (TextView) layout.findViewById(R.id.item_cantuan_name);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
			if(position==0){
				holder.item_cantuan_name.setText("团长");
				String headurl = mHeadData.get(position);
				UILUtils.displayImageNoAnim(headurl, holder.item_cantuan_img);
			}else if(position<mHeadData.size()){
				
					holder.item_cantuan_name.setText("团员");
					String headurl = mHeadData.get(position);
					UILUtils.displayImageNoAnim(headurl, holder.item_cantuan_img);
					
			}else{
					holder.item_cantuan_name.setText("虚位以待");
					holder.item_cantuan_img.setImageResource(R.mipmap.xw);
				}
			return layout;
		}
		
	}
	
	class ViewHolder{
		CircleImageView item_cantuan_img;
		TextView item_cantuan_name;
	}
	
	class mListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTuiJianData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder2 holder;
			if(convertView==null){
				layout =getLayoutInflater().inflate(R.layout.item_pintuan, null);
				holder=new ViewHolder2();
				holder.pintuan_item_img = (ImageView) layout.findViewById(R.id.pintuan_item_img);
				holder.pintuan_item_people = (TextView) layout.findViewById(R.id.pintuan_item_people);
				holder.pintuan_item_title = (TextView) layout.findViewById(R.id.pintuan_item_title);
				holder.pintuan_item_price = (TextView) layout.findViewById(R.id.pintuan_item_price);
				holder.pintuan_item_oldprice = (TextView) layout.findViewById(R.id.pintuan_item_oldprice);
				holder.pintuan_item_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.pintuan_item_btn = (TextView) layout.findViewById(R.id.pintuan_item_btn);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder2) layout.getTag();
			}
				PinTuanTuiJianDatum pinTuanTuiJianDatum = mTuiJianData.get(position);
				String buyNumber = pinTuanTuiJianDatum.getBuyNumber();
				
				String groupsId = pinTuanTuiJianDatum.getGroupsId();
				String groupsPrice = pinTuanTuiJianDatum.getGroupsPrice();
				String photo = pinTuanTuiJianDatum.getPhoto();
				String singlePrice = pinTuanTuiJianDatum.getSinglePrice();
				String title = pinTuanTuiJianDatum.getTitle();
				
				UILUtils.displayImageNoAnim(photo, holder.pintuan_item_img);
				holder.pintuan_item_people.setText("("+buyNumber+"人团)");
				holder.pintuan_item_title.setText(title);
				holder.pintuan_item_price.setText("￥"+groupsPrice);
				holder.pintuan_item_oldprice.setText("单卖价： ￥"+singlePrice);
				
			
			return layout;
		}
		
	}

	class ViewHolder2{
		ImageView pintuan_item_img;
		TextView pintuan_item_people;
		TextView pintuan_item_title;
		TextView pintuan_item_price;
		TextView pintuan_item_oldprice;
		TextView pintuan_item_btn;
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.todetail:
			Intent intent = new Intent(this, PinDetailActivity.class);
			intent.putExtra("groupsId", groupsId);
			intent.putExtra("photo", photo);
			startActivity(intent);
			break;
		case R.id.cantuan_back:
			finish();
			break;
		case R.id.cantuan_join:
			if(userid.equals("")){
				startActivity(new Intent(this, LoginActivity.class));
			}else{
				
				if(isOrder.equals("yes")){
					//邀请拼团
					share_pop.showAtLocation(findViewById(R.id.cantuanlayout), Gravity.CENTER, 0, 0);
					share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							new Thread(new Runnable(){


								@Override
					            public void run() {
					            	Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
					            	api.registerApp(APP_ID);
					            	api.sendReq(createReq(false,"拼团码:"+tcode+"；填写拼团码，寻找最优质拼团；快来一起，拼、拼、拼",thumb));
					            	
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
					            	Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
					            	api.registerApp(APP_ID);
					            	api.sendReq(createReq(true,"拼团码:"+tcode+"；填写拼团码，寻找最优质拼团；快来一起，拼、拼、拼",thumb));
					            	
					            }
					        }).start();
							
							api.registerApp(APP_ID);
							
						}
					});
					share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							share("拼团码:"+tcode+"；填写拼团码，寻找最优质拼团；快来一起，拼、拼、拼");
							
						}
					});
					
					share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ShareQQZone("拼团码:"+tcode+"；填写拼团码，寻找最优质拼团；快来一起，拼、拼、拼");
							
						}
					});
					
				}else{
					Intent intent2 = new Intent(this, ConfirmOrderPinTuanActivity.class);
					intent2.putExtra("groups_id", groupsId);
					intent2.putExtra("tuanPrice", groupsPrice);
					intent2.putExtra("brand_id", brandId);
					intent2.putExtra("isSign", "no");
					intent2.putExtra("tuanId", tuanId);
					startActivity(intent2);
					
				}
				
			}
			break;
		case R.id.cantuan_more:
			Intent intent2 = new Intent(CanTuanDetailActivity.this,PinTuanActivity.class);  
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            
            startActivity(intent2);  
            finish();  
			break;
		case R.id.cantuandetail_share:
			//分享
			share_pop.showAtLocation(findViewById(R.id.cantuanlayout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new Thread(new Runnable(){


						@Override
			            public void run() {
			            	Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
			            	api.registerApp(APP_ID);
			            	api.sendReq(createReq(false,title,thumb));
			            	
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
			            	Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
			            	api.registerApp(APP_ID);
			            	api.sendReq(createReq(true,title,thumb));
			            	
			            }
			        }).start();
				}
			});
			share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					share(title);
					
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ShareQQZone(title);
					
				}
			});
			break;

		default:
			break;
		}
		
	}
	
	public SendMessageToWX.Req createReq(boolean timeLine,String title,Bitmap thumb) {
		String ArticleUrl=link;
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
//            	Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(photo), 120, 120, true);
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
	public void share(String title)
	{
	Bundle bundle = new Bundle();
	//这条分享消息被好友点击后的跳转URL。
	bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, link);
	//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
	bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
	//分享的图片URL
	bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,photo);
	//分享的消息摘要，最长50个字
//	bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
	//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
	//标识该消息的来源应用，值为应用名称+AppId。
//	bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

	mTencent.shareToQQ(this, bundle , qqShareListener);
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
		  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, link);
			//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
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
	private ImageView bottom_right;
	private TextView pin_tv;
	private TextView pin_code;
	private ImageView bottom_left;
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
                    mTencent.shareToQzone(CanTuanDetailActivity.this, params, qqShareListener);
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
					if(mTuiJianData.size()==0){
						initTuiJian();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						String url=Constant.URL.PinTuanTuijianUrl;
						HashMap<String, String> map=new HashMap<String, String>();
						int size = mTuiJianData.size();
						
						map.put("num", ""+size);
						HTTPUtils.post(CanTuanDetailActivity.this, url, map, new VolleyListener() {
							

							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								PinTuanTuiJian fromJson = gson.fromJson(arg0, PinTuanTuiJian.class);
								zsj.com.oyk255.example.ouyiku.brandjson.Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<PinTuanTuiJianDatum> data = fromJson.getData();
									mTuiJianData.addAll(data);
									mListViewAdapter.notifyDataSetChanged();
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								}else{
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
			}.sendEmptyMessageDelayed(0, 2000);
		}

	}
	
}
