package cn.com.anyitou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.anyitou.R;

import cn.com.anyitou.api.ApiUserUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.BankCard;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.universalimageloader.core.ImageLoader;
/**
 * 我的银行卡
 * @author will
 *
 */
public class MyBankCardActivity extends BaseActivity {
	private ActionBar mActionBar;
	
	private LoadingDialog loadingDialog;
//	private BankCardAdapter bankCardAdapter;
	private BankCard bankCard ; 
//	private ListView mListView;
	private TextView mTvBankName,mTvBankCardNo;
	private ImageView mIvBankLogo;
	private View mViewMyCard;
	private View mEmpty;
	private TextView mEmptyTextView;
	private View mViewContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_bankcard);
		super.onCreate(savedInstanceState);
	
		initData();
	}
	
	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		
		mIvBankLogo = (ImageView)findViewById(R.id.bankImage);
		mTvBankName = (TextView)findViewById(R.id.bankNameText);
		mTvBankCardNo = (TextView)findViewById(R.id.cardNoText);
		mViewMyCard = findViewById(R.id.mycard);
		mViewContent = findViewById(R.id.content);
		mEmpty = findViewById(R.id.empty_tip);
		mEmptyTextView = (TextView)findViewById(R.id.xlistview_empty_tip);
		
//		mListView = (ListView)findViewById(R.id.listView);
	}
	private void setViewData(){
		if(bankCard!=null){
			mViewMyCard.setVisibility(View.VISIBLE);
			if(!StringUtils.isEmpty(bankCard.getBank_card_number())){
				mTvBankCardNo.setText("储蓄卡    **** **** **** "+bankCard.getBank_card_number().substring(bankCard.getBank_card_number().length()-4));
			}else{
				mTvBankCardNo.setText("储蓄卡    无");
			}
			mTvBankName.setText(bankCard.getBank_name());
//			ImageLoader.getInstance().displayImage(bankCard.getLogo(), mIvBankLogo);
		}
	}
	@Override
	public void initListener() {
		
	}
	private void initData(){
		
//		bankCards = new ArrayList<BankCard>();
//		bankCardAdapter = new BankCardAdapter(bankCards, mContext);
//		mListView.setAdapter(bankCardAdapter);
		
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.showDialog(loadingDialog);
		ApiUserUtils.getMyCard(mContext, new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancelDialog(loadingDialog);
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
//					logined(parseModel.getToken(), null);
					bankCard = JsonUtils.fromJson(parseModel.getData().toString(), BankCard.class);
					if(bankCard!=null){
						setViewData();
					}else{
						mViewContent.setVisibility(View.GONE);
						mEmptyTextView.setText("暂无绑定银行卡");
						mEmpty.setVisibility(View.VISIBLE);
					}
				}else{
					mViewContent.setVisibility(View.GONE);
					mEmptyTextView.setText("暂无绑定银行卡");
					mEmpty.setVisibility(View.VISIBLE);
					ToastUtils.showToast(mContext, parseModel.getMsg());
				}
			}
		});
	}

	protected void onConfigureActionBar(ActionBar actionBar) {
		actionBar.setTitle("我的银行卡");
		actionBar.setLeftActionButton(R.drawable.btn_back, new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				AppManager.getAppManager().finishActivity();
			}
		});
		
	}
}
