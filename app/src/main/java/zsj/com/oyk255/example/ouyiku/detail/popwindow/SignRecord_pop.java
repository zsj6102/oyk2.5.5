package zsj.com.oyk255.example.ouyiku.detail.popwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import zsj.com.oyk255.R;

public class SignRecord_pop extends PopupWindow{

	public View view;
	public SignRecord_pop(Context context){
		this.view = LayoutInflater.from(context).inflate(R.layout.popwindow_signrecord, null);
		
		//取消按钮
				view.findViewById(R.id.sign_quxiao).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dismiss();
						
					}
				});
		
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
	
}
