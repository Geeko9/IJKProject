package com.geeko.proto.ijkproject.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.ListViewAdapter_Main;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;

public class ContactsDelActivity extends ActionBarActivity {
	String tag = null;
	private ListViewAdapter_Main adapter;
	ListView listView;
	String[] name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contacts_add);
		listView = (ListView) findViewById(R.id.lv_Contacts_Add);

		// adapter = new ListViewAdapter_Main(MyApplication.getContext());

		// List<Profile> nameList = new ArrayList<Profile>();
		UsersTableDbHelper helper = MyApplication.getDbHelper();
		// Select All Query
		String selectQuery = "SELECT " + UsersTableEntry.COLUMN_NAME_NAME
				+ ", " + UsersTableEntry.COLUMN_NAME_PHONE + ", "
				+ UsersTableEntry.COLUMN_NAME_REGION + ", "
				+ UsersTableEntry.COLUMN_NAME_STATUS + " FROM "
				+ UsersTableEntry.TABLE_NAME;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int end = cursor.getCount();
		name = new String[end];
		int count = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				name[count] = cursor.getString(0);
				// adapter.addItem(null, cursor.getString(0),
				// cursor.getString(1),
				// cursor.getString(2), null, cursor.getString(3));
				count++;
			} while (cursor.moveToNext());
		}// return contact list
		listView.setAdapter(new ArrayAdapter<String>(
				MyApplication.getContext(),
				R.layout.simple_list_item_multiple_choice, name));

		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts_del, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_del) {
			SparseBooleanArray checked = listView.getCheckedItemPositions();

			List<String> list = new ArrayList<String>();

			if (checked != null) {
				int size = checked.size();
				for (int i = 0; i < size; i++) {
					int key = checked.keyAt(i);
					boolean value = checked.get(key);
					if (value) {
						list.add(name[key]);
					}
				}
			}

			SQLiteDatabase db = MyApplication.getDbHelper()
					.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			String args = TextUtils.join(", ", name);
			
			db.execSQL(String.format("DELETE FROM users WHERE name IN ('%s');",
					args));
			db.close();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		this.overridePendingTransition(0, 0);
		super.onResume();
	}
}
