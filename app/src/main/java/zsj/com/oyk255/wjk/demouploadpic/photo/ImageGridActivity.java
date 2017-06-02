package zsj.com.oyk255.wjk.demouploadpic.photo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.Constant;


/**
 * 设置图片显示的 GridView 下面可以设置当前页面显示的图片张数 选择图片的界面 GridView 设置图片数量要到 ImageGridAdapter
 */
public class ImageGridActivity extends Activity implements OnClickListener {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	// ArrayList<Entity> dataList;
	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;
	/** 可以选择图片的数量 */
	private int mIntentChooseNumber;

	/** 获得可以选择的图片数量 */
	public int getIntentChooseNumber() {
		Intent intent = getIntent();
		int mChooseNumber = intent.getIntExtra(Constant.PHOTO.INTNET_COUNT, 11);
		Log.e("mChooseNumber", "" + mChooseNumber);
		return mChooseNumber;
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
//				if (UGPConstants.PHOTO.TYPE.equals(UGPConstants.PHOTO.SINGLE)) {
//					Toast.makeText(ImageGridActivity.this, "最多选择" + 1 + "张图片", Toast.LENGTH_SHORT).show();
//				} else if (UGPConstants.PHOTO.TYPE.equals(UGPConstants.PHOTO.MULTIPLE)) {
//					Toast.makeText(ImageGridActivity.this, "最多选择" + mIntentChooseNumber + "张图片", Toast.LENGTH_SHORT).show();
//				}
				Toast.makeText(ImageGridActivity.this, "最多选择" + mIntentChooseNumber + "张图片", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_to_activity_image_grid);

		mIntentChooseNumber = getIntentChooseNumber();

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);

		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				// Collection<String> c = adapter.map.values();
				list.addAll(adapter.mlist);
				// Iterator<String> it = c.iterator();
				// for (; it.hasNext();) {
				// list.add(it.next());
				// }

				if (Bimp.act_bool) {
					finish();
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					/** 设置最多显示的图片张数 */
					if (Bimp.drr.size() < mIntentChooseNumber) {
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		findViewById(R.id.gird_tv_cancel).setOnClickListener(this);

		gridView = (GridView) findViewById(R.id.gridview_grid);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList, mHandler, mIntentChooseNumber);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/**
				 * 
				 */
				adapter.notifyDataSetChanged();
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gird_tv_cancel:
			Intent intent = new Intent(ImageGridActivity.this, ImageBucketActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}

	}
}
