package zsj.com.oyk255.example.ouyiku.wuliuline;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import zsj.com.oyk255.R;

/**
 * 自定义节点Listview
 * 
 * @author zad
 * 
 */
public class CustomNodeListView extends LinearLayout {

	private CustomNodeLineView nodeLineView;
	private ListView listView;
	private BaseAdapter adapter;
	private List<Integer> nodeRadiusDistances;
	private Context context;

	public CustomNodeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.custom_node_listview_layout,
				this);
		this.context = context;
		this.nodeLineView = (CustomNodeLineView) this
				.findViewById(R.id.nodeLineView);
		this.listView = (ListView) this.findViewById(R.id.listView);
		this.listView.setEnabled(false);
		this.nodeRadiusDistances = new ArrayList<Integer>();
	}

	public void setTopNodePaintStrokeWidth(float topNodePaintStrokeWidth) {
		if (this.nodeLineView != null) {
			this.nodeLineView
					.setTopNodePaintStrokeWidth(topNodePaintStrokeWidth);
		}
		invalidate();
	}

	public BaseAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
		this.listView.setAdapter(adapter);
		this.setListViewHeightBasedOnChildren(this.listView);
		this.setNodeCount(adapter.getCount());
		this.setTopNodePaintStrokeWidth(1.0f);
		this.setNodeRadiusDistances(nodeRadiusDistances);
		invalidate();
	}
		
	private void setNodeRadiusDistances(List<Integer> nodeRadiusDistances){
		this.nodeLineView.setNodeRadiusDistances(nodeRadiusDistances);
	}
	
	private void setNodeCount(int nodeCount){
		this.nodeLineView.setNodeCount(nodeCount);
	}
	/**
	 * 根据内嵌ListView高度调整外部ListView Item的高度
	 * 
	 * @param listView
	 */
	private void setListViewHeightBasedOnChildren(ListView listView) {
		android.widget.ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY), 0); //测量listview item的大小
			totalHeight += listItem.getMeasuredHeight(); //累加实际测量的高度
			nodeRadiusDistances.add(listItem.getMeasuredHeight() + listView.getDividerHeight());
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params); 
	}
}
