package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Root;

public class PhoneNumber {

	@Root
	private String PhoneNumber;

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public PhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}
}