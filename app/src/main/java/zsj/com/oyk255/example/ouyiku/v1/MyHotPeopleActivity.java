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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetListview;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetListviewDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetRedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.RedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class MyHotPeopleActivity extends OykActivity implements OnClickListener{

	private GridView mGridView;
	String token;
	String userid;
	private int screenWidth;
	
	ArrayList<HotNetListviewDatum> mListData=new ArrayList<HotNetListviewDatum>();
	private TextView attentNum;
	private TextView fansNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_hot_people);
		PushAgent.getInstance(this).onAppStart();
		screenWidth = ScreenUtils.getScreenWidth(this);
		SharedPreferences sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
		initNum();
	}

	private void initNum() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.HotNetRedDetailURL+"&tid="+userid+"&uid="+userid;
			HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotNetRedDetail fromJson = gson.fromJson(arg0, HotNetRedDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					RedDetail data = fromJson.getData();
					String followcount = data.getFollowcount();
					String fanscount = data.getFanscount();
					attentNum.setText(followcount);
					fansNum.setText(fanscount);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.FashionManHotPictureURL+"&uid="+userid;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotNetListview fromJson = gson.fromJson(arg0, HotNetListview.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mListData.clear();
					List<HotNetListviewDatum> data = fromJson.getData();
					mListData.addAll(data);
					mGridView.setAdapter(new MyHotAdapter());
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	

	private void initUI() {
		findViewById(R.id.back_myhot).setOnClickListener(this);
		mGridView = (GridView) findViewById(R.id.myhot_gridView);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					startActivity(new Intent(MyHotPeopleActivity.this, Creat_fashionActivity.class));
				}else{
					Intent intent = new Intent(MyHotPeopleActivity.this, HotPictureActivity.class);
					HotNetListviewDatum hotNetListviewDatum = mListData.get(position-1);
					String talentPostId = hotNetListviewDatum.getTalentPostId();
					String nickname = hotNetListviewDatum.getNickname();
					String photo = hotNetListviewDatum.getPhoto();
					String userId2 = hotNetListviewDatum.getUserId();
					
					intent.putExtra("faburenID", userId2);
					intent.putExtra("nickphoto", photo);
					intent.putExtra("talent_post_id", talentPostId);
					intent.putExtra("nicknametitle", nickname);
					startActivity(intent);
				}
				
			}
		});
		findViewById(R.id.myhot_attent).setOnClickListener(this);
		findViewById(R.id.myhot_fans).setOnClickListener(this);
		attentNum = (TextView) findViewById(R.id.myhot_num);
		fansNum = (TextView) findViewById(R.id.myhot_fansnum);
		
		
		
	}

	class MyHotAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size()+1;
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
			ViewHolder holder;
			if(convertView==null){
				view=getLayoutInflater().inflate(R.layout.myhot_gridview_item, null);
				holder=new ViewHolder();
				holder.mImg = (ImageView) view.findViewById(R.id.myhot_item_img);
				holder.mImg.getLayoutParams().height=(screenWidth-10)/2;
				view.setTag(holder);
			}else{
				view=convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			if(position==0){
				holder.mImg.setImageResource(R.mipmap.establish);
				
			}else{
				HotNetListviewDatum hotNetListviewDatum = mListData.get(position-1);
				String postImage = hotNetListviewDatum.getPostImage();
				UILUtils.displayImage(postImage, holder.mImg);
				
			}
			return view;
		}
		
	}
	class ViewHolder{
		ImageView mImg;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_myhot:
			finish();
			break;
		case R.id.myhot_attent:
//			 MyAttentList();
			 Intent intent = new Intent(MyHotPeopleActivity.this, Hot_netred_attentionActivity.class);
			 intent.putExtra("tid", userid);
			 startActivity(intent);
			break;
		case R.id.myhot_fans:
			 Intent intent2 = new Intent(MyHotPeopleActivity.this, Hot_netred_fansActivity.class);
			 intent2.putExtra("tid", userid);
			 startActivity(intent2);
			break;

		default:
			break;
		}
		
	}
}
