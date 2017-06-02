package zsj.com.oyk255.example.ouyiku.detail.popwindow;



import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import zsj.com.oyk255.R;

public class Detail_popwindow extends PopupWindow {
	/**
	 * 
	 * 购买详情页弹出popupWindow
	 * */
	private PopupWindow popupWindow;
	public View view;
	
	public Detail_popwindow (Context context){
//		this.context=context;
		this.view = LayoutInflater.from(context).inflate(R.layout.detail_popwindow_item, null);
		//设置外部可点击
		this.setOutsideTouchable(true);
		//监听判断获取触屏位置如果在选择框外面则销毁弹出框
		this.view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int height = view.findViewById(R.id.pop_layout).getTop();
				float y = event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}
				return true;
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
		this.setAnimationStyle(R.style.popWindow_anim_style);
		
		
		
		
		
	}
	
	
}
