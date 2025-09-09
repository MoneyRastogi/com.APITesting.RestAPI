package TestCasesGoogle;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Oauth.PojoClasses.AddPlaceG;
import Oauth.PojoClasses.locationG;

public class SpecBuilderTest {
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Generating the object out of AddApi 
		AddPlaceG p= new AddPlaceG();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
		//you can not pass string directly because the "type" has the return type as list
		List<String> myList= new ArrayList<String>();
		myList.add("shoe park,shop");
		myList.add("shop");
		//now we just need to pass myList as an object here in the setTypes
		p.setTypes(myList);
		//first need to create the object of the LocationG class
		locationG l=new locationG();
		l.setLatitude(-38.383494);
		l.setLongitude(33.427362);
		p.setLocation(l);
		
		
		//Building the object of request spec builder
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		// just pass the object here in the body of our main class AddPlaceG
		ResponseSpecification respec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification res=given().spec(req).body(p);
		Response response=res.when().post("maps/api/place/add/json")
		.then().spec(respec).extract().response();
		
		String responseString=response.asString();
		System.out.println(responseString);
		
		
	}

}
