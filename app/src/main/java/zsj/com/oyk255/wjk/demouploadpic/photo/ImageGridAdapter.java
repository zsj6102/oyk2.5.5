package zsj.com.oyk255.wjk.demouploadpic.photo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zsj.com.oyk255.R;


public class ImageGridAdapter extends BaseAdapter {

	private TextCallback textcallback = null;
	final String TAG = getClass().getSimpleName();
	Activity act;
	List<ImageItem> dataList;
	// Map<String, String> map = new HashMap<String, String>();
	List<String> mlist = new ArrayList<String>();
	BitmapCache cache;
	private Handler mHandler;
	private int selectTotal = 0;
	/** imageGridAdpter的时候设置 */
	private int mChooseNumber = 11;

	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					// Log.e(TAG, "callback, bmp not match");
				}
			} else {
				// Log.e(TAG, "callback, bmp null");
			}
		}
	};

	/** 设置可以选择的堆图片数量 */
	public void setChooseNumber(int mChooseNumber) {
		this.mChooseNumber = mChooseNumber;
	}

	/** 获得可以选择的图片数量 */
	public int getChooseNumber() {
		return mChooseNumber;
	}

	/***/
	public static interface TextCallback {
		public void onListen(int count);
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler, int mChooseNumber) {
		this.act = act;
		dataList = list;
		this.mChooseNumber = mChooseNumber;
		cache = new BitmapCache();
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(act, R.layout.photo_to_item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
			holder.text = (TextView) convertView.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);

		holder.iv.setTag(item.imagePath);
		cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath, callback);
		if (item.isSelected) {
			holder.selected.setImageResource(R.mipmap.photo_icon_data_select);
			holder.text.setBackgroundResource(R.drawable.photo_bgd_relatly_line);
		} else {
			holder.selected.setImageResource(-1);
			holder.text.setBackgroundColor(0x00000000);
		}
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = dataList.get(position).imagePath;
				// TODO 这里有个4 换成了mChooseNumber
				if ((Bimp.drr.size() + selectTotal) < mChooseNumber) {
					item.isSelected = !item.isSelected;
					if (item.isSelected) {
						holder.selected.setImageResource(R.mipmap.photo_icon_data_select);
						holder.text.setBackgroundResource(R.drawable.photo_bgd_relatly_line);
						selectTotal++;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						// map.put(path, path);
						mlist.add(path);

					} else if (!item.isSelected) {
						holder.selected.setImageResource(-1);
						holder.text.setBackgroundColor(0x00000000);
						selectTotal--;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						// map.remove(path);
						for (int i = 0; i < mlist.size(); i++) {
							if (path.equals(mlist.get(i))) {
								mlist.remove(i);
								break;
							}
						}
					}// TODO 这里有个4 换成了mChooseNumber
				} else if ((Bimp.drr.size() + selectTotal) >= mChooseNumber) {
					if (item.isSelected == true) {
						item.isSelected = !item.isSelected;
						holder.selected.setImageResource(-1);
						selectTotal--;
						// map.remove(path);
						for (int i = 0; i < mlist.size(); i++) {
							if (path.equals(mlist.get(i))) {
								mlist.remove(i);
								break;
							}
						}

					} else {
						Message message = Message.obtain(mHandler, 0);
						message.sendToTarget();
					}
				}
			}

		});

		return convertView;
	}
}
