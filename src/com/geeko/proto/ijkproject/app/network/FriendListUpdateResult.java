package com.geeko.proto.ijkproject.app.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class FriendListUpdateResult {
	@Element
	private String Status;

	public String getStatus() {
		return Status;
	}
}
