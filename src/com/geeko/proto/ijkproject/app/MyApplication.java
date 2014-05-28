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

	// �ý��� �α� Ȯ���� ���� �±� ��Ʈ��
	private static final String TAG = "MyApplication";
	private static SharedPreferences pref;
	private static UsersTableDbHelper mDbHelper;

	// �ۿ��� ����� ������ ����� ����� SharedPreferences name ��Ʈ��
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

		// ���� �������� Ȯ�� �� ����� ����(��� ��ȭ��ȣ)�� ���� �� �����Ѵ�.
		if (!pref.getBoolean(PREFERENCE_INITIAL_PROCESS, false)) {
			TelephonyManager tMgr = (TelephonyManager) getContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			
			// mPhoneNumber(������ȣ�� ����: 82)
			String mPhoneNumber = tMgr.getLine1Number().replace("+", "");
			// mPhoneNumber = mPhoneNumber.replace("+82", "0");
			//Log.i(TAG, "phone: " + mPhoneNumber);

			// �� ��ȣ ����, ���� ������ Ȯ�� �ϱ����� ��(SharedPreference: name="INITIAL_PROCESS")�� �����Ѵ�.
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
