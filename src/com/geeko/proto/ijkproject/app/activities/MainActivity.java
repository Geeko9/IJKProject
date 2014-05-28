package com.geeko.proto.ijkproject.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.fragment.PlaceholderFragment;
import com.geeko.proto.ijkproject.app.fragment.UserDetailFragment;

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
public class MainActivity extends ActionBarActivity implements
		PlaceholderFragment.OnChangeListener {

	public static int fragmentNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		// Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.container, newFragment);

		// Commit the transaction
		transaction.commit();
	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;

		switch (idx) {
		case 0:
			newFragment = new PlaceholderFragment();
			fragmentNum = 0;
			break;
		case 1:
			newFragment = new UserDetailFragment();
			fragmentNum = 1;
			break;

		default:
			// Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
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
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			MainActivity.this.startActivity(intent);
			return true;
		} else if (id == R.id.action_contacts) {
			intent = new Intent(MainActivity.this, ContactsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			MainActivity.this.startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Intent i = new Intent(MainActivity.this, MainActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	protected void onResume() {
		this.overridePendingTransition(0, 0);
		super.onResume();
	}

	@Override
	public void onChange(int num) {
		fragmentReplace(num);
	}

	@Override
	public void onBackPressed() {

		if (fragmentNum == 1)
			fragmentReplace(0);
		else
			super.onBackPressed();
	}
}
