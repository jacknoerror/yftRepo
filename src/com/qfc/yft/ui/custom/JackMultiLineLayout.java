package com.qfc.yft.ui.custom;

import com.qfc.yft.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动换行的容器，目前只支持添加高度一样的ChildView,否则每行按行首View的高度
 * @author taotao
 * @Date 2014-6-18
 */
public class JackMultiLineLayout extends ViewGroup {

	private final int PAD = (int) getResources().getDimension(R.dimen.common_margin_bigger);
	private Context context;

	public JackMultiLineLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public JackMultiLineLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public JackMultiLineLayout(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		
	}

	/**
	 * 控制子控件的换行
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int PARENT_WIDTH = r-l;
		int x = 0;
		int y = 0;
		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			final View childView = getChildAt(j);
			// 获取子控件Child的宽高
			int w = childView.getMeasuredWidth();
			int h = childView.getMeasuredHeight();//XXX 考虑设置
			
			if (overTheLine(x+w, PARENT_WIDTH)) {//水平超出
//			if (x+w >PARENT_WIDTH-PAD*2) {//水平超出
//			if (i >= (columns - 1)) {//水平超出
				x = 0;
				y += h;
			} else {
				
			}
			// 计算子控件的顶点坐标
			int left = x + PAD;//固定边距
			int top = y + PAD;
			// 布局子控件
			childView.layout(left, top, left + w, top + h);

			x += w;
			
		}
	}

	/**
	 * 计算控件及子控件所占区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSum=0,heightSum = PAD*2;
		// 设置子空间Child的宽高
		// 创建测量参数		 
		int maxWidthSize = MeasureSpec.getSize(widthMeasureSpec);
		int cellWidthSpec = MeasureSpec.makeMeasureSpec(maxWidthSize, MeasureSpec.AT_MOST);//
		int cellHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST);
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			
			childView.measure(cellWidthSpec, cellHeightSpec);
			
			int childMeasuredHeight = childView.getMeasuredHeight();
			int childMeasuredWidth = childView.getMeasuredWidth();
			
			if(heightSum<childMeasuredHeight)heightSum += childMeasuredHeight; //firsttime
			
			widthSum+= childMeasuredWidth;
			if(overTheLine(widthSum, maxWidthSize)){
				widthSum = childMeasuredWidth;
				heightSum+=childMeasuredHeight;
			}
		}
		// 设置容器控件所占区域大小
		// 注意setMeasuredDimension和resolveSize的用法
		setMeasuredDimension(widthMeasureSpec,//resolveSize(widthSum, widthMeasureSpec), //写死最大
				resolveSize(heightSum, heightMeasureSpec));
		// setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

		// 不需要调用父类的方法
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * @param widthSum
	 * @param maxSize
	 * @return
	 */
	public boolean overTheLine(int widthSum, int maxSize) {
		return widthSum>maxSize-PAD*2;
	}

	/**
	 * 为控件添加边框
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// 获取布局控件宽高
		int width = getWidth();
		int height = getHeight();
		
		int strokeWidth = 5;
		// 创建画笔
		Paint mPaint = new Paint();
		// 设置画笔的各个属性
		mPaint.setColor(getResources().getColor(R.color.bg_grey_darker));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(strokeWidth);
		mPaint.setAntiAlias(true);
		// 创建矩形框
		Rect mRect = new Rect(-strokeWidth, 0, width+strokeWidth*2, height );
		// 绘制边框
		canvas.drawRect(mRect, mPaint);
		// 最后必须调用父类的方法
		super.dispatchDraw(canvas);
	}
}
