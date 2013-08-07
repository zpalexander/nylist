package com.example.nylist;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MSLParser extends AsyncTask<Void, Void, String[][]>{
    private static String TAG_EVENT = "tr";
    private static String TAG_TITLE = ".artist";
    private static String TAG_LOCATION = ".listingLocation";
    private static String TAG_PRICE = ".listingPrice";
    private static String TAG_DATE = ".date-row";
    private static String TAG_DATE_PINPOINT = "td";
    private static String TAG_EVENT_URL = "listingPrice p";
    private static String FEED_URL = "http://www.mysocialist.com/concerts";
    Context context;
    Activity activity;
    ProgressDialog dialog;

    public MSLParser(Activity context) {
	this.context = context.getApplicationContext();
	this.activity = context;
    }
    
    

    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       dialog = ProgressDialog.show(activity, "","Loading", true);
    }

    @Override
    protected String[][] doInBackground(Void... param) {
	String values[][] = new String[50][6];
	try {
	    values = getFeedItems();
	} 
	catch (IOException e) {
	    e.printStackTrace();
	}
	return values;
    }
    
    protected void onPostExecute(String[][] result) {
	super.onPostExecute(result);
	int eventCount = result.length;
	ListRow[] listrow_data = new ListRow[eventCount];
	ListRow temp;
	for (int i=0; i<eventCount; i++) {
	    temp = new ListRow(context, result[i][0], result[i][1], result[i][2], 
		    result[i][3], result[i][4], result[i][5], i);
	    listrow_data[i] = temp;
	}
	((EventList) activity).setList(listrow_data);    
	dialog.dismiss();
    }

    
    public static String[][] getFeedItems() throws IOException {

	Document doc = null;
	String values[][] = new String[50][6];
	Date dateObject = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
	String date = sdf.format(dateObject);


	try{
	    doc = Jsoup.connect(FEED_URL).timeout(0).get();
	    Elements events = doc.select(TAG_EVENT);
	    String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August",       
		    "September", "October", "November", "December"};
	    String delimSpace = "[ ]";
	    int i = 0;
	    for (Element event : events) {
		
		//First check to make sure the array isn't full
		if (values[49][0] != null) {
		    break;
		}
		
		//Check to make sure that the tr isn't a filler
		String className = event.className();
		if (className.contains("listingHeaderRow") || className.contains("full-table-breaker")) {
		    continue;
		}

		//If the tr isn't a filler, check to make sure that it isn't a date, if so, set variable
		if (className.contains("date-row")) {
		    String dateHolder = event.select(TAG_DATE_PINPOINT).text();
		    String[] dateTokens = dateHolder.split(delimSpace);
		    String monthString = dateTokens[2].replaceAll(",","");
		    int monthInt = 0;
		    for(int a=0; a < 12 ; a++) {
			if (monthArray[a].equals(monthString)) {
			    monthInt = (a + 1);
			}
		    }
		    date = Integer.toString(monthInt) + "/" + dateTokens[3];
		}

		//Set event title
		String title = event.select(TAG_TITLE).text();
		values[i][0] = title;

		//Set event date
		values[i][1] = date;

		//Set price
		values[i][3] = event.select(TAG_PRICE).text();
		
		//Set location
		values[i][4] = event.select(TAG_LOCATION).text();
    
		//Set ticket urls
		Elements ticketElements = event.select(TAG_EVENT_URL);
		String ticketURL = null;
		for (Element ticketElement : ticketElements) {
		    ticketURL = ticketElement.attr("abs:href");
		}
		values[i][5] = ticketURL;
		i++;
	    } //End of event loop
	} //End of try clause
	    
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	return values;
    }


}

