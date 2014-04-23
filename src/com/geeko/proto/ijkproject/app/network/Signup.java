package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Signup {

	@Element
	private String PhoneNumber;

	@Element
	private String Nickname;

	@Element
	private String Password;

	public Signup(String PhoneNumber, String Nickname, String Password) {
		this.PhoneNumber = PhoneNumber;
		this.Nickname = Nickname;
		this.Password = Password;
	}
}
