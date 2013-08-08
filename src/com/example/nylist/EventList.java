package com.example.nylist;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class EventList extends Activity {

    //The view for the drawer
    private ListView drawerList;
    
    DrawerLayout drawer;

    //The view for the list
    private ListView listView;

    //The blogs included in the drawer
    private String[] blogs = {"Favorites","Oh My Rockness","artcards","Brooklyn Vegan","My Social List", "Village Voice"};
    public String currentBlog = "NYList";
    public int positionInBlogArray = -1;
    
    //The array holding the current list's objects
    public ListRow[] eventArray;
	
    ActionBar actionBar;
    ActionBarDrawerToggle drawerToggle;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_event_list);
	setStyling(savedInstanceState);
	setupDrawer(savedInstanceState);
    }

    
    @SuppressLint("NewApi")
    public void setupDrawer(Bundle bundle) {
	//Create the drawer
    	drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    	drawerList = (ListView) findViewById(R.id.left_drawer);
    	drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, blogs));
    	
    	
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    	// ActionBarDrawerToggle ties together the the proper interactions
    	// between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
        			drawer, /* DrawerLayout object */
        			R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
        			R.string.drawer_open, /* "open drawer" description for accessibility */
        			R.string.drawer_close /* "close drawer" description for accessibility */
            ) {
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu(); // creates call to
                                             // onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    invalidateOptionsMenu(); // creates call to
                                             // onPrepareOptionsMenu()
                }
            };
            
    		
    	//Set the onClickListener for the drawer
    	drawerList.setOnItemClickListener(new OnItemClickListener()
    	    {
    	        @Override
    	        public void onItemClick(AdapterView<?> parent,
    	                View view, final int pos, long id)
    	        {
    	            populateList(pos);
    	            positionInBlogArray = pos;
    	            drawer.setDrawerListener(
    	        	    new DrawerLayout.SimpleDrawerListener() {
    	        		public void onDrawerClosed(View drawerView) {
    	        		    super.onDrawerClosed(drawerView);
    	        		}
    	        	    }
    	            );
    	            drawer.closeDrawer(drawerList);
    	        }
    	    });
    	//drawer.openDrawer(drawerList);
    	}
    	
    	@SuppressLint("NewApi")
	public void setStyling(Bundle bundle) {
        	//Set font in action bar
        	Typeface actionBarType = Typeface.createFromAsset(this.getAssets(), "fonts/Raleway-Medium.otf");
        	final int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        	TextView title = (TextView) getWindow().findViewById(titleId);
        	title.setTypeface(actionBarType);
        	actionBar = getActionBar();
        	//actionBar.setDisplayShowHomeEnabled(false);
        	actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        	actionBar.setDisplayHomeAsUpEnabled(true);
        	actionBar.setDisplayUseLogoEnabled(false);
        	actionBar.setDisplayShowTitleEnabled(true);
    	}
    
    
    	public void setList(ListRow[] listrowData) {
    	    ListRowAdapter adapter = new ListRowAdapter(this, R.layout.list_row, listrowData);
	    listView = (ListView)findViewById(R.id.list);
	    listView.setAdapter(adapter);
	    eventArray = listrowData;
    	}
    	
    	public void populateList(int p) {
    	if (isNetworkAvailable()) {
        	//This is where the switching will occur to decide which feed gets pulled 
        	switch (p) {
            		case 1: new OMRParser(EventList.this).execute(); break;
            	
            		case 2: new ArtCardsParser(EventList.this).execute(); break;
            	
            		case 3: new BVParser(EventList.this).execute(); break;
            	
            		case 4: new MSLParser(EventList.this).execute(); break;
            	
            		default: break;
        	}
        	currentBlog = blogs[p];
        	actionBar.setTitle(currentBlog);
            }
    	}
    	
    	private boolean isNetworkAvailable() {
    	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    	}
    	                                      

	public void openChild(View view) {
	    Intent intent = new Intent(EventList.this, EventChild.class);
	    String[] values = new String[7];
	    TextView eventIndexTV = (TextView) view.findViewById(R.id.indexView);
	    String eventIndexString = (String) eventIndexTV.getText();
	    int eventIndex = Integer.parseInt(eventIndexString);
	    ListRow eventRow = eventArray[eventIndex];
	    values[0] = eventRow.getTitle();
	    values[1] = eventRow.getDate();
	    values[2] = eventRow.getTime();
	    values[3] = eventRow.getLocation();
	    values[4] = eventRow.getPrice();
	    values[5] = currentBlog;
	    values[6] = eventRow.getTicketLink();
	    intent.putExtra("values", values);
	    startActivity(intent);
        };
        
	

	
        /* Called whenever we call invalidateOptionsMenu() */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // If the nav drawer is open, hide action items related to the content view
            boolean drawerOpen = drawer.isDrawerOpen(drawerList);
            //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }
        
        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            drawerToggle.syncState();
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            drawerToggle.onConfigurationChanged(newConfig);
        }
        
       
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}
	
	@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Pass the event to ActionBarDrawerToggle, if it returns
            // true, then it has handled the app icon touch event
            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // NavUtils.navigateUpFromSameTask(this);
                return true;
                
            case R.id.refresh: refreshList();
            
            case R.id.view_website: viewInBrowser(item.getActionView());
            }
            return super.onOptionsItemSelected(item);
        }
	
	
	
	public void refreshList() {
	    populateList(positionInBlogArray);
	}
	
	public void viewInBrowser(View view) {
	    if (positionInBlogArray != -1) {
		String[] blogURLs = {null, "http://www.ohmyrockness.com","http://artcards.cc","http://www.brooklynvegan.com","http://www.mysocialist.com", null};
		goToUrl(blogURLs[positionInBlogArray]);
	    }
	}
	
	private void goToUrl (String url) {
	    if (url != null) {
	        Uri uriUrl = Uri.parse(url);
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
	    }
	}


	
}