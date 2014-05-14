package com.geeko.proto.ijkproject.app.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.geeko.proto.ijkproject.app.MyApplication;

public class MyCustomWidget extends AppWidgetProvider {

	private static final String TAG = "MyCustomWidget";
	private Context context = MyApplication.getContext();

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

}
