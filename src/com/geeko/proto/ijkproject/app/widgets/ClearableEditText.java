package com.geeko.proto.ijkproject.app.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geeko.proto.ijkproject.R;

/**
 * Customizing LinearLayout
 * 
 * This class is used to customize LinearLayout. This layout can clear text in
 * EditText(et_text) using ImageView(iv_clear).
 * 
 * @author Kim Seonyong
 * @version 1.1 April 20 2014
 * @since 1.1
 */
public class ClearableEditText extends LinearLayout {

	LayoutInflater inflater = null;
	EditText et_text;
	ImageView iv_clear;

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	public ClearableEditText(Context context) {
		super(context);
		initViews();
	}

	void initViews() {
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.clearable_edit_text, this, true);
		et_text = (EditText) findViewById(R.id.et_Clearable_Search);
		iv_clear = (ImageView) findViewById(R.id.iv_Clearable_clear);
		iv_clear.setVisibility(LinearLayout.INVISIBLE);
		clearText();
		showHideClearButton();
	}

	void clearText() {
		iv_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_text.setText("");
			}
		});
	}

	void showHideClearButton() {
		et_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0)
					iv_clear.setVisibility(LinearLayout.VISIBLE);
				else
					iv_clear.setVisibility(LinearLayout.INVISIBLE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public Editable getText() {
		Editable text = et_text.getText();
		return text;
	}

	public void setHint(String hint) {
		et_text.setHint(hint);
	}

	public void setInputType(String type) {
		if (type.equals("password"))
			et_text.setInputType(0x00000081);
	}

	public int getTextSize() {
		return et_text.getText().length();
	}

	public void setMaxLength(int length) {
		et_text.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				length) });
	}
}
