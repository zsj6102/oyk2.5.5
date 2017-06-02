package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.orderjson.Good;
import zsj.com.oyk255.example.ouyiku.orderjson.MyOrder;
import zsj.com.oyk255.example.ouyiku.orderjson.MyOrderDatum;
import zsj.com.oyk255.example.ouyiku.orderjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
import zsj.com.oyk255.example.ouyiku.v1.HotSaleActivity;
import zsj.com.oyk255.example.ouyiku.v1.Order_detail_daifaActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Order_daiFaFragment extends Fragment implements OnClickListener{
	
	private View view;
	private ListView mListview;
	ArrayList<MyOrderDatum> mData=new ArrayList<MyOrderDatum>();
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private ImageView mImgNull;
	public Order_daiFaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_order_dai_fa, container,
					false);
			((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener());
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
		}
		return view;
	}
	private DaifaorderAdapter daifaorderAdapter;

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		userid = sp.getString("userid", "");
		String DaiFuUrl= Constant.URL.MyOrderURL+"&user_id="+userid+"&status="+2;
		HTTPUtils.get(getActivity(), DaiFuUrl, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MyOrder fromJson = gson.fromJson(arg0, MyOrder.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mData.clear();
					List<MyOrderDatum> data = fromJson.getData();
					mData.addAll(data);
					if(mData.size()==0){
						mImgNull.setVisibility(View.VISIBLE);
					}else{
						mImgNull.setVisibility(View.GONE);
						daifaorderAdapter = new DaifaorderAdapter();
						
						mListview.setAdapter(daifaorderAdapter);
					}
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		mListview = (ListView) view.findViewById(R.id.order_daifa_listview);
		mImgNull = (ImageView) view.findViewById(R.id.order_daifa_null);
		mImgNull.setOnClickListener(this);
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String orderId = mData.get(position).getOrderId();
				Intent intent = new  Intent(getActivity(),Order_detail_daifaActivity.class);
				intent.putExtra("orderId", orderId);
				startActivity(intent);
			}
		});
	}
	
	class DaifaorderAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mData.size();
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
			ViewHolder holder=null;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.order_daifa_item, null);
				holder=new ViewHolder();
				holder.order_brand_img = (ImageView) layout.findViewById(R.id.order_brand_img);
				holder.order_brand_name = (TextView) layout.findViewById(R.id.order_brand_name);
				holder.order_goods_name = (TextView) layout.findViewById(R.id.order_goods_name);
				holder.order_goods_attr = (TextView) layout.findViewById(R.id.order_goods_color);
				holder.order_goods_img = (ImageView) layout.findViewById(R.id.order_goods_img);
				holder.order_goods_newprice = (TextView) layout.findViewById(R.id.order_goods_newprice);
				holder.order_goods_oldprice = (TextView) layout.findViewById(R.id.order_goods_oldprice);
				holder.order_goods_num = (TextView) layout.findViewById(R.id.order_goods_num);
				holder.order_goods_allprice = (TextView) layout.findViewById(R.id.order_goods_allprice);
				holder.order_goods_tixing = (TextView) layout.findViewById(R.id.order_goods_tixing);
				holder.tobrandlayout = (LinearLayout) layout.findViewById(R.id.tobrandlayout);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
				MyOrderDatum myOrderDatum = mData.get(position);
				String price = myOrderDatum.getPrice();//总价
				List<Good> goods = myOrderDatum.getGoods();
				Good good = goods.get(0);
				
				String attr = good.getAttr();
				String brandlogo = good.getBrandlogo();
				final String brandmerchantId = good.getBrandmerchantId();
				String brandtitle = good.getBrandtitle();
				String image = good.getImage();
				String price2 = good.getPrice();//单价
				String num = good.getNum();
				String title = good.getTitle();
				
				holder.order_brand_name.setText(brandtitle);
				holder.order_goods_name.setText(title);
				holder.order_goods_attr.setText(attr);
				holder.order_goods_newprice.setText("￥"+price2);
				holder.order_goods_num.setText("x"+num);
				holder.order_goods_allprice.setText("￥"+price);
				UILUtils.displayImageNoAnim(brandlogo, holder.order_brand_img);
				UILUtils.displayImageNoAnim(image, holder.order_goods_img);
				holder.tobrandlayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
						intent.putExtra("mSevenShop1", brandmerchantId);
						startActivity(intent);
					}
				});
				
				holder.order_goods_tixing.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ToastUtils.toast(getActivity(), "提醒成功");
						
					}
				});
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView order_brand_img;
		TextView order_brand_name;
		TextView order_goods_name;
		TextView order_goods_attr;
		ImageView order_goods_img;
		TextView order_goods_newprice;
		TextView order_goods_oldprice;
		TextView order_goods_num;
		TextView order_goods_allprice;
		TextView order_goods_tixing;
		LinearLayout tobrandlayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_daifa_null:
			startActivity(new Intent(getActivity(), HotSaleActivity.class));
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
					
					if(mData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
				    	progressHUD.setMessage("加载中");
				    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
				    	progressHUD.show();
						userid = sp.getString("userid", "");
						int size = mData.size();
						
						String DaiFuUrl=Constant.URL.MyOrderURL+"&user_id="+userid+"&status="+2+"&num="+size;
						HTTPUtils.get(getActivity(), DaiFuUrl, new VolleyListener() {
							

							@Override
							public void onResponse(String arg0) {
								progressHUD.dismiss();
								Gson gson = new Gson();
								MyOrder fromJson = gson.fromJson(arg0, MyOrder.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<MyOrderDatum> data = fromJson.getData();
									mData.addAll(data);
										mImgNull.setVisibility(View.GONE);
										daifaorderAdapter.notifyDataSetChanged();
										pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);	
								}else{
									pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
								}
							}
							
							@Override
							public void onErrorResponse(VolleyError arg0) {
								progressHUD.dismiss();
								
							}
						});
						
					}
				}
			}.sendEmptyMessageDelayed(0, 1000);
		}

	}
	
}
