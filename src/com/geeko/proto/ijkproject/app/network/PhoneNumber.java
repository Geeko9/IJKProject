package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "PhoneNumber")
public class PhoneNumber {

	private String PhoneNumber;

	@Text
	public String getPhoneNumber() {
		return PhoneNumber;
	}

	@Text
	public void setPhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}

	public PhoneNumber(String phoneNumber) {
		setPhoneNumber(phoneNumber);
	}
}
