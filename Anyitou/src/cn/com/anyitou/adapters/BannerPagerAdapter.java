package cn.com.anyitou.adapters;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.GlobalConfig;
import cn.com.anyitou.MyApplication;
import cn.com.anyitou.R;
import cn.com.anyitou.api.constant.Push2Page;
import cn.com.anyitou.api.constant.ReqUrls;
import cn.com.anyitou.entity.Banner;
import cn.com.anyitou.entity.DebtAssignment;
import cn.com.anyitou.entity.Investment;
import cn.com.anyitou.entity.User;
import cn.com.anyitou.ui.HomeActivity;
import cn.com.anyitou.ui.InVestmentDetailActivity;
import cn.com.anyitou.ui.LoginActivity;
import cn.com.anyitou.ui.MessagesActivity;
import cn.com.anyitou.ui.RegisteredAccountActivity;
import cn.com.anyitou.ui.WebActivity;
import cn.com.anyitou.ui.fragment.MyCouponFragment;
import cn.com.anyitou.utils.StringUtils;
import cn.com.anyitou.views.banner.InfinitePagerAdapter;
import cn.com.universalimageloader.core.ImageLoader;

public class BannerPagerAdapter extends InfinitePagerAdapter{

    private final LayoutInflater mInflater;
    private final Context mContext;

    public List<Banner> mList;

    private int height;
    public void setDataList(List<Banner> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    
    public BannerPagerAdapter(Context context,int height) {
        mContext=context;
        this.height = height;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.banner_item_viewpager, container, false);
    		LayoutParams viewParams = view.getLayoutParams();
    		viewParams.height = height;
    		view.setLayoutParams(viewParams);
            holder = new ViewHolder(view);
            view.setTag(holder);
            view.setOnClickListener(holder);
//            holder.image.setOnClickListener(holder);
        }
        Banner item = mList.get(position);
        holder.position = position;
//        holder.name.setText(item.getName());
//        holder.description.setText(item.getName()+"position:"+position);
        
        ImageLoader.getInstance().displayImage(
				item.getPic(),
				holder.image,
				MyApplication.getInstance()
						.getOptions(R.drawable.index_banner));
        return view;
    }

    

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    public class ViewHolder implements OnClickListener{
        public int position;
        TextView name;
        ImageView image;

        public ViewHolder(View view) {
//            name = (TextView) view.findViewById(R.id.item_name);
            image = (ImageView) view.findViewById(R.id.item_image);
        }

		@Override
		public void onClick(View v) {
			Banner banner = mList.get(position);
			Intent webIntent = new Intent(mContext,WebActivity.class);
			String url = banner.getAction();
			if(StringUtils.isEmpty(url)){
				return;
			}
			String type = banner.getType();//1链接，2事件
			if("1".equals(type)){
				if(StringUtils.isEmpty(GlobalConfig.ACCESS_TOKEN)){//用户授权token为空
					url = url +"?"+ReqUrls.ACCESS_TOKEN+"="+GlobalConfig.CLIENT_TOKEN;
				}else{
					url = url +"?"+ReqUrls.ACCESS_TOKEN+"="+GlobalConfig.ACCESS_TOKEN;
				}
				webIntent.putExtra("url", url);
				webIntent.putExtra("name", banner.getName());
				mContext.startActivity(webIntent);
			}else if("2".equals(type)){
				actionToPage(url);
			}
			
		}
    }
    //跳转到指定页面
    private void actionToPage(String toPageJson){
    	String action = "";
    	JSONObject json;
    	try {
			json = new JSONObject(toPageJson);
			action = json.getString("toPage");
			
			if(StringUtils.isEmpty(action)){
	    		return;
	    	}
			
	    	Intent i = new Intent();
	    	if(Push2Page.RegisterActivity.getToPage().equals(action)){//注册页面
	    		User user = MyApplication.getInstance().getCurrentUser();
	    		if(user!=null){
	    			i.setClass(mContext, HomeActivity.class);
					i.putExtra("checkIndex", 2);
				}else{
					i.setClass(mContext, RegisteredAccountActivity.class);
				}
	    		
	    	}if(Push2Page.MainActivity.getToPage().equalsIgnoreCase(action)){//首页
				i.setClass(mContext, HomeActivity.class);
			}else if(Push2Page.InvestmentDetailActivity.getToPage().equalsIgnoreCase(action)){//项目详情
				Investment invest = new Investment();
				String id = json.getString("id");
				invest.setId(id);
				i.putExtra("invest", invest);
				i.putExtra("type", 1);
				i.setClass(mContext, InVestmentDetailActivity.class);
			}else if(Push2Page.DebtDetailActivity.getToPage().equalsIgnoreCase(action)){//债权详情
				DebtAssignment debt = new DebtAssignment();
				String id = json.getString("id");
				debt.setId(id);
				i.putExtra("type", 2);
				i.putExtra("debt", debt);
				i.setClass(mContext, InVestmentDetailActivity.class);
			}else if(Push2Page.MessagesActivity.getToPage().equalsIgnoreCase(action)){//消息中心
				i.setClass(mContext, MessagesActivity.class);
			}else if(Push2Page.MyActivity.getToPage().equalsIgnoreCase(action)){//个人中心
				i.setClass(mContext, HomeActivity.class);
				i.putExtra("checkIndex", 2);
			}else if(Push2Page.MyCouponActivity.getToPage().equalsIgnoreCase(action)){//我的优惠券
				User user = MyApplication.getInstance().getCurrentUser();
				if(user!=null){
					i.setClass(mContext, MyCouponFragment.class);
				}else{
					i.setClass(mContext, LoginActivity.class);
				}
			}else if(Push2Page.ProjectActivity.getToPage().equalsIgnoreCase(action)){//项目列表
				i.setClass(mContext, HomeActivity.class);
				i.putExtra("checkIndex", 1);
			}
	    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
	    	mContext.startActivity(i);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
