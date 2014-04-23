package com.geeko.proto.ijkproject.app.data;

import java.util.ArrayList;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;

/**
 * Created by Seonyong on 2014-03-26.
 */
public class ListViewAdapter_Contacts extends BaseAdapter {
	private Context mContext = null;
	private ArrayList<Item> mListData = new ArrayList<Item>();

	public ListViewAdapter_Contacts(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_contacts_manage, null);

			holder.mName = (TextView) convertView.findViewById(R.id.tvItemName);
			holder.mPhone = (TextView) convertView
					.findViewById(R.id.tvItemPhone);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Item mData = mListData.get(position);

		holder.mName.setText(mData.getName() + " " + mData.getPhone());
		holder.mPhone.setText(mData.getLocation());

		return convertView;
	}

	public void addItem(String name, String phone) {
		Item addInfo = null;
		addInfo = new Item();
		addInfo.setName(name);
		addInfo.setPhone(phone);

		mListData.add(addInfo);
	}

	public void remove(int position) {
		mListData.remove(position);
		dataChange();
	}

	public void dataChange() {
		this.notifyDataSetChanged();
	}

	private class ViewHolder {
		public TextView mName;
		public TextView mPhone;
		public CheckBox mCheckBox;
	}
}