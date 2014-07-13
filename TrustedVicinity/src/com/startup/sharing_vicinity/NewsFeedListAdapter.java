package com.startup.sharing_vicinity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsFeedListAdapter extends BaseAdapter{

	int row;
	Bitmap notfoundBitmap;
	Context ctx;

	public NewsFeedListAdapter(Context c, int textViewResourceId) {
		super();
		this.row = textViewResourceId;
		this.ctx = c;
		notfoundBitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.notfound);
	}

	@Override
	public int getCount() {
		return MainPageActivity.newsItemInfoList.size();
	}


	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return MainPageActivity.newsItemInfoList.get(position);
	}

	public class ViewHolder {
		public TextView message;
		public ImageView image;
		public TextView timeSinceCreated;
		public TextView name;
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
			ThumbnailDownloader thumbnailDownloader = (ThumbnailDownloader) holder.image.getTag(R.id.image);
			if(thumbnailDownloader!=null){
				thumbnailDownloader.cancel(true);
			}
		}

		holder.message = (TextView) view.findViewById(R.id.message);
		holder.message.setText(MainPageActivity.newsItemInfoList.get(position).getMessage());

		holder.name = (TextView) view.findViewById(R.id.name);
		holder.name.setText(MainPageActivity.newsItemInfoList.get(position).getName());

		holder.timeSinceCreated = (TextView) view.findViewById(R.id.timeSinceCreated);
		Date timeCreatedAt = MainPageActivity.newsItemInfoList.get(position).getDateCreated();
		holder.timeSinceCreated.setText(getFormattedDate(timeCreatedAt));

		holder.image = (ImageView) view.findViewById(R.id.image);

		ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(ctx,holder.image);

		try{
			thumbnailDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,MainPageActivity.newsItemInfoList.get(position).getImageUrl());
		}
		catch(Exception e){
			if(thumbnailDownloader!=null)
				thumbnailDownloader.cancel(true);
		}

		holder.image.setTag(R.id.image, thumbnailDownloader);
		holder.image.setImageBitmap(notfoundBitmap);

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

	private String getFormattedDate(Date timeCreatedAt) {
		long diff = new Date().getTime()-timeCreatedAt.getTime();
		long diffDays = diff/(1000*60*60*24);
		long diffHours = diff/(60*60*1000)%24;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		if(diffDays==0 && diffHours==0 && diffMinutes==0 && diffSeconds==0){
			return("Posted 1 second ago");
		}
		else if(diffDays==0 && diffHours==0 && diffMinutes==0){
			return("Posted "+diffSeconds +" seconds ago");
		}
		else if(diffDays==0 && diffHours==0){
			return("Posted "+diffMinutes + " minutes ago");
		}
		else if(diffDays==0){
			return ("Posted "+diffHours +" hours ago");
		}
		else{
			return("Posted "+diffDays +" days ago");
		}
	}

}
