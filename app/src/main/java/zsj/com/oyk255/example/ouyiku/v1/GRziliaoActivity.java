
package zsj.com.oyk255.example.ouyiku.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_headimg_pop;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_nickname_pop;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_sex;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_signature_pop;
import zsj.com.oyk255.example.ouyiku.orderjson.IsSuccess;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.MyHttpUtils;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class GRziliaoActivity extends OykActivity implements OnClickListener{
	//个人中心主界面传来的值
	private String nickname;
	private String photo;
	private String mobile;
	private String sex;
	private String perSig;
	private String userid;//用户id
	private String token;//用户token
	
	private ImageView mPhoto;
	private TextView mName;
	private TextView mPhone;
	private TextView mSex;
	private TextView mSig;
	private PersonData_headimg_pop personData_headimg_pop;
	private PersonData_nickname_pop personData_nickname_pop;
	private PersonData_sex personData_sex;
	private PersonData_signature_pop personData_signature_pop;
	private SharedPreferences sp;
	
	private Bitmap bitmap;
	private File file;
	private static final String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grziliao);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		
		Intent intent = getIntent();
		nickname = intent.getStringExtra("nickname");
		photo = intent.getStringExtra("photo");
		mobile = intent.getStringExtra("mobile");
		sex = intent.getStringExtra("sex");
		perSig = intent.getStringExtra("signature");
		
		initUI();
		initData();
	}

	private void initData() {
		mName.setText(nickname);
		mPhone.setText(mobile);
		mSex.setText(sex);
		mSig.setText(perSig);
		UILUtils.displayImage(photo, mPhoto);
		
	}
	
	private void ChangeInfo(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String	url= Constant.URL.PersonInfoChangeURL;
		String name = mName.getText().toString();
		String sex = mSex.getText().toString();
		String sig = mSig.getText().toString();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("nickname", name);
		map.put("sex", sex);
		map.put("per_sig", sig);
			HTTPUtils.post(this, url, map, new VolleyListener() {
				
				@Override
				public void onResponse(String arg0) {
					progressHUD.dismiss();
					Gson gson = new Gson();
					IsSuccess fromJson = gson.fromJson(arg0, IsSuccess.class);
					zsj.com.oyk255.example.ouyiku.orderjson.Status status = fromJson.getStatus();
					String succeed = status.getSucceed();
					if(succeed.equals("1")){
						finish();
					}else{
						Toast.makeText(GRziliaoActivity.this, "未做修改", Toast.LENGTH_SHORT).show();
						
					}
				}
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					progressHUD.dismiss();
				}
			});
	}

	private void initUI() {
		
		personData_headimg_pop = new PersonData_headimg_pop(this);
		personData_nickname_pop = new PersonData_nickname_pop(this);
		personData_sex = new PersonData_sex(this);
		personData_signature_pop = new PersonData_signature_pop(this);
		
		
		mPhoto = (ImageView) findViewById(R.id.data_img);
		mName = (TextView) findViewById(R.id.data_nickname);
		mPhone = (TextView) findViewById(R.id.data_phone);
		mSex = (TextView) findViewById(R.id.data_sex);
		mSig = (TextView) findViewById(R.id.data_sig);
		findViewById(R.id.back_data).setOnClickListener(this);
		
		
		mPhoto.setOnClickListener(this);
		findViewById(R.id.name_view).setOnClickListener(this);
		findViewById(R.id.phone_view).setOnClickListener(this);
		findViewById(R.id.sex_view).setOnClickListener(this);
		findViewById(R.id.address_view).setOnClickListener(this);
		findViewById(R.id.signature_view).setOnClickListener(this);
		
		findViewById(R.id.data_savedata).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_data:
			finish();
			break;
		case R.id.data_savedata:
			ChangeInfo();
			break;
		case R.id.data_img:
			personData_headimg_pop.showAtLocation(findViewById(R.id.persondata_layout), Gravity.CENTER, 0, 0);
			
			View mPicture = personData_headimg_pop.view.findViewById(R.id.pop_pictureview);
			mPicture.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 激活系统图库，选择一张图片
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, Constant.INTENT.PHOTO_REQUEST_GALLERY);	
				}
			});
			 View mPhoto = personData_headimg_pop.view.findViewById(R.id.pop_photoview);
			 
			mPhoto.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 判断存储卡是否可以用，可用进行存储
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					if (hasSdcard()) {
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(new File(Environment
										.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
					}
					startActivityForResult(intent, Constant.INTENT.PHOTO_REQUEST_CAMERA);
				}
			});
			
			personData_headimg_pop.view.findViewById(R.id.head_queding).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(bitmap!=null){
						send();
						personData_headimg_pop.dismiss();
						
					}else{
						Toast.makeText(GRziliaoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
						personData_headimg_pop.dismiss();
					}
				}
			});
			break;
		case R.id.name_view:
			personData_nickname_pop.showAtLocation(findViewById(R.id.persondata_layout), Gravity.CENTER, 0, 0);
			TextView mPopNickname_save = (TextView) personData_nickname_pop.view.findViewById(R.id.pop_save);
			final EditText mPopNickname_edt = (EditText) personData_nickname_pop.view.findViewById(R.id.pop_nickname);
			mPopNickname_save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String New_name = mPopNickname_edt.getText().toString().trim();
					mName.setText(New_name);
					personData_nickname_pop.dismiss();
				}
			});
			break;
		case R.id.phone_view:
			Intent intent2 = new Intent(this, ChangePhoneActivity.class);
			startActivity(intent2);
			break;
			
		case R.id.sex_view:
			personData_sex.showAtLocation(findViewById(R.id.persondata_layout), Gravity.CENTER, 0, 0);
			View mPop_boy = personData_sex.view.findViewById(R.id.pop_boy);
			View mPop_girl = personData_sex.view.findViewById(R.id.pop_girl);
			mPop_girl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mSex.setText("女");
					personData_sex.dismiss();
				}
			});
			mPop_boy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mSex.setText("男");
					personData_sex.dismiss();
				}
			});
			break;
		case R.id.address_view:
			Intent intent = new Intent(this, Add_AaddressActivity.class);
			intent.putExtra("isOrder", false);
			startActivity(intent);
			
			break;
		case R.id.signature_view:
			personData_signature_pop.showAtLocation(findViewById(R.id.persondata_layout), Gravity.CENTER, 0, 0);
			TextView mSend = (TextView) personData_signature_pop.view.findViewById(R.id.pop_send);
			final EditText mSignature = (EditText) personData_signature_pop.view.findViewById(R.id.pop_signature_et);
			mSend.setOnClickListener(new OnClickListener() {
				
				

				@Override
				public void onClick(View v) {
					String mContent = mSignature.getText().toString().trim();
					mSig.setText(mContent);
					
					personData_signature_pop.dismiss();
				}
			});
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constant.INTENT.PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == Constant.INTENT.PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				if(data==null){
					return;
				}else{
					 Bundle bundle = data.getExtras();
                     if (bundle !=null){
                         Bitmap bitmap = bundle.getParcelable("data");
                         Uri uri = saveBitmap(bitmap);
                         crop(uri);
                     }
				}
				
			} else {
				Toast.makeText(GRziliaoActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
			}

		} else if (requestCode == Constant.INTENT.PHOTO_REQUEST_CUT) {
			try {
				bitmap = data.getParcelableExtra("data");
				this.mPhoto.setImageBitmap(bitmap);
				saveBitmap(bitmap);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
//		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, Constant.INTENT.PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	

	private void send() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.UpLoadHeadImg;
		RequestParams params=new RequestParams();
		//mime，我的理解是，图片是以流的形式上传到服务器的，而服务器还原图片时它的格式是什么？有的服务器自己会设定，有的就需要客户端上传一个要求，服务器根据这个要求还原图片的格式，而这个要求就是mime
		String mime=MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg");
		//upfile 根据接口的不同，这个值也不同，我的理解是；服务器规定，这个key对应的值，就是文件形式的
		params.addBodyParameter("image", file,mime);
		params.addBodyParameter("user_id", userid);
		params.addBodyParameter("token", token);
		
//		params.addBodyParameter("key"," value");//如果上传文件的接口也支持上传其他的参数，就可以启用这个了
		//请求方法是自己封装的，用的是post请求，参数是；接口、参数、回调方法
		MyHttpUtils.parseShareJsonFromNet(url, params, new MyHttpUtils.JsonCallBack() {

			@Override
			public void callback(String jsonStr) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				Log.e("jsonStr", jsonStr);
				Toast.makeText(GRziliaoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	private Uri saveBitmap(Bitmap bitmap){
		file = new File(SAVE_PATH+"/"+PHOTO_FILE_NAME+".jpg");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fos =null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
