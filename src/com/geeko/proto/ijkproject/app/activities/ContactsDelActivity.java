package com.geeko.proto.ijkproject.app.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.GetContacts;

public class ContactsDelActivity extends ActionBarActivity {
	String tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contacts_add);
		ListView listView = (ListView) findViewById(R.id.lv_Contacts_Add);

		new GetContacts();
		Cursor cursor = GetContacts.getURI();
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
					mergeInfo[count] = cursor.getString(1) + " "
							+ cursor.getString(3);

					Log.d(tag, "id=" + id + ", name[" + count + "]="
							+ name[count] + ", phonenumber=" + phoneNum[count]);
					count++;
				}
			} while (cursor.moveToNext() || count > end);
		}

		listView.setAdapter(new ArrayAdapter<String>(
				MyApplication.getContext(),
				R.layout.simple_list_item_multiple_choice, mergeInfo));

		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
}
