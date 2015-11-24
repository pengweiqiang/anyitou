package cn.com.anyitou.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.anyitou.R;
import cn.com.anyitou.entity.MemberChangeRecords;
import cn.com.anyitou.entity.Message;


public class MessagesAdapter extends BaseListAdapter{

	private List<Message> messages;
	private Context context;
	private LayoutInflater inflater;
	
	public MessagesAdapter(List<Message> messages,Context context) {
		this.context = context;
		this.messages = messages;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(messages!=null){
			return messages.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Message message = messages.get(position);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.message_item, null);
			
			viewHolder.mTvDate = (TextView)convertView.findViewById(R.id.date);
			viewHolder.mTvTitle = (TextView)convertView.findViewById(R.id.title);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mTvDate.setText(message.getTime());
		
		viewHolder.mTvTitle.setText(message.getTitle());
		
		
		return convertView;
	}
	
	static final class ViewHolder{
		private TextView mTvDate;
		private TextView mTvTitle;
	}

	
}
