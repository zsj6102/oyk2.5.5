package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.collectjson.CollectBaby;
import zsj.com.oyk255.example.ouyiku.collectjson.CollectBabyDatum;
import zsj.com.oyk255.example.ouyiku.collectjson.DelBaby;
import zsj.com.oyk255.example.ouyiku.collectjson.DelBabyData;
import zsj.com.oyk255.example.ouyiku.collectjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.DetailActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BabyFragment extends Fragment implements OnClickListener{
	boolean isTouch;
	private View view;
	private ListView mlistview_baby;
	private String userid;//用户id
	private String token;//用户token
	private SharedPreferences sp;
	ArrayList<CollectBabyDatum> mBabyData=new ArrayList<CollectBabyDatum>();
	private BabyAdapter babyAdapter;
	ArrayList<String> mCollIdData=new ArrayList<String>();
	private String collect_ID;
	private StringBuffer stringBuffer;
	private StringBuffer append;
	private CheckBox allCheck;
	private ImageView mNull;
	public BabyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_baby, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initData();
			
		}
		return view;
	}
//删除收藏
	private void DelCollect(){
		
		//拼接收藏id
		stringBuffer = new StringBuffer();
		for (int i = 0; i < mCollIdData.size(); i++) {
			String string = mCollIdData.get(i);
			append = stringBuffer.append(string).append(",");
		}
		collect_ID = append.toString();
		
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.DeleteCollectURL+"&user_id="+userid+"&token="+token+"&collect_id="+collect_ID;
		
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				DelBaby fromJson = gson.fromJson(arg0, DelBaby.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					 DelBabyData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						initData();
						allCheck.setChecked(false);
					}else{
						ToastUtils.toast(getActivity(), "删除失败");
					}
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
				
			}
		});
	}
	
	private void initData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.CollectBabyURL+"&user_id="+userid+"&token="+token;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				CollectBaby fromJson = gson.fromJson(arg0, CollectBaby.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					mBabyData.clear();
					List<CollectBabyDatum> data = fromJson.getData();
					mBabyData.addAll(data);
					if(mBabyData.size()==0){
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						babyAdapter = new BabyAdapter();
						mlistview_baby.setAdapter(babyAdapter);
					}
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		
		getActivity().findViewById(R.id.coll_back).setOnClickListener(this);
		view.findViewById(R.id.coll_baby_del).setOnClickListener(this);
		mNull = (ImageView) view.findViewById(R.id.collbaby_null);
//		view.findViewById(R.id.coll_baby_intocar).setOnClickListener(this);
		allCheck = (CheckBox) view.findViewById(R.id.checkcollbaby_all);
		
		allCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isTouch=!isTouch;
				if(isTouch){
					mCollIdData.clear();
					for(int i=0;i<mBabyData.size();i++){  
						babyAdapter.isSelected.put(i,true);  
						CollectBabyDatum collectBabyDatum = mBabyData.get(i);
						String collectId = collectBabyDatum.getCollectId();
						mCollIdData.add(collectId);
						
					}  
					if(mBabyData.size()!=0){
						
					babyAdapter.notifyDataSetChanged();
					}
				}else{
					for(int i=0;i<mBabyData.size();i++){  
	                    if(babyAdapter.isSelected.get(i)==true){  
	                    	babyAdapter.isSelected.put(i, false);  
	                    	CollectBabyDatum collectBabyDatum = mBabyData.get(i);
							String collectId = collectBabyDatum.getCollectId();
							mCollIdData.clear();
	                    }  
	                }  
					if(mBabyData.size()!=0){
					
						babyAdapter.notifyDataSetChanged();
				}
				}
				
			}
		});
		mlistview_baby = (ListView) view.findViewById(R.id.baby_listview);
		mlistview_baby.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CollectBabyDatum collectBabyDatum = mBabyData.get(position);
				String productId = collectBabyDatum.getProductId();
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("product_id", productId);
				Log.e("productId", productId);
				startActivity(intent);
				
			}
		});
	}
	class BabyAdapter extends BaseAdapter{
		
		 public   HashMap<Integer, Boolean> isSelected;  
		public BabyAdapter() {  
	         init();  
	     }  
		private void init() {
			 isSelected = new HashMap<Integer, Boolean>();  
	         for (int i = 0; i < mBabyData.size(); i++) {  
	             isSelected.put(i, false);  
	         }  
		}
		@Override
		public int getCount() {
			return mBabyData.size();
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
				View layout=null;
				ViewHoler holer=null;
				if(convertView==null){
					layout=getActivity().getLayoutInflater().inflate(R.layout.coll_baby_item, null);
					holer=new ViewHoler();
					holer.mIschickBox = (CheckBox) layout.findViewById(R.id.checkcollbaby);
					holer.mLogo = (ImageView) layout.findViewById(R.id.babylogo);
					holer.mTitle = (TextView) layout.findViewById(R.id.babytitle);
					holer.mNew_price = (TextView) layout.findViewById(R.id.textView4);
					holer.mOld_price = (TextView) layout.findViewById(R.id.textView5);
					holer.mOld_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
					holer.mRebate = (TextView) layout.findViewById(R.id.textView3);
					layout.setTag(holer);
				}else{
					layout=convertView;
					holer= (ViewHoler) layout.getTag();
				}
		         
		         CollectBabyDatum collectBabyDatum = mBabyData.get(position);
		         if(collectBabyDatum!=null){
		        	 final String collectId = collectBabyDatum.getCollectId();
		        	 String isEffe = collectBabyDatum.getIsEffe();
		        	 String newPrice = collectBabyDatum.getNewPrice();
		        	 String oldPrice = collectBabyDatum.getOldPrice();
		        	 String picUrl = collectBabyDatum.getPicUrl();
		        	 String productId = collectBabyDatum.getProductId();
		        	 String rebate = collectBabyDatum.getRebate();
		        	 String title = collectBabyDatum.getTitle();
		        	 holer.mTitle.setText(title);
		        	 holer.mNew_price.setText("￥"+newPrice);
		        	 holer.mOld_price.setText("￥"+oldPrice);
		        	 holer.mRebate.setText(rebate+"折");
		        	 UILUtils.displayImageNoAnim(picUrl, holer.mLogo);
		        	 
		        	 holer.mIschickBox.setOnClickListener(new OnClickListener() {
		        		 
		        		 public void onClick(View v) {  
		        			 
		        			 if (isSelected.get(position)) {  
		        				 isSelected.put(position, false);  
		        				 setIsSelected(isSelected);  
		        				 mCollIdData.remove(collectId);
		        			 } else {  
		        				 isSelected.put(position, true);  
		        				 setIsSelected(isSelected);  
		        				 Log.e("collectId", collectId);
		        				 mCollIdData.add( collectId);
		        				 
		        			 }  
		        			 
		        		 }  
		        	 });  
		        	 holer.mIschickBox.setChecked(isSelected.get(position));  
		         }
				
				
			return layout;
		}
		 public  HashMap<Integer, Boolean> getIsSelected() {  
		        return isSelected;  
		    }  
		  
		    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
		    	this.isSelected = isSelected;  
		    }  
		
	}
	
	class ViewHoler{
		ImageView mLogo;
		TextView mTitle;
		TextView mNew_price;
		TextView mOld_price;
		TextView mRebate;
		CheckBox mIschickBox;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coll_back:
			getActivity().finish();
			break;
		case R.id.coll_baby_del:
			if(mCollIdData.size()!=0){
				DelCollect();
				
			}else{
				ToastUtils.toast(getActivity(), "请选择要删除的选项");
				
			}
			break;
		default:
			break;
		}
		
	}
	
	
	
	
	
}
