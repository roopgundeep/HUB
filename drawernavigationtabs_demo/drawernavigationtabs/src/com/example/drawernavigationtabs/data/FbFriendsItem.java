package com.example.drawernavigationtabs.data;

public class FbFriendsItem {

	private String id;
	private String name;

	private String count = "0";
	private int profile = 0;
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;

	public FbFriendsItem() {
	}

	public FbFriendsItem(String name, String id) {
		this.name = name;
		this.id = id;

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
