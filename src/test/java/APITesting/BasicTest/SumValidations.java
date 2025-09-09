package APITesting.BasicTest;

import org.testng.annotations.Test;

import JsonFiles.requestpayload;
import io.restassured.path.json.JsonPath;

public class SumValidations {
	
	@Test
	public void SumofCourses()
	{
		JsonPath js=new JsonPath(requestpayload.CoursePrice());
		int count=js.getInt("courses.proce");
		for(int i=0; i<count;i++)
		{
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amount= price * copies;
			//System.out.println(amount);
		}
	}

}
