package ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseTokenSuccess {
    //invatam despre adnotari JsonProperty numit si alias, porecla

    @JsonProperty("token")
    private String token;

    @JsonProperty("expires")
    private String expires;

    //invatam conceptul Compozitie (verisor cu mostenirea) - compozitia construieste obiecte

    @JsonProperty("status")
    private String status;

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    private String result;
}
