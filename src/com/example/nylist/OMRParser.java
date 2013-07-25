package com.example.nylist;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OMRParser extends AsyncTask<Void, Void, String[][]>{
    private static String TAG_TITLE = ".summary";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_LOCATION= ".fn org";
    private static String TAG_DATE= "item";
    private static String TAG_PRICE = ".price";
    private static String FEED_URL = "http://www.ohmyrockness.com/showlist.cfm?by=date";
    Context context;
    Activity activity;

    public OMRParser(Activity context) {
	this.context = context.getApplicationContext();
	this.activity = context;
    }
    
    

    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       //Display progress bar
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
	int eventCount = result[0].length;
	ListRow[] listrow_data = new ListRow[eventCount];
	ListRow temp;
	for (int i=0; i<eventCount; i++) {
	    temp = new ListRow(context, result[i][0], "1/1/13", "$7", "285 Kent", i);
	    listrow_data[i] = temp;
	}
	((EventList) activity).setList(listrow_data);    
    }

    
    public String[][] getFeedItems() throws IOException {
	
	Document doc = null;
	String values[][] = new String[50][6];

	try{
	    doc = Jsoup.connect(FEED_URL).timeout(0).get();
	    
	    Elements titles = doc.select(TAG_TITLE);
	    Elements locations = doc.select(TAG_LOCATION);
	    Elements prices = doc.select(TAG_PRICE);
	    
	    //Add titles to 2d array
	    int i = 0;
	    int j = 0;
	    for (Element title : titles) {
		values[i][j] = title.text();
		i++;
	    }
	    
	    /*
	    //Add locations to 2d array
	    i = 0;
	    j = 1;
	    for (Element location : locations) {
		values[i][j] = location.text();
		i++;
	    }
	   
	    //Add prices to 2d array
	    i = 0;
	    j = 2;
	    for (Element price: prices) {
		values[i][j] = price.text();
		i++;
	    }
	    */

	}
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	return values;
    }


}
