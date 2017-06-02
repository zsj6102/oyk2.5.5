package zsj.com.oyk255.example.ouyiku.fragment;
import java.util.HashMap;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTop;
import zsj.com.oyk255.example.ouyiku.brandjson.MyWalletTopData;
import zsj.com.oyk255.example.ouyiku.footjson.BankInfo;
import zsj.com.oyk255.example.ouyiku.footjson.BankInfoData;
import zsj.com.oyk255.example.ouyiku.regisjson.Data;
import zsj.com.oyk255.example.ouyiku.regisjson.Regisyanzm;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.Safetivity;
import zsj.com.oyk255.example.ouyiku.v1.WebviewActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class TixianFragment extends Fragment implements OnClickListener{

	private View view;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private TextView eTtixian_ID;
	private String cardnumber;
	private String cardId;
	private EditText eTtixian_password;
	private EditText eTtixian_money;
	private TextView btn_sure;
	private TextView allMoneyt;
	private TextView tiXianMoney;
	private EditText eTtixian_name;

	public TixianFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_tixian, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initBankInfo();
			
		}
		
		return view;
	}
	@Override
	public void onStart() {
		Log.e("onStart", "onStart");
		initBankInfo();
		super.onStart();
	}
	
	@Override
	public void onResume() {
		Log.e("onResume", "onResume");
		initBankInfo();
		super.onResume();
	}
	@Override
	public void onStop() {
		Log.e("onStop", "onStop");
		super.onStop();
	}
	@Override
	public void onDestroyView() {
		Log.e("onDestroyView", "onDestroyView");
		super.onDestroyView();
	}
	@Override
	public void onDestroy() {
		Log.e("onDestroy", "onDestroy");
		super.onDestroy();
	}
	private void initData() {
		String url= Constant.URL.WalletTopURL+"&user_id="+userid+"&token="+token;
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				MyWalletTop fromJson = gson.fromJson(arg0, MyWalletTop.class);
				zsj.com.oyk255.example.ouyiku.brandjson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					MyWalletTopData data = fromJson.getData();
					String allMoney = data.getAllMoney();
					String usefullMoney = data.getUsefullMoney();
					allMoneyt.setText(allMoney);
					tiXianMoney.setText(usefullMoney);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	
	private void TiXian(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.TixianUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		String pass = eTtixian_password.getText().toString().trim();
		String money = eTtixian_money.getText().toString().trim();
		map.put("user_id", userid);
		map.put("money", money);
		map.put("card_id", cardId);
		map.put("paypassword", pass);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("arg0", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				Regisyanzm fromJson = gson.fromJson(arg0, Regisyanzm.class);
				  zsj.com.oyk255.example.ouyiku.regisjson.Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					Data data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						ToastUtils.toast(getActivity(), "申请成功");
						initData();
					}
					if(status2.equals("2")){
						ToastUtils.toast(getActivity(), "超出提现金额");
					}
					if(status2.equals("3")){
						ToastUtils.toast(getActivity(), "提现密码错误");
					}
					
				}
			
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}
	private void initBankInfo() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.UserBankInfoUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				BankInfo fromJson = gson.fromJson(arg0, BankInfo.class);
				 zsj.com.oyk255.example.ouyiku.footjson.Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					BankInfoData data = fromJson.getData();
					
					if(data!=null){
						
						String bankimage = data.getBankimage();
						String bankname = data.getBankname();
						cardnumber = data.getCardnumber();
						cardId = data.getCardId();
						eTtixian_ID.setText(cardnumber);
						btn_sure.setClickable(true);
						btn_sure.setBackgroundResource(R.drawable.button_bg);
					}else{
						eTtixian_ID.setText("请先绑定银行卡");
						btn_sure.setClickable(false);
						cardId = null;
						btn_sure.setBackgroundResource(R.drawable.button_bg_unbind);
					}
					Log.e("cardId", "cardId："+cardId);
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}

	private void initUI() {
		eTtixian_name = (EditText) view.findViewById(R.id.tixian_name);
		eTtixian_ID = (TextView) view.findViewById(R.id.tixian_card);
		eTtixian_money = (EditText) view.findViewById(R.id.tixian_money);
		eTtixian_password = (EditText) view.findViewById(R.id.tixian_password);
		TextView TV_forget = (TextView) view.findViewById(R.id.tixian_forget);
		TV_forget.setOnClickListener(this);
		
		btn_sure = (TextView) view.findViewById(R.id.tixian_btn);
		btn_sure.setOnClickListener(this);
		btn_sure.setClickable(false);
		TextView TV_xieyi = (TextView) view.findViewById(R.id.tiixan_xieyi);
		TV_xieyi.setOnClickListener(this);
		
		allMoneyt = (TextView) getActivity().findViewById(R.id.wallet_zongzichan);
		tiXianMoney = (TextView) getActivity().findViewById(R.id.wallet_tixian);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tixian_btn:
			String money = eTtixian_money.getText().toString().trim();
			if(!money.equals("")){
				int i = Integer.parseInt(money);
			if(i>0){
				TiXian();
			}else{
				ToastUtils.toast(getActivity(), "请输入正确金额金额");
			}
			}else{
				
				ToastUtils.toast(getActivity(), "请输入提现金额");
			}
			
			break;
		case R.id.tixian_forget:
			startActivity(new Intent(getActivity(), Safetivity.class));
			break;
		case R.id.tiixan_xieyi:
			String url=Constant.WebUrl.TixianXieyiURL;
			Intent intent = new Intent(getActivity(), WebviewActivity.class);
			intent.putExtra("title", "提现协议");
			intent.putExtra("url", url);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
