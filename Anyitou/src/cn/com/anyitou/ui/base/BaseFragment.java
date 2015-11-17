package cn.com.anyitou.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

/**
 * 所有 Fragment 的基类
 * 
 * @author Will
 *
 */
public abstract class BaseFragment extends Fragment {

	protected static final int FILL_PARENT = -1;
	protected static final int WRAP_CONTENT = -2;

	protected Activity mActivity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (Activity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart(this.getClass().getName());
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd(this.getClass().getName());
	}
	
	/**
	 * 展示空白页面
	 * @param view
	 * @param tipStr
	 * @param goStr
	 * @param imageId
	 * @param emptyClick
	 */
	/*public void showEmpty(View view,int tipId,int goId,int imageId,OnClickListener emptyClick){
		ImageView imageView = (ImageView)view.findViewById(R.id.empty_imageView);
		imageView.setImageDrawable(this.getResources().getDrawable(imageId));
		TextView textTip = (TextView)view.findViewById(R.id.empty_text_tip);
		textTip.setText(getResources().getString(tipId));
		TextView textGo = (TextView)view.findViewById(R.id.empty_text_go);
		if(goId == 0){
			textGo.setVisibility(View.GONE);
		}else{
			textGo.setText(getResources().getString(goId));
		}
		textGo.setOnClickListener(emptyClick);
	}*/
	
	public void setListViewHeightBasedOnChildren(ListView listView) {

		  ListAdapter listAdapter = listView.getAdapter();

		  if (listAdapter == null) {
			  return;
		  }

		  int totalHeight = 0;

		  for (int i = 0; i < listAdapter.getCount(); i++) {
		   View listItem = listAdapter.getView(i, null, listView);
		   listItem.measure(0, 0);
//		   listItem.measure(  
//	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),  
//	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)); 
//		   totalHeight += listItem.getMeasuredHeightAndState();
		   totalHeight += listItem.getMeasuredHeight();
		  }

		  ViewGroup.LayoutParams params = listView.getLayoutParams();

		  params.height = totalHeight
		    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

//		  ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

		  listView.setLayoutParams(params);
		 }
}
