package com.geeko.proto.ijkproject.app.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seonyong on 2014-03-31.
 */
public class UsersTableDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "PMS.db";
	private static final String TEXT_TYPE = " TEXT ";
	private static final String INTEGER_TYPE = " INTEGER ";
	private static final String BOLB_TYPE = " BOLB ";
	private static final String COMMA_SEP = ", ";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ Table.UsersTableEntry.TABLE_NAME + " ("
			+ Table.UsersTableEntry.COLUMN_NAME_ENTRY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Table.UsersTableEntry.COLUMN_NAME_PHONE + TEXT_TYPE + " UNIQUE, "
			+ Table.UsersTableEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP
			+ Table.UsersTableEntry.COLUMN_NAME_REGION + TEXT_TYPE + COMMA_SEP
			+ Table.UsersTableEntry.COLUMN_NAME_WORKINGPERIOD + INTEGER_TYPE
			+ COMMA_SEP + Table.UsersTableEntry.COLUMN_NAME_PUSHSERVICE
			+ INTEGER_TYPE + COMMA_SEP
			+ Table.UsersTableEntry.COLUMN_NAME_STATUS + TEXT_TYPE + " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ Table.UsersTableEntry.TABLE_NAME;

	public UsersTableDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL(SQL_DELETE_ENTRIES);
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}
