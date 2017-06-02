package zsj.com.oyk255.example.ouyiku.v1;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.ServiceInfo;
import zsj.com.oyk255.example.ouyiku.brandjson.ServiceInfoData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Sale_afterActivity extends OykActivity implements OnClickListener{

	private TextView mQq;
	private TextView mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_after);
		PushAgent.getInstance(this).onAppStart();
		initUI();
		initPhone();
	}

	private String mobile;
	private void initPhone() {
		String url= Constant.URL.SalePhoneUrl;
		HTTPUtils.get(this, url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				ServiceInfo fromJson = gson.fromJson(arg0, ServiceInfo.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					ServiceInfoData data = fromJson.getData();
					mobile = data.getMobile();
					String qq = data.getQq();
					mPhone.setText(mobile);
					mQq.setText(qq);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initUI() {
		findViewById(R.id.back_shouhou).setOnClickListener(this);
		findViewById(R.id.apply_ing).setOnClickListener(this);
		findViewById(R.id.apply_success).setOnClickListener(this);
		findViewById(R.id.apply_false).setOnClickListener(this);
		findViewById(R.id.shouhouxieyi).setOnClickListener(this);
		mPhone = (TextView) findViewById(R.id.phone);
		mQq = (TextView) findViewById(R.id.qq);
		mPhone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_shouhou:
			finish();
			break;
		case R.id.apply_ing:
			Intent intent = new Intent(this, SaleAfter_ingActivity.class);
			intent.putExtra("status", "saleing");
			intent.putExtra("title", "申请中");
			startActivity(intent);
			break;
		case R.id.apply_success:
			Intent intent2 = new Intent(this, SaleAfter_ingActivity.class);
			intent2.putExtra("status", "success");
			intent2.putExtra("title", "售后成功");
			startActivity(intent2);
			break;
		case R.id.apply_false:
			Intent intent3 = new Intent(this, SaleAfter_ingActivity.class);
			intent3.putExtra("status", "false");
			intent3.putExtra("title", "售后失败");
			startActivity(intent3);
			break;
		case R.id.shouhouxieyi:
			Intent intent4 = new Intent(this, WebviewActivity.class);
			intent4.putExtra("title", "售后服务条例");
			intent4.putExtra("url", Constant.WebUrl.SaleServiceURL);
			startActivity(intent4);
			break;
		case R.id.phone:
			Intent intent5 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
			startActivity(intent5);
			break;

		default:
			break;
		}
		
	}

}
