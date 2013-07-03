package com.example.nylist;

import java.util.List;

public class Event {
	String _title;
	String _description;
	String _date;
	String _price;
	String _location;
	String _link;

	public Event() {
		
	}
	
	//Constructor
	public Event(String title, String description, String date, String price, String location, String link) {
		this._title = title;
		this._description = description;
		this._date = date;
		this._price = price;
		this._location = location;
	}

	
	//Getters
	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_description() {
		return _description;
	}

	public void set_description(String _description) {
		this._description = _description;
	}

	public String get_date() {
		return _date;
	}

	
	
	
	//Setters
	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_price() {
		return _price;
	}

	public void set_price(String _price) {
		this._price = _price;
	}

	public String get_location() {
		return _location;
	}

	public void set_location(String _location) {
		this._location = _location;
	}

	public String get_link() {
		return _link;
	}

	public void set_link(String _link) {
		this._link = _link;
	}
	
	
}
