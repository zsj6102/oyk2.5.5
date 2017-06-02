package zsj.com.oyk255.example.ouyiku.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.v1.Order_detail_daipingActivity;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Order_daiPingFragment extends Fragment {
	private View view;
	private ListView mListview;
	public Order_daiPingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_order_dai_ping, container,
					false);
			initUI();
		}
		return view;
	}

	private void initUI() {
		mListview = (ListView) view.findViewById(R.id.order_daiping_listview);
		mListview.setAdapter(new DaipingrderAdapter());	
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getActivity(), Order_detail_daipingActivity.class));
			}
		});
		
	}
	class DaipingrderAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
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
			View layout = getActivity().getLayoutInflater().inflate(R.layout.order_daiping_item, null);
			return layout;
		}
		
	}

}
