package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;

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
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.PersonData_signature_pop;
import zsj.com.oyk255.example.ouyiku.personcenterjson.InfoData;
import zsj.com.oyk255.example.ouyiku.personcenterjson.PersonInfo;
import zsj.com.oyk255.example.ouyiku.personcenterjson.Status;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.v1.CollectionActivity;
import zsj.com.oyk255.example.ouyiku.v1.CouponsActivity;
import zsj.com.oyk255.example.ouyiku.v1.FootprintActivity;
import zsj.com.oyk255.example.ouyiku.v1.GRziliaoActivity;
import zsj.com.oyk255.example.ouyiku.v1.GiftActivity;
import zsj.com.oyk255.example.ouyiku.v1.InviteActivity;
import zsj.com.oyk255.example.ouyiku.v1.LoginActivity;
import zsj.com.oyk255.example.ouyiku.v1.MyHotPeopleActivity;
import zsj.com.oyk255.example.ouyiku.v1.MyOrderActivity;
import zsj.com.oyk255.example.ouyiku.v1.MyPinTuanActivity;
import zsj.com.oyk255.example.ouyiku.v1.My_walletActivity;
import zsj.com.oyk255.example.ouyiku.v1.PersonMessageActivity;
import zsj.com.oyk255.example.ouyiku.v1.RegisterActivity;
import zsj.com.oyk255.example.ouyiku.v1.Safetivity;
import zsj.com.oyk255.example.ouyiku.v1.Sale_afterActivity;
import zsj.com.oyk255.example.ouyiku.v1.ShezhiActivity;
import zsj.com.oyk255.example.ouyiku.v1.WuLiuListActivity;
import zsj.com.oyk255.example.ouyiku.view.CircleImageView;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class PersoncenterFragment extends Fragment implements OnClickListener{

	private View view;
	private TextView mTv_shezhi;
	private PersonData_signature_pop personData_signature_pop;
	private SharedPreferences sp;
	private View mView_Yizhuce;
	private View mView_WeiLogin;
	private CircleImageView mPhoto;
	private TextView mName;
	private TextView mPer_sig;
	private ImageView mLevel;
	private ImageView mSex;
	
	private String nickname;
	private String photo;
	private String mobile;
	private String sex;
	private String perSig;
	int[] ImageID=new int[]{R.mipmap.l1,R.mipmap.l2,R.mipmap.l3,R.mipmap.l4,R.mipmap.l5,R.mipmap.l6,R.mipmap.l7,R.mipmap.l8,R.mipmap.l9,};
	private String userid;//用户id
	private String token;//用户token
	ArrayList<String> mdate=new ArrayList<String>();
	
	public PersoncenterFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_personcenter, container,
					false);
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
		if(!userid.equals("") && !token.equals("")){
			initData(userid,token);
		}else{
			mView_Yizhuce.setVisibility(View.GONE);
			mView_WeiLogin.setVisibility(View.VISIBLE);
			
		}
		super.onStart();
	}
	//个人中心信息
	private void initData(String userid ,String token) {
		final ZProgressHUD progressHUD = ZProgressHUD.getInstance(getActivity());
    	progressHUD.setMessage("加载中");
    	progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER );
    	progressHUD.show();
		String PersonInfoUrl= Constant.URL.PersonInfoURL+"&user_id="+userid+"&token="+token;
		HTTPUtils.get(getActivity(), PersonInfoUrl, new VolleyListener() {
			
			@Override
			public void onResponse(String arg0) {
				progressHUD.dismiss();
				Gson gson = new Gson();
				PersonInfo fromJson = gson.fromJson(arg0, PersonInfo.class);
				Status status = fromJson.getStatus();
				if(status.getSucceed().equals("1")){
					Log.e("initData", arg0);
					InfoData data = fromJson.getData();
					mView_Yizhuce.setVisibility(View.VISIBLE);
					mView_WeiLogin.setVisibility(View.GONE);
					nickname = data.getNickname();
					photo = data.getPhoto();
					mobile = data.getMobile();
					sex = data.getSex();
					perSig = data.getPerSig();
					String level = data.getLevel();
					int level_int = Integer.valueOf(level);
					int imgid=level_int-1;
					mLevel.setImageResource(ImageID[imgid]);
					if(sex.equals("女")){
						mSex.setImageResource(R.mipmap.girl1);
					}else{
						mSex.setImageResource(R.mipmap.boy1);
					}
					mName.setText(nickname);
					mPer_sig.setText(perSig);
					UILUtils.displayImage(photo, mPhoto);
					
				}else{
					ToastUtils.toast(getActivity(), "服务器异常");
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				progressHUD.dismiss();
			}
		});
	}

	private void initUI() {
		personData_signature_pop = new PersonData_signature_pop(getActivity());
		mView_Yizhuce = view.findViewById(R.id.yizhuce);
		mView_WeiLogin = view.findViewById(R.id.weidenglu);
		mView_Yizhuce.setOnClickListener(this);
		mPhoto = (CircleImageView) view.findViewById(R.id.person_img);
		mName = (TextView) view.findViewById(R.id.person_name);
		
		mPer_sig = (TextView) view.findViewById(R.id.person_jieshao);
		mLevel = (ImageView) view.findViewById(R.id.person_lv);
		mSex = (ImageView) view.findViewById(R.id.person_sex);
		mTv_shezhi = (TextView) view.findViewById(R.id.person_shezhi);
		
		mTv_shezhi.setOnClickListener(this);
		
		
		view.findViewById(R.id.dingdan).setOnClickListener(this);
		view.findViewById(R.id.shoucang).setOnClickListener(this);
		view.findViewById(R.id.qianbao).setOnClickListener(this);
		view.findViewById(R.id.yaoqing).setOnClickListener(this);
		
		view.findViewById(R.id.person_login).setOnClickListener(this);
		view.findViewById(R.id.person_regis).setOnClickListener(this);
		
		view.findViewById(R.id.c_youhui).setOnClickListener(this);
		view.findViewById(R.id.c_liwu).setOnClickListener(this);
		view.findViewById(R.id.c_pintuan).setOnClickListener(this);
//		view.findViewById(R.id.shiyongpin).setOnClickListener(this);
		view.findViewById(R.id.c_hongren).setOnClickListener(this);
		view.findViewById(R.id.c_zuji).setOnClickListener(this);
		view.findViewById(R.id.c_wuliu).setOnClickListener(this);
		view.findViewById(R.id.shouhou).setOnClickListener(this);
		view.findViewById(R.id.pingjia).setOnClickListener(this);
		view.findViewById(R.id.tongzhi).setOnClickListener(this);
//		view.findViewById(R.id.pintuan).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_shezhi://设置
			startActivity(new Intent(getActivity(), ShezhiActivity.class));
			break;
		case R.id.person_regis://注册
			startActivity(new Intent(getActivity(), RegisterActivity.class));
			break;
		case R.id.shoucang://收藏
			
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), CollectionActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.dingdan://订单
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), MyOrderActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.yaoqing://邀请
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), InviteActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.qianbao://钱包
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), My_walletActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
		case R.id.person_login://登录
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivityForResult(intent, 0);
			
			break;
		case R.id.c_youhui://优惠
			
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), CouponsActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.c_zuji://足迹

			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), FootprintActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
		case R.id.c_wuliu://物流
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), WuLiuListActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.c_pintuan://我的拼团
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), MyPinTuanActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.c_liwu://礼物
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), GiftActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
		case R.id.c_qidai://期待
//			startActivity(new Intent(getActivity(), Brand_detailActivity.class));
			break;
		case R.id.c_hongren://我的红人
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), MyHotPeopleActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
		case R.id.shouhou://售后
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), Sale_afterActivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			
			break;
			
		case R.id.pingjia://安全
			if(!userid.equals("")){
				startActivity(new Intent(getActivity(), Safetivity.class));
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.tongzhi://通知
			
			if(!userid.equals("")){
				Intent intent3 = new Intent(getActivity(), PersonMessageActivity.class);
				startActivity(intent3);
			}else{
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
			break;
		case R.id.yizhuce://修改个人资料
			Intent intent2 = new Intent(getActivity(), GRziliaoActivity.class);
			intent2.putExtra("nickname", nickname);
			intent2.putExtra("mobile", mobile);
			intent2.putExtra("sex", sex);
			intent2.putExtra("photo", photo);
			intent2.putExtra("signature", perSig);
//			intent2.putExtra("userid", userid);
//			intent2.putExtra("token", token);
			startActivity(intent2);
			
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
		case Constant.INTENT.LOGIN_RESULT:
			String userid = data.getStringExtra("userid");
			String token = data.getStringExtra("token");
			initData(userid, token);
			break;
		case Constant.INTENT.REGIS_RESULT:
			String userid2 = data.getStringExtra("userid");
			String token2 = data.getStringExtra("token");
			initData(userid2, token2);
			break;
			
//		case Constant.INTENT.PERSON_DATA_RESULT:
//			if(data!=null){
//				
//				 String GRid = data.getStringExtra("GRuserID");
//				String GRtoken = data.getStringExtra("GRtoken");
//				initData(GRid,GRtoken);
//			}
//			break;

		default:
			break;
		}
		
	}

//	private void updata() {
//		if(nickname2!=null){
//			mName.setText(nickname2);
//		}
//		if(signature2!=null){
//			
//			mPer_sig.setText(signature2);
//		}
//		if(sex2!=null){
//			
//			if(sex2.equals("女")){
//				mSex.setImageResource(R.drawable.girl1);
//			}else{
//				mSex.setImageResource(R.drawable.boy1);
//			}
//		}
//		
//	}
}
