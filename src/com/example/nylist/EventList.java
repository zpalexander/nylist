package com.example.nylist;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class EventList extends Activity {

//The view for the drawer
private ListView mDrawerList;

//The view for the list
private ListView listView;

//The blogs included in the drawer
private String[] blogs = {"Favorites","Oh My Rockness","artcards","Brooklyn Vegan","Village Voice"};	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		
		//Create the drawer
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, blogs));
		
		
		//Populate the list
		int eventCount = 15;
		ListRow[] listrow_data = new ListRow[eventCount];
		ListRow temp;
		for (int i=0;i<eventCount;i++) {
			temp = new ListRow(this,"Event Title","1/1/13","$7","285 Kent",i);
			listrow_data[i] = temp;	
		}
		
		ListRowAdapter adapter = new ListRowAdapter(this, R.layout.list_row, listrow_data);
		listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);


	}

	

	

		public void openChild(View view) {
			Intent intent = new Intent(EventList.this, EventChild.class);
			String[] values = new String[4];
			TextView titleDisplay = (TextView) view.getRootView().findViewById(R.id.title);
			TextView dateDisplay = (TextView) view.getRootView().findViewById(R.id.date);
			TextView locationDisplay = (TextView) view.getRootView().findViewById(R.id.location);
			TextView priceDisplay = (TextView) view.getRootView().findViewById(R.id.price);
			values[0] = (String) titleDisplay.getText();
			values[1] = (String) dateDisplay.getText();
			values[2] = (String) locationDisplay.getText();
			values[3] = (String) priceDisplay.getText();
			intent.putExtra("values", values);
			startActivity(intent);
        };
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}


	
}