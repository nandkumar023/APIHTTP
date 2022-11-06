package API_TestCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @author Shivakumar
 *
 */

public class GBK_UAT {

	public static String token;
	
	@Test
	public void a_tokenServiceAPI() {
	
		RestAssured.baseURI = "https://uatgbonline.e-gulfbank.com";
		
		Response response = given().contentType("application/x-www-form-urlencoded")
		.header("Authorization","Basic ODVhMjJhMGYtNWU4Ny1kMzA0LTgyNzUtMWRiNmQzYWZjNmMxOmdiazIwMjI=")
		.formParam("grant_type", "CLIENT_CREDENTIALS")
		.formParam("scope", "accounts").log().all()
		.when().post("/digx-auth/v1/token")
		.then().log().all().extract().response();
		
		System.out.println(response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());
		 token = js.getString("access_token");
		System.out.println(token);
	}
	
	@Test
	public void b_accessTokenInfo() {
	Response response = given().queryParam("access_token", token).contentType("text/plain")
	.log().body().when().get("/digx-auth/v1/token/info")
	.then().log().all().extract().response();
	System.out.println(response.getStatusCode());
	JsonPath js = new JsonPath(response.asString());
     System.out.println(js.getString("status.result"));
	}
	
	@Test
	public void c_checkIfUserExists() {
		try {
			Response response = given().queryParam("partyId", "71350268").contentType("application/x-www-form-urlencoded")
					.header("Authorization","Bearer "+token)
					.formParam("grant_type", "CLIENT_CREDENTIALS")
					.formParam("scope", "accounts").log().all()
					.when().get("/digx/v1/users")
			.then().log().all().extract().response();
			if(response.getStatusCode()!=300)
				throw new Exception("hgc");
			System.out.println(response.getStatusCode());
			JsonPath js = new JsonPath(response.asString());
		     System.out.println("Context ID "+js.getString("status.contextID"));

		} catch (Exception e) {
	}

	
	
	}
	
	
}
