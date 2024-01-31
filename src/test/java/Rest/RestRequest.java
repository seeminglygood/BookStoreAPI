package Rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequest {
    //method that makes a request type to an endpoint
    //provides a convenient way to make different types of HTTP requests by encapsulating the details of request configuration and execution
    //follows the principles of RESTful API design by associating specific HTTP methods with their corresponding actions
    public Response performRequest(String requestType, RequestSpecification requestSpecification, String url){
        switch (requestType){
            case RestRequestType.REQUEST_DELETE:
                return prepare(requestSpecification).delete(url);
            case RestRequestType.REQUEST_GET:
                return prepare(requestSpecification).get(url);
            case RestRequestType.REQUEST_POST:
                return prepare(requestSpecification).post(url);
            case RestRequestType.REQUEST_PUT:
                return prepare(requestSpecification).put(url);
        }
        return null;
    }

    // configure the client settings
    public RequestSpecification prepare(RequestSpecification requestSpecification){
        // dynamic config needed here later:
        requestSpecification.baseUri("https://demoqa.com"); //specify baseURL
        requestSpecification.contentType("application/json"); //specify json content type
        return requestSpecification;
    }
}
