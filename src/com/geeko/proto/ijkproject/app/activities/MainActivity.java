package com.geeko.proto.ijkproject.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.fragment.PlaceholderFragment;

/**
 * Activity for managing friend list
 * 
 * This activity is used to get resources from device DB(SQLite) and show them
 * using ListView
 * 
 * @author Kim Seonyong
 * @version 1.1 April 18 2014
 * @since 1.0
 */
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		if (id == R.id.action_settings) {
			intent = new Intent(MainActivity.this, SettingActivity.class);
			MainActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			return true;
		} else if (id == R.id.action_contacts) {
			intent = new Intent(MainActivity.this, ContactsActivity.class);
			MainActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
