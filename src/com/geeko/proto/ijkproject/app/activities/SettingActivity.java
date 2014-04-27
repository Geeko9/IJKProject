package com.geeko.proto.ijkproject.app.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class SettingActivity extends Activity {

	private TextView tv_drop_user;
	AlertDialog.Builder alertDialog;
	ClearableEditText input;
	AsyncTaskDrop asyncTaskDrop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		init();
	}

	private void init() {
		tv_drop_user = (TextView) findViewById(R.id.tv_Setting_Drop);
		alertDialog = new AlertDialog.Builder(SettingActivity.this);
		input = new ClearableEditText(SettingActivity.this);
		asyncTaskDrop = new AsyncTaskDrop();

		alertDialog.setView(input).setTitle("계정삭제")
				.setMessage("암호를 입력하시면 해당 계정 삭제가 가능합니다.")
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setNegativeButton("삭제", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String password = input.getText().toString();
						if(password.equals(MyApplication.getUserSharedPreference().getString(MyApplication.PREFERENCE_PASSWORD, ""))){
							asyncTaskDrop.execute(password); 
						}else {
							Toast.makeText(SettingActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
						}
					}
				});

		tv_drop_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.show();
			}
		});
	}

	public class AsyncTaskDrop extends AsyncTask<String, Void, Void> {

		HttpRequest httpRequest = new HttpRequest();
		
		@Override
		protected Void doInBackground(String... params) {
			try {
				httpRequest.httpRequestDelete("account/", "account", params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
		}
	}
}
