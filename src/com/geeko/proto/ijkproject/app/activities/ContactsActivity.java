/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geeko.proto.ijkproject.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;
import com.geeko.proto.ijkproject.app.MyApplication;
import com.geeko.proto.ijkproject.app.data.db.Table.UsersTableEntry;
import com.geeko.proto.ijkproject.app.data.db.UsersTableDbHelper;
import com.geeko.proto.ijkproject.app.network.Profile;

public class ContactsActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new FavoriteFragment();

			case 1:
				return new ContactsFragment();

			default:
				return new FavoriteFragment();
			}

		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String[] strs = { "즐겨찾기", "연락처보기" };
			return strs[position];
		}
	}

	public static class FavoriteFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_contacts_favorite, container, false);
			return rootView;
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class ContactsFragment extends Fragment {

		String tag = null;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contacts,
					container, false);
			ListView listView = (ListView) rootView
					.findViewById(R.id.lv_Contacts);

			List<String> nameList = new ArrayList<String>();
			UsersTableDbHelper helper = MyApplication.getDbHelper();
			// Select All Query
			String selectQuery = "SELECT " + UsersTableEntry.COLUMN_NAME_NAME
					+ " FROM " + UsersTableEntry.TABLE_NAME;
			SQLiteDatabase db = helper.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String name = new String(cursor.getString(0));
					// contact.setNickName(cursor.getString(1));
					// Adding contact to list
					nameList.add(name);
				} while (cursor.moveToNext());
			}// return contact list

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					MyApplication.getContext(),
					android.R.layout.simple_list_item_1) {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);

					TextView textView = (TextView) view
							.findViewById(android.R.id.text1);

					/* YOUR CHOICE OF COLOR */
					textView.setTextColor(Color.BLACK);

					return view;
				}
			};

			// ArrayAdapter mAdapter = new
			// ArrayAdapter<Profile>(MyApplication.getContext(),
			// android.R.layout.simple_list_item_1)
			adapter.addAll(nameList);
			listView.setAdapter(adapter);

			return rootView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		if (id == R.id.action_add) {
			intent = new Intent(ContactsActivity.this,
					ContactsAddActivity.class);
			ContactsActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			return true;
		} else if (id == R.id.action_del) {
			intent = new Intent(ContactsActivity.this,
					ContactsDelActivity.class);
			ContactsActivity.this.startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public List<Profile> getAllContacts() {
		List<Profile> contactList = new ArrayList<Profile>();
		UsersTableDbHelper helper = MyApplication.getDbHelper();
		// Select All Query
		String selectQuery = "SELECT " + UsersTableEntry.COLUMN_NAME_NAME
				+ " FROM " + UsersTableEntry.TABLE_NAME;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Profile contact = new Profile(cursor.getString(0));
				// contact.setNickName(cursor.getString(1));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}// return contact list
		return contactList;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Intent i = new Intent(ContactsActivity.this, ContactsActivity.class); // your class
		startActivity(i);
		finish();
	}
}
