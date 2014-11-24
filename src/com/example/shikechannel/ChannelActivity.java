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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ChannelActivity extends Activity {

	private ListView listView;
	private List<Map<String, Object>> listItems;
	private ChannelListViewAdapter channelListViewAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		listView = (ListView) findViewById(R.id.channel_ListView);
		listItems = getListItems();
		channelListViewAdapter = new ChannelListViewAdapter(this, listItems);
        //添加listView的头部
		View headView =LayoutInflater.from(this).inflate(R.layout.channel_themes, null);
		listView.addHeaderView(headView);
		listView.setAdapter(channelListViewAdapter);
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
		//Toast.makeText(null, ACTIVITY_SERVICE, duration)
		//ChannelActivity.this.finish();
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
