package ZzZz;

import Objects.RequestObject.RequestAccount;
import Objects.ResponseObject.ResponseAccountFailed;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationUserAlreadyExistsTest {
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

        username = "Letitia";
        password = "Password!??@#1234";

        RequestAccount requestAccount = new RequestAccount(username,password );
        requestSpecification.body(requestAccount);

        Response response = requestSpecification.post("/Account/v1/User");
        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 406);

        ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);
        Assert.assertEquals(responseAccountFailed.getCode(), 1204);
        Assert.assertEquals(responseAccountFailed.getMessage(), "User exists!");
    }
}
