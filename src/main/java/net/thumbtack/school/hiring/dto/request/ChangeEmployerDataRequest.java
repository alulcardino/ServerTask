package net.thumbtack.school.hiring.dto.request;

public class ChangeEmployerDataRequest {
    private final String token;
    private final String name;
    private final String email;
    private final String password;
    private final String companyName;
    private final String address;

    public ChangeEmployerDataRequest(String token, String name, String email, String password, String companyName, String address) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }
}
