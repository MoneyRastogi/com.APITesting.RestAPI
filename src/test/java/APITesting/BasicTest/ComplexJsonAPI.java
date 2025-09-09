package APITesting.BasicTest;

import JsonFiles.requestpayload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonAPI {

	public static void main(String[] args) {

		JsonPath js=new JsonPath(requestpayload.CoursePrice());
		//Print No.of Courses returned by API
		
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase Amount
		int totalAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print title of the first course
		String titleFirstCourse=js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print all course titles and respective prices
		for(int i=0;i<count;i++)
		{
			String courseTitle=js.getString("courses["+i+"].title");
	        int price= js.getInt("courses["+i+"].price");
			System.out.println("Course Name: "+courseTitle+" Price: "+price);	
		}
//		/Print no of copies sold by RPA Course
		
		System.out.println("Print no of copies sold by RPA Course");
		for(int i=0;i<count;i++)
		{
			String courseTitle=js.get("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA"))
			{
				int Copies=js.getInt("courses["+i+"].copies");
				System.out.println(Copies);
				break;
			}
		}
		
	}

}
