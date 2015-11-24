package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.MessagesAdapter;
import cn.com.anyitou.api.ApiMessageUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.Message;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.anyitou.views.XListView;
import cn.com.anyitou.views.XListView.IXListViewListener;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 消息
 * 
 * @author will
 * 
 */
public class MessagesActivity extends BaseActivity implements
		IXListViewListener {
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<Message> messageLists = new ArrayList<Message>();
	MessagesAdapter adapter;
	int page = 1;

	private XListView mListView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_message);
		super.onCreate(savedInstanceState);

		adapter = new MessagesAdapter(messageLists, mContext);

		mListView.setAdapter(adapter);
		initData();
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mListView = (XListView) findViewById(R.id.listView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) findViewById(R.id.xlistview_empty_tip);

		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
	}

	protected void onConfigureActionBar(final ActionBar actionBar) {
		actionBar.setTitle("消息");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});

	}

	private void initData() {
		if (page == 1) {
			loadingDialog = new LoadingDialog(mContext);
			loadingDialog.show();
		} else if(page == 0){
			page++;
		}
		mViewEmpty.setVisibility(View.GONE);
		ApiMessageUtils.getMessageList(mContext,"",String.valueOf(page),
				new RequestCallback() {

					@Override
					public void execute(ParseModel parseModel) {
						loadingDialog.cancelDialog(loadingDialog);
						
						if (ApiConstants.RESULT_SUCCESS.equals(parseModel
								.getCode())) {
							JsonObject data = parseModel.getData().getAsJsonObject();
							if(data != null){
								JsonElement list = data.get("list");
								if(list!=null && list != JsonNull.INSTANCE){
									List<Message> messages = JsonUtils.fromJson(
											list.toString(),
											new TypeToken<List<Message>>() {
											});
									showEmptyListView(messages);
									mListView.onLoadFinish(page, messages.size(),
											"加载完毕");
									adapter.notifyDataSetChanged();
								}else{
									mListView.onLoadFinish(page, 0,
											"加载完毕");
									showEmptyListView(null);
								}
							}else{
								showEmptyListView(null);
							}
						} else {
							showEmptyListView(null);
							ToastUtils.showToast(mContext, parseModel.getMsg());
							mListView.onLoadFinish(page, 0, "");
						}

					}
				});

	}
	
	private void showEmptyListView(List list){
		boolean isEmpty =false;
		if(list == null || list.isEmpty()){
			isEmpty = true;
		}
		if(page == 1){
			messageLists.clear();
			if(isEmpty){
				mViewEmpty.setVisibility(View.VISIBLE);
				mViewEmptyTip.setText("暂无消息");
			}else{
				messageLists.addAll(list);
				mViewEmpty.setVisibility(View.GONE);
			}
		}else{
			if(!isEmpty){
				messageLists.addAll(list);
			}
		}
		
	}

	@Override
	public void initListener() {
		mActionBar.setLeftActionButton(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,MessageDetailActivity.class);
				intent.putExtra("message", messageLists.get(position-1));
				startActivity(intent);
			}
		});
	}




	@Override
	public void onRefresh() {
		page = 0;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}

}
