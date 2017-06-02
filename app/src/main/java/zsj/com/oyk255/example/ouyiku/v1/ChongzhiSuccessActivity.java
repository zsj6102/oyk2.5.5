package zsj.com.oyk255.example.ouyiku.v1;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTop;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTopData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class ChongzhiSuccessActivity extends OykActivity implements OnClickListener{

	private TextView mTotalmoney;
	private TextView mCzmoney;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chongzhi_success);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		total = intent.getStringExtra("czmoney");
		initUI();
		updataData();
	}

	private void initUI() {
		findViewById(R.id.czsucess_back).setOnClickListener(this);
		findViewById(R.id.cz_success_toshop).setOnClickListener(this);
		findViewById(R.id.cz_success_finish).setOnClickListener(this);
		mTotalmoney = (TextView) findViewById(R.id.czsucess_totalmoney);
		mCzmoney = (TextView) findViewById(R.id.czsucess_czmoney);
		mCzmoney.setText(total+"元");
	}
	
		private void updataData(){
		
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.WalletTopURL+"&user_id="+userid+"&token="+token;
		
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MyWalletTop fromJson = gson.fromJson(arg0, MyWalletTop.class);
				 Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					MyWalletTopData data = fromJson.getData();
					String allMoney = data.getAllMoney();
					mTotalmoney.setText(allMoney+"元");
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
		case R.id.czsucess_back:
			finish();
			break;
		case R.id.cz_success_toshop:
			startActivity(new Intent(ChongzhiSuccessActivity.this, HotSaleActivity.class));
			finish();
			break;
		case R.id.cz_success_finish:
			finish();
			break;

		default:
			break;
		}
		
	}

}
