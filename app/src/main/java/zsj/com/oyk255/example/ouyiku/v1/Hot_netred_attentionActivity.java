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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.hotjson.HisAttent;
import zsj.com.oyk255.example.ouyiku.hotjson.HisAttentDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Hot_netred_attentionActivity extends OykActivity implements OnClickListener{

	private ListView mListView;
	String token;
	String userid;
	private String tid;
	ArrayList<HisAttentDatum> mData=new ArrayList<HisAttentDatum>();
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_netred_attention);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		tid = intent.getStringExtra("tid");
		
		initUI();
		initData();
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.ATTENTListUrl+"&user_id="+userid+"&tid="+tid;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				Gson gson = new Gson();
				progressHUD.dismiss();
				HisAttent fromJson = gson.fromJson(arg0, HisAttent.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HisAttentDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					mListView.setAdapter(new AttentNetAdapter());
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		findViewById(R.id.attentionnet_back).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.attentionnet_listView);
		
	}
	class AttentNetAdapter extends BaseAdapter{

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
				layout = getLayoutInflater().inflate(R.layout.hotnetred_more_item, null);
				holder=new ViewHolder();
				holder.mPhoto = (ImageView) layout.findViewById(R.id.hotnetred_more_img);
				holder.mName = (TextView) layout.findViewById(R.id.hotnetred_more_name);
				holder.mSig = (TextView) layout.findViewById(R.id.hotnetred_more_signature);
				holder.mAttent = (ImageView) layout.findViewById(R.id.hotnetred_more_attention);
				layout.setTag(holder);
				
			}else{
				layout=convertView;
				holder=(ViewHolder) layout.getTag();
			}
				HisAttentDatum hisAttentDatum = mData.get(position);
				String nickname = hisAttentDatum.getNickname();
				String isfollow = hisAttentDatum.getIsfollow();
				String perSig = hisAttentDatum.getPerSig();
				String photo = hisAttentDatum.getPhoto();
				final String rankinguserId = hisAttentDatum.getRankinguserId();
				final String userId2 = hisAttentDatum.getUserId();
				
				holder.mName.setText(nickname);
				holder.mSig.setText(perSig);
				UILUtils.displayImageNoAnim(photo, holder.mPhoto);
				if(isfollow.equals("1")){
					holder.mAttent.setImageResource(R.mipmap.h_attention_click);
				}else{
					holder.mAttent.setImageResource(R.mipmap.h_attention);
				}
				
				holder.mAttent.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final ZProgressHUD progressHUD = ZProgressHUD.getInstance(Hot_netred_attentionActivity.this); 
						progressHUD.setMessage("加载中");
				    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
				    	progressHUD.show();
				    	if(userid.equals("")){
				    		startActivity(new Intent(Hot_netred_attentionActivity.this, LoginActivity.class));
				    	}else{
				    		
				    		String anttentUrl="http://a.ouyiku.com/?m=home&c=Talent&a=t_follow"+"&uid="+userid+"&tid="+userId2;
				    		
				    		HTTPUtils.get(Hot_netred_attentionActivity.this, anttentUrl, new VolleyListener() {
				    			
				    			@Override
				    			public void onResponse(String arg0) {
				    				progressHUD.dismiss();
				    				initData() ;
				    			}
				    			
				    			@Override
				    			public void onErrorResponse(VolleyError arg0) {
				    				progressHUD.dismiss();
				    				
				    			}
				    		});
				    	}
					}
				});
				
			return layout;
		}
		
	}
	
	class ViewHolder{
		ImageView mPhoto;
		TextView mName;
		TextView mSig;
		ImageView mAttent;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attentionnet_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
