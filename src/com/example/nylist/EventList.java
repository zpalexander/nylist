package com.example.nylist;



import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.*;


public class EventList extends Activity {

    //The view for the drawer
    private ListView mDrawerList;

    //The view for the list
    private ListView listView;

    //The blogs included in the drawer
    private String[] blogs = {"Favorites","Oh My Rockness","artcards","Brooklyn Vegan","Village Voice"};
    public String currentBlog = "NYList";
    
    //The array holding the current list's objects
    public ListRow[] eventArray;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_event_list);
	//Set font in action bar
	Typeface actionBarType = Typeface.createFromAsset(this.getAssets(), "fonts/Raleway-Medium.otf");
	final int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
	TextView title = (TextView) getWindow().findViewById(titleId);
	title.setTypeface(actionBarType);
		
	//Create the drawer
	final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
	mDrawerList = (ListView) findViewById(R.id.left_drawer);
	mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, blogs));
		
	//Set the onClickListener for the drawer
	mDrawerList.setOnItemClickListener(new OnItemClickListener()
	    {
	        @Override
	        public void onItemClick(AdapterView<?> parent,
	                View view, final int pos, long id)
	        {
	            drawer.setDrawerListener(
	                new DrawerLayout.SimpleDrawerListener()
	            {
	                public void onDrawerClosed(View drawerView)
	                {
	                	//Code for dynamically generating list goes here!!!
	                    super.onDrawerClosed(drawerView);
	                    currentBlog = blogs[pos];
	            		getActionBar().setTitle(currentBlog);
	                }
	            });
	            drawer.closeDrawer(mDrawerList);
	        }
	    });
	
	//Test 
	new OMRParser(this).execute();

	}

    
    
    
    	public void setList(ListRow[] listrowData) {
    	    ListRowAdapter adapter = new ListRowAdapter(this, R.layout.list_row, listrowData);
	    listView = (ListView)findViewById(R.id.list);
	    listView.setAdapter(adapter);
	    eventArray = listrowData;
    	}
    	                                          
	
	public void testPopulate() {
	    //Populate the list
	    int eventCount = 15;
	    ListRow[] listrow_data = new ListRow[eventCount];
	    ListRow temp;
	    for (int i=0;i<eventCount;i++) {
		temp = new ListRow(this,"Event Title","1/1/13","$7","285 Kent","www.ohmyrockness.com", i);
		listrow_data[i] = temp;	
	    }
		
	    ListRowAdapter adapter = new ListRowAdapter(this, R.layout.list_row, listrow_data);
	    listView = (ListView)findViewById(R.id.list);
	    listView.setAdapter(adapter);
	}
	
	

	public void openChild(View view) {
	    Intent intent = new Intent(EventList.this, EventChild.class);
	    String[] values = new String[6];
	    TextView eventIndexTV = (TextView) view.findViewById(R.id.indexView);
	    String eventIndexString = (String) eventIndexTV.getText();
	    int eventIndex = Integer.parseInt(eventIndexString);
	    ListRow eventRow = eventArray[eventIndex];
	    values[0] = eventRow.getTitle();
	    values[1] = eventRow.getDate();
	    values[2] = eventRow.getLocation();
	    values[3] = eventRow.getPrice();
	    values[4] = currentBlog;
	    values[5] = eventRow.getTicketLink();
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