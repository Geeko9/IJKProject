package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;

public class PhoneNumber {

	@Element
	private String PhoneNumber;

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public PhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}
}