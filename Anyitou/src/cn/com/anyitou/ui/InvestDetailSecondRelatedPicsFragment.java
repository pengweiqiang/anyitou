package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.DetailImagesAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.JsonUtils;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.LoadingDialog;
import cn.com.gson.JsonArray;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
import cn.com.gson.reflect.TypeToken;
/**
 * 相关资料
 * @author will
 *
 */
public class InvestDetailSecondRelatedPicsFragment extends BaseFragment {

	View infoView;
	String id = "";
	
	
//	private GridView mGridView;
	private Gallery mGallery;
	List<Urls> imageLists;
	DetailImagesAdapter detailImagesApdater;
	
	
//	private View mBtnMoreImage;
	private View mBtnLeft,mBtnRight;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.related_pics,
				container, false);
		id = this.getArguments().getString("id");
		initView();
		initListener();
		
		return infoView;
	}
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	
	private void initView(){
//		mGridView = (GridView)infoView.findViewById(R.id.gridView);
		mBtnLeft = infoView.findViewById(R.id.left_image);
		mBtnRight = infoView.findViewById(R.id.right_image);
		mGallery = (Gallery)infoView.findViewById(R.id.gallery);
	}

	private void initListener() {
		mBtnLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curPosition = mGallery.getSelectedItemPosition();
				if(curPosition>1){
					mGallery.setSelection(curPosition-1);
				}
			}
		});
		mBtnRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int curPosition = mGallery.getSelectedItemPosition();
				if(curPosition<imageLists.size()-2){
					mGallery.setSelection(curPosition+1);
				}
			}
		});
		mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
//				ToastUtils.showToast(mActivity, "position:"+position);
				if(position <= 1){
					((ImageView)mBtnLeft).setImageDrawable(getResources().getDrawable(R.drawable.left_btn_over_icon));
				}else if(position>=imageLists.size()-2){
					((ImageView)mBtnRight).setImageDrawable(getResources().getDrawable(R.drawable.right_btn_over_icon));
				}else{
					((ImageView)mBtnLeft).setImageDrawable(getResources().getDrawable(R.drawable.left_btn_icon));
					((ImageView)mBtnRight).setImageDrawable(getResources().getDrawable(R.drawable.right_btn_icon));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mActivity,ImageViewActivity.class);
				intent.putExtra("url", imageLists.get(position).getUrl());
				intent.putParcelableArrayListExtra("urls", (ArrayList<? extends Parcelable>) imageLists);
				intent.putExtra("position", position);
				intent.putExtra("name", "图片详情");
				startActivity(intent);
			}
			
		});

	}
	
	private void initData(){
		
		//attachment：相关资料图片
		ApiInvestUtils.contentShow(mActivity, id,"attachment", new RequestCallback() {
			@Override
			public void execute(ParseModel parseModel) {
				
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					JsonObject data = parseModel.getData().getAsJsonObject();
					if(data != null){
						JsonElement list = data.get("list");
						if(list!=null && list != JsonNull.INSTANCE){
							if(list.isJsonObject() && list.getAsJsonObject().has("default")){
								JsonArray defaultArray = list.getAsJsonObject().get("default").getAsJsonArray();
								if(defaultArray.size() !=0){
									imageLists = (List<Urls>) JsonUtils
											.fromJson(list.getAsJsonObject().get("default").toString(),
													new TypeToken<List<Urls>>() {
													});
								}
							}
						}
					}
				}else{
					ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取项目详情失败，请稍后重试":parseModel.getMsg());
				}
				showInvestImages();
			}
		
		});
		
	}
	
	
	/**
	 * 相关资料图片列表
	 */
	private void showInvestImages(){
		if(imageLists==null || imageLists.isEmpty()){
			infoView.findViewById(R.id.image_rl).setVisibility(View.GONE);
			return;
		}
		detailImagesApdater = new DetailImagesAdapter(imageLists, mActivity);
//		mGridView.setAdapter(detailImagesApdater);
		mGallery.setAdapter(detailImagesApdater);
		if(imageLists.size()>2){
			mGallery.setSelection(1);
		}
		detailImagesApdater.notifyDataSetChanged();
	}
	
	

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	
}
