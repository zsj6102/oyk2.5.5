package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import zsj.com.oyk255.R;

public class WelComeActivity extends Activity {

	Handler handler=new Handler();
	private SharedPreferences sp;
	private Editor edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel_come);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		PushAgent.getInstance(this).onAppStart();
		
		sp = getSharedPreferences("data", 0);
		edit = sp.edit();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				boolean isFirst = sp.getBoolean("isFirst", true);
				if(isFirst){
					intent.setClass(WelComeActivity.this, YinDaoActivity.class);
					edit.putBoolean("isFirst", false);
					edit.commit();
				}else{
					intent.setClass(WelComeActivity.this, MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, 1500);
	}

}
