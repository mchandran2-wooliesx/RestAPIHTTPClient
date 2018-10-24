package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.EmployeeData;

public class PostAPITest extends TestBase 
{
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	
	@BeforeMethod
	public void setUp()
	{	
		testBase = new TestBase();
		serviceURL = prop.getProperty("serviceURL");
		apiURL = prop.getProperty("apiURL");
		
		url = serviceURL+apiURL;
	}
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException 
	{
		restClient = new RestClient();
		
		// Prepare the Header Map with values from the POST MAN JSON
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		
		// Generate the JSON Payload using Jackson API:
		
			//	Create a Object Mapper of Jackson API
			ObjectMapper objectMapper = new ObjectMapper();
			
		// <<<MARSHALLING>>> Create the POJO from the Pojo class in src/main/com.qa.data
			EmployeeData employeeData = new EmployeeData("morpheus", "leader");   // Expected RESULT 'EmployeeData' Object for validation
			// TWO WAYS to Convert the employeeData Object to JSON:
			// MARSHALLING 1. Convert the employeeData Object to JSON File and storr it
			objectMapper.writeValue(new File(System.getProperty("user.dir")+"/src/main/java/com/qa/data/employeeData.json"), employeeData);
			
			//MARSHALLING 2. Convert the employeeData Object to JSON String and use that in the program
			String employeeData_JSONString = objectMapper.writeValueAsString(employeeData);
			
		//Call the POST method from the RestClient Class
			closeableHttpResponse = restClient.post(url, employeeData_JSONString, headerMap);
			
		//1. Response Status Code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,testBase.RESPONSE_STATUS_CODE_201);
		
		
		//2. Get the JSON Response as a String then convert that to JSON Object:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		
		System.out.println("Response From API is -->" + responseJSON);
		
		//<<<UNMARSHALLING>>> JSON to Java Object. This is done because our Expected Result is an Object with the values!
		
		EmployeeData employeeData_ResponseObject = objectMapper.readValue(responseString, EmployeeData.class); // ACTUAL RESULT 'EmployeeData' Object
		System.out.println(employeeData_ResponseObject);
		
		// Printing the EXPECTED and ACTUAL Results for Comparison;
		System.out.println("EXPECTED Name = "+ employeeData.getName());
		System.out.println("EXPECTED Job = " + employeeData.getJob());
		
		System.out.println("ACTUAL Name = "+ employeeData_ResponseObject.getName());
		System.out.println("ACTUAL Job = " + employeeData_ResponseObject.getJob());
		System.out.println("ACTUAL ID = "+ employeeData_ResponseObject.getId());
		System.out.println("ACTUAL Created At = " + employeeData_ResponseObject.getCreatedAt());
		
		//Assertions
		
		Assert.assertTrue(employeeData.getName().equals(employeeData_ResponseObject.getName()));
		Assert.assertTrue(employeeData.getJob().equals(employeeData_ResponseObject.getJob()));

		
		
	}
	
}
