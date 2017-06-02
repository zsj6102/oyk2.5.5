package zsj.com.oyk255.example.ouyiku.v1;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.detailjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class OpinionActivity extends OykActivity {
	
	/*
	 * 意见反馈
	 * */
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private EditText mContent;
	private EditText mPhone;
	private TextView mSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
	}
	private void SendOpinion(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String content = mContent.getText().toString().trim();
		String contact = mPhone.getText().toString().trim();
		String url= Constant.URL.OpinionURL+"&user_id="+userid+"&token="+token+"&content="+content+"&contact="+contact;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					Toast.makeText(OpinionActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
				}else {
					
					Toast.makeText(OpinionActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
				}
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
	}
	private void initUI() {
		mContent = (EditText) findViewById(R.id.opinion_edt);
		mPhone = (EditText) findViewById(R.id.opinion_contact);
		mSend = (TextView) findViewById(R.id.opinion_send);
		findViewById(R.id.opinion_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		mSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SendOpinion();
			}
		});
	}

}
