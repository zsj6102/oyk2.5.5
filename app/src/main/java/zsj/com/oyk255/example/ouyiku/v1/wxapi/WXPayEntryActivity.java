package zsj.com.oyk255.example.ouyiku.v1.wxapi;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.v1.PaySuccessActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private static final String APP_ID="wx246edc55db723ecb";
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechatpay);
        PushAgent.getInstance(this).onAppStart();
    	api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
        
//        progressHUD = ZProgressHUD.getInstance(this); 
//    	progressHUD.setMessage("加载中");
//    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
//    	progressHUD.show();
//    	
//    	handler.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//				progressHUD.dismiss();
//				finish();
//
//			}
//		}, 3500);
    	
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}
	
	Handler handler=new Handler();
	private ZProgressHUD progressHUD;

	@Override
	public void onResp(BaseResp resp) {
//		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		SharedPreferences sp = getSharedPreferences("WeChatPay", 0);
		String oids = sp.getString("WCoids", "");
		String total = sp.getString("WCtotal", "");
			
		switch (resp.errCode) {
		case 0:
			if(oids.equals("充值")){
				finish();
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
			}else{
				
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(WXPayEntryActivity.this, PaySuccessActivity.class);
				intent.putExtra("money", total);
				intent.putExtra("oids", oids);
				startActivity(intent);
				finish();
			}
			
			break;
		case -1:
			Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case -2:
			Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
			finish();
			break;

		default:
			break;
		}
		
		
		
		
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}
}