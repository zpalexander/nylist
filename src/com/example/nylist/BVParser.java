package com.example.nylist;

import java.io.IOException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BVParser extends AsyncTask<Void, Void, String[][]> {
    static String TAG_EVENT = "li.ds-entry";
    static String TAG_TITLE = ".ds-entry-title";
    static String TAG_LOCATION = ".location";
    static String TAG_DATE_AND_TIME = ".ds-date";
    static String TAG_TICKET_URL = ".ds-buy-tickets";
    static String FEED_URL = "http://nyc-shows.brooklynvegan.com/";
    Context context;
    Activity activity;
    ProgressDialog dialog;

    public BVParser(Activity context) {
	this.context = context.getApplicationContext();
	this.activity = context;
    } 
    
    

    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       dialog = ProgressDialog.show(activity, "", "Loading",true);
    }

    @Override
    protected String[][] doInBackground(Void... param) {
	String values[][] = new String[50][6];
	try {
	    values = getFeedItems();
	} 
	catch (IOException e) {
	    Log.d("ASSERT", "Exception occured during doInBackground", e);
	    e.printStackTrace();
	}

	Log.d("ASSERT", ("values successfully returned by doInBackground, first title is: "+values[0][0]));
	return values;
    }
    
    protected void onPostExecute(String[][] result) {
	super.onPostExecute(result);
	int eventCount = result.length;
	Log.d("ASSERT", ("event count in onPostExecute is: "+eventCount));
	ListRow[] listrow_data = new ListRow[eventCount];
	ListRow temp;
	for (int i=0; i<eventCount; i++) {
	    if (result[i] != null) {
		temp = new ListRow(context, result[i][0], result[i][1], result[i][2], 
		    result[i][3], result[i][4], result[i][5], i);
		listrow_data[i] = temp;
	    }
	}
	((EventList) activity).setList(listrow_data);    
	dialog.dismiss();
    }


    public String[][] getFeedItems() throws IOException {

	Document doc = null;
	String values[][] = new String[50][6];
	int i = 0;

	try{
	    Log.d("ASSERT","Made it to try block");
	    doc = Jsoup.connect(FEED_URL).timeout(0).get();
	    Elements events = doc.select(TAG_EVENT);
		Log.d("ASSERT","printing events, whatever it is: "+events);

	    String delimSpace = "[ ]";
	    for (Element event : events) {
		Log.d("ASSERT","Made it to getFeedItems's main for loop");

		//Set event title
		Element title = event.select(TAG_TITLE).first();
		String titleString = title.text();
		Log.d("ASSERT","This title is: "+titleString);
		    boolean isFake = checkFake(titleString);
		    if (!isFake) {
			values[i][0] = titleString;
		    }
		    else {
			continue;
		}
    
		//Set event date and time i guess
		Element dateAndTime = event.select(TAG_DATE_AND_TIME).first();
		if (dateAndTime != null) {
		    String[] dateAndTimeTokens = dateAndTime.text().split(delimSpace);
		    String date = dateAndTimeTokens[1];
		    String time = dateAndTimeTokens[3];
		    values[i][1] = date;
		    values[i][2] = time;
		}
    
		//Set price
		values[i][3] = "See Ticket";
    
		//Set location
		Element location = event.select(TAG_LOCATION).first();
		if (location != null) {
		    values[i][4] = location.text();
		}
    
		//Set ticket urls
		Element ticketContainer = event.select(TAG_TICKET_URL).first();
		if (ticketContainer != null) {
		    String ticket = ticketContainer.select("a").attr("href");
		    values[i][5] = ticket;
		}
		else {
		    values[i][3] = "Free";
		}

		i++;
	    } //End of event loop
	} //End of try clause
	    
	catch (IOException e) {
	    Log.d("ASSERT","Exception during getFeedItems");
	    e.printStackTrace();
	}
	Log.d("ASSERT","The first title in getFeedItems before returning is: "+values[0][0]);
	return values;
    }


    private static boolean checkFake(String s) {
	boolean isFake = false;
	String[] days = {"Today", "Tomorrow", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	for (int i=0; i<days.length; i++) {
	    if (s.contains(days[i])) {
		isFake = true;
		return isFake;
	    }
	}
	return isFake;

    }

}
