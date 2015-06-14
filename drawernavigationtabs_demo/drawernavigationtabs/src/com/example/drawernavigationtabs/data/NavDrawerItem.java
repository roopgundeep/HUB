package com.example.drawernavigationtabs.data;

public class NavDrawerItem {
	
	private String title;
	private int icon;
	private String count = "0";
	private int profile=0;
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	
	public NavDrawerItem(){}

	public NavDrawerItem(String title, int icon, int profile){
		this.title = title;
		this.icon = icon;
		this.profile = profile;
	}
	
	public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count, int profile){
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
		this.profile = profile;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getProfile(){
		return this.profile;
	}
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.isCounterVisible;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setProfile(int profile){
		this.profile = profile;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisibility(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}

}
