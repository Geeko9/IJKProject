package com.geeko.proto.ijkproject.app.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;

import com.geeko.proto.ijkproject.app.MyApplication;

public class GetContacts {
	String tag = null;

	public static Cursor getURI() {
		Uri people = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		CursorLoader cursorLoader = new CursorLoader(
				MyApplication.getContext(), people, projection, selection,
				selectionArgs, sortOrder);
		Cursor cursor = cursorLoader.loadInBackground();
		return cursor;
	}

}
