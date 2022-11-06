package API_TestCases;

import POJO_Classes.*;
import io.restassured.*;
import io.restassured.http.*;

import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * @author Nandkumar Babar
 */
public class nb {
    public static void main(String[] args) {
        AddBookRequest a=new AddBookRequest();

        a.setName("nandu");
        a.setAisle("babar1");
        a.setAuthor("BABARSAHEB");
        a.setIsbn("100");

        RestAssured.baseURI="http://216.10.245.166";

        String nbnb = given().contentType(ContentType.JSON)
                .body(a).log().body().when().post("/Library/Addbook.php")
                .then().extract().response().asString();

        System.out.println(nbnb);



    }
}
