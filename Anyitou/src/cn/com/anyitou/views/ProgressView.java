package cn.com.anyitou.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import cn.com.anyitou.R;

/***
 * 自定义圆弧进度条
 * 
 */
public class ProgressView extends View {

	//分段颜色 
//	private static final int[] SECTION_COLORS = { Color.GREEN, Color.BLUE,
//			Color.RED, Color.BLUE,Color.GREEN};
	private static final int[] SECTION_COLORS = new int[] { 0xFF37d38b, 0xFF0643ce,
		0xFFd73263,0xFF0643ce,0xFF37d38b};
	private static final String[] ALARM_LEVEL = { "时", "分", "秒"};
	private float maxCount = 100;
	private float currentCount;
	private int score;
	private String crrentLevel;
	private Paint mPaint;
	private Paint mTextPaint;
	private Paint mWaiPaint;//外环
	private int mWidth, mHeight;
	private Context context;

	public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(context);
	}

	public ProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public ProgressView(Context context) {
		this(context, null);
		this.context = context;
	}

	private void init(Context context) {
		mPaint = new Paint();
		mWaiPaint = new Paint();
		mTextPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaint();
		RectF rectBlackBg = new RectF(10, 10, mWidth - 10, mHeight - 10);
		//设置外圆环的画笔
		canvas.drawArc(rectBlackBg, 0, 360, false, mWaiPaint);
		mPaint.setColor(Color.BLACK);
		canvas.drawText(score+"", mWidth / 2, (mHeight+20) / 2, mTextPaint);
		mTextPaint.setTextSize(dipToPx(12));
		if (crrentLevel != null) {
			canvas.drawText(crrentLevel, mWidth / 2, mHeight / 2 + mHeight/3.5f,
					mTextPaint);
		}
		float section = currentCount / maxCount;
		if (section == 0f) {
//			if (section != 0.0f) {
//				mPaint.setColor(SECTION_COLORS[0]);
//			} else {
				mPaint.setColor(Color.TRANSPARENT);
//			}
		} else {
//			int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
			int count = (int)Math.ceil(section /0.2f);
			if(count ==1 ){
				mPaint.setColor(SECTION_COLORS[0]);
			}else{
				int[] colors = new int[count];
				System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
			
//			float[] positions = new float[count];
//			if (count == 2) {
//				positions[0] = 0.0f;
//				positions[1] = 1.0f - positions[0];
//			} else {
//				positions[0] = 0.0f;
//				positions[1] = (maxCount / 3) / currentCount;
//				positions[2] = 1.0f - positions[0] * 2;
//			}
//			positions[positions.length - 1] = 1.0f;
			// 创建LinearGradient并设置渐变颜色数组  
	        // 第一个,第二个参数表示渐变起点 可以设置起点终点在对角等任意位置  
	        // 第三个,第四个参数表示渐变终点  
	        // 第五个参数表示渐变颜色  
	        // 第六个参数可以为空,表示坐标,值为0-1 new float[] {0.25f, 0.5f, 0.75f, 1 }  
	        // 如果这是空的，颜色均匀分布，沿梯度线。  
	        // 第七个表示平铺方式  
	        // CLAMP重复最后一个颜色至最后  
	        // MIRROR重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果  
	        // REPEAT重复着色的图像水平或垂直方向 
//			LinearGradient shader = new LinearGradient(0, 0, mWidth
//					* section, mHeight, colors, null ,
//					Shader.TileMode.MIRROR);
				float[] positions = new float[count];
				if(count == 2){
					positions[0]=0.2f;
					positions[1]=0.4f;
				}else if(count == 3){
					positions[0]=0.2f;
					positions[1]=0.4f;
					positions[2]=0.6f;
				}else if(count == 4){
					positions[0]=0.2f;
					positions[1]=0.4f;
					positions[2]=0.6f;
					positions[3]=0.8f;
				}else if(count == 5){
					positions[0]=0.2f;
					positions[1]=0.4f;
					positions[2]=0.6f;
					positions[3]=0.8f;
					positions[4]=1f;
				}
//				Shader shader = new SweepGradient(0, 0, colors, null);
//				LinearGradient shader = new LinearGradient(0, 0, mWidth
//						, mHeight, colors, null ,
//						Shader.TileMode.MIRROR);
				LinearGradient shader = new LinearGradient(0f, 10f, Float.valueOf((mWidth-95)+"")
						, Float.valueOf((mHeight+120)+""), colors, null ,
						Shader.TileMode.MIRROR);
//				LinearGradient shader = new LinearGradient(-5f, 30f, Float.valueOf((mWidth-85)+"")
//						, Float.valueOf((mHeight+90)+""), colors, null ,
//						Shader.TileMode.MIRROR);
			mPaint.setShader(shader);
			}
		}
		canvas.drawArc(rectBlackBg, -90, section * 360, false, mPaint);
	}

	private void initPaint() {
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth((float) dipToPx(3));
		mPaint.setStyle(Style.STROKE);
//		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setColor(Color.TRANSPARENT);

		mWaiPaint.setAntiAlias(true);
		mWaiPaint.setStyle(Style.STROKE);
//		mWaiPaint.setStrokeCap(Cap.ROUND);
		mWaiPaint.setColor(context.getResources().getColor(R.color.time_cicle));
//		PathEffect effects = new DashPathEffect(new float[] { 13, 8, 13, 8}, 1); 
		PathEffect effects = new DashPathEffect(new float[] { dipToPx(6), 2, dipToPx(6), 2}, 1); 
		mWaiPaint.setPathEffect(effects);
		mWaiPaint.setStrokeWidth((float) (dipToPx(3)));
		
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStrokeWidth((float) 3.0);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(dipToPx(29));
		mTextPaint.setColor(Color.BLACK);

	}

	private int dipToPx(int dip) {
		float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	public int getScore() {
		return score;
	}

	public String getCrrentLevel() {
		return crrentLevel;
	}

	public void setCrrentLevel(String crrentLevel) {
		this.crrentLevel = crrentLevel;
	}

	public float getMaxCount() {
		return maxCount;
	}

	public float getCurrentCount() {
		return currentCount;
	}

	public void setScore(int score,String title,int maxCount) {
		this.score = score;
		this.crrentLevel = title;
		this.maxCount = maxCount;
		this.currentCount = score > maxCount ? maxCount : score;
		invalidate();
	}

	/***
	 * 设置最大的进度值
	 * 
	 * @param maxCount
	 */
	public void setMaxCount(float maxCount) {
		this.maxCount = maxCount;
	}

	/***
	 * 设置当前的进度值
	 * 
	 * @param currentCount
	 */
	public void setCurrentCount(float currentCount) {
		this.score = (int)currentCount;
		this.currentCount = currentCount > maxCount ? maxCount : currentCount;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		if (widthSpecMode == MeasureSpec.EXACTLY
				|| widthSpecMode == MeasureSpec.AT_MOST) {
			mWidth = widthSpecSize;
		} else {
			mWidth = 0;
		}
		if (heightSpecMode == MeasureSpec.AT_MOST
				|| heightSpecMode == MeasureSpec.UNSPECIFIED) {
			mHeight = dipToPx(15);
		} else {
			mHeight = heightSpecSize;
		}
		setMeasuredDimension(mWidth, mHeight);
	}

}
