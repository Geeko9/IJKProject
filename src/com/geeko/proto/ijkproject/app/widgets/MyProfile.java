package com.geeko.proto.ijkproject.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geeko.proto.ijkproject.R;

public class MyProfile extends LinearLayout {

	LayoutInflater inflater = null;
	ImageView iv_image;
	TextView tv_region, tv_name, tv_message;
	
	public MyProfile(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyProfile(Context context){
		super(context);
	}
	
	void initViews(){
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_myprofile_simple, this, true);
		iv_image = (ImageView) findViewById(R.id.iv_Main_SimpleProfile_Image);
	}

}
