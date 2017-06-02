package zsj.com.oyk255.example.ouyiku.detail.popwindow;



import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import zsj.com.oyk255.R;

public class SignDialog extends PopupWindow {

	public View view;
	public SignDialog(Context context){
		this.view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
		//设置窗口弹出特性
				//设置视图
		this.setContentView(this.view);
		//设置弹出窗体的宽和高
		this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
		this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		//设置弹出窗体可点击
		this.setFocusable(true);
		//实例化一个colordrawable颜色为半透明（弹出窗体的主背景颜色为全透明）
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//设置弹出窗体背景和动画
		this.setBackgroundDrawable(dw);
//		this.setAnimationStyle(R.style.popWindow_anim_style);
	}
	
	
	
	
//	private TextView dialog_tv;
//	Context context;
//	public SignDialog(Context context) {
//		super(context);
//		setContentView(R.layout.dialog_layout);
//		ImageView close = (ImageView) findViewById(R.id.dialog_close);
//		close.setOnClickListener(this);
//		dialog_tv = (TextView) findViewById(R.id.dialog_tv);
//		TimeCount timeCount = new TimeCount(3000, 1000);
//		timeCount.start();
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.dialog_close:
//			cancel();
//			break;
//
//		default:
//			break;
//		}
//		
//	}
//	class TimeCount extends CountDownTimer {
//		
//		public TimeCount(long millisInFuture, long countDownInterval) {
//		super(millisInFuture, countDownInterval);
//		}
//
//		@Override
//		public void onFinish() {// 计时完毕
//			cancel();
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {// 计时过程
//			dialog_tv.setText(millisUntilFinished/1000+"秒后自动关闭");
//		}
//	}
	
	
	
}
