package com.geeko.proto.ijkproject.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
// ���� ���� ��Ƽ��Ƽ �ε�ȭ�� ���� �� ȸ�� ���� ���� Ȯ���Ͽ� ���� ��Ƽ��Ƽ�� Ȯ���Ͽ� �Ѿ�� ���� �����Ѵ�.
public class AppStarterActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_stater);

		new AppStarterAsyncTask().execute();
	}

	public class AppStarterAsyncTask extends AsyncTask<Void, Void, Void> {
		private Intent intent;

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (!MyApplication.getUserSharedPreference().getBoolean(MyApplication.PREFERENCE_SIGNUP_CHECK, false)) {
				intent = new Intent(AppStarterActivity.this, SignUpActivity.class);
			} else {
				intent = new Intent(AppStarterActivity.this, MainActivity.class);
			}
			
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					AppStarterActivity.this.startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					AppStarterActivity.this.finish();
				}
			}.sendEmptyMessageDelayed(0, 1000);
		}
	}
}