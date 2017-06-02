package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.view.HorizontalProgressBarWithNumber;

public class WebviewActivity extends OykActivity {

	private TextView mTitle;
	private WebView mWebView;
	private String title;
	private String url;
	private HorizontalProgressBarWithNumber mBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		PushAgent.getInstance(this).onAppStart();
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		url = intent.getStringExtra("url");
		initUI();
	}

	private void initUI() {
		mTitle = (TextView) findViewById(R.id.webview_title);
		findViewById(R.id.back_webview).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		mBar = (HorizontalProgressBarWithNumber) findViewById(R.id.webviewbar);
		mWebView = (WebView) findViewById(R.id.webView_view);
		mTitle.setText(title);
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		mWebView.loadUrl(url);
		
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

}
