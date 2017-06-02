
package zsj.com.oyk255.example.ouyiku.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtils
{
	private static Toast mToast;
	/**
	 * 显示全局单实例的自定义Toast
	 * 
	 * @param context
	 * @param toastStr
	 */
	public static void toast(Context context,String toastStr)
	{
		if (mToast == null) {
			mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(toastStr == null ? "" : toastStr);
		mToast.show();
	}
}
