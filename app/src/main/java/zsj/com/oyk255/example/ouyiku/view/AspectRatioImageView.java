package zsj.com.oyk255.example.ouyiku.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioImageView extends ImageView{

	/**
	 * 
	 * 自定义imageview，等比例拉伸
	 * */
	
	
	public AspectRatioImageView(Context context) 

	{

	super(context);

	}



	public AspectRatioImageView(Context context, AttributeSet attrs) 

	{

	super(context, attrs);

	}



	public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) 

	{

	super(context, attrs, defStyle);

	}



	@Override

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 

	{

	int width = MeasureSpec.getSize(widthMeasureSpec);

	int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();

	setMeasuredDimension(width, height);

	}

}
