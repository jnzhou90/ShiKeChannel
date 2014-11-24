package com.example.shikechannel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ChannelDetailActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_detail);
		TextView detailTextView = (TextView)findViewById(R.id.chanenlDetail);
		
		Intent detailIntent = getIntent();
		Bundle detailBudle = detailIntent.getExtras();
		String getDetail = detailBudle.getString("channelDetail");
		detailTextView.setText(getDetail);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.channel_detail, menu);
		return true;
	}

}
