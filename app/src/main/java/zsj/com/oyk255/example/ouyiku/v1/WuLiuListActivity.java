package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.personcenterjson.WuLiuList;
import zsj.com.oyk255.example.ouyiku.personcenterjson.WuLiuListDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class WuLiuListActivity extends OykActivity implements OnClickListener{

	private ListView mListview;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<WuLiuListDatum> mData=new ArrayList<WuLiuListDatum>();
	private ImageView mNull;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wu_liu_list);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
		
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.WuLiuListURL+"&user_id="+userid+"&token="+token;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				WuLiuList fromJson = gson.fromJson(arg0, WuLiuList.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<WuLiuListDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					if(mData.size()==0){
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						mListview.setAdapter(new WuLiuAdapter());
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
		mListview = (ListView) findViewById(R.id.wuliulist_listView);
		findViewById(R.id.wuliulist_back).setOnClickListener(this);
		mNull = (ImageView) findViewById(R.id.wuliu_null);
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(WuLiuListActivity.this, WuliuActivity.class);
				WuLiuListDatum wuLiuListDatum = mData.get(position);
				String exphone = wuLiuListDatum.getExphone();//快递电话
				String expressname = wuLiuListDatum.getExpressname();//快递名称
				String expressnum = wuLiuListDatum.getExpressnum();//快递单号
				String orderId = wuLiuListDatum.getOrderId();//订单id
				String ordernum = wuLiuListDatum.getOrdernum();//订单编号
				String productId = wuLiuListDatum.getProductId();//商品id
				intent.putExtra("exphone", exphone);
				intent.putExtra("expressname", expressname);
				intent.putExtra("expressnum", expressnum);
				intent.putExtra("ordernum", ordernum);
				startActivity(intent);
				
			}
		});
	}

	class WuLiuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
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
			ViewHolder holder=null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.wuliu_list_item, null);
				holder=new ViewHolder();
				holder.mLogo= (ImageView) layout.findViewById(R.id.wuliulist_logo);
				holder.mTitle = (TextView) layout.findViewById(R.id.wuliulist_title);
				holder.mPrice= (TextView) layout.findViewById(R.id.wuliulist_price);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			WuLiuListDatum wuLiuListDatum = mData.get(position);
			String title = wuLiuListDatum.getTitle();
			String phone1 = wuLiuListDatum.getPhone1();
			String price = wuLiuListDatum.getPrice();
			
			UILUtils.displayImageNoAnim(phone1, holder.mLogo);
			holder.mTitle.setText(title);
			holder.mPrice.setText(price);
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView mLogo;
		TextView mTitle;
		TextView mPrice;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wuliulist_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
