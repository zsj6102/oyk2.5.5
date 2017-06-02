package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Adress_pop;
import zsj.com.oyk255.example.ouyiku.footjson.BankList;
import zsj.com.oyk255.example.ouyiku.footjson.BankListDatum;
import zsj.com.oyk255.example.ouyiku.personcenterjson.FirstAdress;
import zsj.com.oyk255.example.ouyiku.personcenterjson.FirstAdressDatum;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class BindCardActivity extends OykActivity implements OnClickListener{

	private Adress_pop adress_pop;
	private ListView mBank_Listview;
	private ListView mAddress_Listview;
	ArrayList<BankListDatum> mBankData=new ArrayList<BankListDatum>();
	ArrayList<FirstAdressDatum> mFirstData=new ArrayList<FirstAdressDatum>();
	String type="1";//判断省市区
	private StringBuffer stringBuffer;
	private String maddress;

	private String province;
	private String city;
	private String country;
	private TextView mTv_city;
	private TextView mTv_bank;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private String bankID;
	private EditText mEt_city2;
	private EditText mEt_banknum;
	private EditText mEt_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_card);
		PushAgent.getInstance(this).onAppStart();
		sp = getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initUI();
	}

	private void initUI() {
		stringBuffer = new StringBuffer();
		findViewById(R.id.bind_back).setOnClickListener(this);
		mTv_bank = (TextView) findViewById(R.id.tv_bank);
		mTv_city = (TextView) findViewById(R.id.tv_city);
		mEt_city2 = (EditText) findViewById(R.id.et_city2);
		mEt_banknum = (EditText) findViewById(R.id.et_banknum);
		mEt_name = (EditText) findViewById(R.id.et_name);
		findViewById(R.id.view_bank).setOnClickListener(this);
		findViewById(R.id.view_address).setOnClickListener(this);
		
		
		findViewById(R.id.bind_btn).setOnClickListener(this);
		adress_pop = new Adress_pop(this);
		
	}
	
	private void BindBankCard(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.BindBankUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		String kaihuAdd = mEt_city2.getText().toString().trim();
		String BankNum = mEt_banknum.getText().toString().trim();
		String name = mEt_name.getText().toString().trim();
		
		map.put("user_id", userid);
		map.put("bank_id", bankID);
		map.put("province", province);
		map.put("city", city);
		map.put("address", kaihuAdd);
		map.put("cardnumber", BankNum);
		map.put("cardname", name);
		
		
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				BankList fromJson = gson.fromJson(arg0, BankList.class);
				zsj.com.oyk255.example.ouyiku.footjson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					ToastUtils.toast(BindCardActivity.this, "绑定成功");
					finish();
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
		case R.id.bind_back:
			finish();
			
			break;
		case R.id.bind_btn:
			BindBankCard();
			break;
		case R.id.view_bank:
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
	           imm.hideSoftInputFromWindow(mEt_city2.getWindowToken(),0);   
	           imm.hideSoftInputFromWindow(mEt_banknum.getWindowToken(),0);   
	           imm.hideSoftInputFromWindow(mEt_name.getWindowToken(),0);   
			adress_pop.showAtLocation(findViewById(R.id.BindLAYOUT), Gravity.CENTER, 0, 0);
			adress_pop.view.findViewById(R.id.address_quxiao).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					adress_pop.dismiss();
				}
			});
			
			mBank_Listview = (ListView) adress_pop.view.findViewById(R.id.address_pop_listView);
			GetBankList();
			mBank_Listview.setOnItemClickListener(new OnItemClickListener() {

				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					BankListDatum bankListDatum = mBankData.get(position);
					String bankname = bankListDatum.getBankname();
					bankID = bankListDatum.getId();
					
					mTv_bank.setText(bankname);
					adress_pop.dismiss();
				}
			});
			
			
			break;
		case R.id.view_address:
			InputMethodManager immm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
	           immm.hideSoftInputFromWindow(mEt_city2.getWindowToken(),0);   
	           immm.hideSoftInputFromWindow(mEt_banknum.getWindowToken(),0);   
	           immm.hideSoftInputFromWindow(mEt_name.getWindowToken(),0);
			
			adress_pop.showAtLocation(findViewById(R.id.BindLAYOUT), Gravity.CENTER, 0, 0);
			mAddress_Listview = (ListView) adress_pop.view.findViewById(R.id.address_pop_listView);
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
						mTv_city.setText(maddress);
						type="1";
							adress_pop.dismiss();
							stringBuffer.replace(0, stringBuffer.length(),"");
					}
					
				}
			});
			
			break;

		default:
			break;
		}
		
	}

	private void GetBankList() {
		String url=Constant.URL.BinkListUrl;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this); 
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				BankList fromJson = gson.fromJson(arg0, BankList.class);
				zsj.com.oyk255.example.ouyiku.footjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					progressHUD.dismiss();
					List<BankListDatum> data = fromJson.getData();
					mBankData.clear();
					mBankData.addAll(data);
					mBank_Listview.setAdapter(new BankListAdp());
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	class BankListAdp extends BaseAdapter{

		@Override
		public int getCount() {
			return mBankData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.pop_address_item, null);
			TextView  mTv= (TextView) view.findViewById(R.id.pop_address_tv);
			BankListDatum bankListDatum = mBankData.get(position);
			String bankname = bankListDatum.getBankname();
			mTv.setText(bankname);
			
			
			return view;
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

}
