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
				.getString(MyApplication.PREFERENCE_REGION, "������ ������ �ּ���"));
		profileName.setText(MyApplication.getUserSharedPreference().getString(
				MyApplication.PREFERENCE_NAME, "�̸��� �����ϴ�."));

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

				Toast.makeText(MyApplication.getContext(), "����Ϸ�Ǿ����ϴ�.",
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

			for (int i = 0; i < mListData2.size(); i++) {
				for (int j = 0; j < mListData.size(); j++) {
					if (mListData2.get(i).getPhoneNumber()
							.equals(mListData.get(j).getPhone())) {

						System.out.println("test");
						values.put(Table.UsersTableEntry.COLUMN_NAME_STATUS,
								mListData2.get(i).getUserStatus());

						db.insert(Table.UsersTableEntry.TABLE_NAME, null,
								values);
					}
				}
			}

			adapter.notifyDataSetChanged();
			System.out.println(mListData2.get(0).getUserStatus());

		}
	}
}