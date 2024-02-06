package Tests;

import Actions.AccountActions;
import Hooks.Hooks;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountAuthSuccess;
import Objects.ResponseObject.ResponseAccountSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationSuccessTestFinal extends Hooks {
        public String userID;
        public String username;
        public String password;
        public String token;
        public AccountActions accountActions;
        @Test
        public void testMethod(){
            System.out.println("Step 1 - Create user");
            createUser();

            System.out.println("Step 2 - Generate token");
            generateToken();
//
//            System.out.println("Step 3 - Get new user");
//            interactNewUser();
        }
        public void createUser(){
            accountActions = new AccountActions();
            username = "Letitia" + System.currentTimeMillis(); //adding a timestamp to the username, so it generates a 'unique' string
            password = "Password!@1";
            RequestAccount requestAccount = new RequestAccount(username,password);
            ResponseAccountSuccess responseAccountSuccess = accountActions.createNewAccount(requestAccount);
            userID = responseAccountSuccess.getUserID();
     }

        public void generateToken() {
            accountActions = new AccountActions();

            RequestAccountToken requestAccountToken = new RequestAccountToken(username, password);
            ResponseTokenSuccess responseTokenSuccess = accountActions.generateToken(requestAccountToken);
            token = responseTokenSuccess.getToken();
        }

        public void interactNewUser() {
            RequestSpecification requestSpecification = RestAssured.given();
            requestSpecification.baseUri("https://demoqa.com");
            requestSpecification.contentType("application/json");
            requestSpecification.header("Authorization", "Bearer " + token);

            Response response = requestSpecification.get("/Account/v1/User/" + userID);
            ResponseBody body = response.getBody();
            body.prettyPrint();

            Assert.assertEquals(response.getStatusCode(), 200);

            ResponseAccountAuthSuccess responseAccountAuthSuccess = response.body().as(ResponseAccountAuthSuccess.class);

            Assert.assertNotNull(responseAccountAuthSuccess.getUserId());
            Assert.assertEquals(responseAccountAuthSuccess.getUsername(), username);
            Assert.assertNotNull(responseAccountAuthSuccess.getBooks());
        }
}

