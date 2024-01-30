package Tests;

import RequestObject.RequestAccount;
import ResponseObject.ResponseAccountFailed;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationEmptyUsernameTest {
    public String userID;
    public String username;
    public String password;
    public String token;
    public Integer code;
    public String message;


    @Test
    public void testMethod(){

        System.out.println("Step 1 - create user");
        createUser();

    }

    public void createUser(){  //PASUL 1 CREEAM USERUL CARE SA NE OFERE ID-UL

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        //Configuram request-ul

        username = "";
        password = "Password!@1234";

        RequestAccount requestAccount = new RequestAccount(username,password );
        requestSpecification.body(requestAccount);


        //Accesam response-ul

        Response response = requestSpecification.post("/Account/v1/User");
        ResponseBody body = response.getBody();
        body.prettyPrint();

        //Validam statusul requestului

        Assert.assertEquals(response.getStatusCode(), 400); //principala validare

        //Validam response body-ul requestului

        ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);

        Assert.assertEquals(responseAccountFailed.getCode(), 1200);
        Assert.assertEquals(responseAccountFailed.getMessage(), "UserName and Password required.");

    }
}
