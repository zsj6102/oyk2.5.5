package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import zsj.com.oyk255.example.ouyiku.hotjson.HotPicture;
import zsj.com.oyk255.example.ouyiku.hotjson.PictureDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Fashion_groupActivity extends OykActivity {

	private MyGridView mGrid;
	private TextView mTitle;
	private String cattalent;
	private String title;
	ArrayList<PictureDatum> mData=new ArrayList<PictureDatum>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fashion_group);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout)findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		Intent intent = getIntent();
		cattalent = intent.getStringExtra("cattalentId");
		title = intent.getStringExtra("title");
		initUI();
		initData();
	}

	private FashionGroup fashionGroup;
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.FashionManHotPictureURL+"&cattalent_id="+cattalent;
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
					Gson gson = new Gson();
					HotPicture fromJson = gson.fromJson(arg0, HotPicture.class);
					Status status = fromJson.getStatus();
					String succeed = status.getSucceed();
					if(succeed.equals("1")){
						List<PictureDatum> data = fromJson.getData();
						mData.clear();
						mData.addAll(data);
						fashionGroup = new FashionGroup();
						
						mGrid.setAdapter(fashionGroup);
						
					}
					
					
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		mGrid = (MyGridView) findViewById(R.id.fashiongroup_gridview);
		
		mTitle = (TextView) findViewById(R.id.fashion_group_title);
		mTitle.setText(title);
		mGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(Fashion_groupActivity.this, HotPictureActivity.class);
				PictureDatum pictureDatum = mData.get(position);
				String talentPostId = pictureDatum.getTalentPostId();
				String nickname = pictureDatum.getNickname();
				String isclickgood = pictureDatum.getIsclickgood();
				String photo = pictureDatum.getPhoto();
				String userId2 = pictureDatum.getUserId();
				
				intent.putExtra("faburenID", userId2);
				intent.putExtra("nickphoto", photo);
				intent.putExtra("talent_post_id", talentPostId);
				intent.putExtra("nicknametitle", nickname);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.fashiongroup_back_view).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
	}
	
	
	class FashionGroup extends BaseAdapter{

		@Override
		public int getCount() {
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
			PictureHolder pictureHolder=null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.gridview_fashion_item, null);
				pictureHolder=new PictureHolder();
				pictureHolder.mImg = (ImageView) layout.findViewById(R.id.fashion_item_img);
				pictureHolder.mTitle = (TextView) layout.findViewById(R.id.fashion_item_name);
				pictureHolder.mNick = (TextView) layout.findViewById(R.id.fashion_item_nicheng);
				pictureHolder.mZanNUM = (TextView) layout.findViewById(R.id.fashion_item_zannum);
				pictureHolder.mTou = (ImageView) layout.findViewById(R.id.fashion_item_touxiang);
				pictureHolder.mZan = (ImageView) layout.findViewById(R.id.fashion_item_zan);
				layout.setTag(pictureHolder);
			}else{
				layout  = convertView;
				pictureHolder = (PictureHolder) layout.getTag();
			}
			PictureDatum pictureDatum = mData.get(position);
			pictureHolder.mTitle.setText(pictureDatum.getTalentTitle());
			pictureHolder.mNick.setText(pictureDatum.getNickname());
			pictureHolder.mZanNUM.setText(pictureDatum.getClickgood());
			UILUtils.displayImageNoAnim(pictureDatum.getPostImage(), pictureHolder.mImg);
			UILUtils.displayImageNoAnim(pictureDatum.getPhoto(), pictureHolder.mTou);
			
			return layout;
		}
		
	}
	
	class PictureHolder{
		ImageView mImg;
		TextView mTitle;
		TextView mNick;
		TextView mZanNUM;
		ImageView mTou;
		ImageView mZan;
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
					if(mData.size()==0){
						initData();
					}else{
						int size = mData.size();
						String url=Constant.URL.FashionManHotPictureURL+"&cattalent_id="+cattalent+"&num="+size;
						HTTPUtils.get(Fashion_groupActivity.this, url, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
									Gson gson = new Gson();
									HotPicture fromJson = gson.fromJson(arg0, HotPicture.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<PictureDatum> data = fromJson.getData();
										mData.addAll(data);
										fashionGroup.notifyDataSetChanged();
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
