package com.example.nylist;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import android.util.Log;

public class OMRParser {
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_ITEM = "item";
    private static String TAG_DATE= "item";
    private static String TAG_PRICE = "price";
    private static String FEED_URL = "http://www.ohmyrockness.com/showlist.cfm?by=date";
    public OMRParser() {
    }
    
    public void getFeed() throws IOException {
	URL url = null;
	try{
	    url = new URL(FEED_URL);
	}
	catch (MalformedURLException e) {
	    e.printStackTrace();
	}
	Document doc = null;
	try {
	    doc = (Document) Jsoup.parse(url,3000);
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
	
	
    
    }
}
