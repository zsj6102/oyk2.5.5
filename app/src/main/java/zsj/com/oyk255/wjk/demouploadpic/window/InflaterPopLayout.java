package zsj.com.oyk255.wjk.demouploadpic.window;



import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;

/**
 * @ClassName: InflaterPopLayout
 * @Description: UGP弹窗初始化界面属性
 * @author: 王俊凯
 * @date: 20160428 11:00
 */

public class InflaterPopLayout {
	/**
	 * @param popupWindow
	 *            弹窗传this
	 * @param context
	 *            上下文
	 * @param resource
	 *            XML ID (e.g., R.layout.popwindow)
	 * @param percentWidth
	 *            window宽度=屏幕宽度*percentWidth(e.g.,0.5为屏幕宽度一半)
	 * @param percentHeight
	 *            window高度=屏幕宽度*percentWidth(e.g.,0.5为屏幕宽度一半)
	 */
	public View initLayout(PopupWindow popupWindow, final Activity context, int resource, double percentWidth, double percentHeight) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflater.inflate(resource, null);
		int w = ScreenUtils.getScreenWidth(context);
		int h = ScreenUtils.getScreenHeight(context);
		popupWindow.setContentView(contentView);
		popupWindow.setWidth((int) (w * percentWidth));
		popupWindow.setHeight((int) (w * percentHeight));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		popupWindow.setBackgroundDrawable(dw);
		return contentView;
	}
}
