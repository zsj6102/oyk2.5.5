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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.ProductInfo;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TopList;
import zsj.com.oyk255.example.ouyiku.homejson.TopListData;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.v1.DetailActivity;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class TopFragment extends Fragment {

	String pcategoryId;
	private View view;
	private ListView mListView;
	public TopFragment(String pcategoryId) {
		this.pcategoryId=pcategoryId;
	}
	ArrayList<ProductInfo> mListData=new ArrayList<ProductInfo>();
	private ImageView top_img;
	private ImageView mImgNull;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_top, container, false);
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			initUI();
			initData();
		}
		return view;
	}

	private TopAdapter topAdapter;
	private void initData() {
		String url=Constant.URL.TopListlUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("cat_id", pcategoryId);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				TopList fromJson = gson.fromJson(arg0, TopList.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					TopListData data = fromJson.getData();
					String img = data.getImg();
					UILUtils.displayImageNoAnim(img, top_img);
					List<ProductInfo> list = data.getList();
					mListData.clear();
					mListData.addAll(list);
					if(mListData.size()==0){
						mImgNull.setVisibility(View.VISIBLE);
					}else{
						topAdapter = new TopAdapter();
						
						mListView.setAdapter(topAdapter);
						mImgNull.setVisibility(View.GONE);
						
					}
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	private void initUI() {
		View headView = getActivity().getLayoutInflater().inflate(R.layout.item_top_headview, null);
		mListView = (ListView) view.findViewById(R.id.top_listview);
		top_img = (ImageView) headView.findViewById(R.id.top_img);
		mListView.addHeaderView(headView);
		
		mImgNull = (ImageView) view.findViewById(R.id.topimg_null);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position>=1){
					ProductInfo list = mListData.get(position-1);
					String productId = list.getProductId();
					Intent intent = new Intent(getActivity(), DetailActivity.class);
					intent.putExtra("product_id", productId);
					
					startActivity(intent);
					
				}
				
			}
		});
		
	}
	
	class TopAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size();
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
				layout=getActivity().getLayoutInflater().inflate(R.layout.item_top, null);
				holder=new ViewHolder();
				holder.top_item_img = (ImageView) layout.findViewById(R.id.top_item_img);
				holder.top_item_title = (TextView) layout.findViewById(R.id.top_item_title);
				holder.top_item_old = (TextView) layout.findViewById(R.id.top_item_old);
				holder.top_item_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.top_item_new = (TextView) layout.findViewById(R.id.top_item_new);
				holder.top_item_lvtext = (TextView) layout.findViewById(R.id.top_item_lvtext);
				holder.top_item_lv = (ImageView) layout.findViewById(R.id.top_item_lv);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
			ProductInfo list = mListData.get(position);
			String marketprice = list.getMarketprice();
			String phone1 = list.getPhone1();
			String price = list.getPrice();
			String title = list.getTitle();
			
			if(position==0){
				holder.top_item_lv.setImageResource(R.mipmap.topgold);
				holder.top_item_lvtext.setVisibility(View.GONE);
			}else if(position==1){
				holder.top_item_lv.setImageResource(R.mipmap.topy);
				holder.top_item_lvtext.setVisibility(View.GONE);
			}else if(position==2){
				holder.top_item_lv.setImageResource(R.mipmap.topt);
				holder.top_item_lvtext.setVisibility(View.GONE);
			}else{
				holder.top_item_lv.setImageResource(R.mipmap.topother);
				holder.top_item_lvtext.setVisibility(View.VISIBLE);
				if(position<9){
					holder.top_item_lvtext.setText("0"+(position+1));
				}else{
					holder.top_item_lvtext.setText(""+(position+1));
				}
				
			}
			
			
			UILUtils.displayImageNoAnim(phone1, holder.top_item_img);
			holder.top_item_title.setText(title);
			holder.top_item_old.setText("￥"+marketprice);
			holder.top_item_new.setText("￥"+price);
			
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		ImageView top_item_img;
		TextView top_item_title;
		TextView top_item_old;
		TextView top_item_new;
		TextView top_item_lvtext;
		ImageView top_item_lv;
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
					if(mListData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						
						String url= Constant.URL.TopListlUrl;
						int size = mListData.size();
						
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("cat_id", pcategoryId);
						map.put("num", ""+size);
						HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								TopList fromJson = gson.fromJson(arg0, TopList.class);
								Status status = fromJson.getStatus();
								if(status.getSucceed().equals("1")){
									TopListData data = fromJson.getData();
//									String img = data.getImg();
//									UILUtils.displayImageNoAnim(img, top_img);
									List<ProductInfo> list = data.getList();
//									mListData.clear();
									mListData.addAll(list);
									topAdapter.notifyDataSetChanged();
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
			}.sendEmptyMessageDelayed(0, 1000);
		}

	}
	
	
	
	
	
	

}
