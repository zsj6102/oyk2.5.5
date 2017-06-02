package zsj.com.oyk255.example.ouyiku.fragment;

import java.math.BigDecimal;
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
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.footjson.IsChangeNum;
import zsj.com.oyk255.example.ouyiku.footjson.ShopCarList;
import zsj.com.oyk255.example.ouyiku.footjson.ShopCarListDatum;
import zsj.com.oyk255.example.ouyiku.footjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.ConfirmOrderActivity;
import zsj.com.oyk255.example.ouyiku.v1.HotSaleActivity;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ShoppingcarFragment extends Fragment implements OnClickListener {
	boolean isTouch;
	private View view;
	private ListView mListview;
	private SharedPreferences sp;
	private String userid;//用户id
	private String token;//用户token
	ArrayList<ShopCarListDatum> mListData=new ArrayList<ShopCarListDatum>();
	ArrayList<String> mCollIdData=new ArrayList<String>();//存放购物车id
	
	public ShoppingcarFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_shoppingcar, container, false);
			sp = getActivity().getSharedPreferences("userdata", 0);
			userid = sp.getString("userid", "");
			token = sp.getString("token", "");
			initUI();
			
		}
		return view;
	}
	@Override
	public void onStart() {
		sp = getActivity().getSharedPreferences("userdata", 0);
		userid = sp.getString("userid", "");
		token = sp.getString("token", "");
		initList();
		super.onStart();
	}
	
	private ShopcarAdapter shopcarAdapter;
	private TextView mBianji;
//	private LinearLayout mBotton_bianji;
	private LinearLayout mBotton_delete;
	private ImageView mImg_null;

	private void initList() {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String url= Constant.URL.ShopCarListUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			

			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				ShopCarList fromJson = gson.fromJson(arg0, ShopCarList.class);
				Status status = fromJson.getStatus();
				String succeed = status.getSucceed();
				if(succeed.equals("1")){
					List<ShopCarListDatum> data = fromJson.getData();
					mListData.clear();
					mListData.addAll(data);
					if(mListData.size()==0){
//						mBotton_bianji.setVisibility(View.GONE);
						mImg_null.setVisibility(View.VISIBLE);
					}else{
						mImg_null.setVisibility(View.GONE);
						shopcarAdapter = new ShopcarAdapter();
						mListview.setAdapter(shopcarAdapter);
//						String mBtn = mBianji.getText().toString();
						
						/*if(mBtn.equals("编辑")){
							mBotton_bianji.setVisibility(View.VISIBLE);
						}else{
							mBotton_bianji.setVisibility(View.GONE);
						}
						*/
					}
						
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
		
		
	}
	float allMoney=0;

	private void initUI() {
		mBianji = (TextView) view.findViewById(R.id.shopcar_redact);
		mBianji.setOnClickListener(this);
		mImg_null = (ImageView) view.findViewById(R.id.shop_null);
		mImg_null.setOnClickListener(this);
		mListview = (ListView) view.findViewById(R.id.shopcar_listview);
//		mBotton_bianji = (LinearLayout) view.findViewById(R.id.shopcar_bianjiview);
		mBotton_delete = (LinearLayout) view.findViewById(R.id.shop_deleteview);
//		mAllCheck_shop = (CheckBox) view.findViewById(R.id.shopcar_bianjiview_quanxuan);
		CheckBox mAllCheck_delete = (CheckBox) view.findViewById(R.id.shop_deleteview_quanxuan);
		deleteShop = (TextView) view.findViewById(R.id.shop_deleteview_shanchu);
		deleteShop.setOnClickListener(this);
		view.findViewById(R.id.shop_deleteview_quxiao).setOnClickListener(this);
		
//		mAllGoodsNum = (TextView) view.findViewById(R.id.shopcar_bianjiview_goodsNum);
//		mAllGoodsMoney = (TextView) view.findViewById(R.id.shopcar_bianjiview_money);
		//购买的全选
//		mAllCheck_shop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				isTouch=!isTouch;
//				if(isTouch){
//					mCollIdData.clear();
//					for(int i=0;i<mListData.size();i++){  
//						shopcarAdapter.isSelected2.put(i,true);  
//						 ShopCarListDatum shopCarListDatum = mListData.get(i);
//						String CartId = shopCarListDatum.getCartId();
//						mCollIdData.add(CartId);
//						String price = shopCarListDatum.getPrice();
//						String number = shopCarListDatum.getNumber();
//						Integer num = Integer.valueOf(number);
//						float money = Float.valueOf(price);
//						float total=(float) (num*money);
//						allMoney+=total;				
//					}  
//					goodsNum= mListData.size();
//					mAllGoodsNum.setText(goodsNum+"");
//					mAllGoodsMoney.setText(allMoney+"");
//					if(!userid.equals("")){
//						shopcarAdapter.notifyDataSetChanged();
//						
//					}
//				}else{
//					for(int i=0;i<mListData.size();i++){  
//	                    if(shopcarAdapter.isSelected2.get(i)==true){  
//	                    	shopcarAdapter.isSelected2.put(i, false);  
//	                    	ShopCarListDatum shopCarListDatum = mListData.get(i);
//							String CartId = shopCarListDatum.getCartId();
//							
//							mCollIdData.clear();
//	                    }  
//	                    mAllGoodsNum.setText(0+"");
//	                    allMoney=0;
//						mAllGoodsMoney.setText(allMoney+"");
//	                    
//	                    
//	                }  
//					if(!userid.equals("")){
//						shopcarAdapter.notifyDataSetChanged();
//						
//					}
//				}
//				
//			}
//		});
		//删除的全选
		mAllCheck_delete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isTouch=!isTouch;
				if(isTouch){
					mCollIdData.clear();
					for(int i=0;i<mListData.size();i++){  
						shopcarAdapter.isSelected2.put(i,true);  
						 ShopCarListDatum shopCarListDatum = mListData.get(i);
						String CartId = shopCarListDatum.getCartId();
						mCollIdData.add(CartId);
					}  
					if(!userid.equals("")){
						shopcarAdapter.notifyDataSetChanged();
						
					}
//					shopcarAdapter.notifyDataSetChanged();
				}else{
					for(int i=0;i<mListData.size();i++){  
	                    if(shopcarAdapter.isSelected2.get(i)==true){  
	                    	shopcarAdapter.isSelected2.put(i, false);  
	                    	ShopCarListDatum shopCarListDatum = mListData.get(i);
							String CartId = shopCarListDatum.getCartId();
							
							mCollIdData.clear();
	                    }  
	                }  
					if(!userid.equals("")){
						shopcarAdapter.notifyDataSetChanged();
						
					}
//					shopcarAdapter.notifyDataSetChanged();
				}
				
			}
		});
		
		
	}
	 int int1 ;
	private TextView deleteShop;
//	private TextView mAllGoodsNum;
//	private TextView mAllGoodsMoney;
//	private CheckBox mAllCheck_shop;
	class ShopcarAdapter extends BaseAdapter{
		public HashMap<Integer, String> ShopNum=new HashMap<Integer, String>();
		


		public   HashMap<Integer, Boolean> isSelected2;  
			
			public ShopcarAdapter() {  
		         init();  
		     }  
			private void init() {
				 isSelected2 = new HashMap<Integer, Boolean>();  
		         for (int i = 0; i < mListData.size(); i++) {  
		        	 String number = mListData.get(i).getNumber();
		             isSelected2.put(i, false);  
		             ShopNum.put(i, number);
		         }  
			}
			 public  HashMap<Integer, Boolean> getIsSelected() {  
			        return isSelected2;  
			    }  
			  
			    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
			    	this.isSelected2 = isSelected;  
			    }  
		
		@Override
		public int getCount() {
			return mListData.size();
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
			final ViewHolder holder;
			if(convertView==null){
				layout=getActivity().getLayoutInflater().inflate(R.layout.shopcar_item, null);
				holder=new ViewHolder();
				holder.mCheck = (CheckBox) layout.findViewById(R.id.shopcar_check);
				holder.mLogo = (ImageView) layout.findViewById(R.id.shopcar_img);
				holder.mTitle = (TextView) layout.findViewById(R.id.shopcar_title);
				holder.mAttr = (TextView) layout.findViewById(R.id.shopcar_attr);
				holder.mNewPrice = (TextView) layout.findViewById(R.id.shopcar_newprice);
				holder.mOldPrice = (TextView) layout.findViewById(R.id.shopcar_oldprice);
				holder.mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
				holder.mDelete = (ImageView) layout.findViewById(R.id.delete_num);
				holder.mAdd = (ImageView) layout.findViewById(R.id.add_num);
				holder.mAllMoney = (TextView) layout.findViewById(R.id.shopcar_totalmoney);
				holder.mPay = (TextView) layout.findViewById(R.id.shopcar_pay);
				holder.mNum = (TextView) layout.findViewById(R.id.num);
				layout.setTag(holder);
			}else{
				layout=convertView;
				holder = (ViewHolder) layout.getTag();
			}
			ShopCarListDatum shopCarListDatum = mListData.get(position);
			String attr = shopCarListDatum.getAttr();
			String image = shopCarListDatum.getImage();
			String marketprice = shopCarListDatum.getMarketprice();
			final String price = shopCarListDatum.getPrice();
			String number = shopCarListDatum.getNumber();
//			Integer inter = Integer.valueOf(number);
//			ShopNum.put(position, number);
			String title = shopCarListDatum.getTitle();
			final String cartId = shopCarListDatum.getCartId();
			
			UILUtils.displayImageNoAnim(image, holder.mLogo);
			holder.mTitle.setText(title);
			holder.mAttr.setText(attr);
			holder.mNewPrice.setText("￥"+price);
			holder.mOldPrice.setText("￥"+marketprice);
			holder.mNum.setText(ShopNum.get(position));
			String string = ShopNum.get(position);
			Integer num = Integer.valueOf(string);
			Float money = Float.valueOf(price);
			
			float aa=(float)num;
			
			float qq=money*aa;
			BigDecimal b1 = new BigDecimal(qq);
			float totalmoney = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			
			holder.mAllMoney.setText(totalmoney+"元");
			
			holder.mDelete.setTag("+");
			holder.mAdd.setTag("-");
			String mBtn = mBianji.getText().toString();
			if(mBtn.equals("编辑")){
				holder.mCheck.setVisibility(View.GONE);
			}else{
				holder.mCheck.setVisibility(View.VISIBLE);
				
			}
			
			holder.mAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					holder.mAdd.setClickable(false);
					String numString = holder.mNum.getText().toString();
						int1 = Integer.parseInt(numString);
						
						if (numString == null || numString.equals(""))
						{
							int1 = 0;
							holder.mNum.setText("0");
						} else
						{
							if (v.getTag().equals("-"))
							{
								if (++int1 <= 0)  //先加，再判断
								{
									int1--;
								} else
								{
									
									String url=Constant.URL.ShopCarNumUrl;
									HashMap<String, String> map=new HashMap<String, String>();
									map.put("user_id", userid);
									map.put("token", token);
									map.put("cart_id", cartId);
									map.put("type", "1");
									
									HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
										
										@Override
										public void onResponse(String arg0) {
											Gson gson = new Gson();
											IsChangeNum fromJson = gson.fromJson(arg0, IsChangeNum.class);
											 Status status = fromJson.getStatus();
											 String succeed = status.getSucceed();
											if(succeed.equals("1")){
												String string = String.valueOf(int1);
												holder.mNum.setText(string);
												Float money = Float.valueOf(price);
												float b=(float)int1;
												float qq=money*b;
												BigDecimal b1 = new BigDecimal(qq);
												float totalmoney = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
												holder.mAllMoney.setText(totalmoney+"元");
												mListData.get(position).setNumber(string);
												ShopNum.put(position, string);
												shopcarAdapter.notifyDataSetChanged();
												holder.mAdd.setClickable(true);
												
											}
											
											
										}
										
										@Override
										public void onErrorResponse(VolleyError arg0) {
											
										}
									});
									
									
								}
							}
						}
				}
			});
			
			
			holder.mDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					holder.mDelete.setClickable(false);
					String numString = holder.mNum.getText().toString();
						int1 = Integer.parseInt(numString);
						if(int1==1){
							ToastUtils.toast(getActivity(), "不能再减了");
						}else{
							
						if (numString == null || numString.equals(""))
						{
							int1 = 0;
							holder.mNum.setText("0");
						} else
						{
							if (v.getTag().equals("+"))
							{
								if (--int1 <= 0)  //先减，再判断
									{
										int1++;
									} else
									{
										
										
										String url=Constant.URL.ShopCarNumUrl;
										HashMap<String, String> map=new HashMap<String, String>();
										map.put("user_id", userid);
										map.put("token", token);
										map.put("cart_id", cartId);
										map.put("type", "0");
										
										HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
											
											@Override
											public void onResponse(String arg0) {
												Gson gson = new Gson();
												IsChangeNum fromJson = gson.fromJson(arg0, IsChangeNum.class);
												 Status status = fromJson.getStatus();
												 String succeed = status.getSucceed();
												if(succeed.equals("1")){
													String string = String.valueOf(int1);
													holder.mNum.setText(string);
													Float money = Float.valueOf(price);
													float b=(float)int1;
													float qq=money*b;
													BigDecimal b1 = new BigDecimal(qq);
													float totalmoney = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
													
													
													holder.mAllMoney.setText(totalmoney+"元");
													mListData.get(position).setNumber(string);
													ShopNum.put(position, string);
													shopcarAdapter.notifyDataSetChanged();
													holder.mDelete.setClickable(true);
												}
											}
											
											@Override
											public void onErrorResponse(VolleyError arg0) {
												// TODO Auto-generated method stub
												
											}
										});
										
									}
							}
						}
							
							
						}
						
						
						
				}
			});
			
			
			
			
			
			
			holder.mCheck.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 if (isSelected2.get(position)) {  
        				 isSelected2.put(position, false);  
        				 setIsSelected(isSelected2);  
        				 mCollIdData.remove(cartId);
//        				 holder.mPay.setBackgroundResource(R.drawable.button_bg_unbind);
//        				 holder.mPay.setClickable(false);
        				 //已经点击过全选再点局部checkbox
//        				if(mAllCheck_shop.isChecked()){
//        					String price2 = mListData.get(position).getPrice();
//        					String number2 = mListData.get(position).getNumber();
//        					Integer num = Integer.valueOf(number2);
//        					float money = Float.valueOf(price2);
//        					float total=(float) (num*money);
//        					size=goodsNum-1;
//        					mAllGoodsNum.setText(size+"");
//        					goodsNum=size;
//        					all=allMoney-total;
//        					String valueOf = String.valueOf(all);
//        					mAllGoodsMoney.setText(valueOf);
//        					allMoney=all;
//        				} 
        				
        			 } else {  
        				 isSelected2.put(position, true);  
        				 setIsSelected(isSelected2); 
        				 mCollIdData.add(cartId);
        				 //选中状态
//        				 holder.mPay.setBackgroundResource(R.drawable.button_bg);
//        				 holder.mPay.setClickable(true);
//        				 if(mAllCheck_shop.isChecked()){
//        					 String price2 = mListData.get(position).getPrice();
//         					String number2 = mListData.get(position).getNumber();
//         					Integer num = Integer.valueOf(number2);
//         					float money = Float.valueOf(price2);
//         					float total=(float) (num*money);
//         					all=allMoney+total;
//         					String valueOf = String.valueOf(all);
//         					size=goodsNum+1;
//        					 mAllGoodsNum.setText(size+"");
//        					 mAllGoodsMoney.setText(valueOf);
//        					 allMoney=all;
//        					 goodsNum=size;
//        				 }
        			 }  
        			 
				}
			});
			
			
			holder.mCheck.setChecked(isSelected2.get(position)); 
			
			holder.mPay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
					intent.putExtra("cart_id", cartId);
					startActivity(intent);
					
				}
			});
			
			
			
			
			return layout;
		}
		
	}
//	float all;
//	int goodsNum;
//	int size;
	class ViewHolder{
		ImageView mLogo;
		TextView mTitle;
		TextView mAttr;
		TextView mNewPrice;
		TextView mOldPrice;
		ImageView mDelete;
		ImageView mAdd;
		TextView mAllMoney;
		TextView mPay;
		CheckBox mCheck ;
		TextView mNum;
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcar_redact:
			
			String mBtn = mBianji.getText().toString();
			
			if(!userid.equals("")){
				
				if(mBtn.equals("编辑")){
					mBianji.setText("取消");
					mBotton_delete.setVisibility(View.VISIBLE);
					if(mListData.size()!=0){
						shopcarAdapter.notifyDataSetChanged();
						
					}
				}else{
					mBianji.setText("编辑");
					mBotton_delete.setVisibility(View.GONE);
					if(mListData.size()!=0){
						shopcarAdapter.notifyDataSetChanged();
						
					}
				}
			}else{
				
				if(mBtn.equals("编辑")){
					mBianji.setText("取消");
					mBotton_delete.setVisibility(View.VISIBLE);
				}else{
					mBianji.setText("编辑");
					mBotton_delete.setVisibility(View.GONE);
					
				}
				
			}
			break;
			
		case R.id.shop_deleteview_shanchu:
			if(mCollIdData.size()!=0){
				DelShopCar();
				
			}else{
				ToastUtils.toast(getActivity(), "请选择要删除的选项");
			}
			break;
		case R.id.shop_null:
			startActivity(new Intent(getActivity(), HotSaleActivity.class));
			break;
		case R.id.shop_deleteview_quxiao:
			String mBtn2 = mBianji.getText().toString();
			if(!userid.equals("")){
				
				if(mBtn2.equals("编辑")){
					mBianji.setText("取消");
					mBotton_delete.setVisibility(View.VISIBLE);
					shopcarAdapter.notifyDataSetChanged();
				}else{
					mBianji.setText("编辑");
					mBotton_delete.setVisibility(View.GONE);
					shopcarAdapter.notifyDataSetChanged();
					
				}
			}else{
				if(mBtn2.equals("编辑")){
					mBianji.setText("取消");
					mBotton_delete.setVisibility(View.VISIBLE);
				}else{
					mBianji.setText("编辑");
					mBotton_delete.setVisibility(View.GONE);
				}
				
			}
			
			break;
		default:
			break;
		}
		
	}

	private void DelShopCar() {
		//拼接收藏id
		stringBuffer = new StringBuffer();
		for (int i = 0; i < mCollIdData.size(); i++) {
			String string = mCollIdData.get(i);
			append = stringBuffer.append(string).append(",");
		}
		String car_ID = append.toString();
		String url=Constant.URL.DeleteShopCarUrl;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("user_id", userid);
		map.put("token", token);
		map.put("cart_id", car_ID);
		HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				IsChangeNum fromJson = gson.fromJson(arg0, IsChangeNum.class);
				 Status status = fromJson.getStatus();
				 String succeed = status.getSucceed();
				 if(succeed.equals("1")){
					 initList();
					 
				 }
				
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
				
		
	}
	
	private StringBuffer stringBuffer;
	private StringBuffer append;
	
	
	
	
	
	
	
}
