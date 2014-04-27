package com.geeko.proto.ijkproject.app.data;

import android.content.SharedPreferences;

import com.geeko.proto.ijkproject.app.MyApplication;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
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
