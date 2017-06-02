package zsj.com.oyk255.example.ouyiku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ViewpageIndicator extends View{

	private Paint paint;
	private Paint paint2;
	private static  float cx;
	private static  float cy;
	private static final int banjing=5;
	int count;
	int offset;
	public ViewpageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint2.setColor(Color.parseColor("#f8bbd0"));
	}
	public void move(int position,float percent,int count){
		this.count = count;
		if(position+1!=count){
			offset=(int) ((position+percent)*3*banjing);
		}else{
			offset=(int)(position*3*banjing);
		}
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		cy = getHeight() / 2;
		cx = (float) (getWidth()/2 - 1.5 * (count-1) * banjing); 
		for (int i = 0; i < count; i++) {
			canvas.drawCircle(cx+3*banjing*i, cy, banjing, paint);
		}
		canvas.drawCircle(cx+offset, cy, banjing, paint2);
	}
}
