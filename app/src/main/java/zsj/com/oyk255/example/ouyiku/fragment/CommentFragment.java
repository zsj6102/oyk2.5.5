package zsj.com.oyk255.example.ouyiku.fragment;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;

/**
 * 评论
 *
 */
public class CommentFragment extends Fragment {

	private View commentview;
	private ScrollViewWithListView mCommentListview;

	public CommentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(commentview==null){
			commentview = inflater.inflate(R.layout.fragment_comment, container, false);
			initUI();
			
		}
		return commentview;
	}

	private void initUI() {
		View footView = getActivity().getLayoutInflater().inflate(R.layout.detail_comment_foot_item, null);
		View headView = getActivity().getLayoutInflater().inflate(R.layout.detail_comment_headview_item, null);
		mCommentListview = (ScrollViewWithListView) commentview.findViewById(R.id.comment_listview);
		mCommentListview.addFooterView(footView);
		mCommentListview.addHeaderView(headView);
		mCommentListview.setAdapter(new CommentAdaptr());
	}
	class CommentAdaptr extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
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
			View layout =getActivity().getLayoutInflater().inflate(R.layout.comment_item, null);
			
			return layout;
		}
		
	}
	
}
