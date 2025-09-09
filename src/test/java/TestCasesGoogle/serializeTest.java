package TestCasesGoogle;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Oauth.PojoClasses.AddPlaceG;
import Oauth.PojoClasses.locationG;

public class serializeTest {
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
		
		Response res=given().log().all().queryParam("key", "qaclick123")
		.body(p)// just pass the object here in the body of our main class AddPlaceG
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString=res.asString();
		System.out.println(responseString);
		
		
	}

}
