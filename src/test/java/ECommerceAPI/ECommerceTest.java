package ECommerceAPI;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import ECommercePojo.LoginRequest;
import ECommercePojo.LoginResponse;
import ECommercePojo.OrderDetails;
import ECommercePojo.Orders;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class ECommerceTest {

	public static void main(String[] args) {
		RestAssured.useRelaxedHTTPSValidation(); 
		
		//Login
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		//Implementing Pojo classes
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("postmanacc@gmail.com");
		loginRequest.setUserPassword("Letmein@000");
		
		RequestSpecification reqLogin=given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		String token=loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId=loginResponse.getUserId();
		
		//AddProduct
		RequestSpecification AddProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification reqAddProduct =given().log().all().spec(AddProductBaseReq).param("productName", "Adidas")
		.param("productAddedBy", userId).param("productCategory", "fashion").param("productSubCategory", "shirts")
		.param("productPrice", "98600").param("productDescription", "Addias Originals").param("productFor", "men")
		//using multipart you can add the image to rest assured
		.multiPart("productImage",new File("C:\\Users\\MO20343606\\Downloads\\adidas.png"));
		
		String addProductResponse=reqAddProduct.when().post("api/ecom/product/add-product").then()
				.log().all().extract().response().asString();
		JsonPath js=new JsonPath(addProductResponse);
		String productId=js.get("productId");
		
		//Create Order
		RequestSpecification CreateProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		//Json coming from Pojo
		OrderDetails detail=new OrderDetails();
		detail.setCountry("India");
		detail.setProductOrderedId(productId);
		
		//because original order class expectign the List collection that's why created List
		List<OrderDetails> orderDetailList=new ArrayList<OrderDetails>();
		orderDetailList.add(detail);
		
		Orders order=new Orders();
		order.setOrders(orderDetailList);
		
		RequestSpecification CreateOrderReq=given().log().all().spec(CreateProductBaseReq).body(order);
		
		String responseAddOrder=CreateOrderReq.when().post("/api/ecom/order/create-order").then()
		.log().all().extract().asString();
		System.out.println(responseAddOrder);
		
		//Delete Product
		RequestSpecification DeleteProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		RequestSpecification deleteProdReq=given().log().all().spec(DeleteProductBaseReq).pathParam("productId", productId);
		
		String deleteProdResponse=deleteProdReq.delete("/api/ecom/product/delete-product/{productId}").then()
		.log().all().extract().response().asString();
		
		JsonPath js1=new JsonPath(deleteProdResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
	
	}

}
