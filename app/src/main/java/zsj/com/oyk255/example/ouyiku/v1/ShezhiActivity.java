package zsj.com.oyk255.example.ouyiku.v1;

import java.io.File;

import com.umeng.message.PushAgent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.FileUtils;

public class ShezhiActivity extends OykActivity implements OnClickListener{

	private View myijian;
	private TextView huancun_num;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhi);
		PushAgent.getInstance(this).onAppStart();
		initUI();
	}


	private void initUI() {
		findViewById(R.id.back_denglu).setOnClickListener(this);
		myijian = findViewById(R.id.yijian);
		myijian.setOnClickListener(this);
		findViewById(R.id.changjianwenti).setOnClickListener(this);
		findViewById(R.id.back_shezhi).setOnClickListener(this);
		findViewById(R.id.dengjishuoming).setOnClickListener(this);
		findViewById(R.id.aboutwe).setOnClickListener(this);
		huancun_num = (TextView) findViewById(R.id.huancun_num);
		findViewById(R.id.huancun).setOnClickListener(this);
		File cacheDir = getCacheDir();
		String path = cacheDir.getPath();
		 file = new File(path);
		long fileLen = FileUtils.getFileLen(file);
		String size = FileUtils.size(fileLen);
		huancun_num.setText(size);
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yijian:
			startActivity(new Intent(this, OpinionActivity.class));
			break;
		case R.id.huancun:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("确定清除缓存？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FileUtils.delFilesFromPath(file);
					huancun_num.setText("0B");
				}
			});
			builder.setNegativeButton("取消", null);
			AlertDialog create = builder.create();
			create.show();
			
			break;
		case R.id.back_shezhi:
			finish();
			break;
		case R.id.changjianwenti:
			String Url1= Constant.WebUrl.ChangjianURL;
			Intent intent = new Intent(this, WebviewActivity.class);
			intent.putExtra("title", "常见问题");
			intent.putExtra("url", Url1);
			startActivity(intent);
			break;
		case R.id.dengjishuoming:
			String Url2=Constant.WebUrl.LevelURL;
			Intent intent2 = new Intent(this, WebviewActivity.class);
			intent2.putExtra("title", "等级说明");
			intent2.putExtra("url", Url2);
			startActivity(intent2);
			break;
		case R.id.aboutwe:
			String Url3=Constant.WebUrl.About_usURL;
			Intent intent3 = new Intent(this, WebviewActivity.class);
			intent3.putExtra("title", "关于我们");
			intent3.putExtra("url", Url3);
			startActivity(intent3);
			break;
		case R.id.back_denglu:
			SharedPreferences sp = getSharedPreferences("userdata", 0);
			Editor edit = sp.edit();
			edit.clear();
			edit.commit();
			
			finish();
			break;

		default:
			break;
		}
		
	}

}
