package zsj.com.oyk255.example.ouyiku.v1;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.v1.HomeHuoDongActivity.JavaScriptObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class HomeHuoDong2Activity extends OykActivity {

	private String webUrl;
	private ProgressBar mBar;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_huo_dong2);
		mContext = getApplicationContext();
		Intent intent = getIntent();
		webUrl = intent.getStringExtra("webUrl");
		initUI();
	}

	private void initUI() {
		mBar = (ProgressBar) findViewById(R.id.huodong2bar);
		WebView mWebView = (WebView) findViewById(R.id.huodong2webview);
		mWebView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.addJavascriptInterface(new JavaScriptObject(mContext), "app");
		mWebView.loadUrl(webUrl);
		mWebView.setWebChromeClient(new WebChromeClient() {

	          @Override
	           public void onProgressChanged(WebView view, int newProgress) {
	               if (newProgress == 100) {
	            	   mBar.setVisibility(View.GONE);
	               } else {
	                   if (View.INVISIBLE == mBar.getVisibility()) {
	                	   mBar.setVisibility(View.VISIBLE);
	                   }
	                   mBar.setProgress(newProgress);
	               }
	               super.onProgressChanged(view, newProgress);
	           }
	           
	       });
		
		
	}
	
	
	public class JavaScriptObject {
		
	    Context mContxt;


	    public JavaScriptObject(Context mContxt)
	    {
	        this.mContxt = mContxt;
	    }
	    
	    /** 与js交互时用到的方法，在js里直接调用的 */
	    @JavascriptInterface
		public void back() {
	    	finish();
	    	
		}
	    
	    /** 与js交互时用到的方法，在js里直接调用的 */
	    @JavascriptInterface
		public void shareDic(String url) {

	    	
			String[] split = url.split(",");
				
				Log.e("shareDisssc", "shuju:"+split[0]);
				
	    	
		}
	    
	    
	}

}
