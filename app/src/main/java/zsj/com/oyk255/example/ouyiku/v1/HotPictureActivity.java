package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.MyComment_pop;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_signature_pop;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.hotjson.Comment;
import zsj.com.oyk255.example.ouyiku.hotjson.HisAttent;
import zsj.com.oyk255.example.ouyiku.hotjson.HisAttentDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotNetRedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureComment;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureCommentDatum;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureDetailData;
import zsj.com.oyk255.example.ouyiku.hotjson.HotPictureZan;
import zsj.com.oyk255.example.ouyiku.hotjson.RedDetail;
import zsj.com.oyk255.example.ouyiku.hotjson.Status;
import zsj.com.oyk255.example.ouyiku.hotjson.ZanData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.CircleImageView;
import zsj.com.oyk255.example.ouyiku.view.HorizontalListView;
import zsj.com.oyk255.example.ouyiku.view.MyHorizontalScrollView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class HotPictureActivity extends OykActivity implements OnClickListener{
	boolean isTouch;
	private ScrollViewWithListView mListview;
	private View topview;
	private MyHorizontalScrollView mHorizontalScrollView;
	private View footview;
	private String talent_post_id;
	private SharedPreferences sp;
	private String userid;
	private String token;
	private ScrollViewWithListView mPictureListview;
	private TextView mTitle;
	private TextView mTime;
	private TextView mContent;
	private TextView mZanNum;
	private TextView mCommentNum;
	private ScrollView mScroll;
	private String title;
	private String nickphoto;
	private String faburenID;
	ArrayList<HotPictureCommentDatum> mCommentData=new ArrayList<HotPictureCommentDatum>();
	private TextView mCommentNum2;
	private PersonData_signature_pop personData_signature_pop;
	private ImageView mZanImg;
	private ImageView mAttent;
	private TextView mFanNum;
	ArrayList<HisAttentDatum> mdata=new ArrayList<HisAttentDatum>();
	private HorizontalListView mHotpicture_horizontallistview;
	private IWXAPI api;
	private static final String APP_QQID= Constant.APPID.QQAPPID;
	private static final String APP_ID=Constant.APPID.WXAPPID;
	private Share_pop share_pop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_picture);
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
		
		PushAgent.getInstance(this).onAppStart();
		Intent intent = getIntent();
		talent_post_id = intent.getStringExtra("talent_post_id");
		title = intent.getStringExtra("nicknametitle");
		nickphoto = intent.getStringExtra("nickphoto");
		faburenID = intent.getStringExtra("faburenID");
		
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		
		initUI();
		initData();
		initComment();
		initFansNum();
		initAttentImg();
		
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}

	private void initAttentImg() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url="http://a.ouyiku.com/?m=home&c=Talent&a=t_hisfanslist"+"&uid="+userid+"&tid="+faburenID;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HisAttent fromJson = gson.fromJson(arg0, HisAttent.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<HisAttentDatum> data = fromJson.getData();
					mdata.clear();
					mdata.addAll(data);
					mHotpicture_horizontallistview.setAdapter(new HorListviewAdapter());
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initFansNum() {
		String fansNum=Constant.URL.HotNetRedDetailURL+"&uid="+userid+"&tid="+faburenID;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, fansNum, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotNetRedDetail fromJson = gson.fromJson(arg0, HotNetRedDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					RedDetail data = fromJson.getData();
					String fanscount = data.getFanscount();
					mFanNum.setText("粉丝数："+fanscount);
					String isfollow = data.getIsfollow();//是否关注
					String netuseid = data.getUserId();//该网红的id
					Log.e("netuseid", netuseid);
					if(!userid.equals("") ){
						if(isfollow.equals("1")){
							mAttent.setImageResource(R.mipmap.h_attention_click);
						}else{
							mAttent.setImageResource(R.mipmap.h_attention);
						}
					}else{
						mAttent.setImageResource(R.mipmap.h_attention);
						
					}
			}
			}
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initZan() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String zanUrl=Constant.URL.HotPictureZANlURL+"&uid="+userid+"&tid="+talent_post_id;
		
		HTTPUtils.get(this, zanUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotPictureZan fromJson = gson.fromJson(arg0, HotPictureZan.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					ZanData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						mZanImg.setImageResource(R.mipmap.h_like_click_42);
						String string = mZanNum.getText().toString();
						Integer valueOf = Integer.valueOf(string);
						int zanNum=valueOf+1;
						mZanNum.setText(zanNum+"");
						mZanView.setClickable(false);
						
						
					}
				}else{
					Toast.makeText(HotPictureActivity.this, "检查是否登录", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initComment() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String commentUrl=Constant.URL.HotPictureCommentlURL+"&talent_post_id="+talent_post_id;
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
						mListview.setAdapter(new MyHotPicture());
					}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	private String talentTitle;
	private String talentContent;
	private RelativeLayout mZanView;
	private Tencent mTencent;
	ArrayList<String> mBannerData=new ArrayList<String>();
	private String sharePic;

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.HotPictureDetailURL+"&uid="+userid+"&talent_post_id="+talent_post_id;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			



			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				HotPictureDetail fromJson = gson.fromJson(arg0, HotPictureDetail.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					HotPictureDetailData data = fromJson.getData();
					String createTime = data.getCreateTime();
					talentTitle = data.getTalentTitle();
					talentContent = data.getTalentContent();
					String clickgoodNum = data.getClickgood();
					String commentNum = data.getCommentNum();
					List<String> pic = data.getPic();
					mBannerData.clear();
					mBannerData.addAll(pic);
					if(mBannerData.size()!=0){
						sharePic = mBannerData.get(0);
//						new Thread(new Runnable(){
//
//							@Override
//				            public void run() {
//								thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePic), 120, 120, true);
//				            }
//				        }).start();
						
					}
					String isclickgood = data.getIsClickgood();
					if(isclickgood.equals("1")){
						mZanImg.setImageResource(R.mipmap.h_like_click_42);
					}else{
						mZanImg.setImageResource(R.mipmap.h_like);
					}
					mTitle.setText(talentTitle);
					mTime.setText(createTime);
					mContent.setText(talentContent);
					mZanNum.setText(clickgoodNum);
					mCommentNum.setText(commentNum);
					mCommentNum2.setText("评论("+commentNum+")");
					MyPictuer myPictuer = new MyPictuer();
					mPictureListview.setAdapter(myPictuer);
					mScroll.smoothScrollTo(0, 0);
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}

	private void initUI() {
		mScroll = (ScrollView) findViewById(R.id.hotpicture_scroll);
		mScroll.smoothScrollTo(0, 0);
		TextView mtitle = (TextView) findViewById(R.id.hotpicture_titlename);
		mtitle.setText(title);
		personData_signature_pop = new PersonData_signature_pop(this);
		mZanImg = (ImageView) findViewById(R.id.hot_zan_img);
		
		findViewById(R.id.ht_picture_share).setOnClickListener(this);//分享
		findViewById(R.id.ht_picture_comment).setOnClickListener(this);//评论
//		findViewById(R.id.ht_picture_zan).setOnClickListener(this);//赞
		mZanView = (RelativeLayout) findViewById(R.id.ht_picture_zan);//赞
		mZanView.setOnClickListener(this);
		findViewById(R.id.hotpicture_back).setOnClickListener(this);
		
		mTitle = (TextView) findViewById(R.id.hotpicture_title);
		mTime = (TextView) findViewById(R.id.hotpicture_time);
		mContent = (TextView) findViewById(R.id.hotpicture_content);
		mZanNum = (TextView) findViewById(R.id.zan_num);
		mCommentNum = (TextView) findViewById(R.id.comment_num);
		
		mPictureListview = (ScrollViewWithListView) findViewById(R.id.hotpicture_listview);
		mPictureListview.setFocusable(false);
		mListview = (ScrollViewWithListView) findViewById(R.id.hotpicture_scroll_listview);
		topview = getLayoutInflater().inflate(R.layout.hot_picture_topview, null);
		
		TextView mNickName = (TextView) topview.findViewById(R.id.hotpicture_topview_name);
		mNickName.setText(title);
		
		CircleImageView mImg = (CircleImageView) topview.findViewById(R.id.hotpicture_topview_touxiang);
		UILUtils.displayImageNoAnim(nickphoto, mImg);
		mImg.setOnClickListener(this);
		
		mFanNum = (TextView) topview.findViewById(R.id.hotpicture_topview_fans);
		
		mAttent = (ImageView) topview.findViewById(R.id.hotpicture_topview_attention);
		mAttent.setOnClickListener(this);
		
		
		mCommentNum2 = (TextView) topview.findViewById(R.id.hotpicture_topview_commentnum);
		footview = getLayoutInflater().inflate(R.layout.hot_picture_listview_footview_item, null);
		mListview.addHeaderView(topview);
		mListview.addFooterView(footview);
		
		initHorizontalScrollView();
		share_pop = new Share_pop(this);
		myComment_pop = new MyComment_pop(this);
		
		footview.findViewById(R.id.look_more).setOnClickListener(this);
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position>0){
					myComment_pop.showAtLocation(findViewById(R.id.hotpicture_layout), Gravity.CENTER, 0, 0);
					TextView mAllComment = (TextView) myComment_pop.view.findViewById(R.id.pop_comment_content);
					TextView pop_comment_back = (TextView) myComment_pop.view.findViewById(R.id.pop_comment_back);
					pop_comment_back.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							myComment_pop.dismiss();
						}
					});
					HotPictureCommentDatum hotPictureCommentDatum = mCommentData.get(position-1);
					String content = hotPictureCommentDatum.getContent();
					
					mAllComment.setText(content);
					
				}
			}
		});
	}

	private void initHorizontalScrollView() {
		mHotpicture_horizontallistview = (HorizontalListView) topview.findViewById(R.id.hotpicture_horizontallistview);
		
		
		mHotpicture_horizontallistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(HotPictureActivity.this, Hot_netredActivity.class);
				String nickname = mdata.get(position).getNickname();
				String userId2 = mdata.get(position).getUserId();
				intent.putExtra("tid", userId2);
				intent.putExtra("hotnetTitle", nickname);
				startActivity(intent);
				
			}
		});
		
	}
	class HorListviewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mdata.size();
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
			View layout = getLayoutInflater().inflate(R.layout.hotnet_horlistview_item, null);
			CircleImageView mFansImg = (CircleImageView) layout.findViewById(R.id.hotpicture_topview_touxiang);
			String photo = mdata.get(position).getPhoto();
			String userId2 = mdata.get(position).getUserId();
			
			UILUtils.displayImageNoAnim(photo, mFansImg);
			
			return layout;
		}
		
	}
	
	//评论
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
						startActivity(new Intent(HotPictureActivity.this, LoginActivity.class));
					}else{
						
						personData_signature_pop.showAtLocation(findViewById(R.id.hotpicture_layout), Gravity.CENTER, 0, 0);
						TextView mSend = (TextView) personData_signature_pop.view.findViewById(R.id.pop_send);
						final EditText mSignature = (EditText) personData_signature_pop.view.findViewById(R.id.pop_signature_et);
						mSignature.setText("");
						mSend.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								final ZProgressHUD progressHUD = ZProgressHUD.getInstance(HotPictureActivity.this); 
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
								
								HTTPUtils.post(HotPictureActivity.this, HuifuUrl, map, new VolleyListener() {
									
									@Override
									public void onResponse(String arg0) {
										progressHUD.dismiss();
										Gson gson = new Gson();
										Comment fromJson = gson.fromJson(arg0, Comment.class);
										Status status = fromJson.getStatus();
										String succeed = status.getSucceed();
										if(succeed.equals("1")){
											Toast.makeText(HotPictureActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
											initComment();
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
					ToastUtils.toast(HotPictureActivity.this, "举报成功");
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
	
	class MyPictuer extends BaseAdapter{


		@Override
		public int getCount() {
			return mBannerData.size();
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
			ViewHolder2 holder3;
			if(convertView==null){
				layout= getLayoutInflater().inflate(R.layout.hotpicture_item_img, null);
				holder3 = new ViewHolder2();
				holder3.img = (AspectRatioImageView) layout.findViewById(R.id.hotpicture_img);
				layout.setTag(holder3);
			}else{
				layout=convertView;
				holder3 = (ViewHolder2) layout.getTag();
			}
			
			String picurl = mBannerData.get(position);
			UILUtils.displayImageNoAnim(picurl, holder3.img);
			
			
			return layout;
		}
		
	}
	class ViewHolder2{
		AspectRatioImageView img;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ht_picture_share:
			//分享
			share_pop.showAtLocation(findViewById(R.id.hotpicture_layout), Gravity.CENTER, 0, 0);
			share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new Thread(new Runnable(){

						@Override
			            public void run() {
							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePic), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(false,thumb));
			            }
			        }).start();
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new Thread(new Runnable(){

						@Override
			            public void run() {
							Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePic), 120, 120, true);
							api.registerApp(APP_ID);
							api.sendReq(createReq(true,thumb));
			            }
			        }).start();
					
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					share();
					
				}
			});
			
			share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ShareQQZone();
					
				}
			});
			
			
			break;
		case R.id.ht_picture_comment:
			if(userid.equals("")){
				startActivity(new Intent(HotPictureActivity.this, LoginActivity.class));
				
			}else{
				
				personData_signature_pop.showAtLocation(findViewById(R.id.hotpicture_layout), Gravity.CENTER, 0, 0);
				TextView mSend = (TextView) personData_signature_pop.view.findViewById(R.id.pop_send);
				final EditText mSignature = (EditText) personData_signature_pop.view.findViewById(R.id.pop_signature_et);
				mSignature.setText("");
				
				mSend.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						
					
							final ZProgressHUD progressHUD = ZProgressHUD.getInstance(HotPictureActivity.this); 
							progressHUD.setMessage("加载中");
					    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
					    	progressHUD.show();
							String mContent = mSignature.getText().toString().trim();
							String SendCommentUrl=Constant.URL.HotPictureSendCommentlURL;
							HashMap<String, String> map=new HashMap<String, String>();
							map.put("uid", userid);
							map.put("t_con", mContent);
							map.put("talent_id", talent_post_id);
							HTTPUtils.post(HotPictureActivity.this, SendCommentUrl, map, new VolleyListener() {
								
								@Override
								public void onResponse(String arg0) {
									progressHUD.dismiss();
									Gson gson = new Gson();
									Comment fromJson = gson.fromJson(arg0, Comment.class);
									Status status = fromJson.getStatus();
									String succeed = status.getSucceed();
									if(succeed.equals("1")){
										Toast.makeText(HotPictureActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
										InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
								           imm.hideSoftInputFromWindow(mSignature.getWindowToken(),0);  
										initComment();
										personData_signature_pop.dismiss();
										
									}else{
										Toast.makeText(HotPictureActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
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
			
			
			break;
		case R.id.ht_picture_zan:
			if(userid.equals("")){
				startActivity(new Intent(HotPictureActivity.this, LoginActivity.class));
			}else{
				initZan();
				
			}
			break;
		case R.id.hotpicture_back:
			finish();
			break;
		case R.id.look_more:
			//查看更多评论
			Intent intent2 = new Intent(HotPictureActivity.this, MoreCommentActivity.class);
			intent2.putExtra("talent_post_id", talent_post_id);
			startActivity(intent2);
			break;
		case R.id.hotpicture_topview_touxiang:
			Intent intent = new Intent(HotPictureActivity.this,Hot_netredActivity.class);
			String nickname = title;
			String userId2 = faburenID;
			intent.putExtra("tid", userId2);
			intent.putExtra("hotnetTitle", nickname);
			startActivity(intent);
			
			break;
		case R.id.hotpicture_topview_attention:
			
			
			if(!userid.equals("") && !token.equals("")){
				
				isTouch=!isTouch;
				if(isTouch){
					String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+faburenID;
					final ZProgressHUD progressHUD = ZProgressHUD.getInstance(HotPictureActivity.this); 
					progressHUD.setMessage("加载中");
			    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
			    	progressHUD.show();
					HTTPUtils.get(HotPictureActivity.this, AttentionUrl, new VolleyListener() {
						
						@Override
						public void onResponse(String arg0) {
							progressHUD.dismiss();
							initFansNum();
						}
						
						@Override
						public void onErrorResponse(VolleyError arg0) {
							progressHUD.dismiss();
						}
					});
				}else{
					String AttentionUrl=Constant.URL.FashionManATTENTIONURL+"&uid="+userid+"&tid="+faburenID;
					final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
					progressHUD.setMessage("加载中");
			    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
			    	progressHUD.show();
					HTTPUtils.get(HotPictureActivity.this, AttentionUrl, new VolleyListener() {
						
						@Override
						public void onResponse(String arg0) {
							progressHUD.dismiss();
							initFansNum();
						}
						
						@Override
						public void onErrorResponse(VolleyError arg0) {
							progressHUD.dismiss();
						}
					});
				}
				}else{
					startActivity(new Intent(HotPictureActivity.this, LoginActivity.class));
					
				}
				
			
		
			
			break;

		default:
			break;
		}
		
	}
	
public SendMessageToWX.Req createReq(boolean timeLine,Bitmap thumb) {
		
    	WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.ouyiku.com/talent/detail?id="+talent_post_id;
		final WXMediaMessage msg = new WXMediaMessage(webpage);
		String title = talentTitle;
		msg.description = talentContent;
		msg.title = title;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
//		new Thread(new Runnable(){
//
//			@Override
//            public void run() {
//				thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePic), 120, 120, true);
				msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//            }
//        }).start();
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
//		req.scene = SendMessageToWX.Req.WXSceneSession;
		req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		return req;
    }

	 private String buildTransaction(final String type) {
			return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
		}
	 
	 
	//分享QQ
		public void share()
		{
		Bundle bundle = new Bundle();
		//这条分享消息被好友点击后的跳转URL。
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.ouyiku.com/talent/detail?id="+talent_post_id);
		//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, talentTitle);
		//分享的图片URL
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,mBannerData.get(0));
		//分享的消息摘要，最长50个字
		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, talentContent);
		//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//		bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
		//标识该消息的来源应用，值为应用名称+AppId。
//		bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

		mTencent.shareToQQ(this, bundle , qqShareListener);
		}
		
//		 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
		 private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
		 
		 //分享QQ空间
		 public void ShareQQZone(){
			 Bundle params = new Bundle();
//			 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//			 
			  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
			  params.putString(QzoneShare.SHARE_TO_QQ_TITLE, talentTitle);
			  params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.ouyiku.com/talent/detail?id="+talent_post_id);
				//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
			  params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, talentContent);
//				//分享的图片URL
			  
			  params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mBannerData);
				doShareToQzone(params);
				
		 }
		
		IUiListener qqShareListener = new IUiListener() {
	        @Override
	        public void onCancel() {
//	            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//	                Util.toastMessage(DetailActivity.this, "onCancel: ");
//	                Toast.makeText(DetailActivity.this, "onCancel:", Toast.LENGTH_SHORT).show();
//	            }
	        }
	        @Override
	        public void onComplete(Object response) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onComplete: " + response.toString());
//	            Toast.makeText(DetailActivity.this, "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
	        }
	        @Override
	        public void onError(UiError e) {
	            // TODO Auto-generated method stub
//	            Util.toastMessage(DetailActivity.this, "onError: " + e.errorMessage, "e");
//	            Toast.makeText(DetailActivity.this, "onError: " + e.errorMessage, Toast.LENGTH_SHORT).show();
	        }
	    };
		private MyComment_pop myComment_pop;
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
	    }
		
	    private void doShareToQzone(final Bundle params) {
	        // QZone分享要在主线程做
	        ThreadManager.getMainHandler().post(new Runnable() {

	            @Override
	            public void run() {
	                if (null != mTencent) {
	                    mTencent.shareToQzone(HotPictureActivity.this, params, qqShareListener);
	                }
	            }
	        });
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
