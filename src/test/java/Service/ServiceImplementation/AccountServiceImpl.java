package Service.ServiceImplementation;

import Endpoints.AccountEndpoints;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Service.APIService.AccountAPIService;
import Service.InterfaceService.AccountServiceInterface;
import io.restassured.response.Response;

public class AccountServiceImpl implements AccountServiceInterface {
// this is a service implementation for handling various account-related operations
    public AccountAPIService accountAPIService;
    @Override
    public Response createAccount(RequestAccount requestAccount) {
        accountAPIService = new AccountAPIService();
        return accountAPIService.post(requestAccount, AccountEndpoints.ACCOUNT_CREATE);
    }

    @Override
    public Response generateToken(RequestAccountToken requestAccountToken) {
        accountAPIService = new AccountAPIService();
        return accountAPIService.post(requestAccountToken, AccountEndpoints.ACCOUNT_TOKEN);
    }

    @Override
    public Response getSpecificAccount(String userID, String token) {
        accountAPIService = new AccountAPIService();
        String finalEndpoint = AccountEndpoints.ACCOUNT_USERSPECIFIC.replace("{userID}", userID);
        return accountAPIService.get(finalEndpoint,token);
    }
}
