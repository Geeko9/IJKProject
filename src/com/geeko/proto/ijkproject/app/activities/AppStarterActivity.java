package com.geeko.proto.ijkproject.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;

/**
 * Activity for loading MainActivity and resources
 * 
 * This activity is used to get resources that need before MainActivity is
 * started.
 * 
 * @author Kim Seonyong
 * @version 1.1 April 18 2014
 * @since 1.0
 */
public class AppStarterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_stater);

		new AppStarterAsyncTask().execute();

	}

	public class AppStarterAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Intent intent;
			if (!MyApplication.getUserSharedPreference().getBoolean(
					MyApplication.PREFERENCE_SIGNUP_CHECK, false)) {
				intent = new Intent(AppStarterActivity.this, SignUpActivity.class);
			} else {
				intent = new Intent(AppStarterActivity.this, MainActivity.class);
			}
			AppStarterActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			AppStarterActivity.this.finish();
		}
	}
}