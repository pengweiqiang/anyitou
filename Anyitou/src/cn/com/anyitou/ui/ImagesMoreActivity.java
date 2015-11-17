package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DetailImagesAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.commons.AppManager;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseActivity;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.ActionBar;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonArray;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;

/**
 * 相关资料图片--》更多
 * 
 * @author will
 * 
 */
public class ImagesMoreActivity extends BaseActivity{
	private ActionBar mActionBar;

	View view;
	private LoadingDialog loadingDialog;

	private List<Urls> imageLists = new ArrayList<Urls>();
	DetailImagesAdapter adapter;
	int page = 1;

	private GridView mGridView;
	private View mViewEmpty;
	private TextView mViewEmptyTip;
	
	
	String id = "";//项目投资id
	String category = "";//投资类型
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_image_more);
		super.onCreate(savedInstanceState);
		id = this.getIntent().getStringExtra("id");
		category = this.getIntent().getStringExtra("category");
		
		adapter =  new DetailImagesAdapter(imageLists, mContext);
		mGridView.setAdapter(adapter);
		initData();
	}

	@Override
	public void initView() {
		mActionBar = (ActionBar) findViewById(R.id.actionBar);
		onConfigureActionBar(mActionBar);
		mGridView = (GridView) findViewById(R.id.gridView);
		mViewEmpty = findViewById(R.id.empty_listview);
		mViewEmptyTip = (TextView) findViewById(R.id.xlistview_empty_tip);
		
	}

	protected void onConfigureActionBar(final ActionBar actionBar) {
		actionBar.setTitle("相关资料");
		actionBar.setLeftActionButton(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						AppManager.getAppManager().finishActivity();
					}
				});

	}
	
	private void initData() {
		loadingDialog = new LoadingDialog(mContext);
		loadingDialog.show();
		mViewEmpty.setVisibility(View.GONE);
		
		//attachment：相关资料图片
		ApiInvestUtils.contentShow(mContext, id,"attachment", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				loadingDialog.cancel();
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data != null){
						JsonElement list = data.get("list");
						if(list != JsonNull.INSTANCE){
							if(list.isJsonObject() && list.getAsJsonObject().has(category)){
								JsonArray defaultArray = list.getAsJsonObject().get(category).getAsJsonArray();
								if(defaultArray.size() !=0){
									List<Urls> urls = getImageUrls(defaultArray);
									imageLists.addAll(urls);
									adapter.notifyDataSetChanged();
								}else{
									mViewEmptyTip.setText("暂无图片");
									mViewEmpty.setVisibility(View.VISIBLE);
								}
							}
						}
					}
				}else{
					ToastUtils.showToast(mContext, StringUtils.isEmpty(parseModel.getMsg())?"获取图片失败":parseModel.getMsg());
				}
			}
		
		});

	}
	
	private List<Urls> getImageUrls(JsonArray arrays){
		List<Urls> imageLists = new ArrayList<Urls>();
		for (int i = 0; i < arrays.size(); i++) {
			JsonObject urlJson = arrays.get(i).getAsJsonObject();
			Urls url = new Urls();
			url.setUrl(urlJson.get("url").getAsString());
			imageLists.add(url);
		}
		return imageLists;
	}
	
	

	@Override
	public void initListener() {
		mActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity();
			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,ImageViewActivity.class);
				intent.putExtra("url", imageLists.get(position).getUrl());
				intent.putExtra("name", "图片详情");
				startActivity(intent);
			}
			
		});
	}


	

}
