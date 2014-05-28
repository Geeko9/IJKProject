package com.geeko.proto.ijkproject.app.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class SettingActivity extends Activity {

	private TextView tv_drop_user;
	private AlertDialog.Builder alertDialog;
	private ClearableEditText input;
	private AsyncTaskDrop asyncTaskDrop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		init();
	}

	private void init() {
		tv_drop_user = (TextView) findViewById(R.id.tv_Setting_Drop);
		alertDialog = new AlertDialog.Builder(SettingActivity.this);
		asyncTaskDrop = new AsyncTaskDrop();

		alertDialog.setTitle("계정삭제").setMessage("암호를 입력하시면 해당 계정 삭제가 가능합니다.")
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String password = input.getText().toString();
						if (password.equals(MyApplication
								.getUserSharedPreference().getString(
										MyApplication.PREFERENCE_PASSWORD, ""))) {
							asyncTaskDrop.execute(password);
						} else {
							Toast.makeText(SettingActivity.this,
									"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT)
									.show();
						}
					}
				});

		tv_drop_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				input = new ClearableEditText(SettingActivity.this);
				input.setInputType("password");
				input.setPadding(30, 0, 30, 0);
				alertDialog.setView(input);
				alertDialog.show();
			}
		});
	}

	public class AsyncTaskDrop extends AsyncTask<String, Void, String> {

		HttpRequest httpRequest = new HttpRequest();

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = httpRequest.httpRequestDelete("account/", "account",
						params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("200")) {
				MyApplication
						.getUserSharedPreference()
						.edit()
						.putBoolean(MyApplication.PREFERENCE_SIGNUP_CHECK,
								false).commit();
				MyApplication.getUserSharedPreference().edit()
						.putString(MyApplication.PREFERENCE_SIGN_KEY, "")
						.commit();

				new UsersTableDbHelper(MyApplication.getContext())
						.deleteTable();
				
				
				SQLiteDatabase db = new UsersTableDbHelper(MyApplication.getContext()).getWritableDatabase();
				
				new UsersTableDbHelper(MyApplication.getContext()).onUpgrade(db, 0, 0);
				// MyApplication.setDbHelper(new
				// UsersTableDbHelper(MyApplication.getContext()));
				// // getApplicationContext().deleteDatabase("PMS");
				Toast.makeText(SettingActivity.this, "삭제가 완료되었습니다.",
						Toast.LENGTH_SHORT).show();

				//new UsersTableDbHelper(MyApplication.getContext());

				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
								getBaseContext().getPackageName());
				finishAffinity();
				// finish();
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);

			} else {
			}
		}
	}

	@Override
	protected void onResume() {
		this.overridePendingTransition(0, 0);
		super.onResume();
	}
}
