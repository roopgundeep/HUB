package com.example.drawernavigationtabs;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Menu {	
	private String title;   //done
	private String description = null; //done
	private String type;      //done
	private String url;       //done
	private String pubDate;   //done
	private String timestamp; //done 
	private String imageUrl;  //done
	private Bitmap image;   //done
	private String price;   //done
	private String saving; //done
	private String start_date;
	private String end_date;
	private String venue_name;
	private String venue_address;
	private String venue_city;
	private String venue_region;
	private String venue_country;
		
	public Menu()
	{
		this.price="initial";
		this.title="initial";
		this.description="initial";
		this.saving=null;
		this.image=null;
		this.type=null;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	public void setPrice(String price)
	{
		this.price=price;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public void setImageUrl(String imageUrl)
	{
		this.imageUrl=imageUrl;
	}
	public void setImage(Bitmap image)
	{
		this.image=image;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public void setSaving(String savings)
	{
		this.saving=savings;
	}
	public void setTimestamp(String timestamp)
	{
		this.timestamp=timestamp;
	}
	public void setPubdate(String pubDate)
	{
		this.pubDate=pubDate;
	}
	public void setUrl(String url)
	{
		this.url=url;
	}
	public void setEnddate(String end_date)
	{
		this.end_date=end_date;
	}
	public void setStartdate(String start_date)
	{
		this.start_date=start_date;
	}
	public void setVenueaddress(String venu_address)
	{
		this.venue_address=venue_address;
	}
	public void setVenueCity(String venue_city)
	{
		this.venue_city=venue_city;
	}
	public void setVenueCountry(String venue_country)
	{
		this.venue_country=venue_country;
	}
	public void setVenueName(String venue_name)
	{
		this.venue_name=venue_name;
	}
	public void setVenueRegion(String venue_region)
	{
		this.venue_region=venue_region;
	}
	public String getVenueRegion()
	{
		return this.venue_region;
	}
	public String getVenueName()
	{
		return this.venue_name;
	}
	public String getVenueCountry()
	{
		return this.venue_country;
	}
	public String getVenuecity()
	{
		return this.venue_city;
	}
	public String getVenueaddress()
	{
		return this.venue_address;
	}
	public String getStartDate()
	{
		return this.start_date;
	}
	public String getEnddate()
	{
		return this.end_date;
	}
	public String getUrl()
	{
		return this.url;
	}
	public String getpubDate()
	{
		return this.pubDate;
	}
	public String getTimestamp()
	{
		return this.timestamp;
	}
	public String getSaving()
	{
		return this.saving;
	}
	public String getDescription()
	{
		return this.description;
	}
	public String getPrice()
	{
		return this.price;
	}
	public String getTitle()
	{
		return this.title;
	}
	public String getType()
	{
		return this.type;
	}
	public String getImageUrl()
	{
		return this.imageUrl;
	}
	public Bitmap getImage()
	{
		return this.image;
	}

}
