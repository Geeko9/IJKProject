package com.geeko.proto.ijkproject.app.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;

public class GetContacts {
	String tag = null;
	public static int size = 0;

	public static String getUserList() {
		ArrayList userLists = new ArrayList<String>();
		// userLists = null;
		// List<Profile> nameList = new ArrayList<Profile>();
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
			// userLists = new String[cursor.getCount()];
			do {
				userLists.add(String.valueOf(cursor.getInt(0)));
				Log.i("User _id show!", String.valueOf(cursor.getInt(0)));
				// userLists[counter] = String.valueOf(cursor.getInt(0));
				counter++;
				size = counter;
			} while (cursor.moveToNext());
		}
		System.out.println(userLists.toString());
		// return contact list
		return userLists.toString();
	}

	public static Cursor getURI() {
		Uri people = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		String selection = null;
		String selectionArgs = null;

		selectionArgs = getUserList().replaceAll("\\[", "\\(");
		selectionArgs = selectionArgs.replaceAll("\\]", "\\)");
		System.out.println(selectionArgs);
		if (selectionArgs != null) {
			// selectionArgs = new String[getUserList().length];
			selection = ContactsContract.CommonDataKinds.Phone._ID + " NOT IN "
					+ selectionArgs;
			// selectionArgs = getUserList();
			// selectionArgs = ;
		}
		String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		CursorLoader cursorLoader = new CursorLoader(
				MyApplication.getContext(), people, projection, selection,
				null, sortOrder);
		Cursor cursor = cursorLoader.loadInBackground();

		return cursor;
	}
}
