package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import Rest.RestResponseStatus;
import Service.ServiceImplementation.AccountServiceImpl;
import com.beust.ah.A;
import io.restassured.response.Response;
import org.testng.Assert;

public class AccountActions {

    public AccountServiceImpl accountService;
    public ResponseAccountSuccess createNewAccount(RequestAccount requestAccount){
        accountService = new AccountServiceImpl();
        Response response = accountService.createAccount(requestAccount);
        Assert.assertEquals(RestResponseStatus.SC_CREATED, response.getStatusCode());

        // perform various validations onto the request response body:
        // deserializing the response body into an instance of the ResponseAccountAuthSuccess class
        // this assumes that the response body represents a successful authentication response
        // ResponseAccountAuthSuccess is a Java class defined in the Response Object package, representing the structure of that response
        ResponseAccountSuccess responseAccountSuccess = response.body().as(ResponseAccountSuccess.class);
        // validate that the UserID is not null
        Assert.assertNotNull(responseAccountSuccess.getUserID());
        // validate that the request username is the same as the response one
        Assert.assertEquals(responseAccountSuccess.getUsername(), requestAccount.getUserName());
        // validate that the Books attribute is not null
        Assert.assertNotNull(responseAccountSuccess.getBooks());

        return responseAccountSuccess;
    }

    public ResponseTokenSuccess generateToken(RequestAccountToken requestAccountToken){
        accountService = new AccountServiceImpl();
        Response response = accountService.generateToken(requestAccountToken);
        Assert.assertEquals(RestResponseStatus.SC_OK, response.getStatusCode());

        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(), "Success");
        Assert.assertEquals(responseTokenSuccess.getResult(), "User authorized successfully.");
        return responseTokenSuccess;
    }

}
