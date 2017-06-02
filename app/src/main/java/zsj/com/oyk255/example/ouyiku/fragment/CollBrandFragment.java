package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

import com.google.gson.Gson;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.collectjson.CollectBrand;
import zsj.com.oyk255.example.ouyiku.collectjson.CollectBrandDatum;
import zsj.com.oyk255.example.ouyiku.collectjson.DelBrand;
import zsj.com.oyk255.example.ouyiku.collectjson.DelBrandData;
import zsj.com.oyk255.example.ouyiku.collectjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.Brand_detailActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CollBrandFragment extends Fragment implements OnClickListener{
	boolean isTouch;
	private View view;
	private ListView mlistview_brand;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<CollectBrandDatum> mBrandData=new ArrayList<CollectBrandDatum>();
	private BrandAdapter brandAdapter;
	private CheckBox mCheckAll;
	
	ArrayList<String> mCollIdData=new ArrayList<String>();//存放收藏id
	private String collect_ID2;
	private StringBuffer stringBuffer;
	private StringBuffer append;
	private ImageView mNull;
	public CollBrandFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_coll_brand, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			initbrandData();
		}
		return view;
	}
	
	private void DelCollect(){
		
		
		stringBuffer = new StringBuffer();
		for (int i = 0; i < mCollIdData.size(); i++) {
			String string = mCollIdData.get(i);
			append = stringBuffer.append(string).append(",");
		}
		collect_ID2 = append.toString();
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.DeleteCollectURL+"&user_id="+userid+"&token="+token+"&collect_id="+collect_ID2;
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
//				initbrandData();
				Gson gson = new Gson();
				DelBrand fromJson = gson.fromJson(arg0, DelBrand.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					DelBrandData data = fromJson.getData();
					String status2 = data.getStatus();
					if(status2.equals("1")){
						initbrandData();
						mCheckAll.setChecked(false);
					}else{
						ToastUtils.toast(getActivity(), "删除失败");
					}
					
				}
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				ToastUtils.toast(getActivity(), "服务器异常");
				progressHUD.dismiss();
			}
		});
	}
	
	
	
	private void initbrandData() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity()); 
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url=Constant.URL.CollectBrandURL+"&user_id="+userid+"&token="+token;
		
		HTTPUtils.get(getActivity(), url, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				
				Gson gson = new Gson();
				CollectBrand fromJson = gson.fromJson(arg0, CollectBrand.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<CollectBrandDatum> data = fromJson.getData();
					mBrandData.clear();
					mBrandData.addAll(data);
					if(mBrandData.size()==0){
						mNull.setVisibility(View.VISIBLE);
					}else{
						mNull.setVisibility(View.GONE);
						brandAdapter = new BrandAdapter();
						mlistview_brand.setAdapter(brandAdapter);
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
		
		view.findViewById(R.id.coll_brand_del).setOnClickListener(this);
		mlistview_brand = (ListView) view.findViewById(R.id.brand_listview);
		mNull = (ImageView) view.findViewById(R.id.collbrand_null);
		mlistview_brand.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CollectBrandDatum collectBrandDatum = mBrandData.get(position);
				String brandId = collectBrandDatum.getBrandId();
				Intent intent = new Intent(getActivity(), Brand_detailActivity.class);
				intent.putExtra("mSevenShop1", brandId);
				startActivity(intent);
				
				
			}
		});
		mCheckAll = (CheckBox) view.findViewById(R.id.checkcollbrand_all);
		
		mCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isTouch=!isTouch;
				if(isTouch){
					mCollIdData.clear();
					for(int i=0;i<mBrandData.size();i++){  
						brandAdapter.isSelected2.put(i,true);  
						CollectBrandDatum collectBrandDatum = mBrandData.get(i);
						String collectId = collectBrandDatum.getCollectId();
						mCollIdData.add(collectId);
					}  
					if(mBrandData.size()!=0){
						
						brandAdapter.notifyDataSetChanged();
					}
				}else{
					for(int i=0;i<mBrandData.size();i++){  
	                    if(brandAdapter.isSelected2.get(i)==true){  
	                    	brandAdapter.isSelected2.put(i, false);  
	                    	CollectBrandDatum collectBrandDatum = mBrandData.get(i);
							String collectId = collectBrandDatum.getCollectId();
							mCollIdData.clear();
	                    }  
	                }  
					if(mBrandData.size()!=0){
						
						brandAdapter.notifyDataSetChanged();
					}
				}
			}
		});
		
		
	}
	class BrandAdapter extends BaseAdapter{
		 public   HashMap<Integer, Boolean> isSelected2;  
//			
			public BrandAdapter() {  
		         init();  
		     }  
			private void init() {
				 isSelected2 = new HashMap<Integer, Boolean>();  
		         for (int i = 0; i < mBrandData.size(); i++) {  
		             isSelected2.put(i, false);  
		         }  
			}
		@Override
		public int getCount() {
			return mBrandData.size();
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
			ViewHoler holer=null;
			if(convertView==null){
				layout = getActivity().getLayoutInflater().inflate(R.layout.coll_brand_item, null);
				holer=new ViewHoler();
				holer.mLogo = (ImageView) layout.findViewById(R.id.coll_brand_img);
				holer.mTitle = (TextView) layout.findViewById(R.id.coll_brand_name);
				holer.mNum = (TextView) layout.findViewById(R.id.coll_brand_num);
				holer.mcb = (CheckBox) layout.findViewById(R.id.checkcollbrand);
				layout.setTag(holer);
			}else{
				layout=convertView;
				holer = (ViewHoler) layout.getTag();
			}
			
			if(mBrandData!=null){
				CollectBrandDatum collectBrandDatum = mBrandData.get(position);
				String logo = collectBrandDatum.getLogo();
				String brandId = collectBrandDatum.getBrandId();
				final String collectId = collectBrandDatum.getCollectId();
				String pcount = collectBrandDatum.getPcount();
				String title = collectBrandDatum.getTitle();
				String scount = collectBrandDatum.getScount();
				String xcount = collectBrandDatum.getXcount();
				UILUtils.displayImageNoAnim(logo, holer.mLogo);
				
				holer.mTitle.setText(title);
				holer.mNum.setText("在售商品("+scount+")");
				
				 holer.mcb.setOnClickListener(new OnClickListener() {
	        		 
	        		 public void onClick(View v) {  
	        			 
	        			 if (isSelected2.get(position)) {  
	        				 isSelected2.put(position, false);  
	        				 setIsSelected(isSelected2);  
	        				 mCollIdData.remove(collectId);
	        			 } else {  
	        				 isSelected2.put(position, true);  
	        				 setIsSelected(isSelected2); 
	        				 mCollIdData.add( collectId);
	        				 Log.e("collectId2", collectId);
	        			 }  
	        			 
	        		 }  
	        	 });  
				 
	        	 holer.mcb.setChecked(isSelected2.get(position)); 
			}
				
				
			
			return layout;
		}
		 public  HashMap<Integer, Boolean> getIsSelected() {  
		        return isSelected2;  
		    }  
		  
		    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
		    	this.isSelected2 = isSelected;  
		    }  
	}
	
	class ViewHoler{
		ImageView mLogo;
		TextView mTitle;
		TextView mNum;
		CheckBox mcb;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.coll_brand_del:
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
