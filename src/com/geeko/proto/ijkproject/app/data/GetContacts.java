package com.geeko.proto.ijkproject.app.data;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;

import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.Profile;

public class GetContacts {
	String tag = null;
	private static String[] userLists;

	public static String[] getUserList() {
		userLists = null;

		List<Profile> nameList = new ArrayList<Profile>();
		UsersTableDbHelper helper = MyApplication.getDbHelper();
		// Select All Query
		String selectQuery = "SELECT "
				+ UsersTableEntry.COLUMN_NAME_FOREIGN_KEY + " FROM "
				+ UsersTableEntry.TABLE_NAME;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		int counter = 0;
		if (cursor.moveToFirst()) {
			do {
				userLists[counter] = String.valueOf(cursor.getInt(0));
			} while (cursor.moveToNext());
		}// return contact list
		return userLists;
	}

	public static Cursor getURI() {
		Uri people = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		String selection = ContactsContract.CommonDataKinds.Phone._ID + "!=?";
		String[] selectionArgs = getUserList();
		String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		CursorLoader cursorLoader = new CursorLoader(
				MyApplication.getContext(), people, projection, selection,
				selectionArgs, sortOrder);
		Cursor cursor = cursorLoader.loadInBackground();

		return cursor;
	}
}
