package Objects.ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAccountFailed {
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
