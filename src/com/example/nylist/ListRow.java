package com.example.nylist;

import android.content.Context;
import android.view.View;

public class ListRow extends View {
	public String title;
	public String date;
	public String price;
	public String location;
	public int index;
	
	public ListRow() {
		super(null);
	}
	
	public ListRow(Context context, String title, String date, String price, String location, int index) {
		super(context);
		this.title = title;
		this.date = date;
		this.price = price;
		this.location = location;
		this.index = index;
		
	}
	
	//Getter methods
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
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
	
	
	//Setter methods
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDate(String date) {
		this.date = date;
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
	
}
