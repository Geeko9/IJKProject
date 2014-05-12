package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
@Root(name = "Signup")
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
