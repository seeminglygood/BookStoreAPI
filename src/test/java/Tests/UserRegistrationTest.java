package Tests;

import RequestObject.RequestAccount;
import RequestObject.RequestAccountToken;
import ResponseObject.ResponseAccountAuthSuccess;
import ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistrationTest {
    public String userID;
    public String username;
    public String password;
    public String token;


    @Test
    public void testMethod(){
        System.out.println("Step 1 - create user");
        createUser();

        System.out.println("Step 2 - generate token");
        generateToken();

        System.out.println("Step 3 - obtain new user");
        interractNewUser();

    }

    public void createUser(){

        RequestSpecification requestSpecification = RestAssured.given(); //configuram clientul cu anumite specificatii
        requestSpecification.baseUri("https://demoqa.com"); //specficam url-ul de baza pe care vrem sa il configuram
        requestSpecification.contentType("application/json"); //specificam ca e contentul de tip Jason

        //Configuram request-ul

        username = "Letitia" + System.currentTimeMillis(); //va genera valoare unica
        password = "Password!@1";

//        JSONObject requestBody = new JSONObject();  //construim un body de tipul Jason, functioneaza ca un fel de hashmap (cheie - valoare)
//        requestBody.put("userName",userName);  //stabilim cheia-valoarea
//        requestBody.put("password", "Password!??@#1234");  //stabilim cheia-valoarea

        //Am facut SEREALIZARE, am transformat body-ul sub forma unui obiect facut de noi
        RequestAccount requestAccount = new RequestAccount(username,password);
        requestSpecification.body(requestAccount);   //atasam body-ul pe constructia clientului (cea de sus)

        //Accesam response-ul

        Response response = requestSpecification.post("/Account/v1/User");  // accesam raspunsul trimitand un request de tip POST
//        System.out.println(response.body());
        ResponseBody body = response.getBody();
        body.prettyPrint(); //folosim in loc de system out

        //Validam statusul requestului

        Assert.assertEquals(response.getStatusCode(), 201); //principala validare

        //Validam response body-ul requestului

        ResponseAccountAuthSuccess responseAccountAuthSuccess = response.body().as(ResponseAccountAuthSuccess.class);
        Assert.assertNotNull(responseAccountAuthSuccess.getUserID());  //verificam ca exista o valoare pt id, cat nu e nul
        Assert.assertEquals(responseAccountAuthSuccess.getUsername(), username); //verif ca username are valoarea din request
        Assert.assertNotNull(responseAccountAuthSuccess.getBooks());  //verificam ca books sa contina cel putin "["

        userID = responseAccountAuthSuccess.getUserID();

    }

    //facem un request POST care ne da token-ul - Acum facem autentificarea

    public void generateToken(){

        RequestSpecification requestSpecification = RestAssured.given(); //configuram clientul cu anumite specificatii
        requestSpecification.baseUri("https://demoqa.com"); //specficam url-ul de baza pe care vrem sa il configuram
        requestSpecification.contentType("application/json"); //specificam ca e contentul de tip Jason

        RequestAccountToken requestAccountToken = new RequestAccountToken(username,password);

        requestSpecification.body(requestAccountToken);   //atasam body-ul pe constructia clientului (cea de sus)


        //Accesam response-ul

        Response response = requestSpecification.post("/Account/v1/GenerateToken");  // accesam raspunsul trimitand un request de tip POST
//        System.out.println(response.body());
        ResponseBody body = response.getBody();
        body.prettyPrint(); //folosim in loc de system out

        Assert.assertEquals(response.getStatusCode(), 200);

        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);
        Assert.assertNotNull(responseTokenSuccess.getToken());  //verificam ca exista o valoare pt id, cat nu e nul
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(),"Success"); //verif ca username are valoarea din request
        Assert.assertEquals(responseTokenSuccess.getResult(), "User authorized successfully.");  //verificam ca books sa contina cel putin "["

        token = responseTokenSuccess.getToken();

    }
    //facem un GET pentru userul creat/generat

    public void interractNewUser(){

        RequestSpecification requestSpecification = RestAssured.given(); //configuram clientul cu anumite specificatii
        requestSpecification.baseUri("https://demoqa.com"); //specficam url-ul de baza pe care vrem sa il configuram
        requestSpecification.contentType("application/json"); //specificam ca e contentul de tip Jason
        requestSpecification.header("Authorization", "Bearer " + token);

        Response response = requestSpecification.get("/Account/v1/User/" + userID); //compunere de endpoint

        Assert.assertEquals(response.getStatusCode(), 200);

        ResponseAccountAuthSuccess responseAccountAuthSuccess = response.body().as(ResponseAccountAuthSuccess.class);
        Assert.assertNotNull(responseAccountAuthSuccess.getUserID());  //verificam ca exista o valoare pt id, cat nu e nul
        Assert.assertEquals(responseAccountAuthSuccess.getUsername(), username); //verif ca username are valoarea din request
        Assert.assertNotNull(responseAccountAuthSuccess.getBooks());  //verificam ca books sa contina cel putin "["

    }
}
