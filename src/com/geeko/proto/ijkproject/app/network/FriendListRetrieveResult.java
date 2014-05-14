package com.geeko.proto.ijkproject.app.network;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 */
@Root
public class FriendListRetrieveResult {

	@ElementList
	private List<Profile> FriendList;


	public List<Profile> getFriendList() {
		return FriendList;
	}
}