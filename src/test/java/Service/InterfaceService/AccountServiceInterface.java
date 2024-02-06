package Service.InterfaceService;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import io.restassured.response.Response;

public interface AccountServiceInterface {

    // create an abstract method
    Response createAccount(RequestAccount requestAccount);
    Response generateToken(RequestAccountToken requestAccountToken);
    Response getSpecificAccount(String userID, String token);

}
