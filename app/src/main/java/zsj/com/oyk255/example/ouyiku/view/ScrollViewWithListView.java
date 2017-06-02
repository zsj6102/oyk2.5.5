package zsj.com.oyk255.example.ouyiku.view;

import android.widget.ListView;

public class ScrollViewWithListView extends ListView{
	//自定义listview，scrollview嵌套listview用
		//使用该方法需要注意：子ListView的每个Item必须是LinearLayout，
		//不能是其他的，因为其他的Layout(如RelativeLayout)没有重写onMeasure()，
		//所以会在onMeasure()时抛出异常。

	public ScrollViewWithListView(android.content.Context context,
			android.util.AttributeSet attrs) {
			super(context, attrs);
			}

	/**
	* Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	*/
			public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
			MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);

			}

}
