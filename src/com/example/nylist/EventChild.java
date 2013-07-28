package com.example.nylist;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EventChild extends Activity{
    
    public String ticketLink;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_child);
		
		Typeface titleType = Typeface.createFromAsset(this.getAssets(), "fonts/Raleway-Light.otf");
		Typeface textType = Typeface.createFromAsset(this.getAssets(), "fonts/helveticaneue-webfont.ttf");

		//Retrieve event values from previous activity
		Bundle extras = getIntent().getExtras();
		String[] values = extras.getStringArray("values");
		
		TextView name = (TextView)findViewById(R.id.title);
				name.setText(values[0]);
				name.setTypeface(titleType);
		
		TextView date=(TextView)findViewById(R.id.child_date);
				date.setText(values[1]);
				date.setTypeface(textType);
		
		TextView location=(TextView)findViewById(R.id.child_location);
				location.setText(values[2]);
				location.setTypeface(textType);

		TextView price=(TextView)findViewById(R.id.child_price);
				price.setText(values[3]);
				price.setTypeface(textType);
		
		//Set blog name in action bar
		getActionBar().setTitle((CharSequence) values[4]);
		
		//Set ticket link
		ticketLink = values[5];
		
		//Add fonts to buttons
		Button calendarButton = (Button) findViewById(R.id.calendar_button);
				calendarButton.setTypeface(textType);
		
		Button directionsButton = (Button) findViewById(R.id.directions_button);
				directionsButton.setTypeface(textType);
		
		Button ticketsButton = (Button) findViewById(R.id.tickets_button);
				ticketsButton.setTypeface(textType);
		
	}
	
	
	public void buyTickets(View view) {
	    goToUrl (ticketLink);
	}
	
	private void goToUrl (String url) {
	        Uri uriUrl = Uri.parse(url);
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
	    }
}