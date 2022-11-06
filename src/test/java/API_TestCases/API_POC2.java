package API_TestCases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.API.Base.APIExtentManager;
import com.API.Base.BaseTest;
import com.API.Utilities.APIResources;
import com.API.Utilities.EnumHelper;
import com.API.Utilities.ExceptionHandling;
import com.API.Utilities.LogUtils;
import com.API.Utilities.LoginPage;
import com.API.Utilities.Utils;

import io.restassured.response.Response;

/**
 * @author Shivakumar
 *
 */

public class API_POC2 extends BaseTest {
	LoginPage loginPage;
	Utils util;

	public static String acc_Token;

	@BeforeMethod
	public void beforeTest(Method m) throws Exception {
		APIExtentManager.apitest = APIExtentManager.apiextent.startTest(m.getName());
		new LogUtils().log().info("\n" + "**************** STARTING TEST: " + m.getName() + "*****************" + "\n");
		System.setProperty("-Djsse.enableSNIExtension", "false");
		loginPage = new LoginPage();
		util = new Utils();
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method m) {
		new LogUtils().log().info("\n" + "****************** END TEST: " + m.getName() + "********************" + "\n");
	}

	@Test
	public void a_get_accessToken() throws Exception {
		try {
			acc_Token = loginPage.generate_accessToken();
			System.out.println(acc_Token);

			com.API.Base.BaseTest.PASSED_TC_COUNTER = com.API.Base.BaseTest.PASSED_TC_COUNTER
					+ 1;
		} catch (Exception e) {
			new ExceptionHandling().catchBlockException(e);
		}

	}

	@Test
	public void b_accessTokenInfo() throws Exception {
		try {
			LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
			header.put("Content-Type", "text/plain");
			LinkedHashMap<String, String> querryParam = new LinkedHashMap<String, String>();
			querryParam.put("access_token", acc_Token);
		    util.requestWithHeaderAndParameter(2, header, querryParam, EnumHelper.HTTP_Method.GET,
					APIResources.valueOf("TokenInfoAPI").getResource());

			com.API.Base.BaseTest.PASSED_TC_COUNTER = com.API.Base.BaseTest.PASSED_TC_COUNTER
					+ 1;
		} catch (Exception e) {
			new ExceptionHandling().catchBlockException(e);
		}
	}

	@Test
	public void c_checkIfUserExists() throws Exception {
		try {
			LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
			header.put("Content-Type", "application/x-www-form-urlencoded");
			header.put("Authorization", "Bearer " + acc_Token);
			LinkedHashMap<String, String> querryParam = new LinkedHashMap<String, String>();
			querryParam.put("partyId", "71350268");
			LinkedHashMap<String, String> formBody = new LinkedHashMap<String, String>();
			formBody.put("grant_type", "CLIENT_CREDENTIALS");
			formBody.put("scope", "accounts");
			Response response = util.requestWithHeaderAndFormDataBody_WithParameter(2, header, formBody, querryParam,
					EnumHelper.HTTP_Method.GET, APIResources.valueOf("UserExistsAPI").getResource());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

			com.API.Base.BaseTest.PASSED_TC_COUNTER = com.API.Base.BaseTest.PASSED_TC_COUNTER
					+ 1;
		} catch (Exception e) {
			new ExceptionHandling().catchBlockException(e);
		}
	}


}
