package com.API.Utilities;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import com.API.Base.APIExtentManager;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.LogStatus;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author Shivakumar
 *
 */

public class Utils {
	public static RequestSpecification requestSpec;
	public static Response response;

	public RequestSpecification requestSpecification() throws IOException {
		if (requestSpec == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpec = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpec;
		}
		return requestSpec;

	}

	public String getGlobalValues(String key) throws IOException {
		Properties prop = new Properties();
		String propertyFilePathPath = new File("src\\main\\java\\com\\API\\Utilities\\Global.properties")
				.getAbsolutePath().toString();
		FileInputStream fis = new FileInputStream(propertyFilePathPath);
		prop.load(fis);
		return prop.getProperty(key);
	}

	public String ReplaceGlobalValues(String key, String oldValue, String newValue) throws IOException {
		Properties prop = new Properties();
		String propertyFilePathPath = new File("src\\main\\java\\com\\API\\Utilities\\Global.properties")
				.getAbsolutePath().toString();
		FileInputStream fis = new FileInputStream(propertyFilePathPath);
		prop.load(fis);
		prop.replace(key, oldValue, newValue);
		return prop.getProperty(key);
	}

	// 1.Without path and querry parameter
	public Response requestWithHeaderAndJsonBody(LinkedHashMap<String, String> addHeader, Object obj,
			EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).body(obj);

		try {
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBody(int a, LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> formData, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = null;
		try {
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).params(formData);
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).formParams(formData);
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;

			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;

			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;

			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBodyWithMultiPart(LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> formData, LinkedHashMap<String, String> multipart,
			EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).params(formData)
					.multiPart(key, new File(value));
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;

			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndMultiPart(LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> multipart, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).multiPart(key,
					new File(value));
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeader(LinkedHashMap<String, String> addHeader, EnumHelper.HTTP_Method method,
			String reqUrl) throws Exception {
		RequestSpecification request = given().spec(requestSpecification()).headers(addHeader);
		try {
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

//2.Separate path and querry param

	public Response requestWithHeaderAndJsonBody_WithParameter(int a, LinkedHashMap<String, String> addHeader,
			Object obj, LinkedHashMap<String, String> param, EnumHelper.HTTP_Method method, String reqUrl)
			throws Exception {
		RequestSpecification request = null;
		try {
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(param).body(obj);
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).queryParams(param).body(obj);
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBody_WithParameter(int a, LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> formData, LinkedHashMap<String, String> param, EnumHelper.HTTP_Method method,
			String reqUrl) throws Exception {
		RequestSpecification request = null;
		try {
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(param).params(formData);

			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).queryParams(param)
						.formParams(formData);
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBodyWithMultiPart_WithParameter(int a,
			LinkedHashMap<String, String> addHeader, LinkedHashMap<String, String> formData,
			LinkedHashMap<String, String> param, LinkedHashMap<String, String> multipart, EnumHelper.HTTP_Method method,
			String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = null;
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(param).params(formData)
						.multiPart(key, new File(value));
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).queryParams(param).params(formData)
						.multiPart(key, new File(value));
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndMultiPart_WithParameter(int a, LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> multipart, LinkedHashMap<String, String> param, EnumHelper.HTTP_Method method,
			String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = null;
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(param).multiPart(key,
						new File(value));
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).queryParams(param).multiPart(key,
						new File(value));
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndParameter(int a, LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> param, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = null;
		try {
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(param);
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).queryParams(param);
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().delete(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

//3.Both Path parameter and Querry parameter at a time using	

	public Response requestWithHeaderAndJsonBody_WithBothParameters(LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> pathParam, LinkedHashMap<String, String> querryParam, Object obj,
			EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
				.queryParams(querryParam).body(obj);
		try {
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBody_WithBothParameters(int a, LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> pathParam, LinkedHashMap<String, String> querryParam,
			LinkedHashMap<String, String> formData, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = null;
		try {
			if (a == 1) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
						.queryParams(querryParam).params(formData);
			} else if (a == 2) {
				request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
						.queryParams(querryParam).formParams(formData);
			}
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndFormDataBodyWithMultiPart_WithBothParameters(
			LinkedHashMap<String, String> addHeader, LinkedHashMap<String, String> pathParam,
			LinkedHashMap<String, String> querryParam, LinkedHashMap<String, String> formData,
			LinkedHashMap<String, String> multipart, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
					.queryParams(querryParam).params(formData).multiPart(key, new File(value));
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAndMultiPart_WithBothParameters(LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> pathParam, LinkedHashMap<String, String> querryParam,
			LinkedHashMap<String, String> multipart, EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		String key = null;
		String value = null;
		try {
			for (Map.Entry<String, String> mp : multipart.entrySet()) {
				key = mp.getKey();
				value = mp.getValue();
				break;
			}
			RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
					.queryParams(querryParam).multiPart(key, new File(value));
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public Response requestWithHeaderAnd_BothParameters(LinkedHashMap<String, String> addHeader,
			LinkedHashMap<String, String> pathParam, LinkedHashMap<String, String> querryParam,
			EnumHelper.HTTP_Method method, String reqUrl) throws Exception {
		RequestSpecification request = given().spec(requestSpecification()).headers(addHeader).pathParams(pathParam)
				.queryParams(querryParam);
		try {
			switch (method) {
			case GET:
				response = request.when().get(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case POST:
				response = request.when().post(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PUT:
				response = request.when().put(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case PATCH:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			case DELETE:
				response = request.when().patch(reqUrl).then().extract().response();
				APIExtentManager.apitest.log(LogStatus.PASS, response.getStatusCode() + " " + response.getStatusLine(),
						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
				break;
			default:
				break;
			}
		} catch (Exception e) {
//			if (e.getMessage().contains("Error Response"))
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine()
//						+ "<div id=error_response>" + e.getMessage() + "</div>"
//						+ "<div id=refer_resp>Refer Response <span id=hand_icon>  &#10524; &#10524; &#10524;</span></div>",
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			else
//				APIExtentManager.apitest.log(LogStatus.FAIL, response.getStatusCode() + " " + response.getStatusLine(),
//						"<pre>" + JsonWriter.formatJson(response.asString()) + "</pre>");
//			e.getMessage();
//			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return response;
	}

	public void verifyStatusCode(int actual, int expected) throws Exception {
		try {
			if (actual != expected)
				throw new Exception("Expected Status Code is : " + expected + " But actual is : " + actual);
			APIExtentManager.apitest.log(LogStatus.PASS, "Response Status Code is " + expected, "");
		} catch (Exception e) {
//			APIExtentManager.apitest.log(LogStatus.FAIL, e.getMessage(), "");
			e.getMessage();
			e.printStackTrace();
			throw new Exception(e.getMessage());

		}
	}

	public void verifyValueFromResponseBody(String actual, String expected) throws Exception {
		try {
			if (!actual.equalsIgnoreCase(expected))
				throw new Exception(
						"Expected value from response body is : " + expected + " But actual is : " + actual);
			APIExtentManager.apitest.log(LogStatus.PASS, "Response Body Value is " + expected, "");
		} catch (Exception e) {
//			APIExtentManager.apitest.log(LogStatus.FAIL, e.getMessage(), "");
			e.getMessage();
			e.printStackTrace();
			throw new Exception(e.getMessage());

		}
	}

	public void verifyResponseHeader(String actual, String expected) throws Exception {
		try {
			if (!actual.equalsIgnoreCase(expected))
				throw new Exception("Expected response header is : " + expected + " But actual is : " + actual);
			APIExtentManager.apitest.log(LogStatus.PASS, "Response Headers is " + expected, "");
		} catch (Exception e) {
//			APIExtentManager.apitest.log(LogStatus.FAIL, e.getMessage(), "");
			e.getMessage();
			e.printStackTrace();
			throw new Exception(e.getMessage());

		}
	}

}
