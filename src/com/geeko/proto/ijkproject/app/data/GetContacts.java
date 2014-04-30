package com.geeko.proto.ijkproject.app.data;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GetContacts extends ListActivity {
	String tag = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Cursor cursor = getURI();
		int end = cursor.getCount();
		Log.d(tag, "end = " + end);

		String[] name = new String[end];
		String[] phoneNum = new String[end];
		String[] mergeInfo = new String[end];
		int count = 0;

		if (cursor.moveToFirst()) {

			int idIndex = cursor.getColumnIndex("_id");

			do {

				int id = cursor.getInt(idIndex);

				if (cursor.getString(2).equals("1")) {
					name[count] = cursor.getString(1);
					phoneNum[count] = cursor.getString(3);
					mergeInfo[count] = cursor.getString(1)
							+ cursor.getString(3);

					Log.d(tag, "id=" + id + ", name[" + count + "]="
							+ name[count] + ", phonenumber=" + phoneNum[count]);
					count++;
				}
			} while (cursor.moveToNext() || count > end);
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, mergeInfo));

		final ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	private Cursor getURI() {
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

		CursorLoader cursorLoader = new CursorLoader(this, people, projection,
				selection, selectionArgs, sortOrder);
		Cursor cursor = cursorLoader.loadInBackground();
		return cursor;
	}
}
