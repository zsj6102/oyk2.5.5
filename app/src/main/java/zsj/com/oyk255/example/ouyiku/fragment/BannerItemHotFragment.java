package zsj.com.oyk255.example.ouyiku.fragment;



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
public class BannerItemHotFragment extends Fragment {
	protected int mPosition;
	int mImgRes;
	BannerDatum bannerDatum;
	private ImageView img;
	public BannerItemHotFragment() {
		
	}
	public BannerItemHotFragment(int position,int mImgRes) {
		this.mPosition = position;
		this.mImgRes=mImgRes;
	}
	public BannerItemHotFragment(int position,BannerDatum bannerDatum) {//首页
		this.mPosition = position;
		this.bannerDatum=bannerDatum;
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
		
		return layout;
	}

}
