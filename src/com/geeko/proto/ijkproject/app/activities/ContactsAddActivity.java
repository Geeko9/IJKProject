package com.geeko.proto.ijkproject.app.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import com.geeko.proto.ijkproject.app.network.FriendListUpdate;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.network.PhoneNumber;
import com.geeko.proto.ijkproject.app.network.Profile;

public class ContactsAddActivity extends ActionBarActivity {
	String tag = null;
	ListView listView;
	int[] _id;
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

		_id = new int[end];
		name = new String[end];
		phoneNum = new String[end];
		mergeInfo = new String[end];
		int count = 0;

		if (cursor.moveToFirst()) {

			int idIndex = cursor.getColumnIndex("_id");

			do {

				int id = cursor.getInt(idIndex);

				if (cursor.getString(2).equals("1")) {
					_id[count] = id;
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
			List<PhoneNumber> list2 = new ArrayList<PhoneNumber>();

			int size = checked.size();
			for (int i = 0; i < size; i++) {
				int key = checked.keyAt(i);
				boolean value = checked.get(key);
				if (value) {
					phoneNum[key].replaceAll("-", "");
					phoneNum[key].replaceFirst("010", "8210");
				}
				list.add(new Profile(phoneNum[key], name[key], _id[key]));
				list2.add(new PhoneNumber(phoneNum[key]));
			}

			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			try {
				Serializer serializer = new Persister();
				serializer.write(new FriendListUpdate(list2, null), byteOutput);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (new FrinedListUpdateTask().execute(byteOutput.toString())
					.equals("200")) {
			}

			String[] strArr = { "busy", "free", "local" };
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					values.put(Table.UsersTableEntry.COLUMN_NAME_FOREIGN_KEY, list.get(j).get_id());
					values.put(Table.UsersTableEntry.COLUMN_NAME_PHONE, list
							.get(j).getPhoneNumber());
					values.put(Table.UsersTableEntry.COLUMN_NAME_NAME, list
							.get(j).getNickName());
					values.put(Table.UsersTableEntry.COLUMN_NAME_WORKINGPERIOD,
							0);
					values.put(Table.UsersTableEntry.COLUMN_NAME_REGION, "");
					// values.put(Table.UsersTableEntry.COLUMN_NAME_STATUS,
					// strArr[new Random().nextInt(3)]);

					values.put(Table.UsersTableEntry.COLUMN_NAME_STATUS,
							"local");

					db.insert(Table.UsersTableEntry.TABLE_NAME, null, values);

					Log.i("DB insert check!!", "_id: " + list.get(j).get_id()
							+ " phone: " + list.get(j).getPhoneNumber()
							+ " name: " + list.get(j).getNickName());
				}
			}
		}
		finish();
		return super.onOptionsItemSelected(item);
	}

	public class FrinedListUpdateTask extends AsyncTask<String, Void, String> {

		HttpRequest httpRequest = new HttpRequest();

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = httpRequest.httpRequestPut("friendlist/", null,
						params[0]);
				System.out.println("XML String Test: "+params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.equals("200")) {
					Toast.makeText(MyApplication.getContext(), "리스트 업로드 성공",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MyApplication.getContext(),
							"네트워크 에러" + "\n" + "데이터 네트워크 확인 바랍니다." + result,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
