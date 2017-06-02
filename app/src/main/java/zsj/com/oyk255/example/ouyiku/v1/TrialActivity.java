package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import zsj.com.oyk255.R;

public class TrialActivity extends OykActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);
		PushAgent.getInstance(this).onAppStart();
		initUI();
	}

	private void initUI() {
		findViewById(R.id.mytry_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mytry_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
