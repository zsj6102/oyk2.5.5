package zsj.com.oyk255.example.ouyiku.v1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.AbstractSpinerAdapter;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.MyHttpUtils;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.utils.SpinerPopWindow;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.wjk.demouploadpic.photo.Bimp;
import zsj.com.oyk255.wjk.demouploadpic.photo.FileUtils;
import zsj.com.oyk255.wjk.demouploadpic.photo.PhotoActivity;
import zsj.com.oyk255.wjk.demouploadpic.window.PhotoCameraWindow;

public class Creat_fashionActivity extends FragmentActivity implements OnClickListener,AbstractSpinerAdapter.IOnItemSelectListener {
	 private GridView mGVaddpic;
		/** 这个适配器统一都是这么设置的 尽量不修改 要改的话就修改下 getview里面控件宽高展示 */
		private MyGridAdapter mGridAdapter;
		/** 屏幕宽度 */
		private int screenWidth;
		/**  这里是选择你要上传的图片数量 比如头像的写1就可以了 */
		private int mIntentChooseNumber = 9;
		/** 做个点击图片选择的标志位 重新回到界面才好做判断 */
		private String mClickType;
		/** 相册相机选择弹框 */
		private PhotoCameraWindow photoCameraWindow;
		/** 整个界面的父布局 */
		private View parentView;
		InputMethodManager im;
		private EditText mTitle;
		private EditText mContent;
		
		private String userid;//用户id
		private String token;//用户token
		private SharedPreferences sp;
		private List<String> nameList = new ArrayList<String>();
		private SpinerPopWindow mSpinerPopWindow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_fashion);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
		
	
		
		screenWidth = ScreenUtils.getScreenWidth(this);
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
		mLieBie = (TextView) findViewById(R.id.creatfashion_leibie2);
		mLieBie.setOnClickListener(this);
		
		String[] names = getResources().getStringArray(R.array.hero_name);
		for(int i = 0; i < names.length; i++){
			nameList.add(names[i]);
		}
		

		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
		
		
		  
		  mGVaddpic = (MyGridView) findViewById(R.id.fashion_gridview);
		  mGridAdapter = new MyGridAdapter(this);
			mGVaddpic.setAdapter(mGridAdapter);

			mGVaddpic.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					mClickType = Constant.PHOTO.ADDPIC;
					if (position == Bimp.bmp.size()) {
						photoCameraWindow = new PhotoCameraWindow(Creat_fashionActivity.this, mIntentChooseNumber);
						photoCameraWindow.showPopupWindowCenter(parentView);
					} else {
						Intent intent = new Intent(Creat_fashionActivity.this, PhotoActivity.class);
						intent.putExtra("ID", position);
						startActivity(intent);
					}
				}
			});
			
			findViewById(R.id.creatfashion_sendview).setOnClickListener(this);
			findViewById(R.id.creatfashion_back).setOnClickListener(this);
			mTitle = (EditText) findViewById(R.id.creatfashion_title_et);
			mContent = (EditText) findViewById(R.id.creatfashion_share_edt);
			
		  
	}
	@Override
	public void onItemClick(int pos) {
		setHero(pos);
	}
	private void setHero(int pos){
		if (pos >= 0 && pos <= nameList.size()){
			String value = nameList.get(pos);
		
			mLieBie.setText(value);
		}
	}

	
	private TextView mLieBie;
	private String cattalent_id;
	private void showSpinWindow(){
		Log.e("", "showSpinWindow");
		mSpinerPopWindow.setWidth(mLieBie.getWidth());
		mSpinerPopWindow.showAsDropDown(mLieBie);
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
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.after_sale_img_b));
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

	IdentityHashMap<String, String> map=new IdentityHashMap<String, String>();
			
			
	private void postToServer() {
		/** 上传图片的文件，多个 */
		String url=Constant.URL.CreatHotPeople;
		RequestParams params=new RequestParams();
		//mime，我的理解是，图片是以流的形式上传到服务器的，而服务器还原图片时它的格式是什么？有的服务器自己会设定，有的就需要客户端上传一个要求，服务器根据这个要求还原图片的格式，而这个要求就是mime
		String mime=MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg");
		//upfile 根据接口的不同，这个值也不同，我的理解是；服务器规定，这个key对应的值，就是文件形式的
		
//		if(Bimp.bmpPath.size() > 0){
//			for (int i = 0; i < Bimp.bmpPath.size(); i++) {
//				String i1 = Bimp.bmpPath.get(i);
//				File file = new File(Bimp.bmpPath.get(i));
//				
//				params.addBodyParameter("image[]", file,mime);
//			}
//		}
		
		String mTv = mLieBie.getText().toString();
		if(mTv.equals("主播")){
			cattalent_id = "1";
		}
		if(mTv.equals("嫩模")){
			cattalent_id = "2";
		}
		if(mTv.equals("明星")){
			cattalent_id = "3";
		}
		if(mTv.equals("学生")){
			cattalent_id = "4";
		}
		if(mTv.equals("辣妈")){
			cattalent_id = "5";
		}
		
		
		if(Bimp.bmpPath.size() == 1){
			
			File file = new File(Bimp.bmpPath.get(0));
			
			params.addBodyParameter("image[0]", file,mime);
			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 2){
			Log.e("postToServer", ""+Bimp.bmpPath.size());
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			Log.e("Bimp.bmpPath.get(0)", Bimp.bmpPath.get(0));
			Log.e("Bimp.bmpPath.get(1)", Bimp.bmpPath.get(1));
			
			params.addBodyParameter("image[0]", file);
			params.addBodyParameter("image[1]", file2);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 3){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 4){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
		if(Bimp.bmpPath.size() == 5){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			File file5 = new File(Bimp.bmpPath.get(4));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);
			params.addBodyParameter("image[4]", file5,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
		if(Bimp.bmpPath.size() == 6){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			File file5 = new File(Bimp.bmpPath.get(4));
			File file6 = new File(Bimp.bmpPath.get(5));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);
			params.addBodyParameter("image[4]", file5,mime);
			params.addBodyParameter("image[5]", file6,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 7){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			File file5 = new File(Bimp.bmpPath.get(4));
			File file6 = new File(Bimp.bmpPath.get(5));
			File file7 = new File(Bimp.bmpPath.get(6));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);
			params.addBodyParameter("image[4]", file5,mime);
			params.addBodyParameter("image[5]", file6,mime);
			params.addBodyParameter("image[6]", file7,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		if(Bimp.bmpPath.size() == 8){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			File file5 = new File(Bimp.bmpPath.get(4));
			File file6 = new File(Bimp.bmpPath.get(5));
			File file7 = new File(Bimp.bmpPath.get(6));
			File file8 = new File(Bimp.bmpPath.get(7));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);
			params.addBodyParameter("image[4]", file5,mime);
			params.addBodyParameter("image[5]", file6,mime);
			params.addBodyParameter("image[6]", file7,mime);
			params.addBodyParameter("image[7]", file8,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
		if(Bimp.bmpPath.size() == 9){
			File file = new File(Bimp.bmpPath.get(0));
			File file2 = new File(Bimp.bmpPath.get(1));
			File file3 = new File(Bimp.bmpPath.get(2));
			File file4 = new File(Bimp.bmpPath.get(3));
			File file5 = new File(Bimp.bmpPath.get(4));
			File file6 = new File(Bimp.bmpPath.get(5));
			File file7 = new File(Bimp.bmpPath.get(6));
			File file8 = new File(Bimp.bmpPath.get(7));
			File file9 = new File(Bimp.bmpPath.get(8));
			
			params.addBodyParameter("image[0]", file,mime);
			params.addBodyParameter("image[1]", file2,mime);
			params.addBodyParameter("image[2]", file3,mime);
			params.addBodyParameter("image[3]", file4,mime);
			params.addBodyParameter("image[4]", file5,mime);
			params.addBodyParameter("image[5]", file6,mime);
			params.addBodyParameter("image[6]", file7,mime);
			params.addBodyParameter("image[7]", file8,mime);
			params.addBodyParameter("image[8]", file9,mime);

			String title = mTitle.getText().toString().trim();
			String content = mContent.getText().toString().trim();
			params.addBodyParameter("user_id", userid);
			params.addBodyParameter("cattalent_id", cattalent_id);
			params.addBodyParameter("talent_title", title);
			params.addBodyParameter("talent_content", content);
			MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

				@Override
				public void callback(String jsonStr) {
					Gson gson = new Gson();
					Log.e("jsonStr", jsonStr);
					Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
		}
		
		
		
		
		
		
		
		
		
		
//		String title = mTitle.getText().toString().trim();
//		String content = mContent.getText().toString().trim();
//		params.addBodyParameter("user_id", userid);
//		params.addBodyParameter("cattalent_id", cattalent_id);
//		params.addBodyParameter("talent_title", title);
//		params.addBodyParameter("talent_content", content);
		
//		params.addBodyParameter("key"," value");//如果上传文件的接口也支持上传其他的参数，就可以启用这个了
		//请求方法是自己封装的，用的是post请求，参数是；接口、参数、回调方法
//		MyHttpUtils.parseShareJsonFromNet(url, params, new JsonCallBack() {
//
//			@Override
//			public void callback(String jsonStr) {
//				Gson gson = new Gson();
//				Log.e("jsonStr", jsonStr);
//				Toast.makeText(Creat_fashionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		});

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.creatfashion_sendview:
			if(!userid.equals("")){
				postToServer();
			}else{
				startActivity(new Intent(this, LoginActivity.class));
			}
//			if(Bimp.bmpPath.size() > 0){
//				for (int i = 0; i < Bimp.bmpPath.size(); i++) {
//					File file = new File(Bimp.bmpPath.get(i));
//					
//				}
//			}
				
			
			break;
		case R.id.creatfashion_back:
			finish();
			break;
		case R.id.creatfashion_leibie2:
			showSpinWindow();
			
			break;
			

		default:
			break;
		}
		
	}
	
	@Override
	public void finish() {
		hideSoftInput();
		super.finish();
		overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
	}
			protected void hideSoftInput() {
				View view = getCurrentFocus();
				if(view != null) {
					IBinder binder = view.getWindowToken();
					if(binder != null) {
						im.hideSoftInputFromWindow(binder, 0);
					}
				}
			}
}
