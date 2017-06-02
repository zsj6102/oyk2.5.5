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

public class ChangePhoneActivity extends OykActivity implements OnClickListener{

	
	
	
	private String userid;//用户id
	private String token;//用户token
	private EditText mET_phone;
	private TextView mBt_sendYZM;
	private EditText mET_intoYZM;
	private TextView mBt_NEXT;
	private TimeCount timeCount;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
		
	}

	private void SendYZM() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String phone = mET_phone.getText().toString().trim();
		String url= Constant.URL.ChangePhoneYZMURL+"&token="+token+"&mobile="+phone;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
				 Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					Data data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						Toast.makeText(ChangePhoneActivity.this, "发送中..", Toast.LENGTH_SHORT).show();
						mET_intoYZM.requestFocus();
						timeCount = new TimeCount(60000, 1000);
						timeCount.start();
					}
					if(status2.equals("2")){
						Toast.makeText(ChangePhoneActivity.this, "该号码已注册", Toast.LENGTH_SHORT).show();
					}
					
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
			String phone = mET_phone.getText().toString();
			String code = mET_intoYZM.getText().toString();
			String RegisVerifyYZM=Constant.URL.ChangePhoneVerifyURL+"&token="+token+"&mobile="+phone+"&code="+code;
			
			HTTPUtils.get(this, RegisVerifyYZM, new VolleyListener() {
				
				@Override
				public void onResponse(String arg0) {
					progressHUD.dismiss();
					Gson gson = new Gson();
					RegisYanZM fromJson = gson.fromJson(arg0, RegisYanZM.class);
					 Status status = fromJson.getStatus();
					String succeed = status.getSucceed();
					if(succeed.equals("1")){
						Toast.makeText(ChangePhoneActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(ChangePhoneActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
						
					}
				}
				
				@Override
				public void onErrorResponse(VolleyError arg0) {
					progressHUD.dismiss();
				}
			});
					
		}
	private void initUI() {
		mET_phone = (EditText) findViewById(R.id.change_phone);
		mBt_sendYZM = (TextView) findViewById(R.id.change_btn);
		mET_intoYZM = (EditText) findViewById(R.id.change_yanzhengma);
		mBt_NEXT = (TextView) findViewById(R.id.change_next);
		findViewById(R.id.change_back).setOnClickListener(this);
		
		mBt_sendYZM.setOnClickListener(this);
		mBt_NEXT.setOnClickListener(this);
		
		//edittext手机号码状态改变监听
		mET_phone.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						if(RegexValidateUtil.checkMobileNumber(mET_phone.getText().toString().trim())){
							mBt_sendYZM.setBackgroundResource(R.drawable.button_bg);
							mBt_sendYZM.setClickable(true);
							
						}else{
							mBt_sendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
							mBt_sendYZM.setClickable(false);
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
		
		mET_intoYZM.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(mET_intoYZM.getText()!=null){
					mBt_NEXT.setClickable(true);
					mBt_NEXT.setBackgroundResource(R.drawable.button_bg);
				}else{
					mBt_NEXT.setBackgroundResource(R.drawable.button_bg_unbind);
					mBt_NEXT.setClickable(false);
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
	//验证码倒计时
		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			}

			@Override
			public void onFinish() {// 计时完毕
				if(RegexValidateUtil.checkMobileNumber(mET_phone.getText().toString().trim())){
					mBt_sendYZM.setBackgroundResource(R.drawable.button_bg);
					mBt_sendYZM.setText("获取验证码");
					mBt_sendYZM.setClickable(true);
				}else{
					mBt_sendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
					mBt_sendYZM.setText("获取验证码");
					mBt_sendYZM.setClickable(false);
				}
			}

			@Override
			public void onTick(long millisUntilFinished) {// 计时过程
				mBt_sendYZM.setBackgroundResource(R.drawable.button_bg_unbind);
				mBt_sendYZM.setClickable(false);//防止重复点击
				mBt_sendYZM.setText(millisUntilFinished / 1000 + "秒后重发");
			}
		}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_btn:
			SendYZM();
			
			break;
		case R.id.change_next:
			VerifyYZM();
			
			break;
		case R.id.change_back:
			finish();
			
			break;

		default:
			break;
		}
		
	}
}
