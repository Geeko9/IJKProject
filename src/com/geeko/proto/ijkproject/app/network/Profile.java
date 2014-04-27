package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class Profile implements Comparable<Profile> {

	@Element
	private String PhoneNumber;

	@Element(required = false)
	private String NickName;

	@Element(required = false)
	private String UserStatus;

	@Element(required = false)
	private String Region;

	@Element(required = false)
	private int WorkingPeriod;

	public void setPhoneNumber() {
		this.PhoneNumber = "0" + this.PhoneNumber.substring(2);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}

	public void setNickName(String nickName) {
		this.NickName = nickName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public String getNickName() {
		return NickName;
	}

	public String getUserStatus() {
		return UserStatus;
	}

	public String getRegion() {
		return Region;
	}

	public int getWorkingPeriod() {
		return WorkingPeriod;
	}

	@Override
	public int compareTo(Profile another) {
		return UserStatus.compareTo(another.getUserStatus());
	}

	public Profile(String phoneNumber, String nickName) {
		this.setPhoneNumber(phoneNumber);
		this.setNickName(nickName);
	}
}
