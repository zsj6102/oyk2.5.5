package zsj.com.oyk255.example.ouyiku.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;

/**
 * 商品详情页图片viewpage
 *
 */
@SuppressLint("ValidFragment")
public class DetailGoodsinfoFragment extends Fragment {

	protected int mPosition;
	int mImgRes;
	String url;
	private int screenWidth;
	
	public DetailGoodsinfoFragment(int position,int mImgRes) {
		this.mPosition = position;
		this.mImgRes=mImgRes;
	}
	public DetailGoodsinfoFragment(int position,String url) {
		this.mPosition = position;
		this.url=url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_detail_goodsinfo, container, false);
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
		
//		img.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getActivity(), "item：" + mPosition, Toast.LENGTH_LONG).show();
//			}
//		});
		return layout;
	}

}
