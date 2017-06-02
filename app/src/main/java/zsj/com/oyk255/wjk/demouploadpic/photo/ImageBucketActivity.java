package zsj.com.oyk255.wjk.demouploadpic.photo;

import java.io.Serializable;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.Constant;

/** 相册列表界面 */
public class ImageBucketActivity extends Activity implements OnClickListener {

	// ArrayList<Entity> dataList;//用来装载数据源的列表
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	/** 可以选择图片的数量 */
	private int mIntentChooseNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_to_activity_image_bucket);
		
		mIntentChooseNumber = getIntentChooseNumber();
		
		helper = AlbumHelper.getHelper();
		helper.init(this);
//		helper.init(getApplicationContext());

		initData();
		initView();
	}
	
	/** 获得可以选择的图片数量 */
	public int getIntentChooseNumber() {
		Intent intent = getIntent();
		int mChooseNumber = intent.getIntExtra(Constant.PHOTO.INTNET_COUNT, 11);
		Log.e("mChooseNumber", "" + mChooseNumber);
		return mChooseNumber;
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);
		bimap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo_icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		findViewById(R.id.bucket_ly_back).setOnClickListener(this);

		gridView = (GridView) findViewById(R.id.gridview_bucket);
		adapter = new ImageBucketAdapter(ImageBucketActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.putExtra(Constant.PHOTO.INTNET_COUNT, mIntentChooseNumber);
				intent.putExtra(ImageBucketActivity.EXTRA_IMAGE_LIST, (Serializable) dataList.get(position).imageList);
				intent.setClass(ImageBucketActivity.this, ImageGridActivity.class);
				startActivity(intent);
				finish();
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bucket_ly_back:
			finish();
			break;

		default:
			break;
		}
	}
}
