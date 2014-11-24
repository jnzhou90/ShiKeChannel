package com.example.shikechannel;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ChannelListViewAdapter extends BaseAdapter {

	private List<Map<String, Object>> listItems;
	private LayoutInflater listContainer;
	private Context context;

	final class ViewHolder {// 自定义控件集合
		public ImageView userHeadImg;
		public TextView userNameText;
		public TextView releaseTime;
		public TextView content;
		public TextView remindTime;
		public TextView fansCount;
		public TextView recordCount;

		ViewHolder(View convertView) {
			userHeadImg = (ImageView) convertView.findViewById(R.id.user_pic);
		}
	}

	public ChannelListViewAdapter(Context context,
			List<Map<String, Object>> listItems) {

		this.context = context;
		listContainer = LayoutInflater.from(context);// 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * LisView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup Parent) {
		// TODO Auto-generated method stub
		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			// 获取activity_channel的布局文件
			convertView = listContainer
					.inflate(R.layout.activity_channel, null);
			holder = new ViewHolder(convertView);

			// 获取控件
			holder.userHeadImg = (ImageView) convertView
					.findViewById(R.id.user_pic);
			holder.userNameText = (TextView) convertView
					.findViewById(R.id.user_nickname);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.fansCount = (TextView) convertView
					.findViewById(R.id.fans_count);
			holder.recordCount = (TextView) convertView
					.findViewById(R.id.record_count);
			holder.releaseTime = (TextView) convertView
					.findViewById(R.id.release_time);
			holder.remindTime = (TextView) convertView
					.findViewById(R.id.remind_time);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置文字和图片
		holder.userHeadImg.setImageBitmap(getHome((String) listItems.get(
				position).get("img")));
		;

		holder.userNameText.setText((String) listItems.get(position).get(
				"user_nickname"));
		holder.content.setText((String) listItems.get(position).get("content"));
		holder.fansCount.setText((String) listItems.get(position).get(
				"fans_count"));
		holder.recordCount.setText((String) listItems.get(position).get(
				"record_count"));
		holder.releaseTime.setText((String) listItems.get(position).get(
				"release_time"));
		holder.remindTime.setText((String) listItems.get(position).get(
				"remind_time"));

		return convertView;
	}

	private Bitmap getHome(String string) {
		// TODO Auto-generated method stub
		return null;
	}

		

}
