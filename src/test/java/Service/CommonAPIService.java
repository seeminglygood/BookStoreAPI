package Service;

import LoggerUtility.LoggerUtility;
import Rest.RestRequest;
import Rest.RestRequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIService {
// this is a utility class for making common API requests with different parameters using the RestAssured library

    public Response post(Object body, String url) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.body(body);

        APIServiceHelper.requestLogs(requestSpecification, url, RestRequestType.REQUEST_POST);

        // calls the internal method performRequest to execute the request
        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, url);
        return response;
    }

    public Response post(Object body, String url, String token) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);
        requestSpecification.body(body);

        // to implement here: logs for request/ response

        // calls the internal method performRequest to execute the request
        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, url);
        return response;
    }

    public Response get(String url, String token) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);

        // to implement here: logs for request/ response

        // calls the internal method performRequest to execute the request
        Response response = performRequest(RestRequestType.REQUEST_GET, requestSpecification, url);
        return response;
    }

    // this is a private method that performs the actual HTTP requests
    // it creates a new instance of RestRequest and calls its performRequest method
    // passes the request type, request specification, and endpoint URL
    private Response performRequest(String requestType, RequestSpecification requestSpecification, String url) {
        return new RestRequest().performRequest(requestType, requestSpecification, url);
    }
}
