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
import zsj.com.oyk255.example.ouyiku.regisjson.PassData;
import zsj.com.oyk255.example.ouyiku.regisjson.SetPass;
import zsj.com.oyk255.example.ouyiku.regisjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SetPasswordActivity extends OykActivity implements OnClickListener{

	private EditText mFirstPass;
	private EditText mAgaintPass;
	private TextView mFinish;
	private String firstPass;
	private String secondPass;
	private String yanZheng;
	private String phone;
	private String yaoQing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);
		PushAgent.getInstance(this).onAppStart();
		Intent intent = getIntent();
		yanZheng = intent.getStringExtra("YanZheng");
		phone = intent.getStringExtra("Phone");
		yaoQing = intent.getStringExtra("YaoQing");
		initUI();
	}

	private void initUI() {
		findViewById(R.id.setpass_back).setOnClickListener(this);
		mFinish = (TextView) findViewById(R.id.setpass_finish);
		mFinish.setOnClickListener(this);
		mFirstPass = (EditText) findViewById(R.id.setpass_into);
		mAgaintPass = (EditText) findViewById(R.id.setpass_again);
	}
	private void initData(final String passwork){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String RegisSetPassURL= Constant.URL.RegisSetPassURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("mobile", phone);
		map.put("code", yanZheng);
		map.put("invitation", yaoQing);
		map.put("password", passwork);
		
		HTTPUtils.post(this, RegisSetPassURL, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SetPass fromJson = gson.fromJson(arg0, SetPass.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					PassData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						String token = data.getToken();//用户token
						String userId = data.getUserId();//用户id
						SharedPreferences sp = getSharedPreferences("userdata", 0);
						 Editor edit = sp.edit();
						edit.putString("userid", userId);
						edit.putString("token", token);
						edit.commit();
						//设置密码送成功返回个人中心数据
						Intent intent = new Intent();
						intent.putExtra("userid", userId);
						intent.putExtra("token", token);
						setResult(Constant.INTENT.REGIS_RESULT, intent);
						finish();
						
					}else if(status2.equals("2")){
						Toast.makeText(SetPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
					}else if(status2.equals("3")){
						Toast.makeText(SetPasswordActivity.this, "其他错误", Toast.LENGTH_SHORT).show();
					}
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
		case R.id.setpass_back:
			finish();
			break;
		case R.id.setpass_finish:
			firstPass = mFirstPass.getText().toString().trim();
			secondPass = mAgaintPass.getText().toString().trim();
			if(firstPass.equals("")&&secondPass.equals("")){
				Toast.makeText(SetPasswordActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else{
				if(!firstPass.equals(secondPass)){
					Toast.makeText(SetPasswordActivity.this, "两次输入密码不一样", Toast.LENGTH_LONG).show();
				}else{
					initData(secondPass);
					Toast.makeText(SetPasswordActivity.this, "注册成功", Toast.LENGTH_LONG).show();
					finish();
				}
				
			}
			break;

		default:
			break;
		}
		
	}

}
