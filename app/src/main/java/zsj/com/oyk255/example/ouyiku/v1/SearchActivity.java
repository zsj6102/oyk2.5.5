package zsj.com.oyk255.example.ouyiku.v1;

import java.util.HashMap;


import com.umeng.message.PushAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import zsj.com.oyk255.R;

public class SearchActivity extends OykActivity implements OnClickListener{

	private ListView mListview;
	private EditText mEt_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		PushAgent.getInstance(this).onAppStart();
		initUI();
		
	}
	private void initUI() {
		 findViewById(R.id.back_tomainview).setOnClickListener(this);;
		mEt_search = (EditText) findViewById(R.id.et_search);
		
		
		findViewById(R.id.tv_search).setOnClickListener(this);
		
		mListview = (ListView) findViewById(R.id.listView_history);
		
//		mListview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				startActivity(new Intent(SearchActivity.this, SearchResultActivity.class));
//				finish();
//			}
//		});
//		mListview.setAdapter(new HisListAdapter());
		
	}
	
	class HisListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 6;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.historylistview_item, null);
			return view;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tomainview:
			finish();
			break;
		case R.id.tv_search:
			String key = mEt_search.getText().toString().trim();
			Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
			intent.putExtra("key", key);
			
			startActivity(intent);
			
			break;

		default:
			break;
		}
	}

}
