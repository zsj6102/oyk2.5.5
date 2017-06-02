package zsj.com.oyk255.example.ouyiku.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.NewsGoods;
import zsj.com.oyk255.example.ouyiku.homejson.NewsGoodsDatum;
import zsj.com.oyk255.example.ouyiku.homejson.NewsTop;
import zsj.com.oyk255.example.ouyiku.homejson.NewsTopDatum;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.NewsBrandDetailActivity;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NewsFragment extends Fragment implements OnClickListener{

	private View view;
	private ListView mListView;
	private LinearLayout invis;
	private TextView mToday;
	private TextView mTomorrow;
	int position;
	int tab=0;
	ArrayList<NewsGoodsDatum> mNewsData=new ArrayList<NewsGoodsDatum>();
	ArrayList<NewsGoodsDatum> mNewsData2=new ArrayList<NewsGoodsDatum>();
	
	private ImageView mTopView;
	private TextView tabs_mToday;
	private TextView tabs_mTomorrow;
	public NewsFragment() {
		
	}
	public NewsFragment(int position) {
		this.position=position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view ==null){
			view = inflater.inflate(R.layout.fragment_news, container, false);
			//上拉加载下拉刷新
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new NewsListener());
			initUI();
			topView();
			initToday();
			
			
		}
		
		return view;
	}
	private void topView(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.NewsTopviewURL+"&cat_id="+(position+1);
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				NewsTop fromJson = gson.fromJson(arg0, NewsTop.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<NewsTopDatum> data = fromJson.getData();
					UILUtils.displayImageNoAnim(data.get(0).getPicUrl(), mTopView);
					mTopView.setClickable(false);
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}
	private NewsAdapter newsAdapter;
	private void initToday() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.TodayNewsURL+"&tab="+"0"+"&cat_id="+(position+1);
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				progressHUD.dismiss();
				NewsGoods fromJson = gson.fromJson(arg0, NewsGoods.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<NewsGoodsDatum> data = fromJson.getData();
					mNewsData.clear();
					mNewsData.addAll(data);
					newsAdapter = new NewsAdapter();
					mListView.setAdapter(newsAdapter);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	
	private TomorrowAdapterAdapter tomorrowAdapterAdapter;
	
	private void initTomorrow(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.TodayNewsURL+"&tab="+"1"+"&cat_id="+(position+1);
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				NewsGoods fromJson = gson.fromJson(arg0, NewsGoods.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					 List<NewsGoodsDatum> data = fromJson.getData();
					 mNewsData2.clear();
					 mNewsData2.addAll(data);
					 tomorrowAdapterAdapter = new TomorrowAdapterAdapter();
					mListView.setAdapter(tomorrowAdapterAdapter);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		invis = (LinearLayout) view.findViewById(R.id.invis);
		View topview = getActivity().getLayoutInflater().inflate(R.layout.news_topview_item, null);
		View tabsView = getActivity().getLayoutInflater().inflate(R.layout.news_tabs_item, null);
		mTopView = (ImageView) topview.findViewById(R.id.news_topimg);
		mTopView.setClickable(false);
		
		mListView = (ListView) view.findViewById(R.id.lv);
		mToday = (TextView) view.findViewById(R.id.tv_today);
		mTomorrow = (TextView) view.findViewById(R.id.tv_mingri);
		mToday.setOnClickListener(this);
		mTomorrow.setOnClickListener(this);
		
		mListView.addHeaderView(topview);
		mListView.addHeaderView(tabsView);
		
		tabs_mToday = (TextView) tabsView.findViewById(R.id.tv_today);
		tabs_mTomorrow = (TextView) tabsView.findViewById(R.id.tv_mingri);
		tabs_mToday.setOnClickListener(this);
		tabs_mTomorrow.setOnClickListener(this);
		mToday.setTextColor(Color.parseColor("#EC407A"));
		tabs_mToday.setTextColor(Color.parseColor("#EC407A"));
		
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					invis.setVisibility(View.VISIBLE);
				} else {
					invis.setVisibility(View.GONE);
					}
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position>1){
					
					Intent intent = new Intent(getActivity(),NewsBrandDetailActivity.class);
					if(tab==0){
						NewsGoodsDatum newsGoodsDatum = mNewsData.get(position-2);
						String title = newsGoodsDatum.getTitle();
						String brandId = newsGoodsDatum.getBrandId();
						String brandmerchantId = newsGoodsDatum.getBrandmerchantId();
						String activiteId = newsGoodsDatum.getActiviteId();
						
						intent.putExtra("newstitle", title);
						intent.putExtra("brandId", brandId);
						intent.putExtra("tab", "0");
						intent.putExtra("brandmerchantId",brandmerchantId);
						intent.putExtra("activiteId",activiteId);
						startActivity(intent);
						
					}else{
						NewsGoodsDatum newsGoodsDatum = mNewsData2.get(position-2);
						String title = newsGoodsDatum.getTitle();
						String brandId = newsGoodsDatum.getBrandId();
						String brandmerchantId = newsGoodsDatum.getBrandmerchantId();
						String activiteId = newsGoodsDatum.getActiviteId();
						
						intent.putExtra("newstitle", title);
						intent.putExtra("brandId", brandId);
						intent.putExtra("tab", "1");
						intent.putExtra("brandmerchantId",brandmerchantId);
						intent.putExtra("activiteId",activiteId);
						startActivity(intent);
						
					}
				}
				
				
			}
		});
		
	}

	class NewsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mNewsData.size();
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
			View layout =null;
			TodayViewHolder holder=null;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.today_listview_item, null);
				holder=new TodayViewHolder();
				holder.mLogo = (ImageView) layout.findViewById(R.id.today_brand_img);
				holder.mBrandName = (TextView) layout.findViewById(R.id.today_brand_name);
				holder.mRebate = (TextView) layout.findViewById(R.id.today_brand_zhekou);
				holder.mTime = (TextView) layout.findViewById(R.id.today_brand_time);
				holder.mBigLogo = (AspectRatioImageView) layout.findViewById(R.id.today_brand_goodsimg);
				layout.setTag(holder);
				
			}else{
				layout=convertView;
				holder= (TodayViewHolder) layout.getTag();
			}
			NewsGoodsDatum newsGoodsDatum = mNewsData.get(position);
			String title = newsGoodsDatum.getTitle();
			String logo = newsGoodsDatum.getLogo();
			String rebate = newsGoodsDatum.getRebate();
			String brandmerchantId = newsGoodsDatum.getBrandmerchantId();
			String brandId = newsGoodsDatum.getBrandId();
			String photo = newsGoodsDatum.getPhoto();
			String begintime = newsGoodsDatum.getBegintime();
			String time = newsGoodsDatum.getTime();
			
			String times = times(begintime);
			
			holder.mBrandName.setText(title);
			holder.mTime.setText("剩"+time+"天 ");
			holder.mRebate.setText(rebate+"折");
			UILUtils.displayImageNoAnim(logo, holder.mLogo);
			UILUtils.displayImageNoAnim(photo, holder.mBigLogo);
			
			return layout;
		}
		
		
	}
	class TodayViewHolder{
		ImageView mLogo;
		TextView mBrandName;
		TextView mRebate;
		TextView mTime;
		AspectRatioImageView mBigLogo ;
		
	}
	
	
	class TomorrowAdapterAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mNewsData2.size();
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
			TomorrowViewHolder tomorrowViewHolder=null;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.tomorrow_listview_item, null);
				tomorrowViewHolder=new TomorrowViewHolder();
				tomorrowViewHolder.mLogo = (ImageView) layout.findViewById(R.id.tomorrow_brand_img);
				tomorrowViewHolder.mBigLogo = (AspectRatioImageView) layout.findViewById(R.id.tomorrow_brand_goodsimg);
				tomorrowViewHolder.mName = (TextView) layout.findViewById(R.id.tomorrow_brand_name);
				tomorrowViewHolder.mRebate = (TextView) layout.findViewById(R.id.tomorrow_brand_zhekou);
				tomorrowViewHolder.tomorrow_brand_time = (TextView) layout.findViewById(R.id.tomorrow_brand_time);
				layout.setTag(tomorrowViewHolder);
			}else{
				layout=convertView;
				tomorrowViewHolder=(TomorrowViewHolder) layout.getTag();
				
			}
			NewsGoodsDatum newsGoodsDatum = mNewsData2.get(position);
			String logo = newsGoodsDatum.getLogo();
			String photo = newsGoodsDatum.getPhoto();
			String title = newsGoodsDatum.getTitle();
			String rebate = newsGoodsDatum.getRebate();
			String brandId = newsGoodsDatum.getBrandId();
			String brandmerchantId = newsGoodsDatum.getBrandmerchantId();
			
			tomorrowViewHolder.mName.setText(title);
			tomorrowViewHolder.mRebate.setText(rebate+"折");
			UILUtils.displayImageNoAnim(logo, tomorrowViewHolder.mLogo);
			UILUtils.displayImageNoAnim(photo, tomorrowViewHolder.mBigLogo);
			tomorrowViewHolder.tomorrow_brand_time.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ToastUtils.toast(getActivity(), "已提醒");
				}
			});
			return layout;
		}
		
	}

	class TomorrowViewHolder{
		ImageView mLogo;
		AspectRatioImageView mBigLogo;
		TextView mName ;
		TextView mRebate;
		TextView tomorrow_brand_time;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_today:
			tab=0;
			initToday();
			mToday.setTextColor(Color.parseColor("#EC407A"));
			tabs_mToday.setTextColor(Color.parseColor("#EC407A"));
			mTomorrow.setTextColor(Color.parseColor("#333333"));
			tabs_mTomorrow.setTextColor(Color.parseColor("#333333"));
			
			break;
		case R.id.tv_mingri:
			tab=1;
			initTomorrow();
			mToday.setTextColor(Color.parseColor("#333333"));
			mTomorrow.setTextColor(Color.parseColor("#EC407A"));
			tabs_mToday.setTextColor(Color.parseColor("#333333"));
			tabs_mTomorrow.setTextColor(Color.parseColor("#EC407A"));
			break;

		default:
			break;
		}
		
	}
	
	public class NewsListener implements PullToRefreshLayout.OnRefreshListener
	{
//		int tab;
//		public NewsListener(int tab){
//			this.tab=tab;
//		}
//		
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 下拉刷新操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
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
					if(tab==0){
						if(mNewsData.size()==0){
							initToday();
							pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
						}else{
							int size = mNewsData.size();
							String url=Constant.URL.TodayNewsURL+"&tab="+tab+"&cat_id="+(position+1)+"&num="+size;
							Log.e("加载url", url);
							HTTPUtils.get(getActivity(), url, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Gson gson = new Gson();
									Log.e("加载操作", arg0);
									NewsGoods fromJson = gson.fromJson(arg0, NewsGoods.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<NewsGoodsDatum> data = fromJson.getData();
										mNewsData.addAll(data);
										newsAdapter.notifyDataSetChanged();
										
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
										
									}
									
								}
								
								@Override
								public void onErrorResponse(VolleyError arg0) {
									
								}
							});
						}
					
					}else{
						if(mNewsData2.size()==0){
							initTomorrow();
							pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
						}else{
							int size = mNewsData2.size();
							
							String url=Constant.URL.TodayNewsURL+"&tab="+tab+"&cat_id="+(position+1)+"&num="+size;
							HTTPUtils.get(getActivity(), url, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									Gson gson = new Gson();
									NewsGoods fromJson = gson.fromJson(arg0, NewsGoods.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										 List<NewsGoodsDatum> data = fromJson.getData();
										 mNewsData2.addAll(data);
										 tomorrowAdapterAdapter.notifyDataSetChanged();
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
					
					
				}
			}.sendEmptyMessageDelayed(0, 3000);
		}

	}
	
	public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日HH时");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

}
	
}
