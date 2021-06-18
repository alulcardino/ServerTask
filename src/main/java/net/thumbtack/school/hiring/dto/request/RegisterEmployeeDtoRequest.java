package net.thumbtack.school.hiring.dto.request;

public class RegisterEmployeeDtoRequest {
    private String firstName;
    private String email;
    private String login;
    private String password;

    public RegisterEmployeeDtoRequest(String firstName, String login, String password, String email) {
        this.firstName = firstName;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
