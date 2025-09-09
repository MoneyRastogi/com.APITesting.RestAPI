package TestCasesGoogle;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;

import Oauth.PojoClasses.Api;
import Oauth.PojoClasses.GetCourse;
import Oauth.PojoClasses.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};

	String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyhddf&code=4%2F0AbUR2VMFmAVQHOMKZxci5B6lTnjcLCPcZ6O3ZOXHTpgvSDaBDo5ZEmlJsZ9k8Ctm618m_A&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
	String partialcode=url.split("code=")[1];
	String code=partialcode.split("&scope")[0];
	System.out.println(code);
	String response =given()
			.urlEncodingEnabled(false)
			.queryParams("code",code)
			.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("grant_type", "authorization_code")
			.queryParams("state", "verifyfjdss")
			.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
			// .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
			.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			
			.when().log().all()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();
	// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		
		//We are now implementing the pojo classs here to optimizing the code
		GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses=gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		//Get the course names of WebAutomation
			ArrayList<String> a= new ArrayList<String>();
			List<WebAutomation> w=gc.getCourses().getWebAutomation();
			
			for(int j=0;j<w.size();j++)
			{
				a.add(w.get(j).getCourseTitle());
			}
			//converting the array to arrayList
			List<String> expectedList=	Arrays.asList(courseTitles);
			
			Assert.assertTrue(a.equals(expectedList));

//		//System.out.println(accessToken);
//		String r2=    given().contentType("application/json").
//				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//				.when()
//				.get("https://rahulshettyacademy.com/getCourse.php").asString();
//		System.out.println(r2);
		}
}

