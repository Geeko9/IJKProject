package com.geeko.proto.ijkproject.app.fragment;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

public class AppDialogSet extends DialogFragment {
	ClearableEditText input = new ClearableEditText(getActivity());
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	AsyncTaskDrop asyncTaskDrop = new AsyncTaskDrop();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		input.setInputType("password");
		input.setPadding(30, 0, 30, 0);

		builder.setView(input)
				.setTitle("계정삭제")
				.setMessage("암호를 입력하시면 해당 계정 삭제가 가능합니다.")
				.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String password = input.getText().toString();
						if (password.equals(MyApplication.getUserSharedPreference().getString(MyApplication.PREFERENCE_PASSWORD, ""))) {
							asyncTaskDrop.execute(password);
						} else {

						}
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		return builder.create();
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
						.putBoolean(MyApplication.PREFERENCE_SIGNUP_CHECK, false).commit();
				
				MyApplication.getUserSharedPreference().edit()
						.putString(MyApplication.PREFERENCE_SIGN_KEY, "")
						.commit();
				
				Toast.makeText(getActivity(), "삭제가 완료되었습니다.",
						Toast.LENGTH_SHORT).show();

				Intent i = MyApplication.getContext().getPackageManager()
						.getLaunchIntentForPackage(
								MyApplication.getContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
	}
}
