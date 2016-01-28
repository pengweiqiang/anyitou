package cn.com.anyitou.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.com.anyitou.R;
import cn.com.anyitou.adapters.CheDaiFangDaiDetailImagesAdapter;
import cn.com.anyitou.api.ApiInvestUtils;
import cn.com.anyitou.api.constant.ApiConstants;
import cn.com.anyitou.entity.ParseModel;
import cn.com.anyitou.entity.Urls;
import cn.com.anyitou.ui.base.BaseFragment;
import cn.com.anyitou.utils.HttpConnectionUtil.RequestCallback;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.utils.ToastUtils;
import cn.com.anyitou.views.MyListView;
import cn.com.gson.JsonArray;
import cn.com.gson.JsonElement;
import cn.com.gson.JsonNull;
import cn.com.gson.JsonObject;
/**
 * 相关资料-->车贷
 * @author will
 *
 */
@SuppressWarnings("deprecation")
public class InvestDetailSecondRelatedPicsCheDaiFragment extends BaseFragment {

	View infoView;
	String id = "";
	
	
	private MyListView mListView;
	
//	private View mBtnLeft,mBtnRight;
	
	private List<String[]> titles;
	private Map<String,List<Urls>> images;
	
	private CheDaiFangDaiDetailImagesAdapter adapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoView = inflater.inflate(R.layout.related_pics_chedai,
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
		mListView = (MyListView)infoView.findViewById(R.id.pic_listView);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(false);
	}

	private void initListener() {

	}
	
	private void initData(){
		
		//attachment：相关资料图片
		ApiInvestUtils.contentShow(mActivity, id,"attachment", new RequestCallback() {
			
			@Override
			public void execute(ParseModel parseModel) {
				if(ApiConstants.RESULT_SUCCESS.equals(parseModel.getCode())){
					if(parseModel.getData().isJsonObject()){
						JsonObject data = parseModel.getData().getAsJsonObject();
						json2Image(data);
					}else{
						ToastUtils.showToast(mActivity, StringUtils.isEmpty(parseModel.getMsg())?"获取相关资料失败，请稍后重试":parseModel.getMsg());
					}
				}
			}
		});
	}
	// private List<Investment> getInvests(ParseModel parseModel) {
		// List<Investment> investments = new ArrayList<Investment>();
		// if (parseModel.getData().isJsonObject()) {
		// JsonObject investmentJsonObject = parseModel.getData()
		// .getAsJsonObject();
		// Set<Entry<String, JsonElement>> sets = investmentJsonObject
		// .entrySet();
		// Iterator<Entry<String, JsonElement>> keys = sets.iterator();
		// while (keys.hasNext()) {
		// Entry<String, JsonElement> entry = keys.next();
		// Investment investment = JsonUtils.fromJson(entry.getValue()
		// .toString(), Investment.class);
		// if (investment != null) {
		// investments.add(investment);
		// }
		// }
		// }
		// return investments;
		// }
	private void json2Image(JsonObject data){
		if(data != null){
			//types
			JsonElement types = data.get("types");
			if(types!=null && types.isJsonObject()){
				titles = new ArrayList<String[]>();
				Set<Entry<String, JsonElement>> setTypes = types.getAsJsonObject().entrySet();
				Iterator<Entry<String,JsonElement>> iterator = setTypes.iterator();
				while(iterator.hasNext()){
					Entry<String,JsonElement> entry = iterator.next();
					String []titleArray = new String[2];
					titleArray[0]=entry.getKey();
					titleArray[1] = entry.getValue().getAsJsonObject().get("name").getAsString();
					titles.add(titleArray);
				}
			}
			//list
			JsonElement list = data.get("list");
			if(list!=null && list != JsonNull.INSTANCE && list.isJsonObject()){
				images = new HashMap<String, List<Urls>>();
				Set<Entry<String,JsonElement>> setList = list.getAsJsonObject().entrySet();
				Iterator<Entry<String,JsonElement>> iterator = setList.iterator();
				while(iterator.hasNext()){
					Entry<String,JsonElement> entry = iterator.next();
					JsonElement jsonElementUrl = entry.getValue();
					JsonArray jsonArray = jsonElementUrl.getAsJsonArray();
					List<Urls> urlsList = new ArrayList<Urls>();
					for(int i =0;i<jsonArray.size();i++){
						JsonElement jsonElement = jsonArray.get(i);
						String url = jsonElement.getAsJsonObject().get("url").getAsString();
						String name = jsonElement.getAsJsonObject().get("name").getAsString();
						Urls  urls = new Urls();
						urls.setName(name);
						urls.setUrl(url);
						urlsList.add(urls);
					}
					images.put(entry.getKey(), urlsList);
				}
				
			}
		}
		if(images!=null && !images.isEmpty()){
			adapter = new CheDaiFangDaiDetailImagesAdapter(titles, images, mActivity);
			mListView.setAdapter(adapter);
//			setListViewHeightBasedOnChildren(mListView);
		}
	}
	
	
	/**
	 * 相关资料图片列表
	 */
//	private void showInvestImages(){
//		if(imageLists==null || imageLists.isEmpty()){
//			infoView.findViewById(R.id.image_rl).setVisibility(View.GONE);
//			return;
//		}
//		detailImagesApdater = new DetailImagesAdapter(imageLists, mActivity);
////		mGridView.setAdapter(detailImagesApdater);
//		mGallery.setAdapter(detailImagesApdater);
//		mGallery.setSelection(1);
//		detailImagesApdater.notifyDataSetChanged();
//	}
	
	

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	
}
