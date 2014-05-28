package com.geeko.proto.ijkproject.app.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.network.Signup;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class SignUpActivity extends Activity {

	TextView tv_phone;
	Button btn_signup;
	ClearableEditText cet_name, cet_pass;
	String phoneNumber, password, nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		init();
	}

	private void init() {
		tv_phone = (TextView) findViewById(R.id.tv_SignIn_Phone);
		btn_signup = (Button) findViewById(R.id.btn_action_signup);
		cet_name = (ClearableEditText) findViewById(R.id.cet_SignIn_Name);
		cet_pass = (ClearableEditText) findViewById(R.id.cet_SignIn_Password);

		cet_name.setMaxLength(6);
		cet_pass.setMaxLength(8);

		cet_name.setHint(getResources()
				.getString(R.string.string_hint_nickname));
		cet_pass.setHint(getResources()
				.getString(R.string.string_hint_password));
		cet_pass.setInputType("password");

		tv_phone.setText(MyApplication.getUserSharedPreference().getString(
				MyApplication.PREFERENCE_PHONE, null));
		tv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getContext(),
						"전화번호는 수정이 불가능 합니다.", Toast.LENGTH_SHORT).show();
			}
		});

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				phoneNumber = tv_phone.getText().toString();
				nickname = cet_name.getText().toString();
				password = cet_pass.getText().toString();

				if (cet_name.getTextSize() < 3) {
					Toast.makeText(MyApplication.getContext(),
							"닉네임은 3자 이상 6자 이하로 입력 해 주시기 바랍니다.",
							Toast.LENGTH_SHORT).show();
				} else if (cet_pass.getTextSize() < 6) {
					Toast.makeText(MyApplication.getContext(),
							"패스워드는 6자 이상 8자 이하로 입력 해 주시기 바랍니다.",
							Toast.LENGTH_SHORT).show();
				} else {
					ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
					try {
						Serializer serializer = new Persister();
						serializer.write(new Signup(phoneNumber, nickname,
								password), byteOutput);

						if (new SignUpAsyncTask()
								.execute(byteOutput.toString()).equals("200")) {

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private class SignUpAsyncTask extends AsyncTask<String, Void, String> {

		HttpRequest httpRequest = new HttpRequest();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = httpRequest.httpRequestPost("account/", params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.equals("200")) {
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putBoolean(MyApplication.PREFERENCE_SIGNUP_CHECK,
									true).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_SIGN_KEY,
									httpRequest.getRes()).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_NAME,
									cet_name.getText().toString()).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_PASSWORD,
									cet_pass.getText().toString()).commit();

					MyApplication.getUserSharedPreference().edit()
							.putString(MyApplication.PREFERENCE_STATUS, "0")
							.commit();

					SignUpActivity.this.startActivity(new Intent(
							SignUpActivity.this, MainActivity.class));
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
					SignUpActivity.this.finish();

					Toast.makeText(MyApplication.getContext(), "가입을 축하드립니다.",
							Toast.LENGTH_SHORT).show();

				} else {
					new ReSignUpAsyncTask().execute(password);
					// Toast.makeText(MyApplication.getContext(),
					// "네트워크 에러" + "\n" + "데이터 네트워크 확인 바랍니다.",
					// Toast.LENGTH_SHORT).show();
				}
				// new ReSignUpAsyncTask().execute(password);
			}
		}
	}

	private class ReSignUpAsyncTask extends AsyncTask<String, Void, String> {

		HttpRequest httpRequest = new HttpRequest();

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = httpRequest.httpRequestGet("account/", params[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.equals("200")) {
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putBoolean(MyApplication.PREFERENCE_SIGNUP_CHECK,
									true).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_SIGN_KEY,
									httpRequest.getRes()).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_NAME,
									cet_name.getText().toString()).commit();
					MyApplication
							.getUserSharedPreference()
							.edit()
							.putString(MyApplication.PREFERENCE_PASSWORD,
									cet_pass.getText().toString()).commit();

					SignUpActivity.this.startActivity(new Intent(
							SignUpActivity.this, MainActivity.class));
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
					SignUpActivity.this.finish();

					Toast.makeText(MyApplication.getContext(), "재인증 되었습니다.",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MyApplication.getContext(),
							"네트워크 에러" + "\n" + "데이터 네트워크 확인 바랍니다." + result,
							Toast.LENGTH_SHORT).show();
				}
				MyApplication.setDbHelper(new UsersTableDbHelper(MyApplication
						.getContext()));
			}
		}

	}

	@Override
	protected void onResume() {
		this.overridePendingTransition(0, 0);
		super.onResume();
	}
}
