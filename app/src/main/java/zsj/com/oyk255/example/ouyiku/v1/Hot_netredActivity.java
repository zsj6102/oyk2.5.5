package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetListview;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetListviewDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetRedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.RedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.CircleImageView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Hot_netredActivity extends OykActivity implements OnClickListener{

	boolean isTouch;//是否点击关注
	private ListView mListView;
	private View mHeader;
	private String tid;
	String token;
	String userid;
	private CircleImageView mHotnetred_img;
	private ImageView mHotnetred_level;
	private ImageView mHotnetred_sex;
	private TextView mNick;
	private TextView mSig;
	private TextView mAttentnum;
	private TextView mFansnum;
	int[] ImageID=new int[]{R.mipmap.l1,R.mipmap.l2,R.mipmap.l3,R.mipmap.l4,R.mipmap.l5,R.mipmap.l6,R.mipmap.l7,R.mipmap.l8,R.mipmap.l9,};
	private ImageView mAttention;
	private String title;
	ArrayList<HotNetListviewDatum> mListData=new ArrayList<HotNetListviewDatum>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_netred);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout)findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		tid = intent.getStringExtra("tid");
		title = intent.getStringExtra("hotnetTitle");
		Log.e("tid", tid);
		initUI();
		initData();
		initPicture();
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}
	private Mylistadapter mylistadapter;
	private SharedPreferences sp;
	private void initPicture() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.FashionManHotPictureURL+"&uid="+tid;
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
					mylistadapter = new Mylistadapter();
					
					mListView.setAdapter(mylistadapter);
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
		//headview数据
		String url=Constant.URL.HotNetRedDetailURL+"&tid="+tid+"&uid="+userid; 
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
					String nickname = data.getNickname();
					String photo = data.getPhoto();
					String perSig = data.getPerSig();
					String followcount = data.getFollowcount();
					String fanscount = data.getFanscount();
					String sex = data.getSex();
					String level = data.getLevel();
					String isfollow = data.getIsfollow();//是否关注
					String netuseid = data.getUserId();//该网红的id
					Log.e("netuseid", netuseid);
					if(!userid.equals("") ){
						if(isfollow.equals("1")){
							mAttention.setImageResource(R.mipmap.h_attention_b_click);
						}else{
							mAttention.setImageResource(R.mipmap.h_attention_b);
						}
					}else{
						mAttention.setImageResource(R.mipmap.h_attention_b);
						
					}
					
					mNick.setText(nickname);
					mSig.setText(perSig);
					mAttentnum.setText(followcount);
					mFansnum.setText(fanscount);
					UILUtils.displayImageNoAnim(photo, mHotnetred_img);
					if(sex.equals("1")){
						mHotnetred_sex.setImageResource(R.mipmap.girl1);
					}else{
						mHotnetred_sex.setImageResource(R.mipmap.boy1);
					}
					int level_int = Integer.valueOf(level);
					int imgid=level_int-1;
					mHotnetred_level.setImageResource(ImageID[imgid]);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		
//		mQuickReturnView = findViewById(R.id.hotnetred_title);
		mListView = (ListView) findViewById(R.id.hotnetred_listview);
		mHeader = getLayoutInflater().inflate(R.layout.hotnetred_topview_item, null);
//		mPlaceHolder = mHeader.findViewById(R.id.placeholder);
		mHeader.findViewById(R.id.hotnetred_guanzhu).setOnClickListener(this);
		mHeader.findViewById(R.id.hotnetred_fans).setOnClickListener(this);
		
		mAttention = (ImageView) findViewById(R.id.hotnetred_attention);//关注
		TextView mTitle = (TextView) findViewById(R.id.hotnetred_titlename);
		mTitle.setText(title);
		findViewById(R.id.hotnetred_back).setOnClickListener(this);
		
		mHotnetred_img = (CircleImageView) mHeader.findViewById(R.id.hotnetred_img);
		mHotnetred_level = (ImageView) mHeader.findViewById(R.id.hotnetred_level);
		mHotnetred_sex = (ImageView) mHeader.findViewById(R.id.hotnetred_sex);
		mNick = (TextView) mHeader.findViewById(R.id.hotnetred_name);
		mSig = (TextView) mHeader.findViewById(R.id.hotnetred_qianming);
		mAttentnum = (TextView) mHeader.findViewById(R.id.attentnum);
		mFansnum = (TextView) mHeader.findViewById(R.id.fansnum);
		
		mListView.addHeaderView(mHeader);
		
		
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					
					Intent intent = new Intent(Hot_netredActivity.this, HotPictureActivity.class);
					HotNetListviewDatum hotNetListviewDatum = mListData.get(position-1);
					String talentPostId = hotNetListviewDatum.getTalentPostId();
					String nickname = hotNetListviewDatum.getNickname();
					hotNetListviewDatum.getIsclickgood();
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
		
		
		mAttention.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!userid.equals("") && !token.equals("")){
					
				isTouch=!isTouch;
				if(isTouch){
					String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+tid;
					final ZProgressHUD progressHUD = ZProgressHUD.getInstance(Hot_netredActivity.this); 
					progressHUD.setMessage("加载中");
			    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
			    	progressHUD.show();
					HTTPUtils.get(Hot_netredActivity.this, AttentionUrl, new VolleyListener() {
						
						@Override
						public void onResponse(String arg0) {
							progressHUD.dismiss();
							initData();
						}
						
						@Override
						public void onErrorResponse(VolleyError arg0) {
							progressHUD.dismiss();
						}
					});
				}else{
					String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+tid;
					
					HTTPUtils.get(Hot_netredActivity.this, AttentionUrl, new VolleyListener() {
						
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
					startActivity(new Intent(Hot_netredActivity.this, LoginActivity.class));
				}
				
			}
		});
		
	}

//	private void initQuickReturnView() {
//		mListView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			
//			@Override
//			public void onGlobalLayout() {
//				mQuickReturnHeight = mQuickReturnView.getHeight();
//				mListView.computeScrollY();
//				mCachedVerticalScrollRange = mListView.getListHeight();
//				
//			}
//		});
//		
//		
//		mListView.setOnScrollListener(new OnScrollListener() {
//			@SuppressLint("NewApi")
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//
//				mScrollY = 0;
//				int translationY = 0;
//
//				if (mListView.scrollYIsComputed()) {
//					mScrollY = mListView.getComputedScrollY();
//				}
//
//				rawY = mPlaceHolder.getTop()
//						- Math.min(
//								mCachedVerticalScrollRange
//										- mListView.getHeight(), mScrollY);
//
//				switch (mState) {
//				case STATE_OFFSCREEN:
//					if (rawY <= mMinRawY) {
//						mMinRawY = rawY;
//					} else {
//						mState = STATE_RETURNING;
//					}
//					translationY = rawY;
//					break;
//
//				case STATE_ONSCREEN:
//					if (rawY < -mQuickReturnHeight) {
//						System.out.println("test3");
//						mState = STATE_OFFSCREEN;
//						mMinRawY = rawY;
//					}
//					translationY = rawY;
//					break;
//
//				case STATE_RETURNING:
//
//					if (translationY > 0) {
//						translationY = 0;
//						mMinRawY = rawY - mQuickReturnHeight;
//					}
//
//					else if (rawY > 0) {
//						mState = STATE_ONSCREEN;
//						translationY = rawY;
//					}
//
//					else if (translationY < -mQuickReturnHeight) {
//						mState = STATE_OFFSCREEN;
//						mMinRawY = rawY;
//
//					} else if (mQuickReturnView.getTranslationY() != 0
//							&& !noAnimation) {
//						noAnimation = true;
//						anim = new TranslateAnimation(0, 0,
//								-mQuickReturnHeight, 0);
//						anim.setFillAfter(true);
//						anim.setDuration(250);
//						mQuickReturnView.startAnimation(anim);
//						anim.setAnimationListener(new AnimationListener() {
//
//							@Override
//							public void onAnimationStart(Animation animation) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onAnimationRepeat(Animation animation) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onAnimationEnd(Animation animation) {
//								noAnimation = false;
//								mMinRawY = rawY;
//								mState = STATE_EXPANDED;
//							}
//						});
//					}
//					break;
//
//				case STATE_EXPANDED:
//					if (rawY < mMinRawY - 2 && !noAnimation) {
//						noAnimation = true;
//						anim = new TranslateAnimation(0, 0, 0,
//								-mQuickReturnHeight);
//						anim.setFillAfter(true);
//						anim.setDuration(250);
//						anim.setAnimationListener(new AnimationListener() {
//
//							@Override
//							public void onAnimationStart(Animation animation) {
//							}
//
//							@Override
//							public void onAnimationRepeat(Animation animation) {
//
//							}
//
//							@Override
//							public void onAnimationEnd(Animation animation) {
//								noAnimation = false;
//								mState = STATE_OFFSCREEN;
//							}
//						});
//						mQuickReturnView.startAnimation(anim);
//					} else if (translationY > 0) {
//						translationY = 0;
//						mMinRawY = rawY - mQuickReturnHeight;
//					}
//
//					else if (rawY > 0) {
//						mState = STATE_ONSCREEN;
//						translationY = rawY;
//					}
//
//					else if (translationY < -mQuickReturnHeight) {
//						mState = STATE_OFFSCREEN;
//						mMinRawY = rawY;
//					} else {
//						mMinRawY = rawY;
//					}
//				}
//				/** this can be used if the build is below honeycomb **/
//				if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
//					anim = new TranslateAnimation(0, 0, translationY,
//							translationY);
//					anim.setFillAfter(true);
//					anim.setDuration(0);
//					mQuickReturnView.startAnimation(anim);
//				} else {
//					mQuickReturnView.setTranslationY(translationY);
//				}
//
//			}
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//			}
//		});
//	
//	}

	class Mylistadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size();
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
			ViewHolder holder=null;
			if(convertView==null){
				
				view=getLayoutInflater().inflate(R.layout.hotnetred_scrollview_item, null);
				holder=new ViewHolder();
				holder.mContent = (TextView) view.findViewById(R.id.hotnetred_item_content);
				holder.mCreatTime = (TextView) view.findViewById(R.id.hotnetred_item_time);
				holder.mCommentNum = (TextView) view.findViewById(R.id.hotnetred_item_zannum);
				holder.mZanNum = (TextView) view.findViewById(R.id.hotnetred_item_commentnum);
				holder.mPicture = (ImageView) view.findViewById(R.id.hotnetred_item_img);
				view.setTag(holder);
			}else{
				view=convertView;
				holder = (ViewHolder) view.getTag();
			}
			HotNetListviewDatum hotNetListviewDatum = mListData.get(position);
			String talentTitle = hotNetListviewDatum.getTalentTitle();
			String createTime = hotNetListviewDatum.getCreateTime();
			String clickgood = hotNetListviewDatum.getClickgood();
			String messagecount = hotNetListviewDatum.getMessagecount();
			String postImage = hotNetListviewDatum.getPostImage();
			holder.mContent.setText(talentTitle);
			holder.mCreatTime.setText(createTime);
			holder.mCommentNum.setText(messagecount);
			holder.mZanNum.setText(clickgood);
			UILUtils.displayImageNoAnim(postImage, holder.mPicture);
			return view;
		}
		
	}
	class ViewHolder{
		ImageView mPicture;
		TextView mContent;
		TextView mCreatTime;
		TextView mCommentNum;
		TextView mZanNum;
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hotnetred_guanzhu:
			Intent intent = new Intent(this, Hot_netred_attentionActivity.class);
			intent.putExtra("tid", tid);
			startActivity(intent);
			break;
		case R.id.hotnetred_fans:
			Intent intent2 = new Intent(this, Hot_netred_fansActivity.class);
			intent2.putExtra("tid", tid);
			startActivity(intent2);
			break;
		case R.id.hotnetred_back:
			finish();
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
					
					if(mListData.size()==0){
						initPicture();
					}else{
						int size = mListData.size();
						
						String url=Constant.URL.FashionManHotPictureURL+"&uid="+tid+"&num="+size;
						HTTPUtils.get(Hot_netredActivity.this, url, new VolleyListener() {
							
							@Override
							public void onResponse(String arg0) {
								Gson gson = new Gson();
								HotNetListview fromJson = gson.fromJson(arg0, HotNetListview.class);
								Status status = fromJson.getStatus();
								String succeed = status.getSucceed();
								if(succeed.equals("1")){
									List<HotNetListviewDatum> data = fromJson.getData();
									mListData.addAll(data);
									mylistadapter.notifyDataSetChanged();
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
