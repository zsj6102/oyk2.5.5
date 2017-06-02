package zsj.com.oyk255.wjk.demouploadpic.window;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PictureUtil;
import zsj.com.oyk255.wjk.demouploadpic.photo.ImageBucketActivity;

/**
 * @ClassName: PhotoCameraWindow
 * @Description: UGP弹窗打开自定义相册/系统相机
 * @author: 王俊凯
 * @date: 20160427 18:00
 */
public class PhotoCameraWindow extends BaseWindow {

	private View contentView;
	private String photoPath;
	private int mChooseNumber;

	/**
	 * @param context
	 *            当前界面
	 * @param number
	 *            可选择图片数量
	 * @param
//	 * photoPath
	 *            拍照存储路径(可通过getPhotoPath()获得)
	 */
	public PhotoCameraWindow(final Activity context, int number) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mChooseNumber = number;
		if (contentView == null) {
			contentView = new InflaterPopLayout().initLayout(this, context, R.layout.popwindow_photo_camera_ugp, 0.8, 0.5);
		}
		initView(context);
	}

	/** 找出PopupWindow内部控件 */
	private void initView(final Activity context) {
		TextView tvCamera = (TextView) contentView.findViewById(R.id.pop_tv_camera);
		tvCamera.setText("相机");
		tvCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getCamera(context);
				PhotoCameraWindow.this.dismiss();
			}
		});
		TextView tvPhoto = (TextView) contentView.findViewById(R.id.pop_tv_photo);
		tvPhoto.setText("相册");
		tvPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getCustomPhoto(mChooseNumber, context);
				PhotoCameraWindow.this.dismiss();
			}
		});
		TextView tvCancel = (TextView) contentView.findViewById(R.id.pop_tv_cancel);
		tvCancel.setText("取消");
		tvCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoCameraWindow.this.dismiss();
			}
		});
	}

	/** 拍照 */
	private void getCamera(Activity context) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			// 指定存放拍摄照片的位置
			File f = createImageFile();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			context.startActivityForResult(takePictureIntent, Constant.PHOTO.RESULT_CAMERA_IMAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把程序拍摄的照片放到 SD卡的 Pictures目录中 文件夹中 照片的命名规则为：Constants.PHOTO.FILE_NAME时间戳.jpg
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
		long currentTimeMillis = System.currentTimeMillis();
		String imageFileName = Constant.PHOTO.FILE_NAME + currentTimeMillis + ".jpg";
		File imgFile = new File(PictureUtil.getAlbumDir(), imageFileName);
		photoPath = imgFile.getAbsolutePath();
		return imgFile;
	}

	/** 打开自定义相册 */
	private void getCustomPhoto(int mIntentChooseNumber, Activity context) {
		Intent intentPhoto = new Intent();
		intentPhoto.putExtra(Constant.PHOTO.INTNET_COUNT, mIntentChooseNumber);
		intentPhoto.setClass(context, ImageBucketActivity.class);
		context.startActivity(intentPhoto);
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
}
