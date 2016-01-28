package cn.com.anyitou.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.Grades;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.DeviceInfo;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.roundprogress.TextRoundCornerProgressBar;

/**
 * 我的等级
 * 
 * @author will
 * 
 */
public class MyLevelActivity extends BaseActivity {

	ActionBar mActionBar;
	private LoadingDialog loading;
	
	private View mBtnMemberChangeRecord,mBtnMemberViewRight,mBtnMemberGrowth;
	
	Grades mGrades;
	private TextView mTvLevelName,mTvGrow;
	private TextRoundCornerProgressBar mPbLevel;
	private TextView mTvMax;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_level);
		super.onCreate(savedInstanceState);
		mGrades = (Grades)this.getIntent().getSerializableExtra("grades");
		showMyLevel();
		initData();
	}

	// 设置activity的导航条
	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("我的级别");
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mBtnMemberChangeRecord = findViewById(R.id.member_change_record);
		mBtnMemberViewRight = findViewById(R.id.member_viewright);
		mBtnMemberGrowth = findViewById(R.id.member_growth);
		
		mTvLevelName = (TextView)findViewById(R.id.level_name);
//		mTvGrow = (TextView)findViewById(R.id.level_total);
		mTvMax = (TextView)findViewById(R.id.max);
		mPbLevel = (TextRoundCornerProgressBar) findViewById(R.id.level_progressbar2);
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mBtnMemberChangeRecord.setOnClickListener(onClickListener);
		mBtnMemberViewRight.setOnClickListener(onClickListener);
		mBtnMemberGrowth.setOnClickListener(onClickListener);
	}
	
	private void initData(){
		loading = new LoadingDialog(mContext);
		loading.show();
		ApiUserUtils.getGrades(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loading.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					mGrades = JsonUtils.fromJson(parseModel.getData().toString(), Grades.class);
					showMyLevel();
				}else{
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}
	
	private void showMyLevel(){
		if(mGrades !=null){
			mTvLevelName.setText(mGrades.getName());
			int levelLogo = R.drawable.user_level_normal_big_icon;
			if("1".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_normal_big_icon;
			}else if("2".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_star_big_icon;
			}else if("3".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_gold_big_icon;
			}else if("4".equals(mGrades.getUser_level())){
				levelLogo = R.drawable.user_level_diamond_big_icon;
			}
			int currentGrow = (int) Math.round(Double.valueOf(mGrades.getGrow_val()));
			int max = (int) Math.round( Double.valueOf(StringUtils.isEmpty(mGrades.getNeedGrowVal())?"0":mGrades.getNeedGrowVal()) + currentGrow );
			mPbLevel.setProgressText(currentGrow+"");
			mPbLevel.setMax(max);
			mPbLevel.setProgress(currentGrow);
//			mTvGrow.setText("当前成长值："+mGrades.getGrow_val());
			mTvMax.setText(String.valueOf(max));
			Drawable drawable= getResources().getDrawable(levelLogo);
			/// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			mTvLevelName.setCompoundDrawablePadding(DeviceInfo.dp2px(mContext, 5));
			mTvLevelName.setCompoundDrawables(null,drawable,null,null);
		}
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.member_change_record://会员变动记录
				intent.setClass(mContext, MemberChangeRecordActivity.class);
				break;
			case R.id.member_viewright://查看会员特权
				intent.setClass(mContext, MemberPrivilegeActivity.class);
				break;
			case R.id.member_growth://用户成长值
				intent.setClass(mContext, GrowthRecordActivity.class);
				break;

			default:
				break;
			}
			mContext.startActivity(intent);
		}
	};

}
