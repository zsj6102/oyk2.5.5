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
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.regisYZMjson.RegisYanZM;
import zsj.com.oyk255.example.ouyiku.regisYZMjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SetTixianActivity extends OykActivity implements OnClickListener{

	private String phone;
	private String code;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private EditText mET_firstPass;
	private EditText mET_AgainPass;
	private TextView mFinish;
	private String firstPass;
	private String secondPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_tixian);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		phone = intent.getStringExtra("phone");
		code = intent.getStringExtra("code");
		initUI();
		
	}
	private void initUI() {
		findViewById(R.id.tixiansetpass_back).setOnClickListener(this);
		mET_firstPass = (EditText) findViewById(R.id.tixiansetpass_into);
		mET_AgainPass = (EditText) findViewById(R.id.tixiansetpass_again);
		mFinish = (TextView) findViewById(R.id.tixiansetpass_finish);
		mFinish.setOnClickListener(this);
		
	}
	private void SetTiXianPass(String pass){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.SetPayPassUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("code", code);
		map.put("user_id", userid);
		map.put("token", token);
		map.put("password", pass);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					Toast.makeText(SetTixianActivity.this, "设置成功", Toast.LENGTH_LONG).show();
					SharedPreferences sp = getSharedPreferences("SetTiXian", 0);
					 Editor edit = sp.edit();
					 edit.putString("IsSetTiXian", "yes");
					 edit.commit();
					 finish();
				}else{
					Toast.makeText(SetTixianActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
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
		case R.id.tixiansetpass_finish:
			firstPass = mET_firstPass.getText().toString().trim();
			secondPass = mET_AgainPass.getText().toString().trim();
			if(firstPass.equals("")&&secondPass.equals("")){
				Toast.makeText(SetTixianActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else{
				if(!firstPass.equals(secondPass)){
					Toast.makeText(SetTixianActivity.this, "两次输入密码不一样", Toast.LENGTH_LONG).show();
				}else{
					SetTiXianPass(secondPass);;
//					Toast.makeText(SetTixianActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//					finish();
				}
				
			}
			break;
		case R.id.tixiansetpass_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
