package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.adapters.MyVerticalPagerAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.InVestDetail;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseFragmentActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.VerticalViewPager;
/**
 * 项目详情
 * @author will
 *
 */
@SuppressLint("NewApi") public class InVestmentDetailActivity extends BaseFragmentActivity {
	
	ActionBar mActionBar;
	
	LoadingDialog loadingDialog;
	private VerticalViewPager mVerticalViewPager;
	private List<Fragment> fragmentLists;
	String id = "";//项目详细id
	private InVestDetail investDetail;
	private View mBtnBottom;
	private TextView mTvBottom;
	InvestDetailFirstFragment investDetailFirstFragment;
	InvestDetailSecondFragment investDetailSecondFragment;
	private String status = "";
	private String statusDesc = "";
	private String rate = "";
	private String day = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.invertment_detail);
		super.onCreate(savedInstanceState);
		
		
		
		
		id = this.getIntent().getStringExtra("id");
		status = this.getIntent().getStringExtra("status");
		statusDesc = this.getIntent().getStringExtra("statusDesc");
		rate = this.getIntent().getStringExtra("rate");
		day = this.getIntent().getStringExtra("day");
		
		initView();
		initData();
		initListener();
	}
	
	private void initListener(){
		mBtnBottom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isLogined()){
					ToastUtils.showToast(InVestmentDetailActivity.this, "请先登录");
					Intent intent = new Intent(InVestmentDetailActivity.this,LoginActivity.class);
					startActivity(intent);
					return;
				}
				if(!"1".equals(application.getCurrentUser().getIshfuser())){
					ToastUtils.showToast(InVestmentDetailActivity.this, "尚未开通汇付，2秒后前往注册汇付界面");
					new Handler().postDelayed(new Runnable() {
						public void run() {
							Intent intent = new Intent(InVestmentDetailActivity.this,RegisteredActivity.class);
							startActivity(intent);
						} 
					}, 2000);
					return;
				}
//				Intent intent = new Intent(InVestmentDetailActivity.this,InvestConfirmActivity.class);
//				intent.putExtra("rate", rate);
//				intent.putExtra("day", day);
//				intent.putExtra("id", id);
//				startActivity(intent);
			}
		});
	}
	private void initData() {
		if(StringUtils.isEmpty(id)){
			ToastUtils.showToast(this, "获取项目详情失败，请稍后重试");
			return;
		}
		loadingDialog = new LoadingDialog(this);
		loadingDialog.show();
		
		ApiInvestUtils.contentShow(this, id,"base", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					investDetail = JsonUtils.fromJson(parseModel.getData().toString(), InVestDetail.class);
					setData();
				}else{
					ToastUtils.showToast(InVestmentDetailActivity.this, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
			}
		});
	}
	private void setData(){
		if(investDetail!=null){
			investDetailFirstFragment.setFirstPageList(investDetail.getFirstpage());
			investDetailSecondFragment.setSecondeList(investDetail.getSecondpage());
		}
	}
	private void initView() {
		
		mVerticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);
		mActionBar = (ActionBar)findViewById(R.id.actionBar);
		mActionBar.setTitle("项目详情");
		mActionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
//		mActionBar.hideLeftActionButtonText();
		mBtnBottom = findViewById(R.id.bottom_view);
		mTvBottom = (TextView)findViewById(R.id.bottom_textView);
		if(!"2".equals(status)){//2代表可以投资赚钱
			mBtnBottom.setClickable(false);
			mBtnBottom.setEnabled(false);
			mTvBottom.setText(statusDesc);
			mBtnBottom.setBackgroundColor(getResources().getColor(R.color.invest_detail_bottom_color_enable));
		}else{
			mTvBottom.setText("投资赚钱");
		}
		
		
		
		fragmentLists = new ArrayList<Fragment>();
		investDetailFirstFragment = new InvestDetailFirstFragment();
		
		investDetailSecondFragment = new InvestDetailSecondFragment();
		
		fragmentLists.add(investDetailFirstFragment);
		fragmentLists.add(investDetailSecondFragment);
		MyVerticalPagerAdapter fragmentAdapter = new MyVerticalPagerAdapter(getSupportFragmentManager(), fragmentLists);
		mVerticalViewPager.setAdapter(fragmentAdapter);
		
	}


}
