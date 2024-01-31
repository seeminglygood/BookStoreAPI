package Tests;

import RequestObject.RequestAccount;
import ResponseObject.ResponseAccountFailed;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationInvalidPasswordTest {
    public String username;
    public String password;
    @Test
    public void testMethod(){
        System.out.println("Step 1 - create user");
        createUser();
    }
    public void createUser(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        username = "Letitia" + System.currentTimeMillis();
        password = "Password";

        RequestAccount requestAccount = new RequestAccount(username,password );
        requestSpecification.body(requestAccount);

        Response response = requestSpecification.post("/Account/v1/User");
        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 400);

        ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);

        Assert.assertEquals(responseAccountFailed.getCode(), 1300);
        Assert.assertEquals(responseAccountFailed.getMessage(), "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }
}
