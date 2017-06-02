package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.loginjson.Data;
import zsj.com.oyk255.example.ouyiku.loginjson.Status;
import zsj.com.oyk255.example.ouyiku.loginjson.UserID;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class LoginActivity extends OykActivity implements OnClickListener{

	private EditText mEt_phone;
	private EditText mEt_pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		PushAgent.getInstance(this).onAppStart();
		initUI();
	}

	private void initUI() {
		findViewById(R.id.login_register).setOnClickListener(this);
		findViewById(R.id.forget_password).setOnClickListener(this);
		findViewById(R.id.login_back).setOnClickListener(this);
		findViewById(R.id.login_btn).setOnClickListener(this);
		mEt_phone = (EditText) findViewById(R.id.login_account);
		mEt_pass = (EditText) findViewById(R.id.login_password);
	}
	private void Login(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String LoginUrl= Constant.URL.LoginURL;
		HashMap<String, String> map=new HashMap<String, String>();
		String phone = mEt_phone.getText().toString().trim();
		String pass = mEt_pass.getText().toString().trim();
		map.put("mobile", phone);
		map.put("password", pass);
		HTTPUtils.post(this, LoginUrl, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				UserID fromJson = gson.fromJson(arg0, UserID.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					String token = data.getToken();//用户token
					String userId = data.getUserId();//用户id
					//保存在应用中一边下次启动应用自动登录
					SharedPreferences sp = getSharedPreferences("userdata", 0);
					 Editor edit = sp.edit();
					edit.putString("userid", userId);
					edit.putString("token", token);
					edit.commit();
					//返回个人中心传值
					Intent intent = new Intent();
					intent.putExtra("userid", userId);
					intent.putExtra("token", token);
					setResult(Constant.INTENT.LOGIN_RESULT, intent);
					finish();
					
				}else{
					Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_register:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.forget_password:
			startActivity(new Intent(this, ForgetActivity.class));
			break;
		case R.id.login_btn:
			Login();
			
			break;
		case R.id.login_back:
			finish();
			break;

		default:
			break;
		}
	}

}