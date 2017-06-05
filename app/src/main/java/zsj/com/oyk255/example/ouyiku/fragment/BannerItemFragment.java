package zsj.com.oyk255.example.ouyiku.fragment;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.groupjson.Datum;
import zsj.com.oyk255.example.ouyiku.homejson.BannerDatum;
import zsj.com.oyk255.suiyuchen.UILUtils;

/**
 * 轮播图
 *
 */
@SuppressLint("ValidFragment")
public class BannerItemFragment extends Fragment {
	protected int mPosition;
	int mImgRes;
	BannerDatum bannerDatum;
	Datum datum;
	private ImageView img;

	public BannerItemFragment(int position,int mImgRes) {
		this.mPosition = position;
		this.mImgRes=mImgRes;
	}
	public BannerItemFragment(int position,BannerDatum bannerDatum) {//首页
		this.mPosition = position;
		this.bannerDatum=bannerDatum;
	}
	public BannerItemFragment(int position,Datum datum) {//分类
		this.mPosition = position;
		this.datum=datum;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_banner_item, container, false);
		img = (ImageView) layout.findViewById(R.id.lunbotu);
		if(bannerDatum==null){
			img.setImageResource(mImgRes);
		}else{
			if(img!=null){
				UILUtils.displayImageNoAnim(bannerDatum.getPicUrl(), img);
			}
		}
		if(datum==null){
			img.setImageResource(mImgRes);
		}else{
			if(img!=null){
				UILUtils.displayImageNoAnim(datum.getPicUrl(), img);
			}
		}
		
//		img.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getActivity(), "item��" + mPosition, Toast.LENGTH_LONG).show();
//			}
//		});
		return layout;
	}

}
