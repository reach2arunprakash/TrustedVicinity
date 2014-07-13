package com.startup.sharing_vicinity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPostsListAdapter extends BaseAdapter{

	int row;
	Context ctx;

	public MyPostsListAdapter(Context c, int textViewResourceId) {
		super();
		this.row = textViewResourceId;
		this.ctx = c;
	}

	@Override
	public int getCount() {
		return MyPostsActivity.myPostsInfoList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return MyPostsActivity.myPostsInfoList.get(position);
	}

	public class ViewHolder {
		public TextView message;
		public TextView type;
		public TextView tag;
	}

	public String getDate(String s){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");  
		String out = "";
		try {  
			Date date = format.parse(s); 
			if(date.before(Calendar.getInstance().getTime())){
				out += "Released ";
			}
			else{
				out += DateFormat.format("MMM dd, yyyy", date).toString()+" ";
			}
		}
		catch(Exception e){
		}
		return out;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = (LinearLayout)inflater.inflate(row, null);
			holder = new ViewHolder();
			view.setTag(holder);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}

		holder.message = (TextView) view.findViewById(R.id.myposts_description);
		holder.message.setText(MyPostsActivity.myPostsInfoList.get(position).getMessage());

		holder.type = (TextView) view.findViewById(R.id.mypost_type);
		holder.type.setText(MyPostsActivity.myPostsInfoList.get(position).getType());

		holder.tag = (TextView) view.findViewById(R.id.mypost_tag);
		holder.tag.setText(MyPostsActivity.myPostsInfoList.get(position).getTag());

		if(MainPageActivity.showAnimation){
			Animation animation;
			if(position%2==0)
				animation = AnimationUtils.loadAnimation(ctx, R.anim.left_to_right);
			else
				animation = AnimationUtils.loadAnimation(ctx, R.anim.right_to_left);

			view.startAnimation(animation);
		}
		return view;
	}

}
