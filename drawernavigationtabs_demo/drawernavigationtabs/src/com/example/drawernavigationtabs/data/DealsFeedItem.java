package com.example.drawernavigationtabs.data;

public class DealsFeedItem {
	private int id,globalID, categoryID,freshness;
	private String name, status, image, profilePic, timeStamp, url, category;
	private String created_time,sourceName, pictureFB, message,link,description,sourceImage; 
	private Double rating;
	private String facebookFriend1,facebookFriend2,facebookFriend3;
	private String distance;
	private String phrase;
	private String dealArray,phoneNumber,address,cannonical_url,suggestionArray;
	private boolean dealFlag;
	public DealsFeedItem() {
	}

	public DealsFeedItem(int id, String name, String image, String status,
			String profilePic, String timeStamp, String url) {
		super();
		this.id = id;		
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
		this.facebookFriend1=null;
		this.facebookFriend2=null;
		this.dealFlag=false;
		this.cannonical_url=null;
		this.facebookFriend3=null;
		this.distance=null;
		this.phrase=null;		
	}
	public void setSuggestionArray(String suggestionArray) {
		// TODO Auto-generated method stub
		this.suggestionArray = suggestionArray;
	}
	public String getSuggestionArray() {
		return this.suggestionArray;
	}
	public void setcanonicalUrl(String cannonical_url) {
		// TODO Auto-generated method stub
		this.cannonical_url=cannonical_url;
		
	}
	public String getcanonicalUrl() {
		// TODO Auto-generated method stub
		return cannonical_url;
	}
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getDealArray() {
		return this.dealArray;
	}

	public void setDealArray(String dealArray) {
		this.dealArray = dealArray;
	}
	
	public String getDistance() {
		return this.distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public String getPhrase() {
		return this.phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getFacebookFriend1() {
		return this.facebookFriend1;
	}

	public void setFacebookFriend1(String FacebookFriend) {
		this.facebookFriend1 = FacebookFriend;
	}
	public String getFacebookFriend2() {
		return this.facebookFriend2;
	}

	public void setFacebookFriend2(String FacebookFriend) {
		this.facebookFriend2 = FacebookFriend;
	}
	public String getFacebookFriend3() {
		return this.facebookFriend3;
	}

	public void setFacebookFriend3(String FacebookFriend) {
		this.facebookFriend3 = FacebookFriend;
	}
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getDealFlag() {
		return this.dealFlag;
	}

	public void setDealFlag(boolean dealFlag) {
		this.dealFlag = dealFlag;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImge() {
		return image;
	}

	public void setImge(String image) {
		this.image = image;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getGlobalId() {
		return globalID;
	}

	public void setGlobalId(int gloablID) {
		this.globalID = gloablID;
	}
	
	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getFreshness() {
		return freshness;
	}

	public void setFreshness(int freshness) {
		this.freshness = freshness;
	}
	
	public String getCreatedTime() {
		return created_time;
	}

	public void setCreatedTime(String timestamp) {
		this.created_time = timestamp;
	}
	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public String getPictureFB() {
		return pictureFB;
	}

	public void setPictureFB(String pictureFB) {
		this.pictureFB = pictureFB;
	}
	
	public String getMessage() {
		return message;		
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getLink() {
		return link;		
	}

	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;		
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSourceImage() {
		return sourceImage;		
	}

	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
	}

	
	
}
