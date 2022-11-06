package com.API.Utilities;

import static io.restassured.RestAssured.given;
import java.util.Base64;

import com.API.Base.APIExtentManager;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.LogStatus;
import POJO_Classes.LoginCredentilas;
import POJO_Classes.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author Shivakumar
 *
 */

public class LoginPage {
	Utils util = new Utils();
	TestDataBuild testData = new TestDataBuild();
	public static String token;
	public static String userID;

	public void generateToken() throws Exception {

		RequestSpecification reqLogin = given().relaxedHTTPSValidation().spec(util.requestSpecification())
				.contentType(ContentType.JSON).body(testData.loginRequestPayload());
		APIResources resourceAPI = APIResources.valueOf("LoginAPI");
		System.out.println(resourceAPI.getResource());

		Response response = reqLogin.when().post(resourceAPI.getResource()).then().extract().response();
		if (response.getStatusCode() != 200)
			throw new Exception("Login Request Failed expected status code is 200 but found "
					+ Integer.toString(response.getStatusCode()));
		LoginResponse loginResponse = response.as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		System.out.println(loginResponse.getMessage());
		if (!loginResponse.getMessage().equalsIgnoreCase("Login Successfully"))
			throw new Exception("Login Request Failed expected response message is Login Successfully but found "
					+ loginResponse.getMessage());
		token = loginResponse.getToken();
		userID = loginResponse.getUserId();
	}

	public String generate_accessToken() throws Exception {
		LoginCredentilas credentals = base64Encode(util.getGlobalValues("u_Name"), util.getGlobalValues("pwd"));
		System.out.println("Encoder ----> " + credentals.getCredentials());
		// ODVhMjJhMGYtNWU4Ny1kMzA0LTgyNzUtMWRiNmQzYWZjNmMxOmdiazIwMjI=
		Response response = null;
		String access_Token;
		try {
			response = given().spec(util.requestSpecification())
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Authorization", "Basic " + credentals.getCredentials())
					.formParam("grant_type", "CLIENT_CREDENTIALS").formParam("scope", "accounts").when()
					.post(APIResources.valueOf("TokenServiceAPI").getResource()).then().extract().response();
			JsonPath js = new JsonPath(response.asString());
			access_Token = js.getString("access_token");
			APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
					"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
		} catch (Exception e) {
			if (e.getMessage().contains("Error Response"))
				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
						+ "<div id=error_response>" + e.getMessage() + "</div>"
						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
			else
				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
			e.getMessage();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return access_Token;
	}

	public static LoginCredentilas base64Encode(final String username, final String password) {
		String cred = username + ":" + password;
		cred = Base64.getEncoder().encodeToString(cred.getBytes());
		LoginCredentilas loginCred = new LoginCredentilas();
		loginCred.setCredentials(cred);
		return loginCred;

	}

}
