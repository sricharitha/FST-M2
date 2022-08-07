import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class Project {
	RequestSpecification reqSpec;
	String SSH_KEY ; 
	int SSH_ID;
	
	@BeforeClass
	public void reqestSpec() {
		reqSpec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token ghp_upsoT8axGx124KPEDYuuT1KAFTxNNa51ckjQY")
				.setBaseUri("https://api.github.com")
				.build();
	}


	
  @Test(priority = 1)
  public void postRequest() {
	  String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAQRSSABCABgQCyfdyAjhW5i95sJf7S2lPCNyAzq9bhr2kBjntwzhFFlEZFLPKrato32q+yTNGG31Q/4ZhZD3+y4t1qhetkdmfxq6Guk9VdID8DMaQ4gF938A4wDaIiUzH8OSUjM0VNvw2XIsQ5p9whrtU0+cgQH7YkeowaxzC8iLgrECGjtWU/bHV0ntlfrXgpWsFBM0L/PqENlBhp+nmRtXejHVWh1ZWlCyEARqeCWsiRfPLYEezKkBPRrGeX42Sv0W0+6WrNgNYOVCceoc/LhA39/44yMT4FZAketru06hKS+nnMp7QBXcWQS9eSvwW3nS6Mc9FIAEX5yRGzfZG774/4eSf7uenOWyyCDF12j32P5I8OnPB1HtAiVkpsNU02pp2EwxU9DM2Ol63Ah7vFBUdxrWsbTwEqG3zXMEXtWypOyIEb/lI+uhq9Hgs3FWeMrhcgi0HZ4yMjZFfcZoC27ddVPy0yzHDFLCfEqLCXF8ZvhgsOSAzU+bFRT/Z8dh1/OCpIt5ZbgthM= gmx//0022u6744@DESKTOP-S9QRJJD\"}";

	  
	  Response response = given().spec(reqSpec)
			  .body(reqBody)
			  .when().post("/user/keys");
	  SSH_ID = response.jsonPath().get("id");
//	  System.out.println(SSH_ID);
	  
	  response.then().statusCode(201);
	  
  }
 
 
  @Test(priority = 2)
  public void getRequest() {
	  
	  Response response = given().spec(reqSpec)
			  .param("Id", SSH_ID)
			  .when().get("/user/keys");
	  
	  Reporter.log(response.asPrettyString(),true);
	  response.then().statusCode(200);
  }
//  
  @Test(priority = 3)
  public void deleteRequest() {
	  
	  Response response = given().spec(reqSpec)
			  .pathParam("Id", SSH_ID)
			  .when().delete("/user/keys/{Id}");
	  
	  Reporter.log(response.asPrettyString(),true);
	  response.then().statusCode(204);
	  
  }
}