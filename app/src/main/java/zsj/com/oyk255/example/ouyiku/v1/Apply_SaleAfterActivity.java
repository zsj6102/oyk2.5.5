package zsj.com.oyk255.example.ouyiku.v1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.umeng.message.PushAgent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.MyHttpUtils;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.wjk.demouploadpic.photo.Bimp;
import zsj.com.oyk255.wjk.demouploadpic.photo.FileUtils;
import zsj.com.oyk255.wjk.demouploadpic.photo.PhotoActivity;
import zsj.com.oyk255.wjk.demouploadpic.window.PhotoCameraWindow;

public class Apply_SaleAfterActivity extends OykActivity implements OnClickListener{
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private MyGridView mGridView;
	/**  这里是选择你要上传的图片数量 比如头像的写1就可以了 */
	private int mIntentChooseNumber = 3;
	/** 屏幕宽度 */
	private int screenWidth;
	/** 做个点击图片选择的标志位 重新回到界面才好做判断 */
	private String mClickType;
	/** 相册相机选择弹框 */
	private PhotoCameraWindow photoCameraWindow;
	private MyGridAdapter mGridAdapter;
	IdentityHashMap<String, String> map=new IdentityHashMap<String, String>();
	private View parentView;
	private EditText mEt_Reason;
	private EditText mEt_Money;
	private EditText mEt_Phone;
	private String goodsid;
	private String orderid;
	private EditText mEt_Name;
	private RelativeLayout tuiKuanCheck;
	private RelativeLayout tuiHuoCheck;
	private RelativeLayout huanHuoCheck;
	private ImageView mTuiKuan_Img;
	private ImageView mTuiHuo_Img;
	private ImageView mHuanHuo_Img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply__sale_after);
		PushAgent.getInstance(this).onAppStart();
		screenWidth = ScreenUtils.getScreenWidth(this);
		Intent intent = getIntent();
		goodsid = intent.getStringExtra("goodsid");
		orderid = intent.getStringExtra("orderid");
		
		initUI();
	}
	
	@Override
	protected void onStart() {
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		super.onStart();
	}

	private void initUI() {
		parentView = findViewById(R.id.parentView);
		findViewById(R.id.apply_back).setOnClickListener(this);
		mGridView = (MyGridView) findViewById(R.id.apply_gridview);
		mGridAdapter = new MyGridAdapter(this);
		mGridView.setAdapter(mGridAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mClickType = Constant.PHOTO.ADDPIC;
				if (position == Bimp.bmp.size()) {
					photoCameraWindow = new PhotoCameraWindow(Apply_SaleAfterActivity.this, mIntentChooseNumber);
					photoCameraWindow.showPopupWindowCenter(parentView);
					
				}else{
					Intent intent = new Intent(Apply_SaleAfterActivity.this, PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
				}
			}
		});
		tuiKuanCheck = (RelativeLayout) findViewById(R.id.tuikuan_check);
		tuiHuoCheck = (RelativeLayout) findViewById(R.id.tuihuo_check);
		huanHuoCheck = (RelativeLayout) findViewById(R.id.huanhuo_check);
		mTuiKuan_Img = (ImageView) findViewById(R.id.img_tuikuan);
		mTuiHuo_Img = (ImageView) findViewById(R.id.img_tuihuo);
		mHuanHuo_Img = (ImageView) findViewById(R.id.img_huanhuo);
		
		tuiKuanCheck.setOnClickListener(this);
		tuiHuoCheck.setOnClickListener(this);
		huanHuoCheck.setOnClickListener(this);
		
		mEt_Reason = (EditText) findViewById(R.id.tuikuan_reason_et);
		mEt_Money = (EditText) findViewById(R.id.tuikuan_money_et);
		mEt_Phone = (EditText) findViewById(R.id.tuikuan_phone_et);
		mEt_Name = (EditText) findViewById(R.id.tuikuan_name_et);
		TextView mApply_btn = (TextView) findViewById(R.id.apply_btn);
		mApply_btn.setOnClickListener(this);
		
	}
	
	private void SendSaleAfter(){
		String content = mEt_Reason.getText().toString().trim();
		String money = mEt_Money.getText().toString().trim();
		String phone = mEt_Phone.getText().toString().trim();
		String name = mEt_Name.getText().toString().trim();
		String url=Constant.URL.ApplySaleAfterUrl;
		
		RequestParams params=new RequestParams();
		//mime，我的理解是，图片是以流的形式上传到服务器的，而服务器还原图片时它的格式是什么？有的服务器自己会设定，有的就需要客户端上传一个要求，服务器根据这个要求还原图片的格式，而这个要求就是mime
		String mime=MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg");
		
		if(Bimp.bmpPath.size() == 1){
			
			File file = new File(Bimp.bmpPath.get(0));
			
			params.addBodyParameter("image1", file,mime);
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("token", token);
			params.addBodyParameter("order_id", orderid);
			params.addBodyParameter("good_id", goodsid);
			params.addBodyParameter("type", "1");
			params.addBodyParameter("name", name);
			params.addBodyParameter("money", money);
			params.addBodyParameter("mobile", phone);
			params.addBodyParameter("content", content);
			
			
			
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Apply_SaleAfterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 2){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			
			params.addBodyParameter("image1", file,mime);
			params.addBodyParameter("image2", file2,mime);
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("token", token);
			params.addBodyParameter("order_id", orderid);
			params.addBodyParameter("good_id", goodsid);
			params.addBodyParameter("type", "1");
			params.addBodyParameter("name", name);
			params.addBodyParameter("money", money);
			params.addBodyParameter("mobile", phone);
			params.addBodyParameter("content", content);
			
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Apply_SaleAfterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 3){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			
			params.addBodyParameter("image1", file,mime);
			params.addBodyParameter("image2", file2,mime);
			params.addBodyParameter("image3", file3,mime);
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("token", token);
			params.addBodyParameter("order_id", orderid);
			params.addBodyParameter("good_id", goodsid);
			params.addBodyParameter("type", "1");
			params.addBodyParameter("name", name);
			params.addBodyParameter("money", money);
			params.addBodyParameter("mobile", phone);
			params.addBodyParameter("content", content);
			
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Apply_SaleAfterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
		if(Bimp.bmpPath.size() == 0){
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("token", token);
			params.addBodyParameter("order_id", orderid);
			params.addBodyParameter("good_id", goodsid);
			params.addBodyParameter("type", "1");
			params.addBodyParameter("name", name);
			params.addBodyParameter("money", money);
			params.addBodyParameter("mobile", phone);
			params.addBodyParameter("content", content);
			
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {
				
				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Toast.makeText(Apply_SaleAfterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
					
				}
			});
		}
		
		
		
		
	}
	
	public class MyGridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public MyGridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * GridView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_ugp_gridview_add, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_img_add);
				/** 设置控件高度=屏幕宽度的一半 */
				// TODO 设置选择图片的数量
				holder.image.getLayoutParams().height = screenWidth / 3;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.after_sale_img_b3));
				if (position == mIntentChooseNumber) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mGridAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								long currentTimeMillis = System.currentTimeMillis();
								String imgNewFileName = Constant.PHOTO.FILE_NAME + currentTimeMillis;
								Log.e("UGP_imageFileName", "" + imgNewFileName);
								FileUtils.saveBitmap(bm, "" + imgNewFileName);
								Bimp.bmpPath.add(FileUtils.SDPATH + imgNewFileName + ".JPEG");
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
								Bimp.max += 1;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}
	
	@Override
	public void onDestroy() {
		/** =====当前图片相关 如果退出界面不需要这些属性了 需要注销下 不然会默认保存着==== */
		Bimp.drr.clear();
		Bimp.bmp.clear();
		Bimp.max = 0;
		Bimp.bmpPath.clear();
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		/** 这里是处理相册选择返回的数据 标志位mClickType */
		if (mClickType == Constant.PHOTO.ADDPIC)
			mGridAdapter.update();
		super.onRestart();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 这里是处理相机拍照返回的数据 如果不需要也可以去掉 */
		if (resultCode == RESULT_OK && (mClickType == Constant.PHOTO.ADDPIC)) {
			String currentPhotoPath = photoCameraWindow.getPhotoPath();
			Bimp.drr.add(currentPhotoPath);
			Log.e("currentPhotoPath", "" + currentPhotoPath);
			try {
				Bitmap bm = Bimp.revitionImageSize(currentPhotoPath);
				Bimp.bmp.add(bm);
				long currentTimeMillis = System.currentTimeMillis();
				String imgNewFileName = Constant.PHOTO.FILE_NAME + currentTimeMillis;
				FileUtils.saveBitmap(bm, "" + imgNewFileName);
				Bimp.bmpPath.add(FileUtils.SDPATH + imgNewFileName + ".JPEG");
				Bimp.max += 1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.apply_back:
			finish();
			break;
		case R.id.apply_btn:
			SendSaleAfter();
			break;
		case R.id.tuikuan_check:
			mTuiKuan_Img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			mTuiHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			mHuanHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			break;
		case R.id.tuihuo_check:
			mTuiKuan_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			mTuiHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			mHuanHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			break;
		case R.id.huanhuo_check:
			mTuiKuan_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			mTuiHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on);
			mHuanHuo_Img.setImageResource(R.mipmap.fukuan_pitch_on_click);
			break;

		default:
			break;
		}
		
	}

	

}
