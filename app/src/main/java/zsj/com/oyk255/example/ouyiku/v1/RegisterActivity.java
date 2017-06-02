package zsj.com.oyk255.example.ouyiku.v1;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
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
import zsj.com.oyk255.example.ouyiku.regisjson.Regisyanzm;
import zsj.com.oyk255.example.ouyiku.regisjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.RegexValidateUtil;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class RegisterActivity extends OykActivity implements OnClickListener{

	private TextView mTv_next;
	private EditText mEt_phone;
	private TextView mSendYZM;
	private TimeCount timeCount;
	private TextView btn_next;
	private EditText mEt_YZM;//验证码
	private EditText mEt_YQM;//邀请码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		PushAgent.getInstance(this).onAppStart();
		initUI();
	}

	//获取验证码请求
	private void sendYZM() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String RegisPhoneUrl= Constant.URL.RegisSendYZMURL+mEt_phone.getText();
		HTTPUtils.get(this, RegisPhoneUrl, new VolleyListener() {

						@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				Regisyanzm fromJson = gson.fromJson(arg0, Regisyanzm.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					zsj.com.oyk255.example.ouyiku.regisjson.Data data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("0")){
						Toast.makeText(RegisterActivity.this, "该号码已注册", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(RegisterActivity.this, "正在发送验证码", Toast.LENGTH_SHORT).show();
					}
				}else {
						Toast.makeText(RegisterActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
				 
				}
			}
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	//验证验证码
	private void VerifyYZM(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String RegisVerifyYZM=Constant.URL.RegisVerifyURL+mEt_phone.getText()+"&code="+mEt_YZM.getText()+"&invitation="+mEt_YQM.getText();
		
		HTTPUtils.get(this, RegisVerifyYZM, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
				zsj.com.oyk255.example.ouyiku.regisYZMjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Data data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("0")){
						Toast.makeText(RegisterActivity.this, "该号码已经注册",Toast.LENGTH_SHORT ).show();
					}else if(status2.equals("1")){
						Intent intent = new Intent(RegisterActivity.this, SetPasswordActivity.class);
						intent.putExtra("YanZheng", mEt_YZM.getText().toString());
						intent.putExtra("Phone", mEt_phone.getText().toString());
						intent.putExtra("YaoQing", mEt_YQM.getText().toString());
						
						startActivity(intent);
						finish();
						Toast.makeText(RegisterActivity.this, "验证成功",Toast.LENGTH_SHORT ).show();
					}else if(status2.equals("3")){
						Toast.makeText(RegisterActivity.this, "验证码错误",Toast.LENGTH_SHORT ).show();
					}
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
				
	}
	
	private void initUI() {
		
		
		mTv_next = (TextView) findViewById(R.id.regis_next);
		mTv_next.setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		mEt_phone = (EditText) findViewById(R.id.regis_phone);
		mSendYZM = (TextView) findViewById(R.id.regis_btn);
		mEt_YZM = (EditText) findViewById(R.id.regis_yanzhengma);
		mEt_YQM = (EditText) findViewById(R.id.regis_yaoqingma);
		
		mSendYZM.setOnClickListener(this);
		mSendYZM.setClickable(false);
		mTv_next.setClickable(false);
		
		//edittext手机号码状态改变监听
		mEt_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(RegexValidateUtil.checkMobileNumber(mEt_phone.getText().toString().trim())){
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
		//edittext验证码状态改变监听
		mEt_YZM.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(mEt_YZM.getText()!=null){
					mTv_next.setClickable(true);
					mTv_next.setBackgroundResource(R.drawable.button_bg);
				}else{
					mTv_next.setBackgroundResource(R.drawable.button_bg_unbind);
					mTv_next.setClickable(false);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_next:
			VerifyYZM();
			
			break;
		case R.id.regis_back:
			finish();
			break;
		case R.id.regis_btn:
			sendYZM();
			timeCount = new TimeCount(60000, 1000);
			timeCount.start();
			break;

		default:
			break;
		}
	}
	//验证码倒计时
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {// 计时完毕
			if(RegexValidateUtil.checkMobileNumber(mEt_phone.getText().toString().trim())){
				mSendYZM.setBackgroundResource(R.drawable.button_bg);
				mSendYZM.setText("获取验证码");
				mSendYZM.setClickable(true);
			}else{
				mSendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
				mSendYZM.setText("获取验证码");
				mSendYZM.setClickable(false);
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
