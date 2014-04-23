package com.geeko.proto.ijkproject.app.network;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class FriendListRetrieveResult {

	@Element
	private int Status;

	@ElementList
	private List<Profile> FriendList;

	public int getStatus() {
		return Status;
	}

	public List<Profile> getFriendList() {
		return FriendList;
	}
}