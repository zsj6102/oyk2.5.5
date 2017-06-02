package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.groupjson.Group1Datum;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupFirst;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupTuiJian;
import zsj.com.oyk255.example.ouyiku.groupjson.GroupTuiJianDatum;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.v1.DetailActivity;
import zsj.com.oyk255.example.ouyiku.v1.Group_secondActivity;
import zsj.com.oyk255.example.ouyiku.v1.Group_second_huazhuangActivity;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GoodsFragment extends Fragment {

	private View rootview;
	private MyGridView goods_gridview;
	ArrayList<Group1Datum> mGridData=new ArrayList<Group1Datum>();
	ArrayList<GroupTuiJianDatum> mTuiJIanData=new ArrayList<GroupTuiJianDatum>();
	private MyGridviewAdapter myGridviewAdapter;
	private MyGridView mgridview_tuijian;
	private int screenWidth;
	public GoodsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootview==null){
			
			rootview = inflater.inflate(R.layout.fragment_goods, container, false);
			((PullToRefreshLayout) rootview.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			screenWidth = ScreenUtils.getScreenWidth(getActivity());
			initUI();
			initData();
			initTuiJina();
		}
		return rootview;
	}

	private TuijianGridview tuijianGridview;
	
	private void initTuiJina() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.GroupTuijianURL;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				GroupTuiJian fromJson = gson.fromJson(arg0, GroupTuiJian.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<GroupTuiJianDatum> data = fromJson.getData();
					mTuiJIanData.clear();
					mTuiJIanData.addAll(data);
					tuijianGridview = new TuijianGridview();
					mgridview_tuijian.setAdapter(tuijianGridview);
				}
				
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
		//post请求
		String url=Constant.URL.GroupFirstURL;
		
		HTTPUtils.post(getActivity(), url, null, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				GroupFirst fromJson = gson.fromJson(arg0, GroupFirst.class);
				List<Group1Datum> data = fromJson.getData();
				mGridData.clear();
				mGridData.addAll(data);
				myGridviewAdapter = new MyGridviewAdapter();
				goods_gridview.setAdapter(myGridviewAdapter);
				
				//跳转二级分类。传ID和标题名字
				goods_gridview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
							String pcategoryId = mGridData.get(position).getPcategoryId();
							if(pcategoryId.equals("162")){
							Intent intent = new Intent(getActivity(), Group_second_huazhuangActivity.class);
							intent.putExtra("pcategoryId", pcategoryId);
							startActivity(intent);
							
								
							}else{
								Intent intent = new Intent(getActivity(), Group_secondActivity.class);
								String mName = mGridData.get(position).getMName();
								intent.putExtra("mName", mName);
								intent.putExtra("pcategoryId", pcategoryId);
								startActivity(intent);
								
							}
							
						
					}
				});
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		//一级大分类和商品推荐
		goods_gridview = (MyGridView) rootview.findViewById(R.id.goods_gridview);
		mgridview_tuijian = (MyGridView) rootview.findViewById(R.id.goods_gridview_tuijian);
		
		
		
		mgridview_tuijian.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GroupTuiJianDatum groupTuiJianDatum = mTuiJIanData.get(position);
				String product_id = groupTuiJianDatum.getProductId();
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("product_id", product_id);
				
				startActivity(intent);
				
			}
		});
		
		
	}
	public class MyGridviewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mGridData.size();
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
			ViewHolder2 holder2 = null;
			if(convertView == null){
				view=getActivity().getLayoutInflater().inflate(R.layout.goods_item, null);
				holder2 = new ViewHolder2();
				holder2.goods_img = (ImageView) view.findViewById(R.id.goods_img);
				holder2.mTitle = (TextView) view.findViewById(R.id.textView1);
				holder2.mSecTitle = (TextView) view.findViewById(R.id.textView2);
				view.setTag(holder2);
			}else{
				view  = convertView;
				holder2 = (ViewHolder2) view.getTag();
				
			}
			Group1Datum group1Datum = mGridData.get(position);
			holder2.mTitle.setText(group1Datum.getMName());
			holder2.mSecTitle.setText(group1Datum.getMDetail());
			UILUtils.displayImageNoAnim(group1Datum.getBUrl(), holder2.goods_img);
			return view;
		}
		
	}
	
	class ViewHolder2{
		ImageView goods_img;
		TextView mTitle;
		TextView mSecTitle;
	}
	class TuijianGridview extends BaseAdapter{

		@Override
		public int getCount() {
			return mTuiJIanData.size();
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
				view=getActivity().getLayoutInflater().inflate(R.layout.gridview_item, null);
                holder = new ViewHolder();
				holder.OldPrice = (TextView) view.findViewById(R.id.gridview_oldprice);
				holder.OldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.Imginfo = (ImageView) view.findViewById(R.id.gridview_imginfo);
				 holder.Imginfo.getLayoutParams().height=(screenWidth-10)/2;
				holder.GoodsInfo = (TextView) view.findViewById(R.id.gridview_tv_title);
				holder.NewPrice = (TextView) view.findViewById(R.id.gridview_price);
				holder.Discount = (TextView) view.findViewById(R.id.discount);
				view.setTag(holder);
			}else{
				view  = convertView;
				holder = (ViewHolder) view.getTag();
			}
			GroupTuiJianDatum groupTuiJianDatum = mTuiJIanData.get(position);
			String newPrice = groupTuiJianDatum.getNewPrice();
			String oldPrice = groupTuiJianDatum.getOldPrice();
			String picUrl = groupTuiJianDatum.getPicUrl();
			String productId = groupTuiJianDatum.getProductId();
			String rebate = groupTuiJianDatum.getRebate();
			String title = groupTuiJianDatum.getTitle();
			
			holder.OldPrice.setText("￥"+oldPrice);
			holder.NewPrice.setText("￥"+newPrice);
			holder.GoodsInfo.setText(title);
			holder.Discount.setText(rebate+"折");
			UILUtils.displayImageNoAnim(picUrl, holder.Imginfo);
			
			
			return view;
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
					if(mTuiJIanData.size()==0){
						
						initTuiJina();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						
						String productId = mTuiJIanData.get(mTuiJIanData.size()-1).getProductId();
						
						String url=Constant.URL.GroupTuijianURL+"&product_id="+productId;
						HTTPUtils.get(getActivity(), url, new VolleyListener() {

							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								GroupTuiJian fromJson = gson.fromJson(arg0, GroupTuiJian.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									List<GroupTuiJianDatum> data = fromJson.getData();
									mTuiJIanData.addAll(data);
									tuijianGridview.notifyDataSetChanged();
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
