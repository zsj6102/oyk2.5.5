package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.MyComment_pop;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_signature_pop;
import zsj.com.oyk255.example.ouyiku.hotjson.Comment;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureComment;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureCommentDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class MoreCommentActivity extends OykActivity implements OnClickListener{

	private ListView mListView;
	ArrayList<HotPictureCommentDatum> mCommentData=new ArrayList<HotPictureCommentDatum>();
	private SharedPreferences sp;
	private String userid;
	private String token;
	private String talent_post_id;
	private PersonData_signature_pop personData_signature_pop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_comment);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		PushAgent.getInstance(this).onAppStart();
		
		Intent intent = getIntent();
		talent_post_id = intent.getStringExtra("talent_post_id");
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		initData();
	}
	
	private MyHotPicture myHotPicture;
	private MyComment_pop myComment_pop;

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String commentUrl= Constant.URL.HotPictureCommentlURL+"&talent_post_id="+talent_post_id;
		HTTPUtils.get(this, commentUrl, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
					Gson gson = new Gson();
					HotPictureComment fromJson = gson.fromJson(arg0, HotPictureComment.class);
					Status status = fromJson.getStatus();
					String succeed = status.getSucceed();
					if(succeed.equals("1")){
						mCommentData.clear();
						List<HotPictureCommentDatum> data = fromJson.getData();
						mCommentData.addAll(data);
						myHotPicture = new MyHotPicture();
						
						mListView.setAdapter(myHotPicture);
					}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		findViewById(R.id.morecomment_back).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.morecomment_listview);
		personData_signature_pop = new PersonData_signature_pop(this);
		myComment_pop = new MyComment_pop(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				myComment_pop.showAtLocation(findViewById(R.id.morecommentlayout), Gravity.CENTER, 0, 0);
				TextView mAllComment = (TextView) myComment_pop.view.findViewById(R.id.pop_comment_content);
				TextView pop_comment_back = (TextView) myComment_pop.view.findViewById(R.id.pop_comment_back);
				pop_comment_back.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						myComment_pop.dismiss();
					}
				});
				HotPictureCommentDatum hotPictureCommentDatum = mCommentData.get(position);
				String content = hotPictureCommentDatum.getContent();
				
				mAllComment.setText(content);
				
				
			}
		});
	}
	
	class MyHotPicture extends BaseAdapter{

		

		@Override
		public int getCount() {
			return mCommentData.size();
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
				layout=getLayoutInflater().inflate(R.layout.hotpicture_listview_item, null);
				holder=new ViewHolder();
				holder.mNickIMG = (ImageView) layout.findViewById(R.id.hotpicture_comment_item_img);
				holder.mReport = (TextView) layout.findViewById(R.id.hotpicture_comment_item_report);
				holder.mNickname = (TextView) layout.findViewById(R.id.hotpicture_comment_item_name);
				holder.mTime = (TextView) layout.findViewById(R.id.hotpicture_comment_item_time);
				holder.mHuifu = (TextView) layout.findViewById(R.id.hotpicture_comment_item_reply);
				holder.mComment = (TextView) layout.findViewById(R.id.hotpicture_comment_item_comment);
				layout.setTag(holder);
			}else{
				layout  = convertView;
				holder = (ViewHolder) layout.getTag();
			}
			HotPictureCommentDatum hotPictureCommentDatum = mCommentData.get(position);
			String content = hotPictureCommentDatum.getContent();
			String createTime = hotPictureCommentDatum.getCreateTime();
			final String nickname = hotPictureCommentDatum.getNickname();
			String photo = hotPictureCommentDatum.getPhoto();
			final String userId2 = hotPictureCommentDatum.getUserId();//发表评论人id
			
			
			holder.mNickname.setText(nickname);
			holder.mTime.setText(createTime);
			holder.mComment.setText(content);
			UILUtils.displayImageNoAnim(photo, holder.mNickIMG);
			holder.mHuifu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(userid.equals("")){
						startActivity(new Intent(MoreCommentActivity.this, LoginActivity.class));
					}else{
						
						personData_signature_pop.showAtLocation(findViewById(R.id.morecommentlayout), Gravity.CENTER, 0, 0);
						TextView mSend = (TextView) personData_signature_pop.view.findViewById(R.id.pop_send);
						final EditText mSignature = (EditText) personData_signature_pop.view.findViewById(R.id.pop_signature_et);
						mSignature.setText("");
						mSend.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								final ZProgressHUD progressHUD = ZProgressHUD.getInstance(MoreCommentActivity.this); 
								progressHUD.setMessage("加载中");
						    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
						    	progressHUD.show();
								String mContent = mSignature.getText().toString().trim();
								String HuifuUrl=Constant.URL.HotPictureSendCommentlURL;
								HashMap<String, String> map=new HashMap<String, String>();
								map.put("uid", userid);
								map.put("t_con", mContent);
								map.put("talent_id", talent_post_id);
								map.put("tuid", userId2);
								map.put("tuname", nickname);
								
								HTTPUtils.post(MoreCommentActivity.this, HuifuUrl, map, new VolleyListener() {
									
									@Override
									public void onResponse(String arg0) {
										progressHUD.dismiss();
										Gson gson = new Gson();
										Comment fromJson = gson.fromJson(arg0, Comment.class);
										Status status = fromJson.getStatus();
										String succeed = status.getSucceed();
										if(succeed.equals("1")){
											Toast.makeText(MoreCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
											initData();
											personData_signature_pop.dismiss();
											
										}
										
									}
									
									@Override
									public void onErrorResponse(VolleyError arg0) {
										progressHUD.dismiss();
										
									}
								});
								
							}
						});
					}
					
				}
			});
			
			holder.mReport.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ToastUtils.toast(MoreCommentActivity.this, "举报成功");
				}
			});
			
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView mNickIMG;
		TextView mNickname;
		TextView mComment;
		TextView mTime;
		TextView mReport;
		TextView mHuifu;
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
					if(mCommentData.size()==0){
						initData();
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}else{
						int size = mCommentData.size();
						String commentUrl=Constant.URL.HotPictureCommentlURL+"&talent_post_id="+talent_post_id+"&num="+size;
						HTTPUtils.get(MoreCommentActivity.this, commentUrl, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
									Gson gson = new Gson();
									HotPictureComment fromJson = gson.fromJson(arg0, HotPictureComment.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										List<HotPictureCommentDatum> data = fromJson.getData();
										mCommentData.addAll(data);
										myHotPicture.notifyDataSetChanged();
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
			}.sendEmptyMessageDelayed(0, 2000);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.morecomment_back:
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
