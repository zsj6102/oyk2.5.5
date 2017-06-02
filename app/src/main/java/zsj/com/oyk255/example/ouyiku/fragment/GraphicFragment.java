package zsj.com.oyk255.example.ouyiku.fragment;

import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.suiyuchen.UILUtils;

/**
 * 图文介绍
 *
 */
public class GraphicFragment extends Fragment {

	private View graphicview;
	private ScrollViewWithListView mGraphicListview;
	ArrayList<String> mGraphicData;
	
	public GraphicFragment() {
	}
	public GraphicFragment(ArrayList<String> mGraphicData) {
		this.mGraphicData=mGraphicData;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(graphicview==null){
			graphicview = inflater.inflate(R.layout.fragment_graphic, container, false);
			initUI();
			initData();
		}
		
		return graphicview;
	}

	private void initData() {
		
		
	}
	@SuppressWarnings("static-access")
	private void initUI() {
		View footView = getActivity().getLayoutInflater().inflate(R.layout.detail_footview_item, null);
		mGraphicListview = (ScrollViewWithListView) graphicview.findViewById(R.id.graphic_listview);
		mGraphicListview.addFooterView(footView);
		mGraphicListview.setAdapter(new GraphicAdaptr());
		mGraphicListview.setEnabled(false);//设置listview不可点击
		
	}
	
	 class GraphicAdaptr extends BaseAdapter{

			@Override
			public int getCount() {
				return mGraphicData.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@SuppressLint("ViewHolder")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View layout =getActivity().getLayoutInflater().inflate(R.layout.graphic_item, null);
				AspectRatioImageView mGraphic_img = (AspectRatioImageView) layout.findViewById(R.id.graphic_img);
				UILUtils.displayImageNoAnim(mGraphicData.get(position), mGraphic_img);
				
				return layout;
			}
			
		}
}
