package APITesting.BasicTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import JsonFiles.ReUsableMethods;
import JsonFiles.requestpayload;

public class BasicAPI {

	public static void main(String[] args) {
		
		//Validate if ADD place API is working as expected
		/*Rest Assured works on-
		given- All input details
		when- submit the API - resource and https go under this method
		then- Validate the response
		//Add place->update place with nEW ADDRESS -> gET PLACE TO VALIDATE if new address is present in response

*/
		//POST method
		RestAssured.baseURI="https://rahulshettyacademy.com";
		RestAssured.useRelaxedHTTPSValidation();
		String response=given().log().all().queryParam("key", "qaclick123").headers("Content-type","application/json")
		.body(requestpayload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		//validating the status code
		.header("server","Apache/2.4.41 (Ubuntu)" ).extract().response().asString();
		System.out.println(response);
		
		JsonPath js=new JsonPath(response); //for parsing json-> this has knowledge of string json
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		//update place with nEW ADDRESS
		
		String newAddress= "63, side layout, Africa 07";
		given().log().all().queryParam("key", "qaclick123").header("content-type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}") 
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place- we are not sending any body and header
		String getPlaceResponse=given().log().all().queryParam("key","qaclick123")
		.queryParam("place_id",placeId )
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse); //from reusable method
		String actualaddress= js1.getString("address");
		System.out.println(actualaddress);
		//Using TestNg Assertions
		Assert.assertEquals(actualaddress, newAddress);
	}

}
