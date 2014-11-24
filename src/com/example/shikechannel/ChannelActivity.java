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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ChannelActivity extends Activity {

	private ListView listView;
	private List<Map<String, Object>> listItems;
	private ChannelListViewAdapter channelListViewAdapter;

	// private Integer[] user_pic = { R.drawable.kaibiedetanke,
	// R.drawable.sunjin,
	// R.drawable.annan, R.drawable.ic_launcher };
	// private String[] user_nickname = { "开贝塔的坦克", "孙锦", "安南", "周江南1" };
	// private String[] release_time = { "1分钟前", "1分钟前", "1分钟前", "3分钟前" };
	// private String[] content = { "每天临睡觉前背单词！！", " 俞敏洪讲座", "每天临睡觉前背单词",
	// "每天早上读书学习" };
	// private String[] remind_time = { "每天  6:00", "每天  6:00", "每天  6:00",
	// "每天  6:00" };
	// private String[] fans_count = { "643人关注", "643人关注", "643人关注", "643人关注" };
	// private String[] record_count = { "1165条记录", "1165条记录", "1165条记录",
	// "1165条记录" };

	// private TextView channelDetailTV =
	// (TextView)findViewById(R.id.tv_channel_details);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_channel);
		setContentView(R.layout.activity_main);
		// final ImageButton newButton = (ImageButton)
		// findViewById(R.id.newrecord);

		listView = (ListView) findViewById(R.id.channel_ListView);
		listItems = getListItems();

		channelListViewAdapter = new ChannelListViewAdapter(this, listItems);

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

	/*
	 * private List<Map<String, Object>> getListItems() {
	 * 
	 * List<Map<String, Object>> listItems = new ArrayList<Map<String,
	 * Object>>();
	 * 
	 * for (int i = 0; i < user_nickname.length; i++) { Map<String, Object> map
	 * = new HashMap<String, Object>(); map.put("user_pic", user_pic[i]);
	 * map.put("user_nickname", user_nickname[i]); map.put("release_time",
	 * release_time[i]); map.put("content", content[i]); map.put("remind_time",
	 * remind_time[i]); map.put("fans_count", fans_count[i]);
	 * map.put("record_count", record_count[i]); listItems.add(map); } return
	 * listItems; }
	 */
	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream inputStream;
		try {
			inputStream = this.getAssets().open("Channel_pic.txt");
			String json = readTextFile(inputStream);
			//String jsonUTF8 = new String(json);
			//String jsonUTF8 = JsonFilter(json);
			
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
	
	
	
/*    public static String toUtf8(String str) { 
        try {
			return new String(str.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
  }*/
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
