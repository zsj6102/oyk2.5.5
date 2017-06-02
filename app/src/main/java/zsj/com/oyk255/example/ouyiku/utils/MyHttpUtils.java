package zsj.com.oyk255.example.ouyiku.utils;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MyHttpUtils {
	/**
	 * @author Administrator
	 *
	 */
	static Context context;
	public interface JsonCallBack{
		public void callback(String jsonStr);
	}
	/**
	 * post提交数据返回结果
	 * @param url
	 * @param params
	 * @param callBack
	 */
	public static void parseShareJsonFromNet(String url,RequestParams params, final JsonCallBack callBack){
		HttpUtils httpUtils = new HttpUtils(5000);
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
				Log.e("onFailure", "解析错误");
				Log.e("data1", arg0.toString());
				Log.e("data2", arg1);
				ToastUtils.toast(context, "上传失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				callBack.callback(arg0.result);
				Log.e("onSuccess", "解析成功");
				Log.e("onSuccess11", arg0.result);
			}
		});
	}
	
}
