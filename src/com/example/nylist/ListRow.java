package com.example.nylist;

import android.content.Context;
import android.view.View;

public class ListRow extends View {
	public String title;
	public String date;
	public String time;
	public String price;
	public String location;
	public String ticketLink;
	public int index;
	
	public ListRow() {
		super(null);
	}
	
	public ListRow(Context context, String title, String date, String time, String price, String location, String ticketLink, int index) {
		super(context);
		this.title = title;
		this.date = date;
		this.time = time;
		this.price = price;
		this.location = location;
		this.ticketLink = ticketLink;
		this.index = index;
		
	}
	
	//Getter methods
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
	    	return time;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getTicketLink() {
	    	return ticketLink;
	}
	
	
	//Setter methods
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setTime(String time) {
	    	this.time = time;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setTicketLink(String link) {
	    	this.ticketLink = link;
	}
	
}
