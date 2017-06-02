package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;
import com.umeng.message.PushAgent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.invitejson.InviteCord;
import zsj.com.oyk255.example.ouyiku.invitejson.InviteCordDatum;
import zsj.com.oyk255.example.ouyiku.invitejson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Invite_recordActivity extends OykActivity {
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<InviteCordDatum> mListData=new ArrayList<InviteCordDatum>();
	private ListView mListView;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_record);
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
		String url= Constant.URL.InviteRecordURL+"&user_id="+userid+"&token="+token;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				InviteCord fromJson = gson.fromJson(arg0, InviteCord.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<InviteCordDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					mListView.setAdapter(new ListViewAdapter());
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		mListView = (ListView) findViewById(R.id.inviterecord_listView);
		findViewById(R.id.inviterecord_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
//		
		
	}
	
	
	class ListViewAdapter  extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
				layout=getLayoutInflater().inflate(R.layout.invite_record_item, null);
				holder=new ViewHolder();
				holder.invite_record_name = (TextView) layout.findViewById(R.id.invite_record_name);
				holder.invite_record_money= (TextView) layout.findViewById(R.id.invite_record_money);
				holder.invite_record_time = (TextView) layout.findViewById(R.id.invite_record_time);
				layout.setTag(holder);
				
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			
			if(mListData!=null){
				InviteCordDatum inviteCordDatum = mListData.get(position);
				String gmoney = inviteCordDatum.getGmoney();
				String createTime = inviteCordDatum.getCreateTime();
				String userName = inviteCordDatum.getUserName();
				
				holder.invite_record_name.setText(userName);
				holder.invite_record_money.setText(gmoney);
				holder.invite_record_time.setText(createTime);
				
			}
			
			
			return layout;
		}
		
	}
	
	class ViewHolder{
		TextView invite_record_name;
		TextView invite_record_money;
		TextView invite_record_time;
		
	}
	
}
