package com.geeko.proto.ijkproject.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;

/**
 * Application for managing resources
 * 
 * This class is used to get values like context, dbHelper and sharedPreference.
 * 
 * @author Kim Seonyong
 * @version 1.1 April 18 2014
 * @since 1.0
 */
public class MyApplication extends Application {

	// 시스템 로그 확인을 위한 태그 스트링
	private static final String TAG = "MyApplication";
	private static SharedPreferences pref;
	private static UsersTableDbHelper mDbHelper;

	// 앱에서 사용자 정보를 습득시 사용할 SharedPreferences name 스트링
	public static final String PREFERENCE = "USERINFO";
	public static final String PREFERENCE_PHONE = "PHONE";
	public static final String PREFERENCE_NAME = "NAME";
	public static final String PREFERENCE_PASSWORD = "PASSWORD";
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

	/**
	 * Method that gets SharedPreference contain user-information
	 * 
	 * @return The application SharedPreference(name: USERINFO)
	 */
	public static SharedPreferences getUserSharedPreference() {
		return pref;
	}

	/**
	 * Method that sets context
	 * 
	 * @param context
	 */
	public static void setContext(Context context) {
		MyApplication.context = context;
	}

	/**
	 * Method that initialize the application
	 */
	private void initCheck() {
		pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

		// 최초 실행인지 확인 후 사용자 정보(기기 전화번호)를 생성 및 저장한다.
		if (!pref.getBoolean(PREFERENCE_INITIAL_PROCESS, false)) {
			TelephonyManager tMgr = (TelephonyManager) getContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			
			// mPhoneNumber(국제번호가 포함: 82)
			String mPhoneNumber = tMgr.getLine1Number().replace("+", "");
			// mPhoneNumber = mPhoneNumber.replace("+82", "0");
			//Log.i(TAG, "phone: " + mPhoneNumber);

			// 폰 번호 저장, 최초 실행을 확인 하기위한 값(SharedPreference: name="INITIAL_PROCESS")로 저장한다.
			pref.edit().putString(PREFERENCE_PHONE, mPhoneNumber).commit();
			pref.edit().putBoolean(PREFERENCE_INITIAL_PROCESS, true).commit();
			//mDbHelper = new UsersTableDbHelper(context);
		}
		mDbHelper = new UsersTableDbHelper(context);
	}
	
    public static UsersTableDbHelper getDbHelper() {
        return MyApplication.mDbHelper;
    }
    public static void setDbHelper(UsersTableDbHelper dbHelper){
    	mDbHelper = new UsersTableDbHelper(context);
    }
}
