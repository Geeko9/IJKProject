package com.geeko.proto.ijkproject.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.widgets.ClearableEditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	private ImageView profileImage;
	private TextView profileLocation, profileName;
	public PlaceholderFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {

		ClearableEditText cet_search = (ClearableEditText) getView().findViewById(R.id.cet_Main_Search);
		cet_search.setHint(getResources().getString(R.string.string_hint_search));

		profileLocation = (TextView) getView().findViewById(R.id.tv_Main_Profile_Location);
		profileName = (TextView) getView().findViewById(R.id.tv_Main_Profile_Name);

		profileLocation.setText(MyApplication.getUserSharedPreference().getString(MyApplication.PREFERENCE_REGION, "지역을 설정해 주세요"));
		profileName.setText(MyApplication.getUserSharedPreference().getString(MyApplication.PREFERENCE_NAME, "이름이 없습니다."));
	}
}