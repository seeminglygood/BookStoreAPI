package Service.ServiceImplementation;

import Endpoints.AccountEndpoints;
import RequestObject.RequestAccount;
import RequestObject.RequestAccountToken;
import Service.APIService.AccountAPIService;
import Service.InterfaceService.AccountServiceInterface;
import io.restassured.response.Response;

public class AccountServiceImpl implements AccountServiceInterface {

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
