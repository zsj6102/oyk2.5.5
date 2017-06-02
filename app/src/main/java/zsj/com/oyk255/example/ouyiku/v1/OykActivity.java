package zsj.com.oyk255.example.ouyiku.v1;

import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import zsj.com.oyk255.R;

public class OykActivity extends Activity {
	/**
	 * base基类
	 * 
	 * @author csx
	 * 
	 */
	InputMethodManager im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushAgent.getInstance(this).onAppStart();
		im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (savedInstanceState == null) {
			overridePendingTransition(R.anim.push_from_right,R.anim.push_to_left);
		}
	}
	@Override
	public void finish() {
		hideSoftInput();
		super.finish();
		overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
	}
	
	//关闭输入法
		protected void hideSoftInput() {
			View view = getCurrentFocus();
			if(view != null) {
				IBinder binder = view.getWindowToken();
				if(binder != null) {
					im.hideSoftInputFromWindow(binder, 0);
				}
			}
		}
}
