package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;

public class Safetivity extends OykActivity implements OnClickListener{

	private String isSetting;
	private SharedPreferences sp;
	private ImageView mImg_Tixian;
	private TextView mTv_Tixian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safetivity);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("SetTiXian", 0);
		isSetting = sp.getString("IsSetTiXian", "no");
		initUI();
		
	}

	private void initUI() {
		findViewById(R.id.safe_back).setOnClickListener(this);
		findViewById(R.id.loginpass).setOnClickListener(this);
		findViewById(R.id.tixianpass).setOnClickListener(this);
		mImg_Tixian = (ImageView) findViewById(R.id.safe_img2);
		mTv_Tixian = (TextView) findViewById(R.id.safe_tv);
		if(isSetting.equals("yes")){
			mImg_Tixian.setImageResource(R.mipmap.right);
			mTv_Tixian.setText("修改");
		}else{
			mImg_Tixian.setImageResource(R.mipmap.o_no);
			mTv_Tixian.setText("设置");
			
		}
	}
	@Override
	protected void onStart() {
		sp = getSharedPreferences("SetTiXian", 0);
		isSetting = sp.getString("IsSetTiXian", "no");
		if(isSetting.equals("yes")){
			mImg_Tixian.setImageResource(R.mipmap.right);
			mTv_Tixian.setText("修改");
		}else{
			mImg_Tixian.setImageResource(R.mipmap.o_no);
			mTv_Tixian.setText("设置");
		}
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.safe_back:
			finish();
			
			break;
		case R.id.loginpass:
			Intent intent2 = new Intent(this, ChangePassActivity.class);
			startActivity(intent2);
			
			break;
		case R.id.tixianpass:
			Intent intent = new Intent(Safetivity.this, SetTiXianPassActivity.class);
			
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}

}
