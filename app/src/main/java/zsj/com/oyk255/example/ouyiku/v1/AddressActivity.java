package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Adress_pop;
import zsj.com.oyk255.example.ouyiku.personcenterjson.FirstAdress;
import zsj.com.oyk255.example.ouyiku.personcenterjson.FirstAdressDatum;
import zsj.com.oyk255.example.ouyiku.personcenterjson.IsSuccess;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.SwitchButton;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class AddressActivity extends OykActivity implements OnClickListener{
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private Adress_pop adress_pop;
	ArrayList<FirstAdressDatum> mFirstData=new ArrayList<FirstAdressDatum>();
	private ListView mAddress_Listview;
	
	String type="1";//判断省市区
	private StringBuffer stringBuffer;
	private String maddress;
	private TextView mAllcity;
	private TextView mSure;
	
	private EditText mET_name;
	private EditText mET_phone;
	private EditText mET_detailaddress;
	private SwitchButton mSwitchbutton;
	
	private String province;//id
	private String city;//id
	private String country;//id
	
	private String name;
	private String address;
	private String phone;
	private String isuse;
	private String type2;//判断增加地址还是编辑地址
	private String address_id;
	private TextView mDelete;
	private String str_city;
	private String str_country;
	private String str_province;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		type2 = intent.getStringExtra("type");
		address_id = intent.getStringExtra("address_id");
		initUI();
		//如果是编辑页面跳转过来的获取编辑地址
		if(type2.equals("2")){
			name = intent.getStringExtra("name");
			phone= intent.getStringExtra("mobile");
			isuse = intent.getStringExtra("isUse");
			address = intent.getStringExtra("address");
//			maddress = intent.getStringExtra("ssq");
			mET_name.setText(name);
			mET_phone.setText(phone);
			if(isuse.equals("1")){
				mSwitchbutton.setChecked(true);
			}else{
				mSwitchbutton.setChecked(false);
			}
			mET_detailaddress.setText(address);
			mAllcity.setText(maddress);
			
			str_city = intent.getStringExtra("city");
			str_country = intent.getStringExtra("country");
			str_province = intent.getStringExtra("province");
			province = intent.getStringExtra("province_id");
			city = intent.getStringExtra("city_id");
			country = intent.getStringExtra("country_id");
			mAllcity.setText(str_province+str_city+str_country);
			
			
		}else{
			
		}
		
		
	}
	//编辑收货地址
	private void AddAddress(){
		if(mSwitchbutton.isChecked()){
			isuse="1";
		}else{
			isuse="0";
		}
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.AddAddressURL;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("type", type2);
		map.put("province", province);
		map.put("city", city);
		map.put("country", country);
		map.put("name", name);
		map.put("mobile", phone);
		map.put("address", address);
		map.put("is_use", isuse);
		map.put("address_id", address_id);
				HTTPUtils.post(this, url, map, new VolleyListener() {
					
					@Override
					public void onResponse(String arg0) {
						progressHUD.dismiss();
						Gson gson = new Gson();
						IsSuccess fromJson = gson.fromJson(arg0, IsSuccess.class);
						Status status = fromJson.getStatus();
						String succeed = status.getSucceed();
						if(succeed.equals("1")){
							finish();
						}
						
					}
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						progressHUD.dismiss();
						
					}
				});
				
	
	}
	//获取全国地址
	private void GetArea(String type,String area_id){
		
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
			String url=Constant.URL.GetAreaURL+"&type="+type+"&area_id="+area_id;
			
			HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				FirstAdress fromJson = gson.fromJson(arg0, FirstAdress.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					List<FirstAdressDatum> data = fromJson.getData();
					mFirstData.clear();
					mFirstData.addAll(data);
					mAddress_Listview.setAdapter(new AddRessAdp());
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}
	//删除收货地址
	private void deleteAdddress(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.DeleteAddressURL+"&user_id="+userid+"&token="+token+"&address_id="+address_id;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				IsSuccess fromJson = gson.fromJson(arg0, IsSuccess.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					finish();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		stringBuffer = new StringBuffer();
		mDelete = (TextView) findViewById(R.id.address_delete);
		mDelete.setOnClickListener(this);
		if(type2.equals("1")){
			mDelete.setVisibility(View.GONE);
		}else{
			mDelete.setVisibility(View.VISIBLE);
			
		}
		mET_name = (EditText) findViewById(R.id.address_name);
		mET_phone = (EditText) findViewById(R.id.address_phone);
		mET_detailaddress = (EditText) findViewById(R.id.address_detailaddress);
		mSwitchbutton = (SwitchButton) findViewById(R.id.address_switchbutton);
		mSwitchbutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					isuse = "1";
				}else{
					isuse="0";
					
				}
				
			}
		});
		mAllcity = (TextView) findViewById(R.id.address_address);
		mAllcity.setOnClickListener(this);
		adress_pop = new Adress_pop(this);
		
		mSure = (TextView) findViewById(R.id.address_sure);
		mSure.setOnClickListener(this);
		findViewById(R.id.address_back).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_address:
			View view = getWindow().peekDecorView();
			if(view!=null){
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
			adress_pop.showAtLocation(findViewById(R.id.address_layout), Gravity.CENTER, 0, 0);
			mAddress_Listview = (ListView) adress_pop.view.findViewById(R.id.address_pop_listView);
			//取消按钮
			adress_pop.view.findViewById(R.id.address_quxiao).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					type="1";
					stringBuffer.replace(0, stringBuffer.length(),"");
					adress_pop.dismiss();
					
				}
			});
			GetArea("1", "");
			
			mAddress_Listview.setOnItemClickListener(new OnItemClickListener() {

				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					if(type.equals("1")){
						if(mFirstData!=null){
							FirstAdressDatum firstAdressDatum = mFirstData.get(position);
							province= firstAdressDatum.getAreaId();
							String name = firstAdressDatum.getName();
							Log.e("areaId1", province);
							Log.e("name1", name);
						
							stringBuffer.append(name);
							Log.e("stringBuffer", ""+stringBuffer);
							GetArea("2", province);
							type="2";
							
						}
						
					}else if(type.equals("2")){
						if(mFirstData!=null){
							FirstAdressDatum firstAdressDatum = mFirstData.get(position);
							city= firstAdressDatum.getAreaId();
							String name = firstAdressDatum.getName();
							stringBuffer.append(name);
							Log.e("areaId2", city);
							Log.e("name2", name);
							GetArea("3", city);
							type="3";
						}
					}else if(type.equals("3")){
						FirstAdressDatum firstAdressDatum = mFirstData.get(position);
						country= firstAdressDatum.getAreaId();
						String name = firstAdressDatum.getName();
						stringBuffer.append(name);
						maddress = stringBuffer.toString();
						
						Log.e("areaId3", country);
						Log.e("name3", name);
						Log.e("stringBuffer", maddress);
						mAllcity.setText(maddress);
						type="1";
							adress_pop.dismiss();
							stringBuffer.replace(0, stringBuffer.length(),"");
					}
					
				}
			});
			
			break;
		case R.id.address_sure:
			
			name = mET_name.getText().toString();
			address = mET_detailaddress.getText().toString();
			phone = mET_phone.getText().toString();
			AddAddress();
			
			break;
		case R.id.address_back:
			finish();
			
			break;
		case R.id.address_delete:
			 deleteAdddress();
			
			break;

		default:
			break;
		}
	}
	
	
	class AddRessAdp extends BaseAdapter{

	@Override
	public int getCount() {
		return mFirstData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = getLayoutInflater().inflate(R.layout.pop_address_item, null);
		TextView  mTv= (TextView) view.findViewById(R.id.pop_address_tv);
		FirstAdressDatum firstAdressDatum = mFirstData.get(position);
		String name = firstAdressDatum.getName();
		mTv.setText(name);
		return view;
	}
	
}
	
	
	
}
