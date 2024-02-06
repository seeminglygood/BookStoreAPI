package Service;

import LoggerUtility.LoggerUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.internal.RequestSpecificationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;

public class APIServiceHelper {

    // this class contains methods that display information about the request and response

    public static void requestLogs(RequestSpecification requestSpecification, String path, String methodType){
        LoggerUtility.info(" ===== Request Information ===== ");
        LoggerUtility.info(getRequestURL(path));
        LoggerUtility.info(getRequestMethod(methodType));
        LoggerUtility.info(getRequestBody(requestSpecification));

    }
    private static String getRequestURL(String path){
        return "Request URL: " + "https://demoqa.com" + path;
    }

    private static String getRequestMethod(String method){
        return "Request method: " + method;
    }

    private static String getRequestBody(RequestSpecification requestSpecification){
        String requestBody="Request BODY: \n";
        Object object=((RequestSpecificationImpl) requestSpecification).getBody();
        if (object!=null)
        {
            ObjectMapper mapper=new ObjectMapper();
            try {
                requestBody=requestBody+mapper.readTree(object.toString()).toPrettyString();
            } catch (JsonProcessingException e) {

            }
        }
        return requestBody;

    }
}
