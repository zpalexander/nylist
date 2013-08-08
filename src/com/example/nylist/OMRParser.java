package com.example.nylist;

import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OMRParser extends AsyncTask<Void, Void, String[][]>{
    private static String TAG_TITLE = ".summary";
    private static String TAG_LINK = ".ticketLink";
    private static String TAG_LOCATION= ".fn";
    private static String TAG_DATE= ".date";
    private static String TAG_PRICE = ".tickets";
    private static String FEED_URL = "http://www.ohmyrockness.com/showlist.cfm?by=date";
    Context context;
    Activity activity;
    ProgressDialog dialog;

    public OMRParser(Activity context) {
	this.context = context.getApplicationContext();
	this.activity = context;
    }
    
    

    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       dialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
       dialog.setMessage("Summarizing blog...");
       dialog.show();
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

    
    public String[][] getFeedItems() throws IOException {
	
	Document doc = null;
	String values[][] = new String[50][6];

	try{
	    doc = Jsoup.connect(FEED_URL).timeout(0).get();
	    
	    Elements titles = doc.select(TAG_TITLE);
	    Elements dates = doc.select(TAG_DATE);
	    Elements prices = doc.select(TAG_PRICE);
	    Elements locations = doc.select(TAG_LOCATION);
	    Elements tickets = doc.select(TAG_LINK);
	    
	    //Add titles to 2d array
	    int i = 0;
	    for (Element title : titles) {
		values[i][0] = title.text();
		i++;
	    }
	    
	    //Add dates and times to 2d array
	    i = 0;
	    for (Element date : dates) {
		String tempDate = date.text();
		String delim = "[ ]";
		String[] tokens = tempDate.split(delim);
		values[i][1] = tokens[1];
		//Set time
		String time = (tokens[2] + tokens[3]);
		values[i][2] = time;
		i++;
	    } 
	   
	    //Add prices to 2d array
	    i = 0;
	    for (Element price : prices) {
		String temp = price.text();
		if (temp.toLowerCase().contains("FREE SHOW".toLowerCase())) {
		    values[i][3] = "Free Show";
		}
		else {
		    temp = temp.replaceAll("\\D+","");
		    values[i][3] = ("$" + temp);
		}
		i++;
	    }
	    
	    //Add locations to 2d array
	    i = 0;
	    for (Element location : locations) {
		values[i][4] = location.text();
		i++;
	    }
	    
	   //Add links to 2d array
	    i = 0;
	    for (Element ticket : tickets) {
		while (values[i][2] == "Free Show") {
		    i++;
		}
		
		values[i][5] = ticket.attr("href");
		i++;
	    }
	   


	}
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	return values;
    }


}
