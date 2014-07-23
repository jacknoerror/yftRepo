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
 * �Զ����е�������Ŀǰֻ֧����Ӹ߶�һ����ChildView,����ÿ�а�����View�ĸ߶�
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
	 * �����ӿؼ��Ļ���
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int PARENT_WIDTH = r-l;
		int x = 0;
		int y = 0;
		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			final View childView = getChildAt(j);
			// ��ȡ�ӿؼ�Child�Ŀ��
			int w = childView.getMeasuredWidth();
			int h = childView.getMeasuredHeight();//XXX ��������
			
			if (overTheLine(x+w, PARENT_WIDTH)) {//ˮƽ����
//			if (x+w >PARENT_WIDTH-PAD*2) {//ˮƽ����
//			if (i >= (columns - 1)) {//ˮƽ����
				x = 0;
				y += h;
			} else {
				
			}
			// �����ӿؼ��Ķ�������
			int left = x + PAD;//�̶��߾�
			int top = y + PAD;
			// �����ӿؼ�
			childView.layout(left, top, left + w, top + h);

			x += w;
			
		}
	}

	/**
	 * ����ؼ����ӿؼ���ռ����
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSum=0,heightSum = PAD*2;
		// �����ӿռ�Child�Ŀ��
		// ������������		 
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
		// ���������ؼ���ռ�����С
		// ע��setMeasuredDimension��resolveSize���÷�
		setMeasuredDimension(widthMeasureSpec,//resolveSize(widthSum, widthMeasureSpec), //д�����
				resolveSize(heightSum, heightMeasureSpec));
		// setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

		// ����Ҫ���ø���ķ���
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
	 * Ϊ�ؼ���ӱ߿�
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// ��ȡ���ֿؼ����
		int width = getWidth();
		int height = getHeight();
		
		int strokeWidth = 5;
		// ��������
		Paint mPaint = new Paint();
		// ���û��ʵĸ�������
		mPaint.setColor(getResources().getColor(R.color.bg_grey_darker));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(strokeWidth);
		mPaint.setAntiAlias(true);
		// �������ο�
		Rect mRect = new Rect(-strokeWidth, 0, width+strokeWidth*2, height );
		// ���Ʊ߿�
		canvas.drawRect(mRect, mPaint);
		// ��������ø���ķ���
		super.dispatchDraw(canvas);
	}
}
