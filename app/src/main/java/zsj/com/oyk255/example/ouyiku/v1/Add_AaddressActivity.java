package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import zsj.com.oyk255.R;

import zsj.com.oyk255.example.ouyiku.personcenterjson.GetAddress;
import zsj.com.oyk255.example.ouyiku.personcenterjson.GetAddressDatum;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Add_AaddressActivity extends OykActivity implements OnClickListener{

	/*收货地址列表
	 * 
	 * */
	private ListView mListView;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<GetAddressDatum> mAddressData=new ArrayList<GetAddressDatum>();
	private ImageView mBackground;
	private boolean isOrder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();
		setContentView(R.layout.activity_add__aaddress);
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		Intent intent = getIntent();
		isOrder = intent.getBooleanExtra("isOrder", false);
		
		initUI();
	}
	@Override
	protected void onStart() {
		super.onStart();
		initData();
	}
	private void initData() {
		String url= Constant.URL.GetAddressURL+"&user_id="+userid+"&token="+token;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
					Gson gson = new Gson();
					GetAddress fromJson = gson.fromJson(arg0, GetAddress.class);
					Status status = fromJson.getStatus();
					String succeed = status.getSucceed();
					if(succeed.equals("1")){
						List<GetAddressDatum> data = fromJson.getData();
						if(data.size()!=0){
							mBackground.setVisibility(View.GONE);
							mAddressData.clear();
							mAddressData.addAll(data);
							mListView.setAdapter(new AddressAdapter());
							
						}else{
							mBackground.setVisibility(View.VISIBLE);
						}
					}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}

	private void initUI() {
		findViewById(R.id.add_addview).setOnClickListener(this);
		findViewById(R.id.add_back).setOnClickListener(this);
		mBackground = (ImageView) findViewById(R.id.add_nothing);
		
		mListView = (ListView) findViewById(R.id.add_listview);
		if(isOrder){
			mListView.setOnItemClickListener(new OnItemClickListener() {
				
							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								Intent intent = new Intent(Add_AaddressActivity.this, AddressActivity.class);
								GetAddressDatum getAddressDatum = mAddressData.get(position);
								String addressId = getAddressDatum.getAddressId();
								String address = getAddressDatum.getAddress();
							    String city = getAddressDatum.getCity();
								String country = getAddressDatum.getCountry();
								String isUse = getAddressDatum.getIsUse();
								String mobile = getAddressDatum.getMobile();
								String name = getAddressDatum.getName();
								String province = getAddressDatum.getProvince();
							
								
								
								intent.putExtra("province", province);
								intent.putExtra("city", city);
								intent.putExtra("country", country);
								intent.putExtra("mobile", mobile);
								intent.putExtra("name", name);
								intent.putExtra("address", address);
								intent.putExtra("address_id", addressId);
								setResult(110, intent);
								finish();
							}
						});
		}
	}
	class AddressAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mAddressData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder holder=null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.add_address_item, null);
				holder=new ViewHolder();
				holder.add_shouhuo1 = (TextView) layout.findViewById(R.id.add_shouhuo1);
				holder.add_moren = (TextView) layout.findViewById(R.id.add_moren);
				holder.bianji = (TextView) layout.findViewById(R.id.bianji);
				holder.add_name = (TextView) layout.findViewById(R.id.add_name);
				holder.add_phone = (TextView) layout.findViewById(R.id.add_phone);
				holder.add_address = (TextView) layout.findViewById(R.id.add_address);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			holder.add_shouhuo1.setText("收货地址"+(position+1));
			
			GetAddressDatum getAddressDatum = mAddressData.get(position);
			final String addressId = getAddressDatum.getAddressId();
			final String address = getAddressDatum.getAddress();
			final String city = getAddressDatum.getCity();
			final String country = getAddressDatum.getCountry();
			final String isUse = getAddressDatum.getIsUse();
			final String mobile = getAddressDatum.getMobile();
			final String name = getAddressDatum.getName();
			final String province = getAddressDatum.getProvince();
			
			
			if(isUse.equals("1")){
				holder.add_moren.setVisibility(View.VISIBLE);
			}else{
				holder.add_moren.setVisibility(View.GONE);
				
			}
			holder.add_name.setText(name);
			holder.add_phone.setText(mobile);
			holder.add_address.setText(province+city+country+address);
			
			holder.bianji.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(Add_AaddressActivity.this, AddressActivity.class);
					GetAddressDatum getAddressDatum = mAddressData.get(position);
					final String addressId = getAddressDatum.getAddressId();
					final String address = getAddressDatum.getAddress();
					final String city = getAddressDatum.getCity();
					final String country = getAddressDatum.getCountry();
					final String isUse = getAddressDatum.getIsUse();
					final String mobile = getAddressDatum.getMobile();
					final String name = getAddressDatum.getName();
					final String province = getAddressDatum.getProvince();
					String province_id = getAddressDatum.getProvince_id();
					String city_id = getAddressDatum.getCity_id();
					String country_id = getAddressDatum.getCountry_id();
					
					intent.putExtra("city", city);
					intent.putExtra("country", country);
					intent.putExtra("province", province);
					intent.putExtra("province_id", province_id);
					intent.putExtra("city_id", city_id);
					intent.putExtra("country_id", country_id);
					
					intent.putExtra("type", "2");
					intent.putExtra("address_id", addressId);
					
					intent.putExtra("name", name);
					intent.putExtra("mobile", mobile);
					intent.putExtra("isUse", isUse);
					intent.putExtra("address", address);
					startActivityForResult(intent, Constant.INTENT.ADDRESS_REQUEST2);
					
				}
			});
			
			return layout;
		}
		
	}
	class ViewHolder{
		TextView add_shouhuo1;
		TextView add_moren;
		TextView bianji;
		TextView add_name;
		TextView add_phone;
		TextView add_address;
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_addview:
			Intent intent = new Intent(this, AddressActivity.class);
			intent.putExtra("type", "1");
			intent.putExtra("address_id", "");
			
			startActivityForResult(intent, Constant.INTENT.ADDRESS_REQUEST);
			
			break;
		case R.id.add_back:
			finish();
			
			break;

		default:
			break;
		}
	}

}
