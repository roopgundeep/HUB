package com.example.drawernavigationtabs.data;

public class NewsFeedItem {
	private int id,globalID, categoryID,freshness;
	private String name, status, image, profilePic, timeStamp, url, category;
	private String created_time,sourceName, pictureFB, message,link,description,sourceImage; 
	private String subCategory,MainImage, facebookFriend1,facebookFriend2,facebookFriend3,suggestionArray;
	public NewsFeedItem() {
	}

	public NewsFeedItem(int id, String name, String image, String status,
			String profilePic, String timeStamp, String url) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.facebookFriend1=null;
		this.facebookFriend2=null;
		this.facebookFriend3=null;
		this.category=null;
		this.created_time=null;
		this.sourceName=null;
		this.pictureFB=null;
		this.message=null;
		this.link=null;
		this.description=null;
		this.sourceImage=null;
		this.MainImage=null;
		this.suggestionArray=null;		
		this.url=null;
	}
	public void setSuggestionArray(String suggestionArray) {
		// TODO Auto-generated method stub
		this.suggestionArray = suggestionArray;
	}
	public String getSuggestionArray() {
		return this.suggestionArray;
	}
	public String getFacebookFriend3() {
		return facebookFriend3;
	}

	public void setFacebookFriend3(String facebookFriend3) {
		this.facebookFriend3 = facebookFriend3;
	}
	
	public String getFacebookFriend2() {
		return facebookFriend2;
	}

	public void setFacebookFriend2(String facebookFriend2) {
		this.facebookFriend2 = facebookFriend2;
	}
	
	public String getFacebookFriend1() {
		return facebookFriend1;
	}

	public void setFacebookFriend1(String facebookFriend1) {
		this.facebookFriend1 = facebookFriend1;
	}
	
	public String getMainImage() {
		return MainImage;
	}

	public void setMainImage(String image) {
		this.MainImage = image;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
