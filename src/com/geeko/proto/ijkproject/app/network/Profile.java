package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
public class Profile implements Comparable<Profile> {

	@Element(required = false)
	private String PhoneNumber;

	@Element(required = false)
	private String NickName;

	@Element(required = false)
	private String Region;

	@Element(required = false)
	private String WorkingPeriod;

	@Element(required = false)
	private String UserStatus;

	private int _id;

	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public void setWorkingPeriod(String workingPeriod) {
		WorkingPeriod = workingPeriod;
	}

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

	public String getWorkingPeriod() {
		return WorkingPeriod;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	@Override
	public int compareTo(Profile another) {
		return UserStatus.compareTo(another.getUserStatus());
	}

	public Profile(String phoneNumber, String nickName, int _id) {
		this.setPhoneNumber(phoneNumber);
		this.setNickName(nickName);
		this.set_id(_id);
	}

	public Profile(String phoneNumber, String nickName) {
		this.setPhoneNumber(phoneNumber);
		this.setNickName(nickName);
	}

	public Profile(String nickName) {
		this.setNickName(nickName);
	}
	
	public Profile(){
		
	}
}
