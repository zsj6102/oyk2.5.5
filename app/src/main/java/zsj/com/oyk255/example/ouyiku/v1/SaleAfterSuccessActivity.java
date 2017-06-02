package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleSuccessDetail;
import zsj.com.oyk255.example.ouyiku.brandjson.SaleSuccessDetailData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.orderjson.IsSuccess;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class SaleAfterSuccessActivity extends OykActivity implements OnClickListener{

	private String applyId;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private EditText mKuaiName;
	private EditText mKuaiId;
	private TextView mTuiAddress;
	private TextView mTuiName;
	private TextView mTuiPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_after_success);
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		applyId = intent.getStringExtra("applyId");
		initUI();
		initData();
		
	}
	private void initData() {
			
				String url= Constant.URL.SaleAfterDetailUrl;
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("user_id", userid);
				map.put("token", token);
				map.put("id", applyId);
				HTTPUtils.post(SaleAfterSuccessActivity.this, url, map, new VolleyListener() {
					
					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						SaleSuccessDetail fromJson = gson.fromJson(arg0, SaleSuccessDetail.class);
						Status status = fromJson.getStatus();
						if(status.getSucceed().equals("1")){
							SaleSuccessDetailData data = fromJson.getData();
							String selleraddress = data.getSelleraddress();
							String sellermobile = data.getSellermobile();
							String sellername = data.getSellername();
							
							mTuiAddress.setText(selleraddress);
							mTuiName.setText(sellername);
							mTuiPhone.setText(sellermobile);
						}
						
					}
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
		});
		
	}
	private void initUI() {
		mTuiAddress = (TextView) findViewById(R.id.tuihuoaddress);
		mTuiName = (TextView) findViewById(R.id.tuihuoname);
		mTuiPhone = (TextView) findViewById(R.id.tuihuophone);
		mKuaiName = (EditText) findViewById(R.id.kuaidiname);
		mKuaiId = (EditText) findViewById(R.id.kuaidiid);
		TextView mBtn = (TextView) findViewById(R.id.tuihuo_btn);
		mBtn.setOnClickListener(this);
		findViewById(R.id.salesuccess_back).setOnClickListener(this);
	}
	
	//录入快递单号
	private void IntoKuai(){
		String expressname = mKuaiName.getText().toString().trim();
		String expressnum = mKuaiId.getText().toString().trim();
		String url=Constant.URL.LuRuKuaiDIUrl;
		HashMap<String, String> map=new HashMap<>();
		map.put("id", applyId);
		map.put("user_id", userid);
		map.put("token", token);
		map.put("expressname", expressname);
		map.put("expressnum", expressnum);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				
				IsSuccess fromJson = gson.fromJson(arg0, IsSuccess.class);
				zsj.com.oyk255.example.ouyiku.orderjson.Status status = fromJson.getStatus();
				
				if(status.getSucceed().equals("1")){
					ToastUtils.toast(SaleAfterSuccessActivity.this, "上传成功");
					finish();
				}else{
					ToastUtils.toast(SaleAfterSuccessActivity.this, "上传失败");
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tuihuo_btn:
			IntoKuai();
			
			
			break;
		case R.id.salesuccess_back:
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
