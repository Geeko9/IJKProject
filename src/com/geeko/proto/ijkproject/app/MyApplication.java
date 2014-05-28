package com.geeko.proto.ijkproject.app;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;

import com.geeko.proto.ijkproject.app.data.db.MachineTableDbHelper;
import com.geeko.proto.ijkproject.app.data.db.Table;
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
			// Log.i(TAG, "phone: " + mPhoneNumber);

			// �� ��ȣ ����, ���� ������ Ȯ�� �ϱ����� ��(SharedPreference:
			// name="INITIAL_PROCESS")�� �����Ѵ�.
			pref.edit().putString(PREFERENCE_PHONE, mPhoneNumber).commit();
			pref.edit().putBoolean(PREFERENCE_INITIAL_PROCESS, true).commit();

			mDbHelper = new UsersTableDbHelper(context);

			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			String[] code = { "008", "008w", "010", "010w", "017", "017w",
					"020", "020w", "030", "030w", "035", "035w", "02", "02w",
					"3", "3w", "6", "6w", "8", "8w" };
			String[] model = { "MINI-8", "MINI-8W", "MINI-10", "MINI-10W",
					"MINI-17", "MINI-17W", "MINI-20", "MINI-20W", "MINI-30",
					"MINI-30W", "MINI-35", "MINI-35W", "PWR-02", "PWR-02W",
					"PWR-3", "PWR-3W", "PWR-6", "PWR-6W", "PWR-8", "PWR-8W" };

			for (int i = 0; i < 20; i++) {
				values.put(Table.MachinesTableEntry.COLUMN_NAME_CODE, code[i]);
				values.put(Table.MachinesTableEntry.COLUMN_NAME_MODELNAME,
						model[i]);
				values.put(Table.MachinesTableEntry.COLUMN_NAME_TYPE,
						"excavator");
				if (i < 12) {
					values.put(Table.MachinesTableEntry.COLUMN_NAME_SIZE,
							"mini");
				} else if (i < 14) {
					values.put(Table.MachinesTableEntry.COLUMN_NAME_SIZE,
							"medium");
				} else {
					values.put(Table.MachinesTableEntry.COLUMN_NAME_SIZE,
							"large");
				}
				System.out.println("check db insert:" + values.toString());
				db.insert(Table.MachinesTableEntry.TABLE_NAME, null, values);
			}
			db.close();

		}
		mDbHelper = new UsersTableDbHelper(context);
		//mDbHelper.close();
		// machineDbHelper = new MachineTableDbHelper(context);
		// machineDbHelper.close();
	}

	public static UsersTableDbHelper getDbHelper() {
		return MyApplication.mDbHelper;
	}

	public static void setDbHelper(UsersTableDbHelper dbHelper) {
		mDbHelper = new UsersTableDbHelper(context);
	}
}
