package zsj.com.oyk255.example.ouyiku.v1;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.regisYZMjson.Data;
import zsj.com.oyk255.example.ouyiku.regisYZMjson.RegisYanZM;
import zsj.com.oyk255.example.ouyiku.regisYZMjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.RegexValidateUtil;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SetTiXianPassActivity extends OykActivity implements OnClickListener{
	
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private EditText mPhone_et;
	private EditText mYZM_et;
	private TextView mSendYZM;
	private TextView mNext;
	TimeCount timeCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_ti_xian_pass);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		
	}

	private void initUI() {
		mPhone_et = (EditText) findViewById(R.id.forget_phone);
		mSendYZM = (TextView) findViewById(R.id.forget_btn);
		mYZM_et = (EditText) findViewById(R.id.forget_yanzhengma);
		mNext = (TextView) findViewById(R.id.forget_next);
		findViewById(R.id.tixian_back).setOnClickListener(this);
		mSendYZM.setClickable(false);
		mNext.setClickable(false);
		mSendYZM.setOnClickListener(this);
		mNext.setOnClickListener(this);
		mPhone_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(RegexValidateUtil.checkMobileNumber(mPhone_et.getText().toString().trim())){
					mSendYZM.setBackgroundResource(R.drawable.button_bg);
					mSendYZM.setClickable(true);
					
				}else{
					mSendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
					mSendYZM.setClickable(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mYZM_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(mYZM_et.getText()!=null){
					mNext.setClickable(true);
					mNext.setBackgroundResource(R.drawable.button_bg);
				}else{
					mNext.setBackgroundResource(R.drawable.button_bg_unbind);
					mNext.setClickable(false);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
	}
	
	private void getYZM(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String phone = mPhone_et.getText().toString().trim();
		String url= Constant.URL.ChangePassYZMURL+"&user_id="+userid+"&token="+token+"&mobile="+phone;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						Toast.makeText(SetTiXianPassActivity.this, "正在发送验证码", Toast.LENGTH_SHORT).show();
						mYZM_et.requestFocus();
						timeCount = new TimeCount(60000, 1000);
						timeCount.start();
					}else if(status2.equals("0")){}
					Toast.makeText(SetTiXianPassActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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
		case R.id.forget_btn:
			getYZM();
			break;
		case R.id.forget_next:
			 VerifyYZM();
			break;
		case R.id.tixian_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	private void VerifyYZM() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		final String phone = mPhone_et.getText().toString().trim();
		final String code = mYZM_et.getText().toString().trim();
		String url=Constant.URL.SetPayPassVerifyYZMUrl+"&mobile="+phone+"&code="+code;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Intent intent = new Intent(SetTiXianPassActivity.this,SetTixianActivity.class);
					intent.putExtra("phone", phone);
					intent.putExtra("code", code);
					startActivity(intent);
					finish();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
			//验证码倒计时
			class TimeCount extends CountDownTimer {
				public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
				}

				@Override
				public void onFinish() {// 计时完毕
					if(RegexValidateUtil.checkMobileNumber(mPhone_et.getText().toString().trim())){
						mSendYZM.setBackgroundResource(R.drawable.button_bg);
						mSendYZM.setText("获取验证码");
						mSendYZM.setClickable(true);
					}else{
						mSendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
						mSendYZM.setText("获取验证码");
						mSendYZM.setClickable(false);
						Toast.makeText(SetTiXianPassActivity.this, "无效的手机号码", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onTick(long millisUntilFinished) {// 计时过程
					mSendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
					mSendYZM.setClickable(false);//防止重复点击
					mSendYZM.setText(millisUntilFinished / 1000 + "秒后重发");
				}
			}
}