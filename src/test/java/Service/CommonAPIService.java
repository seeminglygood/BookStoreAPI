package Service;

import Rest.RestRequest;
import Rest.RestRequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIService {
    // this class contains methods for various types of requests with different parameters

    public Response post(Object body, String url){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.body(body);

        // to implement here: logs for request/ response

            Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, url);
            return response;

    }

    public Response post(Object body, String url, String token){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);
        requestSpecification.body(body);

        // to implement here: logs for request/ response

        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, url);
        return response;

    }

    public Response get(String url, String token){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer " + token);

        // to implement here: logs for request/ response

        Response response = performRequest(RestRequestType.REQUEST_GET, requestSpecification, url);
        return response;

    }

    // a RestRequest instance that would call the performRequest method
    private Response performRequest(String requestType, RequestSpecification requestSpecification, String url){
        return new RestRequest().performRequest(requestType, requestSpecification, url);


    }

}
