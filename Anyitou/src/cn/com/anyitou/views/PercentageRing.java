package cn.com.anyitou.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import cn.com.anyitou.R;
import cn.com.anyitou.utils.DeviceInfo;

public class PercentageRing extends View{
	private Paint mCirclePaint;
	private Paint mTextPaint;
	private Paint mArcPaint;
	private Paint mArcPaint2;
	private int mCircleX;
	private int mCircleY;
	private float mCurrentAngle;
	private RectF mArcRectF;
	private float mStartSweepValue;
	private float mTargetPercent;
	private float mCurrentPercent;

	private int mRadius;
	private int mCircleBackground;
	private int mRingColor;
	private int mTextSize;
	private int mTextColor;
	private int mRingColor2;


	public PercentageRing(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PercentageRing(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTargetPercent = 10;
		//自定义属性 values/attr
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PercentageRing);
		//中间圆的背景颜色  默认为浅紫色
		mCircleBackground = typedArray.getColor(R.styleable.PercentageRing_circleBackground, 0xffffffff);
		//外圆环的颜色  默认为深紫色
		mRingColor = typedArray.getColor(R.styleable.PercentageRing_ringColor, 0xfff45149);
		mRingColor2 = typedArray.getColor(R.styleable.PercentageRing_circleBackground, 0xffe6e6e6);
		//中间圆的半径 默认为60
		mRadius = typedArray.getInt(R.styleable.PercentageRing_radiusPercent, 60);
		mRadius = DeviceInfo.dp2px(this.getContext(), Float.valueOf(mRadius+""));
		//字体颜色 默认为白色
		mTextColor = typedArray.getColor(R.styleable.PercentageRing_textTitleColor, 0xfff45149);
        //最后一定要调用这个 释放掉TypedArray
		typedArray.recycle();
        //初始化数据
		init(context);
		
	}

	public PercentageRing(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context){
		//圆环开始角度 -90° 正北方向
		mStartSweepValue = -90;
		//当前角度
		mCurrentAngle = 0;
		//当前百分比
		mCurrentPercent = 0;
		//设置中心园的画笔
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleBackground);
		mCirclePaint.setStyle(Paint.Style.FILL);
		//设置文字的画笔
		mTextPaint = new Paint();
		mTextPaint.setColor(mTextColor);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setStrokeWidth((float) (0.035*mRadius));
		mTextPaint.setTextSize(mRadius/2);
		mTextPaint.setTextAlign(Align.CENTER);
		//设置外圆环的画笔
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setColor(mRingColor);
		mArcPaint.setStyle(Paint.Style.STROKE);
		mArcPaint.setStrokeWidth((float) (0.15*mRadius));
		
		
		mArcPaint2 = new Paint();
		mArcPaint2.setAntiAlias(true);
		mArcPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);//帮助消除锯齿
		mArcPaint2.setColor(mRingColor2);
		mArcPaint2.setStyle(Paint.Style.STROKE);//设置中控的样式
		mArcPaint2.setStrokeWidth((float) (0.15*mRadius));
		//获得文字的字号 因为要设置文字在圆的中心位置
		mTextSize = (int) mTextPaint.getTextSize();


	}

	//主要是测量wrap_content时候的宽和高，因为宽高一样，只需要测量一次宽即可，高等于宽
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec), measure(widthMeasureSpec));
		//设置圆心坐标
		mCircleX = getMeasuredWidth()/2;
		mCircleY = getMeasuredHeight()/2;
		//如果半径大于圆心横坐标，需要手动缩小半径的值，否则就画到外面去了
		if (mRadius>mCircleX) {
			//设置半径大小为圆心横坐标到原点的距离
			mRadius = mCircleX;
			mRadius = (int) (mCircleX-0.1*mRadius);
			//因为半径改变了，所以要重新设置一下字体宽度
			mTextPaint.setStrokeWidth((float) (0.035*mRadius));
			//重新设置字号
			mTextPaint.setTextSize(mRadius/2);
			//重新设置外圆环宽度
			mArcPaint.setStrokeWidth((float) (0.15*mRadius));
			
			mArcPaint2.setStrokeWidth((float) (0.15*mRadius));
            //重新获得字号大小
			mTextSize = (int) mTextPaint.getTextSize();
		}
		//画中心园的外接矩形，用来画圆环用
		mArcRectF = new RectF(mCircleX-mRadius, mCircleY-mRadius, mCircleX+mRadius, mCircleY+mRadius);
	}

	//当wrap_content的时候，view的大小根据半径大小改变，但最大不会超过屏幕
	private int measure(int measureSpec){
		int result=0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}else {
			result =(int) (1.1*mRadius*2);
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;

	}
    //开始画中间圆、文字和外圆环
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        //画中间圆
		canvas.drawCircle(mCircleX, mCircleY, mRadius, mCirclePaint);
		canvas.drawCircle(mCircleX, mCircleY ,mRadius, mArcPaint2);
        //画圆环
		canvas.drawArc(mArcRectF, mStartSweepValue ,mCurrentAngle, false, mArcPaint);
		
		
        //画文字
		canvas.drawText(String.valueOf(Math.round(mCurrentPercent))+"%", mCircleX, mCircleY+mTextSize/4, mTextPaint);
        //判断当前百分比是否小于设置目标的百分比
		if (mCurrentPercent<mTargetPercent) {
            //当前百分比+1
			mCurrentPercent+=1;
            //当前角度+360
			mCurrentAngle+=3.6;
            //每4ms重画一次
//			postInvalidateDelayed(4);
			postInvalidate();
		}

	}

    //设置目标的百分比
	public void setTargetPercent(int percent){
		this.mTargetPercent = percent;
	}

}
