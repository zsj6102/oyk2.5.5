package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.personcenterjson.WuLiuDetail;
import zsj.com.oyk255.example.ouyiku.personcenterjson.WuLiuDetailDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.example.ouyiku.wuliuline.CustomNodeListView;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class WuliuActivity extends OykActivity implements OnClickListener{

	private CustomNodeListView mListView;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String mExphone;//快递电话
	private String mExpressname;//快递名称
	private String mExpressnum;//快递编号
	private String mOrdernum;//订单号
	private TextView m_state;
	ArrayList<WuLiuDetailDatum> mData=new ArrayList<WuLiuDetailDatum>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wuliu);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		
		Intent intent = getIntent();
		mExphone = intent.getStringExtra("exphone");
		mExpressname = intent.getStringExtra("expressname");
		mExpressnum = intent.getStringExtra("expressnum");
		mOrdernum = intent.getStringExtra("ordernum");
		initUI();
		initData();
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.WuLiuDetailURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("com", mExpressname);
		map.put("nu", mExpressnum);
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				
				Log.e("arg0", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				WuLiuDetail fromJson = gson.fromJson(arg0, WuLiuDetail.class);
				List<WuLiuDetailDatum> data = fromJson.getData();
				String state = fromJson.getState();
				
				switch (state) {
				case "0":
					m_state.setText("在途中..");
					break;
				case "1":
					m_state.setText("已揽件");
					break;
				case "2":
					m_state.setText("疑难");
					break;
				case "3":
					m_state.setText("已签收");
					break;
				case "4":
					m_state.setText("退签");
					break;
				case "5":
					m_state.setText("同城派送中..");
					break;
				case "6":
					m_state.setText("退回");
				case "7":
					m_state.setText("转单");
					break;

				default:
					break;
				}
				mData.clear();
				mData.addAll(data);
				mListView.setAdapter(new WuLiuAdapter());
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		findViewById(R.id.wuliu_back).setOnClickListener(this);
		m_state = (TextView) findViewById(R.id.wuliu_state);
		TextView m_company = (TextView) findViewById(R.id.wuliu_company);
		m_company.setText(mExpressname);
		TextView m_danhao = (TextView) findViewById(R.id.wuliu_danhao);
		m_danhao.setText(mExpressnum);
		TextView m_phone = (TextView) findViewById(R.id.wuliu_phone);
		m_phone.setText(mExphone);
		
		mListView = (CustomNodeListView) findViewById(R.id.customNodeListView1);
		
		
		
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
				layout=getLayoutInflater().inflate(R.layout.wuliu_detail_item, null);
				holder=new ViewHolder();
				holder.mInfo = (TextView) layout.findViewById(R.id.wuliu_info);
				holder.mTime = (TextView) layout.findViewById(R.id.wuliu_time);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder= (ViewHolder) layout.getTag();
			}
			WuLiuDetailDatum wuLiuDetailDatum = mData.get(position);
			String context = wuLiuDetailDatum.getContext();
			String time = wuLiuDetailDatum.getTime();
			holder.mInfo.setText(context);
			holder.mTime.setText(time);
			if(position > 0){
				holder.mInfo.setTextColor(Color.parseColor("#CDCDCD"));
				holder.mTime.setTextColor(Color.parseColor("#CDCDCD"));
			}else{
				holder.mInfo.setTextColor(Color.parseColor("#5FCC7C"));
				holder.mTime.setTextColor(Color.parseColor("#5FCC7C"));
			}
			
			return layout;
		}
		
	}
	class ViewHolder{
		TextView mInfo;
		TextView mTime;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wuliu_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
