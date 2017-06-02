package zsj.com.oyk255.example.ouyiku.v1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PinDetailBannerFragment extends Fragment {
	
	protected int mPosition;
	int mImgRes;
	String url;
	private int screenWidth;
	private String buyNumber;
	
	public PinDetailBannerFragment(int position,int mImgRes) {
		this.mPosition = position;
		this.mImgRes=mImgRes;
	}
	public PinDetailBannerFragment(int position,String url,String buyNumber) {
		this.mPosition = position;
		this.url=url;
		this.buyNumber=buyNumber;
	}

	public PinDetailBannerFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_pin_detail_banner, container,
				false);
		
		screenWidth = ScreenUtils.getScreenWidth(getActivity());
		
		ImageView img = (ImageView) layout.findViewById(R.id.goods_img);
		img.getLayoutParams().height = screenWidth ;
		img.getLayoutParams().width = screenWidth ;
		if(url==null){
			img.setImageResource(mImgRes);
			
		}else{
			if(img!=null){
				UILUtils.displayImageNoAnim(url, img);
			}
		}
		
		TextView mNum = (TextView) layout.findViewById(R.id.pintuan_totalpeople);
		Integer valueOf = Integer.valueOf(buyNumber);
		
		
		if(valueOf<10){
			mNum.setText("0"+buyNumber);
		}else{
			mNum.setText(buyNumber);
		}
		
		
		
		return layout;
	}

}
