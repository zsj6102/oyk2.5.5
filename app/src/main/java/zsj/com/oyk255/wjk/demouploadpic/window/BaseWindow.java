package zsj.com.oyk255.wjk.demouploadpic.window;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @ClassName: BaseWindow
 * @Description: UGP弹窗半透明设置
 * @author: 王俊凯
 * @date: 20160427 18:00
 */
public class BaseWindow extends PopupWindow {
	public BaseWindow(Activity context) {
		super(context);
		initTransparency(context);
	}

	/** 设置调用界面的背景颜色 半透明 */
	public void initTransparency(final Activity context) {
		/** 显示透明度 */
		android.view.WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		context.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		lp.alpha = 0.7f;
		context.getWindow().setAttributes(lp);
		/** 消失监听不透明 */
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				android.view.WindowManager.LayoutParams lp = context.getWindow().getAttributes();
				lp.alpha = 1f;
				context.getWindow().setAttributes(lp);
			}
		});
	}
	
	public void showPopupWindowCenter(View parentView) {
		if (!this.isShowing()) {
			this.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		} else {
			this.dismiss();
		}
	}
}
