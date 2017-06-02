package zsj.com.oyk255.example.ouyiku.wuliuline;

import java.util.List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import zsj.com.oyk255.R;

/**
 * 仿淘宝物流节点显示线
 * 
 * @author zad
 * 
 */
public class CustomNodeLineView extends View {

	private Context context;
	/**
	 * 画笔
	 */
	private Paint mPaint;
	/**
	 * 节点线顶部节点外环半径
	 */
	private int topNodeOutSideRadius;
	/**
	 * 节点先顶部节点内环半径
	 */
	private int topNodeInSideRadius;
	/**
	 * 节点线其他节点半径
	 */
	private int otherNodeRadius;
	/**
	 * 节点线节点之间的距离（距离相等的情况）
	 */
	private int nodeRadiusDistance;
	/**
	 * 节点线顶部节点颜色
	 */
	private int topNodeColor;
	/**
	 * 节点线其他节点颜色
	 */
	private int otherNodeColor;
	/**
	 * 节点线顶部外边距
	 */
	private int viewMarginTop;
	/**
	 * 节点线节点个数
	 */
	private int nodeCount;
	/**
	 * 节点线X位置
	 */
	private int nodeLineX;
	/**
	 * 节点线宽度
	 */
	private int viewWidth;
	/**
	 * 顶部节点外环厚度
	 */
	private float topNodePaintStrokeWidth;
	/**
	 * 每个节点之间的距离集合（距离不等的情况）
	 */
	private List<Integer> nodeRadiusDistances;

	public CustomNodeLineView(Context context) {
		this(context, null);
	}

	public CustomNodeLineView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomNodeLineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		mPaint = new Paint();
		mPaint.setAntiAlias(true); // 抗锯齿
		// 初始化属性数组
		final TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CustomNodeLineView);
		// 节点线顶部节点外环半径
		topNodeOutSideRadius = (int) a.getDimension(
				R.styleable.CustomNodeLineView_topNodeOutSideRadius,
				Dp2Px(context, 10));
		// 节点线顶部节点内环半径
		topNodeInSideRadius = (int) a.getDimension(
				R.styleable.CustomNodeLineView_topNodeInSideRadius,
				Dp2Px(context, 8));
		// 节点线其他节点半径
		otherNodeRadius = (int) a.getDimension(
				R.styleable.CustomNodeLineView_otherNodeRadius,
				Dp2Px(context, 6));
		// 节点线节点距离
		nodeRadiusDistance = (int) a.getDimension(
				R.styleable.CustomNodeLineView_nodeRadiusDistance,
				Dp2Px(context, 20));
		// 顶部节点颜色
		topNodeColor = a.getColor(R.styleable.CustomNodeLineView_topNodeColor,
				Color.parseColor("#5FCC7C"));
		// 其他节点颜色
		otherNodeColor = a.getColor(
				R.styleable.CustomNodeLineView_otherNodeColor,
				Color.parseColor("#D8D8D8"));
		// 节点线顶部外边距
		viewMarginTop = (int) a.getDimension(
				R.styleable.CustomNodeLineView_viewMarginTop,
				Dp2Px(context, 10));
		// 节点线节点个数
		nodeCount = (int) a.getInteger(
				R.styleable.CustomNodeLineView_nodeCount, 0);
		// 节点线的宽度
		viewWidth = (int) a.getDimension(
				R.styleable.CustomNodeLineView_viewWidth, 1);
		// 顶部节点外环厚度
		topNodePaintStrokeWidth = 1.0f;
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 默认节点线的X坐标位于空间的正中间
		nodeLineX = getMeasuredWidth() / 2;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 设置画笔颜色为顶部节点颜色，绘制顶部节点
		mPaint.setColor(topNodeColor);
		float nodeLineY = 0.0f;
		// 遍历节点
		for (int i = 0; i < nodeCount; i++) {
			if (i == 0) {
				// 设置画笔样式为空心
				mPaint.setStyle(Style.STROKE);
				// 外环画笔宽度
				mPaint.setStrokeWidth(topNodePaintStrokeWidth);
				// 绘制顶部节点外环
				canvas.drawCircle(nodeLineX, topNodeOutSideRadius
						+ viewMarginTop, topNodeOutSideRadius, mPaint);
				// 设置画笔为实心
				mPaint.setStyle(Style.FILL);
				// 绘制顶部节点内环
				canvas.drawCircle(nodeLineX, topNodeOutSideRadius
						+ viewMarginTop, topNodeInSideRadius, mPaint);
				// 设置画笔颜色
				mPaint.setColor(otherNodeColor);
				// 绘制其他节点下的节点线
				canvas.drawLine(nodeLineX, 2 * topNodeOutSideRadius
						+ viewMarginTop + topNodePaintStrokeWidth / 2,
						nodeLineX, topNodeOutSideRadius + viewMarginTop
								+ nodeRadiusDistances.get(i),
						mPaint);
				nodeLineY += 2 * topNodeOutSideRadius + viewMarginTop
						+ topNodePaintStrokeWidth / 2
						+ nodeRadiusDistances.get(i) - 2 * otherNodeRadius;
			} else{
				// 绘制其他节点
				canvas.drawCircle(nodeLineX, nodeLineY,otherNodeRadius, mPaint);
				// 绘制其他节点下的节点线
				canvas.drawLine(nodeLineX, nodeLineY,
						nodeLineX, nodeLineY + nodeRadiusDistances.get(i), mPaint);
				nodeLineY += nodeRadiusDistances.get(i);
			}
		}
	}

	/**
	 * dp转换成px
	 * 
	 * @param context
	 * @param dp
	 * @return int
	 */
	private static int Dp2Px(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f * (dp >= 0 ? 1 : -1));
	}

	public int getTopNodeRadius() {
		return topNodeOutSideRadius;
	}

	public void setTopNodeRadius(int topNodeRadius) {
		this.topNodeOutSideRadius = topNodeRadius;
		invalidate();
	}

	public int getNodeRadius() {
		return otherNodeRadius;
	}

	public void setNodeRadius(int nodeRadius) {
		this.otherNodeRadius = nodeRadius;
		invalidate();
	}

	public int getNodeRadiusDistance() {
		return nodeRadiusDistance;
	}

	public void setNodeRadiusDistance(int nodeRadiusDistance) {
		this.nodeRadiusDistance = nodeRadiusDistance;
		invalidate();
	}

	public int getTopNodeColor() {
		return topNodeColor;
	}

	public void setTopNodeColor(int topNodeColor) {
		this.topNodeColor = topNodeColor;
		invalidate();
	}

	public int getOtherNodeColor() {
		return otherNodeColor;
	}

	public void setOtherNodeColor(int otherNodeColor) {
		this.otherNodeColor = otherNodeColor;
		invalidate();
	}

	public int getViewMarginTop() {
		return viewMarginTop;
	}

	public void setViewMarginTop(int viewMarginTop) {
		this.viewMarginTop = viewMarginTop;
		invalidate();
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
		invalidate();
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
		invalidate();
	}

	public float getTopNodePaintStrokeWidth() {
		return topNodePaintStrokeWidth;
	}

	public void setTopNodePaintStrokeWidth(float topNodePaintStrokeWidth) {
		this.topNodePaintStrokeWidth = topNodePaintStrokeWidth;
		invalidate();
	}

	public int getTopNodeOutSideRadius() {
		return topNodeOutSideRadius;
	}

	public void setTopNodeOutSideRadius(int topNodeOutSideRadius) {
		this.topNodeOutSideRadius = topNodeOutSideRadius;
	}

	public int getTopNodeInSideRadius() {
		return topNodeInSideRadius;
	}

	public void setTopNodeInSideRadius(int topNodeInSideRadius) {
		this.topNodeInSideRadius = topNodeInSideRadius;
	}

	public int getOtherNodeRadius() {
		return otherNodeRadius;
	}

	public void setOtherNodeRadius(int otherNodeRadius) {
		this.otherNodeRadius = otherNodeRadius;
	}

	public List<Integer> getNodeRadiusDistances() {
		return nodeRadiusDistances;
	}

	public void setNodeRadiusDistances(List<Integer> nodeRadiusDistances) {
		this.nodeRadiusDistances = nodeRadiusDistances;
	}

}