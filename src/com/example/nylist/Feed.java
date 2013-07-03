package com.example.nylist;

import java.util.List;

public class Feed {
	String _title;
	String _description;
	String _date;
	String _price;
	String _location;
	String _link;
	List<Event> _items;
	
	public Feed(String title, String description, String date, String price, String location, String link) {
		this._title = title;
		this._description = description;
		this._date = date;
		this._price = price;
		this._location = location;
	}
	
	public void setItems(List<Event> items) {
        this._items = items;
    }
	
	
	//Getters
	public String get_title() {
		return _title;
	}

	public String get_description() {
		return _description;
	}

	public String get_date() {
		return _date;
	}

	public String get_price() {
		return _price;
	}

	public String get_location() {
		return _location;
	}

	public String get_link() {
		return _link;
	}

	public List<Event> get_items() {
		return _items;
	}
}
