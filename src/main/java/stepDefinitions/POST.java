package stepDefinitions;

import Base.*;
import client.*;
import com.fasterxml.jackson.databind.*;
import cucumber.api.java.*;
import cucumber.api.java.en.*;
import org.apache.http.util.*;
import org.json.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static client.restclient.*;

public class POST extends TestBase{
    String URI;

    @Before
    public void URL(){
        String url= prop.getProperty("URL");
        String endpoint = prop.getProperty("END");
        URI=url+endpoint;
    }

    @Given("^Hit the post method$")
    public void hit_the_post_method() throws IOException {
        HashMap<String ,String> header=new HashMap<String, String>();
        header.put("Content-Type","application/json");

        ObjectMapper mapper=new ObjectMapper();
//        users user =new users("nandkumar","QA");
        //OObject to json
//        mapper.writeValue(new File("src/main1/java/client/users.json"), "");

        //object to json in string
        String userjsonString = mapper.writeValueAsString("");


        practice p=new practice();
        p.setName("NANDKUMAR");
        p.setAge(21);
        p.setSalary(10.00);

//
//        String json = "{" +
//                "Name : Jai," +
//                "Age : 25, " +
//                "Salary: 25000.00 " +
//                "}";
//
        JSONObject jsonObj = new JSONObject(p);
        System.out.println(jsonObj.toString(4)); // pretty print json



        restclient.POST(URI,jsonObj.toString(),header);

        String response = EntityUtils.toString(closeablehttpPOSTresponse.getEntity(), "UTF-8");
        JSONObject jsonresponse =new JSONObject(response);
        System.out.println("Responce is "+jsonresponse);    }

}
