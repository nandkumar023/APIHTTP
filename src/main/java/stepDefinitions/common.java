package stepDefinitions;

import cucumber.api.*;
import cucumber.api.java.*;
import cucumber.api.java.en.*;

import java.util.*;

public class common {

    @Before
    public void setup(){
        System.out.println("Set up method");
    }
    @After
    public void tearDown(){
        System.out.println("Tear down method");
    }

    @Given("^nandkumar without \"([^\"]*)\" and \"([^\"]*)\" example concept$")
    public void nandkumar_without_and_example_concept(String username, String password){
        System.out.println(username);
        System.out.println(password);
    }

    @Given("^nandkumar datatable concept$")
    public void nandkumar_datatable_concept(DataTable data)   {
        List<List<String>> dat=data.raw();
        System.out.println(dat.get(0).get(0));
        System.out.println(dat.get(0).get(1));

    }

    @Given("^nandkumar map concepts$")
    public void nandkumar_map_concepts(DataTable data)   {
        for(Map<String, String> map : data.asMaps(String.class,String.class)) {
            System.out.println(map.get("username"));
            System.out.println(map.get("password"));
        }
    }

    @Given("^nandkumar \"([^\"]*)\" and \"([^\"]*)\" with example concepts$")
    public void nandkumar_and_with_example_concepts(String username, String password)   {
        System.out.println(username);
        System.out.println(password);
    }
}
