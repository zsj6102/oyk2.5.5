package zsj.com.oyk255.example.ouyiku.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;

/**
 * 头像工具
 * 
 * @author wlh
 * 
 */
public class PhotoUtil {
	public static final int PHOTO_REQUEST_CUT = 3;// 剪裁

	@SuppressLint("SimpleDateFormat")
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 剪裁
	 * 
	 * @param uri
	 * @param size
	 * @param context
	 */
	public static void startPhotoZoom(Uri uri, int size, Activity context) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	public static String byte2hex(byte[] b) {// 二进制转字符串
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	// Bitmap转换成byte[]
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static String getHeadString(Bitmap bm) {
		byte[] bytes = bitmap2Bytes(bm);
		return byte2hex(bytes);
	}

	public static byte[] bmpToByteArray1(final Bitmap bmp,final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,final boolean needRecycle) {
		int i = 0;
		int j = 0;
		if (bmp.getHeight() > bmp.getWidth()) {
			i = bmp.getWidth();
			j = bmp.getWidth();
		} else {
			i = bmp.getHeight();
			j = bmp.getHeight();
		}

		Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j),null);
		if (needRecycle) bmp.recycle();
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		localBitmap.compress(CompressFormat.JPEG, 100,localByteArrayOutputStream);
		localBitmap.recycle();
		byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
		try {
			localByteArrayOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayOfByte;
	}
	
	
	/**
	 * 把网络资源图片转化成bitmap
	 * @param url  网络资源图片
	 * @return  Bitmap
	 */
	public static Bitmap GetLocalOrNetBitmap(String url) {
	    Bitmap bitmap = null;
	    InputStream in = null;
	    BufferedOutputStream out = null;
	    try {
	        in = new BufferedInputStream(new URL(url).openStream(), 1024);
	        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	        out = new BufferedOutputStream(dataStream, 1024);
	        copy(in, out);
	        out.flush();
	        byte[] data = dataStream.toByteArray();
	        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
	        data = null;
	        return bitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	private static void copy(InputStream in, OutputStream out)
	        throws IOException {
	    byte[] b = new byte[1024];
	    int read;
	    while ((read = in.read(b)) != -1) {
	        out.write(b, 0, read);
	    }
	}

}
