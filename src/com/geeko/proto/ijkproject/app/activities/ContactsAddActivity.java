package com.geeko.proto.ijkproject.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.GetContacts;
import com.geeko.proto.ijkproject.app.data.db.Table;
import com.geeko.proto.ijkproject.app.network.Profile;

public class ContactsAddActivity extends ActionBarActivity {
	String tag = null;
	ListView listView;
	String[] name;
	String[] phoneNum;
	String[] mergeInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
	}

	private void init() {
		setContentView(R.layout.activity_contacts_add);
		listView = (ListView) findViewById(R.id.lv_Contacts_Add);

		new GetContacts();
		Cursor cursor = GetContacts.getURI();
		int end = cursor.getCount();
		Log.d(tag, "end = " + end);

		name = new String[end];
		phoneNum = new String[end];
		mergeInfo = new String[end];
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_add) {
			SparseBooleanArray checked = listView.getCheckedItemPositions();

			// Toast.makeText(MyApplication.getContext(), checked.toString(),
			// Toast.LENGTH_SHORT).show();

			SQLiteDatabase db = MyApplication.getDbHelper()
					.getWritableDatabase();
			ContentValues values = new ContentValues();
			List<Profile> list = new ArrayList<Profile>();

			int size = checked.size(); // number of name-value pairs in the
										// array
			for (int i = 0; i < size; i++) {
				int key = checked.keyAt(i);
				boolean value = checked.get(key);
				if (value) {
					// Toast.makeText(MyApplication.getContext(),
					// "�대쫫: " + name[key] + " �꾪솕踰덊샇: " + phoneNum[key],
					// Toast.LENGTH_SHORT).show();
					phoneNum[key].replaceAll("-", "");
					phoneNum[key].replaceFirst("010", "+82");
				}
				list.add(new Profile(phoneNum[key], name[key]));
			}
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					values.put(Table.UsersTableEntry.COLUMN_NAME_PHONE, list
							.get(j).getPhoneNumber());
					values.put(Table.UsersTableEntry.COLUMN_NAME_NAME, list
							.get(j).getNickName());
					values.put(Table.UsersTableEntry.COLUMN_NAME_WORKINGPERIOD,
							0);
					values.put(Table.UsersTableEntry.COLUMN_NAME_REGION, "");
					values.put(Table.UsersTableEntry.COLUMN_NAME_STATUS,
							"local");

					db.insert(Table.UsersTableEntry.TABLE_NAME, null, values);
					// MainActivity.mAdapter.notifyDataSetChanged();
				}
			}
			// if (listBooleanArray != null) {
			// List<Profile> list;
			// for (int i = 0; i < listBooleanArray.size(); i++) {
			// mListView.getItemAtPosition(listBooleanArray.get(i))
			// }
			// }
		}
		finish();
		return super.onOptionsItemSelected(item);
	}
}
