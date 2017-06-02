package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;
//import zsj.com.oyk255.example.ouyiku.homejson.BannerDatum;
//import zsj.com.oyk255.example.ouyiku.homejson.Datum;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeBanner;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeBrandTuiJia;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeBrandTuiJiaDatum;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeThree;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijian;
//import zsj.com.oyk255.example.ouyiku.homejson.HomeTuijianData;
//import zsj.com.oyk255.example.ouyiku.homejson.SevenShop;
//import zsj.com.oyk255.example.ouyiku.homejson.Status;
//import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
//import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout.OnRefreshListener;
//import zsj.com.oyk255.example.ouyiku.utils.Constant;
//import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
//import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
//import zsj.com.oyk255.example.ouyiku.v1.CouponsActivity;
//import zsj.com.oyk255.example.ouyiku.v1.DetailActivity;
//import zsj.com.oyk255.example.ouyiku.v1.HomeHuoDongActivity;
//import zsj.com.oyk255.example.ouyiku.v1.HotSaleActivity;
//import zsj.com.oyk255.example.ouyiku.v1.LoginActivity;
//import zsj.com.oyk255.example.ouyiku.v1.NewGoodsActivity;
//import zsj.com.oyk255.example.ouyiku.v1.NewsPeopleDetailActivity;
//import zsj.com.oyk255.example.ouyiku.v1.PhoneChongzhiActivity;
//import zsj.com.oyk255.example.ouyiku.v1.PinTuanActivity;
//import zsj.com.oyk255.example.ouyiku.v1.R;
//import zsj.com.oyk255.example.ouyiku.v1.RedBagActivity;
//import zsj.com.oyk255.example.ouyiku.v1.SeaShopActivity;
//import zsj.com.oyk255.example.ouyiku.v1.SearchActivity;
//import zsj.com.oyk255.example.ouyiku.v1.SignActivity;
//import zsj.com.oyk255.example.ouyiku.v1.TimeSaleActivity;
//import zsj.com.oyk255.example.ouyiku.v1.TopActivity;
//import zsj.com.oyk255.example.ouyiku.v1.TryuseActivity;
//import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
//import zsj.com.oyk255.example.ouyiku.view.MyGridView;
//import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
//import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
//import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import com.google.gson.Gson;
//import com.suiyuchen.HTTPUtils;
//import com.suiyuchen.UILUtils;
//import com.suiyuchen.VolleyListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.BannerDatum;
import zsj.com.oyk255.example.ouyiku.homejson.Datum;
import zsj.com.oyk255.example.ouyiku.homejson.HomeBanner;
import zsj.com.oyk255.example.ouyiku.homejson.HomeBrandTuiJia;
import zsj.com.oyk255.example.ouyiku.homejson.HomeBrandTuiJiaDatum;
import zsj.com.oyk255.example.ouyiku.homejson.HomeThree;
import zsj.com.oyk255.example.ouyiku.homejson.SevenShop;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
import zsj.com.oyk255.example.ouyiku.v1.HomeHuoDongActivity;
import zsj.com.oyk255.example.ouyiku.v1.HotSaleActivity;
import zsj.com.oyk255.example.ouyiku.v1.LoginActivity;
import zsj.com.oyk255.example.ouyiku.v1.NewGoodsActivity;
import zsj.com.oyk255.example.ouyiku.v1.PhoneChongzhiActivity;
import zsj.com.oyk255.example.ouyiku.v1.PinTuanActivity;
import zsj.com.oyk255.example.ouyiku.v1.RedBagActivity;
import zsj.com.oyk255.example.ouyiku.v1.SeaShopActivity;
import zsj.com.oyk255.example.ouyiku.v1.SearchActivity;
import zsj.com.oyk255.example.ouyiku.v1.SignActivity;
import zsj.com.oyk255.example.ouyiku.v1.TimeSaleActivity;
import zsj.com.oyk255.example.ouyiku.v1.TopActivity;
import zsj.com.oyk255.example.ouyiku.v1.TryuseActivity;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment implements OnClickListener{

	private View rootview;
	private ScrollViewWithListView mListView;
	int[] mImgRes=new int[]{R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	// 当前是否是用户手动拖拽状态
	public boolean mDragging;
	private ViewPager pager;
	private ViewpageIndicator viewpageIndicator;
	private TextView mSearch_edit;
	private ImageView mStoreImg1;
	private ImageView mStoreImg2;
	private ImageView mStoreImg3;
	private ImageView mStoreImg4;
	private ImageView mStoreImg5;
//	private ImageView mStoreImg6;
//	private ImageView mStoreImg7;
	private String picUrl1;
	private String picUrl2;
	private String picUrl3;
	private String picUrl4;
	private String picUrl5;
//	private String picUrl6;
//	private String picUrl7;
	ArrayList<BannerDatum> mBannerData =new ArrayList<BannerDatum>();
	ArrayList<Datum> mSevenShop=new ArrayList<Datum>();
	ArrayList<String> mThreeData=new ArrayList<String>();
	
	ArrayList<HomeBrandTuiJiaDatum> mHomeTuijiandata=new ArrayList<HomeBrandTuiJiaDatum>();
	private ScrollView mscroll;
	private MyGridview myGridview;
	private int screenWidth;
	private ImageView xianshimiaosha;
	private ImageView zerobuy;
	private ImageView ninebuy;
	private ImageView redbag;
	private ImageView pingtuan;
	private ImageView pinhongbao;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootview==null){
			rootview = inflater.inflate(R.layout.fragment_home, container, false);
			//上拉加载下拉刷新
			((PullToRefreshLayout) rootview.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			screenWidth = ScreenUtils.getScreenWidth(getActivity());
			initUI();
			initBanner();
			initData();
			initTuiJian();
			initThreeImg();
			
			
		}
		return rootview;
	}
	
	@Override
	public void onStart() {
		sp = getActivity().getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	
	
	private void initThreeImg() {
		String url= Constant.URL.HomeThreePicUrl;
		
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				HomeThree fromJson = gson.fromJson(arg0, HomeThree.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<String> data = fromJson.getData();
					mThreeData.clear();
					mThreeData.addAll(data);
					String pic1 = mThreeData.get(0);
					String pic2 = mThreeData.get(1);
					String pic3 = mThreeData.get(2);
					String pic4 = mThreeData.get(3);
					String pic5 = mThreeData.get(4);
					String pic6 = mThreeData.get(5);
					UILUtils.displayImageNoAnim(pic1, xianshimiaosha);
					UILUtils.displayImageNoAnim(pic2, ninebuy);
					UILUtils.displayImageNoAnim(pic3, zerobuy);
					UILUtils.displayImageNoAnim(pic4, redbag);
					UILUtils.displayImageNoAnim(pic5, pingtuan);
					UILUtils.displayImageNoAnim(pic6, pinhongbao);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initTuiJian() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		//商品推荐
				String HomeTuijianUrl=Constant.URL.HomeBrandUrl;
//				HashMap<String, String> TuijianMap=new HashMap<String, String>();
				HTTPUtils.post(getActivity(), HomeTuijianUrl, null, new VolleyListener() {
					
					

					@Override
					public void onResponse(String arg0) {
						progressHUD.dismiss();
						Gson gson = new Gson();
						HomeBrandTuiJia fromJson = gson.fromJson(arg0, HomeBrandTuiJia.class);
						Status status = fromJson.getStatus();
						String succeed = status.getSucceed();
						if(succeed.equals("1")){
							List<HomeBrandTuiJiaDatum> data = fromJson.getData();
							mHomeTuijiandata.addAll(data);
							myGridview = new MyGridview();
							mListView.setAdapter(myGridview);
							myGridview.notifyDataSetChanged();
							mscroll.smoothScrollTo(0, 0);
						}
						
						
						
//						HomeTuijian fromJson = gson.fromJson(arg0, HomeTuijian.class);
//						zsj.com.oyk255.example.ouyiku.groupjson.Status status = fromJson.getStatus();
//						String succeed = status.getSucceed();
//						if(succeed.equals("1")){
//							mHomeTuijiandata.clear();
//							List<HomeTuijianData> data = fromJson.getData();
//							mHomeTuijiandata.addAll(data);
//							myGridview = new MyGridview();
//							mGridview.setAdapter(myGridview);
//							myGridview.notifyDataSetChanged();
//							mscroll.smoothScrollTo(0, 0);
//						}
						
					}
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						progressHUD.dismiss();
					}
				});
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		//首页7间店铺
		String url=Constant.URL.HomeSeverStoreURL;
		
		HTTPUtils.post(getActivity(), url, null, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SevenShop sevenShop = gson.fromJson(arg0, SevenShop.class);
				Status status = sevenShop.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mSevenShop.clear();
					List<Datum> data = sevenShop.getData();
					picUrl1 = data.get(0).getPicUrl();
					picUrl2 = data.get(1).getPicUrl();
					picUrl3 = data.get(2).getPicUrl();
					picUrl4 = data.get(3).getPicUrl();
					picUrl5 = data.get(4).getPicUrl();
//					picUrl6 = data.get(5).getPicUrl();
//					picUrl7 = data.get(6).getPicUrl();
					for (int i = 0; i < data.size(); i++) {
						Datum datum = data.get(i);
						mSevenShop.add(datum);
						
					}
					
					UILUtils.displayImageNoAnim(picUrl1, mStoreImg1);
					UILUtils.displayImageNoAnim(picUrl2, mStoreImg2);
					UILUtils.displayImageNoAnim(picUrl3, mStoreImg3);
					UILUtils.displayImageNoAnim(picUrl4, mStoreImg4);
					UILUtils.displayImageNoAnim(picUrl5, mStoreImg5);
//					UILUtils.displayImageNoAnim(picUrl6, mStoreImg6);
//					UILUtils.displayImageNoAnim(picUrl7, mStoreImg7);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
		
	}

	private void initBanner() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		//轮播图
		String bannerUrl=Constant.URL.HomeBannerURL;;
		HTTPUtils.post(getActivity(), bannerUrl, null, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				HomeBanner fromJson = gson.fromJson(arg0, HomeBanner.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					progressHUD.dismiss();
					List<BannerDatum> data = fromJson.getData();
					mBannerData.addAll(data);
					FragmentManager fm = getChildFragmentManager();
					pager.setAdapter(new BannerAdapter(fm));
					pager.setOnPageChangeListener(new BannerPagerChangedListener());
					pager.setCurrentItem(50000);//设置默认的时候图片在中间
					
				}else{
					progressHUD.dismiss();
				}

			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	private void initUI() {
		mscroll = (ScrollView) rootview.findViewById(R.id.homescrollView);
		
		
		rootview.findViewById(R.id.hotsale).setOnClickListener(this);//热卖
		rootview.findViewById(R.id.newgoods).setOnClickListener(this); //新品
		rootview.findViewById(R.id.bangdan).setOnClickListener(this);//榜单
		rootview.findViewById(R.id.seabuy).setOnClickListener(this); //海淘
		rootview.findViewById(R.id.sign).setOnClickListener(this);//海淘
		
		mStoreImg1 = (ImageView) rootview.findViewById(R.id.store_img1);
		mStoreImg2 = (ImageView) rootview.findViewById(R.id.store_img2);
		mStoreImg3 = (ImageView) rootview.findViewById(R.id.store_img3);
		mStoreImg4 = (ImageView) rootview.findViewById(R.id.store_img4);
		mStoreImg5 = (ImageView) rootview.findViewById(R.id.store_img5);
//		mStoreImg6 = (ImageView) rootview.findViewById(R.id.store_img6);
//		mStoreImg7 = (ImageView) rootview.findViewById(R.id.store_img7);
		
		mStoreImg1.setOnClickListener(this);
		mStoreImg2.setOnClickListener(this);
		mStoreImg3.setOnClickListener(this);
		mStoreImg4.setOnClickListener(this);
		mStoreImg5.setOnClickListener(this);
//		mStoreImg6.setOnClickListener(this);
//		mStoreImg7.setOnClickListener(this);
		
		
		xianshimiaosha = (ImageView) rootview.findViewById(R.id.xianshimiaosha);
		zerobuy = (ImageView) rootview.findViewById(R.id.zerobuy);
		ninebuy = (ImageView) rootview.findViewById(R.id.ninebuy);
		
		redbag = (ImageView) rootview.findViewById(R.id.redbag);
		pingtuan = (ImageView) rootview.findViewById(R.id.pingtuan);
		pinhongbao = (ImageView) rootview.findViewById(R.id.pinhongbao);
		
		
		
		xianshimiaosha.setOnClickListener(this);
		zerobuy.setOnClickListener(this);
		ninebuy.setOnClickListener(this);
		redbag.setOnClickListener(this);
		pingtuan.setOnClickListener(this);
		pinhongbao.setOnClickListener(this);
		
		
		mSearch_edit = (TextView) rootview.findViewById(R.id.search_edit);//搜索
		mSearch_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
		
		mListView =  (ScrollViewWithListView) rootview.findViewById(R.id.home_listdview);
		pager = (ViewPager) rootview.findViewById(R.id.lunbo_viewpage);
			
		
		viewpageIndicator = (ViewpageIndicator) rootview.findViewById(R.id.viewpageIndicator1);
		// 自动滑动
		autoScroll();
		
		//推荐列表
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HomeBrandTuiJiaDatum homeBrandTuiJiaDatum = mHomeTuijiandata.get(position);
				String brandId = homeBrandTuiJiaDatum.getBrandId();
				Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
				intent.putExtra("mSevenShop1", brandId);
				
				startActivity(intent);
				
				
			}
		});
		
	}
	
	public void autoScroll() {
		
		pager.postDelayed( new Runnable() {
			public void run() {
				int position = pager.getCurrentItem();
				if(!mDragging ){//如果手指没有触摸
					pager.setCurrentItem(position + 1);
				}
				pager.postDelayed(this, 4000);
			}
		}, 4000);
	}

	class BannerAdapter extends FragmentPagerAdapter{
		public BannerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position ) {
			int index= position %mImgRes.length;
			if(mBannerData.size()==0){
				HomeBannerFragment bannerItemFragment = new HomeBannerFragment(index, mImgRes[index]);
				return bannerItemFragment;
			}else{
				BannerDatum bannerDatum = mBannerData.get(index);
				HomeBannerFragment bannerItemFragment2 = new HomeBannerFragment(index, bannerDatum);
				return bannerItemFragment2;
			}
		}

		@Override
		public int getCount() {
			return 100000;
		}
		
	}
	class BannerPagerChangedListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_IDLE://手指松开的时候
				
				mDragging = false;
				break;
			case ViewPager.SCROLL_STATE_DRAGGING://手指触摸的时候
				mDragging = true;
				break;
			case ViewPager.SCROLL_STATE_SETTLING://默认状态
				mDragging = false;
				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			//自定义viewpageIndicator
			position%=4;
			viewpageIndicator.move(position, arg1,4);
		}

		@Override
		public void onPageSelected(int arg0) {
		}
		
	}
	class MyGridview extends BaseAdapter{


		@Override
		public int getCount() {
			return mHomeTuijiandata.size();
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
			ViewHolder holder = null;
			if(convertView == null){
				view=getActivity().getLayoutInflater().inflate(R.layout.home_tuijian_item, null);
				holder = new ViewHolder();
				holder.Title = (TextView) view.findViewById(R.id.home_listitem_title);
				holder.Imginfo = (AspectRatioImageView) view.findViewById(R.id.home_listitem_img);
				holder.Discount = (TextView) view.findViewById(R.id.home_listitem_zhekou);
				holder.Time = (TextView) view.findViewById(R.id.home_listitem_time);
//				
//				 holder.Imginfo.getLayoutParams().height=(screenWidth-10)/2;
//				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
//				
//				holder.NewPrice = (TextView) view.findViewById(R.id.gridview_price);
				view.setTag(holder);
			}else{
				view  = convertView;
				//通过tag把layout对应的viewholder找到
				holder = (ViewHolder) view.getTag();
			}
				HomeBrandTuiJiaDatum homeBrandTuiJiaDatum = mHomeTuijiandata.get(position);
				String picUrl = homeBrandTuiJiaDatum.getPicUrl();
				String rebate = homeBrandTuiJiaDatum.getRebate();
				String time = homeBrandTuiJiaDatum.getTime();
				String title = homeBrandTuiJiaDatum.getTitle();
				String brandId = homeBrandTuiJiaDatum.getBrandId();
				
				holder.Title.setText(title);
				holder.Discount.setText(rebate+"折起");
				holder.Time.setText("剩余"+time+"天");
				UILUtils.displayImageNoAnim(picUrl, holder.Imginfo);
				
				
//			holder.OldPrice.setText("¥"+homeTuijianData.getOld_price());
//			holder.NewPrice.setText("¥"+homeTuijianData.getNew_price());
//			holder.GoodsInfo.setText(homeTuijianData.getTitle());
//			holder.Discount.setText(homeTuijianData.getRebate()+"折");
//			UILUtils.displayImageNoAnim(homeTuijianData.getPic_url(), holder.Imginfo);
				
			
			
			return view;
		}
		
	}
	
	class ViewHolder
	{
		AspectRatioImageView Imginfo;
		TextView Title;
		TextView Time;
		TextView Discount;
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ninebuy://9.9元
			startActivity(new Intent(getActivity(), PhoneChongzhiActivity.class));
			break;
		case R.id.newgoods://新品上市
			startActivity(new Intent(getActivity(), NewGoodsActivity.class));
			break;
		case R.id.bangdan://榜单
			startActivity(new Intent(getActivity(), TopActivity.class));
			break;
		case R.id.zerobuy://0元购
			startActivity(new Intent(getActivity(), TryuseActivity.class));
			break;
		case R.id.seabuy://海淘
			startActivity(new Intent(getActivity(), SeaShopActivity.class));
			break;
		case R.id.hotsale://热卖
			startActivity(new Intent(getActivity(), HotSaleActivity.class));
			break;
		case R.id.xianshimiaosha://限时秒杀
			startActivity(new Intent(getActivity(), TimeSaleActivity.class));
			break;
		case R.id.sign://签到
			startActivity(new Intent(getActivity(), SignActivity.class));
			break;
		case R.id.redbag://红包
			startActivity(new Intent(getActivity(), RedBagActivity.class));
			break;
		case R.id.pingtuan://拼团
			startActivity(new Intent(getActivity(), PinTuanActivity.class));
			break;
		case R.id.store_img1:
			Log.e("store_img1", mSevenShop.get(0).getMerchantId());
			Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
			intent.putExtra("mSevenShop1", mSevenShop.get(0).getMerchantId());
			startActivity(intent);
			break;
		case R.id.store_img2:
			Log.e("store_img2", mSevenShop.get(1).getMerchantId());
			Intent intent1 = new Intent(getActivity(), Brand_detailActivity.class);
			intent1.putExtra("mSevenShop1", mSevenShop.get(1).getMerchantId());
			startActivity(intent1);
			break;
		case R.id.store_img3:
			Log.e("store_img3", mSevenShop.get(2).getMerchantId());
			Intent intent2 = new Intent(getActivity(), Brand_detailActivity.class);
			intent2.putExtra("mSevenShop1", mSevenShop.get(2).getMerchantId());
			startActivity(intent2);
			break;
		case R.id.store_img4:
			Log.e("store_img4", mSevenShop.get(3).getMerchantId());
			Intent intent3 = new Intent(getActivity(), Brand_detailActivity.class);
			intent3.putExtra("mSevenShop1", mSevenShop.get(3).getMerchantId());
			startActivity(intent3);
			break;
		case R.id.store_img5:
			Log.e("store_img5", mSevenShop.get(4).getMerchantId());
			Intent intent4 = new Intent(getActivity(), Brand_detailActivity.class);
			intent4.putExtra("mSevenShop1", mSevenShop.get(4).getMerchantId());
			startActivity(intent4);
			break;
		case R.id.pinhongbao:
			if(!userid.equals("")){
				Intent intent5 = new Intent(getActivity(), HomeHuoDongActivity.class);
				startActivity(intent5);
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
//		case R.id.store_img7:
//			Log.e("store_img7", mSevenShop.get(6).getMerchantId());
//			Intent intent6 = new Intent(getActivity(), Brand_detailActivity.class);
//			intent6.putExtra("mSevenShop1", mSevenShop.get(6).getMerchantId());
//			startActivity(intent6);
//			break;

		default:
			break;
		}
		
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
					//商品推荐
					if(mHomeTuijiandata.size()==0){
						initTuiJian();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						HashMap<String, String> TuijianMap=new HashMap<String, String>();
						int size = mHomeTuijiandata.size();
						String HomeTuijianUrl=Constant.URL.HomeBrandUrl;
						TuijianMap.put("num", ""+size);
						
						HTTPUtils.post(getActivity(), HomeTuijianUrl, TuijianMap, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								HomeBrandTuiJia fromJson = gson.fromJson(arg0, HomeBrandTuiJia.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<HomeBrandTuiJiaDatum> data = fromJson.getData();
									mHomeTuijiandata.addAll(data);
									myGridview.notifyDataSetChanged();
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								}else{
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
								}
							}
							
							@Override
							public void onErrorResponse(VolleyError arg0) {
								
							}
						});
					}
					
				}
			}.sendEmptyMessageDelayed(0, 3000);
		}

	}

	
}
