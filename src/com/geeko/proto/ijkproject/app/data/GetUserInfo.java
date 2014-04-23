package com.geeko.proto.ijkproject.app.data;

import android.content.SharedPreferences;

import com.geeko.proto.ijkproject.app.MyApplication;

public class GetUserInfo {
	private SharedPreferences sharedPreferences;

	public GetUserInfo() {
		sharedPreferences = MyApplication.getUserSharedPreference();
	}

	public String getOriginNumber() {
		return sharedPreferences.getString(MyApplication.PREFERENCE_PHONE, "");
	}

	public String getLocalNumber() {
		return sharedPreferences.getString(MyApplication.PREFERENCE_PHONE, "")
				.replace("+82", "0");
	}
}
