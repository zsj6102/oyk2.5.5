package zsj.com.oyk255.example.ouyiku.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {
	/** 查看屏幕的宽 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int mScreenWidth = localDisplayMetrics.widthPixels;
		return mScreenWidth;
	}

	/** 查看屏幕的高 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		int mScreenHeight = localDisplayMetrics.heightPixels;
		return mScreenHeight;
	}
}
