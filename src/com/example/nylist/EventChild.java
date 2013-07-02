package com.example.nylist;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EventChild extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_child);
		
		//Retrieve event values from previous activity
		Bundle extras = getIntent().getExtras();
		String[] values = extras.getStringArray("values");
		
		TextView name = (TextView)findViewById(R.id.title);
				name.setText(values[0]);
		
		TextView date=(TextView)findViewById(R.id.child_date);
				date.setText(values[1]);
		
		TextView location=(TextView)findViewById(R.id.child_location);
				location.setText(values[2]);
		
		TextView price=(TextView)findViewById(R.id.child_price);
				price.setText(values[3]);
				
		getActionBar().setTitle((CharSequence) values[4]);
		
	}
}