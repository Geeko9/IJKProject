package com.geeko.proto.ijkproject.app.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.activities.ContactsAddActivity;
import com.geeko.proto.ijkproject.app.data.ListViewAdapter_Main;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.Profile;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	private ImageView profileImage;
	private TextView profileLocation, profileName;
	OnChangeListener mListener;

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
		ListView listView = (ListView) getView().findViewById(
				R.id.lv_Main_Friends);
		LinearLayout mProfileBox = (LinearLayout) getView().findViewById(
				R.id.linearlayout_main_profile);

		profileLocation = (TextView) getView().findViewById(
				R.id.tv_Main_Profile_Location);
		profileName = (TextView) getView().findViewById(
				R.id.tv_Main_Profile_Name);

		mProfileBox.setBackgroundColor(0x5540B553);
		profileLocation.setText(MyApplication.getUserSharedPreference()
				.getString(MyApplication.PREFERENCE_REGION, "지역을 설정해 주세요"));
		profileName.setText(MyApplication.getUserSharedPreference().getString(
				MyApplication.PREFERENCE_NAME, "이름이 없습니다."));

		profileName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onChange(1);
			}
		});

		ListViewAdapter_Main adapter = new ListViewAdapter_Main(
				MyApplication.getContext());

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
}