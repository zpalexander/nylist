package com.example.nylist;

import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EventChild extends Activity{
    
    public String titleString;
    public String dateString;
    public String timeString;
    public String locationString;
    public String priceString;
    public String blogString;
    public String ticketLinkString;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.event_child);
		
	Typeface titleType = Typeface.createFromAsset(this.getAssets(), "fonts/Raleway-Light.otf");
	Typeface textType = Typeface.createFromAsset(this.getAssets(), "fonts/helveticaneue-webfont.ttf");

	//Retrieve event values from previous activity
	Bundle extras = getIntent().getExtras();
	String[] values = extras.getStringArray("values");
	
	//Set public values
	titleString = values[0];
	dateString = values[1];
	timeString = values[2];
	locationString = values[3];
	priceString = values[4];
	blogString = values[5];
	ticketLinkString = values[6];


	TextView name = (TextView)findViewById(R.id.title);
		name.setText(titleString);
		name.setTypeface(titleType);
		
	TextView date=(TextView)findViewById(R.id.child_date);
		date.setText(dateString+" at "+timeString);
		date.setTypeface(textType);
		
	TextView location=(TextView)findViewById(R.id.child_location);
		location.setText(locationString);
		location.setTypeface(textType);

	TextView price=(TextView)findViewById(R.id.child_price);
		price.setText(priceString);
		price.setTypeface(textType);
		
	//Set blog name in action bar
	getActionBar().setTitle((CharSequence) blogString);
	
		
	//Add fonts to buttons
	Button calendarButton = (Button) findViewById(R.id.calendar_button);
		calendarButton.setTypeface(textType);
		
	Button directionsButton = (Button) findViewById(R.id.directions_button);
		directionsButton.setTypeface(textType);
		
	Button ticketsButton = (Button) findViewById(R.id.tickets_button);
		ticketsButton.setTypeface(textType);
		
    }
	
	/*
	 * Button functions
	 */
	
	@SuppressLint("NewApi")
	public void addToCalendar(View view) {
	    if ((dateString != null) && (timeString != null)) {
        	    //Parse date 
        	    String dateDelim = "/";
        	    String[] dateTokens = dateString.split(dateDelim);
        	    int month = Integer.parseInt(dateTokens[0]);
        	    int day = Integer.parseInt(dateTokens[1]);
        	    
        	    //Parse time
        	    String timeDelim = ":";
        	    String[] timeTokens = timeString.split(timeDelim);
        	    int startHour = Integer.parseInt(timeTokens[0]);
        	    char[] minuteCharArray = timeTokens[1].toCharArray();
        	    String minutes = new StringBuilder().append(minuteCharArray[0]).append(minuteCharArray[1]).toString();
        	    int startMinute = Integer.parseInt(minutes);
        
        	    Calendar beginTime = Calendar.getInstance();
        	    beginTime.set(2013, (month-1), day, startHour, startMinute);
        	    Calendar endTime = Calendar.getInstance();
        	    endTime.set(2013, (month-1), day, (startHour+2), startMinute);
        	    
        	    Intent calendarIntent = new Intent(Intent.ACTION_EDIT);
        	    calendarIntent.setType("vnd.android.cursor.item/event");
        	    calendarIntent.putExtra(Events.TITLE, titleString);
        	    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
        	                        beginTime.getTimeInMillis());
        	    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
        	                        endTime.getTimeInMillis());
        	    calendarIntent.putExtra(Events.ALL_DAY, false);// periodicity
        	    //calendarIntent.putExtra(Events.DESCRIPTION,strDescription));
        	    calendarIntent.putExtra(Events.EVENT_LOCATION, locationString);
        
        	    startActivity(calendarIntent);
	    }
	}
	
	
	public  void viewLocation(View view) {
	    String delim = "[ ]";
	    String[] tokens = locationString.split(delim);
	    String temp = "";
	    for (int i=0; i<tokens.length; i++) {
		temp = temp + tokens[i] + "+";
	    }
	    String query = ("geo:0,0?q="+temp+",+New+York,+New+York");
	    Uri locationUri = Uri.parse(query);
	    Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
	    startActivity(mapIntent);
	}
	
	
	public void buyTickets(View view) {
	    goToUrl (ticketLinkString);
	}
	
	private void goToUrl (String url) {
	    if (url != null) {
	        Uri uriUrl = Uri.parse(url);
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
	    }
	}
}