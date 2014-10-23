package dk.vandborgandersen.restapi.domain;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class LoginResponse {
    private boolean authenticationOk;
    private String message;
    private Person person;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
