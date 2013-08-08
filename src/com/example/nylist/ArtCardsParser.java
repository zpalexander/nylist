package com.example.nylist;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArtCardsParser extends AsyncTask<Void, Void, String[][]>{ 

    String TAG_CARD = ".card";
    String TAG_DATE = ".date";
    String TAG_SHOW = ".info";
    String TAG_LOCATION = ".map";
    String FEED_URL = "http://artcards.cc/newyork/";
    Context context;
    Activity activity;
    ProgressDialog dialog;

    //Constructor
    public ArtCardsParser(Activity context) {
	this.context = context.getApplicationContext();
	this.activity = context;
    }

    
    //AsyncTask methods
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       dialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
       dialog.setMessage("Summarizing blog...");
       dialog.show();
    }

    
    @Override
    protected String[][] doInBackground(Void... arg0) {
	String values[][] = new String[50][6];
	try {
	    values = getFeedItems();
	} 
	catch (IOException e) {
	    e.printStackTrace();
	}
	return values;
    }
   
    @Override
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
	
	String values[][] = new String[50][6];
	Document doc = null;
	
	try{
	    doc = Jsoup.connect(FEED_URL).timeout(0).get();
		    
	    Elements cards = doc.select(TAG_CARD);
		    
	    int i = 0;
	    for (Element card : cards) {
		
		//Cap events at 50 for now
		if (values[49][0] != null) {
		    break;
		}
		
		Elements shows = card.select(TAG_SHOW);
		for (Element show : shows) {
		    String showText = show.text();
		    
		    //Set up delims for parsing
		    String delimAt = " at";
		    String delimSpace = " ";
		    String delimColon = ":";
		    String delimComma = ",";
		    String delimHyphen = "-";
		   
		    
		    //Set title
		    String[] showTextTokens = showText.split(delimAt);
		    values[i][0] = (showTextTokens[0]);
		   
		    //Set date
		    String dateString = card.select(TAG_DATE).text();
		    String[] dateTokens = dateString.split(delimSpace);
		    String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August",       
			    "September", "October", "November", "December"};
		    int monthInt = 0;
		    for(int a=0; a < 12 ; a++) {
			if (monthArray[a].equals(dateTokens[1])) {
			    monthInt = (a + 1);
			}
		    }
		    String date = (monthInt + "/" + dateTokens[2]);
		    values[i][1] = date;

		    //Set time
		    String infoString = showTextTokens[(showTextTokens.length-1)];
		    String[] infoTokens = infoString.split(delimColon);
		    String detailsString = "";
		    for (int z=1; z<infoTokens.length; z++) {
			detailsString = detailsString + infoTokens[z];
		    }
		    String[] detailsTokens = detailsString.split(delimComma);
		    int timeIndex = (detailsTokens.length-2);
		    if (timeIndex > 0) {
			String timeFull = detailsTokens[timeIndex];
			String[] timeHolderArray = timeFull.split(delimHyphen);
			String time = timeHolderArray[0].replaceAll("\\s","");
			if (time.length() == 1) {
			    time = time + ":00";
			}
			else if (time.length() == 3) {
			    time = (time.substring(0) + ":" + time.substring(1,2));
			}
			else if (time.length() == 4) {
			    time = (time.substring(0,1) + ":" + time.substring(2,3));
			}
			values[i][2] = (time+"PM");
		    }
		                                                 

		    //Set price
		    if (showText.contains("$")) {
			
		    }
		    else {
			values[i][3] = "Free";
		    }
		    
		    //Set location
		    String locationText = show.select(TAG_LOCATION).text();
		    String[] locationTokens = locationText.split(delimColon);
		    values[i][4] = locationTokens[(locationTokens.length - 1 )].replaceAll("\\s","");
		    i++;
		}
	    }
	}
		    
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	return values;
    }
    
    

}