package zsj.com.oyk255.example.ouyiku.v1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.fragment.FashionmanFragment;
import zsj.com.oyk255.example.ouyiku.fragment.GroupFragment;
import zsj.com.oyk255.example.ouyiku.fragment.HomeFragment;
import zsj.com.oyk255.example.ouyiku.fragment.PersoncenterFragment;
import zsj.com.oyk255.example.ouyiku.fragment.ShoppingcarFragment;
import zsj.com.oyk255.example.ouyiku.homejson.Status;
import zsj.com.oyk255.example.ouyiku.homejson.UpdataVer;
import zsj.com.oyk255.example.ouyiku.homejson.UpdataVerData;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  {
	
	private FragmentTabHost mTabHost;
	String m_appNameStr="ouyiku.apk";
	private Handler m_mainHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		mPushAgent.onAppStart();
		
		
//		initUpdata();
		
		
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("首页").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item, null)),
                HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("分类").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item2, null)),
                GroupFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("红人库").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item3, null)),
                FashionmanFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("购物车").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item4, null)),
                ShoppingcarFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("个人中心").setIndicator(getLayoutInflater().inflate(R.layout.tabs_item5, null)),
        		PersoncenterFragment.class, null);
        
	}
	
//	 private void HideStatusBar()  
//	 {  
//	     //隐藏标题  
//	     requestWindowFeature(Window.FEATURE_NO_TITLE);  
//	       
//	     //定义全屏参数  
//	     int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;  
//	     //获得窗口对象  
//	     Window myWindow = this.getWindow();  
//	     //设置Flag标识  
//	     myWindow.setFlags(flag, flag);  
//	 }  
	
	
//	private ProgressDialog m_progressDlg;
	private void initUpdata() {
		final int verCode = getVerCode();
		m_mainHandler = new Handler();
		
		String url= Constant.URL.UpDataUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("vid", ""+verCode);
		Log.e("verCode", ""+verCode);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				UpdataVer fromJson = gson.fromJson(arg0, UpdataVer.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					UpdataVerData data = fromJson.getData();
					if(data!=null){
						final String apkurl = data.getApkurl();
						String vername = data.getVername();
						Integer valueOf = Integer.valueOf(vername);
						

						if(valueOf>verCode){
							
							
							String str= "发现新版本，是否立即更新？";  
						    Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("软件更新").setMessage(str)  
						            // 设置内容  
						            .setPositiveButton("更新",// 设置确定按钮  
						                    new DialogInterface.OnClickListener() {  
						                        @Override  
						                        public void onClick(DialogInterface dialog,  
						                                int which) { 
						                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkurl));
						                            startActivity(intent);
						                        }

												
						                    })  
						            .setNegativeButton("暂不更新",  
						                    new DialogInterface.OnClickListener() {  
						                        public void onClick(DialogInterface dialog,  
						                                int whichButton) {  
						                            // 点击"取消"按钮之后退出程序  
						                        	dialog.dismiss();
						                        }  
						                    }).create();// 创建  
						    // 显示对话框  
						    dialog.show();  
							
							
						}
						
						
						
					}
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		
		
	}
	
	private void downFile(final String apkurl) {
		new Thread() {  
	        public void run() {  
	            HttpClient client = new DefaultHttpClient();  
	            HttpGet get = new HttpGet(apkurl);  
	            HttpResponse response;  
	            try {  
	                response = client.execute(get);  
	                HttpEntity entity = response.getEntity();  
	                long length = entity.getContentLength();  
	                
//	                m_progressDlg.setMax((int)length);//设置进度条的最大值
	                
	                InputStream is = entity.getContent();  
	                FileOutputStream fileOutputStream = null;  
	                if (is != null) {  
	                    File file = new File(  
	                            Environment.getExternalStorageDirectory(),  
	                            m_appNameStr);  
	                    fileOutputStream = new FileOutputStream(file);  
	                    byte[] buf = new byte[1024];  
	                    int ch = -1;  
	                    int count = 0;  
	                    while ((ch = is.read(buf)) != -1) {  
	                        fileOutputStream.write(buf, 0, ch);  
	                        count += ch;  
	                        if (length > 0) {  
//	                        	 m_progressDlg.setProgress(count);
	                        }  
	                    }  
	                }  
	                fileOutputStream.flush();  
	                if (fileOutputStream != null) {  
	                    fileOutputStream.close();  
	                }  
	                down();  //告诉HANDER已经下载完成了，可以安装了
	            } catch (ClientProtocolException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }.start();  
		
	}  
	
	/**
	 * 告诉HANDER已经下载完成了，可以安装了
	 */
	private void down() {
        m_mainHandler.post(new Runnable() {
            public void run() {
//                m_progressDlg.cancel();
                update();
            }
        });
}
	/**
	 * 安装程序
	 */
    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


	private int getVerCode() {
		int verCode=-1;
		try {
			verCode = getPackageManager().getPackageInfo("zsj.com.oyk255.example.ouyiku.v1", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
		
	}
	boolean isBackPressed;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(isBackPressed){
//				finish();
				System.exit(0);
				return true;
			}
			Toast.makeText(this, "再按一次back键退出", Toast.LENGTH_SHORT).show();
			isBackPressed=true;
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					isBackPressed=false;
				}
			}, 3000);
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
}

}
