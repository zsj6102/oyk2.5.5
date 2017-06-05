package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetail;
import zsj.com.oyk255.example.ouyiku.brandjson.PinTuanDetailData;
import zsj.com.oyk255.example.ouyiku.brandjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */

@SuppressLint("ValidFragment")
public class PinDetailBottomFragment extends Fragment {

	private View view;
	private String groupsId;
	private WebView mWebView;
	
	public PinDetailBottomFragment() {
		// Required empty public constructor
	}
	public PinDetailBottomFragment(String groupsId) {
		this.groupsId=groupsId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_pin_detail_bottom, container,
					false);
			initView();
			initData();
		}
		return view;
	}
	
	private void initData() {
		String url= Constant.URL.PinTuanDetailUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("groups_id", groupsId);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
					Gson gson = new Gson();
					PinTuanDetail fromJson = gson.fromJson(arg0, PinTuanDetail.class);
					Status status = fromJson.getStatus();
					if(status.getSucceed().equals("1")){
						PinTuanDetailData data = fromJson.getData();
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
		mWebView = (WebView) view.findViewById(R.id.pinfragment2_webview);
		
	}

}
