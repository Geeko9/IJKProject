package com.geeko.proto.ijkproject.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Application for managing resources
 * 
 * This class is used to get values like context, dbHelper and sharedPreference.
 * 
 * @author Geeko
 * @version 1.1 April 18 2014
 * @since 1.0
 */
public class MyApplication extends Application {

	private static final String TAG = "MyApplication";
	private static SharedPreferences pref;

	public static final String PREFERENCE = "USERINFO";
	public static final String PREFERENCE_PHONE = "PHONE";
	public static final String PREFERENCE_NAME = "NAME";
	public static final String PREFERENCE_REGION = "REGION";
	public static final String PREFERENCE_PERIOD = "PERIOD";
	public static final String PREFERENCE_STATUS = "STATUS";
	public static final String PREFERENCE_INITIAL_PROCESS = "INITIAL";
	public static final String PREFERENCE_SIGNUP_CHECK = "SIGNUP";
	public static final String PREFERENCE_SIGN_KEY = "SIGNKEY";

	/**
	 * Context need for other activities. instead getApplicationContext() method
	 */
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		MyApplication.setContext(getApplicationContext());

		initCheck();
	}

	/**
	 * Method that gets context
	 * 
	 * @return The application context
	 */
	public static Context getContext() {
		return context;
	}

	public static SharedPreferences getUserSharedPreference() {
		return pref;
	}

	public static void setContext(Context context) {
		MyApplication.context = context;
	}

	// 최초 실행인지 확인 후 사용자 정보를 생성 및 저장한다.
	private void initCheck() {
		pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

		if (!pref.getBoolean(PREFERENCE_INITIAL_PROCESS, false)) {
			TelephonyManager tMgr = (TelephonyManager) getContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mPhoneNumber = tMgr.getLine1Number();
			// mPhoneNumber = mPhoneNumber.replace("+82", "0");
			Log.i(TAG, "phone: " + mPhoneNumber);

			pref.edit().putString(PREFERENCE_PHONE, mPhoneNumber).commit();
			pref.edit().putBoolean("INITIAL_PROCESS", true).commit();
			// mDBHelper = new UsersTableDbHelper(context);
		}
	}
}
