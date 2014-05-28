package com.geeko.proto.ijkproject.app.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.activities.ContactsAddActivity;
import com.geeko.proto.ijkproject.app.data.HangulUtils;
import com.geeko.proto.ijkproject.app.data.Item;
import com.geeko.proto.ijkproject.app.data.ListViewAdapter_Main;
import com.geeko.proto.ijkproject.app.data.db.Table;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.FriendListRetrieveResult;
import com.geeko.proto.ijkproject.app.network.HttpRequest;
import com.geeko.proto.ijkproject.app.network.Machine;
import com.geeko.proto.ijkproject.app.network.Profile;
import com.geeko.proto.ijkproject.app.network.ProfileUpdate;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	private ImageView profileImage;
	private TextView profileLocation, profileName;
	private Switch switchStatus;
	private OnChangeListener mListener;
	private LinearLayout mProfileBox;
	private Profile profile;
	private String searchKeyword;
	private ListView listView;
	private ListViewAdapter_Main adapter;

	public PlaceholderFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {

		ClearableEditText cet_search = (ClearableEditText) getView()
				.findViewById(R.id.cet_Main_Search);
		cet_search.setHint(getResources()
				.getString(R.string.string_hint_search));
		listView = (ListView) getView().findViewById(R.id.lv_Main_Friends);
		switchStatus = (Switch) getView().findViewById(R.id.switch1);

		mProfileBox = (LinearLayout) getView().findViewById(
				R.id.linearlayout_main_profile);

		String status = null;
		status = MyApplication.getUserSharedPreference().getString(
				MyApplication.PREFERENCE_STATUS, "free");

		if (status.equals("free")) {
			switchStatus.setChecked(true);
			mProfileBox.setBackgroundColor(0x5540B553);
		} else if (status.equals("busy")) {
			switchStatus.setChecked(false);
			mProfileBox.setBackgroundColor(0x55F84322);
		}

		profileLocation = (TextView) getView().findViewById(
				R.id.tv_Main_Profile_Location);
		profileName = (TextView) getView().findViewById(
				R.id.tv_Main_Profile_Name);

		switchStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				ProfileUpdate profileUpdate = new ProfileUpdate();
				profile = new Profile();
				ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
				Serializer serializer = new Persister();
				if (isChecked) {
					profile.setUserStatus("free");

				} else {
					profile.setUserStatus("busy");
				}
				try {
					profileUpdate.setProfile(profile);

					serializer.write(profileUpdate, byteOutput);
					if (new changeStatusAsync().execute(byteOutput.toString())
							.equals("200")) {
						Toast.makeText(MyApplication.getContext(), "wow",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

		profileLocation.setText(MyApplication.getUserSharedPreference()
				.getString(MyApplication.PREFERENCE_REGION, "지역을 설정해 주세요"));
		profileName.setText(MyApplication.getUserSharedPreference().getString(
				MyApplication.PREFERENCE_NAME, "이름이 없습니다."));

		mProfileBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onChange(1);
			}
		});

		adapter = new ListViewAdapter_Main(MyApplication.getContext());

		List<Profile> nameList = new ArrayList<Profile>();
		UsersTableDbHelper helper = MyApplication.getDbHelper();
		// Select All Query
		String selectQuery = "SELECT " + UsersTableEntry.COLUMN_NAME_NAME
				+ ", " + UsersTableEntry.COLUMN_NAME_PHONE + ", "
				+ UsersTableEntry.COLUMN_NAME_REGION + ", "
				+ UsersTableEntry.COLUMN_NAME_STATUS + " FROM "
				+ UsersTableEntry.TABLE_NAME;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				adapter.addItem(null, cursor.getString(0), cursor.getString(1),
						cursor.getString(2), null, cursor.getString(3));
			} while (cursor.moveToNext());
		}// return contact list

		if (adapter.isEmpty()) {
			ImageView addFriends = (ImageView) getView().findViewById(
					R.id.iv_Social_Add);
			addFriends.setVisibility(0);
			addFriends.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							ContactsAddActivity.class);
					getActivity().startActivity(intent);
				}
			});
		} else {
			listView.setAdapter(adapter);

			// selectQuery = "SELECT " + MachinesTableEntry.COLUMN_NAME_CODE
			// + ", " + MachinesTableEntry.COLUMN_NAME_MODELNAME + ", "
			// + MachinesTableEntry.COLUMN_NAME_TYPE + ", "
			// + MachinesTableEntry.COLUMN_NAME_SIZE + " FROM "
			// + MachinesTableEntry.TABLE_NAME;
			// db = helper.getWritableDatabase();
			// cursor = db.rawQuery(selectQuery, null);
			// if (cursor.moveToFirst()) {
			// do {
			// System.out.println("머신 데이터 내용: " + cursor.getString(0) +
			// cursor.getString(1) + cursor.getString(2) + cursor.getString(3));
			// } while (cursor.moveToNext());
			// }// return contact list
		}

		cet_search.et_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					searchKeyword = s.toString();

					ListViewAdapter_Main adapter = new ListViewAdapter_Main(
							MyApplication.getContext());
					listView.setAdapter(adapter);
					List<Profile> nameList = new ArrayList<Profile>();
					UsersTableDbHelper helper = MyApplication.getDbHelper();
					String selectQuery = "SELECT "
							+ UsersTableEntry.COLUMN_NAME_NAME + ", "
							+ UsersTableEntry.COLUMN_NAME_PHONE + ", "
							+ UsersTableEntry.COLUMN_NAME_REGION + ", "
							+ UsersTableEntry.COLUMN_NAME_STATUS + " FROM "
							+ UsersTableEntry.TABLE_NAME;
					SQLiteDatabase db = helper.getWritableDatabase();
					Cursor cursor = db.rawQuery(selectQuery, null);

					boolean isAdd = false;

					if (cursor.moveToFirst()) {
						do {
							isAdd = false;
							if (searchKeyword != null
									&& "".equals(searchKeyword.trim()) == false) {
								String iniName = HangulUtils
										.getHangulInitialSound(
												cursor.getString(0),
												searchKeyword);

								if (iniName.indexOf(searchKeyword) >= 0) {
									isAdd = true;
								}
							} else {
								isAdd = true;
							}
							if (isAdd) {
								adapter.addItem(null, cursor.getString(0),
										cursor.getString(1),
										cursor.getString(2), null,
										cursor.getString(3));

							}
						} while (cursor.moveToNext());
						adapter.notifyDataSetChanged();

						// adapter.notifyDataSetChanged();
					}// return contact list

					// displayList();
				} catch (Exception e) {
					Log.e("", e.getMessage(), e);
				}
			}
		});

		new getFriendListAsync().execute(null, null);
	}

	public interface OnChangeListener {
		public void onChange(int num);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnChangeListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnChangeListener");
		}
	}

	public class changeStatusAsync extends AsyncTask<String, Void, String> {

		String str = "profile/";

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = new HttpRequest().httpRequestPut(str, null, params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("200")) {

				Toast.makeText(MyApplication.getContext(), "변경완료되었습니다.",
						Toast.LENGTH_SHORT).show();

				String status = profile.getUserStatus();
				MyApplication.getUserSharedPreference().edit()
						.putString(MyApplication.PREFERENCE_STATUS, status)
						.commit();

				if (status.equals("free")) {
					mProfileBox.setBackgroundColor(0x5540B553);
				} else if (status.equals("busy")) {
					mProfileBox.setBackgroundColor(0x55F84322);
				}
			}
		}
	}

	public class getFriendListAsync extends AsyncTask<Void, Void, String> {

		HttpRequest httpRequest = new HttpRequest();

		@Override
		protected String doInBackground(Void... params) {
			String result = null;
			try {
				result = httpRequest.httpRequestGet(
						"getFriendList",
						MyApplication.getUserSharedPreference().getString(
								MyApplication.PREFERENCE_SIGN_KEY, ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ArrayList<Item> mListData = adapter.getItemList();
			ArrayList<String> machineList = new ArrayList<String>(
					mListData.size());
			ArrayList<Profile> mListData2 = new ArrayList<Profile>();
			FriendListRetrieveResult friendListResult;
			Serializer serializer = new Persister();

			try {
				friendListResult = serializer.read(
						FriendListRetrieveResult.class, httpRequest.getRes());
				mListData2 = (ArrayList<Profile>) friendListResult
						.getFriendList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SQLiteDatabase db = MyApplication.getDbHelper()
					.getWritableDatabase();
			ContentValues values = new ContentValues();

			if (mListData != null && mListData2 != null)
				for (int i = 0; i < mListData2.size(); i++) {
					for (int j = 0; j < mListData.size(); j++) {
						System.out.println("mListData: "
								+ mListData.get(j).getPhone());
						System.out.println("mListData2: "
								+ mListData2.get(i).getPhoneNumber());
						String str2 = "";
						if (mListData2.get(i).getPhoneNumber()
								.equals(mListData.get(j).getPhone())) {

							List<Machine> mList = new ArrayList<Machine>();
							mList = mListData2.get(i).getOwnMachines();
							int size = mList.size();

							for (int k = 0; k < size; k++) {
								Cursor c = db.rawQuery(
										"select modelname from machines where id = "
												+ "'"
												+ mList.get(k).getMachineCode()
												+ "'", null);
								c.moveToFirst();
								str2 += c.getString(0);
								if (k != size - 1) {
									str2 += ", ";
								}
							}
							machineList.add(str2);
							System.out.println("match check: "
									+ mListData.get(j).getName() + " " + str2);
							// Cursor c = db.rawQuery("select * from machines",
							// null);
							// c.moveToFirst();

							// values.put(
							// Table.OwnMachinesTableEntry.COLUMN_NAME_ENTRY_ID,
							// mListData2.get(i).getPhoneNumber());
							// values.put(
							// Table.OwnMachinesTableEntry.COLUMN_NAME_ID_MACHINE,
							// mListData2.get(i).getPhoneNumber());
							// values.put(
							// Table.OwnMachinesTableEntry.COLUMN_NAME_NUMMACHINE,
							// mListData2.get(i).getPhoneNumber());
							//
							// String[] str = new String[1];
							// str[0] = mListData.get(j).getPhone();
							//
							// db.update(Table.UsersTableEntry.TABLE_NAME,
							// values,
							// Table.UsersTableEntry.COLUMN_NAME_PHONE
							// + "=" + str[0], null);

							// // System.out.println("test");
							// //
							// values.put(Table.UsersTableEntry.COLUMN_NAME_PHONE,
							// // mListData.get(j).getPhone());

							// }
							// machineList.add(j, str2);

							values.put(
									Table.UsersTableEntry.COLUMN_NAME_STATUS,
									mListData2.get(i).getUserStatus());
							String[] str = new String[1];
							str[0] = mListData.get(j).getPhone();

							db.update(Table.UsersTableEntry.TABLE_NAME, values,
									Table.UsersTableEntry.COLUMN_NAME_PHONE
											+ "=" + str[0], null);
						} else {
							machineList.add(null);
						}
					}
				}
			ListViewAdapter_Main adapter2 = new ListViewAdapter_Main(
					MyApplication.getContext());
			List<Profile> nameList = new ArrayList<Profile>();
			UsersTableDbHelper helper = MyApplication.getDbHelper();
			// Select All Query
			String selectQuery = "SELECT " + UsersTableEntry.COLUMN_NAME_NAME
					+ ", " + UsersTableEntry.COLUMN_NAME_PHONE + ", "
					+ UsersTableEntry.COLUMN_NAME_REGION + ", "
					+ UsersTableEntry.COLUMN_NAME_STATUS + " FROM "
					+ UsersTableEntry.TABLE_NAME;
			db = helper.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			int counter = 0;
			if (cursor.moveToFirst()) {
				do {
					adapter2.addItem(null, cursor.getString(0),
							cursor.getString(1), cursor.getString(2),
							machineList.get(counter), cursor.getString(3));
					counter++;
				} while (cursor.moveToNext());
			}// return contact list

			// adapter.notifyDataSetChanged();
			listView.setAdapter(adapter2);
			System.out.println(httpRequest.getRes());

		}
	}
}