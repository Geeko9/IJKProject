package com.geeko.proto.ijkproject.app.services;

import java.util.List;

import android.os.AsyncTask;

/**
 * @author Kim Seonyong
 * @version 1.1 April 20 2014
 * @since 1.1
 */
public class MAsyncTask extends AsyncTask<Void, Void, Void> {

	String str;
	List<?> list;

	public MAsyncTask() {
		this.str = null;
		this.list = null;
	}

	public MAsyncTask(List<?> list, String string) {
		this.str = string;
		this.list = list;
	}

	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

	}

}
