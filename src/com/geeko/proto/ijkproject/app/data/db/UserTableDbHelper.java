package com.geeko.proto.ijkproject.app.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seonyong on 2014-03-28.
 */
public class UserTableDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PMS.db";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Table.UserTableEntry.TABLE_NAME + " (" +
                    Table.UserTableEntry._ID + " INTEGER PRIMARY KEY," +
                    Table.UserTableEntry.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP +
                    Table.UserTableEntry.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    Table.UserTableEntry.COLUMN_NAME_REGION + TEXT_TYPE + COMMA_SEP +
                    Table.UserTableEntry.COLUMN_NAME_PERIOD + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Table.UserTableEntry.TABLE_NAME;

    public UserTableDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
