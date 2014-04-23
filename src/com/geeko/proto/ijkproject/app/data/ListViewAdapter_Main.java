package com.geeko.proto.ijkproject.app.data;

import java.util.ArrayList;

import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;

/**
 * Created by Seonyong on 2014-03-26.
 */
public class ListViewAdapter_Main extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<Item> mListData = new ArrayList<Item>();

    public ListViewAdapter_Main(Context mContext) {
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

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_main, null);

            holder.mIcon = (ImageView) convertView.findViewById(R.id.ivItem);
            holder.mNameNPhone = (TextView) convertView.findViewById(R.id.tvItemNameNPhone);
            holder.mLocation = (TextView) convertView.findViewById(R.id.tvItemLoc);
            holder.mMachine = (TextView) convertView.findViewById(R.id.tvItemMachine);
            holder.phoneCall = (ImageView) convertView.findViewById(R.id.ibItemCall);
            holder.sendSMS = (ImageView) convertView.findViewById(R.id.ibItemSms);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Item mData = mListData.get(position);

        if (mData.getIcon() != null) {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.getIcon());
        } else {
            holder.mIcon.setVisibility(View.GONE);
        }

        holder.mNameNPhone.setText(mData.getName() + " " + mData.getPhone());
        holder.mLocation.setText(mData.getLocation());
        holder.mMachine.setText(mData.getMachine());

        holder.phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + mData.getPhone())));
            }
        });

        holder.sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + mData.getPhone())));
            }
        });

        if(mData.getStatus().equals("busy")) {
            convertView.setBackgroundColor(0x55F84322);
        }else if(mData.getStatus().equals("free")){
            convertView.setBackgroundColor(0x5540B553);
        }

        return convertView;
    }

    public void addItem(Drawable icon, String name, String phone, String location, String machine, String status) {
        Item addInfo = null;
        addInfo = new Item();
        addInfo.setIcon(icon);
        addInfo.setName(name);
        addInfo.setPhone(phone);
        addInfo.setLocation(location);
        addInfo.setMachine(machine);
        addInfo.setStatus(status);

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
        public ImageView mIcon;
        public TextView mNameNPhone;
        public TextView mLocation;
        public TextView mMachine;
        public ImageView phoneCall;
        public ImageView sendSMS;
    }
}
