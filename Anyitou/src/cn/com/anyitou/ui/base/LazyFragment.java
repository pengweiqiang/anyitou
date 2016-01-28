package cn.com.anyitou.ui.base;


/**
 * 懒加载数据的fragment
 * @author pengweiqiang
 *
 */
public abstract class LazyFragment extends BaseFragment{
	
	protected boolean isVisible;
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()){
			isVisible = true;
			onVisible();
		}else{
			isVisible = false;
			onInvisible();
		}
	}
	
	protected void onVisible(){
		lazyLoad();
	}
	
	protected abstract void lazyLoad();
	
	protected void onInvisible(){}

}
