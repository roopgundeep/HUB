package com.example.drawernavigationtabs.data;

public class Griditem {
	
	public String name;
	public Integer imageId;
	public Integer index;
	
	public Griditem(){
		
	}
    public Griditem(String name, Integer imageId, Integer index) {        
    	this.name = name;
        this.imageId = imageId;
        this.index = index;
    }
    public void setName(String name){
    	this.name=name;
    }
    public String getName(){
    	return name;
    }
    public void setimageId(Integer imageId){
    	this.imageId=imageId;
    }
    public Integer getImageId(){
    	return imageId;
    }
    public void setIndex(Integer index){
    	this.index=index;
    }
    public Integer getIndex(){
    	return index;
    }
}
