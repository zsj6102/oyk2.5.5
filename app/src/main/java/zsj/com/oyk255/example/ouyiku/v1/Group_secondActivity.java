package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.groupjson.SecondData;
import zsj.com.oyk255.example.ouyiku.groupjson.SecondData2;
import zsj.com.oyk255.example.ouyiku.groupjson.SecondGroup;
import zsj.com.oyk255.example.ouyiku.groupjson.SecondGroupImg;
import zsj.com.oyk255.example.ouyiku.groupjson.Status;
import zsj.com.oyk255.example.ouyiku.groupjson.TopDatum;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class Group_secondActivity extends OykActivity {

	private ListView mListView;
	HashMap<String, String> map=new HashMap<String, String>();
	private String mName;//标题
	private String pcategoryId;//id
	private TextView mTitle;
	ArrayList<SecondData> mdata=new ArrayList<SecondData>();
	HashMap<Integer, List<SecondData2>> hashmap=new HashMap<Integer, List<SecondData2>>();
	private ImageView topImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_second);
		PushAgent.getInstance(this).onAppStart();
		Intent intent = getIntent();
		mName = intent.getStringExtra("mName");
		pcategoryId = intent.getStringExtra("pcategoryId");
		initUI();
		initData();
	}

	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
		progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		//二级分类listview列表数据
		String url= Constant.URL.GroupSecondURL;
		map.put("pcategory_id", pcategoryId);
		HTTPUtils.post(this, url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Log.e("initData", arg0);
				progressHUD.dismiss();
				Gson gson = new Gson();
				SecondGroup fromJson = gson.fromJson(arg0, SecondGroup.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<SecondData> data = fromJson.getData();
					mdata.addAll(data);
					mListView.setAdapter(new SecGroupListAdapter());
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
		//二级分类头布局图片
		String TopImgUrl=Constant.URL.GroupSecondTopURL;
		HashMap<String, String> imgmap=new HashMap<String, String>();
		imgmap.put("cat_id", pcategoryId);
		HTTPUtils.post(this, TopImgUrl, imgmap, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				SecondGroupImg fromJson = gson.fromJson(arg0, SecondGroupImg.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<TopDatum> data = fromJson.getData();
					TopDatum topDatum = data.get(0);
					String picUrl = topDatum.getPicUrl();
					UILUtils.displayImageNoAnim(picUrl, topImg);
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		mTitle = (TextView) findViewById(R.id.group_second_title);
		mTitle.setText(mName);
		View topview = getLayoutInflater().inflate(R.layout.group_second_topview, null);
		topImg = (ImageView) topview.findViewById(R.id.group_second_topview);
		
		mListView = (ListView) findViewById(R.id.group_second_listview);
		mListView.addHeaderView(topview);
		
		findViewById(R.id.group_second_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	class SecGroupListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mdata.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder2 holder2=null;
			if(convertView==null){
				layout=getLayoutInflater().inflate(R.layout.group_second_listview_item, null);
				holder2=new ViewHolder2();
				holder2.Gridview = (MyGridView) layout.findViewById(R.id.group_second_listviewitem_gridview);
				holder2.mTv = (TextView) layout.findViewById(R.id.group_second_listviewitem_group);
				layout.setTag(holder2);
			}else{
				layout=convertView;
				holder2 = (ViewHolder2) layout.getTag();
			}
			holder2.mTv.setText(mdata.get(position).getM_name());
			
			List<SecondData2> sscat = mdata.get(position).getSscat();
			hashmap.put(position, sscat);
			holder2.Gridview.setAdapter(new SecGroupGridAdapter(position));
			holder2.Gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position2, long id) {
					String pcategory_id = hashmap.get(position).get(position2).getPcategory_id();
					
					Intent intent = new Intent(Group_secondActivity.this, SearchResultActivity.class);
					intent.putExtra("pcategory_id", pcategory_id);
					startActivity(intent);
				}
			});
			
			
			return layout;
		}
		
	}
	
	class ViewHolder2{
		MyGridView Gridview;
		TextView mTv;
	}
	
	class SecGroupGridAdapter extends BaseAdapter{
		int position;
		public SecGroupGridAdapter(int position){
			this.position=position;
		}
		@Override
		public int getCount() {
			return hashmap.get(position).size();
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
		public View getView(int pos, View convertView, ViewGroup parent) {
			View layout = null;
			ViewHolder holder=null;
			if(convertView==null){
				layout= getLayoutInflater().inflate(R.layout.group_second_gridview_item, null);
				holder=new ViewHolder();
				holder.mIMG = (ImageView) layout.findViewById(R.id.groupsecond_gridviewitem_img);
				holder.mTV = (TextView) layout.findViewById(R.id.groupsecond_gridviewitem_name);
				layout.setTag(holder);
			}else{
				layout  = convertView;
				//通过tag把layout对应的viewholder找到
				holder = (ViewHolder) layout.getTag();
			}
			SecondData2 secondData2 = hashmap.get(position).get(pos);
			String b_url = secondData2.getB_url();
			String m_name = secondData2.getM_name();
			String pcategory_id = secondData2.getPcategory_id();//id
			
			UILUtils.displayImageNoAnim(b_url, holder.mIMG);
			holder.mTV.setText(m_name);
			
			
			return layout;
		}
		
	}
	class ViewHolder{
		ImageView mIMG;
		TextView mTV;
	}
}
