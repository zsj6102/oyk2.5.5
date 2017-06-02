package zsj.com.oyk255.example.ouyiku.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import zsj.com.oyk255.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class News_tomorrowFragment extends Fragment {

	private View view;
	private ListView mListView;

	public News_tomorrowFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			if(view==null){
				view = inflater.inflate(R.layout.fragment_news_tomorrow, container,
						false);
				initUI();
			}
		return view;
	}

	private void initUI() {
		mListView = (ListView) view.findViewById(R.id.newstomorrow_listview);
		mListView.setAdapter(new TomorrowAdapterAdapter());
	}
	class TomorrowAdapterAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = getActivity().getLayoutInflater().inflate(R.layout.tomorrow_listview_item, null);
			return layout;
		}
		
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }   
}
