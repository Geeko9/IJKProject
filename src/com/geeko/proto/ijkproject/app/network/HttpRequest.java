package com.geeko.proto.ijkproject.app.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.geeko.proto.ijkproject.app.data.GetUserInfo;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class HttpRequest {
	HttpClient client = new DefaultHttpClient();
	String url = "http://54.199.134.156:3000/ijk/";
	String res = null;
	private List<BasicNameValuePair> params;

	// params.add(new BasicNameValuePair("name", "value"));

	public String httpRequestPost(String path, String body) throws IOException {

		HttpPost post = new HttpPost(url + path);
		post.setHeader("Content-Type", "application/xml; charset=UTF-8");

		if (body != null) {
			StringEntity ent = new StringEntity(body, HTTP.UTF_8);
			post.setEntity(ent);
			System.out.println(body);
		} else {
			return null;
		}

		HttpResponse responsePost = client.execute(post);
		HttpEntity resEntity = responsePost.getEntity();

		if (resEntity != null) {
			res = EntityUtils.toString(resEntity, HTTP.UTF_8);
			// String res = EntityUtils.toString(resEntity);
			System.out.println(res);
			// return
			// String.valueOf(responsePost.getStatusLine().getStatusCode());
		}
		return String.valueOf(responsePost.getStatusLine().getStatusCode());
	}

	public String httpRequestDelete(String path, String type, String str)
			throws IOException {
		if (type.equals("account")) {
			String phoneno = "phoneno=" + new GetUserInfo().getOriginNumber();
			String passwd = "passwd=" + str;
			path = path + "?" + phoneno + "&" + passwd;
		}
		HttpDelete delete = new HttpDelete(url + path);
		Log.i("HttpRequest", url + path);
		HttpResponse responseDelete = client.execute(delete);
		HttpEntity resEntity = responseDelete.getEntity();
		if (resEntity != null) {
			res = EntityUtils.toString(resEntity, HTTP.UTF_8);
			// String res = EntityUtils.toString(resEntity);
			System.out.println(res);
			// return
			// String.valueOf(responseDelete.getStatusLine().getStatusCode());
		}
		//Log.i("HttpRequestDelete Log", "request tyep: " + delete.getMethod()  +" "+String.valueOf(responseDelete.getStatusLine().getStatusCode()));
		return String.valueOf(responseDelete.getStatusLine().getStatusCode());
	}

	public String httpRequestPut(String path, List nameValue, String body)
			throws IOException {
		// String testUrl = "http://54.199.134.156:3000/ijk/test/";
		//
		HttpPut put = new HttpPut(url + path);
		put.setHeader("Content-Type", "application/xml; charset=UTF-8");
		// HttpPost post = new HttpPost(testUrl);

		if (nameValue != null) {
			// params.add(new BasicNameValuePair("PhoneNumber", "01012345678"));
			// UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
			// HTTP.UTF_8);
			// put.setEntity(ent);
		} else if (body != null) {
			StringEntity ent = new StringEntity(body, HTTP.UTF_8);
			put.setEntity(ent);
			System.out.println(body);
		} else {
			return null;
		}

		HttpResponse responsePost = client.execute(put);
		HttpEntity resEntity = responsePost.getEntity();

		if (resEntity != null) {
			String res = EntityUtils.toString(resEntity, HTTP.UTF_8);
			// String res = EntityUtils.toString(resEntity);
			// System.out.println(res);
			return String.valueOf(responsePost.getStatusLine().getStatusCode());
		}
		return String.valueOf(responsePost.getStatusLine().getStatusCode());
	}

	public String httpRequestGet(String path) throws IOException {
		HttpGet get = new HttpGet(url + path);
		// ?��?
		HttpResponse responseGet = client.execute(get);
		HttpEntity resEntity = responseGet.getEntity();
		if (resEntity != null) {
			String res = EntityUtils.toString(resEntity, HTTP.UTF_8);
			// String res = EntityUtils.toString(resEntity);
			// System.out.println(res);
			return res;
		}
		return null;
	}

	public String getRes() {
		return this.res;
	}
}