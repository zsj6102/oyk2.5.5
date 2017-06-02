package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.footjson.BankInfo;
import zsj.com.oyk255.example.ouyiku.footjson.BankInfoData;
import zsj.com.oyk255.example.ouyiku.footjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.v1.BindCardActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class YinhangkaFragment extends Fragment implements OnClickListener{

	private View view;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	private LinearLayout un_Bind;
	private LinearLayout bank_Bind;
	private ImageView bank_img;
	private TextView nBankName;
	private TextView nBankNum;
	
	public YinhangkaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_yinhangka, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			
		}
		return view;
	}
	
	private void DeleteBank(){
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.DeleteBankUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("card_id", cardId);
		
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				BankInfo fromJson = gson.fromJson(arg0, BankInfo.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					initBankInfo() ;
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	@Override
	public void onStart() {
		initBankInfo();
		super.onStart();
	}
	private String cardId;
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
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					BankInfoData data = fromJson.getData();
					
					if(data!=null){
						bank_Bind.setVisibility(View.VISIBLE);
						un_Bind.setVisibility(View.GONE);
						String bankimage = data.getBankimage();
						String bankname = data.getBankname();
						String cardnumber = data.getCardnumber();
						cardId = data.getCardId();
						
						UILUtils.displayImageNoAnim(bankimage, bank_img);
						nBankName.setText(bankname);
						nBankNum.setText(cardnumber);
					}else{
						bank_Bind.setVisibility(View.GONE);
						un_Bind.setVisibility(View.VISIBLE);
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
		RelativeLayout to_Bind = (RelativeLayout) view.findViewById(R.id.bank_tobindview);
		un_Bind = (LinearLayout) view.findViewById(R.id.bank_unbind);
		bank_Bind = (LinearLayout) view.findViewById(R.id.bank_bind);
		to_Bind.setOnClickListener(this);
		bank_img = (ImageView) view.findViewById(R.id.bank_img);
		nBankName = (TextView) view.findViewById(R.id.bank_bankname);
		nBankNum = (TextView) view.findViewById(R.id.bank_weihao);
		TextView nBankChange = (TextView) view.findViewById(R.id.bank_change);
		nBankChange.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bank_tobindview:
			Intent intent = new Intent(getActivity(), BindCardActivity.class);
			
			startActivity(intent);
			
			break;
		case R.id.bank_change:
			DeleteBank();
			
			break;

		default:
			break;
		}
		
	}

}
