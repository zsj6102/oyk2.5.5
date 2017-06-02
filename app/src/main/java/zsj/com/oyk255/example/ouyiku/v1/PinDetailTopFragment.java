package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetail;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetailData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.CustomViewPager;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PinDetailTopFragment extends Fragment implements OnClickListener{

	private View view;
	int[] mImgRes=new int[]{R.mipmap.logo, R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	private String groupsId;
	ArrayList<String> mBannerData=new ArrayList<String>();
	private TextView mTitle;
	private TextView mNewPrice;
	private TextView mOldPrice;
	private TextView mHavePeople;
	private TextView mDaoJishi;
	private TextView mAttr;
	private CustomViewPager mLunBoPager;
	private ViewpageIndicator viewpageIndicator;
	private ImageView mBrandImg;
	private TextView mBrandTitle;
	private TextView mdetail_goodsnum;
	private TextView mdetail_colletesnum;
	private TextView mbuyPeople;
	private ImageView mCollImg;
	private String brandId;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private String buyNumber;
	
	public PinDetailTopFragment() {
		// Required empty public constructor
	}
	public PinDetailTopFragment(String groupsId) {
		this.groupsId=groupsId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_pin_detail_top, container,
					false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
		}
		
		return view;
	}
	
	@Override
	public void onStart() {
		sp = getActivity().getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}
	
	
	private String productId;
	private String tuanId;

	private void initColl(){
		String url= Constant.URL.PinTuanDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groupsId);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanDetail fromJson = gson.fromJson(arg0, PinTuanDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					PinTuanDetailData data = fromJson.getData();
					String ucolnum = data.getUcolnum();//商品是否收藏
					String isbcollect = data.getIsbcollect();//品牌是否收藏
					if(ucolnum.equals("1")){
						mGoodsColl.setImageResource(R.mipmap.sc_click);
					}else{
						mGoodsColl.setImageResource(R.mipmap.sc);
						
					}
					if(isbcollect.equals("1")){
						mCollImg.setImageResource(R.mipmap.collect_click);
					}else{
						mCollImg.setImageResource(R.mipmap.collect);
					}
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private String iskaituan;
	
	private void initData() {
		String url=Constant.URL.PinTuanDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groupsId);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {




			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				PinTuanDetail fromJson = gson.fromJson(arg0, PinTuanDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					PinTuanDetailData data = fromJson.getData();
					List<String> productImage = data.getProductImage();
					mBannerData.clear();
					mBannerData.addAll(productImage);
					String title = data.getTitle();
					String sku = data.getSku();
					String brandtitle = data.getBrandtitle();
					String singlePrice = data.getSinglePrice();
					String groupsPrice = data.getGroupsPrice();
					String logo = data.getLogo();
					brandId = data.getBrandId();
					String brandsalenum = data.getBrandsalenum();
					String brandtotal = data.getBrandtotal();
					buyNumber = data.getBuyNumber();
					String collectnum = data.getCollectnum();
					String isbcollect = data.getIsbcollect();
					String ucolnum = data.getUcolnum();//商品是否收藏
					String tuancount = data.getTuancount();
					String endtime1 = data.getEndtime1();
					productId = data.getProductId();
					tuanId = data.getTuanId();
					iskaituan = data.getIskaituan();
					
					
					recLen = Integer.valueOf(endtime1);
					
					 timer.schedule(task, 1000, 1000);
					 
					if(ucolnum.equals("1")){
						mGoodsColl.setImageResource(R.mipmap.sc_click);
					}else{
						mGoodsColl.setImageResource(R.mipmap.sc);
						
					}
					
					mTitle.setText(title);
					mNewPrice.setText("¥"+groupsPrice);
					mOldPrice.setText("¥"+singlePrice);
					mAttr.setText(sku);
					UILUtils.displayImageNoAnim(logo, mBrandImg);
					mBrandTitle.setText(brandtitle);
					mdetail_goodsnum.setText(brandtotal);
					mdetail_colletesnum.setText(collectnum);
					mbuyPeople.setText(brandsalenum);
					if(isbcollect.equals("1")){
						mCollImg.setImageResource(R.mipmap.collect_click);
					}else{
						mCollImg.setImageResource(R.mipmap.collect);
					}
					mHavePeople.setText("已"+tuancount+"人参加");
					
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
		private int recLen ;    
		Timer timer = new Timer();
    
		TimerTask task = new TimerTask() {  
	        @Override  
	        public void run() {  
	 
	            getActivity().runOnUiThread(new Runnable() {      // UI thread  
	                @Override  
	                public void run() {  
	                    recLen--;  
//	                    String secToTime = secToTime(recLen);
	                    String formatSecond = formatSecond(recLen);
	                    
	                    mDaoJishi.setText(formatSecond);  
	                    if(recLen < 0){  
	                        timer.cancel();  
	                    }  
	                }  
	            });  
	        }  
	    };
		private ImageView mGoodsColl;
	    
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
	    
	    //秒数转换为天时分秒
	    public String formatSecond(int second){
	        String  timeStr = "0秒";
	        if(second!=0){
//	            Double s=(Double) second;
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
	    
	    
	    
	 
	
	private void initUI() {
		mLunBoPager = (CustomViewPager) view.findViewById(R.id.pintuandetail_lunbo);
		viewpageIndicator = (ViewpageIndicator) view.findViewById(R.id.pintuandetailviewpageIndicator2);
		mTitle = (TextView) view.findViewById(R.id.pintuandetail_name);
		mNewPrice = (TextView) view.findViewById(R.id.pintuandetail_newprice);
		mOldPrice = (TextView) view.findViewById(R.id.pintuandetail_oldprice);
		mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		mHavePeople = (TextView) view.findViewById(R.id.pintuandetail_people);
		mDaoJishi = (TextView) view.findViewById(R.id.pintuandetail_time);
		mAttr = (TextView) view.findViewById(R.id.pintuandetail_attr);
		
		mBrandImg = (ImageView) view.findViewById(R.id.detail_brand_img);
		mBrandTitle = (TextView) view.findViewById(R.id.detail_brandname);
		mdetail_goodsnum = (TextView) view.findViewById(R.id.detail_goodsnum);
		mdetail_colletesnum = (TextView) view.findViewById(R.id.detail_colletesnum);
		mbuyPeople = (TextView) view.findViewById(R.id.detail_haoping);
		mCollImg = (ImageView) view.findViewById(R.id.detail_collect);
		view.findViewById(R.id.detail_collectview).setOnClickListener(this);
		view.findViewById(R.id.detailstore).setOnClickListener(this);
		mGoodsColl = (ImageView) view.findViewById(R.id.pintuandetail_collect);
		mGoodsColl.setOnClickListener(this);
		view.findViewById(R.id.pintuandetail_cantuan).setOnClickListener(this);
		
		
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
			PinDetailBannerFragment bannerItemFragment = new PinDetailBannerFragment(index, mImgRes[index]);
			return bannerItemFragment;
		}
		else{
			String bannerDatum = mBannerData.get(index);
			PinDetailBannerFragment bannerItemFragment2 = new PinDetailBannerFragment(index, bannerDatum,buyNumber);
			return bannerItemFragment2;
		}
	}

	@Override
	public int getCount() {
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
	String BrandCollectUrl=Constant.URL.DetailBrandCollectURL+"&user_id="+userid+"&brand_id="+brandId;
	HTTPUtils.get(getActivity(), BrandCollectUrl, new VolleyListener() {
		
		@Override
		public void onResponse(String arg0) {
			Log.e("pinpai", arg0);
			progressHUD.dismiss();
			Gson gson = new Gson();
			IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
			zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
			String succeed = status.getSucceed();
			if(succeed.equals("1")){
				initColl();
				//TODO 不能直接刷新initData  等接口完善在修改
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

//收藏商品
//TODO 接口有问题
	private void ClickCollectBaby(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
		progressHUD.setMessage("加载中");
		progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
		progressHUD.show();
		String BrandCollectUrl=Constant.URL.PinTuanGoodsCollUrl+"&user_id="+userid+"&groups_id="+groupsId;
		HTTPUtils.get(getActivity(), BrandCollectUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
//				initColl();
				progressHUD.dismiss();
				Gson gson = new Gson();
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				zsj.com.oyk255.example.ouyiku.detailjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					initColl();
					//TODO 不能直接刷新initData  等接口完善在修改
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
	case R.id.detail_collectview://品牌收藏
		if(userid.equals("")){
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}else{
			ClickCollect();
			
		}
		break;
	case R.id.pintuandetail_collect:
		if(userid.equals("")){
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}else{
			ClickCollectBaby();
		}
		break;
	case R.id.pintuandetail_cantuan:
		if(userid.equals("")){
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}else{
			if(tuanId!=null){
				Intent intent = new Intent(getActivity(), CanTuanDetailActivity.class);
				intent.putExtra("tuanId", tuanId);
				intent.putExtra("brandId", brandId);
				intent.putExtra("isOrder", "no");//是否是从我的拼团过来的
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "暂无人开团", Toast.LENGTH_LONG).show();
			}
		}
		
		
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
	

}
