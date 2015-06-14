package com.example.drawernavigationtabs.data;

public class EventsFeedItem {
	private int id,globalID, categoryID,freshness;
	private String name, status, image, profilePic, timeStamp, url, category;
	private String created_time,sourceName, pictureFB, message,link,description,sourceImage; 
	private String VenueName;
	private String startTime;
	private String endTime,address;
	private String organizerName,suggestionArray;
	public EventsFeedItem() {
	}

	public EventsFeedItem(int id, String name, String image, String status,
			String profilePic, String timeStamp, String url) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.status = status;
		this.profilePic = profilePic;
		this.timeStamp = timeStamp;
		this.url = url;
	}
	public void setSuggestionArray(String suggestionArray) {
		// TODO Auto-generated method stub
		this.suggestionArray = suggestionArray;
	}
	public String getSuggestionArray() {
		return this.suggestionArray;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getOrganizerName() {
		return this.organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getVenue() {
		return this.VenueName;
	}

	public void setVenue(String VenueName) {
		this.VenueName = VenueName;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
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
