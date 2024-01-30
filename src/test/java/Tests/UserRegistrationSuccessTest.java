package Tests;

import RequestObject.RequestAccount;
import RequestObject.RequestAccountToken;
import ResponseObject.ResponseAccountAuthSuccess;
import ResponseObject.ResponseAccountSuccess;
import ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationSuccessTest {
    public String userID;
    public String username;
    public String password;
    public String token;


    @Test
    public void testMethod(){
        System.out.println("Step 1 - Create user");
        createUser();

        System.out.println("Step 2 - Generate token");
        generateToken();

        System.out.println("Step 3 - Get new user");
        interactNewUser();

    }

    public void createUser(){

        // ***** Define client characteristics / specifications: *****
        // -----------------------------------------------------------------------------------------------------------------------------------------
        // RestAssured.given(): RestAssured is a class in the Rest Assured library
        // it provides a starting point for building HTTP requests for testing RESTful APIs
        // the given() method returns an instance of RequestSpecification
        // the result of RestAssured.given() is assigned to a variable named requestSpecification
        // this variable can then be used to further customize and build an HTTP request
        RequestSpecification requestSpecification = RestAssured.given();

        /* after obtaining the requestSpecification object, you can chain methods on it to
        define various aspects of the HTTP request, such as setting headers, query parameters,
        authentication details, and the request body */
        requestSpecification.baseUri("https://demoqa.com"); //specify baseURL
        requestSpecification.contentType("application/json"); //specify json content type

        // ***** Configure the request: *****
        // -----------------------------------------------------------------------------------------------------------------------------------------

        username = "Letitia" + System.currentTimeMillis(); //adding a timestamp to the username, so it generates a 'unique' string
        password = "Password!@1";

        // the RequestAccount class is defined under RequestObject package
        // requestAccount is a variable of type RequestAccount
        // the constructor call above creates a new instance of RequestAccount, initializing it with the provided username and password params
        // the newly created RequestAccount object is assigned to the variable requestAccount
        // requestAccount is now referencing an instance of the RequestAccount class
        // now this object can be used to interact with the methods and properties defined in the RequestAccount class
        RequestAccount requestAccount = new RequestAccount(username,password);

        // the body method is used to set the request body for a given HTTP request
        // passing the Java object requestAccount as the request body
        // requestAccount object represents the payload of the request
        // RestAssured automatically serializes this object to the appropriate format (in this case JSON) and set it as request body
        // the serialization process is crucial when you need to send structured data, such as an object, in the request body
        requestSpecification.body(requestAccount);

        // ***** Access the response *****
        // -----------------------------------------------------------------------------------------------------------------------------------------

        // access the response by sending a POST request to the "/Account/v1/User" endpoint using the already configured requestSpecification
        // capture the response from the server in the response variable (instance of the Response class in RestAssured)
        Response response = requestSpecification.post("/Account/v1/User");

        // obtains the body of the response using the getBody() method of the Response class
        ResponseBody body = response.getBody();

        // prints the response body in a human-readable format
        // prettyPrint() method is a convenient way to display the response body with proper indentation, making it easier to read
        body.prettyPrint();

        // ***** Perform validations *****
        // -----------------------------------------------------------------------------------------------------------------------------------------

        // validate the HTTP status code of the response received from the server:
        Assert.assertEquals(response.getStatusCode(), 201);

        // perform various validations onto the request response body:
        // deserializing the response body into an instance of the ResponseAccountAuthSuccess class
        // this assumes that the response body represents a successful authentication response
        // ResponseAccountAuthSuccess is a Java class defined in the Response Object package, representing the structure of that response
        ResponseAccountSuccess responseAccountSuccess = response.body().as(ResponseAccountSuccess.class);
        // validate that the UserID is not null
        Assert.assertNotNull(responseAccountSuccess.getUserID());
        // validate that the request username is the same as the response one
        Assert.assertEquals(responseAccountSuccess.getUsername(), username);
        // validate that the Books attribute is not null
        Assert.assertNotNull(responseAccountSuccess.getBooks());

        //  retrieve the value of the userID attribute from the ResponseAccountSuccess object
        // assign the retrieved value to the variable userID
        userID = responseAccountSuccess.getUserID();

    }


    public void generateToken() {

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        RequestAccountToken requestAccountToken = new RequestAccountToken(username, password);
        requestSpecification.body(requestAccountToken);


        Response response = requestSpecification.post("/Account/v1/GenerateToken");
        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);

        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(), "Success");
        Assert.assertEquals(responseTokenSuccess.getResult(), "User authorized successfully.");

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