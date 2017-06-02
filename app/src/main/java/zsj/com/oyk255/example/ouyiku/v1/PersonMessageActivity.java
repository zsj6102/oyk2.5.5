package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import com.umeng.message.PushAgent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.personcenterjson.PersonMessage;
import zsj.com.oyk255.example.ouyiku.personcenterjson.PersonMessageDatum;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.pullableview.MyListener;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PersonMessageActivity extends OykActivity implements OnClickListener{

	private ListView mListView;
	ArrayList<PersonMessageDatum> mData=new ArrayList<PersonMessageDatum>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_message);
		PushAgent.getInstance(this).onAppStart();
		((PullToRefreshLayout) findViewById(R.id.refresh_view1))
		.setOnRefreshListener(new MyListener());
		initUI();
		initList();
	}

	
	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.MessageURL;
		HTTPUtils.get(this, url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				PersonMessage fromJson = gson.fromJson(arg0, PersonMessage.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<PersonMessageDatum> data = fromJson.getData();
					mData.clear();
					mData.addAll(data);
					mListView.setAdapter(new MessageAdapter());
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		
	}


	private void initUI() {
		
			findViewById(R.id.message_back).setOnClickListener(this);
			
			mListView = (ListView) findViewById(R.id.message_listView);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String newsUrl = mData.get(position).getNewsUrl();
					
					
				}
			});
		
	}
	class MessageAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mData.size();
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
			View view = null;
			ViewHolder holder;
			if(convertView==null){
				view=getLayoutInflater().inflate(R.layout.message_listview_items, null);
				holder=new ViewHolder();
				holder.title = (TextView) view.findViewById(R.id.message_title);
				holder.time = (TextView) view.findViewById(R.id.message_time);
				holder.content = (TextView) view.findViewById(R.id.message_content);
				view.setTag(holder);
				
			}else{
				view=convertView;
				holder= (ViewHolder) view.getTag();
			}
			PersonMessageDatum personMessageDatum = mData.get(position);
			String time = personMessageDatum.getTime();
			String title = personMessageDatum.getTitle();
			String detail = personMessageDatum.getDetail();
			String newsUrl = personMessageDatum.getNewsUrl();
			
			holder.title.setText(title);
			holder.time.setText(time);
			holder.content.setText(detail);
			
			
			return view;
		}
		
	}
	class ViewHolder{
		TextView title;
		TextView time;
		TextView content;
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
