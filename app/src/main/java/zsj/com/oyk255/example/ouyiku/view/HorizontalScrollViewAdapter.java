package zsj.com.oyk255.example.ouyiku.view;

import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;

public class HorizontalScrollViewAdapter
{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<Integer> mDatas;

	public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount()
	{
		return 7;
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.branddetail_grid_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.branddetail_new_img);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.branddetail_new_tvname);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageResource(R.mipmap.logo);
		viewHolder.mText.setText("some info ");

		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}

}
