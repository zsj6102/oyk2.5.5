package zsj.com.oyk255.example.ouyiku.fragment;

//import zsj.com.oyk255.example.ouyiku.homejson.BannerDatum;
//import zsj.com.oyk255.example.ouyiku.v1.R;
//import zsj.com.oyk255.example.ouyiku.v1.R.drawable;
//import zsj.com.oyk255.example.ouyiku.v1.R.id;
//import zsj.com.oyk255.example.ouyiku.v1.R.layout;
//import com.suiyuchen.UILUtils;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.homejson.BannerDatum;
import zsj.com.oyk255.suiyuchen.UILUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 */

@SuppressLint("ValidFragment")
public class HomeBannerFragment extends Fragment {
	
	int position;
	BannerDatum bannerDatum;
	int mImgRes;
	public HomeBannerFragment() {
		// Required empty public constructor
	}
	public HomeBannerFragment(int position,BannerDatum bannerDatum) {
		this.position = position;
		this.bannerDatum=bannerDatum;
	}
	public HomeBannerFragment(int position,int mImgRes) {
		this.position = position;
		this.mImgRes=mImgRes;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_banner, container, false);
		ImageView mImg = (ImageView) view.findViewById(R.id.homelunbotu);
		if(bannerDatum==null){
			mImg.setImageResource(R.mipmap.logo);
		}else{
			if(bannerDatum.getPicUrl()!=null){
				UILUtils.displayImageNoAnim(bannerDatum.getPicUrl(), mImg);
			}else{
				mImg.setImageResource(R.mipmap.logo);
			}
		}
		
		return view;
	}

}
