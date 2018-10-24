package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient 
{
	
	//1. GET Method - without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException 
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		// Create a Get Request Object Connection with the URL
		HttpGet httpget = new HttpGet(url);
		
		// Hit the URL & store the response
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		
		return closeableHttpResponse;	
		
	}
	
	
	//2. GET Method - with Headers:
	
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException 
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		// Create a Get Request Object Connection with the URL
		HttpGet httpget = new HttpGet(url);
		
		//Add the Headers from the Header Map to the httget URL
		for(Map.Entry<String, String> map:headerMap.entrySet())
		{
			httpget.addHeader(map.getKey(), map.getValue());
		}
		
		
		
		// Hit the URL & store the response
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		
		return closeableHttpResponse;	
		
	}

	
	// 3. POST Method:
	public CloseableHttpResponse post(String url,String entityString,HashMap<String, String> headerMap) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		// Create a POST Request object connection with URL(URI)		
		HttpPost httppost = new HttpPost(url);
		
		// Define the Payload using setEntity method
		httppost.setEntity(new StringEntity(entityString));
		
		// Add the Headers
		for(Entry<String, String> headerSet :headerMap.entrySet()) 
		{
			httppost.addHeader(headerSet.getKey(), headerSet.getValue());
		}
	
		// Hit the URL & store the response
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		
		return closeableHttpResponse;

		
	}
	
	
}
