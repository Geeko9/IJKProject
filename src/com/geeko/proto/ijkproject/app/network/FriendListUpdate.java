package com.geeko.proto.ijkproject.app.network;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class FriendListUpdate {

	@ElementList
	private List<PhoneNumber> NewFriend;

	@ElementList
	private List<PhoneNumber> DelFriend;

	public List<PhoneNumber> getNewFriend() {
		return NewFriend;
	}

	public List<PhoneNumber> getDelFriend() {
		return DelFriend;
	}

	public FriendListUpdate(List<PhoneNumber> listNew, List<PhoneNumber> listDel) {
		this.NewFriend = listNew;
		this.DelFriend = listDel;
	}

}
