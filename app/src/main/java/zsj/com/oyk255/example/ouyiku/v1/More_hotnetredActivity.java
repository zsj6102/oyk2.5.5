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
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetRed;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class More_hotnetredActivity extends OykActivity implements OnClickListener{
	boolean isTouch;//是否点击关注
	private ListView mListView;

	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<HotNetDatum> mMoreHotData=new ArrayList<HotNetDatum>();
	
	private MoreNetAdapter moreNetAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_hotnetred);
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
		String MoreHotNetUrl= Constant.URL.FashionManHOTNETURL+"&rkuid="+"0"+"&uid="+userid;
		HTTPUtils.get(this, MoreHotNetUrl, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotNetRed fromJson = gson.fromJson(arg0, HotNetRed.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mMoreHotData.clear();
					List<HotNetDatum> data = fromJson.getData();
					mMoreHotData.addAll(data);
					moreNetAdapter = new MoreNetAdapter();
					
					mListView.setAdapter(moreNetAdapter );
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		findViewById(R.id.morenet_back).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.morenet_listView);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String useid = mMoreHotData.get(position).getUserId();
				String nickname = mMoreHotData.get(position).getNickname();
				Intent intent = new Intent(More_hotnetredActivity.this, Hot_netredActivity.class);
				intent.putExtra("tid", useid);
				intent.putExtra("hotnetTitle", nickname);
				startActivity(intent);
				
			}
		});
	}

	private ImageView mAttention;
	class MoreNetAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			return mMoreHotData.size();
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
				layout=getLayoutInflater().inflate(R.layout.hotnetred_more_item, null);
				holder=new ViewHolder();
				holder.mImg = (ImageView) layout.findViewById(R.id.hotnetred_more_img);
				holder.mNickName = (TextView) layout.findViewById(R.id.hotnetred_more_name);
				holder.mSignature = (TextView) layout.findViewById(R.id.hotnetred_more_signature);
				mAttention = (ImageView) layout.findViewById(R.id.hotnetred_more_attention);
				layout.setTag(holder);
			}else{
				layout  = convertView;
				//通过tag把layout对应的viewholder找到
				holder = (ViewHolder) layout.getTag();
			}
				final HotNetDatum hotNetDatum = mMoreHotData.get(position);
				holder.mNickName.setText(hotNetDatum.getNickname());
				holder.mSignature.setText(hotNetDatum.getPerSig());
				UILUtils.displayImageNoAnim(hotNetDatum.getPhoto(), holder.mImg);
				final String isfollow = hotNetDatum.getIsfollow();
				
				if(!userid.equals("") ){
					if(isfollow.equals("1")){
						mAttention.setImageResource(R.mipmap.h_attention_click);
					}else{
						mAttention.setImageResource(R.mipmap.h_attention);
					}
				}else{
					mAttention.setImageResource(R.mipmap.h_attention);
					
				}
				final String tid = hotNetDatum.getUserId();//网红id
				
				mAttention.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!userid.equals("") && !token.equals("")){
							
						isTouch=!isTouch;
						if(isTouch){
							String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+tid;
							
							HTTPUtils.get(More_hotnetredActivity.this, AttentionUrl, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									initData();
								}
								
								@Override
								public void onErrorResponse(VolleyError arg0) {
									
								}
							});
						}else{
							String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+tid;
							
							HTTPUtils.get(More_hotnetredActivity.this, AttentionUrl, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									initData();
								}
								
								@Override
								public void onErrorResponse(VolleyError arg0) {
									
								}
							});
						}
						}else{
							startActivity(new Intent(More_hotnetredActivity.this, LoginActivity.class));
							
						}
						
					}
				});
					
			
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView mImg;
		TextView mNickName;
		TextView mSignature;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.morenet_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	
}
