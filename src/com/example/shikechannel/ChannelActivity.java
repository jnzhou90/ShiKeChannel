package com.example.shikechannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ChannelActivity extends Activity {

	private ListView listView;
	private List<Map<String, Object>> listItems; // 显示的列表对应的字符串
	private ChannelListViewAdapter channelListViewAdapter; // ListView的适配器
	private PullToRefreshListView mPullRefreshListView;// PullToRefreshListView实例

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化mPullRefreshListView并设置监听器，以执行当需要刷新时，
		// 应该怎么办，至于真正执行刷新的类GetDataTask（）

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_listview);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
				});
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						Toast.makeText(ChannelActivity.this, "已经到最后了",
								Toast.LENGTH_SHORT).show();
					}
				});
		View headView = LayoutInflater.from(this).inflate(
				R.layout.channel_themes, null);
		listItems = getListItems();
		channelListViewAdapter = new ChannelListViewAdapter(this, listItems);
		// listView = (ListView) findViewById(R.id.channel_ListView);
		listView = mPullRefreshListView.getRefreshableView();
		listView.setAdapter(channelListViewAdapter);

		// 添加listView的头
		listView.addHeaderView(headView);

	}

	/**
	 * 点击添加“+”号按钮的事件
	 * 
	 * @param view
	 */
	public void clickNewRecord(View view) {
		Toast.makeText(ChannelActivity.this, "亲，可以添加信息了", 200).show();
	}

	/**
	 * 点击返回图片按钮后返回的信息
	 * 
	 * @param view
	 */
	public void clickBack(View view) {
		Toast.makeText(ChannelActivity.this, "亲， 这是返回键", 1000).show();
		// Toast.makeText(null, ACTIVITY_SERVICE, duration)
		// ChannelActivity.this.finish();
	}

	/**
	 * 点击图标出现详情
	 * 
	 * @param view
	 */
	public void clickChannelDetail(View view) {

		// String channelDetailTVStr =channelDetailTV.getText().toString();

		Toast.makeText(ChannelActivity.this, "出现详情", 1000).show();
		Bundle channelBundle = new Bundle();
		channelBundle.putString("channelDetail", "学霸养成记详情！！！！！ ");// TODO yao
																	// 要进一步修改成
		Intent channelIntent = new Intent();
		channelIntent.setClass(ChannelActivity.this,
				ChannelDetailActivity.class);
		channelIntent.putExtras(channelBundle);
		startActivity(channelIntent);

	}

	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream inputStream;
		try {
			inputStream = this.getAssets().open("Channel_pic.txt");
			String json = readTextFile(inputStream);

			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				map = new HashMap<String, Object>();
				map.put("user_pic", array.getJSONObject(i)
						.getString("user_pic"));
				map.put("user_nickname",
						array.getJSONObject(i).getString("user_nickname"));
				map.put("release_time",
						array.getJSONObject(i).getString("release_time"));
				map.put("content", array.getJSONObject(i).getString("content"));
				map.put("remind_time",
						array.getJSONObject(i).getString("remind_time"));
				map.put("fans_count",
						array.getJSONObject(i).getString("fans_count"));
				map.put("record_count",
						array.getJSONObject(i).getString("record_count"));
				list.add(map);
			}
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	// //工具类
	/**
	 * 
	 * @param inputStream
	 * @return
	 */
	public String readTextFile(InputStream inputStream) {
		String readedStr = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				readedStr += tmp;
			}
			br.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return readedStr;
	}

	/*
	 * private class GetDataTask extends AsyncTask<Void, Void, HashMap<String,
	 * Object>> {
	 * 
	 * @Override protected HashMap<String, Object> doInBackground(Void...
	 * params) { // TODO Auto-generated method stub // Simulates a background
	 * job. try { Thread.sleep(1000); } catch (InterruptedException e) { }
	 * HashMap<String, Object> map = new HashMap<String, Object>(); try {
	 * 
	 * map = new HashMap<String, Object>(); map.put("user_pic",
	 * R.drawable.annan); map.put("user_nickname", "下拉后更新后的名字");
	 * map.put("release_time", "30秒前"); map.put("content", "下拉后更新后的内容");
	 * map.put("remind_time", "下拉刷新后的时间"); map.put("fans_count", "1000");
	 * map.put("record_count", "16754"); } catch (Exception e) { // TODO
	 * Auto-generated catch block setTitle("map出错了"); }
	 * 
	 * return map; }
	 */

	private class GetDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>>{

		protected List<Map<String, Object>> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			InputStream inputStreamNew;
			try {
				//inputStreamNew = this.open("Channel_pic_new.txt");
				inputStreamNew = getAssets().open("Channel_pic_new.txt");  
				String json = readTextFile(inputStreamNew);

				JSONArray array = new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					map = new HashMap<String, Object>();
					map.put("user_pic", array.getJSONObject(i)
							.getString("user_pic"));
					map.put("user_nickname",
							array.getJSONObject(i).getString("user_nickname"));
					map.put("release_time",
							array.getJSONObject(i).getString("release_time"));
					map.put("content", array.getJSONObject(i).getString("content"));
					map.put("remind_time",
							array.getJSONObject(i).getString("remind_time"));
					map.put("fans_count",
							array.getJSONObject(i).getString("fans_count"));
					map.put("record_count",
							array.getJSONObject(i).getString("record_count"));
					list.add(map);
				}
				return list;

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return list;
		}


		// 这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			// 在头部增加新添内容
			listItems.addAll(0, result);		
			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			channelListViewAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);// 这句是必有的，AsyncTask规定的格式
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.channel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
