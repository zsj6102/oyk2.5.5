package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetail;
import zsj.com.oyk255.example.ouyiku.homejson.TimeSaleDetailData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ValidFragment")
public class DetailBottomFragment extends Fragment {

	private View view;
	String productId;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	private WebView mWebView;
	public DetailBottomFragment(String productId) {
		this.productId=productId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_detail_bottom, container,
					false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initView();
			initData();
		}
		return view;
	}

	private void initData() {
		String url= Constant.URL.TimeDetailUrl;
		
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("product_id", productId);
		map.put("user_id", userid);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				TimeSaleDetail fromJson = gson.fromJson(arg0, TimeSaleDetail.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					TimeSaleDetailData data = fromJson.getData();
					String proDetail = data.getProDetail();
					WebSettings settings = mWebView.getSettings();
					settings.setJavaScriptEnabled(true);
					mWebView.loadUrl(proDetail);
				}
				
				
				
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	public void initView() {
		mWebView = (WebView) view.findViewById(R.id.fragment2_webview);
		
	}
	

}
