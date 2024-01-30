package ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAccountAuthSuccess {
    //invatam despre adnotari JsonProperty numit si alias, porecla

    @JsonProperty("userID")
    private String userID;

    @JsonProperty("username")
    private String username;

    //invatam conceptul Compozitie (verisor cu mostenirea) - compozitia construieste obiecte

    @JsonProperty("books")
    private List<BookObject> books;

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public List<BookObject> getBooks() {
        return books;
    }
}
