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
	private List<Map<String, Object>> listItems; // ��ʾ���б��Ӧ���ַ���
	private ChannelListViewAdapter channelListViewAdapter; // ListView��������
	private PullToRefreshListView mPullRefreshListView;// PullToRefreshListViewʵ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ��ʼ��mPullRefreshListView�����ü���������ִ�е���Ҫˢ��ʱ��
		// Ӧ����ô�죬��������ִ��ˢ�µ���GetDataTask����

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
						Toast.makeText(ChannelActivity.this, "�Ѿ��������",
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

		// ���listView��ͷ
		listView.addHeaderView(headView);

	}

	/**
	 * �����ӡ�+���Ű�ť���¼�
	 * 
	 * @param view
	 */
	public void clickNewRecord(View view) {
		Toast.makeText(ChannelActivity.this, "�ף����������Ϣ��", 200).show();
	}

	/**
	 * �������ͼƬ��ť�󷵻ص���Ϣ
	 * 
	 * @param view
	 */
	public void clickBack(View view) {
		Toast.makeText(ChannelActivity.this, "�ף� ���Ƿ��ؼ�", 1000).show();
		// Toast.makeText(null, ACTIVITY_SERVICE, duration)
		// ChannelActivity.this.finish();
	}

	/**
	 * ���ͼ���������
	 * 
	 * @param view
	 */
	public void clickChannelDetail(View view) {

		// String channelDetailTVStr =channelDetailTV.getText().toString();

		Toast.makeText(ChannelActivity.this, "��������", 1000).show();
		Bundle channelBundle = new Bundle();
		channelBundle.putString("channelDetail", "ѧ�����ɼ����飡�������� ");// TODO yao
																	// Ҫ��һ���޸ĳ�
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

	// //������
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
	 * R.drawable.annan); map.put("user_nickname", "��������º������");
	 * map.put("release_time", "30��ǰ"); map.put("content", "��������º������");
	 * map.put("remind_time", "����ˢ�º��ʱ��"); map.put("fans_count", "1000");
	 * map.put("record_count", "16754"); } catch (Exception e) { // TODO
	 * Auto-generated catch block setTitle("map������"); }
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


		// �����Ƕ�ˢ�µ���Ӧ����������addFirst������addLast()�������¼ӵ����ݼӵ�LISTView��
		// ����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			// ��ͷ��������������
			listItems.addAll(0, result);		
			// ֪ͨ�������ݼ��Ѿ��ı䣬�������֪ͨ����ô������ˢ��mListItems�ļ���
			channelListViewAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);// ����Ǳ��еģ�AsyncTask�涨�ĸ�ʽ
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
