package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ProfileUpdate")
public class ProfileUpdate {

	@Element
	private Profile Profile;

	public Profile getProfile() {
		return Profile;
	}

	public void setProfile(Profile profile) {
		Profile = profile;
	}

}
