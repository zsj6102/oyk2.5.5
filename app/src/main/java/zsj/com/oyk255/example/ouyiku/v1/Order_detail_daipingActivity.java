package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.os.Bundle;

import zsj.com.oyk255.R;

public class Order_detail_daipingActivity extends OykActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail_daiping);
		PushAgent.getInstance(this).onAppStart();
	}

}
