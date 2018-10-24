package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase
{
	TestBase testbase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() 
	{
		testbase = new TestBase();
		serviceURL = prop.getProperty("serviceURL");
		apiURL = prop.getProperty("apiURL");
		url = serviceURL+apiURL;

	}
	
	@Test(priority=1)
	public void getAPITestWithoutHeader() throws ClientProtocolException, IOException 
	{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);

		// 1. Get the STATUS CODE 
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--> "+statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");
				
		//2. Get the JSON BODY as a STRING & CONVERT to JSON Object
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
				
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API--->"+responseJSON);
		
		// Getting the value of JSON Objects:	
		String per_page = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of per_page :" + per_page);
		Assert.assertEquals(Integer.parseInt(per_page), 3);
		
		// Getting the value of JSON Arrays:	
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		
		System.out.println("data[0]/id = " + id);
		System.out.println("data[0]/first_name = " + first_name);
		System.out.println("data[0]/last_name = " + last_name);
		System.out.println("data[0]/avatar = " + avatar);
				
		//3. Get the Headers in a Header Array and then convert that into a HashMap
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
				
		Map<String, String> allHeaders = new HashMap<String, String>();
				
		for (Header header:headersArray ) 
		{
			allHeaders.put(header.getName(), header.getValue());
		}
				
		System.out.println("All Headers --->"+allHeaders);
	}
	
	
	
	
	@Test(priority=2)
	public void getAPITestWithHeader() throws ClientProtocolException, IOException 
	{
		
		HashMap<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type","application/json");
		
		
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url, headerMap);
		
		// 1. Get the STATUS CODE 
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--> "+statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");
				
		//2. Get the JSON BODY as a STRING & CONVERT to JSON Object
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
				
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API--->"+responseJSON);
		
		// Getting the value of JSON Objects:	
		String per_page = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("Value of per_page :" + per_page);
		Assert.assertEquals(Integer.parseInt(per_page), 3);
		
		// Getting the value of JSON Arrays:	
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		
		System.out.println("data[0]/id = " + id);
		System.out.println("data[0]/first_name = " + first_name);
		System.out.println("data[0]/last_name = " + last_name);
		System.out.println("data[0]/avatar = " + avatar);
		

				
		//3. Get the Headers in a Header Array and then convert that into a HashMap
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
				
		Map<String, String> allHeaders = new HashMap<String, String>();
				
		for (Header header:headersArray ) 
		{
			allHeaders.put(header.getName(), header.getValue());
		}
				
		System.out.println("All Headers --->"+allHeaders);
	}

}
