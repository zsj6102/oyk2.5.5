package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
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
import zsj.com.oyk255.example.ouyiku.homejson.HomeBanner;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.hotjson.Datum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotFiveTitle;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetRed;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPicture;
import zsj.com.oyk255.example.ouyiku.hotjson.PictureDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.v1.Creat_fashionActivity;
import zsj.com.oyk255.example.ouyiku.v1.Fashion_groupActivity;
import zsj.com.oyk255.example.ouyiku.v1.HotPictureActivity;
import zsj.com.oyk255.example.ouyiku.v1.Hot_netredActivity;
import zsj.com.oyk255.example.ouyiku.v1.More_hotnetredActivity;
import zsj.com.oyk255.example.ouyiku.view.CircleImageView;
import zsj.com.oyk255.example.ouyiku.view.HorizontalListView;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FashionmanFragment extends Fragment implements OnClickListener{
//	boolean isVisible;//轮播图是否可见
	private boolean mDragging;//是否触碰轮播图
	private View view;
	private MyGridView mGridview;
//	private View mTopview;
	ArrayList<Integer> mdata=new ArrayList<Integer>();
	private ViewPager viewPager_banner;
	ArrayList<BannerDatum> mBanner=new ArrayList<BannerDatum>();
	int[] mImgRes=new int[]{R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo};
	private ViewpageIndicator viewpageIndicator;
	private ImageView mImg_one;
	private ImageView mImg_two;
	private ImageView mImg_three;
	private ImageView mImg_four;
	private ImageView mImg_five;
	ArrayList<Datum> mFive=new ArrayList<Datum>();
	private TextView mTv1;
	private TextView mTv2;
	private TextView mTv3;
	private TextView mTv4;
	private TextView mTv5;
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<HotNetDatum> mHotnetDATA=new ArrayList<HotNetDatum>();
	ArrayList<PictureDatum> mHotPictureDATA=new ArrayList<PictureDatum>();
	private HorizontalListView mFashion_listview;
	private ScrollView mScroll;
	private String cattalentId1;
	private String cattalentId2;
	private String cattalentId3;
	private String cattalentId4;
	private String cattalentId5;
	
	private String title1;
	private String title2;
	private String title3;
	private String title4;
	private String title5;
	int screenWidth;
	public FashionmanFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			
			view = inflater.inflate(R.layout.fragment_fashionman, container, false);
			
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			screenWidth = ScreenUtils.getScreenWidth(getActivity());
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
		//热门美图
		
		HotPictureUrl();
		super.onStart();
	}
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		//轮播
		String BannerUrl= Constant.URL.FashionManBannerURL;
		
		HTTPUtils.post(getActivity(), BannerUrl, null, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HomeBanner fromJson = gson.fromJson(arg0, HomeBanner.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<BannerDatum> data = fromJson.getData();
					mBanner.addAll(data);
					FragmentManager fm = getChildFragmentManager();
					viewPager_banner.setAdapter(new BannerAdapter(fm));
					viewPager_banner.setOnPageChangeListener(new BannerPagerChangedListener());
					viewPager_banner.setCurrentItem(50000);//设置默认的时候图片在中间
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
		//5个入口
		String FiveTitleURL=Constant.URL.FashionManFIVEURL;
		HTTPUtils.post(getActivity(), FiveTitleURL, null, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotFiveTitle fromJson = gson.fromJson(arg0, HotFiveTitle.class);
				 zsj.com.oyk255.example.ouyiku.hotjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<Datum> data = fromJson.getData();
					mFive.clear();
					mFive.addAll(data);
					UILUtils.displayImageNoAnim(mFive.get(0).getCattalentPicUrl(), mImg_one);
					UILUtils.displayImageNoAnim(mFive.get(1).getCattalentPicUrl(), mImg_two);
					UILUtils.displayImageNoAnim(mFive.get(2).getCattalentPicUrl(), mImg_three);
					UILUtils.displayImageNoAnim(mFive.get(3).getCattalentPicUrl(), mImg_four);
					UILUtils.displayImageNoAnim(mFive.get(4).getCattalentPicUrl(), mImg_five);
					
					title1 = mFive.get(0).getCattalentName();
					title2 = mFive.get(1).getCattalentName();
					title3 = mFive.get(2).getCattalentName();
					title4 = mFive.get(3).getCattalentName();
					title5 = mFive.get(4).getCattalentName();
					
					
					
					mTv1.setText(title1);
					mTv2.setText(title2);
					mTv3.setText(title3);
					mTv4.setText(title4);
					mTv5.setText(title5);
					
					cattalentId1 = mFive.get(0).getCattalentId();
					cattalentId2 = mFive.get(1).getCattalentId();
					cattalentId3 = mFive.get(2).getCattalentId();
					cattalentId4 = mFive.get(3).getCattalentId();
					cattalentId5 = mFive.get(4).getCattalentId();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
				
		//热门网红
		String HotNetUrl=Constant.URL.FashionManHOTNETURL;
		
		HTTPUtils.get(getActivity(), HotNetUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotNetRed fromJson = gson.fromJson(arg0, HotNetRed.class);
				zsj.com.oyk255.example.ouyiku.hotjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mHotnetDATA.clear();
					List<HotNetDatum> data = fromJson.getData();
					mHotnetDATA.addAll(data);
					mFashion_listview.setAdapter(new FashionListAdapter());
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
		
	}

	private FashionGridAdapter fashionGridAdapter;
	private void HotPictureUrl() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String HotPictureurl=Constant.URL.FashionManHotPictureURL+"&num="+"0";
		HTTPUtils.get(getActivity(), HotPictureurl, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotPicture fromJson = gson.fromJson(arg0, HotPicture.class);
				zsj.com.oyk255.example.ouyiku.hotjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mHotPictureDATA.clear();
					List<PictureDatum> data = fromJson.getData();
					mHotPictureDATA.addAll(data);
					fashionGridAdapter = new FashionGridAdapter();
					mGridview.setAdapter(fashionGridAdapter);
					
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		mScroll = (ScrollView) view.findViewById(R.id.fashion_scroll);
		mScroll.smoothScrollTo(0, 0);
		view.findViewById(R.id.fashion_creat).setOnClickListener(this);
		mGridview = (MyGridView) view.findViewById(R.id.fashion_gridview);
		
		viewPager_banner = (ViewPager) view.findViewById(R.id.hotlunbo_viewpage);
		viewpageIndicator = (ViewpageIndicator) view.findViewById(R.id.hot_viewpageIndicator);
		autoScroll();
		
		mImg_one = (ImageView) view.findViewById(R.id.img_one);
		mImg_two = (ImageView) view.findViewById(R.id.img_two);
		mImg_three = (ImageView) view.findViewById(R.id.img_three);
		mImg_four = (ImageView) view.findViewById(R.id.img_four);
		mImg_five = (ImageView) view.findViewById(R.id.img_five);
		
		mTv1 = (TextView) view.findViewById(R.id.tv1);
		mTv2 = (TextView) view.findViewById(R.id.tv2);
		mTv3 = (TextView) view.findViewById(R.id.tv3);
		mTv4 = (TextView) view.findViewById(R.id.tv4);
		mTv5 = (TextView) view.findViewById(R.id.tv5);
		//5个人分类
		view.findViewById(R.id.zhubo).setOnClickListener(this);
		view.findViewById(R.id.mote).setOnClickListener(this);
		view.findViewById(R.id.mingxing).setOnClickListener(this);
		view.findViewById(R.id.xuesheng).setOnClickListener(this);
		view.findViewById(R.id.lama).setOnClickListener(this);
		
		view.findViewById(R.id.fishion_more).setOnClickListener(this);
		mFashion_listview = (HorizontalListView) view.findViewById(R.id.fashion_horizontallistview);
		
		//热门网红详情
		mFashion_listview.setOnItemClickListener(new HorizontalListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String useid = mHotnetDATA.get(position).getUserId();
				String nickname = mHotnetDATA.get(position).getNickname();
				Intent intent = new Intent(getActivity(), Hot_netredActivity.class);
				intent.putExtra("tid", useid);
				intent.putExtra("hotnetTitle", nickname);
				startActivity(intent);
				
			}
		});
		
		
		
		
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent intent = new Intent(getActivity(), HotPictureActivity.class);
					PictureDatum pictureDatum = mHotPictureDATA.get(position);
					String talentPostId = pictureDatum.getTalentPostId();
					String nickname = pictureDatum.getNickname();
					String isclickgood = pictureDatum.getIsclickgood();
					String photo = pictureDatum.getPhoto();
					String userId2 = pictureDatum.getUserId();
					
					intent.putExtra("faburenID", userId2);
					intent.putExtra("nickphoto", photo);
					intent.putExtra("talent_post_id", talentPostId);
					intent.putExtra("nicknametitle", nickname);
					startActivity(intent);
				
			}
		});
	}
	
	class FashionGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mHotPictureDATA.size();
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
			View layout = null;
			PictureHolder pictureHolder=null;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.gridview_fashion_item, null);
				pictureHolder=new PictureHolder();
				pictureHolder.mImg = (ImageView) layout.findViewById(R.id.fashion_item_img);
				pictureHolder.mTitle = (TextView) layout.findViewById(R.id.fashion_item_name);
				pictureHolder.mNick = (TextView) layout.findViewById(R.id.fashion_item_nicheng);
				pictureHolder.mZanNUM = (TextView) layout.findViewById(R.id.fashion_item_zannum);
				pictureHolder.mTou = (CircleImageView) layout.findViewById(R.id.fashion_item_touxiang);
				pictureHolder.mZan = (ImageView) layout.findViewById(R.id.fashion_item_zan);
				layout.setTag(pictureHolder);
			}else{
				layout  = convertView;
				pictureHolder = (PictureHolder) layout.getTag();
			}
			PictureDatum pictureDatum = mHotPictureDATA.get(position);
			pictureHolder.mTitle.setText(pictureDatum.getTalentTitle());
			pictureHolder.mNick.setText(pictureDatum.getNickname());
			pictureHolder.mZanNUM.setText(pictureDatum.getClickgood());
			UILUtils.displayImageNoAnim(pictureDatum.getPostImage(), pictureHolder.mImg);
			UILUtils.displayImageNoAnim(pictureDatum.getPhoto(), pictureHolder.mTou);
			
			return layout;
		}
		
	}
	class PictureHolder{
		ImageView mImg;
		TextView mTitle;
		TextView mNick;
		TextView mZanNUM;
		CircleImageView mTou;
		ImageView mZan;
	}
	
	class FashionListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mHotnetDATA.size();
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
			View layout = null;
			ViewHold hold=null;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.branddetail_grid_item, null);
				hold= new ViewHold();
				ImageView img_new = (ImageView) layout.findViewById(R.id.imageView_new);
				img_new.setVisibility(View.GONE);
				hold.mImg = (ImageView) layout.findViewById(R.id.branddetail_new_img);
				hold.mSignature = (TextView) layout.findViewById(R.id.branddetail_new_tvname);
				layout.setTag(hold);
			}else{
				layout  = convertView;
				//通过tag把layout对应的viewholder找到
				hold = (ViewHold) layout.getTag();
			}
			
			HotNetDatum hotNetDatum = mHotnetDATA.get(position);
			String nickname = hotNetDatum.getNickname();
			String photo = hotNetDatum.getPhoto();
			
			hold.mSignature.setText(nickname);
			UILUtils.displayImageNoAnim(photo, hold.mImg);
			return layout;
		}
		
		
	}
	class ViewHold{
		ImageView mImg;
		TextView mSignature;
	}
	
	class BannerAdapter extends FragmentPagerAdapter{
		public BannerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position ) {
			int index= position %mImgRes.length;
			if(mBanner.size()==0){
				BannerItemHotFragment bannerItemFragment = new BannerItemHotFragment(index, mImgRes[index]);
				return bannerItemFragment;
			}else{
				BannerDatum bannerDatum = mBanner.get(index);
				BannerItemHotFragment bannerItemFragment2 = new BannerItemHotFragment(index, bannerDatum);
				return bannerItemFragment2;
			}
		}

		@Override
		public int getCount() {
			return 100000;
		}
		
	}
	public void autoScroll() {
		
	viewPager_banner.postDelayed( new Runnable() {
			public void run() {
				int position = viewPager_banner.getCurrentItem();
				if(!mDragging ){//如果手指没有触摸
					viewPager_banner.setCurrentItem(position + 1);
				}
				viewPager_banner.postDelayed(this, 4000);
			}
		}, 4000);
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fashion_creat:
			startActivity(new Intent(getActivity(), Creat_fashionActivity.class));
			break;
		case R.id.zhubo:
			Intent intent2 = new Intent(getActivity(), Fashion_groupActivity.class);
			intent2.putExtra("cattalentId", cattalentId1);
			intent2.putExtra("title", title1);
			startActivityForResult(intent2, Constant.INTENT.FASHION_ZHUBO);
			
			break;
		case R.id.mote:
			Intent intent3 = new Intent(getActivity(), Fashion_groupActivity.class);
			intent3.putExtra("cattalentId", cattalentId2);
			intent3.putExtra("title", title2);
			startActivityForResult(intent3, Constant.INTENT.FASHION_MOTE);
			break;
		case R.id.mingxing:
			Intent intent4 = new Intent(getActivity(), Fashion_groupActivity.class);
			intent4.putExtra("cattalentId", cattalentId3);
			intent4.putExtra("title", title3);
			startActivityForResult(intent4, Constant.INTENT.FASHION_MINGXING);
			break;
		case R.id.xuesheng:
			Intent intent5 = new Intent(getActivity(), Fashion_groupActivity.class);
			intent5.putExtra("cattalentId", cattalentId4);
			intent5.putExtra("title", title4);
			startActivityForResult(intent5, Constant.INTENT.FASHION_XUESHENG);
			break;
		case R.id.lama:
			Intent intent6 = new Intent(getActivity(), Fashion_groupActivity.class);
			intent6.putExtra("cattalentId", cattalentId5);
			intent6.putExtra("title", title5);
			startActivityForResult(intent6, Constant.INTENT.FASHION_LAMA);
			break;
		case R.id.fishion_more:
			Intent intent = new Intent(getActivity(), More_hotnetredActivity.class);
			startActivity(intent);
			break;
		

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
					// 千万别忘了告诉控件加载完毕了哦！
					if(mHotPictureDATA.size()==0){
						HotPictureUrl();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mHotPictureDATA.size();
						Log.e("size", ""+size);
						String HotPictureurl=Constant.URL.FashionManHotPictureURL+"&num="+size;
						HTTPUtils.get(getActivity(), HotPictureurl, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Log.e("加载操作", arg0);
								Gson gson = new Gson();
								HotPicture fromJson = gson.fromJson(arg0, HotPicture.class);
								zsj.com.oyk255.example.ouyiku.hotjson.Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<PictureDatum> data = fromJson.getData();
									mHotPictureDATA.addAll(data);
									fashionGridAdapter.notifyDataSetChanged();;
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
