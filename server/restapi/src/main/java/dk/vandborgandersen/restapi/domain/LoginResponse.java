package dk.vandborgandersen.restapi.domain;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class LoginResponse {
    private boolean authenticationOk;
    private String message;
    private String username;

    public boolean isAuthenticationOk() {
        return authenticationOk;
    }

    public void setAuthenticationOk(boolean authenticationOk) {
        this.authenticationOk = authenticationOk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
