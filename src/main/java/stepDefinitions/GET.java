package stepDefinitions;

import Base.*;
import client.*;
import cucumber.api.java.*;
import cucumber.api.java.en.*;
import org.apache.http.util.*;
import org.json.*;

import java.util.*;

import static client.restclient.closeablehttpGETresponse;

public class GET extends TestBase{
    String URI;

    @Before
    public void URL(){
        String url= prop.getProperty("URL");
        String endpoint = prop.getProperty("END");
        URI=url+endpoint;
    }

    @Given("^Hit the GET method$")
    public void hit_the_GET_method() throws Exception {
        HashMap<String ,String> header=new HashMap<String, String>();
        header.put("Content-Type","application/json");

        restclient.GET(URI,header);

        int statusCode = closeablehttpGETresponse.getStatusLine().getStatusCode();
        System.out.println("Status code "+ statusCode);

        String response = EntityUtils.toString(closeablehttpGETresponse.getEntity(), "UTF-8");
        JSONObject jsonresponse =new JSONObject(response);
        System.out.println("Responce is "+jsonresponse);

        String perPage = restclient.getValueByJPath(URI, "/per_page",header);
        String lastName = restclient.getValueByJPath(URI, "/data[0]/last_name",header);

        System.out.println(perPage);
        System.out.println(lastName);


    }
}
