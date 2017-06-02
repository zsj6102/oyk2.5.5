package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.groupjson.MeiBrand;
import zsj.com.oyk255.example.ouyiku.groupjson.MeiBrandDatum;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BrandMeizhuangFragment extends Fragment {

	public BrandMeizhuangFragment() {
	}

	private View rootview;
	private MyGridView brand_gridview;
	ArrayList<MeiBrandDatum> mBrandData=new ArrayList<MeiBrandDatum>();
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(rootview==null){
			rootview = inflater.inflate(R.layout.fragment_brand, container, false);
			((PullToRefreshLayout) rootview.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			initUI();
			initData();
		}
		return rootview;
		
	}

	private BrandGridview brandGridview;
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.GroupMeiBrandURL;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MeiBrand fromJson = gson.fromJson(arg0, MeiBrand.class);
				Status status = fromJson.getStatus();
				
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<MeiBrandDatum> data = fromJson.getData();
					mBrandData.clear();
					mBrandData.addAll(data);
					brandGridview = new BrandGridview();
					brand_gridview.setAdapter(brandGridview);
					
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		brand_gridview = (MyGridView) rootview.findViewById(R.id.brand_gridview);
		
		brand_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
				String brandId = mBrandData.get(position).getBrandId();
				intent.putExtra("mSevenShop1", brandId);
				startActivity(intent);
				
			}
		});
		
	}
	class BrandGridview extends BaseAdapter{

		@Override
		public int getCount() {
			//TODO
			return mBrandData.size();
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
			if(convertView==null){
				view=getActivity().getLayoutInflater().inflate(R.layout.brand_item, null);
				holder = new ViewHolder();
				holder.mBrand_img = (ImageView) view.findViewById(R.id.brand_img);
				view.setTag(holder);
			}else{
				view  = convertView;
				holder = (ViewHolder) view.getTag();
			}
			 MeiBrandDatum meiBrandDatum = mBrandData.get(position);
			String logo = meiBrandDatum.getLogo();
			UILUtils.displayImageNoAnim(logo, holder.mBrand_img);
			
			
			
			return view;
		}
		
	}
	class ViewHolder{
		ImageView mBrand_img;
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
					if(mBrandData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mBrandData.size();
						
						
						String url=Constant.URL.GroupMeiBrandURL+"&num="+size;
						Log.e("url", url);
						HTTPUtils.get(getActivity(), url, new VolleyListener() {
							

							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								MeiBrand fromJson = gson.fromJson(arg0, MeiBrand.class);
								
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<MeiBrandDatum> data = fromJson.getData();
									mBrandData.addAll(data);
									brandGridview.notifyDataSetChanged();
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
