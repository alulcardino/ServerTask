package net.thumbtack.school.hiring.dto.request;

public class RegisterEmployerDtoRequest {
    private String login;
    private String password;
    private String firstName;
    private String companyName;
    private String address;
    private String email;

    public RegisterEmployerDtoRequest(String firstName, String login, String password, String email, String companyName, String address) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.companyName = companyName;
        this.address = address;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
